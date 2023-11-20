package domain;

public class Actividad {

    // Atributos de la clase Actividad
    protected int IDActividad;
    protected String TipoActividad;
    protected int plazas;

    // Constructor
    public Actividad(int IDActividad, String TipoActividad, int plazas) {
        this.IDActividad = IDActividad;
        this.TipoActividad = TipoActividad;
        this.plazas = plazas;
    }

    // Métodos getter y setter 

    public int getIDActividad() {
        return IDActividad;
    }

    public void setIDActividad(int IDActividad) {
        this.IDActividad = IDActividad;
    }

    public String getTipoActividad() {
        return TipoActividad;
    }

    public void setTipoActividad(String TipoActividad) {
        this.TipoActividad = TipoActividad;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }
}

