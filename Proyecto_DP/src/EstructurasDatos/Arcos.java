package EstructurasDatos;

/**
 * @name Arcos
 * @description Clase de los arcos del grafo
 * @group GMCarlos_8
 * @author Carlos Guillen Moreno
 * @date
 * @version 
 */

public class Arcos 
{
	private int Origen;
	private int Destino;
	
	public Arcos() {
		this.Origen = 0;
		this.Destino = 0;
	}

	public Arcos(int origen, int destino) {
		this.Origen = origen;
		this.Destino = destino;
	}
	
	public int getDestino() {
		return Destino;
	}
	public int getOrigen() {
		return Origen;
	}
	public void setDestino(int destino) {
		Destino = destino;
	}
	public void setOrigen(int origen) {
		Origen = origen;
	}
	
	@Override
	public String toString() {
		String s= new String("Origen: "+this.Origen+" Destino: "+this.Destino+"\n");
		return s;
	}

}
