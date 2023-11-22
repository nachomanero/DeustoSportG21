package domain;

public class Agenda {
	
	//clase contenedora con una estructura que almacena informacion
	
	//... que estructura?
	//... que informacion?

    // Atributos de la clase Agenda
    private Clase IDClase;
    private Actividad IDActividad;
    private Sala IDSala;
    
    private Usuario Usuario; //?????
    
    
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
	public void agenda(Clase iDClase, Actividad iDActividad, Sala iDSala, domain.Usuario usuario) {
		IDClase = iDClase;
		IDActividad = iDActividad;
		IDSala = iDSala;
		Usuario = usuario;
	}
	
}
