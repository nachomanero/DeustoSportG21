package domain;

import java.util.HashSet;

public class Gestor implements itfGestor{
	
	//Gestiona las operaciones sobre el modelo logico
	
	
	//esta clase se comunica el GestorBD para solicitar los datos
	
	//aqui deben estar los MAPAS, los SET, etc
	
	private HashSet<Usuario> usuarios;
	//...
	
	public Gestor()
	{
		usuarios = new HashSet<Usuario>();
		
		//...

	}

	@Override
	public void cargarUsuariosCSV() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarClasesCSV() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarUsuariosBD() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardarUsuariosBD() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
