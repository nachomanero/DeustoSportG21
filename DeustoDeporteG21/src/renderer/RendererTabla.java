package renderer;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class RendererTabla extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (column == 2) {
            Date fechaActual = new Date();

            Date fechaCelda = null;
            if (value instanceof Date) {
                fechaCelda = (Date) value;
            } else {
                try {
                    fechaCelda = formatoFecha.parse(value.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Calendar calActual = Calendar.getInstance();
            Calendar calCelda = Calendar.getInstance();
            calActual.setTime(fechaActual);
            calCelda.setTime(fechaCelda);

            if (calActual.get(Calendar.YEAR) == calCelda.get(Calendar.YEAR) &&
                calActual.get(Calendar.MONTH) == calCelda.get(Calendar.MONTH) &&
                calActual.get(Calendar.DAY_OF_MONTH) == calCelda.get(Calendar.DAY_OF_MONTH)) {
                cellComponent.setForeground(Color.RED);
            } else {
                cellComponent.setForeground(table.getForeground());
            }
        }

        return cellComponent;
    }
}
