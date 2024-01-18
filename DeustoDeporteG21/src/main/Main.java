package main;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import db.GestorBD;
import domain.Gestor;
import domain.Usuario;
import gui.VentanaMenuAdmin;
import gui.VentanaPrincipal;
import io.FicheroLogger;

public class Main {
	private static final String PROPERTIES_FILE = "conf/config.properties";
	private static final String INPUT_KEY = "input";
	private static final String OUTPUT_KEY = "output";
	private static final String FicheroUsuarios = "resources/data/Usuarios.csv";

	public static void main(String[] args) {
		FicheroLogger ficheroLogger = new FicheroLogger();
		
		GestorBD gestorBD = new GestorBD();
		Gestor gestor = new Gestor();
		
		gestor.cargarUsuariosCSV("resources/data/Usuarios.csv");
		gestor.cargarClasesCSV("resources/data/Clases.csv");
		gestor.cargarReservasCSV();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Guardando reservas en la base de datos...");
        }, 0, 5, TimeUnit.MINUTES);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
        }));
		
        
        //USO DE PROPERTIES 
        try {
			Properties properties = loadProperties(); 
			if (properties.containsKey(INPUT_KEY)) {
				List<Usuario> usuarios = loadData(properties.getProperty(INPUT_KEY));
				System.out.println(String.format("- Se han leido los usuarios del fichero '%s'", 
				usuarios.size(), properties.getProperty(INPUT_KEY)));
				if (usuarios != null && properties.containsKey(OUTPUT_KEY)) {
					storeCSV(properties.getProperty(OUTPUT_KEY), usuarios);
				} else {
					System.out.println("- No se ha podido crear el fichero.");
				}
			} else {
				System.out.println("- No se puede localizar el fichero de entrada.");
			}


        	
        }catch (Exception ex) {
			System.err.println(String.format("Error en el main: %s", ex.getMessage()));
			ex.printStackTrace();
		}
        
		
		
		//System.out.println(gestorBD.obtenerTodasLasClases());
		
		VentanaPrincipal wind = new VentanaPrincipal( gestor , gestorBD );
	
		wind.mostrarVentana();
		
	        
        FicheroLogger.cerrarFileHandler();
        
        
    }
	

	private static List<Usuario> leerUsuariosDesdeCSV(String string) {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Usuarios.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String nombre = datos[0].trim();
                String dni = datos[1].trim();
                String apellido = datos[2].trim();
                String direccion= datos[3].trim();
                String correoElectronico = datos[4].trim();
                String contrasena = datos[5].trim();
                Usuario usuario = new Usuario(dni,nombre,apellido,direccion,correoElectronico,contrasena);
                usuarios.add(usuario);
            }
        } catch (Exception ex) {
        	System.err.println();
        }
        return usuarios;
	}

	private static Properties loadProperties() {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(PROPERTIES_FILE));
		} catch (Exception ex) {
			System.err.println(String.format("Error leyendo propiedades: %s", ex.getMessage()));
			ex.printStackTrace();
		}
		
		return properties;
	}
	@SuppressWarnings("unchecked")
	private static List<Usuario> loadData(String FicheroUsuarios) {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FicheroUsuarios))) {
			return (List<Usuario>) in.readObject();
		} catch (Exception ex) {
			System.err.println(String.format("Error leyendo '%s': %s", FicheroUsuarios, ex.getMessage()));
			ex.printStackTrace();
		}
		return null;
	}
	private static void storeCSV(String FicheroUsuarios, List<Usuario> usuarios) {
		if (FicheroUsuarios!= null && !usuarios.isEmpty()) {
			try (PrintWriter out = new PrintWriter(new FileOutputStream(FicheroUsuarios))){
				for(Usuario usuario: usuarios) {
	                out.println(usuario.getDni()+","+usuario.getNombre() + "," + usuario.getApellido() + "," + usuario.getDireccion()+","+usuario.getCorreoElectronico()+","+usuario.getContrasena());
				}
	            System.out.println("Lista de usuarios guardada correctamente en " + FicheroUsuarios);
	
			}catch(Exception ex) {
				System.err.println("error en guardar en el csv"+ex.getStackTrace());
			}
			
		}
	}



	

}
