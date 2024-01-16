package gui;

import java.awt.EventQueue;

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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.Color;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.logging.Level;


import io.FicheroLogger;

public class VentanaEditarClase extends JFrame {

	private JPanel contentPane;
	private JTextField txtFecha;
	private JTextField txtHora;
	private JTextField txtLugar;
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());
	
    private Gestor g;
    private GestorBD gbd;
    
	public VentanaEditarClase(Gestor gestor , GestorBD gestorBD , Clase clase) {
		
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
		panel_Labels.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Tipo de clase: ");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
		panel_2.add(lblNewLabel_1);
		
		JComboBox<TipoActividad> comboBox = new JComboBox<TipoActividad>(TipoActividad.values());
		comboBox.setMaximumRowCount(20);
		panel_2.add(comboBox, BorderLayout.EAST);
		comboBox.setSelectedItem(clase.getTipoActividad());
		
		JPanel panel_3 = new JPanel();
		panel_Labels.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Fecha: ");
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
		panel_3.add(lblNewLabel_2, BorderLayout.WEST);
		
		txtFecha = new JTextField();
		txtFecha.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_3.add(txtFecha, BorderLayout.EAST);
		txtFecha.setColumns(10);
		txtFecha.setText(new SimpleDateFormat("dd-MM-yyyy").format(clase.getFecha()));
		
		JPanel panel_4 = new JPanel();
		panel_Labels.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_3 = new JLabel("Hora: ");
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 13));
		panel_4.add(lblNewLabel_3, BorderLayout.WEST);
		
		txtHora = new JTextField();
		txtHora.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_4.add(txtHora, BorderLayout.EAST);
		txtHora.setColumns(10);
		txtHora.setText(clase.getHora());
		
		JPanel panel_5 = new JPanel();
		panel_Labels.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_4 = new JLabel("Lugar: ");
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 13));
		panel_5.add(lblNewLabel_4, BorderLayout.WEST);
		
		txtLugar = new JTextField();
		panel_5.add(txtLugar, BorderLayout.EAST);
		txtLugar.setColumns(10);
		txtLugar.setText(String.valueOf(clase.getIDClase()));
		
		JPanel panel_6 = new JPanel();
		panel_Labels.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_5 = new JLabel("Capacidad: ");
		lblNewLabel_5.setFont(new Font("Arial", Font.BOLD, 13));
		panel_6.add(lblNewLabel_5, BorderLayout.WEST);
		
		SpinnerModel spinnerModel = new SpinnerNumberModel(clase.getPlazas(), 0, 40, 1);
		JSpinner spinnerCapacidad = new JSpinner(spinnerModel);
		panel_6.add(spinnerCapacidad, BorderLayout.EAST);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(102, 153, 153));
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("DATOS DE LA CLASE");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_1.add(lblNewLabel);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(102, 153, 153));
		contentPane.add(panel_7, BorderLayout.SOUTH);
		
		JButton btnEditarClase = new JButton("Editar Clase");
		btnEditarClase.setBackground(new Color(192, 192, 192));
		btnEditarClase.setFont(new Font("Arial", Font.BOLD, 13));
		panel_7.add(btnEditarClase);
		
		JButton btnRetroceder = new JButton("Retroceder");
		btnRetroceder.setBackground(new Color(192, 192, 192));
		btnRetroceder.setFont(new Font("Arial", Font.BOLD, 13));
		panel_7.add(btnRetroceder);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBackground(new Color(192, 192, 192));
		btnSalir.setFont(new Font("Arial", Font.BOLD, 13));
		panel_7.add(btnSalir);
		
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
		String nomfichClases = "resources/data/Clases.csv";
		
		
		
		btnRetroceder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnRetroceder);
                thisFrame.dispose();
			
				VentanaMenuAdmin vent = new VentanaMenuAdmin(g , gbd );
				vent.mostrarVentana();
				
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
                    LOGGER.log(Level.INFO,"Se ha salido de la aplicacion."+result);
                }
			}
		});
    

		
		btnEditarClase.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GestorBD gbd = new GestorBD();
				Gestor g = new Gestor();
				
				try {
					String patronHora = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
					String patronFecha = "^(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-\\d{4}$";
					
					String hora = txtHora.getText();
					TipoActividad tipoClase = (TipoActividad)comboBox.getSelectedItem();
					String fecha = txtFecha.getText();
					java.util.Date fecha1 = formatoFecha.parse(fecha);
					java.sql.Date sqlFecha = new java.sql.Date(fecha1.getTime());
					int lugar =  Integer.parseInt(txtLugar.getText());
					int capacidad = (int) spinnerCapacidad.getValue();
					
					if(Pattern.matches(patronHora, hora)) {
						if(Pattern.matches(patronFecha, fecha)) {
							Clase cl = new Clase(clase.getIDClase(),hora,tipoClase,sqlFecha,lugar,capacidad);
							gbd.editarClase(cl);
							g.actualizarClaseEnCSV(cl, nomfichClases);
							
							JOptionPane.showMessageDialog(null, "Clase modificada correctamente","EDICION DE CLASE",JOptionPane.INFORMATION_MESSAGE);
							JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnEditarClase);
			                thisFrame.dispose();
						
							VentanaMenuAdmin vent = new VentanaMenuAdmin(g , gbd );
							vent.mostrarVentana();
							LOGGER.log(Level.INFO,"Se ha modificadp una clase satisfactoriamente.");
						}else {
							txtFecha.setText("");
							JOptionPane.showMessageDialog(null, "La fecha introducida no es inválida \n El formato es: (dd-MM-yyyy)" ,"FECHA INCORRECTA",JOptionPane.ERROR_MESSAGE);
							LOGGER.log(Level.WARNING, "Se ha intentado añadir una clase con una fecha en formato incorrecto");
						}
						
					}else {
						txtHora.setText("");
						JOptionPane.showMessageDialog(null, "La hora introducida no es inválida \n El formato es: (hh:mm)" ,"HORA INVÁLIDA",JOptionPane.ERROR_MESSAGE);
						LOGGER.log(Level.WARNING, "Se ha intentado añadir una clase con una hora en formato incorrecto");
					}
					
					
					
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}    
				
				
				
				
			}
		});
	}

	public void mostrarVentana() {
		// TODO Auto-generated method stub
		getContentPane().setVisible(true);
	}
	





}
