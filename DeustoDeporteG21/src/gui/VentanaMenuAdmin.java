package gui;

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
import java.awt.Color;

public class VentanaMenuAdmin extends JFrame {

	private JPanel contentPane;


	public VentanaMenuAdmin() {
		setBackground(new Color(102, 153, 153));
		
		setResizable(false);
		setBounds(new Rectangle(200, 200, 550, 350));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 550, 350);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 153, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Centro = new JPanel();
		panel_Centro.setBackground(new Color(102, 153, 153));
		contentPane.add(panel_Centro, BorderLayout.CENTER);
		
		JPanel panel_Centro_Dcha = new JPanel();
		panel_Centro_Dcha.setBackground(new Color(102, 153, 153));
		FlowLayout fl_panel_Centro_Dcha = (FlowLayout) panel_Centro_Dcha.getLayout();
		fl_panel_Centro_Dcha.setVgap(70);
		panel_Centro.add(panel_Centro_Dcha);
		
		JButton btnAniadirClase = new JButton("Añadir Clase");
		btnAniadirClase.setBackground(new Color(192, 192, 192));
		btnAniadirClase.setFont(new Font("Arial", Font.BOLD, 13));
		panel_Centro_Dcha.add(btnAniadirClase);
		
		JPanel panel_Centro_Izq = new JPanel();
		panel_Centro_Izq.setBackground(new Color(102, 153, 153));
		panel_Centro.add(panel_Centro_Izq);
		
		JButton btnModClase = new JButton("Modificar Clase");
		btnModClase.setBackground(new Color(192, 192, 192));
		btnModClase.setFont(new Font("Arial", Font.BOLD, 13));
		panel_Centro_Izq.add(btnModClase);
		
		JPanel panelNorte = new JPanel();
		panelNorte.setBackground(new Color(102, 153, 153));
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JLabel lblTitulo = new JLabel("MENÚ DE ADMINISTRADOR");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelNorte.add(lblTitulo);
		
		JPanel panelSur = new JPanel();
		panelSur.setBackground(new Color(102, 153, 153));
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		JButton btnSalir = new JButton("SALIR");
		btnSalir.setBackground(new Color(192, 192, 192));
		btnSalir.setFont(new Font("Arial", Font.BOLD, 13));
		panelSur.add(btnSalir);
	}

}
