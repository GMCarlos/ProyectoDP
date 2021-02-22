package Movimiento;

import java.util.Queue;

import Excepciones.MapaException;

public interface Movimiento {
	public Queue<Integer> mover(Integer salaInicio) throws MapaException;
}
