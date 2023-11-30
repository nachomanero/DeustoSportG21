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
    private JList<Clase> actividadesList;
    private DefaultListModel<Clase> actividadesListModel;
    private JButton selectActivityButton;
    private JButton exitButton;
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());

    private Gestor g;
    private GestorBD gbd;
	protected String dniUsuario;
	

    public VentanaCalendarioActividades(Gestor gestor , GestorBD gestorBD , String dniUsuario ) {
    	
    	g = gestor;
        gbd = gestorBD;

        JFrame frame = new JFrame("HORARIO DE ACTIVIDADES");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel selectDateLabel = new JLabel("Seleccionar fecha:");
        topPanel.add(selectDateLabel);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setPreferredSize(new Dimension(150, dateChooser.getPreferredSize().height));
        topPanel.add(dateChooser);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        actividadesListModel = new DefaultListModel<>();
        actividadesList = new JList<>(actividadesListModel);
        JScrollPane scrollPane = new JScrollPane(actividadesList);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        selectActivityButton = new JButton("Apuntarme");
        selectActivityButton.addActionListener(e -> actividadSeleccionada());
        selectActivityButton.setPreferredSize(new Dimension(100, 40));
        selectActivityButton.setEnabled(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(selectActivityButton, gbc);

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
                showActivitiesForSelectedDate();
            }

			private void showActivitiesForSelectedDate() {
				
				
			}
        });

       
    }
    private void mostrarActividades(){
        Date selectedDate = dateChooser.getDate();

        if (selectedDate == null) {
            JOptionPane.showMessageDialog(null, "La fecha seleccionada no es válida.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String selectedDateString = sdf.format(selectedDate);

        List<Clase> clasesDisponibles = null;
        try {
            clasesDisponibles = gbd.obtenerClasesPorFecha(selectedDateString);
            actividadesListModel.clear();

            if (!clasesDisponibles.isEmpty()) {
                for (Clase clase : clasesDisponibles) {
                    actividadesListModel.addElement(clase);
                }
                LOGGER.log(Level.INFO, "Se han cargado las actividades correctamente.");
            } else {
                LOGGER.log(Level.INFO, "No hay actividades disponibles para la fecha seleccionada.");
            }

            selectActivityButton.setEnabled(false);
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Error parsing date: " + selectedDateString, e);
            JOptionPane.showMessageDialog(null, "Error al obtener las clases para la fecha seleccionada.");
        }
    }
    
   
    private void actividadSeleccionada() {
        Clase selectedActivity = actividadesList.getSelectedValue();

        if (selectedActivity != null) {
            LOGGER.log(Level.INFO, "Actividad seleccionada para apuntarse: " + selectedActivity);

            int id = selectedActivity.getIDClase();
            int plazasDisponibles = selectedActivity.getPlazas();

            if (plazasDisponibles > 0) {
                int result = JOptionPane.showConfirmDialog(
                    null,
                    "¿Seguro que quieres apuntarte a la clase de " + selectedActivity.getTipoActividad().toString() + " con ID " + selectedActivity.getIDClase(),
                    "Apuntarse a clase",
                    JOptionPane.YES_NO_OPTION
                );

                if (result == JOptionPane.YES_OPTION) {
                   
                    gbd.actualizarPlazas(id, plazasDisponibles - 1);

                    JOptionPane.showMessageDialog(null, "Te has apuntado a la clase correctamente", "Apuntarse a clase", JOptionPane.INFORMATION_MESSAGE);
                    mostrarActividades();  
                }
            } else {
                JOptionPane.showMessageDialog(null, "No hay plazas disponibles para esta clase", "Apuntarse a clase", JOptionPane.WARNING_MESSAGE);
            }
        }
    }


	private void setupExitButton() {
		exitButton = new JButton("Salir");
        exitButton.addActionListener(new ActionListener() {
        

			@Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(exitButton);
                    thisFrame.dispose();
					VentanaUsuario vent = new VentanaUsuario(g, gbd, dniUsuario);
                    vent.setVisible(true);
                }
            }
        });
        exitButton.setPreferredSize(new Dimension(100, 40));
		
	}








	public void mostrarVentana() {
        getContentPane().setVisible(true);
    }
    
    
    
    
   
   
}
