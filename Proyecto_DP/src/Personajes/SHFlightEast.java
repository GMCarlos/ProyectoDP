package Personajes;

import Clases.Arma;
import Clases.Dir;
import Clases.Mapa;
import EstructurasDatos.Arbol;
import Excepciones.ArmaException;

public class SHFlightEast extends SuperHeroeVolador{
	private int poderArmasArbol;
	public SHFlightEast() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getPoderArmasArbol() {
		return poderArmasArbol;
	}

	public void setPoderArmasArbol(int poderArmasArbol) {
		this.poderArmasArbol = poderArmasArbol;
	}

	public SHFlightEast(String nombre, char marca, int turno) {
		super(nombre, marca, turno);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void procesar() throws ArmaException {
		Mapa mapa = Mapa.obtenerInstancia();
		if (this.getNumSalaDondeSeEncuentra() == mapa.gethP().getId()) {
			this.peleaConHombrePuerta();
		}

		if (getNumSalaDondeSeEncuentra() != 1111) {

			// movimiento
			if (!getColaDirecciones().isEmpty()) {
				Dir x;
				x = getColaDirecciones().poll();
				switch (x) {
				case N:
					if (getNumSalaDondeSeEncuentra() - mapa.getAncho() >= 0 && mapa.getG()
							.adyacente(getNumSalaDondeSeEncuentra(), getNumSalaDondeSeEncuentra() - mapa.getAncho())) {
						this.setNumSalaDondeSeEncuentra(getNumSalaDondeSeEncuentra() - mapa.getAncho());
						mapa.insertarPersonaje(this, getNumSalaDondeSeEncuentra());
					}
					break;

				case S:
					if (getNumSalaDondeSeEncuentra() + mapa.getAncho() < (mapa.getAlto() * mapa.getAncho())
							&& mapa.getG().adyacente(getNumSalaDondeSeEncuentra(),
									getNumSalaDondeSeEncuentra() + mapa.getAncho())) {
						this.setNumSalaDondeSeEncuentra(getNumSalaDondeSeEncuentra() + mapa.getAncho());
						mapa.insertarPersonaje(this, getNumSalaDondeSeEncuentra());
					}
					break;

				case E:
					if ((getNumSalaDondeSeEncuentra() + 1) < (mapa.getAlto() * mapa.getAncho())
							&& mapa.getG().adyacente(getNumSalaDondeSeEncuentra(), getNumSalaDondeSeEncuentra() + 1)) {
						this.setNumSalaDondeSeEncuentra(getNumSalaDondeSeEncuentra() + 1);
						mapa.insertarPersonaje(this, getNumSalaDondeSeEncuentra());
					}
					break;

				case W:
					if ((getNumSalaDondeSeEncuentra() - 1) >= 0
							&& mapa.getG().adyacente(getNumSalaDondeSeEncuentra(), getNumSalaDondeSeEncuentra() - 1)) {
						this.setNumSalaDondeSeEncuentra(getNumSalaDondeSeEncuentra() - 1);
						mapa.insertarPersonaje(this, getNumSalaDondeSeEncuentra());
					}
					break;
				}

			} else {
				if (mapa.gethP().getEstadoPuerta() == false) {
					mapa.insertarPersonaje(this, getNumSalaDondeSeEncuentra());
				}
			}
			// recoger arma
			this.recogerArmaCuandoLlegaASala();
			// aquí se capturan a los villanos de la sala
			// error
			Villano x = (Villano) mapa.getSala(this.getNumSalaDondeSeEncuentra()).obtenerPrimerVillanoDeLaSala();
			// compruebo de que haya villano para insertarlo
			if (x != null) {
				if (x.getArmaVillano() != null) {
					if (this.arbolArmas.pertenece(x.getArmaVillano())) {

						if (obtenerArmaConMismoNombre(x.getArmaVillano()).getPoder() > x.getArmaVillano().getPoder()) {
							// elimino el villano de la sala porque lo inserto en la cola
							// creo que el remove me elimina el personaje de la sala
							this.colaVillanosCapturados.add((Villano) x);
							mapa.getSala(this.getNumSalaDondeSeEncuentra()).getColaPersonajes().remove(x);
						}

					}
				}

			}
			setTurno(getTurno() + 1);
			
			if(this.getArbolArmas().vacio()) {
				setPoderArmasArbol(0);
			}else {
				int[] w = new int[1];
				w[0]=0;
				calcularPoderDeArmas(w);
				setPoderArmasArbol(w[0]);
			}
		}
	}
	
	
	
	private void calcularPoderDeArmas(int[] w) {
		// TODO Auto-generated method stub
		calcularPoderDeArmasRecursivo(w, getArbolArmas());
		
	}
	
	
	
	
	
	

	private void calcularPoderDeArmasRecursivo(int[] w, Arbol<Arma> arbolArmas) {
		// TODO Auto-generated method stub
		Arbol<Arma> arbolAux;
		if (!arbolArmas.vacio()) {

			w[0] = arbolArmas.getRaiz().getPoder() + w[0];

			if (arbolArmas.getHijoIzq() != null) {
				arbolAux = arbolArmas.getHijoIzq();
				calcularPoderDeArmasRecursivo(w, arbolAux);
			}
			if (arbolArmas.getHijoDer() != null) {
				arbolAux = arbolArmas.getHijoDer();
				calcularPoderDeArmasRecursivo(w, arbolAux);

			}
		}
	}


	



	@Override
	public String toString() {
		String x= new String();
		int a;
		Mapa mapa= Mapa.obtenerInstancia();
		if(this.getTurno()-1==mapa.getTurno() && getSeHaMovido()==true ) {
			a=getTurno()-1;
			x="(shflighteast:"+this.getMarca()+":"+this.getNumSalaDondeSeEncuentra()+":"+a+":"+this.getArbolArmas().toString()+"(powersum,"+getPoderArmasArbol()+")"+")";
		}else {
			x="(shflighteast:"+this.getMarca()+":"+this.getNumSalaDondeSeEncuentra()+":"+this.getTurno()+":"+this.getArbolArmas().toString()+"(powersum,"+getPoderArmasArbol()+")"+")";
		}
		return x;
	}
}
