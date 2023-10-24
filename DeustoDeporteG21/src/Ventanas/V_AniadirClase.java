package Ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Component;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import javax.swing.JButton;

public class V_AniadirClase extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					V_AniadirClase frame = new V_AniadirClase();
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
	public V_AniadirClase() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 550, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
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
		panel_2.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setMaximumRowCount(20);
		panel_2.add(comboBox, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		panel_Labels.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Fecha: ");
		panel_3.add(lblNewLabel_2, BorderLayout.WEST);
		
		textField = new JTextField();
		textField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_3.add(textField, BorderLayout.EAST);
		textField.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_Labels.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_3 = new JLabel("Hora: ");
		panel_4.add(lblNewLabel_3, BorderLayout.WEST);
		
		textField_1 = new JTextField();
		textField_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_4.add(textField_1, BorderLayout.EAST);
		textField_1.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		panel_Labels.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_4 = new JLabel("Lugar: ");
		panel_5.add(lblNewLabel_4, BorderLayout.WEST);
		
		textField_2 = new JTextField();
		panel_5.add(textField_2, BorderLayout.EAST);
		textField_2.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		panel_Labels.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_5 = new JLabel("Capacidad: ");
		panel_6.add(lblNewLabel_5, BorderLayout.WEST);
		
		JSpinner spinner = new JSpinner();
		panel_6.add(spinner, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("DATOS DE LA CLASE");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_1.add(lblNewLabel);
		
		JPanel panel_7 = new JPanel();
		contentPane.add(panel_7, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("CREAR CLASE");
		panel_7.add(btnNewButton);
	}

}
