package Ventanas;

import java.awt.EventQueue;

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
