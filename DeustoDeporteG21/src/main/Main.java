package main;

import java.awt.EventQueue;

import db.GestorBD;
import domain.Gestor;
import gui.VentanaMenuAdmin;
import gui.VentanaPrincipal;
import io.FicheroLogger;

public class Main {
	
	public static void main(String[] args) {
		GestorBD gestorBD = new GestorBD();
		Gestor gestor = new Gestor();
		gestor.realizarOperacionEnBD();
		gestorBD.crearTablas();
		 FicheroLogger ficheroLogger = new FicheroLogger();
        EventQueue.invokeLater(new Runnable() {
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
        FicheroLogger.cerrarFileHandler();
    }
	

}
