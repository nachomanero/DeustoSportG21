package renderer;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


public class RendererTabla extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Verificar si la columna es la columna de fechas (índice 2)
        if (column == 2) {
            // Obtener la fecha actual
            Date fechaActual = new Date();

            // Convertir el valor de la celda a una fecha
            Date fechaCelda = null;
            if (value instanceof Date) {
                fechaCelda = (Date) value;
            } else {
                try {
                    fechaCelda = formatoFecha.parse(value.toString());
                } catch (Exception e) {
                    // Manejo de errores si la conversión falla
                    e.printStackTrace();
                }
            }

         // Comparar solo la parte de la fecha (día, mes y año)
            Calendar calActual = Calendar.getInstance();
            Calendar calCelda = Calendar.getInstance();
            calActual.setTime(fechaActual);
            calCelda.setTime(fechaCelda);

            if (calActual.get(Calendar.YEAR) == calCelda.get(Calendar.YEAR) &&
                calActual.get(Calendar.MONTH) == calCelda.get(Calendar.MONTH) &&
                calActual.get(Calendar.DAY_OF_MONTH) == calCelda.get(Calendar.DAY_OF_MONTH)) {
                // Cambiar el color del texto a rojo solo si la fecha coincide con la fecha actual
                cellComponent.setForeground(Color.RED);
            } else {
                // Restablecer el color a predeterminado si no coincide
                cellComponent.setForeground(table.getForeground());
            }
        }

        return cellComponent;
    }
}
