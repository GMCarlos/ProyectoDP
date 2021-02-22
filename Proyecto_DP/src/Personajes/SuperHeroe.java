package Personajes;

import java.util.LinkedList;
import java.util.Queue;

import Clases.Arma;
import Clases.Dir;
import Clases.Mapa;
import Clases.Sala;
import EstructurasDatos.Arbol;
import Excepciones.ArmaException;

public abstract class SuperHeroe extends Personaje {
	/*Arbol de armas del superheroe*/
	protected Arbol<Arma> arbolArmas;
	/*Cola de villanos capturados por el sh*/
	protected Queue<Villano> colaVillanosCapturados;
	
	
	/**
	 * @PRE El personaje debe de estar inicializado
	 * @Explicaci√≥n Se devuelve el valor del atributo arbolArmas del superheroe
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public Arbol<Arma> getArbolArmas() {
		return arbolArmas;
	}

	/**
	 * @PRE 
	 * @param arbolArmas = arbol de armas
	 * @Explicaci√≥n Se almacena en el atributo del superheroe arbolArmas el valor que se entra por parametro de entrada
	 * @Post El atributo arbolArmas del superheroe se ha modificado
	 * @Complejidad: 0(1)
	 */
	public void setArbolArmas(Arbol<Arma> arbolArmas) {
		this.arbolArmas = arbolArmas;
	}

	/**
	 * @PRE El personaje debe de estar inicializado
	 * @Explicaci√≥n Se devuelve el valor del atributo colaVillanosCapturados del superheroe
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public Queue<Villano> getColaVillanosCapturados() {
		return colaVillanosCapturados;
	}

	/**
	 * @PRE 
	 * @param colaVillanosCapturados = cola de Villanos que atrapan los sh
	 * @Explicaci√≥n Se almacena en el atributo del superheroe colaVillanosCapturados el valor que se entra por parametro de entrada
	 * @Post El atributo colaVillanosCapturados del superheroe se ha modificado
	 * @Complejidad: 0(1)
	 */
	public void setColaVillanosCapturados(Queue<Villano> colaVillanosCapturados) {
		this.colaVillanosCapturados = colaVillanosCapturados;
	}

	

	/**
	 * @PRE 
	 * @param nombre = nombre del sh
	 * @param marca = marca del sh
	 * @param turno = turno del superheroe
	 * @Explicaci√≥n Constructor parametrizado del personaje
	 * @Post Crea una instancia de personaje
	 * @Complejidad: 0(1)
	 */
	public SuperHeroe(String nombre, char marca, int turno) {
		super(nombre, marca, turno);
		this.setEsSuperHeroe(true);
		this.arbolArmas= new Arbol<Arma>();
		this.colaVillanosCapturados= new LinkedList<Villano>();
		this.colaDirecciones= new LinkedList<Dir>();
	}
	
	/**
	 * @PRE 
	 * @param nombre = nombre del sh
	 * @param marca = marca del sh
	 * @Explicaci√≥n Constructor parametrizado del personaje
	 * @Post Crea una instancia de personaje
	 * @Complejidad: 0(1)
	 */
	public SuperHeroe(String nombre, char marca) {
		this.setNombre(nombre);
		this.setMarca(marca);
		this.setEsSuperHeroe(true);
		this.setTurno(0);
		this.arbolArmas= new Arbol<Arma>();
		this.colaDirecciones= new LinkedList<Dir>();
	}

	/**
	 * @PRE 
	 * @Explicaci√≥n Constructor por defecto del personaje
	 * @Post Crea una instancia de personaje
	 * @Complejidad: 0(1)
	 */
	public SuperHeroe() {
		super();
		this.arbolArmas= new Arbol<Arma>();
		this.colaDirecciones= new LinkedList<Dir>();
	}
	
	/**
	 * @PRE 
	 * @param x = instancia de villano
	 * @Explicaci√≥n Metodo que inserta un villano en la cola del superheroe
	 * @Post AÒade un villano a la cola
	 * @Complejidad: 0(1)
	 */
	public void insertarVillano(Villano x) {
		this.colaVillanosCapturados.add(x);
	}


