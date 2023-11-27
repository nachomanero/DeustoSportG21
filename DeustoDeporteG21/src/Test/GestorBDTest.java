package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.System.Logger.Level;
import java.sql.SQLException;
import javax.xml.catalog.CatalogException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import db.GestorBD;
import domain.Clase;
import domain.Reserva;
import domain.Sala;
import domain.TipoActividad;
import domain.Usuario;
import io.FicheroLogger;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
public class GestorBDTest {

    private static GestorBD gestorBD;
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());

    @BeforeClass
    public static void iniciarBD() {
        gestorBD = new GestorBD();
    }

    @SuppressWarnings("static-access")
	@AfterClass
    public static void cerrarBD() throws  SQLException {
    	if (gestorBD.connection != null && !gestorBD.connection.isClosed()) {
		    gestorBD.connection.close();
		    LOGGER.log(java.util.logging.Level.INFO,"BD Cerrada con exito.");
		}
    }

    @SuppressWarnings("static-access")
	@Test
    public void testCrearTablas() {
    	try {
    	gestorBD.crearTablas();
        assertNotNull(gestorBD.obtenerRegistros("Usuario", Usuario.class));
        assertNotNull(gestorBD.obtenerRegistros("Sala", Sala.class));
        assertNotNull(gestorBD.obtenerRegistros("Clase", Clase.class));
        assertNotNull(gestorBD.obtenerRegistros("Reserva", Reserva.class));
    	}catch(Exception e){
    		LOGGER.log(java.util.logging.Level.SEVERE,"Error al crear las tablas."+e.getCause());
    	}
    }
    @Test
    public void testAniadiryEliminarUsuario() {
    	try {
        Usuario usuario = new Usuario("79134735S", "Iñigo", "Diaz", "Gran Via 55_1A", "inigodm12@gmail.com", "100ComplejosSinSentido");
        gestorBD.añadirUsuario(usuario);
        LOGGER.log(java.util.logging.Level.INFO,"Usuario aniadido correctamente");
        assertTrue(gestorBD.obtenerRegistros("Usuario", Usuario.class).contains(usuario));
        gestorBD.eliminarUsuario("79134735S");
        assertFalse(gestorBD.obtenerRegistros("Usuario", Usuario.class).contains(usuario));
        LOGGER.log(java.util.logging.Level.INFO,"Usuario eliminado correctamente");
    }catch(Exception e){
    	LOGGER.log(java.util.logging.Level.SEVERE,"Error al añadir o eliminar usuario"+e.getCause());
    }
   }
    @Test
    public void testAniadirClaseYeliminarla() {
    	try {
            Clase clase = new Clase(1, "08:00", TipoActividad.YOGA, new Date(), 1, 20);
            gestorBD.añadirClase(clase);
            assertTrue(gestorBD.obtenerRegistros("Clase", Clase.class).contains(clase));
            gestorBD.eliminarClase(1);
            assertFalse(gestorBD.obtenerRegistros("Clase", Clase.class).contains(clase));
            
            LOGGER.log(java.util.logging.Level.INFO,"Clase aniadida y eliminada correctamente.");
    	}catch(Exception e) {
    		LOGGER.log(java.util.logging.Level.SEVERE,"Error en la prueba."+e.getCause());
    	}
    }
    //Comprobar si funciona,no se si esta bien, ha sido un poco idea feliz je je
    @Test
    public void testCrearReservayCancelarReserva() {
    	try {
            Reserva reserva = new Reserva("8445", TipoActividad.YOGA, 1, new Date(), "08:00");
            gestorBD.añadirReservas(Collections.singletonMap("123456789", Collections.singletonList(reserva)));
            assertTrue(gestorBD.obtenerRegistros("Reserva", Reserva.class).contains(reserva));
            gestorBD.cancelarReserva("123456789", TipoActividad.YOGA, 1, (java.sql.Date) new Date(), "08:00");
            assertFalse(gestorBD.obtenerRegistros("Reserva", Reserva.class).contains(reserva));
            LOGGER.log(java.util.logging.Level.INFO,"Prueba de reservas hecha correctamente.");
    	}catch(Exception e) {
    		LOGGER.log(java.util.logging.Level.SEVERE,"Error en la prueba."+e.getCause());
    	}

    }
    @Test
    public void testComprobarUsuarioExistente() {
    	try {
            Usuario usuario = new Usuario("47282324A", "Antonio", "Garcia", "Calle Mayor 32 3A", "garciaantonio@gmail.com", "NoSeADondeVoy12");
            gestorBD.añadirUsuario(usuario);
            assertTrue(gestorBD.comprobarUsuario("garciaantonio@gmail.com", "NoSeADondeVoy12"));
            assertFalse(gestorBD.comprobarUsuario("garciaantonio@gmail.com", "AntonioG1976"));
            LOGGER.log(java.util.logging.Level.INFO,"Prueba de usuario existente correcta.");
    	}catch(Exception e) {
    		LOGGER.log(java.util.logging.Level.SEVERE,"Error en la prueba de si existe el usuario."+e.getCause());
    	}
    }
    
    //Ver si lo de date funciona correctamente
    
    @Test
    public void testBuscarClasesPorFecha() {
    	try {
    		Clase clase1 = new Clase(1, "08:00", TipoActividad.YOGA, new Date(), 1, 20);
            Clase clase2 = new Clase(2, "10:00", TipoActividad.FUNCIONAL, new Date(), 1, 15);
            gestorBD.añadirClase(clase1);
            gestorBD.añadirClase(clase2);
            List<String> clasesEncontradas = gestorBD.buscarClasesPorFecha((java.sql.Date) new Date());
            assertTrue(clasesEncontradas.contains("08:00 - YOGA"));
            assertTrue(clasesEncontradas.contains("10:00 - Funcional"));
            LOGGER.log(java.util.logging.Level.INFO,"Prueba de busqueda de clases correcta.");
    	}catch(Exception e) {
    		LOGGER.log(java.util.logging.Level.SEVERE,"Error en la busqueda de clases."+e.getCause());
    	}
    }
    @Test
    public void testEditarClase() {
    	try {
            Clase clasePrueba = new Clase(1, "08:00", TipoActividad.YOGA, new Date(), 1, 20);
            gestorBD.añadirClase(clasePrueba);
            Clase claseModificada = new Clase(1, "10:00", TipoActividad.PILATES, new Date(), 2, 15);
            assertTrue(gestorBD.editarClase(claseModificada));
            assertEquals(claseModificada, gestorBD.obtenerRegistros("Clase", Clase.class).iterator().next());
            LOGGER.log(java.util.logging.Level.INFO,"Prueba de modificar clase correcta.");
    	}catch(Exception e) {
    		LOGGER.log(java.util.logging.Level.SEVERE,"Error en la prueba de editar una clase."+e.getCause());
    	}
    }
    @Test
    public void testObtenerTodasLasClases() {
    	try {
        Clase clase1 = new Clase(1, "08:00", TipoActividad.YOGA, new Date(), 1, 20);
        Clase clase2 = new Clase(2, "10:00", TipoActividad.PILATES, new Date(), 2, 15);
        Clase clase3 = new Clase(1, "08:10", TipoActividad.CROSSFIT, new Date(), 3, 5);
        gestorBD.añadirClase(clase1);
        gestorBD.añadirClase(clase2);
        gestorBD.añadirClase(clase3);
        List<Clase> ListaConTodasLasClases = gestorBD.obtenerTodasLasClases();
        assertTrue(ListaConTodasLasClases.contains(clase1));
        assertTrue(ListaConTodasLasClases.contains(clase2));
        assertTrue(ListaConTodasLasClases.contains(clase3));
        LOGGER.log(java.util.logging.Level.INFO,"Prueba de obtener clases correcta.");
    	}catch(Exception e) {
    		LOGGER.log(java.util.logging.Level.SEVERE,"Error en la prueba de obtener las clases."+e.getCause());
    	}
    }
    //Este tambien hay que mirar . :)
    @Test
    public void testVisualizarClaseExistente() {
    	try {
    	      Clase clase = new Clase(1, "08:00", TipoActividad.YOGA, new Date(), 1, 20);
    	      gestorBD.añadirClase(clase);
    	      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	      System.setOut(new PrintStream(outputStream));
    	      gestorBD.visualizarClase(1);
    	      assertTrue(outputStream.toString().contains("Detalles de la clase:"));
    	      assertTrue(outputStream.toString().contains("ID de Clase: 1"));
    	      assertTrue(outputStream.toString().contains("Hora: 08:00"));
    	      assertTrue(outputStream.toString().contains("Tipo de Actividad: YOGA"));
    	      LOGGER.log(java.util.logging.Level.INFO,"Prueba de visualizacion de clases correcta.");
    	}catch(Exception e) {
    		LOGGER.log(java.util.logging.Level.SEVERE,"Error en la rpeuab de visualizar clases existentes"+e.getCause());
    	}
    }
    @Test
    public void testVisualizarClaseNoExistente() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        gestorBD.visualizarClase(343);
        assertTrue(outputStream.toString().contains("No se encontró ninguna clase con ID 9343"));
        LOGGER.log(java.util.logging.Level.INFO,"Prueba de clases que no existen exitosa.");
    }

}

