package recursividad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import db.GestorBD;
import domain.Clase;

public class CombinacionClases {
	
	public static List<Clase> obtenerCombinacionClases(String fechaInicio, GestorBD gestorBD) throws ParseException {
        List<Clase> combinacionClasesSemana = new ArrayList<>();
        CombinacionClasesRecursivo(fechaInicio, gestorBD, combinacionClasesSemana, 0);
        return combinacionClasesSemana;
    }

    private static void CombinacionClasesRecursivo(String contadorFecha, GestorBD gestorBD, List<Clase> combinacionClasesSemana, int contadorSemana) throws ParseException {
        if (contadorSemana>=7) {
        	return;
        }
    	
        List<Clase> clasesDia = gestorBD.obtenerClasesPorFecha(contadorFecha);

        if (clasesDia != null && !clasesDia.isEmpty()) {
            Random random = new Random();
            int indiceClaseAleatoria = random.nextInt(clasesDia.size());
            combinacionClasesSemana.add(clasesDia.get(indiceClaseAleatoria));
        }

        String siguienteDia = avanzarUnDia(contadorFecha);
        if (siguienteDia != null) {
            CombinacionClasesRecursivo(siguienteDia, gestorBD, combinacionClasesSemana, contadorSemana+1);
        }
    }

    private static String avanzarUnDia(String fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();

        try {
            Date fechaDate = formatoFecha.parse(fecha);
            calendar.setTime(fechaDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            return formatoFecha.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

	

	    