	/**
	 * @PRE El personaje tiene que estar inicializado
	 * @Explicaci√≥n MÈtodo que muestra la informacion de los atributos del superheroe
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	@Override
	public String toString() {
		return "SuperHeroe [marca= " +this.getMarca()+", nombre= "+this.getNombre()+", sala= "+this.getNumSalaDondeSeEncuentra()+", Armas=[ "+"\n"+this.arbolArmas.toString()+  "]";
	}

	public void addWeapon(Arma arma) {
		this.arbolArmas.insertar(arma);		
	}
	

	
	// recoger arma de mayor poder
	public void recogerArmaCuandoLlegaASala() throws ArmaException {
		Mapa mapa = Mapa.obtenerInstancia();
		Sala s = mapa.getSala(this.getNumSalaDondeSeEncuentra());
		// si hay armas en la sala entra
		// AQUI DA PROBLEMAS
		if (s.hayArma()) {
			Arma a = s.armaDeLaSalaConMayorPoder();
			// sino est· vacio el arbol, compruebo si est· en el arbol
			if (!getArbolArmas().vacio()) {
				//buscamos el arma a en su arbol
				boolean enc;
				enc=perteneceArmaAlArbol(a);
				//si est·, le sumo el poder
				if(enc) {
					sumarPoderArmaArbol(a);
				}else {
					//si no est· en el arbol la inserto
					getArbolArmas().insertar(a);
				}
				

			}else {
				getArbolArmas().insertar(a);
			}
		}
	}

	
	public void sumarPoderArmaArbol(Arma a) throws ArmaException {
		sumarPoderArmaArbolRecursivo(getArbolArmas(), a);
		
	}

	private void sumarPoderArmaArbolRecursivo(Arbol<Arma> arbolArmas2, Arma a) throws ArmaException {
		Arbol<Arma> arbolAux;
		if (!arbolArmas2.vacio()) {
			
			if (arbolArmas2.getRaiz().getNombreArma() == a.getNombreArma()) {
				arbolArmas2.getRaiz().setPoder(arbolArmas2.getRaiz().getPoder()+a.getPoder());
			}

			if (arbolArmas2.getHijoIzq() != null) {
				arbolAux = arbolArmas2.getHijoIzq();
				sumarPoderArmaArbolRecursivo(arbolAux,a);
			}
			if (arbolArmas2.getHijoDer() != null) {
				arbolAux = arbolArmas2.getHijoDer();
				sumarPoderArmaArbolRecursivo(arbolAux, a);
			}

		}
		
	}

	//CAMBIAR COSAS
	public boolean perteneceArmaAlArbol(Arma buscada) {
		Boolean[] aux=new Boolean[1];
		aux[0]=false;
		perteneceArmaAlArbolRecursivo(getArbolArmas(),buscada, aux);
		return aux[0];
 	}
	
	private void perteneceArmaAlArbolRecursivo(Arbol<Arma> arbolArmas2, Arma buscada, Boolean[] aux) {
		Arbol<Arma> arbolAux;
		if (!arbolArmas2.vacio()) {
			
			if (arbolArmas2.getRaiz().getNombreArma() == buscada.getNombreArma()) {
				aux[0] = true;
			}

			if (arbolArmas2.getHijoIzq() != null) {
				arbolAux = arbolArmas2.getHijoIzq();
				perteneceArmaAlArbolRecursivo(arbolAux, buscada, aux);
			}
			if (arbolArmas2.getHijoDer() != null) {
				arbolAux = arbolArmas2.getHijoDer();
				perteneceArmaAlArbolRecursivo(arbolAux, buscada, aux);
			}

		}
	}
	
	
	//Pre: arbol no vacio
	//llama al metodo recursivo
	public Arma ArmaMayorPoder() {
		//preguntar a Jose si puedo crear un arma sin valores para poder hacer comprobaciones en el arbol
		
		Arma[] aux = new Arma [1];
		this.recursivoArmaMayorPoder(this.arbolArmas, aux);
		return aux[0];
	}
	
	//Preguntar a Jose si est· bien
	private void recursivoArmaMayorPoder(Arbol<Arma> arbolArmas2, Arma[] aux2) {
		Arbol<Arma> aux;
		if (!arbolArmas2.vacio()) {
			// La primera vez que entra est· vacia la arma y le asigno un valor para poder comparar
			if (aux2[0] == null) {
				aux2[0] = arbolArmas2.getRaiz();
			}

			if (arbolArmas2.getHijoIzq() != null) {
				aux = arbolArmas2.getHijoIzq();
				recursivoArmaMayorPoder(aux, aux2);
			}
			
			if (arbolArmas2.getRaiz().getPoder() > aux2[0].getPoder()) {
				aux2[0] = arbolArmas2.getRaiz();
			}
			else {
				if(arbolArmas2.getRaiz().getPoder() == aux2[0].getPoder()) {
					if(arbolArmas2.getRaiz().getNombreArma().compareTo(aux2[0].getNombreArma()) < 0 ) {
						aux2[0] = arbolArmas2.getRaiz();
					}
				}
			}
			
			if (arbolArmas2.getHijoDer() != null) {
				aux = arbolArmas2.getHijoDer();
				recursivoArmaMayorPoder(aux, aux2);
			}

		}
	}
	
	public Arma obtenerArmaConMismoNombre(Arma x) {
		Arma[] aux = new Arma [1];
		this.recursivoArmaConMismoNombre(this.arbolArmas, aux, x);
		return aux[0];
	}
	
	private void recursivoArmaConMismoNombre(Arbol<Arma> arbolArmas2, Arma[] aux, Arma x) {
		Arbol<Arma> Arbolaux;
		if (!arbolArmas2.vacio()) {
			if(arbolArmas2.getRaiz()!=null) {
				if(arbolArmas2.getRaiz().getNombreArma()==x.getNombreArma()) {
					aux[0]=arbolArmas.getRaiz();
				}
				if(arbolArmas2.getHijoIzq()!=null) {
					Arbolaux=arbolArmas2.getHijoIzq();
					recursivoArmaConMismoNombre(Arbolaux, aux, x);
				}
				if(arbolArmas2.getHijoDer()!=null) {
					Arbolaux=arbolArmas2.getHijoDer();
					recursivoArmaConMismoNombre(Arbolaux, aux, x);
				}
			}

		}
		
	}

	public void peleaConHombrePuerta() {
		Arma mayorPoder = this.ArmaMayorPoder();
		Mapa mapa = Mapa.obtenerInstancia();
		

			// COSAS CAMBIADAS
			if (mayorPoder == null) {
				// System.out.println("El superHeroe "+this.getNombre() +" no tiene arma.");
			} else {
				// System.out.println("El superheroe "+this.getNombre()+" usa "+
				// mayorPoder.getNombreArma() + ":"+ mayorPoder.getPoder());
				if (mapa.gethP().pertenece(mayorPoder)) {
					// Si encuentra el arma
					if (mayorPoder.getPoder() > mapa.gethP().buscarArmaEnArbol(mayorPoder).getPoder()) {
						// System.out.println("El HP elimina el arma "+ mayorPoder.getNombreArma()+ "
						// con poder "+mapa.gethP().buscarArmaEnArbol(mayorPoder).getPoder());
						Arma x = mapa.gethP().buscarArmaEnArbol(mayorPoder);
						// System.out.println(x.getNombreArma());
						mapa.gethP().eliminarArma(x);

					}
				} else {
					// System.out.println("DoorMan does not have the weapon, no figth");
				}
				// DespuÈs de la pelea, se elimina el arma del arbol
				// System.out.println("Superhero loses his weapon: Arma:
				// "+mayorPoder.getNombreArma() );
				// System.out.println("El superHeroe elimina el arma "+
				// mayorPoder.getNombreArma()+ " de su arbol");
				this.arbolArmas.borrar(mayorPoder);

				// Comprobar si se cumple la condiciÛn de apertura
				if (mapa.gethP().getArbolArmas().retornarAltura() < mapa.gethP().getAlturaAperturaCerradura()) {
					mapa.gethP().setEstadoPuerta(true);
					//mapa.getTeseracto().insertarPersonaje(this);
					//setNumSalaDondeSeEncuentra(mapa.getTeseracto().getIdentificador());
					// System.out.println("El superHeroe "+this.getNombre()+" abre el portal del HP.
					// Es el dueÒo del mundo!!");
				}
			}
			if (mapa.gethP().getEstadoPuerta() == true) {
			mapa.getTeseracto().insertarPersonaje(this);
			setNumSalaDondeSeEncuentra(mapa.getTeseracto().getIdentificador());
		}

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
			// aquÌ se capturan a los villanos de la sala
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

		}
	}
	
}
