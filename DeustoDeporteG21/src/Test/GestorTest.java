package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import db.GestorBD;
import domain.Gestor;
import domain.Usuario;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
                             "98765432J,Jane,Smith,456 Oak St,jane.smith@example.com,securepass");
            }

           
            gestor.cargarUsuariosCSV(tempFile.getAbsolutePath());

            
            HashSet<Usuario> usuariosEsperados = new HashSet<>();
            
            usuariosEsperados.add(new Usuario("12345678I", "John", "Doe", "123 Main St", "john.doe@example.com", "password123"));
            usuariosEsperados.add(new Usuario("98765432J", "Jane", "Smith", "456 Oak St", "jane.smith@example.com", "securepass"));
            assertEquals(usuariosEsperados, gestor.getUsuarios());
            
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
    	Usuario usuarioAEliminar = new Usuario("123456789", "John", "Doe", "123 Main St", "john.doe@example.com", "password123");
		//gestor.(usuarioAEliminar);
		//gestor.(usuarioAEliminar);
		assertFalse(gestor.getUsuarios().contains(usuarioAEliminar));
   
    	}

    
    }


