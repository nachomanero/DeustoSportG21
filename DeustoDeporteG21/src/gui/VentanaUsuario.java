package gui;

import javax.swing.JFrame;



import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import io.FicheroLogger;
import java.util.logging.Logger;
import java.util.logging.Level;
public class VentanaUsuario extends JFrame {

	private JPanel contentPane;
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());

	
	public VentanaUsuario() {
		
		setResizable(false);
		setBounds(new Rectangle(200, 200, 550, 350));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		
		JLabel agenda = new JLabel("");
		agenda.setHorizontalAlignment(SwingConstants.RIGHT);
		agenda.setIcon(new ImageIcon("resources/images/Agenda.png"));
		agenda.setToolTipText("Mi Agenda");
		
		JLabel clases = new JLabel("");
		clases.setHorizontalAlignment(SwingConstants.LEFT);
		clases.setIcon(new ImageIcon("resources/images/FuturasClases.png"));
		clases.setToolTipText("Futuras clases");
		panelFotos.add(agenda);
		panelFotos.add(clases);	
		
		
        agenda.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(agenda);
                thisFrame.dispose();
				VentanaAgenda ventanaAgenda = new VentanaAgenda();
				ventanaAgenda.setVisible(true);
                LOGGER.log(Level.INFO, "VIsualizacion de la agenda del Usuario");

            }

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        });

        
        clases.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	VentanaCalendarioActividades calendarioActividades= new VentanaCalendarioActividades();
				calendarioActividades.mostrarVentana();
				JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(clases);
                thisFrame.dispose();
                LOGGER.log(Level.INFO, "Se esta mostrando el calendario de Actividades");

			
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

		
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		panelSur.setBackground(new Color(102, 153, 153));
		
		JButton btnSalir = new JButton("Salir");
		panelSur.add(btnSalir);
		
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                	JFrame thisFrame = (JFrame) SwingUtilities.getWindowAncestor(btnSalir);
                    thisFrame.dispose();
                    LOGGER.log(Level.INFO, "El usuario ha salido."+result);

                }else {
                	LOGGER.log(Level.WARNING,"Salida de la aplicacion fallida"+result);
                }
			}
		});
		
		
	}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaUsuario().setVisible(true);
        });
    }
	
    

}

