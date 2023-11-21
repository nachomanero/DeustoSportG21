package gui;
import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import io.FicheroLogger;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument.Content;

import io.FicheroLogger;

import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.util.logging.Logger;
import java.util.logging.Level;
public class VentanaPrincipal {

	private JFrame frame;
	private JPanel contentPane;
	private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());

	

	/**
	 * Launch the application.
	 */
	/*
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
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);


		JPanel panelTitulo = new JPanel();
		frame.getContentPane().add(panelTitulo);
		panelTitulo.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 153, 153));
		panelTitulo.add(panel, BorderLayout.NORTH);
		
		JLabel lblDeustoSport = new JLabel("DEUSTO SPORT");
		lblDeustoSport.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
		panel.add(lblDeustoSport);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(102, 153, 153));
		panelTitulo.add(panel_1, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(102, 153, 153));
		panelTitulo.add(panel_2, BorderLayout.SOUTH);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(102, 153, 153));
		panelTitulo.add(panel_3, BorderLayout.EAST);
		
		JPanel panel_4 = new JPanel();
		panelTitulo.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(102, 153, 153));
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
		panel_6.setBackground(new Color(102, 153, 153));
		panel_4.add(panel_6, BorderLayout.NORTH);
		
		JLabel lblSubtitulo = new JLabel("Todos los deportes, estan en Deusto Sport");
		lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 13));
		panel_6.add(lblSubtitulo);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(102, 153, 153));
		panel_4.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon("resources/images/Logo.png"));
		panel_7.add(lblFoto);
		
		JLabel lblTextoSubFoto = new JLabel("Inicia sesion o unete a nosotros!");
		lblTextoSubFoto.setFont(new Font("Arial", Font.BOLD, 15));
		panel_7.add(lblTextoSubFoto);
		
		frame.addKeyListener(new KeyListener() {
		    private boolean ctrlPressed = false;

		    @Override
		    public void keyTyped(KeyEvent e) {
		        
		    }

		    @Override
		    public void keyPressed(KeyEvent e) {
		        
		        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
		            ctrlPressed = true;
		        }

		        
		        if (ctrlPressed && e.getKeyCode() == KeyEvent.VK_SPACE) {
		        	LOGGER.log(Level.INFO, "CTRL + SPACE pressed");
		            VentanaMenuAdmin ventanaMenuAdmin = new VentanaMenuAdmin();
		            ventanaMenuAdmin.mostrarVentana();
		        }
		    }

		    @Override
		    public void keyReleased(KeyEvent e) {
		        
		        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
		            ctrlPressed = false;
		        }
		    }
		});

		
		frame.setFocusable(true);
		frame.requestFocus();
		
		btnCrearUsuario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
                frame.dispose();

                VentanaRegistro ventanaRegistro = new VentanaRegistro();
                ventanaRegistro.mostrarVentana();
				
			}
		});
		
		
		btnIniciarSesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();

                VentanaInicioSesion ventanaInicioSesion = new VentanaInicioSesion();
                ventanaInicioSesion.mostrarVentana();
                LOGGER.log(Level.INFO, "botón inicio sesión pressed");
			}
		});
		
		btnSalida.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                	JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnSalida);
                    thisFrame.dispose();
                }
			}
		});
		LOGGER.log(Level.INFO, "botón salida pressed");
	}
	public void mostrarVentana() {
        frame.setVisible(true);
    }
	
	
	


}
