package main;

import java.awt.EventQueue;

import gui.VentanaMenuAdmin;
import gui.VentanaPrincipal;
import io.FicheroLogger;

public class Main {
	
	public static void main(String[] args) {
		
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //VentanaPrincipal window = new VentanaPrincipal();
                	
                    VentanaMenuAdmin window = new VentanaMenuAdmin();
                    window.mostrarVentana();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	

}
