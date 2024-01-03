package renderer;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class RendererTabla implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {

        Component c = null;

        switch (column) {
            case 3:
                c = new JLabel(value.toString());
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);

                // Obtener la fecha actual
                Date fechaActual = new Date();

                // Obtener la fecha de la celda (asumiendo que el valor es una fecha)
                Date fechaCelda = (Date) value;

                // Verificar si la fecha de la celda es igual a la fecha actual
                if (isSameDay(fechaActual, fechaCelda)) {
                    // Configurar el color de fondo a naranja
                    c.setBackground(Color.ORANGE);
                } else {
                    // Configurar el color de fondo a blanco (o el color predeterminado)
                    c.setBackground(table.getBackground());
                }

                break;

            default:
                // Para otras columnas, utiliza el renderizador predeterminado
                c = new DefaultTableCellRenderer().getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                break;
        }

        return c;
    }

    // Método para verificar si dos fechas son del mismo día
    private boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date1).equals(sdf.format(date2));
    }
}