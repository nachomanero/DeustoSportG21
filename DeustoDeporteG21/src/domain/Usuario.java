package domain;

import java.io.Serializable;

public  class  Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
    private String dni;
    private String nombre;
    private String apellido;
    private String direccion;
    private String correoElectronico;
    private String contrasena;
    
    private Agenda agenda;

    // Constructor de Usuario
    public Usuario(String dni, String nombre, String apellido, String direccion, String correoElectronico, String contrasena) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }

    // Métodos getter y setter para acceder y modificar los atributos del Usuario
    
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

	@Override
	public String toString() {
		return "Usuario [dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", direccion=" + direccion
				+ ", correoElectronico=" + correoElectronico + ", contrasena=" + contrasena + "]";
	}

	public void usuario(final String dni, final String nombre, final String apellido, final String direccion, final String correoElectronico,
			final String contrasena) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.correoElectronico = correoElectronico;
		this.contrasena = contrasena;
	}

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	
}
