package domain;

public class Admin extends Usuario {
    private String IDEmpleado;
    public Admin(String dni, String nombre, String apellido, String direccion, String correoElectronico, String contrasena, String IDEmpleado) {
        super(dni, nombre, apellido, direccion, correoElectronico, contrasena);
        this.IDEmpleado = IDEmpleado;
    }

    // Método getter y setter para el atributo propio de Admin

    public String getIDEmpleado() {
        return IDEmpleado;
    }

    public void setIDEmpleado(String IDEmpleado) {
        this.IDEmpleado = IDEmpleado;
    }

	@Override
	public String toString() {
		return "Admin [IDEmpleado=" + IDEmpleado + ", dni=" + getDni() + ", nombre=" + getNombre() + ", apellido=" + getApellido()
				+ ", direccion=" + getDireccion() + ", correoElectronico=" + getCorreoElectronico() + ", contrasena=" + getContrasena()
				+ ", getIDEmpleado()=" + getIDEmpleado() + ", getDni()=" + getDni() + ", getNombre()=" + getNombre()
				+ ", getApellido()=" + getApellido() + ", getDireccion()=" + getDireccion()
				+ ", getCorreoElectronico()=" + getCorreoElectronico() + ", getContrasena()=" + getContrasena()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

	public void admin(String dni, String nombre, String apellido, String direccion, String correoElectronico,
			String contrasena, String iDEmpleado) {
		IDEmpleado = iDEmpleado;
	}

	public Admin(String dni, String nombre, String apellido, String direccion, String correoElectronico,
			String contrasena) {
		super(dni, nombre, apellido, direccion, correoElectronico, contrasena);
	}
}

