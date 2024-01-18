package renderer;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


public class RendererTabla implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {

    	JLabel result = new JLabel(value.toString());
		
		//Se definen para el label los colores de texto y fondo por defecto de la tabla
		result.setBackground(table.getBackground());
		result.setForeground(table.getForeground());
					
		//Si el valor es de tipo Editorial: se renderiza con la imagen centrada
		if (column==3) {
			Date d = (Date) value;
			
			result.setText("");		
			result.setToolTipText(d.toString());
			result.setHorizontalAlignment(JLabel.CENTER);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			
	        LocalDateTime fechaHoraActual = LocalDateTime.now();

	        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	        String fechaFormateada = fechaHoraActual.format(formato);
	        var fechaTabla = sdf.format(d);
	        
	        if (fechaTabla == fechaFormateada){
	        	
	        	result.setBackground(new Color(250, 249, 249));
			
			}
		
				
		
		result.setOpaque(true);
		
		
    }
		return result;}

    
}