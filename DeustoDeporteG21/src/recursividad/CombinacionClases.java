package recursividad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import db.GestorBD;
import domain.Clase;

public class CombinacionClases {
	
	public static List<Clase> obtenerCombinacionClasesBD(String fechaInicio, GestorBD gestorBD) throws ParseException {
        List<Clase> combinacionClasesSemana = new ArrayList<>();
        obtenerCombinacionClasesRecursivo(fechaInicio, gestorBD, combinacionClasesSemana);
        return combinacionClasesSemana;
    }

    private static void obtenerCombinacionClasesRecursivo(String fechaActual, GestorBD gestorBD, List<Clase> combinacionClasesSemana) throws ParseException {
        // Utilizar el método del gestor de base de datos para obtener las clases por fecha
        List<Clase> clasesDia = gestorBD.obtenerClasesPorFecha(fechaActual);

        if (clasesDia != null && !clasesDia.isEmpty()) {
            // Elegir una clase aleatoria para el día
            Random random = new Random();
            int indiceClaseAleatoria = random.nextInt(clasesDia.size());
            combinacionClasesSemana.add(clasesDia.get(indiceClaseAleatoria));
        }

        // Llamar recursivamente para el siguiente día
        String siguienteDia = avanzarUnDia(fechaActual);
        if (siguienteDia != null) {
            obtenerCombinacionClasesRecursivo(siguienteDia, gestorBD, combinacionClasesSemana);
        }
    }

    private static String avanzarUnDia(String fecha) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();

        try {
            Date fechaDate = formatoEntrada.parse(fecha);
            calendar.setTime(fechaDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            return formatoEntrada.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

	

	    
