package main;

import java.awt.EventQueue;
import java.text.ParseException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import db.GestorBD;
import domain.Gestor;
import gui.VentanaMenuAdmin;
import gui.VentanaPrincipal;
import io.FicheroLogger;

public class Main {
	
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
        }, 0, 4, TimeUnit.MINUTES);

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
		
		
		
		//System.out.println(gestorBD.obtenerTodasLasClases());
		
		VentanaPrincipal wind = new VentanaPrincipal( gestor , gestorBD );
	
		wind.mostrarVentana();
		
	        
        FicheroLogger.cerrarFileHandler();
        
        
    }
	

}
