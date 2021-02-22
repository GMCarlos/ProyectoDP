package Movimiento;

import java.util.LinkedList;
import java.util.Queue;

import Clases.Mapa;
import Excepciones.MapaException;

public class MovimientoDefensa implements Movimiento{

	@Override
	public Queue<Integer> mover(Integer salaInicio) throws MapaException {
		Mapa mapa = Mapa.obtenerInstancia();
		int salaDestino;
		salaDestino=mapa.obtenerSalaSinSalidaEste();
		//salaDestino guarda la sala de más al este sin salida
		
		Queue<Integer> camino= new LinkedList<Integer>();
		camino.add(salaInicio);
		while(salaInicio!=salaDestino) {
			salaInicio=mapa.getG().siguiente(salaInicio, salaDestino);
			camino.add(salaInicio);
		}
		camino.add(salaInicio);
		//aqui estaria en la sala ya este y ahora tendría que ir a la salaDaily
		salaDestino=mapa.getAlto()*mapa.getAncho()-1;
		while(salaInicio!=salaDestino) {
			salaInicio=mapa.getG().siguiente(salaInicio, salaDestino);
			camino.add(salaInicio);
		}
		camino.add(salaInicio);
		return camino;
		
	}

}
