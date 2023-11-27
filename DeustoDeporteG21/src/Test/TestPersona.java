package Test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import domain.Gestor;
import domain.Usuario;
public class TestPersona {
    private Gestor gestor;
	private Usuario Usuario;
	private String nombre = "Name";
	private String apellido = "Surname";
	private String direccion="direccion";
	private String dni="dni";
	private String correoElectronico= "correoElectronico";
	private String contraseña="contrasenia";
	
	@Before
	public void test() throws Exception{
		Usuario = new Usuario(dni,nombre,apellido,direccion,correoElectronico,contraseña);
	}
	@After
	public void tearDown() throws Exception {
		Usuario = null;
	}
	@Test
	public void testPerson() {
		assertNotNull(Usuario);
		assertEquals(apellido,Usuario.getDni());
		assertEquals(nombre, Usuario.getNombre());
		assertEquals(apellido, Usuario.getApellido());
		assertEquals(direccion,Usuario.getDireccion());
		assertEquals(correoElectronico,Usuario.getCorreoElectronico());
		assertEquals(contraseña,Usuario.getContrasena());
	}
	@Test
	public void testGetDNI() {
		assertEquals(dni, Usuario.getDni());
	}
	@Test
	public void testSetDNI() {
		String newDNI = "79134735S";
		Usuario.setNombre(newDNI);
		assertEquals(newDNI, Usuario.getDni());
	}
	@Test
	public void testGetName() {
		assertEquals(nombre, Usuario.getNombre());
	}
	@Test
	public void testSetName() {
		String newName = "Iñigo";
		Usuario.setNombre(newName);
		assertEquals(newName, Usuario.getNombre());
	}
	
	@Test
	public void testGetApellido() {
		assertEquals(apellido, Usuario.getApellido());
	}
	@Test
	public void testSetApellido() {
		String newApellido = "Diaz";
		Usuario.setNombre(newApellido);
		assertEquals(newApellido, Usuario.getApellido());
	}
	@Test
	public void testGetDireccion() {
		assertEquals(direccion, Usuario.getDireccion());
	}
	@Test
	public void testSetDireccion() {
		String newDireccion = "Gran via 34 4Izq";
		Usuario.setNombre(newDireccion);
		assertEquals(newDireccion, Usuario.getDireccion());
	}
	@Test
	public void testGetCorreoElectronico() {
		assertEquals(correoElectronico, Usuario.getCorreoElectronico());
	}
	@Test
	public void testSetCorreoElectronico() {
		String newCorreoElectronico = "inigodm12@gmail.com";
		Usuario.setNombre(newCorreoElectronico);
		assertEquals(newCorreoElectronico, Usuario.getCorreoElectronico());
	}
	@Test
	public void testGetContraseña() {
		assertEquals(contraseña, Usuario.getContrasena());
	}
	@Test
	public void testSetContraseña() {
		String newContraseña = "Ascdv234";
		Usuario.setNombre(newContraseña);
		assertEquals(newContraseña, Usuario.getContrasena());
	}

}
