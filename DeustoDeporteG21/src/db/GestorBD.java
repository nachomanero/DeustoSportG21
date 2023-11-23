package db;


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
import java.util.logging.Logger;

import domain.Clase;
import domain.TipoActividad;
import domain.Usuario;
import io.FicheroLogger;
import java.util.logging.Level;
public class GestorBD {

	//gestiona las operaciones sobre el modelo fisico
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());
	
	protected static final String DATABASE_FILE = "resources/db/DeustoSport.db";
	protected static  String CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_FILE;
	
	public static Connection connection = null;
	
	public GestorBD() {		
		 
	        try {
	            connection = DriverManager.getConnection(CONNECTION_STRING);
	            LOGGER.log(Level.INFO, "Conexión establecida con éxito a la base de datos.");
	        } catch (SQLException ex) {
	            LOGGER.log(Level.WARNING, "Error al establecer la conexión a la base de datos.");
	            ex.printStackTrace();
	        }
	
	}
		
	public static void crearTablas() {
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
                
                LOGGER.log(Level.INFO, "Creacion de la tabla usuario satisfactoria.");
            } else {
                LOGGER.log(Level.WARNING, "Error en la creacion de la tabla usuario.");
            }

            // Crear tabla Sala
            String createSala = "CREATE TABLE IF NOT EXISTS Sala ("
                    + "IDSala INTEGER PRIMARY KEY, "
                    + "nombre TEXT, "
                    + "capacidad INTEGER)";
            if (!stmt.execute(createSala)) {
                
                LOGGER.log(Level.INFO, "Creacion de la tabla sala satisfactoria.");
            } else {
                LOGGER.log(Level.WARNING, "Error al crear la tabla sala.");
            }

            // Crear tabla de clases
            String createClases = "CREATE TABLE IF NOT EXISTS Clase ("
                    + "idClase INTEGER PRIMARY KEY, "
                    + "hora TEXT NOT NULL, "
                    + "tipoActividad TEXT NOT NULL, "
                    + "fecha DATE NOT NULL, "
                    + "IDSala INTEGER NOT NULL, "
                    + "plazas INTEGER NOT NULL, "
                    + "FOREIGN KEY (IDSala) REFERENCES Sala(IDSala))";

            if (!stmt.execute(createClases)) {
               
                LOGGER.log(Level.INFO, "Creacion de la tabla clase satisfactoria.");
            } else {
                LOGGER.log(Level.WARNING, "Error en la creacion de la tabla clase.");
            }

            // Crear tabla reserva
            String createReserva = "CREATE TABLE IF NOT EXISTS Reserva ("
                    + "IDReserva INTEGER PRIMARY KEY, "
                    + "DNI TEXT NOT NULL, "
                    + "TipoActividad TEXT NOT NULL, "
                    + "IDSala INTEGER NOT NULL, "
                    + "fecha DATE NOT NULL, "
                    + "hora TEXT NOT NULL, "
                    + "FOREIGN KEY (DNI) REFERENCES Usuario(dni), "
                    + "FOREIGN KEY (IDSala) REFERENCES Sala(IDSala))";

            if (!stmt.execute(createReserva)) {
                
                LOGGER.log(Level.INFO, "Creacion de la tabla reserva satisfactoria.");
            } else {
                LOGGER.log(Level.WARNING, "Error en la creacion de la tabla reserva.");
            }

            
            LOGGER.log(Level.INFO, "Base de datos creada con exito.");
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "Error en la creacion de la base de datos.");
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
                    }
                }
            }
        } catch (SQLException | ParseException ex) {
        	LOGGER.log(Level.WARNING,"Error al obtener registros desde la base de datos.");
            ex.printStackTrace();
        } catch (Exception e) {
        	LOGGER.log(Level.WARNING,"Error general.");
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
	        	LOGGER.log(Level.INFO,"Se ha borrado la tabla usuario correctamente.");
	        }else {
	        	LOGGER.log(Level.WARNING,"Error al borrar la tabla usuario.");
	        }

            String dropClase = "DROP TABLE IF EXISTS CLASE";
            if (!stmt.execute(dropClase)) {
	        	System.out.println("\n- Se ha borrado la tabla Clase");
	        	LOGGER.log(Level.INFO,"Se ha borrado la tabla clase correctamente.");
	        }else {
	        	LOGGER.log(Level.WARNING,"Error al borrar la tabla clase.");
	        }

            System.out.println("Base de datos borrada exitosamente.");
            LOGGER.log(Level.INFO,"Base datos borrada correctamente.");
        } catch (Exception ex) {
        	LOGGER.log(Level.WARNING,"Error al borrar la base de datos.");
            ex.printStackTrace();
        }
        
        try {
			//Se borra el fichero de la BBDD
			Files.delete(Paths.get(DATABASE_FILE));
			System.out.println("\n- Se ha borrado el fichero de la BBDD");
			LOGGER.log(Level.INFO,"Se ha borrado el fichero de la BD correctamente.");
		} catch (Exception ex) {
			LOGGER.log(Level.WARNING,"Error al borrar el archivo de la BD.");
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
	                LOGGER.log(Level.INFO,"usuario añadido a la BD correctamente");
	            }
	        } catch (SQLException ex) {
	        	LOGGER.log(Level.WARNING,"Error al añadir usuario a la base de datos.");
	            ex.printStackTrace();
	        } catch (Exception e) {
	        	LOGGER.log(Level.WARNING,"Error general.");
	            e.printStackTrace();
	        }
	    }
		
		
	    
	    public void añadirClase(Clase clase) {
	        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
	           
	            String verificarExistencia = "SELECT COUNT(*) FROM Clase WHERE IDClase = ?";
	            try (PreparedStatement verificarStmt = connection.prepareStatement(verificarExistencia)) {
	                verificarStmt.setInt(1, clase.getIDClase());
	                ResultSet resultSet = verificarStmt.executeQuery();
	                resultSet.next();

	                if (resultSet.getInt(1) == 0) {
	                    
	                    String sql = "INSERT INTO Clase (idClase, hora, tipoActividad, fecha, IDSala, plazas) VALUES (?, ?, ?, ?, ?, ?)";
	                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	                        preparedStatement.setInt(1, clase.getIDClase());
	                        preparedStatement.setString(2, clase.getHora());
	                        preparedStatement.setString(3, clase.getTipoActividad().toString());
	                        preparedStatement.setDate(4, new java.sql.Date(clase.getFecha().getTime()));
	                        preparedStatement.setInt(5, clase.getIDSala());
	                        preparedStatement.setInt(6, clase.getPlazas());
	                        preparedStatement.executeUpdate();
	                        LOGGER.log(Level.INFO, "Clase añadida a la BD correctamente.");
	                    }
	                } else {
	                    LOGGER.log(Level.WARNING, "La clase con ID " + clase.getIDClase() + " ya existe en la base de datos.");
	                }
	            }
	        } catch (SQLException ex) {
	            LOGGER.log(Level.WARNING, "Error al añadir la clase a la BD");
	            ex.printStackTrace();
	        } catch (Exception e) {
	            LOGGER.log(Level.WARNING, "Error general");
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
	                LOGGER.log(Level.INFO,"Reserva añadida a la base de datos con exito.");
	            }
	        } catch (SQLException ex) {
	        	LOGGER.log(Level.WARNING,"Error al añadir reserva a la base de datos.");
	            ex.printStackTrace();
	        } catch (Exception e) {
	        	LOGGER.log(Level.WARNING,"Error general.");
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
	                    LOGGER.log(Level.INFO,"Clase eliminada con exito.");
	                } else {
	                    System.out.println("No se encontró ninguna clase con ID " + IDClase);
	                    LOGGER.log(Level.WARNING,"No se ha eliminado ninguna clase.");
	                }
	            }
	        } catch (SQLException ex) {
	            LOGGER.log(Level.WARNING,"Error al eliminar clase de la BS.");
	            ex.printStackTrace();
	        } catch (Exception e) {
	        	LOGGER.log(Level.WARNING,"Error general.");
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
				LOGGER.log(Level.INFO,"Se han borrado usuarios correctamente.");
				System.out.format("\n- Se han borrado %d usuarios", result);
			} catch (Exception ex) {
				LOGGER.log(Level.WARNING,"Error al borrar de la BD.");
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
	                	LOGGER.log(Level.INFO,"se ha cancelado la reserva con exito.");
	                    System.out.println("Reserva cancelada de la base de datos con éxito.");
	                } else {
	                	LOGGER.log(Level.WARNING,"Error al borrar la reseerva.");
	                    System.out.println("No se encontró ninguna reserva con los datos proporcionados.");
	                }
	            }
	        } catch (SQLException ex) {
	        	LOGGER.log(Level.WARNING,"Error al cancelar la reserva de la BD.");
	            ex.printStackTrace();
	        } catch (Exception e) {
	        	LOGGER.log(Level.WARNING,"Error general.");
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
	                    LOGGER.log(Level.INFO,"Clase modificada con exito.");
	                } else {
	                	LOGGER.log(Level.WARNING,"No se ha encontrado ninguna clase.");
	                    System.out.println("No se encontró ninguna clase con ID " + clase.getIDClase());
	                }
	            }
	        } catch (SQLException ex) {
	        	LOGGER.log(Level.WARNING,"Error al modificar clase en la base de datos.");
	            ex.printStackTrace();
	        } catch (Exception e) {
	        	LOGGER.log(Level.WARNING,"Error general.");
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
	        	LOGGER.log(Level.WARNING,"Error al comprobar el usuario.");
	            ex.printStackTrace();
	        } catch (Exception e) {
	        	LOGGER.log(Level.WARNING,"Error general.");
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
	                    LOGGER.log(Level.INFO,"Se ha encontrado una clase.%S");
	                    
	                } else {
	    	        	LOGGER.log(Level.WARNING,"no se ha encontrado ninguna clase con esa id.%S");
	                    System.out.println("No se encontró ninguna clase con ID " + IDClase);
	                }
	            }
	        } catch (SQLException ex) {
	        	LOGGER.log(Level.WARNING,"Error al visualizar la clase.");
	            ex.printStackTrace();
	        } catch (Exception e) {
	        	LOGGER.log(Level.WARNING,"Error general.");
	            e.printStackTrace();
	        }
	    }
	    
	
}
