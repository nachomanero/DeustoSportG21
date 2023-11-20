package domain;

public class Clase {

    // Atributos de la clase Clase
    protected int IDClase;
    protected String hora;
    protected Actividad actividad;
    protected Fecha fecha;
    protected Sala sala;
    // Constructor
    public Clase(int IDClase, String hora, Actividad actividad, Fecha fecha, Sala sala) {
        this.IDClase = IDClase;
        this.hora = hora;
        this.actividad = actividad;
        this.fecha = fecha;
        this.sala=sala;
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
    
    public Sala getSala() {
    	return sala;
    }
    public void setSala(Sala sala) {
    	this.sala=sala;
    }
    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

	@Override
	public String toString() {
		return "Clase [IDClase=" + IDClase + ", hora=" + hora + ", actividad=" + actividad + ", fecha=" + fecha
				+ ", sala=" + sala + "]";
	}
	public void clase(int iDClase, String hora, Actividad actividad, Fecha fecha, Sala sala) {
		IDClase = iDClase;
		this.hora = hora;
		this.actividad = actividad;
		this.fecha = fecha;
		this.sala = sala;
	}
    
}
