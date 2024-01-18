package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import java.util.List;
import java.util.Map;

import db.GestorBD;
import domain.Reserva;

public class VentanaHistorialReservas extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree tree;
    private DefaultMutableTreeNode rootNode;
    private static GestorBD gestorBD; 

    public VentanaHistorialReservas(GestorBD gestorBD) {
        VentanaHistorialReservas.gestorBD = gestorBD; 
        setTitle("Historial de Reservas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        rootNode = new DefaultMutableTreeNode("Historial de Reservas");
        tree = new JTree(rootNode);

        tree.setCellRenderer(new CustomTreeCellRenderer());

        cargarHistorialReservas();

        JScrollPane scrollPane = new JScrollPane(tree);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void cargarHistorialReservas() {
        Map<String, List<Reserva>> reservasPorUsuario = gestorBD.obtenerTodasLasReservas();

        for (String usuarioDni : reservasPorUsuario.keySet()) {
            DefaultMutableTreeNode userNode = new DefaultMutableTreeNode("DNI: " + usuarioDni);
            List<Reserva> reservasUsuario = reservasPorUsuario.get(usuarioDni);

            for (Reserva reserva : reservasUsuario) {
                DefaultMutableTreeNode reservaNode = new DefaultMutableTreeNode(reserva.toString());
                userNode.add(reservaNode);
            }

            rootNode.add(userNode);
        }

        tree.expandRow(0);
    }

    private class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            if (value instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

                if (node.getUserObject() != null && node.getUserObject() instanceof String) {
                    String dni = (String) node.getUserObject();

                    int numReservas = node.getChildCount();

                    if (numReservas > 5) {
                        setForeground(Color.CYAN); 
                    }
                }
            }

            return this;
        }
    }
}


