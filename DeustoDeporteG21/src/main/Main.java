package main;

import java.awt.EventQueue;

import db.GestorBD;
import domain.Gestor;
import gui.VentanaMenuAdmin;
import gui.VentanaPrincipal;
import io.FicheroLogger;

public class Main {
	
	public static void main(String[] args) {
		
		FicheroLogger ficheroLogger = new FicheroLogger();
		
		GestorBD gestorBD = new GestorBD();
		
		gestorBD.crearTablas();
		
		Gestor gestor = new Gestor();

	
		gestor.cargarUsuariosCSV("resources/data/Usuarios.csv");
		gestor.cargarClasesCSV("resources/data/Clases.csv");
	

		
      /*  EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaPrincipal window = new VentanaPrincipal();
                	
                    //VentanaMenuAdmin window = new VentanaMenuAdmin();
                    window.mostrarVentana();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        */
        FicheroLogger.cerrarFileHandler();
        
        
    }
	

}
