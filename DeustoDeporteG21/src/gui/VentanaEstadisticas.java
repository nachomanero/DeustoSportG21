package gui;

import javax.swing.*;

import db.GestorBD;
import domain.Gestor;
import domain.TipoActividad;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class VentanaEstadisticas extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnMostrarTopClases;
    private JPanel panelProgressBar;

    private Gestor gestor;
    private JButton btnHistorialReservas;

    public VentanaEstadisticas(Gestor g, GestorBD gb) {
        this.gestor = g;  
        getContentPane().setBackground(new Color(102, 153, 153));
        setTitle("TOP CLASES");
        setSize(400, 200);

        btnMostrarTopClases = new JButton("Mostrar Top Clases");
        btnMostrarTopClases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTopClases();
            }
        });

        btnHistorialReservas = new JButton("Historial de Reservas");

        btnHistorialReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarHistorialReservas(g, gb);  
            }
        });

        panelProgressBar = new JPanel();
        panelProgressBar.setLayout(new GridLayout(3, 1));

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnMostrarTopClases);
        panelBoton.add(btnHistorialReservas);

        getContentPane().add(panelBoton, BorderLayout.NORTH);
        getContentPane().add(panelProgressBar, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void mostrarTopClases() {
        gestor.guardarActividadesMasElegidas();

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
        int totalReservas = actividadesMap.values().stream().mapToInt(value -> value).sum();
        int reservasActividad = actividadesMap.getOrDefault(actividad, 0);
        return totalReservas == 0 ? 0 : (int) Math.round((double) reservasActividad / totalReservas * 100);
    }

    private void mostrarHistorialReservas(Gestor g, GestorBD gb) {
        try {
            VentanaHistorialReservas ventanaHistorial = new VentanaHistorialReservas(gb);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void mostrarVentana() {
        getContentPane().setVisible(true);
    }
}

