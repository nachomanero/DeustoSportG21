package domain;

public class Agenda {

    // Atributos de la clase Agenda
    protected Clase IDClase;
    protected Actividad IDActividad;
    protected Sala IDSala;
    protected Usuario Usuario;
	public Clase getIDClase() {
		return IDClase;
	}
	public void setIDClase(Clase iDClase) {
		IDClase = iDClase;
	}
	public Actividad getIDActividad() {
		return IDActividad;
	}
	public void setIDActividad(Actividad iDActividad) {
		IDActividad = iDActividad;
	}
	public Sala getIDSala() {
		return IDSala;
	}
	public void setIDSala(Sala iDSala) {
		IDSala = iDSala;
	}
	public Usuario getUsuario() {
		return Usuario;
	}
	public void setUsuario(Usuario usuario) {
		Usuario = usuario;
	}
	@Override
	public String toString() {
		return "Agenda [IDClase=" + IDClase + ", IDActividad=" + IDActividad + ", IDSala=" + IDSala + ", Usuario="
				+ Usuario + "]";
	}
	public Agenda() {
		super();
	}
	public Agenda(Clase iDClase, Actividad iDActividad, Sala iDSala, domain.Usuario usuario) {
		super();
		IDClase = iDClase;
		IDActividad = iDActividad;
		IDSala = iDSala;
		Usuario = usuario;
	}
}
