package Ventanas;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.ImageIcon;

public class VentanaRegistro {

	private JFrame frame;
	private JPanel panel_1;
	private JPanel panel_3;
	private JLabel lblTitulo;
	private JButton btnRetroceder;
	private JButton btnAceptar;
	private JPanel panel;
	private JPasswordField textFieldContrasenia;
	private JTextField textFieldCorreo;
	private JTextField textFieldDNI;
	private JTextField textFieldApellido;
	private JTextField textFieldNombre;
	private JLabel lblNewLabel_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistro window = new VentanaRegistro();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaRegistro() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 550, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);;
		frame.setResizable(false);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(104, 221, 169));
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		lblTitulo = new JLabel("Bienvenido! Registrese aqui");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
		panel_1.add(lblTitulo);
		
		panel_3 = new JPanel();
		panel_3.setBackground(new Color(104, 221, 169));
		frame.getContentPane().add(panel_3, BorderLayout.SOUTH);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Arial", Font.BOLD, 13));
		btnAceptar.setBackground(new Color(192, 192, 192));
		panel_3.add(btnAceptar);
		
		btnRetroceder = new JButton("Retroceder");
		btnRetroceder.setFont(new Font("Arial", Font.BOLD, 13));
		btnRetroceder.setBackground(new Color(192, 192, 192));
		panel_3.add(btnRetroceder);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBackground(new Color(192, 192, 192));
		btnSalir.setFont(new Font("Arial", Font.BOLD, 13));
		panel_3.add(btnSalir);
		
		panel = new JPanel();
		panel.setBackground(new Color(104, 221, 169));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
		lblNombre.setBounds(10, 73, 62, 16);
		panel.add(lblNombre);
		
		JLabel lblNewLabel = new JLabel("Apellido:");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 99, 62, 16);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("DNI:");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 128, 62, 16);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Correo:");
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewLabel_2.setBounds(10, 159, 62, 13);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Contrase\u00F1a:");
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewLabel_3.setBounds(10, 186, 77, 16);
		panel.add(lblNewLabel_3);
		
		textFieldContrasenia = new JPasswordField();
		textFieldContrasenia.setFont(new Font("Arial", Font.PLAIN, 10));
		textFieldContrasenia.setBounds(97, 185, 124, 19);
		panel.add(textFieldContrasenia);
		textFieldContrasenia.setColumns(10);
		
		textFieldCorreo = new JTextField();
		textFieldCorreo.setFont(new Font("Arial", Font.PLAIN, 10));
		textFieldCorreo.setBounds(97, 156, 124, 19);
		panel.add(textFieldCorreo);
		textFieldCorreo.setColumns(15);
		
		textFieldDNI = new JTextField();
		textFieldDNI.setFont(new Font("Arial", Font.PLAIN, 10));
		textFieldDNI.setBounds(97, 127, 124, 19);
		panel.add(textFieldDNI);
		textFieldDNI.setColumns(10);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setFont(new Font("Arial", Font.PLAIN, 10));
		textFieldApellido.setBounds(97, 98, 124, 19);
		panel.add(textFieldApellido);
		textFieldApellido.setColumns(10);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setFont(new Font("Arial", Font.PLAIN, 10));
		textFieldNombre.setBounds(97, 69, 124, 19);
		panel.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon("Fotos/FotoRegistro.jpg"));
		lblNewLabel_4.setBounds(231, 0, 305, 251);
		panel.add(lblNewLabel_4);
	}
}
