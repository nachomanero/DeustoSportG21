package domain;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

import db.GestorBD;
import io.FicheroLogger;
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
	
	public void realizarOperacionEnBD() {
        
		
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
				if (!usuarios.contains(u)) {
				    usuarios.add(u);
				    gestorBD.añadirUsuario(u);
				    LOGGER.log(Level.INFO, "Usuario agregado a la base de datos: " + u);
				} else {
				    LOGGER.log(Level.WARNING, "Intento de agregar un usuario duplicado: " + u);
				}
				
			}
		} catch (IOException e) {
	        LOGGER.log(Level.WARNING, "Error al abrir el archivo de Usuarios.", e);
	    }
	    //System.out.println(usuarios);
		
	}
	
	@Override
	public void guardarUsuariosCSV() {
		// TODO Auto-generated method stub
		try (PrintWriter writer = new PrintWriter(new FileWriter("Usuarios.csv"))) {
	        for (Usuario usuario : usuarios) {
	            String linea = String.format("%s,%s,%s,%s,%s,%s",
	                    usuario.getDni(),
	                    usuario.getNombre(),
	                    usuario.getApellido(),
	                    usuario.getDireccion(),
	                    usuario.getCorreoElectronico(),
	                    usuario.getContrasena());
	            writer.println(linea);
	        }
	        LOGGER.log(Level.INFO, "Usuarios guardados en el archivo CSV con éxito.");
	        System.out.println("Usuarios guardados en el archivo CSV con éxito.");
	    } catch (IOException ex) {
	        LOGGER.log(Level.WARNING, "Error al guardar usuarios en el archivo CSV.");
	        ex.printStackTrace();
	    }
	}
	
	//Este metodo lo he hecho para poder poner ID automático al crear las clases
	public int obtenerUltimoIDClase(String nomFichClases) {
		int contador = 0;
		try {
			Scanner sc = new Scanner(new FileReader(nomFichClases));
			String linea;
			
			while(sc.hasNext()) {
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
            String datosClase = String.format(
                    "%d,%s,%s,%s,%d,%d%n",
                    clase.getIDClase(),
                    clase.getHora(),
                    clase.getTipoActividad().toString(),
                    new SimpleDateFormat("dd-MM-yyyy").format(clase.getFecha()),
                    clase.getIDSala(),
                    clase.getPlazas()
            );
            
            writer.write(datosClase);
            LOGGER.log(Level.INFO, "Clase añadida en el archivo CSV con éxito.");
            System.out.println("Clase añadida al archivo CSV correctamente.");
            
        } catch (IOException e) {
        	LOGGER.log(Level.WARNING, "Error al guardar la clase en el archivo CSV");
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
				LOGGER.log(Level.INFO,"Clases cargadas correctamente.");
				try {
					Clase c = new Clase(Integer.parseInt(idClase), hora, TipoActividad.valueOf(tipoActividad), formatoFecha.parse(fecha), Integer.parseInt(sala), Integer.parseInt(plazas));
					clases.add(c);
					gestorBD.añadirClase(c);
					LOGGER.log(Level.INFO, "Usuario agregado a la base de datos: " + c);
			        
				} catch (NumberFormatException e) {
					LOGGER.log(Level.WARNING,"Error.");
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch 
					LOGGER.log(Level.WARNING,"Error.");
					e.printStackTrace();
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.WARNING,"Error al abrir el archivo de clases.");
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void guardarClasesCSV() {
		// TODO Auto-generated method stub
		try (PrintWriter writer = new PrintWriter(new FileWriter("Clases.csv"))) {
	        for (Clase clase : clases) {
	            String linea = String.format("%s,%s,%s,%s,%s,%s",
	                    clase.getIDClase(),
	                    clase.getHora(),
	                    clase.getTipoActividad(),
	                    clase.getFecha(),
	                    clase.getIDSala(),
	                    clase.getPlazas());
	            writer.println(linea);
	        }
	        LOGGER.log(Level.INFO, "Clases guardadas en archivo CSV con éxito.");
	        //System.out.println("Clases guardadas en archivo CSV con éxito.");
	    } catch (IOException ex) {
	        LOGGER.log(Level.WARNING, "Error al guardar clases en archivo CSV.");
	        ex.printStackTrace();
	    }
	}
	

	@Override
	public void cargarUsuariosBD() {
		// TODO Auto-generated method stub
		usuarios = gestorBD.obtenerRegistros("usuario", Usuario.class);
		//System.out.println("Usuarios cargados desde la base de datos con éxito.");
		LOGGER.log(Level.INFO,"Usuarios cargados con exito desde la BD.");
	}

	@Override
	public void guardarUsuariosBD() {
		// TODO Auto-generated method stub
		for (Usuario usuario : usuarios) {
            gestorBD.añadirUsuario(usuario); 
        }
		LOGGER.log(Level.INFO,"Usuarios guardados en la base de datos con éxito.");
        //System.out.println("Usuarios guardados en la base de datos con éxito.");
		
	}

	
	@Override
	public void cargarClasesBD() {
		// TODO Auto-generated method stub
		clases = gestorBD.obtenerRegistros("clase", Clase.class);
		LOGGER.log(Level.INFO,"Clases cargadas desde la base de datos con éxito.");
		//System.out.println("Clases cargadas desde la base de datos con éxito.");
	}

	@Override
	public void guardarClasesBD() {
		// TODO Auto-generated method stub
		try {
	        for (Clase clase : clases) {
	            gestorBD.añadirClase(clase); 
	        }
	        LOGGER.log(Level.INFO,"Clases guardadas en la base de datos con éxito.");
	        System.out.println("Clases guardadas en la base de datos con éxito.");
	    } catch (Exception ex) {
	    	LOGGER.log(Level.WARNING,"Error al guardar clases en la base de datos.");
	        System.err.format("Error al guardar clases en la base de datos: ", ex.getMessage());
	        ex.printStackTrace();
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

	

	




}
