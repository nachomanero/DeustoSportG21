package domain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import db.GestorBD;
import io.FicheroLogger;
import java.util.logging.Level;

public class Gestor implements itfGestor {
	private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());

	

	private HashSet<Usuario> usuarios;
	private HashSet<Clase> clases;
	private Map<String, List<Reserva>> reservas;
	private GestorBD gestorBD;
	// ...

	public Gestor() {
		usuarios = new HashSet<Usuario>();
		clases = new HashSet<Clase>();
		gestorBD = new GestorBD();
		reservas = new HashMap<>();

		// ...

	}
	
	
	
	
	
	public List<TipoActividad> las3MasSolicitadas(Map<TipoActividad, Integer> tabla) {
	    List<Estadistica> listaEstadisticas = new ArrayList<>();

	    for (TipoActividad actividad : tabla.keySet()) {
	        listaEstadisticas.add(new Estadistica(actividad, tabla.get(actividad)));
	    }

	    listaEstadisticas.sort((o1, o2) -> o2.getVeces() - o1.getVeces());

	    List<TipoActividad> actividadesMasSolicitadas = new ArrayList<>();

	    int i = 0;
	    int cont = 0;
	    while (i < listaEstadisticas.size() && cont < 3) {
	        actividadesMasSolicitadas.add(listaEstadisticas.get(i).getActividad());
	        i++;
	        cont++;
	    }

	    return actividadesMasSolicitadas;
	}

	


	
	public Map<TipoActividad, Integer> cargarActividadesMasElegidas() {
	    Map<TipoActividad, Integer> actividadesMasElegidas = new HashMap<>();

	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("masSolicitadas.dat"))) {
	        actividadesMasElegidas = (Map<TipoActividad, Integer>) ois.readObject();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return actividadesMasElegidas;
	}



	
	public void guardarActividadesMasElegidas() {
	    Map<String, List<Reserva>> reservas = gestorBD.obtenerTodasLasReservas();

	    Map<TipoActividad, Integer> cuentaActividades = new HashMap<>();

	    for (List<Reserva> misReservas : reservas.values()) {
	        for (Reserva r : misReservas) {
	            TipoActividad actividad = r.getTipoActividad();

	            if (cuentaActividades.containsKey(actividad)) {
	                cuentaActividades.put(actividad, cuentaActividades.get(actividad) + 1);
	            } else {
	                cuentaActividades.put(actividad, 1);
	            }
	        }
	    }

	    String filePath = "masSolicitadas.dat";

	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
	        oos.writeObject(cuentaActividades);
	        System.out.println("Fichero creado correctamente: " + filePath);
	    } catch (IOException e) {
	        System.err.println("Error escribiendo el fichero: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


	public void realizarOperacionEnBD() {

	}

	@Override
	public void cargarUsuariosCSV(String nomfichUsuarios) {
		// TODO Auto-generated method stub
		try {
			Scanner sc = new Scanner(new FileReader(nomfichUsuarios));
			String linea;
			while (sc.hasNext()) {
				linea = sc.nextLine();
				String[] partes = linea.split(",");
				String dni = partes[0];
				String nombre = partes[1];
				String apellido = partes[2];
				String direccion = partes[3];
				String correo = partes[4];
				String contraseña = partes[5];

				Usuario u = new Usuario(dni, nombre, apellido, direccion, correo, contraseña);
				if (!usuarios.contains(u)) {
					usuarios.add(u);
					gestorBD.añadirUsuario(u);
					LOGGER.log(Level.INFO, "Usuario agregado a la base de datos: " + u);
				} else {
					LOGGER.log(Level.WARNING, "Intento de agregar un usuario duplicado: " + u);
				}

			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error al abrir el archivo de Usuarios.", e);
		}
		// System.out.println(usuarios);

	}

	@Override
	public void guardarUsuariosCSV() {
		// TODO Auto-generated method stub
		try (PrintWriter writer = new PrintWriter(new FileWriter("Usuarios.csv"))) {
			for (Usuario usuario : usuarios) {
				String linea = String.format("%s,%s,%s,%s,%s,%s", usuario.getDni(), usuario.getNombre(),
						usuario.getApellido(), usuario.getDireccion(), usuario.getCorreoElectronico(),
						usuario.getContrasena());
				writer.println(linea);
			}
			LOGGER.log(Level.INFO, "Usuarios guardados en el archivo CSV con éxito.");
			// System.out.println("Usuarios guardados en el archivo CSV con éxito.");
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "Error al guardar usuarios en el archivo CSV.");
			ex.printStackTrace();
		}
	}

	// Este metodo lo he hecho para poder poner ID automático al crear las clases
	public int obtenerUltimoIDClase(String nomFichClases) {
		int contador = 0;
		try {
			Scanner sc = new Scanner(new FileReader(nomFichClases));
			String linea;

			while (sc.hasNext()) {
				linea = sc.nextLine();
				contador++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return contador;

	}

	public void agregarClaseACSV(Clase clase, String rutaArchivoCSV) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivoCSV, true))) {
			// Crear una cadena con los datos de la clase
			String datosClase = String.format("%d,%s,%s,%s,%d,%d%n", clase.getIDClase(), clase.getHora(),
					clase.getTipoActividad().toString(), new SimpleDateFormat("dd-MM-yyyy").format(clase.getFecha()),
					clase.getIDSala(), clase.getPlazas());

			writer.write(datosClase);
			LOGGER.log(Level.INFO, "Clase añadida en el archivo CSV con éxito.");
			// System.out.println("Clase añadida al archivo CSV correctamente.");

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error al guardar la clase en el archivo CSV");
			e.printStackTrace();
		}
	}

	@Override
	public void cargarClasesCSV(String nomfichClases) {
		// TODO Auto-generated method stub
		try {

			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			Scanner sc = new Scanner(new FileReader(nomfichClases));
			String linea;
			while (sc.hasNext()) {
				linea = sc.nextLine();
				String[] partes = linea.split(",");
				String idClase = partes[0];
				String hora = partes[1];
				String tipoActividad = partes[2];
				String fecha = partes[3];
				String sala = partes[4];
				String plazas = partes[5];
				LOGGER.log(Level.INFO, "Clases cargadas correctamente.");
				try {

					String nuevaFecha = "";

					// 0123456789
					// dd-mm-aaaa

					String dia = fecha.substring(0, 2);
					String mes = fecha.substring(3, 5);
					String año = fecha.substring(6, 10);

					nuevaFecha = año + "-" + mes + "-" + dia;

					Date f = formatoFecha.parse(nuevaFecha);

					Clase c = new Clase(Integer.parseInt(idClase), hora, TipoActividad.valueOf(tipoActividad), f,
							Integer.parseInt(sala), Integer.parseInt(plazas));
					clases.add(c);
					gestorBD.añadirClase(c);
					LOGGER.log(Level.INFO, "Clase agregada a la base de datos: " + c);

				} catch (NumberFormatException e) {
					LOGGER.log(Level.WARNING, "Error.");
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch
					LOGGER.log(Level.WARNING, "Error.");
					e.printStackTrace();
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.SEVERE, "Error al abrir el archivo de clases.");
			e.printStackTrace();
		}

	}

	@Override
	public void guardarClasesCSV() {
		// TODO Auto-generated method stub
		try (PrintWriter writer = new PrintWriter(new FileWriter("Clases.csv"))) {
			for (Clase clase : clases) {
				String linea = String.format("%s,%s,%s,%s,%s,%s", clase.getIDClase(), clase.getHora(),
						clase.getTipoActividad(), clase.getFecha(), clase.getIDSala(), clase.getPlazas());
				writer.println(linea);
			}
			LOGGER.log(Level.INFO, "Clases guardadas en archivo CSV con éxito.");
			// System.out.println("Clases guardadas en archivo CSV con éxito.");
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "Error al guardar clases en archivo CSV.");
			ex.printStackTrace();
		}
	}

	public void cargarReservasCSV() {
		try {
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

			BufferedReader br = new BufferedReader(new FileReader("resources/data/Reservas.csv"));
			String linea = br.readLine();
			while (linea != null) {
				String[] datos = linea.split(",");
				String dni = datos[0];

				String tipoActividad = datos[1];
				String idSala = datos[2];
				String fecha = datos[3];
				String hora = datos[4];

				try {
					Reserva reserva = new Reserva(dni, TipoActividad.valueOf(tipoActividad), Integer.parseInt(idSala),
							formatoFecha.parse(fecha), hora);
					reserva.setDNI(dni);

					if (reservas.containsKey(dni)) {
						reservas.get(dni).add(reserva);
						LOGGER.log(Level.INFO, "Reserva agregada al mapa existente: " + reserva);
					} else {
						List<Reserva> lReservas = new ArrayList<>();
						lReservas.add(reserva);
						reservas.put(dni, lReservas);
						LOGGER.log(Level.INFO, "Reserva agregada al mapa: " + reserva);
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}

				linea = br.readLine();
			}
			br.close();
			gestorBD.añadirReservas(reservas);

		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Error al abrir el archivo de reservas.");
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error al guardar la reserva en el archivo CSV");
			e.printStackTrace();
		}
	}

	@Override
	public void cargarUsuariosBD() {
		// TODO Auto-generated method stub
		usuarios = gestorBD.obtenerRegistros("usuario", Usuario.class);
		// System.out.println("Usuarios cargados desde la base de datos con éxito.");
		LOGGER.log(Level.INFO, "Usuarios cargados con exito desde la BD.");
	}

	@Override
	public void guardarUsuariosBD() {
		// TODO Auto-generated method stub
		for (Usuario usuario : usuarios) {
			gestorBD.añadirUsuario(usuario);
		}
		LOGGER.log(Level.INFO, "Usuarios guardados en la base de datos con éxito.");
		// System.out.println("Usuarios guardados en la base de datos con éxito.");

	}

	@Override
	public void cargarClasesBD() {
		// TODO Auto-generated method stub
		clases = gestorBD.obtenerRegistros("clase", Clase.class);
		LOGGER.log(Level.INFO, "Clases cargadas desde la base de datos con éxito.");
		// System.out.println("Clases cargadas desde la base de datos con éxito.");
	}

	@Override
	public void guardarClasesBD() {
		// TODO Auto-generated method stub
		try {
			for (Clase clase : clases) {
				gestorBD.añadirClase(clase);
			}
			LOGGER.log(Level.INFO, "Clases guardadas en la base de datos con éxito.");
			// System.out.println("Clases guardadas en la base de datos con éxito.");
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Error al guardar clases en la base de datos.");
			// System.err.format("Error al guardar clases en la base de datos: ",
			// ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void actualizarClaseEnCSV(Clase claseActualizada, String rutaArchivo) {
		List<String> lineas = new ArrayList<String>();

		try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
			String linea;
			while ((linea = reader.readLine()) != null) {
				// Separar los datos de la línea por coma
				String[] datos = linea.split(",");
				int idClase = Integer.parseInt(datos[0].trim());

				// Verificar si la línea contiene la clase que estamos buscando
				if (idClase == claseActualizada.getIDClase()) {
					// Actualizar la línea con los nuevos datos
					String nuevaLinea = String.format("%d,%s,%s,%s,%d,%d", claseActualizada.getIDClase(),
							claseActualizada.getHora(), claseActualizada.getTipoActividad().toString(),
							claseActualizada.getFecha().toString(), claseActualizada.getIDSala(),
							claseActualizada.getPlazas());
					lineas.add(nuevaLinea);
				} else {
					// Conservar la línea original
					lineas.add(linea);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			// Manejar la excepción según tus necesidades
		}

		// Escribir las líneas actualizadas en el archivo CSV
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
			for (String nuevaLinea : lineas) {
				writer.write(nuevaLinea);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			// Manejar la excepción según tus necesidades
		}
	}

	public void borrarClasePorId(int idClase, String rutaArchivoCSV) {
		try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivoCSV));
				BufferedWriter writer = new BufferedWriter(new FileWriter("temp.csv"))) {
			String linea;
			while ((linea = reader.readLine()) != null) {
				String[] partes = linea.split(",");
				int id = Integer.parseInt(partes[0].trim());

				// Verificar si la línea contiene la clase que estamos buscando
				if (id == idClase) {
					// No agregar la línea al archivo temporal (borrar la línea)
					continue;
				}

				// Conservar la línea original en el archivo temporal
				writer.write(linea);
				writer.newLine();
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error al leer o escribir en el archivo CSV.", e);
			e.printStackTrace();
		}

		// Renombrar el archivo temporal al original para efectuar el borrado
		try {
			if (!new java.io.File("temp.csv").renameTo(new java.io.File(rutaArchivoCSV))) {
				LOGGER.log(Level.SEVERE, "Error al renombrar el archivo temporal.");
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error al renombrar el archivo temporal.", e);
			e.printStackTrace();
		}
	}

	public void añadirUsuarioACSV(Usuario usuario, String rutaArchivoCSV) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivoCSV, true))) {

			String datosUsuario = String.format("%s,%s,%s,%s,%s,%s%n", usuario.getDni(), usuario.getNombre(),
					usuario.getApellido(), usuario.getDireccion(), usuario.getCorreoElectronico(),
					usuario.getContrasena());

			writer.write(datosUsuario);
			LOGGER.log(Level.INFO, "Usuario añadido al archivo CSV con éxito.");
			System.out.println("Usuario añadido al archivo CSV con éxito.");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error al añadir el usuario al archivo CSV.", e);
			e.printStackTrace();
		}
	}

	public HashSet<Usuario> getUsuarios() {
		// TODO Auto-generated method stub
		return usuarios;
	}

	public HashSet<Clase> getClases() {
		// TODO Auto-generated method stub
		return clases;
	}

	public void agregarReservaUsuario(String dniUsuario, int id) {

		List<Reserva> susReservas = gestorBD.obtenerReservasPorDni(dniUsuario);

		Clase claseBuscada = null;
		for (Clase c : clases) {
			if (c.getIDClase() == id) {
				claseBuscada = c;
				break;
			}
		}

		Reserva r = new Reserva(dniUsuario, claseBuscada.getTipoActividad(), claseBuscada.getIDSala(),
				claseBuscada.getFecha(), claseBuscada.getHora());

		susReservas.add(r);

		gestorBD.añadirReserva(r);
	}

	public boolean apuntadoAEsaClase(String dniUsuario, TipoActividad tipoActividad, Date fecha, String hora) {
		for (Reserva reserva : gestorBD.obtenerReservasPorDni(dniUsuario)) {

			System.out.println(reserva);

			if (reserva.getTipoActividad() == tipoActividad && reserva.getFecha().equals(fecha)
					&& reserva.getHora().equals(hora)) {
				return true;
			}
		}
		return false;
	}




	public int getNumeroReservas(String dni) {
	    if (reservas.containsKey(dni)) {
	        return reservas.get(dni).size();
	    } else {
	        return 0;
	    }
	}




	public Map<String, List<Reserva>> obtenerReservas() {
		// TODO Auto-generated method stub
		return reservas;
	}




	public String obtenerDni() {
	    
	    if (!usuarios.isEmpty()) {
	        return usuarios.iterator().next().getDni();
	    } else {
	        return null; 
	    }
	}

}
