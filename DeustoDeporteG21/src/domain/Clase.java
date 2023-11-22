package domain;

import java.util.Date;

public class Clase {

    // Atributos de la clase Clase
    private int IDClase;
    private String hora;
    private TipoActividad tipoActividad;
    private Date fecha;
    private int IDSala;
    // Constructor
    public Clase(int IDClase, String hora, Actividad actividad, Date fecha, int sala, TipoActividad tipoActividad) {
        this.IDClase = IDClase;
        this.hora = hora;
        this.tipoActividad = tipoActividad;
        this.fecha = fecha;
        this.IDSala=IDSala;
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
    
    public int getSala() {
    	return IDSala;
    }
    public void setSala(int sala) {
    	this.IDSala=IDSala;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

	@Override
	public String toString() {
		return "Clase [IDClase=" + IDClase + ", hora=" + hora + ", tipoActividad=" + tipoActividad + ", fecha=" + fecha
				+ ", sala=" + IDSala + "]";
	}
	public void clase(int iDClase, String hora, Actividad actividad, Date fecha, int sala, TipoActividad tipoActividad) {
		IDClase = iDClase;
		this.hora = hora;
		this.tipoActividad = tipoActividad;
		this.fecha = fecha;
		this.IDSala = IDSala;
	}

	

	public Clase() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
