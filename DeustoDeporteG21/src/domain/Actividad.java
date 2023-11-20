package domain;

public class Actividad {

    // Atributos de la clase Actividad
    protected int IDActividad;
    protected TipoActividad TipoActividad;
    protected int plazas;

    // Constructor
    public Actividad(int IDActividad, domain.TipoActividad TipoActividad, int plazas) {
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

    public TipoActividad getTipoActividad() {
        return TipoActividad;
    }

    public void setTipoActividad(TipoActividad TipoActividad) {
        this.TipoActividad = TipoActividad;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }
}

