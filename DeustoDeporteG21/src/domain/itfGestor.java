package domain;

public interface itfGestor {
	
	//si no hay BD que use los datos que estan aqui
	public boolean cargarUsuariosCSV();
	public boolean cargarClasesCSV();
	
	//no deberias tener ningun metodo VOID !!!!
	//porque eso significa que tienes feedback de ese metodo
	
	//si lo haces void, al menos deberias ponerle una excepcion
	//las excepciones se usan para avisar de errores en tiempo de ejecucion
	//y con boolean puedes responder a la llamada de la funcion
	//con verdadedo porque todo ha ido bien o con falso porque ha ido mal
	
	//se puede hacer con boolean, con int, ... etc, donde int es el codigo 
	//respuesta de la operacion
	
	public boolean cargarUsuariosBD();
	public boolean cargarClasesBD();
	public boolean guardarUsuariosBD();
	public boolean guardarClasesBD();
	public void cargarUsuariosCSV(String nomfich);
	public void cargarClasesCSV(String nomfichClases);
	
	public boolean borrarUsuario( String dni );
	
	
}
