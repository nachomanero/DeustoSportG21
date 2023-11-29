package gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import db.GestorBD;
import domain.Clase;
import domain.Gestor;
import domain.TipoActividad;
import io.FicheroLogger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentanaEdicionActividades extends JFrame {
    private JTextField actividadTextField;
    private JList<Clase> actividadesList;
    private DefaultListModel<Clase> actividadesListModel;
    private List<Clase> actividades;
    private JButton selectActivityButton;
    private JButton exitButton;
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());

    private Gestor g;
    private GestorBD gbd;
    
    public VentanaEdicionActividades( Gestor gestor , GestorBD gestorBD ) {
    	
    	g = gestor;
    	gbd = gestorBD;
    	
        JFrame frame = new JFrame("EDICIÓN ACTIVIDADES");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel actividadLabel = new JLabel("Filtrar por tipo de actividad:");
        topPanel.add(actividadLabel);

        actividadTextField = new JTextField(15);
        actividadTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateActividadesList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateActividadesList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateActividadesList();
            }
        });
        topPanel.add(actividadTextField);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        actividadesListModel = new DefaultListModel<>();
        actividadesList = new JList<>(actividadesListModel);
        JScrollPane scrollPane = new JScrollPane(actividadesList);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        selectActivityButton = new JButton("Editar");
        selectActivityButton.addActionListener(e -> selectActivity());

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

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

        frame.getContentPane().add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        GestorBD gbd = new GestorBD();

        List<Clase> todasLasClases = gbd.obtenerTodasLasClases();
        actividades = todasLasClases;
        actividadesListModel = new DefaultListModel<>();
        for (Clase actividad : actividades) {
            actividadesListModel.addElement(actividad);
        }

        actividadesList.setModel(actividadesListModel);

        actividadesList.addListSelectionListener(e -> {
            boolean elemSelect = !actividadesList.isSelectionEmpty();
            selectActivityButton.setEnabled(elemSelect);
        });
    }

    private void updateActividadesList() {
        actividadesListModel.clear();

        String typeToFilter = actividadTextField.getText().trim().toLowerCase();

        for (Clase actividad : actividades) {
            if (tipoActividadCoincide(actividad, typeToFilter) || typeToFilter.isEmpty()) {
                actividadesListModel.addElement(actividad);
                LOGGER.log(Level.INFO, "Se ha añadido una actividad correctamente" + actividad);
            } else {
                LOGGER.log(Level.SEVERE, "Ha ocurrido un error al añadir la actividad" + actividad);
            }
        }
    }

    private boolean tipoActividadCoincide(Clase actividad, String tipoToFilter) {
        // Implementa la lógica para verificar si el tipo de actividad coincide
        // Puedes acceder al tipo de actividad con actividad.getTipoActividad()
        // y compararlo con tipoToFilter
        return actividad.getTipoActividad().toString().toLowerCase().contains(tipoToFilter);
    }

    private void selectActivity() {
        Clase selectedActivity = actividadesList.getSelectedValue();

        if (selectedActivity != null) {
            LOGGER.log(Level.INFO, "Actividad seleccionada para editar: " + selectedActivity);

            // Crear una instancia de VentanaEditarClase y mostrarla
            VentanaEditarClase ventanaEditar = new VentanaEditarClase(g , gbd , selectedActivity);
            ventanaEditar.mostrarVentana();
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
                    VentanaMenuAdmin vent = new VentanaMenuAdmin(g , gbd );
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



