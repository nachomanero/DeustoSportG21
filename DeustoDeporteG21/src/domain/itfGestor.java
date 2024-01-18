package domain;

public interface itfGestor {
	
	
	
	public void cargarUsuariosBD();
	public void cargarClasesBD();
	public void guardarUsuariosBD();
	public void guardarClasesBD();
	public void cargarUsuariosCSV(String nomfich);
	public void cargarClasesCSV(String nomfichClases);
	public void guardarUsuariosCSV();
	public void guardarClasesCSV();

	
	
	
}
