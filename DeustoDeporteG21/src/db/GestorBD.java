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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import domain.Clase;
import domain.Reserva;
import domain.Sala;
import domain.TipoActividad;
import domain.Usuario;
import io.FicheroLogger;
import java.util.logging.Level;

public class GestorBD {

	private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());

	protected static final String DATABASE_FILE = "resources/db/DeustoSport.db";
	protected static String CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_FILE;

	public static Connection connection = null;

	public GestorBD() {

		try {
			connection = DriverManager.getConnection(CONNECTION_STRING);
			LOGGER.log(Level.INFO, "Conexión establecida con éxito a la base de datos.");
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Error al establecer la conexión a la base de datos.");
			ex.printStackTrace();
		}

		crearTablas();
	}

	public void crearTablas() {
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING); Statement stmt = con.createStatement()) {

			String createUsuarios = "CREATE TABLE IF NOT EXISTS Usuario (" + "dni TEXT PRIMARY KEY, " + "nombre TEXT, "
					+ "apellido TEXT, " + "direccion TEXT, " + "correoElectronico TEXT, " + "contrasena TEXT)";
			if (!stmt.execute(createUsuarios)) {

				LOGGER.log(Level.INFO, "Creacion de la tabla usuario satisfactoria.");
			} else {
				LOGGER.log(Level.SEVERE, "Error en la creacion de la tabla usuario.");
			}

			String createSala = "CREATE TABLE IF NOT EXISTS Sala (" + "IDSala INTEGER PRIMARY KEY, " + "nombre TEXT, "
					+ "capacidad INTEGER)";
			if (!stmt.execute(createSala)) {

				LOGGER.log(Level.INFO, "Creacion de la tabla sala satisfactoria.");
			} else {
				LOGGER.log(Level.SEVERE, "Error al crear la tabla sala.");
			}

			String createClases = "CREATE TABLE IF NOT EXISTS Clase (" + "idClase INTEGER PRIMARY KEY, "
					+ "hora TEXT NOT NULL, " + "tipoActividad TEXT NOT NULL, " + "fecha DATE NOT NULL, "
					+ "IDSala INTEGER NOT NULL, " + "plazas INTEGER NOT NULL, "
					+ "FOREIGN KEY (IDSala) REFERENCES Sala(IDSala))";

			if (!stmt.execute(createClases)) {

				LOGGER.log(Level.INFO, "Creacion de la tabla clase satisfactoria.");
			} else {
				LOGGER.log(Level.SEVERE, "Error en la creacion de la tabla clase.");
			}

			String createReserva = "CREATE TABLE IF NOT EXISTS Reserva (" + "IDReserva INTEGER PRIMARY KEY, "
					+ "DNI TEXT NOT NULL, " + "TipoActividad TEXT NOT NULL, " + "IDSala INTEGER NOT NULL, "
					+ "fecha DATE NOT NULL, " + "hora TEXT NOT NULL, " + "FOREIGN KEY (DNI) REFERENCES Usuario(dni), "
					+ "FOREIGN KEY (IDSala) REFERENCES Sala(IDSala))";

			if (!stmt.execute(createReserva)) {

				LOGGER.log(Level.INFO, "Creacion de la tabla reserva satisfactoria.");
			} else {
				LOGGER.log(Level.SEVERE, "Error en la creacion de la tabla reserva.");
			}

			LOGGER.log(Level.INFO, "Base de datos creada con exito.");
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Error en la creacion de la base de datos.");
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

							Usuario usuario = new Usuario(dni, nombre, apellido, direccion, correoElectronico,
									contrasena);
							registros.add(tipoClase.cast(usuario));
						} else if (tipoClase.equals(Clase.class)) {
							int idClase = resultSet.getInt("idClase");
							String hora = resultSet.getString("hora");
							String tipoActividad = resultSet.getString("tipoActividad");
							String fechaString = resultSet.getString("fecha");
							int sala = resultSet.getInt("IDSala");
							int plazas = resultSet.getInt("plazas");

							SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
							java.util.Date fecha = formatoFecha.parse(fechaString);

							Clase clase = new Clase(idClase, hora, TipoActividad.valueOf(tipoActividad), fecha, sala,
									plazas);
							
							registros.add(tipoClase.cast(clase));
						} else if (tipoClase.equals(Sala.class)) {
							int idSala = resultSet.getInt("IDSala");
							String nombre = resultSet.getString("nombre");
							int capacidad = resultSet.getInt("capacidad");

							Sala sala = new Sala(idSala, nombre, capacidad);
							registros.add(tipoClase.cast(sala));
						} else if (tipoClase.equals(Reserva.class)) {
							String dni = resultSet.getString("DNI");
							String tipoActividad = resultSet.getString("TipoActividad");
							int idSala = resultSet.getInt("IDSala");
							String fechaString = resultSet.getString("fecha");
							String hora = resultSet.getString("hora");

							SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
							java.util.Date fecha = formatoFecha.parse(fechaString);

							Reserva reserva = new Reserva(dni, TipoActividad.valueOf(tipoActividad), idSala, fecha,
									hora);
							registros.add(tipoClase.cast(reserva));
						}
					}
				}
			}
		} catch (SQLException | ParseException ex) {
			LOGGER.log(Level.SEVERE, "Error al obtener registros desde la base de datos.");
			ex.printStackTrace();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error general.");
			e.printStackTrace();
		}

		return registros;
	}

	public List<String> buscarClasesPorFecha(Date fecha) {
		List<String> clasesEncontradas = new ArrayList<>();
		try {
			// Statement stmt = con.createStatement();
			String buscarClases = "SELECT * FROM Clase WHERE fecha = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(buscarClases);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clasesEncontradas;

	}

	public void borrarBD() {
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING); Statement stmt = con.createStatement()) {
			
			String dropUsuario = "DROP TABLE IF EXISTS USUARIO";
			if (!stmt.execute(dropUsuario)) {
				System.out.println("\n- Se ha borrado la tabla Usuario");
				LOGGER.log(Level.INFO, "Se ha borrado la tabla usuario correctamente.");
			} else {
				LOGGER.log(Level.SEVERE, "Error al borrar la tabla usuario.");
			}

			String dropClase = "DROP TABLE IF EXISTS CLASE";
			if (!stmt.execute(dropClase)) {
				System.out.println("\n- Se ha borrado la tabla Clase");
				LOGGER.log(Level.INFO, "Se ha borrado la tabla clase correctamente.");
			} else {
				LOGGER.log(Level.SEVERE, "Error al borrar la tabla clase.");
			}

			System.out.println("Base de datos borrada exitosamente.");
			LOGGER.log(Level.INFO, "Base datos borrada correctamente.");
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Error al borrar la base de datos.");
			ex.printStackTrace();
		}

		try {

			Files.delete(Paths.get(DATABASE_FILE));
			System.out.println("\n- Se ha borrado el fichero de la BBDD");
			LOGGER.log(Level.INFO, "Se ha borrado el fichero de la BD correctamente.");
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Error al borrar el archivo de la BD.");
			ex.printStackTrace();
		}

	}

	public void añadirUsuario(Usuario usuario) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
			String verificarExistencia = "SELECT COUNT(*) FROM Usuario WHERE dni = ?";
			try (PreparedStatement verificarStmt = connection.prepareStatement(verificarExistencia)) {
				verificarStmt.setString(1, usuario.getDni());
				ResultSet resultSet = verificarStmt.executeQuery();
				resultSet.next();

				if (resultSet.getInt(1) == 0) {
					String sql = "INSERT INTO Usuario (dni, nombre, apellido, direccion, correoElectronico, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
					try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
						preparedStatement.setString(1, usuario.getDni());
						preparedStatement.setString(2, usuario.getNombre());
						preparedStatement.setString(3, usuario.getApellido());
						preparedStatement.setString(4, usuario.getDireccion());
						preparedStatement.setString(5, usuario.getCorreoElectronico());
						preparedStatement.setString(6, usuario.getContrasena());

						preparedStatement.executeUpdate();
						LOGGER.log(Level.INFO, "Usuario añadido a la base de datos correctamente.");
					}
				} else {
					LOGGER.log(Level.WARNING,
							"El usuario con DNI " + usuario.getDni() + " ya existe en la base de datos.");
				}
			}
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Error al añadir el usuario a la base de datos", ex);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error general", e);
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
					LOGGER.log(Level.WARNING,
							"La clase con ID " + clase.getIDClase() + " ya existe en la base de datos.");
				}
			}
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Error al añadir la clase a la BD");
			ex.printStackTrace();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error general");
			e.printStackTrace();
		}
	}

	public void añadirReserva(Reserva reserva) {

		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
			String sql = "INSERT INTO Reserva (DNI, TipoActividad, IDSala, fecha, hora) VALUES (?, ?, ?, ?, ?)";

			if (!reservaDuplicada(connection, reserva)) {
				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
					preparedStatement.setString(1, reserva.getDNI());
					preparedStatement.setString(2, reserva.getTipoActividad().name());
					preparedStatement.setInt(3, reserva.getIDSala());
					preparedStatement.setDate(4, new java.sql.Date(reserva.getFecha().getTime()));
					preparedStatement.setString(5, reserva.getHora());
					preparedStatement.executeUpdate();
				}
			}

			LOGGER.log(Level.INFO, "Reservas añadidas a la base de datos con éxito.");
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Error al añadir reservas a la base de datos.");
			ex.printStackTrace();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error general.");
			e.printStackTrace();
		}

	}

	public void añadirReservas(Map<String, List<Reserva>> reservas) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
			String sql = "INSERT INTO Reserva (DNI, TipoActividad, IDSala, fecha, hora) VALUES (?, ?, ?, ?, ?)";

			for (List<Reserva> listaReservas : reservas.values()) {
				for (Reserva reserva : listaReservas) {
					if (!reservaDuplicada(connection, reserva)) {
						try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
							preparedStatement.setString(1, reserva.getDNI());
							preparedStatement.setString(2, reserva.getTipoActividad().name());
							preparedStatement.setInt(3, reserva.getIDSala());
							preparedStatement.setDate(4, new java.sql.Date(reserva.getFecha().getTime()));
							preparedStatement.setString(5, reserva.getHora());
							preparedStatement.executeUpdate();
						}
					}
				}
			}

			LOGGER.log(Level.INFO, "Reservas añadidas a la base de datos con éxito.");
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Error al añadir reservas a la base de datos.");
			ex.printStackTrace();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error general.");
			e.printStackTrace();
		}
	}

	private boolean reservaDuplicada(Connection connection, Reserva reserva) throws SQLException {

		String sql = "SELECT COUNT(*) FROM Reserva WHERE DNI = ? AND TipoActividad = ? AND IDSala = ? AND fecha = ? AND hora = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, reserva.getDNI());
			preparedStatement.setString(2, reserva.getTipoActividad().name());
			preparedStatement.setInt(3, reserva.getIDSala());
			preparedStatement.setDate(4, new java.sql.Date(reserva.getFecha().getTime()));
			preparedStatement.setString(5, reserva.getHora());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					return count > 0;
				}
			}
		}
		return false;
	}

	public List<Reserva> obtenerReservasPorDni(String dni) {
		List<Reserva> reservas = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
			String sql = "SELECT * FROM Reserva WHERE DNI = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, dni);
				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					String tipoActividad = resultSet.getString("TipoActividad");
					int idSala = resultSet.getInt("IDSala");
					java.sql.Date fechaSql = resultSet.getDate("fecha");
					String hora = resultSet.getString("hora");

					SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
					Reserva reserva = new Reserva(dni, TipoActividad.valueOf(tipoActividad), idSala,
							formatoFecha.parse(formatoFecha.format(fechaSql)), hora);
					reservas.add(reserva);
				}
			}
		} catch (SQLException | ParseException ex) {
			ex.printStackTrace();
		}

		return reservas;
	}

	public List<Clase> obtenerClasesPorFecha(String fecha) throws ParseException {
		List<Clase> clases = new ArrayList<>();

		try (Connection conexion = DriverManager.getConnection(CONNECTION_STRING)) {
			String consulta = "SELECT idClase, hora, tipoActividad, fecha, IDSala, plazas FROM Clase WHERE fecha like ?";
			try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
				SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");

				java.sql.Date fechaSql = new java.sql.Date(formatoSalida.parse(fecha).getTime());

				System.out.println(fechaSql);

				statement.setDate(1, fechaSql);

				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						int idClase = resultSet.getInt("idClase");
						String hora = resultSet.getString("hora");
						String tipoActividad = resultSet.getString("tipoActividad");
						java.sql.Date fechaSqlResultado = resultSet.getDate("fecha");
						int idSala = resultSet.getInt("IDSala");
						int plazas = resultSet.getInt("plazas");

						//String fechaResultado = formatoSalida.format(fechaSqlResultado);
						//java.sql.Date fechaFormateada = formatoEntrada.parse(fechaResultado);

						Clase clase = new Clase(idClase, hora, TipoActividad.valueOf(tipoActividad), fechaSqlResultado,
								idSala, plazas);
						clases.add(clase);
					}
				}
			}
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}

		return clases;

	}

	public void eliminarClase(int IDClase) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
			String sql = "DELETE FROM clase WHERE IDClase = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(1, IDClase);
				int rowCount = preparedStatement.executeUpdate();

				if (rowCount > 0) {
					System.out.println("Clase eliminada de la base de datos con éxito.");
					LOGGER.log(Level.INFO, "Clase eliminada con exito.");
				} else {
					System.out.println("No se encontró ninguna clase con ID " + IDClase);
					LOGGER.log(Level.SEVERE, "No se ha eliminado ninguna clase.");
				}
			}
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Error al eliminar clase de la BS.");
			ex.printStackTrace();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error general.");
			e.printStackTrace();
		}
	}

	public void eliminarUsuario(String dni) {

		try (Connection con = DriverManager.getConnection(CONNECTION_STRING); Statement stmt = con.createStatement()) {

			String sql = "DELETE FROM USUARIO WHERE DNI = ;" + dni;
			int result = stmt.executeUpdate(sql);
			LOGGER.log(Level.INFO, "Se han borrado usuarios correctamente.");
			System.out.format("\n- Se han borrado %d usuarios", result);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Error al borrar de la BD.");
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
					LOGGER.log(Level.INFO, "se ha cancelado la reserva con exito.");
					System.out.println("Reserva cancelada de la base de datos con éxito.");
				} else {
					LOGGER.log(Level.SEVERE, "Error al borrar la reseerva.");
					System.out.println("No se encontró ninguna reserva con los datos proporcionados.");
				}
			}
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Error al cancelar la reserva de la BD.");
			ex.printStackTrace();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error general.");
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

				return resultSet.next();
			}
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Error al comprobar el usuario.");
			ex.printStackTrace();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error general.");
			e.printStackTrace();
		}

		return false;
	}

	public boolean editarClase(Clase clase) {
		String sql = "UPDATE Clase SET hora = ?, tipoActividad = ?, fecha = ?,  idSala = ?, plazas = ? WHERE idClase = ?";

		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			java.sql.Date fechaSql = new java.sql.Date(clase.getFecha().getTime());
			preparedStatement.setString(1, clase.getHora());
			preparedStatement.setString(2, clase.getTipoActividad().toString());
			preparedStatement.setDate(3, (Date) fechaSql);
			preparedStatement.setInt(4, clase.getIDSala());
			preparedStatement.setInt(5, clase.getPlazas());
			preparedStatement.setInt(6, clase.getIDClase());

			int filasAfectadas = preparedStatement.executeUpdate();

			if (filasAfectadas > 0) {
				System.out.println("Se ha actualizado una linea");
			} else {
				System.out.println("No se ha actualizado ninguna linea");
			}
			return filasAfectadas > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Clase> obtenerTodasLasClases() {
		List<Clase> clases = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
			String sql = "SELECT * FROM Clase";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						int idClase = resultSet.getInt("idClase");
						String hora = resultSet.getString("hora");
						String tipoActividad = resultSet.getString("tipoActividad");
						java.sql.Date timestamp = resultSet.getDate("fecha");
						int IDSala = resultSet.getInt("IDSala");
						int plazas = resultSet.getInt("plazas");

						java.util.Date fecha = new java.util.Date(timestamp.getTime());

						//SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MM-yyyy");
						//String fechaFormateadaStr = sdfOutput.format(fecha);
						//java.util.Date fechaFormateada = sdfOutput.parse(fechaFormateadaStr);

						Clase clase = new Clase(idClase, hora, TipoActividad.valueOf(tipoActividad), timestamp,
								IDSala, plazas);
						clases.add(clase);
					}
				}
			}
		} catch (SQLException  ex) {
			LOGGER.log(Level.SEVERE, "Error al obtener todas las clases desde la base de datos.");
			ex.printStackTrace();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error general.");
			e.printStackTrace();
		}

		return clases;
	}

	private java.util.Date convertirFecha(String fechaStr) {
		try {
			SimpleDateFormat sdfInput = new SimpleDateFormat("dd-MM-yyyy");
			return sdfInput.parse(fechaStr);
		} catch (ParseException e) {
			LOGGER.log(Level.SEVERE, "Error al convertir fecha: " + fechaStr, e);
			return null; 
		}
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
					LOGGER.log(Level.INFO, "Se ha encontrado una clase.%S");

				} else {
					LOGGER.log(Level.SEVERE, "no se ha encontrado ninguna clase con esa id.%S");
					System.out.println("No se encontró ninguna clase con ID " + IDClase);
				}
			}
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Error al visualizar la clase.");
			ex.printStackTrace();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error general.");
			e.printStackTrace();
		}
	}

	public String obtenerDniPorCredenciales(String correoElectronico, String contrasena) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
			String sql = "SELECT dni FROM Usuario WHERE correoElectronico = ? AND contrasena = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, correoElectronico);
				preparedStatement.setString(2, contrasena);
				ResultSet resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					return resultSet.getString("dni");
				}
			}
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Error al obtener el DNI por credenciales.");
			ex.printStackTrace();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error general.");
			e.printStackTrace();
		}

		return null;
	}

	public void actualizarPlazas(int id, int nuevasPlazas) {
		try (Connection conexion = DriverManager.getConnection(CONNECTION_STRING)) {
			String consulta = "UPDATE Clase SET plazas = ? WHERE idClase = ?";
			try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
				statement.setInt(1, nuevasPlazas);
				statement.setInt(2, id);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Error updating available seats for class with ID " + id, e);
		}
	}
	
	public void actualizarReserva(Reserva reserva) {
	    try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
	        String sql = "UPDATE Reserva SET TipoActividad = ?, IDSala = ?, fecha = ?, hora = ? WHERE DNI = ? AND fecha = ? AND hora = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setString(1, reserva.getTipoActividad().name());
	            preparedStatement.setInt(2, reserva.getIDSala());
	            preparedStatement.setDate(3, new java.sql.Date(reserva.getFecha().getTime()));
	            preparedStatement.setString(4, reserva.getHora());
	            preparedStatement.setString(5, reserva.getDNI());
	            preparedStatement.setDate(6, new java.sql.Date(reserva.getFecha().getTime())); // fecha original
	            preparedStatement.setString(7, reserva.getHora()); // hora original

	            int rowCount = preparedStatement.executeUpdate();

	            if (rowCount > 0) {
	                LOGGER.log(Level.INFO, "Reserva actualizada con éxito.");
	                System.out.println("Reserva actualizada en la base de datos con éxito.");
	            } else {
	                LOGGER.log(Level.WARNING, "No se encontró ninguna reserva para actualizar.");
	                System.out.println("No se encontró ninguna reserva para actualizar.");
	            }
	        }
	    } catch (SQLException ex) {
	        LOGGER.log(Level.SEVERE, "Error al actualizar la reserva en la base de datos.");
	        ex.printStackTrace();
	    } catch (Exception e) {
	        LOGGER.log(Level.SEVERE, "Error general.");
	        e.printStackTrace();
	    }
	}
	
	public int obtenerSiguienteID(String nombreTabla, String nombreColumnaID) {
	    int siguienteID = 1;  

	    try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
	        String sql = "SELECT MAX(" + nombreColumnaID + ") FROM " + nombreTabla;
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    int maxID = resultSet.getInt(1);
	                    siguienteID = maxID + 1;
	                }
	            }
	        }
	    } catch (SQLException ex) {
	        LOGGER.log(Level.SEVERE, "Error al obtener el siguiente ID desde la base de datos.", ex);
	        ex.printStackTrace();
	    } catch (Exception e) {
	        LOGGER.log(Level.SEVERE, "Error general.", e);
	        e.printStackTrace();
	    }

	    return siguienteID;
	}

	public Map<String, List<Reserva>> obtenerTodasLasReservas() {
        Map<String, List<Reserva>> reservasPorUsuario = new HashMap<>();

        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
            String sql = "SELECT * FROM Reserva";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String dni = resultSet.getString("DNI");

                    String tipoActividad = resultSet.getString("TipoActividad");
                    int idSala = resultSet.getInt("IDSala");
                    java.sql.Date fechaSql = resultSet.getDate("fecha");
                    String hora = resultSet.getString("hora");

                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
                    Reserva reserva = new Reserva(dni, TipoActividad.valueOf(tipoActividad), idSala,
                            formatoFecha.parse(formatoFecha.format(fechaSql)), hora);

                    reservasPorUsuario.computeIfAbsent(dni, k -> new ArrayList<>()).add(reserva);
                }
            }
        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
        }

        return reservasPorUsuario;
    }
	
	

}
