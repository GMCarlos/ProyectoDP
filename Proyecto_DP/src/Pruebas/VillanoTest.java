package Pruebas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Clases.Dir;
import Personajes.Villano;

public class VillanoTest {
	@Test
	public void extraTest() {
		Villano villano= new Villano("Cacahuete", 'R', 20);
		assertFalse(villano.getNombre()=="Fernando");
		assertTrue(villano.getMarca()=='R');
		assertNotNull(villano.getTurno());
		
	}
	
	@Test
	public void extraDireccion() {
		Villano villa= new Villano("Cacahuete", 'R', 20);
		villa.encolarDireccion(Dir.E);
		villa.encolarDireccion(Dir.W);
		
		assertNotNull(villa.getColaDirecciones());
		assertTrue(villa.devolverDireccion()==Dir.E);
		assertFalse(villa.devolverDireccion()==Dir.N);
	}
}
