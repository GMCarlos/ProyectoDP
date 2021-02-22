package Pruebas;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import Clases.Arma;
import Clases.Mapa;
import Clases.Sala;
import Excepciones.MapaException;
import Excepciones.SalaException;
import Personajes.SuperHeroeExtrasensorial;
import Personajes.SuperHeroeFisico;
import Personajes.SuperHeroeVolador;
import Personajes.Villano;


/**
 * EC3 Implementacion de la clase SalaTest que representa la EC3
 * dentro del proyecto .
 * 
 * @version 1
 * @author <b> GMCarlos_8 </b><br>
 *         Carlos Guillen Moreno.<br>
 *         Asignatura Desarrollo de Programas Curso 17/18
 */

public class SalaTest {

	@Test
	public void testSalaId() throws SalaException {
		Sala s = new Sala(1);
		Sala s1 = new Sala(2);

		/** True: el identificador de la sala s es 1. */
		assertTrue(s.getIdentificador() == 1);

		/** True: el identificador de la sala s1 es 2. */
		assertTrue(s1.getIdentificador() == 2);

		/** False: el identificador de las salas es el mismo. */
		assertFalse(s.getIdentificador() == s1.getIdentificador());

	}

	@Test
	public void testSalaArmas(){
		Sala s = new Sala(1);
		Arma l1, l2, l3;

		l1 = new Arma("Arma1",1);
		s.insertarArma(l1);

		l2 = new Arma("Arma2",2);
		s.insertarArma(l2);

		l3 = new Arma("Arma3",3);
		s.insertarArma(l3);
		

		/** True: la primera arma del suelo tiene poder 1 */
		assertTrue(s.getListaArmas().get(2).getPoder()==1);
		assertTrue(s.getListaArmas().get(2).getNombreArma()== "Arma1");

		/** True: la segunda arma del suelo tiene poder 2 */
		assertTrue(s.getListaArmas().get(1).getPoder()==2);
		assertTrue(s.getListaArmas().get(1).getNombreArma()== "Arma2");

		/** True: la tercera arma del suelo tiene poder 3 */
		assertTrue(s.getListaArmas().get(0).getPoder()==3);
		assertTrue(s.getListaArmas().get(0).getNombreArma()== "Arma3");
	}

	@Test
	public void testSalaColaPersonaje() throws MapaException {
		Mapa mapa = Mapa.obtenerInstancia();
		mapa.setAlto(6);
		mapa.setAncho(6);
		mapa.gethP().setId(35);
		mapa.inicializarSalas(6, 6);
		Sala s = new Sala(1);
		
		//String nombre, char marca, int turno
		SuperHeroeExtrasensorial heroe1 = new SuperHeroeExtrasensorial("Extrasensorial1", 'E' , 0);
		SuperHeroeFisico heroe2 = new SuperHeroeFisico("Fisico1", 'F', 1);
		SuperHeroeVolador heroe3 = new SuperHeroeVolador("Volador1", 'V', 2);
		Villano villano1 = new Villano("Villano1", 'X', 3);
		
		
		s.insertarPersonaje(heroe1);
		s.insertarPersonaje(heroe2);
		s.insertarPersonaje(heroe3);
		s.insertarPersonaje(villano1);
		
		
		
		
		/** True: el primer personaje en salir es Extrasensorial1 */
		assertTrue(s.getColaPersonajes().poll().getMarca() == 'E');
		
		
		/** True: el primer personaje en salir es Fisico1 */
		assertTrue(s.getColaPersonajes().poll().getMarca() == 'F');
		
		/** True: el segundo personaje en salir es Volador1 */
		assertTrue(s.getColaPersonajes().poll().getMarca() == 'V');
			
		/** False: el ultimo personaje en salir es Villano1 */
		assertFalse(s.getColaPersonajes().poll().getMarca() == 'B');

	}

	@Test
	public void testSalaMarcaKruskal() throws SalaException{
		Sala s1 = new Sala(1);
		Sala s2 = new Sala(2);
		int marcaK1 = 1;
		int marcaK2 = 20;
		
		s1.setKruskal(marcaK1);
		s2.setKruskal(marcaK1);
		
		/** True: la sala s1 y s2 tienen la misma marca de kruskal */
		assertTrue(s1.getKruskal() == s2.getKruskal());
		
		/** False: la sala s1 y s2 tienen diferente marca de kruskal */
		assertFalse(s1.getKruskal() != s2.getKruskal());
		
		s2.setKruskal(marcaK2);
		
		/** False: la sala s1 y s2 tienen la misma marca de kruskal */
		assertFalse(s1.getKruskal() == s2.getKruskal());
		
		/** True: la sala s1 y s2 tienen diferente marca de kruskal */
		assertTrue(s1.getKruskal() != s2.getKruskal());
		
		s1.setKruskal(marcaK2);
		
		/** True: la sala s1 y s2 tienen la misma marca de kruskal */
		assertTrue(s1.getKruskal() == s2.getKruskal());
		
		/** False: la sala s1 y s2 tienen diferente marca de kruskal */
		assertFalse(s1.getKruskal() != s2.getKruskal());
	}
	
	@Test
	public void hayArmasTest() {
		Sala sala = new Sala();
		Arma arma1 = new Arma("Arma",13);
		ArrayList <Arma> listaArmas = new ArrayList<Arma>();
		sala.setListaArmas(listaArmas);
		listaArmas.add(arma1);
		assertTrue(sala.hayArma());
		listaArmas.remove(0);
		assertFalse (sala.hayArma());
	}
	
}
