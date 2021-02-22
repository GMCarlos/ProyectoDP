package Pruebas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Clases.Dir;
import Personajes.SuperHeroeFisico;

public class SuperHeroeFisicoTest {
	@Test
	public void fisicoTest() {
		SuperHeroeFisico fisico= new SuperHeroeFisico("Cacahuete", 'R', 20);
		assertFalse(fisico.getNombre()=="Fernando");
		assertTrue(fisico.getMarca()=='R');
		assertNotNull(fisico.getTurno());
		
	}
	
	@Test
	public void fisicoDireccion() {
		SuperHeroeFisico fisico= new SuperHeroeFisico("Cacahuete", 'R', 20);
		fisico.encolarDireccion(Dir.E);
		fisico.encolarDireccion(Dir.W);
		
		assertNotNull(fisico.getColaDirecciones());
		assertTrue(fisico.devolverDireccion()==Dir.E);
		assertFalse(fisico.devolverDireccion()==Dir.N);
	}
}
