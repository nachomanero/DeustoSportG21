package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import domain.Gestor;
import domain.Usuario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuariosTest {

    private Gestor gestorUsuarios;

    @Before
    public void setUp() {
        gestorUsuarios = new Gestor();
    }

    @Test
    void loadUsuarios() {
        
        String contenidoCSV = "123456789,John,Doe,123 Main St,john.doe@example.com,password123\n" +
                              "987654321,Jane,Smith,456 Oak St,jane.smith@example.com,securepass";

        // Crear un archivo temporal con el contenido CSV
        Path tempFile;
        try {
            tempFile = Files.createTempFile("usuariosTest", ".csv");
            Files.write(tempFile, contenidoCSV.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error al crear el archivo temporal", e);
        }

        // Llamar al método cargarUsuariosCSV con el archivo temporal
        gestorUsuarios.cargarUsuariosCSV(tempFile.toString());

        // Verificar que los usuarios se cargaron correctamente
        List<Usuario> usuariosEsperados = new ArrayList<>();
        usuariosEsperados.add(new Usuario("123456789", "John", "Doe", "123 Main St", "john.doe@example.com", "password123"));
        usuariosEsperados.add(new Usuario("987654321", "Jane", "Smith", "456 Oak St", "jane.smith@example.com", "securepass"));

        assertEquals(usuariosEsperados, gestorUsuarios.getUsuarios());
    }

    @Test
    void errorCSV() {
        // Intentar cargar usuarios desde un archivo inexistente
        assertThrows(IOException.class, () -> gestorUsuarios.cargarUsuariosCSV("archivoInexistente.csv"));
    }
}

