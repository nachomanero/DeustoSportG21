package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.FlowLayout;

public class VentanaAgenda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<String> activityListModel = new DefaultListModel<>();
	private JList<String> activityList = new JList<>(activityListModel);

	/**
	 * Launch the application.
	 */

	public VentanaAgenda() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		
		contentPane.setLayout(new BorderLayout(0, 0));
		setBounds(200, 200, 550, 350);
		contentPane.setBackground(new Color(102, 153, 153));
		
		setLocationRelativeTo(null);
		setVisible(true);
		//setResizable(false);
		
		JPanel panelCentro = new JPanel();
		panelCentro.setBackground(new Color(102, 153, 153));
		panelCentro.setLayout(new GridLayout(2,1));
		
		
		// Un panel muestra la lista de las clases a las que estamos apuntados, pudiendo asi clickar en una
		// para mas informacion de ella
		JScrollPane scrollPaneMisClases = new JScrollPane();
		scrollPaneMisClases.setBackground(new Color(102, 153, 153));
		scrollPaneMisClases.setBorder(new TitledBorder("Mis clases"));
		panelCentro.add(scrollPaneMisClases);
		
		
		// La informacion de dicha clase y el boton de desapuntarse apareceran al pulsar en una clase
		JPanel panelClase = new JPanel();
		panelClase.setBackground(new Color(102, 153, 153));
		panelCentro.add(panelClase);
		panelClase.setLayout(new BorderLayout(0, 0));
		panelClase.setBorder(new TitledBorder("")); // La clase seleccionada como titulo
		panelClase.setVisible(false);
	
		

		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(new Color(102, 153, 153));
		panelClase.add(panelInfo, BorderLayout.CENTER);
		panelInfo.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTipoClase = new JPanel();
		FlowLayout fl_panelTipoClase = (FlowLayout) panelTipoClase.getLayout();
		fl_panelTipoClase.setAlignment(FlowLayout.LEFT);
		panelInfo.add(panelTipoClase, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Tipo de clase:");
		panelTipoClase.add(lblNewLabel);
		
		JLabel lblTipoClase = new JLabel("");
		panelTipoClase.add(lblTipoClase);
		
		JPanel panel_2 = new JPanel();
		panelInfo.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFecha = new JPanel();
		FlowLayout fl_panelFecha = (FlowLayout) panelFecha.getLayout();
		fl_panelFecha.setAlignment(FlowLayout.LEFT);
		panel_2.add(panelFecha, BorderLayout.NORTH);
		
		JLabel lblNewLabel_2 = new JLabel("Fecha:");
		panelFecha.add(lblNewLabel_2);
		
		JLabel lblFecha = new JLabel("");
		panelFecha.add(lblFecha);
		
		JPanel panelHora = new JPanel();
		FlowLayout fl_panelHora = (FlowLayout) panelHora.getLayout();
		fl_panelHora.setAlignment(FlowLayout.LEFT);
		panel_2.add(panelHora, BorderLayout.CENTER);
		
		JLabel lblNewLabel_4 = new JLabel("Hora:");
		panelHora.add(lblNewLabel_4);
		
		JLabel lblHora = new JLabel("");
		panelHora.add(lblHora);
		
		JPanel panelLugar = new JPanel();
		FlowLayout fl_panelLugar = (FlowLayout) panelLugar.getLayout();
		fl_panelLugar.setAlignment(FlowLayout.LEFT);
		panel_2.add(panelLugar, BorderLayout.SOUTH);
		
		JLabel lblNewLabel_6 = new JLabel("Lugar:");
		panelLugar.add(lblNewLabel_6);
		
		JLabel lblLugar = new JLabel("");
		panelLugar.add(lblLugar);
		
		JPanel panelBoton = new JPanel();
		panelBoton.setBackground(new Color(102, 153, 153));
		panelClase.add(panelBoton, BorderLayout.SOUTH);
		
		JButton btnDesapuntar = new JButton("Desapuntarme");
		panelBoton.add(btnDesapuntar);
		contentPane.add(panelCentro, BorderLayout.CENTER);
		
		

		setContentPane(contentPane);
		
		JPanel panelNorte = new JPanel();
		panelNorte.setBackground(new Color(102, 153, 153));
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JLabel lblAgenda_1 = new JLabel("Agenda");
		lblAgenda_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblAgenda_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		panelNorte.add(lblAgenda_1);
		
		JPanel panelSur = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelSur.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		JButton btnSalir = new JButton("Salir");
		panelSur.add(btnSalir);
		btnSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnSalir);
                thisFrame.dispose();
				VentanaUsuario ventanaUsuario = new VentanaUsuario();
				ventanaUsuario.setVisible(true);
				
			}
		});
		
		activityList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					int selectedIndex = activityList.getSelectedIndex();
					if(selectedIndex != -1) {
						String selectedActivity = activityListModel.getElementAt(selectedIndex);
						mostrarDatosActividad(selectedActivity, panelClase);
					} else {
						panelClase.removeAll();
					}
				}
			}
		});
		
	}
	private void mostrarDatosActividad(String selectedActivity, JPanel panelClase) {
		
	}
	
	

}
