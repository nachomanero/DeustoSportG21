package domain;

import java.util.Date;

public class Reserva {
	private String DNI;
    private TipoActividad TipoActividad;
    private int IDSala;
    private Date fecha;
    private String hora;
	public Reserva() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reserva(String dNI, TipoActividad tipoActividad, int iDSala, Date fecha, String hora) {
		super();
		DNI = dNI;
		TipoActividad = tipoActividad;
		IDSala = iDSala;
		this.fecha = fecha;
		this.hora = hora;
	}
	@Override
	public String toString() {
		return "Reserva [DNI=" + DNI + ", TipoActividad=" + TipoActividad + ", IDSala=" + IDSala + ", fecha=" + fecha
				+ ", hora=" + hora + "]";
	}
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	public TipoActividad getTipoActividad() {
		return TipoActividad;
	}
	public void setTipoActividad(TipoActividad tipoActividad) {
		TipoActividad = tipoActividad;
	}
	public int getIDSala() {
		return IDSala;
	}
	public void setIDSala(int iDSala) {
		IDSala = iDSala;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
}
