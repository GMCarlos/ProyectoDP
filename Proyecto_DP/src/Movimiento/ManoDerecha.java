package Movimiento;

import java.util.LinkedList;
import java.util.Queue;

import Clases.Mapa;

public class ManoDerecha implements Movimiento{

	@Override
	public Queue<Integer> mover(Integer salaInicio) {
		Mapa mapa=Mapa.obtenerInstancia();
		Queue<Integer> camino = new LinkedList<Integer>();
//		int salaInicio=0;
		int salaFinal=mapa.getAlto()*mapa.getAncho()-1;
		String ultimoMov="Sur";
		camino.add(salaInicio);
		while(salaInicio!=salaFinal) {
			
			switch(ultimoMov) {
			case "Norte":
				//N a E
				if(mapa.getG().adyacente(salaInicio, salaInicio+1)) {
					ultimoMov="Este";
					salaInicio=salaInicio+1;
					camino.add(salaInicio);
				}else {
					//N a N
					if(mapa.getG().adyacente(salaInicio, salaInicio-mapa.getAncho())) {
						ultimoMov="Norte";
						salaInicio=salaInicio-mapa.getAncho();
						camino.add(salaInicio);
					}else {//N a O
						if(mapa.getG().adyacente(salaInicio, salaInicio-1)) {
							ultimoMov="Oeste";
							salaInicio=salaInicio-1;
							camino.add(salaInicio);
						}else {
							//N a S
							if(mapa.getG().adyacente(salaInicio, salaInicio+mapa.getAncho())) {
								ultimoMov="Sur";
								salaInicio=salaInicio+mapa.getAncho();
								camino.add(salaInicio);
							}
						}
					}
				}
				break;
				
			case "Oeste":
				//O a N
				if(mapa.getG().adyacente(salaInicio, salaInicio-mapa.getAncho())) {
					ultimoMov="Norte";
					salaInicio=salaInicio-mapa.getAncho();
					camino.add(salaInicio);
				}else {
					//O a O
					if(mapa.getG().adyacente(salaInicio, salaInicio-1)) {
						ultimoMov="Oeste";
						salaInicio=salaInicio-1;
						camino.add(salaInicio);
					}else {//O a S
						if(mapa.getG().adyacente(salaInicio, salaInicio+mapa.getAncho())) {
							ultimoMov="Sur";
							salaInicio=salaInicio+mapa.getAncho();
							camino.add(salaInicio);
						}else {
							//O a E
							if(mapa.getG().adyacente(salaInicio, salaInicio+1)) {
								ultimoMov="Este";
								salaInicio=salaInicio+1;
								camino.add(salaInicio);
							}
						}
					}
				}
				break;
				
			case "Sur":
				//S a O
				if(mapa.getG().adyacente(salaInicio, salaInicio-1)) {
					ultimoMov="Oeste";
					salaInicio=salaInicio-1;
					camino.add(salaInicio);
				}else {
					//S a S
					if(mapa.getG().adyacente(salaInicio, salaInicio+mapa.getAncho())) {
						ultimoMov="Sur";
						salaInicio=salaInicio+mapa.getAncho();
						camino.add(salaInicio);
					}else {//S a E
						if(mapa.getG().adyacente(salaInicio, salaInicio+1)) {
							ultimoMov="Este";
							salaInicio=salaInicio+1;
							camino.add(salaInicio);
						}else {
							//S a N
							if(mapa.getG().adyacente(salaInicio, salaInicio-mapa.getAncho())) {
								ultimoMov="Norte";
								salaInicio=salaInicio-mapa.getAncho();
								camino.add(salaInicio);
							}
						}
					}
				}
				break;
				
			case "Este":
				//E a S
				if(mapa.getG().adyacente(salaInicio, salaInicio+mapa.getAncho())) {
					ultimoMov="Sur";
					salaInicio=salaInicio+mapa.getAncho();
					camino.add(salaInicio);
				}else {
					//E a E
					if(mapa.getG().adyacente(salaInicio, salaInicio+1)) {
						ultimoMov="Este";
						salaInicio=salaInicio+1;
						camino.add(salaInicio);
					}else {//E a N
						if(mapa.getG().adyacente(salaInicio, salaInicio-mapa.getAncho())) {
							ultimoMov="Norte";
							salaInicio=salaInicio-mapa.getAncho();
							camino.add(salaInicio);
						}else {
							//E a O
							if(mapa.getG().adyacente(salaInicio, salaInicio-1)) {
								ultimoMov="Oeste";
								salaInicio=salaInicio-1;
								camino.add(salaInicio);
							}
						}
					}
				}
				break;
			}
				
		}
		return camino;
		
	}



}


//SI EL ULTIMO MOVIMIENTO QUE HE HECHO ES EL NORTE, ME MUEVO AL ESTE
//SI EL ULTIMO MOVIMIENTO QUE HE HECHO ES EL OESTE, ME MUEVO AL NORTE
//SI EL ULTIMO MOVIMIENTO QUE HE HECHO ES EL SUR, ME MUEVO AL OESTE
//SI EL ULTIMO MOVIMIENTO QUE HE HECHO ES EL ESTE, ME MUEVO AL SUR

//SINO HAY ARCOS, HAY PARED