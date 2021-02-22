package Pruebas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Clases.Dir;
import Personajes.SuperHeroeVolador;

public class SuperHeroeVoladorTest {
	@Test
	public void voladorTest() {
		SuperHeroeVolador fisico= new SuperHeroeVolador("Cacahuete", 'R', 20);
		assertFalse(fisico.getNombre()=="Fernando");
		assertTrue(fisico.getMarca()=='R');
		assertNotNull(fisico.getTurno());
		
	}
	
	@Test
	public void voladorDireccion() {
		SuperHeroeVolador volador= new SuperHeroeVolador("Cacahuete", 'R', 20);
		volador.encolarDireccion(Dir.E);
		volador.encolarDireccion(Dir.W);
		
		assertNotNull(volador.getColaDirecciones());
		assertTrue(volador.devolverDireccion()==Dir.E);
		assertFalse(volador.devolverDireccion()==Dir.N);
	}
}
