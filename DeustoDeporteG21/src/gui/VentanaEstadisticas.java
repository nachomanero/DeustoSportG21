package gui;

import javax.swing.*;

import domain.Gestor;
import domain.TipoActividad;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;


public class VentanaEstadisticas extends JFrame {

    private JButton btnMostrarTopClases;
    private JPanel panelProgressBar;

    private Gestor gestor; 

    public VentanaEstadisticas(Gestor gestor) {
        this.gestor = gestor; 
        setTitle("TOP CLASES");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnMostrarTopClases = new JButton("Mostrar Top Clases");
        btnMostrarTopClases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTopClases();
            }
        });

        panelProgressBar = new JPanel();
        panelProgressBar.setLayout(new GridLayout(3, 1));

        panelProgressBar = new JPanel();
        panelProgressBar.setLayout(new GridLayout(3, 1));

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        panelBoton.add(btnMostrarTopClases);

        getContentPane().add(panelBoton, BorderLayout.NORTH);
        getContentPane().add(panelProgressBar, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void mostrarTopClases() {
  
        Map<TipoActividad, Integer> actividadesMap = gestor.cargarActividadesMasElegidas();
        List<TipoActividad> topClases = gestor.las3MasSolicitadas(actividadesMap);

        panelProgressBar.removeAll();
        for (TipoActividad actividad : topClases) {
            JProgressBar progressBar = new JProgressBar();
            progressBar.setStringPainted(true);
            panelProgressBar.add(progressBar);

            int porcentaje = calcularPorcentajeActividad(actividad, actividadesMap);
            progressBar.setValue(porcentaje);
            progressBar.setString(actividad.toString() + ": " + porcentaje + "%");
        }

        revalidate();
        repaint();
    }

    private int calcularPorcentajeActividad(TipoActividad actividad, Map<TipoActividad, Integer> actividadesMap) {
        int totalReservas = actividadesMap.values().stream().mapToInt(Integer::intValue).sum();
        int reservasActividad = actividadesMap.getOrDefault(actividad, 0);
        return totalReservas == 0 ? 0 : (int) Math.round((double) reservasActividad / totalReservas * 100);
    }
    
    public void mostrarVentana() {
    	getContentPane().setVisible(true);
    }

   
}
