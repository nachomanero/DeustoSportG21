package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import domain.Gestor;
import domain.Usuario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class GestorTest {

    private Gestor gestor;
    
    @Before
    public void setUp() {
    	gestor = new Gestor();
       
    }

    @Test
    public void testCargarUsuariosCSV() {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("usuariosTest", ".csv");
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write("12345678I,John,Doe,123 Main St,john.doe@example.com,password123\n" +
                             "98765432J,Jane,Smith,456 Oak St,jane.smith@example.com,securepass\n"+
                             "66672234A,Mikel,Gonzalez,Gran Via 23,mikelgo@gmail.com,mikel1234\n");
            }

            gestor.cargarUsuariosCSV(tempFile.getAbsolutePath());

            // Crear usuarios esperados
            HashSet<Usuario> usuariosEsperados = new HashSet<>();
            usuariosEsperados.add(new Usuario("12345678I", "John", "Doe", "123 Main St", "john.doe@example.com", "password123"));
            usuariosEsperados.add(new Usuario("98765432J", "Jane", "Smith", "456 Oak St", "jane.smith@example.com", "securepass"));
            usuariosEsperados.add(new Usuario("66672234A", "Mikel", "Gonzalez", "Gran Via 23", "mikelgo@gmail.com", "mikel1234"));

            // Obtener usuarios actuales
            HashSet<Usuario> usuariosObtenidos = gestor.getUsuarios();

            // Convertir conjuntos a arrays para la comparación
            Usuario[] esperadosArray = usuariosEsperados.toArray(new Usuario[0]);
            Usuario[] obtenidosArray = usuariosObtenidos.toArray(new Usuario[0]);

            // Comparar los arrays
            assertEquals(esperadosArray, obtenidosArray);

            // Eliminar el archivo temporal
            tempFile.delete();

        } catch (IOException e) {
            e.printStackTrace();
            fail("Excepción durante la prueba: " + e.getMessage());
        } finally {
            if (tempFile != null && !tempFile.delete()) {
                System.err.println("No se pudo eliminar el archivo temporal.");
            }
        }
    }
    @Test
    public void testEliminarUsuario() throws IOException {
    	Usuario usuarioAEliminar = new Usuario("66672234A", "Mikel", "Gonzalez", "Gran Via 23", "mikelgo@gmail.com", "mikel1234");
    	//gestor.(usuarioAEliminar);
		//gestor.(usuarioAEliminar);
		assertFalse(gestor.getUsuarios().contains(usuarioAEliminar));
   
    	}

    
    }


