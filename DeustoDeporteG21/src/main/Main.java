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
		Gestor gestor = new Gestor();
		
		gestor.cargarUsuariosCSV("resources/data/Usuarios.csv");
		gestor.cargarClasesCSV("resources/data/Clases.csv");
		gestor.cargarReservasCSV();
		
		
		//System.out.println(gestorBD.obtenerTodasLasClases());
		
		VentanaPrincipal wind = new VentanaPrincipal( gestor , gestorBD );
	
		wind.mostrarVentana();
		
	        
        FicheroLogger.cerrarFileHandler();
        
        
    }
	

}
