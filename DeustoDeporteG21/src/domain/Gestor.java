package domain;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Scanner;

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
	public void cargarUsuariosCSV(String nomfichUsuarios) {
		// TODO Auto-generated method stub
		try {
			Scanner sc = new Scanner(new FileReader(nomfichUsuarios));
			String linea;
			while(sc.hasNext()) {
				linea = sc.nextLine();
				String [] partes = linea.split(",");
				String dni = partes[0];
				String nombre = partes[1];
				String apellido = partes[2];
				String direccion = partes[3];
				String correo = partes[4];
				String contraseña = partes[5];
				
				Usuario u = new Usuario(dni,nombre,apellido,direccion,correo,contraseña);
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.format("\n* Error al abrir el archivo de Usuarios: %s", e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Override
	public void cargarClasesCSV(String nomfichClases) {
		// TODO Auto-generated method stub
		try {
			
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
			Scanner sc = new Scanner(new FileReader(nomfichClases));
			String linea;
			while(sc.hasNext()) {
				linea = sc.nextLine();
				String [] partes = linea.split(",");
				String idClase = partes[0];
				String hora = partes[1];
				String tipoActividad = partes[2];
				String fecha = partes[3];
				String sala = partes[4];
				
				
				
				
				Clase c = new Clase(Integer.parseInt(idClase), hora, TipoActividad.valueOf(tipoActividad), formatoFecha.parse(fecha), idSala);
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.format("\n* Error al abrir el archivo de Clases: %s", e.getMessage());
			e.printStackTrace();
		}
		
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
