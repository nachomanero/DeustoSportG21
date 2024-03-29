package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import db.GestorBD;
import domain.Gestor;
import domain.Usuario;
import gui.VentanaPrincipal;
import io.FicheroLogger;

public class Main {
	private static final String PROPERTIES_FILE = "conf/config.properties";
	private static final String INPUT_KEY = "input";
	private static final String OUTPUT_KEY = "output";
	private static final String FicheroUsuarios = "resources\\data\\propertiesUsuarios.csv";

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
        }, 0, 1, TimeUnit.MINUTES);

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
                List<Usuario> usuarios = leerUsuariosDesdeCSV(properties.getProperty(INPUT_KEY));
                if (usuarios != null) {
                    System.out.println(String.format("- Se han leído %d usuarios del fichero '%s'",
                            usuarios.size(), properties.getProperty(INPUT_KEY)));
                    if (properties.containsKey(OUTPUT_KEY)) {
                        storeCSV(properties.getProperty(OUTPUT_KEY), usuarios);
                        storeData(properties.getProperty(OUTPUT_KEY), usuarios);
                    } else {
                        System.out.println("- No se ha proporcionado el nombre del fichero de salida.");
                    }
                } else {
                    System.out.println("- No se han leído usuarios o el fichero de entrada es incorrecto.");
                }
            } else {
                System.out.println("- No se puede localizar el fichero de entrada en las propiedades.");
            }

        } catch (Exception ex) {
            System.err.println(String.format("Error en el main: %s", ex.getMessage()));
            ex.printStackTrace();
        }
        
        /*
        
        try {
            Properties properties = loadProperties();
            if (properties.containsKey("input_file")) {
                List<String> rutas = leerRutasDesdeArchivo(properties.getProperty("input_file"));
                if (rutas != null) {
                    System.out.println(String.format("- Se han leído %d rutas del archivo '%s'",
                            rutas.size(), properties.getProperty("input_file")));

                    if (properties.containsKey("output_database_connection") &&
                            properties.containsKey("output_database_user") &&
                            properties.containsKey("output_database_password") &&
                            properties.containsKey("output_table") &&
                            properties.containsKey("output_column")) {

                        guardarRutasEnBaseDeDatos(
                                properties.getProperty("output_database_connection"),
                                properties.getProperty("output_database_user"),
                                properties.getProperty("output_database_password"),
                                properties.getProperty("output_table"),
                                properties.getProperty("output_column"),
                                rutas
                        );

                        System.out.println("- Rutas guardadas en la base de datos correctamente.");
                    } else {
                        System.out.println("- Faltan propiedades para la conexión de la base de datos o la definición de la tabla/columna.");
                    }
                } else {
                    System.out.println("- No se han leído rutas o el archivo de entrada es incorrecto.");
                }
            } else {
                System.out.println("- No se puede localizar el archivo de entrada en las propiedades.");
            }
        } catch (Exception ex) {
            System.err.println(String.format("Error en el main: %s", ex.getMessage()));
            ex.printStackTrace();
        }
        */
        
       
		
		
		//System.out.println(gestorBD.obtenerTodasLasClases());
		
		VentanaPrincipal wind = new VentanaPrincipal( gestor , gestorBD );
	
		wind.mostrarVentana();
		
	        
        FicheroLogger.cerrarFileHandler();
        
        
    }
	
	
	
	private static List<String> leerRutasDesdeArchivo(String filePath) {
        List<String> rutas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String ruta;
            while ((ruta = reader.readLine()) != null) {
                rutas.add(ruta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rutas;
    }
	
	 private static void guardarRutasEnBaseDeDatos(
             String databaseConnection,
             String databaseUser,
             String databasePassword,
             String tableName,
             String columnName,
             List<String> rutas) {

         String sql = "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)";

         try (Connection connection = DriverManager.getConnection(databaseConnection, databaseUser, databasePassword);
              PreparedStatement statement = connection.prepareStatement(sql)) {

             for (String ruta : rutas) {
                 statement.setString(1, ruta);
                 statement.executeUpdate();
             }

         } catch (Exception e) {
             e.printStackTrace();
         }
     }
	 
	 

	private static List<Usuario> leerUsuariosDesdeCSV(String ficheroUsuarios) {
	    List<Usuario> usuarios = new ArrayList<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(ficheroUsuarios))) {
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            String[] datos = linea.split(",");
	            
	            if (datos.length >= 6) {
	                String nombre = datos[0].trim();
	                String dni = datos[1].trim();
	                String apellido = datos[2].trim();
	                String direccion = datos[3].trim();
	                String correoElectronico = datos[4].trim();
	                String contrasena = datos[5].trim();
	                
	                Usuario usuario = new Usuario(dni, nombre, apellido, direccion, correoElectronico, contrasena);
	                usuarios.add(usuario);
	            } else {
	                System.err.println("Error: La línea no tiene la cantidad esperada de elementos: " + linea);
	            }
	        }
	    } catch (Exception ex) {
	        System.err.println(String.format("Error leyendo CSV '%s': %s", ficheroUsuarios, ex.getMessage()));
	        ex.printStackTrace();
	    }
	    return usuarios;
	}

	private static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader(PROPERTIES_FILE)) {
            properties.load(fileReader);
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
	  private static void storeCSV(String ficheroUsuarios, List<Usuario> usuarios) {
	        if (ficheroUsuarios != null && !usuarios.isEmpty()) {
	            try (PrintWriter out = new PrintWriter(new FileOutputStream(ficheroUsuarios))) {
	                for (Usuario usuario : usuarios) {
	                    out.println(usuario.getDni() + "," + usuario.getNombre() + "," + usuario.getApellido() + ","
	                            + usuario.getDireccion() + "," + usuario.getCorreoElectronico() + ","
	                            + usuario.getContrasena());
	                }
	                System.out.println("Lista de usuarios guardada correctamente en " + ficheroUsuarios);

	            } catch (Exception ex) {
	                System.err.println(String.format("Error al guardar en el CSV '%s': %s", ficheroUsuarios,
	                        ex.getMessage()));
	                ex.printStackTrace();
	            }

	        }
	  }
	  private static void storeData(String FicheroUsuarios, List<Usuario> usuarios) {
	        if (FicheroUsuarios != null && !usuarios.isEmpty()) {
	            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FicheroUsuarios))) {
	                out.writeObject(usuarios);
	                System.out.println("Lista de usuarios guardada correctamente en " + FicheroUsuarios);

	            } catch (Exception ex) {
	                System.err.println(String.format("Error al guardar en el objeto serializado '%s': %s", FicheroUsuarios,
	                        ex.getMessage()));
	                ex.printStackTrace();
	            }

	        }
	    }


	

}
