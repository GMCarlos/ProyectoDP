package Movimiento;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import Clases.Mapa;

public class Profundidad implements Movimiento{

	@Override
	public Queue<Integer> mover(Integer salaInicio) {
		Mapa mapa=Mapa.obtenerInstancia();
		Queue<Integer> camino=new LinkedList<Integer>();
		Queue<Integer> x=new LinkedList<Integer>();
		Queue<Integer>[] caminoSolucion = new Queue[1];
		caminoSolucion[0]=x;
		Boolean[] caminoFinalizado=new Boolean[1];
		caminoFinalizado[0]=false;
		calcularCaminoProfundidadRecursivo(caminoFinalizado, salaInicio, mapa.getAlto()*mapa.getAncho()-1, camino, caminoSolucion);
		return caminoSolucion[0];
		
	}

	
	public void calcularCaminoProfundidadRecursivo(Boolean[] caminoFinalizado, int idSala, int salaDaily, Queue<Integer> sol, Queue<Integer>[] caminoSolucion) {
		if(caminoFinalizado[0]==false) {
			int salaTransitada;
			Mapa mapa=Mapa.obtenerInstancia();
			//retomo lo que habia hasta que llegue a la sala final, sala Dailyplanet
			Queue<Integer> solucionFinal = new LinkedList<Integer>(sol);
			solucionFinal.add(idSala);
			if(idSala==salaDaily) {
				Queue<Integer> solucion = new LinkedList<Integer>(sol);
				solucion.add(idSala);
				caminoSolucion[0]=solucion;
				caminoFinalizado[0]=true;
				//colaDeColas.add(solucion);
			}
			Set<Integer> Adyacentes = new LinkedHashSet<Integer>(sol);
			//guardo en Adyacentes los nodos adyacentes de idSala
			mapa.getG().adyacente(idSala, Adyacentes);
			java.util.Iterator<Integer> iter = Adyacentes.iterator();
			
			while(iter.hasNext()) {
				salaTransitada=iter.next();
				if(!sol.contains(salaTransitada)) {
					calcularCaminoProfundidadRecursivo(caminoFinalizado,salaTransitada,salaDaily, solucionFinal, caminoSolucion);
				}
			}
		}
		
		
	}

}
