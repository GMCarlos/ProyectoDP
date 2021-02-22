package Personajes;

import java.util.LinkedList;
import java.util.Queue;
import Clases.Arma;
import Clases.Dir;
import Clases.Mapa;
import Excepciones.ArmaException;


public abstract class Personaje {
	/*Nombre del personaje*/
	private String nombre;
	/*Marca del personaje*/
	private char marca;
	/*Turno del personaje*/
	private int turno;
	/*Id de la sala donde se encuentra el personaje*/
	private int numSalaDondeSeEncuentra;
	/*Booleano que dice si un personaje es superheroe (true) o no (false)*/
	private boolean esSuperHeroe;
	/*Booleano para controlar si un personaje ha comenzado el movimiento en la simulacion*/
	private boolean seHaMovido;
	/*Cola de direcciones para el movimiento del personaje*/
	protected Queue<Dir> colaDirecciones;
	
	/**
	 * @PRE el personaje debe de estar inicializado
	 * @Explicaci贸n En caso de que el personaje sea sh, devuelve true, en caso contrario false
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public boolean getEsSuperHeroe() {
		return esSuperHeroe;
	}
	
	
	
	public boolean getSeHaMovido() {
		return seHaMovido;
	}



	public void setSeHaMovido(boolean seHaMovido) {
		this.seHaMovido = seHaMovido;
	}



	public Queue<Dir> transformarColaDeNodosEnDirecciones(Queue<Integer> x) {
		int nodo1, nodo2;
		Mapa mapa = Mapa.obtenerInstancia();
		Queue<Dir> colaDirecciones= new LinkedList<Dir>();
		//primera Sala
		nodo1=x.poll();
		int tama駉=x.size();
		x.add(nodo1);
		for(int i=0; i<tama駉;i++) {
			nodo2=x.poll();
			if(nodo1-1==nodo2 ) {
				colaDirecciones.add(Dir.W);
				nodo1=nodo2;
				x.add(nodo2);
			}else {
				if(nodo1+1==nodo2) {
					colaDirecciones.add(Dir.E);
					nodo1=nodo2;
					x.add(nodo2);
				}else {
					if(nodo1+mapa.getAncho()==nodo2) {
						colaDirecciones.add(Dir.S);
						nodo1=nodo2;
						x.add(nodo2);
					}else {
						if(nodo1-mapa.getAncho()==nodo2) {
							colaDirecciones.add(Dir.N);
							nodo1=nodo2;
							x.add(nodo2);
						}
					}
				}
			}
			
		}
		return colaDirecciones;
	}
	
	/**
	 * @PRE 
	 * @param esSuperHeroe = booleano que indica si es o no sH el personaje
	 * @Explicaci贸n Guarda en el atributo del personaje el valor booleano que se introduce por parametros
	 * @Post Se almacena en la instancia del personaje el valor de esSuperheroe
	 * @Complejidad: 0(1)
	 */
	public void setEsSuperHeroe(boolean esSuperHeroe) {
		this.esSuperHeroe = esSuperHeroe;
	}
	
	/**
	 * @PRE el personaje debe de estar inicializado
	 * @Explicaci贸n Devuelve el valor del atributo nombre de la instancia del superheroe
	 * @Post 
	 * @return devuelve el nombre del personaje
	 * @Complejidad: 0(1)
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * @PRE 
	 * @param nombre = nombre que se almacena en la instancia
	 * @Explicaci贸n Guarda en el atributo nombre del personaje el valor que se introduce por parametros
	 * @Post Se almacena en la instancia del personaje el valor de nombre
	 * @Complejidad: 0(1)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @PRE el personaje debe de estar inicializado
	 * @Explicaci贸n Devuelve el valor del atributo marca de la instancia del superheroe
	 * @Post 
	 * @return devuelve la marca del personaje
	 * @Complejidad: 0(1)
	 */
	public char getMarca() {
		return marca;
	}
	
	/**
	 * @PRE 
	 * @param marca = marca que se almacena en la instancia
	 * @Explicaci贸n Guarda en el atributo marca del personaje el valor que se introduce por parametros
	 * @Post Se almacena en la instancia del personaje el valor de marca
	 * @Complejidad: 0(1)
	 */
	public void setMarca(char marca) {
		this.marca = marca;
	}
	
