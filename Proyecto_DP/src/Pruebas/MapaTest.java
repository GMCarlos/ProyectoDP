package Pruebas;

import static org.junit.Assert.*;

import org.junit.Test;

import Clases.Mapa;
import Clases.Sala;
import Excepciones.MapaException;

public class MapaTest {

	@Test
	public void mapaTest() throws MapaException {
		Mapa mapa = Mapa.obtenerInstancia();
		mapa.setAlto(5);
		mapa.setAncho(4);
		
		Sala prueba = new Sala();
		mapa.setTeseracto(prueba);
		assertNotNull(mapa.getTeseracto());
		assertTrue(mapa.getTeseracto().getColaPersonajes().size()==0);
		
		assertTrue(mapa.getAlto()==5);
		assertTrue(mapa.getAncho()==4);
		
		
		
	}

}
