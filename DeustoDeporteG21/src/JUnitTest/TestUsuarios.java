package JUnitTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import domain.Gestor;
import domain.Usuario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestUsuarios {

    private Gestor gestor;

    @Before
    public void setUp() {
        gestor = new Gestor(); 
    }

    @Test
    public void testCargarUsuariosCSV() {
       
        String tempFileName = "tempUsuarios.csv";
        crearArchivoTemporal(tempFileName);

        
        gestor.cargarUsuariosCSV(tempFileName);

       

        
        assertEquals(3, gestor.getUsuarios().size());

        
        assertTrue(gestor.getUsuarios().contains(new Usuario("123", "John", "Doe", "Address1", "john@example.com", "password")));

        
       // eliminarArchivoTemporal(tempFileName);
    }

   
    private void crearArchivoTemporal(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
           
            writer.write("123,John,Doe,Address1,john@example.com,password\n");
            writer.write("456,Jane,Smith,Address2,jane@example.com,pass123\n");
            writer.write("789,Bob,Johnson,Address3,bob@example.com,abc123\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eliminarArchivoTemporal(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
