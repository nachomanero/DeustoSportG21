package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


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

import java.util.Collection;
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
    public void testAniadirUsuario() {
    	try {
        Usuario usuario = new Usuario("12345678P", "John", "Brown", "Gra Via 34 5A", "johnBrown@gmail.com", "IndioTxikiBatZen1234");
        gestorBD.añadirUsuario(usuario);
        assertTrue(((Collection<Usuario>) gestorBD).contains(usuario));
        LOGGER.log(java.util.logging.Level.INFO,"Usuario añadido con exito.");
    	}catch(Exception e) {
    		LOGGER.log(java.util.logging.Level.SEVERE,"Error al aniadir usuario"+e.getCause());
    	}
    }
    
	@Test
    public void testEliminarUsuario() {
		try {
        Usuario usuario = new Usuario("12345678P", "John", "Brown", "Gra Via 34 5A", "johnBrown@gmail.com", "IndioTxikiBatZen1234");
        gestorBD.eliminarUsuario(usuario.getDni());
        assertFalse(((Collection<Usuario>) gestorBD).contains(usuario));
		}catch(Exception e) {
			LOGGER.log(java.util.logging.Level.SEVERE,"Error en la prueba de eliminar usuario."+e.getCause());
		}
	}
    
    @Test
    public void testAniadirClaseYeliminarla() {
    	try {
    		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyMMddHHmmssSSS");
    		java.util.Date fecha = formatoFecha.parse("2023-12-12");
            Clase clase = new Clase(523, "08:00", TipoActividad.YOGA, fecha, 1, 20);
            gestorBD.añadirClase(clase);
            assertTrue(gestorBD.obtenerRegistros("Clase", Clase.class).contains(clase));
            gestorBD.eliminarClase(523);
            assertFalse(gestorBD.obtenerRegistros("Clase", Clase.class).contains(clase));
            LOGGER.log(java.util.logging.Level.INFO,"Clase aniadida y eliminada correctamente.");
    	}catch(Exception e) {
    		LOGGER.log(java.util.logging.Level.SEVERE,"Error en la prueba."+e.getCause());
    	}
    }
    @Test
    public void testCrearReservayCancelarReserva() {
    	try {
    		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyMMddHHmmssSSS");
    		java.util.Date fecha = formatoFecha.parse("2023-12-12");
            Reserva reserva = new Reserva("12348445Z", TipoActividad.YOGA, 1, fecha, "08:00");
            gestorBD.añadirReservas(Collections.singletonMap("123456789", Collections.singletonList(reserva)));
            assertTrue(gestorBD.obtenerRegistros("Reserva", Reserva.class).contains(reserva));
            gestorBD.cancelarReserva("123456789", TipoActividad.YOGA, 1,(java.sql.Date) fecha, "08:00");
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
    		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyMMddHHmmssSSS");
    		java.util.Date fecha = formatoFecha.parse("2023-12-12");
        Clase clase1 = new Clase(788, "08:00", TipoActividad.YOGA,fecha, 1, 20);
        Clase clase2 = new Clase(639, "10:00", TipoActividad.PILATES, fecha, 2, 15);
        Clase clase3 = new Clase(542, "08:10", TipoActividad.CROSSFIT, fecha, 3, 5);
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
    /*
    private Date SimpleDateFormat(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	private java.sql.Date SimpleDateFormat(java.sql.Date i) {
		return i;
	}
*/
    @Test
    public void testVisualizarClaseExistente() {
    	try {
    	      Clase clase = new Clase(836, "08:00", TipoActividad.YOGA, new Date(), 1, 20);
    	      gestorBD.añadirClase(clase);
    	      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	      System.setOut(new PrintStream(outputStream));
    	      gestorBD.visualizarClase(836);
    	      assertTrue(outputStream.toString().contains("Detalles de la clase:"));
    	      assertTrue(outputStream.toString().contains("ID de Clase: 836"));
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
        assertTrue(outputStream.toString().contains("No se encontró ninguna clase con ID 343"));
        LOGGER.log(java.util.logging.Level.INFO,"Prueba de clases que no existen exitosa.");
    }

}

