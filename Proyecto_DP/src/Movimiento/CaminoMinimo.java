package Movimiento;

import java.util.LinkedList;
import java.util.Queue;

import Clases.Mapa;

public class CaminoMinimo implements Movimiento{

	@Override
	public Queue<Integer> mover(Integer salaInicio) {
		Mapa mapa = Mapa.obtenerInstancia();
//		int salaInicio=0;
		int salaDestino=mapa.getAlto()*mapa.getAncho()-1;
		Queue<Integer> camino= new LinkedList<Integer>();
		camino.add(salaInicio);
		while(salaInicio!=salaDestino) {
			salaInicio=mapa.getG().siguiente(salaInicio, salaDestino);
			camino.add(salaInicio);
		}
		camino.add(salaInicio);
		return camino;
	}

}
