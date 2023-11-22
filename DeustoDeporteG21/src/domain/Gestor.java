package domain;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Scanner;

import db.GestorBD;

public class Gestor implements itfGestor{
	
	
	//Gestiona las operaciones sobre el modelo logico
	
	
	//esta clase se comunica el GestorBD para solicitar los datos
	
	//aqui deben estar los MAPAS, los SET, etc
	
	private HashSet<Usuario> usuarios;
	private HashSet<Clase> clases;
	private GestorBD gestorBD;
	//...
	
	public Gestor()
	{
		usuarios = new HashSet<Usuario>();
		clases = new HashSet<Clase>();
		gestorBD = new GestorBD();
		
		//...

	}

	@Override
	public void cargarUsuariosCSV(String nomfichUsuarios) {
		// TODO Auto-generated method stub
		try {
			Scanner sc = new Scanner(new FileReader(nomfichUsuarios));
			String linea;
			while(sc.hasNext()) {
				linea = sc.nextLine();
				String [] partes = linea.split(",");
				String dni = partes[0];
				String nombre = partes[1];
				String apellido = partes[2];
				String direccion = partes[3];
				String correo = partes[4];
				String contraseña = partes[5];
				
				Usuario u = new Usuario(dni, nombre, apellido, direccion, correo, contraseña);
				usuarios.add(u);
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.format("\n* Error al abrir el archivo de Usuarios: %s", e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Override
	public void cargarClasesCSV(String nomfichClases) {
		// TODO Auto-generated method stub
		try {
			
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
			Scanner sc = new Scanner(new FileReader(nomfichClases));
			String linea;
			while(sc.hasNext()) {
				linea = sc.nextLine();
				String [] partes = linea.split(",");
				String idClase = partes[0];
				String hora = partes[1];
				String tipoActividad = partes[2];
				String fecha = partes[3];
				String sala = partes[4];
				String plazas = partes[5];
				
				try {
					Clase c = new Clase(Integer.parseInt(idClase), hora, TipoActividad.valueOf(tipoActividad), formatoFecha.parse(fecha), Integer.parseInt(sala), Integer.parseInt(plazas));
					clases.add(c);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.format("\n* Error al abrir el archivo de Clases: %s", e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Override
	public void cargarUsuariosBD() {
		// TODO Auto-generated method stub
		HashSet<Usuario> usuarios = gestorBD.obtenerRegistros("usuario", Usuario.class);
		System.out.println("Usuarios cargados desde la base de datos con éxito.");
		
	}

	@Override
	public void guardarUsuariosBD() {
		// TODO Auto-generated method stub
		for (Usuario usuario : usuarios) {
            gestorBD.añadirUsuario(usuario); // Asumiendo que tienes un método añadirUsuario en GestorBD
        }
        System.out.println("Usuarios guardados en la base de datos con éxito.");
		
	}

	@Override
	public void cargarClasesBD() {
		// TODO Auto-generated method stub
		HashSet<Clase> clases = gestorBD.obtenerRegistros("clase", Clase.class);
		System.out.println("Clases cargadas desde la base de datos con éxito.");
	}

	@Override
	public void guardarClasesBD() {
		// TODO Auto-generated method stub
		try {
	        for (Clase clase : clases) {
	            gestorBD.añadirClase(clase); // Asumiendo que tienes un método añadirClase en GestorBD
	        }
	        System.out.println("Clases guardadas en la base de datos con éxito.");
	    } catch (Exception ex) {
	        System.err.format("Error al guardar clases en la base de datos: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	}

	@Override
	public boolean cargarUsuariosCSV() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cargarClasesCSV() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean borrarUsuario(String dni) {
		// TODO Auto-generated method stub
		return false;
	}


	
}
