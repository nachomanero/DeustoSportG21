package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Fecha {

    public static List<LocalDate> generarFechas(int[] años) {
        List<LocalDate> fechas = new ArrayList<>();

        for (int año : años) {
            for (int mes = 1; mes <= 12; mes++) {
                int diasEnMes = LocalDate.of(año, mes, 1).lengthOfMonth();
                for (int dia = 1; dia <= diasEnMes; dia++) {
                    fechas.add(LocalDate.of(año, mes, dia));
                }
            }
        }

        return fechas;
    }

    public static void main(String[] args) {
        int[] años = {2023, 2024};
        List<LocalDate> todasLasFechas = generarFechas(años);

        // Imprime todas las fechas generadas
        todasLasFechas.forEach(System.out::println);
    }
}


