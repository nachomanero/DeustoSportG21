package Ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.FlowLayout;
import javax.swing.ImageIcon;

public class VentanaInicioSesion {

	JFrame frame;
	private JPasswordField txtContrasenia;
	private JTextField textFieldCorreo;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInicioSesion window = new VentanaInicioSesion();
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
	public VentanaInicioSesion() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 550, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setLocation(500, 250);
		JPanel panelSur = new JPanel();
		panelSur.setBackground(new Color(102, 153, 153));
		frame.getContentPane().add(panelSur, BorderLayout.SOUTH);
		frame.setResizable(false);
		JButton btnRetroceder = new JButton("Retroceder");
		btnRetroceder.setBackground(new Color(192, 192, 192));
		btnRetroceder.setFont(new Font("Arial", Font.ITALIC, 13));
		panelSur.add(btnRetroceder);
		
		JButton btnAcceder = new JButton("Acceder");
		btnAcceder.setBackground(new Color(192, 192, 192));
		btnAcceder.setFont(new Font("Arial", Font.ITALIC, 13));
		panelSur.add(btnAcceder);
		
		JPanel panelOeste = new JPanel();
		panelOeste.setBackground(new Color(102, 153, 153));
		frame.getContentPane().add(panelOeste, BorderLayout.WEST);
		
		JPanel panelNorte = new JPanel();
		panelNorte.setBackground(new Color(102, 153, 153));
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);
		
		JLabel lblTitulo = new JLabel("Inicio Sesion");
		lblTitulo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
		panelNorte.add(lblTitulo);
		
		JPanel panelEste = new JPanel();
		panelEste.setBackground(new Color(102, 153, 153));
		frame.getContentPane().add(panelEste, BorderLayout.EAST);
		
		JPanel panelCentro = new JPanel();
		frame.getContentPane().add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCentroSur = new JPanel();
		panelCentroSur.setBackground(new Color(102, 153, 153));
		panelCentro.add(panelCentroSur, BorderLayout.SOUTH);
		panelCentroSur.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblContrasenia = new JLabel("Contrase\u00F1a:");
		lblContrasenia.setFont(new Font("Arial", Font.ITALIC, 13));
		panelCentroSur.add(lblContrasenia);
		
		txtContrasenia = new JPasswordField();
		txtContrasenia.setFont(new Font("Arial", Font.ITALIC, 9));
		txtContrasenia.setText("");
		panelCentroSur.add(txtContrasenia);
		txtContrasenia.setColumns(15);
		
		JPanel panel = new JPanel();
		panelCentro.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(102, 153, 153));
		panel.add(panel_1, BorderLayout.SOUTH);
		
		JLabel lblCorreo = new JLabel("Correo:");
		lblCorreo.setFont(new Font("Arial", Font.ITALIC, 13));
		panel_1.add(lblCorreo);
		
		textFieldCorreo = new JTextField();
		textFieldCorreo.setText("");
		panel_1.add(textFieldCorreo);
		textFieldCorreo.setColumns(15);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(102, 153, 153));
		panel_2.add(panel_3, BorderLayout.WEST);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(102, 153, 153));
		panel_2.add(panel_4, BorderLayout.NORTH);
		
		JLabel lblNewLabel_1 = new JLabel("Bienvenido al Inicio de sesion de DeustoSport!");
		panel_4.add(lblNewLabel_1);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(102, 153, 153));
		panel_2.add(panel_5, BorderLayout.SOUTH);
		
		JLabel lblNewLabel = new JLabel("");
		panel_5.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon("Fotos/ImagenInicioSesion.jpg"));
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(102, 153, 153));
		panel_2.add(panel_6, BorderLayout.EAST);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(102, 153, 153));
		panel_2.add(panel_7, BorderLayout.CENTER);
		
		

		btnAcceder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Bienvenido!!","INICIO DE SESIÓN",JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
				
				
			}
		});
		
		
		btnRetroceder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();

                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
                ventanaPrincipal.mostrarVentana();
				
			}
		});
	}
	
	public void mostrarVentana() {
        frame.setVisible(true);
    }

}
