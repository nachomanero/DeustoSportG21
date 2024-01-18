package domain;

public class Agenda {
	
	

    
    private Clase IDClase;
    private TipoActividad tipoActividad;
    private Sala IDSala;
    
    private Usuario Usuario;
    
    
	public Clase getIDClase() {
		return IDClase;
	}
	public void setIDClase(Clase iDClase) {
		IDClase = iDClase;
	}
	public TipoActividad getTipoActividad() {
		return tipoActividad;
	}
	public void setIDActividad(TipoActividad TipoActividad) {
		tipoActividad = TipoActividad;
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
		return "Agenda [IDClase=" + IDClase + ", IDActividad=" + tipoActividad + ", IDSala=" + IDSala + ", Usuario="
				+ Usuario + "]";
	}
	public Agenda() {
		super();
	}
	public void agenda(Clase iDClase, TipoActividad TipoActividad, Sala iDSala, domain.Usuario usuario) {
		IDClase = iDClase;
		tipoActividad = TipoActividad;
		IDSala = iDSala;
		Usuario = usuario;
	}
	
}
