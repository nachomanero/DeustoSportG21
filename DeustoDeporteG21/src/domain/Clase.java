package domain;

import java.util.Date;

public class Clase {

    // Atributos de la clase Clase
    protected int IDClase;
    protected String hora;
    protected TipoActividad tipoActividad;
    protected Date fecha;
    protected int ID;
    // Constructor
    public Clase(int IDClase, String hora, Actividad actividad, Date fecha, Sala sala, TipoActividad tipoActividad) {
        this.IDClase = IDClase;
        this.hora = hora;
        this.tipoActividad = tipoActividad;
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

    public TipoActividad getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(TipoActividad tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

    public Date getFecha() {
        return fecha;
    }
    
    public Sala getSala() {
    	return sala;
    }
    public void setSala(Sala sala) {
    	this.sala=sala;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

	@Override
	public String toString() {
		return "Clase [IDClase=" + IDClase + ", hora=" + hora + ", tipoActividad=" + tipoActividad + ", fecha=" + fecha
				+ ", sala=" + sala + "]";
	}
	public void clase(int iDClase, String hora, Actividad actividad, Date fecha, Sala sala, TipoActividad tipoActividad) {
		IDClase = iDClase;
		this.hora = hora;
		this.tipoActividad = tipoActividad;
		this.fecha = fecha;
		this.sala = sala;
	}

	

	public Clase() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
