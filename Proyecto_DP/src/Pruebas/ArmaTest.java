package Pruebas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Clases.Arma;

public class ArmaTest {
	@Test
	public void testArma() {
		Arma a = new Arma("Nombre1", 1);
		assertTrue(a.getNombreArma()=="Nombre1");
		assertTrue(a.getPoder()==1);
		
		assertFalse(a.getNombreArma()=="Lanza");
		assertFalse(a.getPoder()==17);
		
	}
	
	@Test
	public void testGetSetArma() {
		Arma arma = new Arma();
		
		arma.setNombreArma("Martillo");
		arma.setPoder(19);
		
		assertTrue(arma.getNombreArma() == "Martillo");
		assertFalse(arma.getPoder() == 13);
	}
}
