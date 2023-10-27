package Ventanas;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;

public class VentanaPrincipal {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
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
	public VentanaPrincipal() {
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
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);


		JPanel panelTitulo = new JPanel();
		frame.getContentPane().add(panelTitulo);
		panelTitulo.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panelTitulo.add(panel, BorderLayout.NORTH);
		
		JLabel lblDeustoSport = new JLabel("DEUSTO SPORT");
		lblDeustoSport.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
		panel.add(lblDeustoSport);
		
		JPanel panel_1 = new JPanel();
		panelTitulo.add(panel_1, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		panelTitulo.add(panel_2, BorderLayout.SOUTH);
		
		JPanel panel_3 = new JPanel();
		panelTitulo.add(panel_3, BorderLayout.EAST);
		
		JPanel panel_4 = new JPanel();
		panelTitulo.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.SOUTH);
		
		JButton btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.setFont(new Font("Arial", Font.BOLD, 13));
		btnIniciarSesion.setBackground(new Color(192, 192, 192));
		panel_5.add(btnIniciarSesion);
		
		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.setFont(new Font("Arial", Font.BOLD, 13));
		btnCrearUsuario.setBackground(new Color(192, 192, 192));
		panel_5.add(btnCrearUsuario);
		
		JButton btnSalida = new JButton("Salir");
		btnSalida.setBackground(new Color(192, 192, 192));
		btnSalida.setFont(new Font("Arial", Font.BOLD, 13));
		panel_5.add(btnSalida);
		
		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6, BorderLayout.NORTH);
		
		JLabel lblSubtitulo = new JLabel("Todos los deportes, estan en Deusto Sport");
		lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 13));
		panel_6.add(lblSubtitulo);
		
		JPanel panel_7 = new JPanel();
		panel_4.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon("Fotos/Logo.png"));
		panel_7.add(lblFoto);
		
		JLabel lblTextoSubFoto = new JLabel("Inicia sesion o unete a nosotros!");
		lblTextoSubFoto.setFont(new Font("Arial", Font.BOLD, 15));
		panel_7.add(lblTextoSubFoto);
	}

}
