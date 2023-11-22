package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;

import domain.Clase;
import domain.TipoActividad;
import domain.Usuario;

public class GestorBD {

	//gestiona las operaciones sobre el modelo fisico
	
	protected static final String DRIVER_NAME = "org.sqlite.JDBC";
	protected static final String DATABASE_FILE = "db/DeustoMart.db";
	protected static final String CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_FILE;
	
	public GestorBD() {		
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException ex) {
			System.err.format("\n* Error al cargar el driver de BBDD: %s", ex.getMessage());
			ex.printStackTrace();
		}
	}
	

	public void crearTablas() {
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	   	     Statement stmt = con.createStatement()) {
	         // Crear tabla de usuarios
	        String createUsuarios = "CREATE TABLE IF NOT EXISTS Usuario ("
	                              + "dni TEXT PRIMARY KEY, "
	                              + "nombre TEXT, "
	                              + "apellido TEXT, "
	                              + "direccion TEXT, "
	                              + "correoElectronico TEXT, "
	                              + "contrasena TEXT)";
	        if (!stmt.execute(createUsuarios)) {
		        System.out.println("\n- Se ha creado la tabla Usuario");
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
	        String createClases = "CREATE TABLE IF NOT EXISTS Clase ("
	                            + "idClase INTEGER PRIMARY KEY, "
	                            + "hora TEXT NOT NULL, "
	                            + "tipoActividad TEXT NOT NULL, "
	                            + "fecha DATE NOT NULL, "
	                            + "IDSala INTEGER NOT NULL, "
	                            + "plazas INTEGER NOT NULL"
	                            + "FOREIGN KEY (IDSala) REFERENCES Sala(IDSala))";
	        
	        if (!stmt.execute(createClases)) {
		       	System.out.println("\n- Se ha creado la tabla Clase");
		    }   

	        
	        //Crear tabla reserva
	        String createReserva = "CREATE TABLE IF NOT EXISTS Reserva ("
	        		+ "IDReserva INTEGER PRIMARY KEY AUTOINCREMENT,"
	        		+ "DNI TEXT NOT NULL"
	        		+ "TipoActividad TEXT NOT NULL, "
	        		+ "IDSala INTEGER NOT NULL, "
	        		+ "fecha DATE NOT NULL, "
	        		+ "hora TEXT NOT NULL, "
	        		+ "FOREIGN KEY (DNI) REFERENCES Usuario(dni), "
	        		+ "FOREIGN KEY IDSala REFERENCES Sala(IDSala)";
	        
	        if (!stmt.execute(createReserva)) {
		        System.out.println("\n- Se ha creado la tabla Actividad");
		    }

	        System.out.println("Base de datos creada con exito.");
	    } catch (Exception ex) {
	        System.err.format("\n* Error al crear la BBDD: %s", ex.getMessage());
			ex.printStackTrace();
	    }
	}

	
	
	public <T> HashSet<T> obtenerRegistros(String tabla, Class<T> tipoClase) {
        HashSet<T> registros = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
            String sql = "SELECT * FROM " + tabla;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        if (tipoClase.equals(Usuario.class)) {
                            String dni = resultSet.getString("dni");
                            String nombre = resultSet.getString("nombre");
                            String apellido = resultSet.getString("apellido");
                            String direccion = resultSet.getString("direccion");
                            String correoElectronico = resultSet.getString("correoElectronico");
                            String contrasena = resultSet.getString("contrasena");

                            // Crear objeto Usuario y agregarlo al conjunto
                            Usuario usuario = new Usuario(dni, nombre, apellido, direccion, correoElectronico, contrasena);
                            registros.add(tipoClase.cast(usuario));
                        } else if (tipoClase.equals(Clase.class)) {
                            int idClase = resultSet.getInt("idClase");
                            String hora = resultSet.getString("hora");
                            String tipoActividad = resultSet.getString("actividad");
                            String fechaString = resultSet.getString("fecha");
                            int sala = resultSet.getInt("sala");
                            int plazas = resultSet.getInt("plazas");

                            // Convertir la cadena de fecha a objeto Date
                            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date fecha = formatoFecha.parse(fechaString);

                            // Crear objeto Clase y agregarlo al conjunto
                            Clase clase = new Clase(idClase, hora, TipoActividad.valueOf(tipoActividad), fecha, sala, plazas);
                            registros.add(tipoClase.cast(clase));
                        }
                        // Puedes agregar más condiciones según el tipo de clase que necesites manejar
                    }
                }
            }
        } catch (SQLException | ParseException ex) {
            System.err.format("Error al obtener registros desde la base de datos: %s%n", ex.getMessage());
            ex.printStackTrace();
        } catch (Exception e) {
            System.err.format("Error general: %s%n", e.getMessage());
            e.printStackTrace();
        }

        return registros;
    }
	
	
	
	public void borrarBD() {
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
	    
	    public void añadirUsuario(Usuario usuario) {
	    	try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
	            String sql = "INSERT INTO usuario (dni, nombre, apellido, direccion, correoElectronico, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	                preparedStatement.setString(1, usuario.getDni());
	                preparedStatement.setString(2, usuario.getNombre());
	                preparedStatement.setString(3, usuario.getApellido());
	                preparedStatement.setString(4, usuario.getDireccion());
	                preparedStatement.setString(5, usuario.getCorreoElectronico());
	                preparedStatement.setString(6, usuario.getContrasena());

	                preparedStatement.executeUpdate();
	            }
	        } catch (SQLException ex) {
	            System.err.format("Error al añadir usuario a la base de datos: %s%n", ex.getMessage());
	            ex.printStackTrace();
	        } catch (Exception e) {
	            System.err.format("Error general: %s%n", e.getMessage());
	            e.printStackTrace();
	        }
	    }
		
		
	    
	    public void añadirClase(Clase clase) {
	    	try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
	            String sql = "INSERT INTO clase (IDClase, hora, actividad, fecha, sala, plazas) VALUES (?, ?, ?, ?, ?,?)";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            	preparedStatement.setInt(1, clase.getIDClase());
	                preparedStatement.setString(2, clase.getHora());
	                preparedStatement.setString(3, clase.getTipoActividad().toString());
	                preparedStatement.setDate(4, new java.sql.Date(clase.getFecha().getTime()));
	                preparedStatement.setInt(5, clase.getIDSala());
	                preparedStatement.setInt(6, clase.getPlazas());

	                preparedStatement.executeUpdate();
	            }
	        } catch (SQLException ex) {
	            System.err.format("Error al añadir clase a la base de datos: %s%n", ex.getMessage());
	            ex.printStackTrace();
	        } catch (Exception e) {
	            System.err.format("Error general: %s%n", e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    
	    
	    
	    public void añadirReserva(String DNI, TipoActividad TipoActividad, int IDSala, Date fecha, String hora) {
	        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
	            String sql = "INSERT INTO Reserva (DNI, TipoActividad, IDSala, fecha, hora) VALUES (?, ?, ?, ?, ?)";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	                preparedStatement.setString(1, DNI);
	                preparedStatement.setString(2, TipoActividad.name()); // Suponiendo que TipoActividad es un enum
	                preparedStatement.setInt(3, IDSala);
	                preparedStatement.setDate(4, fecha);
	                preparedStatement.setString(5, hora);
	                preparedStatement.executeUpdate();
	            }

	            System.out.println("Reserva añadida a la base de datos con éxito.");
	        } catch (SQLException ex) {
	            System.err.format("Error al añadir reserva a la base de datos: %s%n", ex.getMessage());
	            ex.printStackTrace();
	        } catch (Exception e) {
	            System.err.format("Error general: %s%n", e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    
	    
	    public void eliminarClase(int IDClase) {
	        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
	            String sql = "DELETE FROM clase WHERE IDClase = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	                preparedStatement.setInt(1, IDClase);
	                int rowCount = preparedStatement.executeUpdate();

	                if (rowCount > 0) {
	                    System.out.println("Clase eliminada de la base de datos con éxito.");
	                } else {
	                    System.out.println("No se encontró ninguna clase con ID " + IDClase);
	                }
	            }
	        } catch (SQLException ex) {
	            System.err.format("Error al eliminar clase de la base de datos: %s%n", ex.getMessage());
	            ex.printStackTrace();
	        } catch (Exception e) {
	            System.err.format("Error general: %s%n", e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    
	    
	    public void eliminarUsuario(String dni ) {
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
	    
	    
	    public void cancelarReserva(String DNI, TipoActividad TipoActividad, int IDSala, Date fecha, String hora) {
	        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
	            String sql = "DELETE FROM Reserva WHERE DNI = ? AND TipoActividad = ? AND IDSala = ? AND fecha = ? AND hora = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	                preparedStatement.setString(1, DNI);
	                preparedStatement.setString(2, TipoActividad.name());
	                preparedStatement.setInt(3, IDSala);
	                preparedStatement.setDate(4, fecha);
	                preparedStatement.setString(5, hora);
	                int rowCount = preparedStatement.executeUpdate();

	                if (rowCount > 0) {
	                    System.out.println("Reserva cancelada de la base de datos con éxito.");
	                } else {
	                    System.out.println("No se encontró ninguna reserva con los datos proporcionados.");
	                }
	            }
	        } catch (SQLException ex) {
	            System.err.format("Error al cancelar reserva de la base de datos: %s%n", ex.getMessage());
	            ex.printStackTrace();
	        } catch (Exception e) {
	            System.err.format("Error general: %s%n", e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    
	    
	    public void modificarClase(Clase clase) {
	        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
	            String sql = "UPDATE clase SET IDClase = ?, hora = ?, tipoActividad = ?, fecha = ?, IDSala = ?, plazas = ? WHERE IDClase = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	                preparedStatement.setInt(1, clase.getIDClase());
	                preparedStatement.setString(2, clase.getHora());
	                preparedStatement.setString(3, clase.getTipoActividad().name());
	                preparedStatement.setDate(4, (Date) clase.getFecha());
	                preparedStatement.setInt(5, clase.getIDSala());
	                preparedStatement.setInt(6, clase.getPlazas());
	                int rowCount = preparedStatement.executeUpdate();

	                if (rowCount > 0) {
	                    System.out.println("Clase modificada en la base de datos con éxito.");
	                } else {
	                    System.out.println("No se encontró ninguna clase con ID " + clase.getIDClase());
	                }
	            }
	        } catch (SQLException ex) {
	            System.err.format("Error al modificar clase en la base de datos: %s%n", ex.getMessage());
	            ex.printStackTrace();
	        } catch (Exception e) {
	            System.err.format("Error general: %s%n", e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    
	    public boolean comprobarUsuario(String correoElectronico, String contrasena) {
	        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
	            String sql = "SELECT * FROM Usuario WHERE correoElectronico = ? AND contrasena = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	                preparedStatement.setString(1, correoElectronico);
	                preparedStatement.setString(2, contrasena);
	                ResultSet resultSet = preparedStatement.executeQuery();

	                // Si hay al menos una fila en el resultado, el usuario existe
	                return resultSet.next();
	            }
	        } catch (SQLException ex) {
	            System.err.format("Error al comprobar usuario: %s%n", ex.getMessage());
	            ex.printStackTrace();
	        } catch (Exception e) {
	            System.err.format("Error general: %s%n", e.getMessage());
	            e.printStackTrace();
	        }

	        return false; // En caso de error o si no se encuentra el usuario
	    }
	    
	    
	    public void visualizarClase(int IDClase) {
	        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
	            String sql = "SELECT * FROM clase WHERE IDClase = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	                preparedStatement.setInt(1, IDClase);
	                ResultSet resultSet = preparedStatement.executeQuery();

	                if (resultSet.next()) {
	                    int idClase = resultSet.getInt("IDClase");
	                    String hora = resultSet.getString("hora");
	                    TipoActividad tipoActividad = TipoActividad.valueOf(resultSet.getString("tipoActividad"));
	                    Date fecha = resultSet.getDate("fecha");
	                    int IDSala = resultSet.getInt("IDSala");

	                    System.out.println("Detalles de la clase:");
	                    System.out.println("ID de Clase: " + idClase);
	                    System.out.println("Hora: " + hora);
	                    System.out.println("Tipo de Actividad: " + tipoActividad);
	                    System.out.println("Fecha: " + fecha);
	                    System.out.println("ID de Sala: " + IDSala);
	                } else {
	                    System.out.println("No se encontró ninguna clase con ID " + IDClase);
	                }
	            }
	        } catch (SQLException ex) {
	            System.err.format("Error al visualizar clase: %s%n", ex.getMessage());
	            ex.printStackTrace();
	        } catch (Exception e) {
	            System.err.format("Error general: %s%n", e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    
	
}
