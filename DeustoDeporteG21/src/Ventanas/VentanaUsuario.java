package Ventanas;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import javax.swing.SwingConstants;

public class VentanaUsuario extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaUsuario frame = new VentanaUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/*

	/**
	 * Create the frame.
	 */
	public VentanaUsuario() {
		
		setResizable(false);
		setBounds(new Rectangle(200, 200, 550, 350));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 550, 350);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(102, 153, 153));
		
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);
		JLabel lblTitulo = new JLabel("MENÚ");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelNorte.add(lblTitulo);
		panelNorte.setBackground(new Color(102, 153, 153));
		
		JPanel panelCentro = new JPanel();
		panelCentro.setLayout(new BorderLayout(0,0));
		contentPane.add(panelCentro, BorderLayout.CENTER);

		
		JPanel panelFotos = new JPanel();
		panelFotos.setLayout(new GridLayout(1, 2, 50, 0));
		panelCentro.add(panelFotos, BorderLayout.CENTER);
		panelFotos.setBackground(new Color(102, 153, 153));
		
		JLabel lblFoto1 = new JLabel("");
		lblFoto1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFoto1.setIcon(new ImageIcon("Fotos/Agenda.png"));
		
		JLabel lblFoto2 = new JLabel("");
		lblFoto2.setHorizontalAlignment(SwingConstants.LEFT);
		lblFoto2.setIcon(new ImageIcon("Fotos/FuturasClases.png"));
		panelFotos.add(lblFoto1);
		panelFotos.add(lblFoto2);		
		
		JPanel panelBotones = new JPanel();
		panelCentro.add(panelBotones, BorderLayout.SOUTH);
		panelBotones.setBackground(new Color(102, 153, 153));
		
		JPanel panelBoton1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelBoton1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		flowLayout_1.setHgap(30);
		panelBotones.add(panelBoton1);
		panelBoton1.setBackground(new Color(102, 153, 153));
		
		JButton btnMiAgenda = new JButton("Mi agenda");
		btnMiAgenda.setFont(new Font("Arial", Font.BOLD, 13));
		panelBoton1.add(btnMiAgenda);
		
		JPanel panelBoton2 = new JPanel();
		panelBotones.add(panelBoton2);
		panelBoton2.setBackground(new Color(102, 153, 153));
		
		JButton btnFutClases = new JButton("Futuras clases");
		btnFutClases.setFont(new Font("Arial", Font.BOLD, 13));
		panelBoton2.add(btnFutClases);
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		panelSur.setBackground(new Color(102, 153, 153));
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(e -> System.exit(0));
		panelSur.add(btnSalir);
		
		
	}

}
