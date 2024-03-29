package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import db.GestorBD;
import domain.Clase;
import domain.Gestor;
import domain.TipoActividad;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Component;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.Color;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.logging.Level;


import io.FicheroLogger;
public class VentanaAniadirClase extends JFrame {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private String nomfichClases = "resources/data/Clases.csv";

	private Gestor g;
	private GestorBD gbd;
	
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());


	public VentanaAniadirClase(Gestor gestor , GestorBD gestorBD) {
		
		g = gestor;
		gbd = gestorBD;
		
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
		
		JPanel panel_Labels = new JPanel();
		panel_Labels.setBounds(new Rectangle(0, 0, 100000, 0));
		panel.add(panel_Labels);
		panel_Labels.setLayout(new BoxLayout(panel_Labels, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(102, 153, 153));
		panel_Labels.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Tipo de clase:                                      ");
		lblNewLabel_1.setBackground(new Color(0,0,0));
		panel_2.add(lblNewLabel_1);
		
		JComboBox<TipoActividad> comboBox = new JComboBox<TipoActividad>(TipoActividad.values());
		//JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setMaximumRowCount(20);
		panel_2.add(comboBox, BorderLayout.EAST);
		
		
		
		var seleccionCombo = comboBox.getSelectedItem();
		String strTipoClase = seleccionCombo.toString();
		System.out.println(seleccionCombo);
		
		
		//if(seleccionCombo == TipoActividad.FUNCIONAL) {
			//textField_1.setText("1");
		//}
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(102, 153, 153));
		panel_Labels.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Fecha:              ");
		panel_3.add(lblNewLabel_2, BorderLayout.WEST);
		
		
		textField = new JTextField();
		textField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_3.add(textField, BorderLayout.EAST);
		textField.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(102, 153, 153));
		panel_Labels.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_3 = new JLabel("Hora: ");
		panel_4.add(lblNewLabel_3, BorderLayout.WEST);
		
		textField_1 = new JTextField();
		textField_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_4.add(textField_1, BorderLayout.EAST);
		textField_1.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(102, 153, 153));
		panel_Labels.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_4 = new JLabel("Lugar: ");
		panel_5.add(lblNewLabel_4, BorderLayout.WEST);
		
		textField_2 = new JTextField();
		panel_5.add(textField_2, BorderLayout.EAST);
		textField_2.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(102, 153, 153));
		panel_Labels.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_5 = new JLabel("Capacidad: ");
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
		
		JButton btnCrearClase = new JButton("Crear Clase");
		btnCrearClase.setBackground(new Color(192, 192, 192));
		btnCrearClase.setFont(new Font("Arial", Font.BOLD, 13));
		panel_7.add(btnCrearClase);
		
		JButton btnRetroceder = new JButton("Retroceder");
		btnRetroceder.setBackground(new Color(192, 192, 192));
		btnRetroceder.setFont(new Font("Arial", Font.BOLD, 13));
		panel_7.add(btnRetroceder);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBackground(new Color(192, 192, 192));
		btnSalir.setFont(new Font("Arial", Font.BOLD, 13));
		panel_7.add(btnSalir);
		
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
		
		
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TipoActividad tipoClase = (TipoActividad)comboBox.getSelectedItem();
				int claseInd = tipoClase.ordinal() + 1;
				textField_2.setText(String.valueOf(claseInd));
				
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
                    LOGGER.log(Level.INFO, "El usuario ha salido."+result);
                }else {
                	LOGGER.log(Level.SEVERE,"Salida de la aplicacion fallida"+result);
                }
			}
		});
		
		btnRetroceder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnRetroceder);
                thisFrame.dispose();
			
				VentanaMenuAdmin vent = new VentanaMenuAdmin(g , gbd);
				vent.mostrarVentana();
			}
		});
		
		btnCrearClase.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				try {
					String patronHora = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
					String patronFecha = "^(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-\\d{4}$";

					TipoActividad tipoClase = (TipoActividad)comboBox.getSelectedItem();
					int id = gbd.obtenerSiguienteID( "Clase", "idClase");
					String txtFecha = textField.getText();
					java.util.Date fecha = formatoFecha.parse(txtFecha);
					java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());
					String hora = textField_1.getText();
					int lugar =  Integer.parseInt(textField_2.getText());
					int capacidad = (int) spinner.getValue();
					if(Pattern.matches(patronHora, hora)) {
						if(Pattern.matches(patronFecha, txtFecha)) {
							if(capacidad >= 5) {
								Clase cl = new Clase(id,hora,tipoClase,sqlFecha,lugar,capacidad);
								gbd.añadirClase(cl);
								g.agregarClaseACSV(cl, nomfichClases);
								JOptionPane.showMessageDialog(null, "Clase creada correctamente","CREACION DE CLASE",JOptionPane.INFORMATION_MESSAGE);
								JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnCrearClase);
				                thisFrame.dispose();
								VentanaMenuAdmin vent = new VentanaMenuAdmin(g , gbd );
								vent.mostrarVentana();
				                LOGGER.log(Level.INFO, "Se ha creado una sesion correctamente");
							}else {
								textField.setText("");
								JOptionPane.showMessageDialog(null, "El numero minimo de plazas es de 5" ,"CAPACIDAD INCORRECTA",JOptionPane.ERROR_MESSAGE);
								LOGGER.log(Level.WARNING, "Se ha intentado añadir una clase con un numero de plazas incorrecto");
							}
						}else {
							textField.setText("");
							JOptionPane.showMessageDialog(null, "La fecha introducida no es inválida \n El formato es: (dd-MM-yyyy)" ,"FECHA INCORRECTA",JOptionPane.ERROR_MESSAGE);
							LOGGER.log(Level.WARNING, "Se ha intentado añadir una clase con una fecha en formato incorrecto");
						}
					}else {
						textField_1.setText("");
						JOptionPane.showMessageDialog(null, "La hora introducida no es inválida \n El formato es: (hh:mm)" ,"HORA INVÁLIDA",JOptionPane.ERROR_MESSAGE);
						LOGGER.log(Level.WARNING, "Se ha intentado añadir una clase con una hora en formato incorrecto");
					}
					
					
					
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Rellene bien la fecha" ,"FECHA INCORRECTA",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
					System.out.println("Problema con la fecha");
					LOGGER.log(Level.WARNING, "Error haciendo el Parse de la fecha");
				}

			}
		});
	}
	
	
	
	public void mostrarVentana() {
        getContentPane().setVisible(true);
    }
	
	

}
