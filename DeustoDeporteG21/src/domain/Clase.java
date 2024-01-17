package domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Clase implements Comparable<Clase>{

    // Atributos de la clase Clase
    private int IDClase;
    private String hora;
    private TipoActividad tipoActividad;
    private Date fecha;
    private int IDSala;
    private int plazas;
    // Constructor
    public Clase(int IDClase, String hora, TipoActividad tipoActividad, Date fecha, int IDSala, int plazas) {
        this.IDClase = IDClase;
        this.hora = hora;
        this.tipoActividad = tipoActividad;
        this.fecha = fecha;
        this.IDSala=IDSala;
        this.plazas = plazas;
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
    
    public int getIDSala() {
    	return IDSala;
    }
    public void setIDSala(int IDSala) {
    	this.IDSala=IDSala;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateadaStr = sdfOutput.format(fecha);
		return hora + "  -  " + tipoActividad + "  -  " + fechaFormateadaStr
				+ "  -  " + "Sala " + IDSala + "  -  "  + plazas + " plazas" ;
	}
	public void clase(int iDClase, String hora, TipoActividad tipoActividad, Date fecha, int IDSala, int plazas) {
		IDClase = iDClase;
		this.hora = hora;
		this.tipoActividad = tipoActividad;
		this.fecha = fecha;
		this.IDSala = IDSala;
		this.plazas = plazas;
	}

	

	public Clase() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    
	@Override
	public int compareTo(Clase o) {
	    
	    int comparacionPlazas = o.plazas - this.plazas;

	    if (comparacionPlazas == 0) {
	        
	        int comparacionLongitud = Integer.compare(this.tipoActividad.toString().length(),
	                o.tipoActividad.toString().length());

	        return comparacionLongitud;
	    }

	    return comparacionPlazas;
	}

}
