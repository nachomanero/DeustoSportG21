package gui;

import javax.swing.JFrame;
import io.FicheroLogger;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import db.GestorBD;
import domain.Gestor;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.util.logging.Logger;
import java.util.logging.Level;
public class VentanaMenuAdmin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());

private Gestor g;
private GestorBD gbd;
    
	public VentanaMenuAdmin( Gestor gestor , GestorBD gestorBD ) {
		
		g = gestor;
		gbd = gestorBD;
		
		setBackground(new Color(102, 153, 153));
		
		setVisible(true);
		setResizable(false);
		setBounds(new Rectangle(200, 200, 550, 350));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		
		JButton btnEliminarClase = new JButton("Eliminar Clase");
		btnEliminarClase.setBackground(new Color(192, 192, 192));
		btnEliminarClase.setFont(new Font("Arial", Font.BOLD, 13));
		panel_Centro_Dcha.add(btnEliminarClase);
		
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
		
		addKeyListener(new KeyListener() {
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
		        	JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnModClase);
	                thisFrame.dispose();
		            VentanaEstadisticas ventanaEstadisticas = new VentanaEstadisticas(g, gestorBD );
		            mostrarVentana();		       
		            ventanaEstadisticas.requestFocus();
		            LOGGER.log(Level.INFO,"El admin ha accedido a las estadisticas.");
		          
		        }
		    }

		    @Override
		    public void keyReleased(KeyEvent e) {
		        
		        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
		            ctrlPressed = false;
		        }
		    }
		});
		
		setFocusable(true);
		requestFocus();
		
		
	

		
		btnAniadirClase.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnModClase);
                thisFrame.dispose();
                
                VentanaAniadirClase vent = new VentanaAniadirClase( g , gbd );
                vent.mostrarVentana();
                LOGGER.log(Level.INFO,"El admin ha accedido al apartado de anñadir clase.");
				
			}
		});
		
		btnEliminarClase.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnModClase);
                thisFrame.dispose();
                
                VentanaEliminarActividades vent = new VentanaEliminarActividades( g , gbd );
                vent.mostrarVentana();
                LOGGER.log(Level.INFO,"Se va a acceder a eliminar una clase.");
			}
		});
		
		btnModClase.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnModClase);
                thisFrame.dispose();
			
				VentanaEdicionActividades vent = new VentanaEdicionActividades(g , gbd );
				vent.mostrarVentana();
				LOGGER.log(Level.INFO,"Se va a acceder a editar una clase.");
			}
		});
		
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                	JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnSalir);
                    thisFrame.dispose();
                    LOGGER.log(Level.INFO,"Se procede a salir"+ result);
			}}
		});
	}
	
	public void mostrarVentana() {
        getContentPane().setVisible(true);
    }
	


}
