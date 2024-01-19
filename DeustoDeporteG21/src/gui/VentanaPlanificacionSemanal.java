package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.toedter.calendar.JDateChooser;

import db.GestorBD;
import domain.Clase;
import domain.Gestor;
import domain.Reserva;
import io.FicheroLogger;
import recursividad.CombinacionClases;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.logging.Level;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;

@SuppressWarnings("unused")
public class VentanaPlanificacionSemanal extends JFrame {
    private static final long serialVersionUID = 1L;

    private Gestor g;
    private GestorBD gbd;
    private String dniUsuario;
    private JDateChooser dateChooser;
    private JButton btnCambiar, btnApuntarse, exitButton;
    private DefaultListModel<Clase> actividadesListModel;
    private JList<Clase> actividadesList;
    private List<Clase> combinacionActividades;
    private Date date;
	private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());
    public VentanaPlanificacionSemanal(Gestor gestor, GestorBD gestorBD, String dniUsuario) {
        g = gestor;
        gbd = gestorBD;
        this.dniUsuario = dniUsuario;

        JFrame frame = new JFrame("PLANIFICACION SEMANAL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel selectDateLabel = new JLabel("Seleccionar dia 1:");
        topPanel.add(selectDateLabel);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy");
        dateChooser.setPreferredSize(new Dimension(150, dateChooser.getPreferredSize().height));
        topPanel.add(dateChooser);

        btnCambiar = new JButton("Cambiar");
        topPanel.add(btnCambiar);
        btnCambiar.setEnabled(false);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        actividadesListModel = new DefaultListModel<>();
        actividadesList = new JList<>(actividadesListModel);
        combinacionActividades = new ArrayList<>();

        JScrollPane scrollPane = new JScrollPane(actividadesList);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        btnApuntarse = new JButton("Apuntarme");
        btnApuntarse.setPreferredSize(new Dimension(100, 40));
        btnApuntarse.setEnabled(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(btnApuntarse, gbc);

        setupExitButton();
        gbc.gridx = 0;
        gbc.gridy = 1;
        rightPanel.add(exitButton, gbc);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        frame.getContentPane().add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        dateChooser.getDateEditor().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                mostrarActividades();
            }
        });

        btnApuntarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apuntarse();
                actividadesListModel.clear();
            }
        });

        btnCambiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarActividades();
            }
        });
    }

    private void mostrarActividades() {
        Date selectedDate = dateChooser.getDate();

        if (selectedDate == null) {
            JOptionPane.showMessageDialog(null, "La fecha seleccionada no es válida.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String selectedDateString = sdf.format(selectedDate);

        try {
            if (selectedDate != date || date == null) {
                combinacionActividades = CombinacionClases.obtenerCombinacionClases(selectedDateString, gbd);
                date = selectedDate;
            }

            actividadesListModel.clear();

            if (!combinacionActividades.isEmpty()) {
                for (Clase clase : combinacionActividades) {
                    actividadesListModel.addElement(clase);
                }
                LOGGER.log(Level.INFO, "Se han cargado las actividades correctamente.");
            } else {
                LOGGER.log(Level.INFO, "No hay actividades disponibles para la fecha seleccionada.");
            }

            btnApuntarse.setEnabled(true);
            btnCambiar.setEnabled(true);
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Error parsing date: " + selectedDateString, e);
            JOptionPane.showMessageDialog(null, "Error al obtener las clases para la fecha seleccionada.");
        }
    }

    private void apuntarse() {
        if (combinacionActividades != null) {
            Map<String, List<Reserva>> listaReservas = new HashMap<>();
            listaReservas.put(dniUsuario, new ArrayList<>());
            combinacionActividades.forEach(c -> {
                int id = c.getIDClase();
                int plazasDisponibles = c.getPlazas();
                gbd.actualizarPlazas(id, plazasDisponibles - 1);
                g.agregarReservaUsuario(dniUsuario, id);
                Reserva reserva = new Reserva(dniUsuario, c.getTipoActividad(), c.getIDSala(), c.getFecha(),
                        c.getHora());
                listaReservas.get(dniUsuario).add(reserva);
            });
            gbd.añadirReservas(listaReservas);
            btnApuntarse.setEnabled(false);
            btnCambiar.setEnabled(false);
            
            JOptionPane.showMessageDialog(
                    VentanaPlanificacionSemanal.this, 
                    "Te has apuntado a las clases correctamente",
                    "Apuntarse a clases",
                    JOptionPane.INFORMATION_MESSAGE); 
        } else {
            JOptionPane.showMessageDialog(null, "Ya estás apuntado a esta clase", "Apuntarse a clase",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void setupExitButton() {
        exitButton = new JButton("Retroceder");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(exitButton);
                thisFrame.dispose();
                VentanaCalendarioActividades vent = new VentanaCalendarioActividades(g, gbd, dniUsuario);
                vent.setVisible(true);
            }
        });
        exitButton.setPreferredSize(new Dimension(100, 40));
    }

    public void mostrarVentana() {
        getContentPane().setVisible(true);
    }
    
}

