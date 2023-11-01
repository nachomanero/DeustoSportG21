package main;

import java.awt.EventQueue;

import gui.VentanaPrincipal;

public class Main {
	
	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaPrincipal window = new VentanaPrincipal();
                    window.mostrarVentana();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	

}