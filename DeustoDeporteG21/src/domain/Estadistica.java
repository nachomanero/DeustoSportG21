package domain;

public class Estadistica
{
	private TipoActividad a;
	private int veces;

	public Estadistica(TipoActividad clave, Integer v) {
		
		a = clave;
		veces = v;
	}
	
	public int getVeces()
	{
		return veces;
	}
	
	public TipoActividad getActividad()
	{
		return a;
	}
	
}