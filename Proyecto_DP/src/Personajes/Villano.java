package Personajes;

import Clases.Arma;
import Clases.Dir;
import Clases.Mapa;
import Clases.Sala;

public class Villano extends Personaje {
	/*Arma del villano*/
	private Arma armaVillano;
	
	public Villano() {
		super();
		this.armaVillano= new Arma();
	}

	public Arma getArmaVillano() {
		return armaVillano;
	}

	public void setArmaVillano(Arma armaVillano) {
		this.armaVillano = armaVillano;
	}

	public Villano(String nombre, char marca, Arma arma) {
		this.setNombre(nombre);
		this.setMarca(marca);
		this.setTurno(0);
		this.setEsSuperHeroe(false);
		this.armaVillano= arma;
	}
	
	public Villano(String nombre, char marca, int turno) {
		this.setNombre(nombre);
		this.setMarca(marca);
		this.setEsSuperHeroe(false);
		this.setTurno(turno);
		this.armaVillano= new Arma();
	}

	public void addWeapon(Arma arma) {
		this.armaVillano=arma;
	}
	
	
	
	@Override
	public String toString() {
		String s;
		int a;
		Mapa mapa= Mapa.obtenerInstancia();
		if(getArmaVillano().getNombreArma()!=""&& getArmaVillano().getPoder()!=0) {
			if(this.getTurno()-1==mapa.getTurno()&& getSeHaMovido()==true ) {
				a=getTurno()-1;
				s="(villain:"+this.getMarca()+":"+this.getNumSalaDondeSeEncuentra()+":"+a+":"+getArmaVillano().toString()+")";
			}else {
				s="(villain:"+this.getMarca()+":"+this.getNumSalaDondeSeEncuentra()+":"+this.getTurno()+":"+getArmaVillano().toString()+")";
			}
			
		}else {
			if(this.getTurno()-1==mapa.getTurno()&& getSeHaMovido()==true) {
				a=getTurno()-1;
				s="(villain:"+this.getMarca()+":"+this.getNumSalaDondeSeEncuentra()+":"+a+":"+")";
			}else {
				s="(villain:"+this.getMarca()+":"+this.getNumSalaDondeSeEncuentra()+":"+this.getTurno()+":"+")";
			}
			
		}
		return s;
		
	}

	//recibir un arma
	public void recogerArmaCuandoLlegaASala() {
		Arma a;
		Mapa mapa = Mapa.obtenerInstancia();
		Sala s = mapa.getSala(this.getNumSalaDondeSeEncuentra());
		if(s.hayArma()) {
			if(getArmaVillano().getNombreArma()!="") {
				if(!s.getListaArmas().isEmpty()) {
					a=s.armaDeLaSalaConMayorPoder();
					if(a.getPoder()>this.armaVillano.getPoder()) {
						//System.out.println("Se elimina el arma "+ s.armaDeLaSalaConMayorPoder().getNombreArma()+" de la sala");
						s.insertarArma(this.armaVillano);
						this.armaVillano=a;
						
					}else {
						s.insertarArma(a);
					}
				}
				
			}else {
				if(!s.getListaArmas().isEmpty()) {
					setArmaVillano(s.armaDeLaSalaConMayorPoder());
				}
			}
			
		}
	}
	
	
	public void peleaConHombrePuerta() {
		Mapa mapa = Mapa.obtenerInstancia();
		// Si el arma del villano tiene más o igual poder que el arma de mayor poder del
		// hp
		
			if (armaVillano == null) {
				//System.out.println("El Villano " + this.getNombre() + " no tiene arma.");
			} else {
				if (this.armaVillano.getPoder() >= mapa.gethP().getArmaMayorPoder().getPoder()) {
					//System.out.println("El hombrePuerta pierde su arma " + mapa.gethP().getArmaMayorPoder().getNombreArma());
					mapa.gethP().eliminarArma(mapa.gethP().getArmaMayorPoder());
				}
				// Comprobar si se cumple la condición de apertura
				if (mapa.gethP().getArbolArmas().retornarAltura() < mapa.gethP().getAlturaAperturaCerradura()) {
					mapa.gethP().setEstadoPuerta(true);
					//mapa.getTeseracto().insertarPersonaje(this);
					//setNumSalaDondeSeEncuentra(mapa.getTeseracto().getIdentificador());
					//System.out.println("El superHeroe "+this.getNombre()+" abre el portal del HP. Es el dueño del mundo!!");
				}
			}
			if (mapa.gethP().getEstadoPuerta() == true) {
				mapa.getTeseracto().insertarPersonaje(this);
				setNumSalaDondeSeEncuentra(mapa.getTeseracto().getIdentificador());
			}
			
		}
	

	@Override
	public void procesar() {
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
			this.recogerArmaCuandoLlegaASala();
			SuperHeroe x = (SuperHeroe) mapa.getSala(this.getNumSalaDondeSeEncuentra()).obtenerPrimerSHDeLaSala();
			// compruebo de que haya SuperHeroe para insertarlo
			if (x != null) {
				// compruebo de que tenga un arma el Villano igual que la del superheroe
				if (getArmaVillano() != null) {
					if (x.getArbolArmas().pertenece(this.armaVillano)) {
						// si el poder del arma que tienen en comun tiene mayor poder en el superheroe
						if (x.obtenerArmaConMismoNombre(this.armaVillano).getPoder() < this.getArmaVillano()
								.getPoder()) {
							// elimino el arma del superHeroe
							x.getArbolArmas().borrar(x.obtenerArmaConMismoNombre(this.armaVillano));
						}
					}
				}

			}
			setTurno(getTurno() + 1);

		}
	}
}
