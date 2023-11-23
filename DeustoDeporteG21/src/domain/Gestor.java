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
import java.util.logging.Logger;

import db.GestorBD;
import io.FicheroLogger;
import java.util.logging.Logger;
import java.util.logging.Level;
public class Gestor implements itfGestor{
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());

	
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
				LOGGER.log(Level.INFO,"Usuarios cargados con exito.%S");
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.WARNING,"Error al abrir el archivo de Usuarios.%S");
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
				LOGGER.log(Level.INFO,"Clases cargadas correctamente.%S");
				try {
					Clase c = new Clase(Integer.parseInt(idClase), hora, TipoActividad.valueOf(tipoActividad), formatoFecha.parse(fecha), Integer.parseInt(sala), Integer.parseInt(plazas));
					clases.add(c);
				} catch (NumberFormatException e) {
					LOGGER.log(Level.WARNING,"Error.%S");
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch 
					LOGGER.log(Level.WARNING,"Error.%S");
					e.printStackTrace();
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.WARNING,"Error al abrir el archivo de clases.%S");
			e.printStackTrace();
		}
		
	}

	@Override
	public void cargarUsuariosBD() {
		// TODO Auto-generated method stub
		HashSet<Usuario> usuarios = gestorBD.obtenerRegistros("usuario", Usuario.class);
		System.out.println("Usuarios cargados desde la base de datos con éxito.");
		LOGGER.log(Level.INFO,"Usuarios cargados con exito desde la BD.%S");
	}

	@Override
	public void guardarUsuariosBD() {
		// TODO Auto-generated method stub
		for (Usuario usuario : usuarios) {
            gestorBD.añadirUsuario(usuario); 
        }
		LOGGER.log(Level.INFO,"Usuarios guardados en la base de datos con éxito.%S");
        System.out.println("Usuarios guardados en la base de datos con éxito.");
		
	}

	@Override
	public void cargarClasesBD() {
		// TODO Auto-generated method stub
		HashSet<Clase> clases = gestorBD.obtenerRegistros("clase", Clase.class);
		LOGGER.log(Level.INFO,"Clases cargadas desde la base de datos con éxito.");
		System.out.println("Clases cargadas desde la base de datos con éxito.");
	}

	@Override
	public void guardarClasesBD() {
		// TODO Auto-generated method stub
		try {
	        for (Clase clase : clases) {
	            gestorBD.añadirClase(clase); // Asumiendo que tienes un método añadirClase en GestorBD
	        }
	        LOGGER.log(Level.INFO,"Clases guardadas en la base de datos con éxito.%S");
	        System.out.println("Clases guardadas en la base de datos con éxito.");
	    } catch (Exception ex) {
	    	LOGGER.log(Level.WARNING,"Error al guardar clases en la base de datos.%S");
	        System.err.format("Error al guardar clases en la base de datos: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	}
/*
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
*/

	
}
