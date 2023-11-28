package gui;

import com.toedter.calendar.JDateChooser;

import db.GestorBD;
import domain.Clase;
import domain.Reserva;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;


import io.FicheroLogger;

public class VentanaCalendarioActividades extends JFrame {
    private JDateChooser dateChooser;
    private JPanel eventPanel;
    private Map<Date, String> events;
    private boolean dateSelected = false;
    private boolean activitySelected = false;
	protected String dniUsuario;
    
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());

    public VentanaCalendarioActividades() {
        JFrame frame = new JFrame("HORARIO DE ACTIVIDADES");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setResizable(false);

        events = new HashMap<>();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel selectDateLabel = new JLabel("Seleccionar fecha:");
        topPanel.add(selectDateLabel);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setPreferredSize(new Dimension(150, dateChooser.getPreferredSize().height));
        topPanel.add(dateChooser);

       

        eventPanel = new JPanel();
        eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(eventPanel);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frame.getContentPane().add(mainPanel);

        JButton selectActivityButton = new JButton("Apuntarme");
        selectActivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activitySelected) {
                    selectActivity();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona una actividad antes de apuntarte.");
                    LOGGER.log(Level.SEVERE, "Intento de apuntarse sin seleccionar una actividad.");
                }
            }
        });

        JButton salirButton = new JButton("Salir");
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                VentanaUsuario ventanaUsuario = new VentanaUsuario(dniUsuario);
                ventanaUsuario.setVisible(true);
            }


        });

        selectActivityButton.setPreferredSize(new Dimension(150, 40));
        salirButton.setPreferredSize(new Dimension(150, 40));

        bottomPanel.add(selectActivityButton);
        bottomPanel.add(salirButton);

       
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        selectActivityButton.setPreferredSize(new Dimension(150, 40));
        selectActivityButton.setEnabled(false);
        bottomPanel.add(selectActivityButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        dateChooser.getDateEditor().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (dateChooser.getDate() != null) {
                    dateSelected = true;
                    try {
						showEventsForSelectedDate();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                } else {
                    dateSelected = false;
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showEventsForSelectedDate() throws ParseException {
        if (!dateSelected) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fecha para poder mostrar las actividades disponibles.");
            return;
        }

        Date selectedDate = dateChooser.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String selectedDateString = sdf.format(selectedDate);

        GestorBD gestorBD = new GestorBD();
        List<Clase> clasesDisponibles = gestorBD.obtenerClasesPorFecha(selectedDateString);

        eventPanel.removeAll();

        if (!clasesDisponibles.isEmpty()) {
            JTextArea textArea = new JTextArea("Clases disponibles para " + selectedDateString + ":\n");
            for (Clase clase : clasesDisponibles) {
                textArea.append(clase.getTipoActividad() + " - " + clase.getHora() + "\n");
            }
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setOpaque(false);
            textArea.setEditable(false);
            eventPanel.add(new JScrollPane(textArea));
            activitySelected = true;
        } else {
            JTextArea textArea = new JTextArea("No hay clases disponibles para esta fecha.");
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setOpaque(false);
            textArea.setEditable(false);
            eventPanel.add(new JScrollPane(textArea));
            activitySelected = false;
        }

        eventPanel.revalidate();
        eventPanel.repaint();
    }


	

    private void selectActivity() {
        Date selectedDate = dateChooser.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDateString = sdf.format(selectedDate);

        String eventsForDate = events.get(selectedDate);

        if (eventsForDate != null) {
            String[] activityOptions = eventsForDate.split("\n");
            String selectedActivity = (String) JOptionPane.showInputDialog(null,
                    "Selecciona una actividad:",
                    "Actividades disponibles para " + selectedDateString,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    activityOptions,
                    activityOptions[0]);

            if (selectedActivity != null) {
                JOptionPane.showMessageDialog(null, "Has seleccionado la actividad: " + selectedActivity);
                LOGGER.log(Level.INFO, "Actividad seleccionada" + selectedActivity);
            }
        }
    }
    

    public void mostrarVentana() {
        getContentPane().setVisible(true);
    }
    
    
    
    
   
   
}
