package Clases;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Excepciones.ArmaException;
import Personajes.Personaje;

public class Sala {
	/*Identificador de la sala*/
	private int identificador;
	/*Lista de armas de la sala*/
	private ArrayList <Arma> listaArmas;
	/*Cola de personajes de la sala*/
	private Queue<Personaje> colaPersonajes;
	/*Identificador de kruskal de la sala*/
	private int kruskal;

	public ArrayList<Arma> getListaArmas() {
		return listaArmas;
	}

	public void setListaArmas(ArrayList<Arma> listaArmas) {
		this.listaArmas = listaArmas;
	}

	/**
	 * @PRE La sala debe de estar inicializada
	 * @Explicación Se devuelve el valor del atributo kruskal
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public int getKruskal() {
		return kruskal;
	}

	/**
	 * @PRE 
	 * @param kruskal = entero 
	 * @Explicación Se almacena en el atributo de la sala kruskal el valor que se entra por parametro de entrada
	 * @Post El atributo kruskal del mapa se ha modificado
	 * @Complejidad: 0(1)
	 */
	public void setKruskal(int kruskal) {
		this.kruskal = kruskal;
	}

	/**
	 * @PRE La sala debe de estar inicializada
	 * @Explicación Se devuelve el valor del atributo identificador
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public int getIdentificador() {
		return identificador;
	}
	/**
	 * @PRE 
	 * @param identificador = entero
	 * @Explicación Se almacena en el atributo de la sala identificador el valor que se entra por parametro de entrada
	 * @Post El atributo identificador del mapa se ha modificado
	 * @Complejidad: 0(1)
	 */
	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	/**
	 * @PRE 
	 * @Explicación M�todo que muestra toda la informacion del objeto Sala
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	@Override
	public String toString() {
		return "Sala [identificador=" + identificador + ", listaArmas=" + listaArmas + ", colaPersonajes="
				+ colaPersonajes + "]";
	}

	/**
	 * @PRE
	 * @param q = instancia de un objeto Personaje 
	 * @Explicación M�todo que inserta en la cola de la sala el personaje que se entra por parametros
	 * @Post La cola tendr� el personaje
	 * @Complejidad: 0(1)
	 */
	public void insertarPersonaje(Personaje q) {
		this.colaPersonajes.add(q);
	}
	/**
	 * @PRE 
	 * @param identificador = entero que se utiliza para identificar la sala e identificar su marca de Kruskal
	 * @Explicación Constructor parametrizado que inicializa una sala
	 * @Post La sala queda inicializada
	 * @Complejidad: 0(1)
	 */
	public Sala(int identificador) {
		this.identificador = identificador;
		this.kruskal=identificador;
		this.listaArmas= new ArrayList <Arma>();
		this.colaPersonajes=new LinkedList<Personaje>();
	}
	
	/**
	 * @PRE 
	 * @Explicación Constructor no parametrizado que inicializa una sala
	 * @Post La sala queda inicializada
	 * @Complejidad: 0(1)
	 */
	public Sala() {
		this.identificador = 0;
		this.kruskal=0;
		this.listaArmas= new ArrayList <Arma>();
		this.colaPersonajes=new LinkedList<Personaje>();
	}
	
