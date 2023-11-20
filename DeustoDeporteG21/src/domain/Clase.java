package domain;

public class Clase {

    // Atributos de la clase Clase
    private int IDClase;
    private String hora;
    private Actividad actividad;
    private Fecha fecha;
    // Constructor
    public Clase(int IDClase, String hora, Actividad actividad, Fecha fecha) {
        this.IDClase = IDClase;
        this.hora = hora;
        this.actividad = actividad;
        this.fecha = fecha;
    }
    // Métodos getter y setter 

    public int getIDClase() {
        return IDClase;
    }

    public void setIDClase(int IDClase) {
        this.IDClase = IDClase;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }
}
