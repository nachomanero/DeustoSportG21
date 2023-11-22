package db;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Usuario;

public class GestorBD {

	//gestiona las operaciones sobre el modelo fisico
	
	protected static final String DRIVER_NAME = "org.sqlite.JDBC";
	protected static final String DATABASE_FILE = "db/database.db";
	protected static final String CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_FILE;
	
	public GestorBD() {		
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException ex) {
			System.err.format("\n* Error al cargar el driver de BBDD: %s", ex.getMessage());
			ex.printStackTrace();
		}
	}
	

	public void crearBBDD() {
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	   	     Statement stmt = con.createStatement()) {
	         // Crear tabla de usuarios
	        String createUsuarios = "CREATE TABLE IF NOT EXISTS Usuario ("
	                              + " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
	                              + "dni TEXT PRIMARY KEY, "
	                              + "nombre TEXT, "
	                              + "apellido TEXT, "
	                              + "direccion TEXT, "
	                              + "correoElectronico TEXT, "
	                              + "contrasena TEXT)";
	        if (!stmt.execute(createUsuarios)) {
		        System.out.println("\n- Se ha creado la tabla Usuario");
		    }
	            
	        // Crear tabla actividad
	            
	        String createActividad = "CREATE TABLE IF NOT EXISTS Actividad ("
	            		           + "IDActividad INTEGER PRIMARY KEY, "
	            	           	   + "TipoActividad TIPOACTIVIDAD, "
	            		           + "plazas INTEGER)";
	        if (!stmt.execute(createActividad)) {
		        System.out.println("\n- Se ha creado la tabla Actividad");
		    }
	            
	        //Crear tabla Sala
	        String createSala = "CREATE TABLE IF NOT EXISTS Sala ("
	            		      + "IDSala INTEGER PRIMARY KEY, "
	            	       	  + "nombre TEXT, "
	            	    	  + "capacidad INTEGER)";
	        if (!stmt.execute(createSala)) {
		        System.out.println("\n- Se ha creado la tabla Sala");
		    }
	            

	        // Crear tabla de clases
	        String createClases = "CREATE TABLE IF NOT EXISTS clase ("
	                            + "idClase INTEGER PRIMARY KEY, "
	                            + "hora TEXT, "
	                            + "actividad ACTIVIDAD, "
	                            + "fecha FECHA, "
	                            + "sala SALA)";
	            
	        if (!stmt.execute(createClases)) {
		       	System.out.println("\n- Se ha creado la tabla Clase");
		    }

	        System.out.println("Base de datos creada con exito.");
	    } catch (Exception ex) {
	        System.err.format("\n* Error al crear la BBDD: %s", ex.getMessage());
			ex.printStackTrace();
	    }
	}
	
	public void borrarBBDD() {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
        		Statement stmt = con.createStatement()) {
            // Borrar tablas
            String dropUsuario = "DROP TABLE IF EXISTS USUARIO";
            if (!stmt.execute(dropUsuario)) {
	        	System.out.println("\n- Se ha borrado la tabla Usuario");
	        }

            String dropClase = "DROP TABLE IF EXISTS CLASE";
            if (!stmt.execute(dropClase)) {
	        	System.out.println("\n- Se ha borrado la tabla Clase");
	        }

            System.out.println("Base de datos borrada exitosamente.");
        } catch (Exception ex) {
        	System.err.format("\n* Error al borrar la BBDD: %s", ex.getMessage());
            ex.printStackTrace();
        }
        
        try {
			//Se borra el fichero de la BBDD
			Files.delete(Paths.get(DATABASE_FILE));
			System.out.println("\n- Se ha borrado el fichero de la BBDD");
		} catch (Exception ex) {
			System.err.format("\n* Error al borrar el archivo de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}
        
    }
	    
	    public void insertarUsuario(Usuario... usuarios) {
			//Se abre la conexión y se obtiene el Statement
			try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
			     Statement stmt = con.createStatement()) {
				//Se define la plantilla de la sentencia SQL
				String sql = "INSERT INTO USUARIO (DNI, NOMBRE, APELLIDO, DIRECCION, CORREOELECTRONICO, CONTRASENA) VALUES ('%s', '%s', '%s', '%s', '%s', '%s');";
				
				System.out.println("- Insertando usuario...");
				
				//Se recorren los clientes y se insertan uno a uno
				for (Usuario u : usuarios) {
					if (1 == stmt.executeUpdate(String.format(sql, u.getDni(), u.getNombre(), u.getApellido(), u.getDireccion(), u.getCorreoElectronico(), u.getContrasena()))) {					
						System.out.format("\n - Usuario insertado: %s", u.toString());
					} else {
						System.out.format("\n - No se ha insertado el usuario: %s", u.toString());
					}
				}			
			} catch (Exception ex) {
				System.err.format("\n* Error al insertar datos de la BBDD: %s", ex.getMessage());
				ex.printStackTrace();						
			}				
		}
	    
	    
	    public List<Usuario> obtenerDatos() {
			List<Usuario> usuarios = new ArrayList<>();
			
			//Se abre la conexión y se obtiene el Statement
			try (Connection con = DriverManager.getConnection("usuarios.csv");
			     Statement stmt = con.createStatement()) {
				String sql = "SELECT * FROM USUARIO WHERE ID >= 0";
				
				//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
				ResultSet rs = stmt.executeQuery(sql);			
				Usuario usuario;
				
				//Se recorre el ResultSet y se crean objetos Cliente
				while (rs.next()) {
					usuario = new Usuario(rs.getString("DNI"), rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getString("DIRECCION"), rs.getString("CORREOELECTRONICO"), rs.getString("CONTRASENA"));
					usuario.setId(rs.getInt("ID"));
					
					//Se inserta cada nuevo cliente en la lista de clientes
					usuarios.add(usuario);
				}
				
				//Se cierra el ResultSet
				rs.close();
				
				System.out.format("\n- Se han recuperado %d usuarios...", usuarios.size());			
			} catch (Exception ex) {
				System.err.format("\n* Error al obtener datos de la BBDD: %s", ex.getMessage());
				ex.printStackTrace();						
			}		
			
			return usuarios;
		}
	    
	    public void borrarUsuario(String dni ) {
			//Se abre la conexión y se obtiene el Statement
			try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
			     Statement stmt = con.createStatement()) {
				//Se ejecuta la sentencia de borrado de datos
				String sql = "DELETE FROM USUARIO WHERE DNI = ;" + dni ;			
				int result = stmt.executeUpdate(sql);
				
				System.out.format("\n- Se han borrado %d usuarios", result);
			} catch (Exception ex) {
				System.err.format("\n* Error al borrar datos de la BBDD: %s", ex.getMessage());
				ex.printStackTrace();						
			}		
		}
	    	
	
	
	
	
	
}
