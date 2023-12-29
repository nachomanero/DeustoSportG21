package domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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
		SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MM-yyyy");
        String fechaFormateadaStr = sdfOutput.format(fecha);
		return "Reserva [TipoActividad=" + TipoActividad + ", IDSala=" + IDSala + ", fecha=" + fechaFormateadaStr
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
