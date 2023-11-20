package domain;
public class Sala {


	// Atributos de la clase Sala
    protected int IDSala;
    protected String nombre;
    protected int capacidad;

    // Constructor
    public Sala(int IDSala, String nombre, int capacidad) {
        this.IDSala = IDSala;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    // Métodos getter y setter para acceder y modificar los atributos

    public int getIDSala() {
        return IDSala;
    }

    public void setIDSala(int IDSala) {
        this.IDSala = IDSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    @Override
	public String toString() {
		return "Sala [IDSala=" + IDSala + ", nombre=" + nombre + ", capacidad=" + capacidad + "]";
	}

	public  void sala(int iDSala, String nombre, int capacidad) {
		IDSala = iDSala;
		this.nombre = nombre;
		this.capacidad = capacidad;
	}
    
    
}