	/**
	 * @PRE La sala tiene que estar inicializada y el arma que se inserta tiene que estar inicializada
	 * @param e = instancia de arma
	 * @Explicación Se inserta el arma en la lista ordenada por poder del arma
	 * @Post La lista de la sala contendr� el arma introducida
	 * @Complejidad: 0(n)
	 */
	public void insertarArma(Arma e) {
		if(listaArmas.isEmpty()) {
			this.listaArmas.add(e);
		}else {
			int i=0;
			boolean enc=false;
			while(i<listaArmas.size()&&!enc) {
				if(listaArmas.get(i).getPoder()<e.getPoder()) {
					this.listaArmas.add(i, e);
					enc=true;
				}
				i++;
			}	
			if(!enc) {
				this.listaArmas.add(e);
			}
		}	
	}
	/**
	 * @PRE 
	 * @Explicación Metodo que te devuelve el arma con mayor poder de la sala
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public Arma armaDeLaSalaConMayorPoder() {
		Arma aux = this.listaArmas.get(0);
		this.listaArmas.remove(0);
		return aux;
	}

	/**
	 * @PRE La lista de armas de la sala debe de estar inicializada
	 * @Explicación M�todo que devuelve true en caso de que haya al menos un arma en la lista de la sala y false en caso contrario
	 * @Post La sala queda inicializada
	 * @Complejidad: 0(1)
	 */
	public boolean hayArma() {
		if(this.listaArmas.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * @PRE La cola de personajes debe de estar llena
	 * @Explicación Se devuelve el primer personaje de la cola
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public Personaje getPrimeroCola() {
		return this.colaPersonajes.peek();
	}

	/**
	 * @PRE La sala debe de estar inicializada
	 * @Explicación Se devuelve el valor del atributo colaPersonajes que almacena los personajes de la sala
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public Queue<Personaje> getColaPersonajes() {
		return colaPersonajes;
	}

	/**
	 * @PRE 
	 * @param colaPersonajes = instancia de cola 
	 * @Explicación Se almacena en el atributo de la sala colaPersonajes el valor que se entra por parametro de entrada
	 * @Post El atributo colaPersonajes se ha modificado
	 * @Complejidad: 0(1)
	 */	
	public void setColaPersonajes(Queue<Personaje> colaPersonajes) {
		this.colaPersonajes = colaPersonajes;
	}
	
	
	/**
	 * @PRE debe de haber personajes en la cola de la sala
	 * @Explicación Se devuelve el primer villano que se encuentre en la sala en caso de que haya
	 * @Post 
	 * @Complejidad: 0(n)
	 */	
	public Personaje obtenerPrimerVillanoDeLaSala() {
		Personaje aux=null;
		Personaje x=null;
		boolean enc=false;
		int tam = this.colaPersonajes.size();
		while(0<tam) {
			aux=this.colaPersonajes.poll();		//Saco el personaje de la cola
			if(enc==false) {	//Si no he encontrado el villano aun
				if(aux.getEsSuperHeroe()==false) {	//Si es villano
					enc=true;
					x=aux;
					this.insertarPersonaje(aux);
				}else {
					this.insertarPersonaje(aux);
				}
			}else {
				this.insertarPersonaje(aux);
			}
			tam--;
		}
		return x;
	}
	
	/**
	 * @PRE debe de haber personajes en la cola de la sala
	 * @Explicación Se devuelve el primer superheroe que se encuentre en la sala en caso de que haya
	 * @Post 
	 * @Complejidad: 0(n)
	 */
	public Personaje obtenerPrimerSHDeLaSala() {
		Personaje aux=null;
		Personaje x=null;
		boolean enc=false;
		int tam = this.colaPersonajes.size();
		while(0<tam) {
			aux=this.colaPersonajes.poll();		//Saco el personaje de la cola
			if(enc==false) {	//Si no he encontrado el villano aun
				if(aux.getEsSuperHeroe()==true) {	//Si es superheroe
					enc=true;
					x=aux;
					this.insertarPersonaje(aux);
				}else {
					this.insertarPersonaje(aux);
				}
			}else {
				this.insertarPersonaje(aux);
			}
			tam--;
		}
		return x;
	}
	
	/**
	 * @throws ArmaException 
	 * @PRE la sala debe de estar inicializada
	 * @Explicación M�todo que hace que los personajes de la sala interactuen segun sus especificaciones
	 * @Post 
	 * @Complejidad: 0(n)
	 */
	public void procesar() throws ArmaException { 
		Mapa mapa = Mapa.obtenerInstancia();
		int tam = this.colaPersonajes.size();
		while(0<tam) {
			Personaje aux;
			aux = this.colaPersonajes.poll();
			if(aux.getTurno()==mapa.getTurno()) {
				if(aux.getSeHaMovido()==false) {
					aux.setSeHaMovido(true);
				}
				aux.procesar();
			}else {
				getColaPersonajes().add(aux);
			}
			
//			if(mapa.gethP().getEstadoPuerta()==false) {
//				
//				this.insertarPersonaje(aux);
//			}else {
////				System.out.println("Se inserta el personaje "+ aux.getNombre()+ " en la sala Teseracto");
//				mapa.getTeseracto().insertarPersonaje(aux);
//			}
			tam--;
		}
	}
	
	/**
	 * @PRE la sala debe de estar inicializada
	 * @Explicación Metodo que devuelve true en caso de que la cola de personajes de la sala est� vacia
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public boolean estaVacia() {
		return this.colaPersonajes.isEmpty();
	}
	/**
	 * @PRE debe de estar el arma que se quiere borrar en la lista de la sala
	 * @param a = instancia de arma
	 * @Explicación Se elimina el arma pasada por parametros de la lista de armas de la sala
	 * @Post Se elimina el arma de la lista de la sala
	 * @Complejidad: 0(1)
	 */
	public void eliminarArma(Arma a) {
		this.listaArmas.remove(a);
	}

	public void mostrarArmas(BufferedWriter escritura) throws IOException {
		String loQueVoyaVolvarEnElFichero = "";
		System.out.print("(square:"+getIdentificador()+":");
		loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+"(square:"+getIdentificador()+":";
		String x= new String();
		for(int i=0; i<getListaArmas().size();i++) {
			x=x+getListaArmas().get(i).toString();
		}
		System.out.println(x+")");
		loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+x+")"+"\n";
		escritura.flush();
		escritura.write(loQueVoyaVolvarEnElFichero);
	}

	public void mostrarPersonajes(BufferedWriter escritura) throws IOException {
		String loQueVoyaVolvarEnElFichero = "";
		Personaje aux;
		String x=new String();
		for(int i=0;i<this.getColaPersonajes().size();i++) {
			aux=getColaPersonajes().poll();
			x=aux.toString();
			System.out.println(x);
			loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+x+"\n";
			
			getColaPersonajes().add(aux);
		}
		escritura.write(loQueVoyaVolvarEnElFichero);
	}
	
}
