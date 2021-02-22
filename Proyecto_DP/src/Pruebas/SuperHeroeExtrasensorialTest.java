package Pruebas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Clases.Dir;
import Personajes.SuperHeroeExtrasensorial;

public class SuperHeroeExtrasensorialTest {
	@Test
	public void extraTest() {
		SuperHeroeExtrasensorial extra= new SuperHeroeExtrasensorial("Cacahuete", 'R', 20);
		assertFalse(extra.getNombre()=="Fernando");
		assertTrue(extra.getMarca()=='R');
		assertNotNull(extra.getTurno());
		
	}
	
	@Test
	public void extraDireccion() {
		SuperHeroeExtrasensorial extra= new SuperHeroeExtrasensorial("Cacahuete", 'R', 20);
		extra.encolarDireccion(Dir.E);
		extra.encolarDireccion(Dir.W);
		
		assertNotNull(extra.getColaDirecciones());
		assertTrue(extra.devolverDireccion()==Dir.E);
		assertFalse(extra.devolverDireccion()==Dir.N);
	}
}
