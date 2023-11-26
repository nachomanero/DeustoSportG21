package gui;


import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import db.GestorBD;
import domain.Gestor;
import domain.Usuario;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import io.FicheroLogger;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.logging.Level;
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

    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());
    private JTextField textFieldDireccion;


	
	public VentanaRegistro() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 550, 350);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);;
		frame.setResizable(false);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(102, 153, 153));
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		lblTitulo = new JLabel("Bienvenido! Registrese aqui");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
		panel_1.add(lblTitulo);
		
		panel_3 = new JPanel();
		panel_3.setBackground(new Color(102, 153, 153));
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
		panel.setBackground(new Color(102, 153, 153));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
		lblNombre.setBounds(10, 59, 62, 16);
		panel.add(lblNombre);
		
		JLabel lblNewLabel = new JLabel("Apellido:");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 88, 62, 16);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("DNI:");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 117, 62, 16);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Correo:");
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewLabel_2.setBounds(10, 148, 62, 13);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Contrase\u00F1a:");
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewLabel_3.setBounds(10, 175, 77, 16);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_5 = new JLabel("Direccion:");
		lblNewLabel_5.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewLabel_5.setBounds(10, 204, 77, 16);
		panel.add(lblNewLabel_5);
		
		textFieldContrasenia = new JPasswordField();
		textFieldContrasenia.setFont(new Font("Arial", Font.PLAIN, 10));
		textFieldContrasenia.setBounds(97, 174, 129, 19);
		panel.add(textFieldContrasenia);
		textFieldContrasenia.setColumns(10);
		
		textFieldCorreo = new JTextField();
		textFieldCorreo.setFont(new Font("Arial", Font.PLAIN, 10));
		textFieldCorreo.setBounds(97, 145, 129, 19);
		panel.add(textFieldCorreo);
		textFieldCorreo.setColumns(15);
		
		textFieldDNI = new JTextField();
		textFieldDNI.setFont(new Font("Arial", Font.PLAIN, 10));
		textFieldDNI.setBounds(97, 116, 129, 19);
		panel.add(textFieldDNI);
		textFieldDNI.setColumns(10);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setFont(new Font("Arial", Font.PLAIN, 10));
		textFieldApellido.setBounds(97, 87, 129, 19);
		panel.add(textFieldApellido);
		textFieldApellido.setColumns(10);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setFont(new Font("Arial", Font.PLAIN, 10));
		textFieldNombre.setBounds(97, 58, 129, 19);
		panel.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldDireccion = new JTextField();
		textFieldDireccion.setFont(new Font("Arial", Font.PLAIN, 10));
		textFieldDireccion.setBounds(97, 203, 129, 19);
		panel.add(textFieldDireccion);
		textFieldDireccion.setColumns(10);
		
		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon("resources/images/FotoRegistro.jpg"));
		lblNewLabel_4.setBounds(231, 0, 305, 251);
		panel.add(lblNewLabel_4);
		
		btnAceptar.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        
		    	String nomfichClases = "resources/data/Usuarios.csv";
		        String nombre = textFieldNombre.getText();
		        String apellido = textFieldApellido.getText();
		        String dni = textFieldDNI.getText();
		        String correo = textFieldCorreo.getText();
		        String contrasenia = new String(textFieldContrasenia.getPassword());
		        String direccion = textFieldDireccion.getText();
		        
		        

		        
		        if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || correo.isEmpty() || contrasenia.isEmpty() || direccion.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
	                LOGGER.log(Level.SEVERE, "Se ha intentado registrar un usuario sin tener todos los campos de datos completados.");
		        }
		        else {
		        	
		        	if(dniCorrecto(dni) == true) {
		        		Usuario u = new Usuario(dni, nombre, apellido, direccion, correo, contrasenia);
			        	GestorBD gbd = new GestorBD();
			        	Gestor g = new Gestor();
			        	gbd.añadirUsuario(u);
			            g.añadirUsuarioACSV(u, nomfichClases);
			        	
			            JOptionPane.showMessageDialog(null, "Registro completado correctamente", "REGISTRO", JOptionPane.INFORMATION_MESSAGE);
			            frame.dispose();
		                LOGGER.log(Level.INFO, "Se ha registrado al nuevo usuario correctamente.");


			            VentanaPrincipal vent = new VentanaPrincipal();
			            vent.mostrarVentana();
		        	}else {
		        		JOptionPane.showMessageDialog(null, "El DNI no tiene el formato correcto", "Error de Registro", JOptionPane.ERROR_MESSAGE);
		        		LOGGER.log(Level.SEVERE, "Se ha intentado registrar un usuario con un DNI con formato incorrecto.");
		        	}
		        	
		        	
		        	
		        }
		    }
		});
		
		
		
	
		
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                	JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnSalir);
                    thisFrame.dispose();
                    LOGGER.log(Level.INFO, "El usuario ha salido.");
                }else {
                	LOGGER.log(Level.SEVERE,"Intento de salida fallido.");
                }
				
			}
		});
		
		btnRetroceder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();

                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
                ventanaPrincipal.mostrarVentana();
                LOGGER.log(Level.INFO,"Se ha accedido a la ventana principal.");
			}
		});
	}
	public void mostrarVentana() {
        frame.setVisible(true);
    }
	
	private boolean dniCorrecto(String dni) {
		String patron = "[0-9]{8}[A-Z]";
		return Pattern.matches(patron, dni);
	}
}