	/**
	 * @PRE el personaje debe de estar inicializado
	 * @Explicaci贸n Devuelve el valor del atributo turno de la instancia del personaje
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public int getTurno() {
		return turno;
	}
	
	/**
	 * @PRE 
	 * @param turno = turno que se almacena en la instancia
	 * @Explicaci贸n Guarda en el atributo turno del personaje el valor que se introduce por parametros
	 * @Post Se almacena en la instancia del personaje el valor de turno
	 * @Complejidad: 0(1)
	 */
	public void setTurno(int turno) {
		this.turno = turno;
	}
	/**
	 * @PRE el personaje debe de estar inicializado
	 * @Explicaci贸n Devuelve el valor del atributo numSalaDondeSeEncuentra de la instancia del personaje
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public int getNumSalaDondeSeEncuentra() {
		return numSalaDondeSeEncuentra;
	}
	
	/**
	 * @PRE 
	 * @param numSalaDondeSeEncuentra = id de la sala donde se encuentra el personaje 
	 * @Explicaci贸n Guarda en el atributo numSalaDondeSeEncuentra del personaje el valor que se introduce por parametros
	 * @Post Se almacena en la instancia del personaje el valor de numSalaDondeSeEncuentra
	 * @Complejidad: 0(1)
	 */
	public void setNumSalaDondeSeEncuentra(int numSalaDondeSeEncuentra) {
		this.numSalaDondeSeEncuentra = numSalaDondeSeEncuentra;
	}

	
	/**
	 * @PRE 
	 * @param nombre = nombre del personaje
	 * @param marca = marca del personaje
	 * @param turno = turno en el que se encuentra el personaje
	 * @Explicaci贸n Constructor parametrizado de un Personaje
	 * @Post Se crea una instancia de personaje
	 * @Complejidad: 0(1)
	 */
	public Personaje(String nombre, char marca, int turno) {
		this.nombre = nombre;
		this.marca = marca;
		this.turno = turno;
		this.numSalaDondeSeEncuentra = 0;
		this.colaDirecciones= new LinkedList<Dir>();
		this.seHaMovido=false;
		
	}
	
	/**
	 * @PRE 
	 * @Explicaci贸n Constructor no parametrizado de un Personaje
	 * @Post Se crea una instancia de personaje con los valores por defecto
	 * @Complejidad: 0(1)
	 */
	public Personaje() {
		this.nombre = "";
		this.marca = ' ';
		this.turno = 0;
		this.numSalaDondeSeEncuentra = 0;
		this.colaDirecciones=new LinkedList<Dir>();
	}
	
	/**
	 * @PRE 
	 * @param a = instancia de arma que se almacena en el personaje
	 * @Explicaci贸n M閠odo que almacena el arma en el personaje
	 * @Post El arma se almacena en la instancia
	 * @Complejidad: 0(1)
	 */
	public abstract void addWeapon(Arma a);
	
	
	public Queue<Dir> getColaDirecciones() {
		return colaDirecciones;
	}

	public void setColaDirecciones(Queue<Dir> colaDirecciones) {
		this.colaDirecciones = colaDirecciones;
	}

	/**
	 * @PRE el personaje debe de tener direcciones almacenadas
	 * @Explicaci贸n Se devuelve una direccion de la cola de direcciones del personaje
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public Dir devolverDireccion() {
		Dir aux;
		aux=this.colaDirecciones.poll();
		this.colaDirecciones.add(aux);
		return aux;
	}
	
	/**
	 * @PRE 
	 * @Explicaci贸n Se almacena una direccion en la cola de direcciones del personaje
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public void encolarDireccion(Dir direccion) {
		this.colaDirecciones.add(direccion);
	}
	
	/**
	 * @PRE el personaje debe de estar inicializado
	 * @Explicaci贸n Se devuelve la informacion de los atributos de la instancia
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	@Override
	public String toString() {
		return "Personaje= ";
	}
	
	/**
	 * @throws ArmaException 
	 * @PRE debe de haber armas en la sala para recoger
	 * @Explicaci贸n Se recoge el arma de la sala si se cumplen las condiciones
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public abstract void recogerArmaCuandoLlegaASala() throws ArmaException;
	
	/**
	 * @PRE el hombrePuerta tiene que estar en la misma sala que el personaje para poder interactuar
	 * @Explicaci贸n El personaje interactua con el HP para intentar vencerle y asi, abrir el teseracto
	 * @Post En caso de ganar el personaje cambia el estado de la puerta del HP																																	
	 * @Complejidad: 0(1)
	 */
	public abstract void peleaConHombrePuerta();
	
	/**
	 * @throws ArmaException 
	 * @PRE el personaje debe de estar inicializado
	 * @Explicaci贸n El personaje realiza sus acciones interactuando en su turno
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public abstract void procesar() throws ArmaException;
}
