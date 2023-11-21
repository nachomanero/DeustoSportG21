package gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import io.FicheroLogger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentanaEdicionActividades extends JFrame {
    private JTextField actividadTextField;
    private JList<String> actividadesList;
    private DefaultListModel<String> actividadesListModel;
    private List<String> actividades;
    private JButton selectActivityButton;
    private JButton exitButton;
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());

    public VentanaEdicionActividades() {
        JFrame frame = new JFrame("EDICIÓN ACTIVIDADES");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setResizable(false);

        actividades = obtenerListaActividades(); 
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
    }

    private void updateActividadesList() {
        actividadesListModel.clear();

        String typeToFilter = actividadTextField.getText().trim().toLowerCase();

        for (String actividad : actividades) {
            if (actividad.toLowerCase().contains(typeToFilter) || typeToFilter.isEmpty()) {
                actividadesListModel.addElement(actividad);
            }
        }

        boolean actividadesDisponibles = !actividadesListModel.isEmpty();
        selectActivityButton.setEnabled(actividadesDisponibles);
    }

    private void selectActivity() {
        if (!actividadesListModel.isEmpty()) {
            String[] activityOptions = new String[actividadesListModel.size()];
            actividadesListModel.copyInto(activityOptions);

            String selectedActivity = (String) JOptionPane.showInputDialog(null,
                    "Selecciona una actividad:",
                    "Actividades disponibles",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    activityOptions,
                    activityOptions[0]);

            if (selectedActivity != null) {
                JOptionPane.showMessageDialog(null, "Has seleccionado la actividad para editar: " + selectedActivity);
                LOGGER.log(Level.INFO, "Actividad seleccionada para editar: " + selectedActivity);
                VentanaEditarClase ventanaEditar = new VentanaEditarClase();
                ventanaEditar.mostrarVentana();
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
                    VentanaMenuAdmin vent = new VentanaMenuAdmin();
                    vent.setVisible(true);
                }
            }
        });
        exitButton.setPreferredSize(new Dimension(100, 40));
    }

    
    private List<String> obtenerListaActividades() {
        
        List<String> listaActividades = new ArrayList<>();
        listaActividades.add("Yoga");
        listaActividades.add("Pilates");
        listaActividades.add("Spinning");
        return listaActividades;
    }
    

    public void mostrarVentana() {
        getContentPane().setVisible(true);
    }
   

    /*
   public static void main(String[] args) {
	   FicheroLogger ficheroLogger = new FicheroLogger();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaEdicionActividades();
            }
        });
        FicheroLogger.cerrarFileHandler();
    }
    */
    

}


