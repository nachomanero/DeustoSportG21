package Ventanas;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VentanaEdicionActividades {
    private JDateChooser dateChooser;
    private JPanel eventPanel;
    private Map<Date, String> events;
    private boolean dateSelected = false;
    private boolean activitySelected = false;
    private JButton selectActivityButton;

    public VentanaEdicionActividades() {
        JFrame frame = new JFrame("EDICIÓN ACTIVIDADES");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setResizable(false);

        events = new HashMap<>();

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel selectDateLabel = new JLabel("Seleccionar fecha:");
        topPanel.add(selectDateLabel);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setPreferredSize(new Dimension(150, dateChooser.getPreferredSize().height));
        topPanel.add(dateChooser);

        JButton showEventsButton = new JButton("Mostrar actividades disponibles para editar");
        showEventsButton.addActionListener(e -> {
            if (dateSelected) {
                showEventsForSelectedDate();
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una fecha antes para poder editarla.");
            }
        });
        topPanel.add(showEventsButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        eventPanel = new JPanel();
        eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(eventPanel);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        selectActivityButton = new JButton("Editar");
        selectActivityButton.addActionListener(e -> {
            if (activitySelected) {
                selectActivity();
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una actividad antes de editar.");
            }
        });
        selectActivityButton.setPreferredSize(new Dimension(100, 40));
        selectActivityButton.setEnabled(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(selectActivityButton, gbc);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        dateChooser.getDateEditor().addPropertyChangeListener("date", evt -> {
            if (dateChooser.getDate() != null) {
                dateSelected = true;
            } else {
                dateSelected = false;
            }
        });

        frame.getContentPane().add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showEventsForSelectedDate() {
        if (!dateSelected) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fecha antes de mostrar actividades.");
            return;
        }

        Date selectedDate = dateChooser.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDateString = sdf.format(selectedDate);

        String eventsForDate = events.get(selectedDate);
        eventPanel.removeAll();

        if (eventsForDate != null) {
            JTextArea textArea = new JTextArea(eventsForDate);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setOpaque(false);
            textArea.setEditable(false);
            eventPanel.add(new JScrollPane(textArea));
            activitySelected = true;
        } else {
            JTextArea textArea = new JTextArea("No hay actividades para esta fecha.");
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setOpaque(false);
            textArea.setEditable(false);
            eventPanel.add(new JScrollPane(textArea));
            activitySelected = false;
        }

        eventPanel.revalidate();
        eventPanel.repaint();
        selectActivityButton.setEnabled(activitySelected);
    }

    private void selectActivity() {
        if (activitySelected) {
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
                    JOptionPane.showMessageDialog(null, "Has seleccionado la actividad para editar: " + selectedActivity);
                }
            }
        }
    }


}
