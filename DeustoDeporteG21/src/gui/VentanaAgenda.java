package gui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import db.GestorBD;
import domain.Gestor;
import domain.Reserva;

import java.awt.FlowLayout;
public class VentanaAgenda extends JFrame {
  
	private JTable tablaReservas;
	private DefaultTableModel modeloTabla;
	private String dniGuardado;
	private JButton btnRetroceder;

	private Gestor g;
	private GestorBD gbd;
	
	/**
	 * Launch the application.
	 * @param dniGuardado2 
	 */

	public VentanaAgenda(Gestor gestor , GestorBD gestorBD , String dniGuardado) {
		
		g = gestor;
		gbd = gestorBD;
		
		this.dniGuardado = dniGuardado;
		initComponents();
		cargarReservas();
	}
		private void cargarReservas() {
	        
	        List<Reserva> reservasUsuario = gbd.obtenerReservasPorDni(dniGuardado);
	        modeloTabla.setRowCount(0);
	        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
	        for (Reserva reserva : reservasUsuario) {
	            Object[] fila = {
	                    reserva.getTipoActividad(),
	                    reserva.getIDSala(),
	                    formatoFecha.format(reserva.getFecha()),
	                    reserva.getHora()
	            };
	            modeloTabla.addRow(fila);
	        }

		
	}
	
	private void initComponents() {
		setTitle("MI AGENDA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Tipo Actividad");
        modeloTabla.addColumn("ID Sala");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Hora");
        
        tablaReservas = new JTable(modeloTabla);
        
        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        btnRetroceder = new JButton("Retroceder");
        btnRetroceder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retrocederAVentanaUsuario();
            }

			private void retrocederAVentanaUsuario() {
				// TODO Auto-generated method stub
				VentanaUsuario ventanaUsuario = new VentanaUsuario(g , gbd , dniGuardado);
			    ventanaUsuario.setVisible(true);
			    dispose();
			}
        });
        tablaReservas = new JTable(modeloTabla) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaReservas.getTableHeader().setReorderingAllowed(false);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(btnRetroceder);
        getContentPane().add(panelBoton, BorderLayout.SOUTH);

		
	}
	

}
