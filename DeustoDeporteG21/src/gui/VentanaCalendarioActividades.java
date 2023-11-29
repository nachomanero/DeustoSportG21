package gui;

import com.toedter.calendar.JDateChooser;

import db.GestorBD;
import domain.Clase;
import domain.Gestor;
import domain.Reserva;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

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
    private JList<Clase> listaActividades;
    private DefaultListModel<Clase> modeloLista;

 
    private boolean dateSelected = false;
    private boolean activitySelected = false;
	protected String dniUsuario;
	private Gestor g;
	private GestorBD gbd;
    
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());

    public VentanaCalendarioActividades(Gestor gestor , GestorBD gestorBD , String dniUsuario ) {
    	
    	g = gestor;
    	gbd = gestorBD;
    	
    	
        JFrame frame = new JFrame("HORARIO DE ACTIVIDADES");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setResizable(false);

       

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
        
        listaActividades = new JList<>();
        modeloLista = new DefaultListModel<>();
        listaActividades.setModel(modeloLista);

       

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
                    showEventsForSelectedDate();
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
                VentanaUsuario ventanaUsuario = new VentanaUsuario(g , gbd , dniUsuario);
                ventanaUsuario.setVisible(true);
            }


        });

        selectActivityButton.setPreferredSize(new Dimension(150, 40));
        salirButton.setPreferredSize(new Dimension(150, 40));

       
        bottomPanel.add(salirButton);

       
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        selectActivityButton.setPreferredSize(new Dimension(150, 40));
        selectActivityButton.setEnabled(false);
        bottomPanel.add(selectActivityButton);

        dateChooser.getDateEditor().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (dateChooser.getDate() != null) {
                    dateSelected = true;
                    showEventsForSelectedDate();
                } else {
                    dateSelected = false;
                }
            }
        });
        /*listaActividades.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // User has finished selecting
                    activitySelected = true;
                }
            }
        });
        */

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showEventsForSelectedDate() {
        if (!dateSelected) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fecha para poder mostrar las actividades disponibles.");
            return;
        }

        Date selectedDate = dateChooser.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String selectedDateString = sdf.format(selectedDate);

        
        List<Clase> clasesDisponibles = null;
        try {
            clasesDisponibles = gbd.obtenerClasesPorFecha(selectedDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        eventPanel.removeAll();

        if (!clasesDisponibles.isEmpty()) {
            JTextArea textArea = new JTextArea();
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

	


    public void mostrarVentana() {
        getContentPane().setVisible(true);
    }
    
    
    
    
   
   
}
