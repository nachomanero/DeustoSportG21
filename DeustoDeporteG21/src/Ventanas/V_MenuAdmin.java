package Ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Rectangle;

public class V_MenuAdmin extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					V_MenuAdmin frame = new V_MenuAdmin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public V_MenuAdmin() {
		
		setResizable(false);
		setBounds(new Rectangle(200, 200, 550, 350));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 550, 350);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Centro = new JPanel();
		contentPane.add(panel_Centro, BorderLayout.CENTER);
		
		JPanel panel_Centro_Dcha = new JPanel();
		FlowLayout fl_panel_Centro_Dcha = (FlowLayout) panel_Centro_Dcha.getLayout();
		fl_panel_Centro_Dcha.setVgap(70);
		panel_Centro.add(panel_Centro_Dcha);
		
		JButton btnAniadirClase = new JButton("Añadir Clase");
		panel_Centro_Dcha.add(btnAniadirClase);
		
		JPanel panel_Centro_Izq = new JPanel();
		panel_Centro.add(panel_Centro_Izq);
		
		JButton btnModClase = new JButton("Modificar Clase");
		panel_Centro_Izq.add(btnModClase);
		
		JPanel panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JLabel lblTitulo = new JLabel("MENÚ DE ADMINISTRADOR");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelNorte.add(lblTitulo);
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		JButton btnSalir = new JButton("SALIR");
		panelSur.add(btnSalir);
	}

}
