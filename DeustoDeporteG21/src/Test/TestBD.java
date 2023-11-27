package Test;

import static org.junit.Assert.*;
import db.GestorBD;
//import es.deusto.prog3.examen.ord202201.BeforeClass;
//import es.deusto.prog3.examen.ord202201.DBManager;
//import es.deusto.prog3.examen.ord202201.Statistics;

import org.junit.Test;

public class TestBD {
	private GestorBD gestorBD;
	@org.junit.BeforeClass
	public static void setUpBeforeClass() {
		try {
			GestorBD gestorBD = new GestorBD();

		} catch (Exception ex) {
			fail("Error loading Rocket Launches.");
		}		
	}
}
