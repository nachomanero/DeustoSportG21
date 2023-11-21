package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.Color;
import java.util.logging.Logger;
import java.util.logging.Level;


import io.FicheroLogger;

public class VentanaEditarClase extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());
	
	public VentanaEditarClase() {
		setBackground(new Color(102, 153, 153));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		
		setResizable(false);
		setBounds(200, 200, 550, 350);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 153, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 153, 153));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 65));
		
		JLabel lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon("resources/images/FotoClases.jpg"));
		panel.add(lblFoto);
		
		JPanel panel_Labels = new JPanel();
		panel_Labels.setBounds(new Rectangle(0, 0, 100000, 0));
		panel.add(panel_Labels);
		panel_Labels.setLayout(new BoxLayout(panel_Labels, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_Labels.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Tipo de clase: ");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
		panel_2.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setMaximumRowCount(20);
		panel_2.add(comboBox, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		panel_Labels.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Fecha: ");
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
		panel_3.add(lblNewLabel_2, BorderLayout.WEST);
		
		textField = new JTextField();
		textField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_3.add(textField, BorderLayout.EAST);
		textField.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_Labels.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_3 = new JLabel("Hora: ");
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 13));
		panel_4.add(lblNewLabel_3, BorderLayout.WEST);
		
		textField_1 = new JTextField();
		textField_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_4.add(textField_1, BorderLayout.EAST);
		textField_1.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		panel_Labels.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_4 = new JLabel("Lugar: ");
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 13));
		panel_5.add(lblNewLabel_4, BorderLayout.WEST);
		
		textField_2 = new JTextField();
		panel_5.add(textField_2, BorderLayout.EAST);
		textField_2.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		panel_Labels.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_5 = new JLabel("Capacidad: ");
		lblNewLabel_5.setFont(new Font("Arial", Font.BOLD, 13));
		panel_6.add(lblNewLabel_5, BorderLayout.WEST);
		
		JSpinner spinner = new JSpinner();
		panel_6.add(spinner, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(102, 153, 153));
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("DATOS DE LA CLASE");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_1.add(lblNewLabel);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(102, 153, 153));
		contentPane.add(panel_7, BorderLayout.SOUTH);
		
		JButton btnEditarClase = new JButton("Editar Clase");
		btnEditarClase.setBackground(new Color(192, 192, 192));
		btnEditarClase.setFont(new Font("Arial", Font.BOLD, 13));
		panel_7.add(btnEditarClase);
		
		JButton btnRetroceder = new JButton("Retroceder");
		btnRetroceder.setBackground(new Color(192, 192, 192));
		btnRetroceder.setFont(new Font("Arial", Font.BOLD, 13));
		panel_7.add(btnRetroceder);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBackground(new Color(192, 192, 192));
		btnSalir.setFont(new Font("Arial", Font.BOLD, 13));
		panel_7.add(btnSalir);
		
		
		btnRetroceder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnRetroceder);
                thisFrame.dispose();
			
				VentanaMenuAdmin vent = new VentanaMenuAdmin();
				vent.mostrarVentana();
				
			}
		});
		
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int result = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                	JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnSalir);
                    thisFrame.dispose();
                    LOGGER.log(Level.INFO,"Se ha salido de la aplicacion."+result);
                }
			}
		});
		
		btnEditarClase.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Clase modificada correctamente","EDICION DE CLASE",JOptionPane.INFORMATION_MESSAGE);
				JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnEditarClase);
                thisFrame.dispose();
			
				VentanaMenuAdmin vent = new VentanaMenuAdmin();
				vent.mostrarVentana();
				LOGGER.log(Level.INFO,"Se ha modificadp una clase satisfactoriamente.");
			}
		});
	}
	public void mostrarVentana() {
        getContentPane().setVisible(true);
    }

}
