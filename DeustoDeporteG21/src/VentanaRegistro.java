

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;

public class VentanaRegistro {

	private JFrame frame;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JLabel lblTitulo;
	private JButton btnRetroceder;
	private JButton btnAceptar;

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocation(550, 250);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		lblTitulo = new JLabel("Bienvenido! Registrese aqui");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
		panel_1.add(lblTitulo);
		
		panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.EAST);
		
		panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, BorderLayout.SOUTH);
		
		btnRetroceder = new JButton("Retroceder");
		btnRetroceder.setBackground(new Color(192, 192, 192));
		panel_3.add(btnRetroceder);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBackground(new Color(192, 192, 192));
		panel_3.add(btnAceptar);
		
		panel_4 = new JPanel();
		frame.getContentPane().add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
	}
}
