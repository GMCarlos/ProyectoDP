package Clases;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

import EstructurasDatos.Arcos;
import EstructurasDatos.Grafo;
import Excepciones.ArmaException;
import Excepciones.MapaException;
import Personajes.Personaje;
import EstructurasDatos.GenAleatorios;;

/**
 * EC1 - proyecto17_18 Implementacion de la clase Mapa que representa la EC1
 * dentro del proyecto
 * 
 * @version 3.0
 * @author <b> Carlos Guill�n Moreno </b><br>
 *         Asignatura Desarrollo de Programas<br/>
 *         Curso 17/18
 */
public class Mapa {
	/*Altura de la matriz del mapa*/
	private int alto;
	/*Anchura de la matriz del mapa*/
	private int ancho;
	/*Matriz de salas del mapa*/
	private Sala[][] matrizSalas; 
	/*Instancia del hP del mapa*/
	private HombrePuerta hP;
	/*Atributo estatico del mapa para acceder al mapa desde cualquier clase*/
	private static Mapa mapa=null;	//Singleton
	/*Sala Teseracto a la que se accede cuando se vence al hP*/
	private Sala Teseracto;
	/*Grafo que almacena los nodos y los arcos entre ellos del mapa*/
	private Grafo g;
	/*Turno de simulacion del mapa*/
	private int turno; 
	static final Integer MAXTURNOS = 100;
	
	
	
	public Sala[][] getMatrizSalas() {
		return matrizSalas;
	}

	public void setMatrizSalas(Sala[][] matrizSalas) {
		this.matrizSalas = matrizSalas;
	}

	public Grafo getG() {
		return g;
	}

	public void setG(Grafo g) {
		this.g = g;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	/**
	 * @PRE
	 * @Explicación Se devuelve el valor del atributo hP
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public HombrePuerta gethP() {
		return hP;
	}

	/**
	 * @PRE
	 * @param hP= instancia de hombrePuerta
	 * @Explicación Se asigna el valor hP de parametro de entrada al atributo hP del hP
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public void sethP(HombrePuerta hP) {
		this.hP = hP;
	}

	/**
	 * @param escritura 
	 * @throws MapaException 
	 * @throws IOException 
	 * @PRE
	 * @Explicación Se devuelve el valor del atributo hP
	 * @Post 
	 * @Complejidad: 0(n2)
	 */
	public void iniciarMapa(int ancho, int alto, int salaDailyPlanet, int alturaApertura, BufferedWriter escritura) throws MapaException, IOException {
		setAncho(ancho);
		setAlto(alto);
		gethP().setId(salaDailyPlanet);
		gethP().setAlturaAperturaCerradura(alturaApertura);
		inicializarSalas(alto, ancho);
		
		kruskal();
		
		

		//int[] idSalasConArmas = { 1, 2, 8, 14, 15, 21, 27, 35, 28, 29, 33, 34 };
		
		Arma [] armasPuerta = {new Arma("CampoEnergia", 5), new Arma("Armadura",13), new 
                Arma("Anillo",11), new Arma("Acido",1), new Arma("Antorcha",5), new 
                Arma("Bola",3), new Arma("Baston",22), new Arma("CadenaFuego",11), new 
                Arma("Espada",11), new Arma("Cetro",20), new Arma("Capa",10), new 
                Arma("CampoMagnetico",5), new Arma("Escudo",3), new Arma("Garra",22), new  
                Arma("Flecha",12), new Arma("Gema",4)};
		gethP().configurar(armasPuerta);		
		dibujarMapa(escritura);	//sin pared derribadas
		crearAtajos();
		repartoArmas();
		
	}
	

	private void mostrarInformacionPersonaje(BufferedWriter escritura) throws IOException {
		int tama�o, tama�o2;
		Personaje y;
		String loQueVoyaVolvarEnElFichero = "";
		
		
		//Mostra informacion de la sala
		for(int w=0; w<this.alto;w++) {
			for(int q=0; q<this.ancho;q++) {
				if(!matrizSalas[w][q].getColaPersonajes().isEmpty()) {
					tama�o=matrizSalas[w][q].getColaPersonajes().size();
					while(tama�o!=0) {
						y=this.matrizSalas[w][q].getColaPersonajes().poll();
						System.out.print("(path:"+y.getMarca()+":");
						loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+"(path:"+y.getMarca()+":";
						tama�o2=y.getColaDirecciones().size();
						while(tama�o2!=0) {
							Dir d;
							d=y.getColaDirecciones().poll();
							System.out.print(" "+d);
							loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+" "+d;
							y.getColaDirecciones().add(d);
							tama�o2--;
						}
						System.out.println(")");
						loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+")"+"\n";
						this.matrizSalas[w][q].insertarPersonaje(y);
						tama�o--;
					}
				}
			}
		}
		escritura.write(loQueVoyaVolvarEnElFichero);
//		System.out.println("(turn:"+this.turno+")");
//		System.out.println("(map:"+this.gethP().getId()+")");
//		System.out.println("(doorman:"+this.gethP().getEstadoPuerta()+":"+this.gethP().getAlturaAperturaCerradura()+":"+this.gethP().getArbolArmas().toString()+")");
		
	}

	/**
	 * @param escritura 
	 * @throws IOException 
	 * @PRE el mapa debe estar inicializado
	 * @Explicación Se representa el mapa mostrando los caminos entre salas y los personajes de las salas
	 * @Post se muestra por consola los datos
	 * @Complejidad: 0(n2)
	 */	
	public void dibujarMapa(BufferedWriter escritura) throws IOException {
		String loQueVoyaVolvarEnElFichero = "";
		int i,j;
		//BORDE DE ARRIBA
		for(i=0;i<this.ancho;i++) {
			System.out.print(" _");
			loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+" _";
		}
		System.out.println();
		loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+"\n";
		for(i=0; i<this.alto;i++) {
			//BORDE DE LA IZQUIERDA
			System.out.print("|");
			loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+"|";
			for(j=0;j<this.ancho;j++) {
				if(matrizSalas[i][j].getColaPersonajes().isEmpty()) {
					if(this.g.adyacente(i*this.ancho+j,i*this.ancho+j+this.ancho)) {
						System.out.print(" ");
						loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+" ";
					}else {
						System.out.print("_");
						loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+"_";
					}
					
				}else {
					if(matrizSalas[i][j].getColaPersonajes().size()==1) {
						Personaje x;
						x=matrizSalas[i][j].getPrimeroCola();
						System.out.print(x.getMarca());
						loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+x.getMarca();
					}else {
						System.out.print(matrizSalas[i][j].getColaPersonajes().size());
						loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+matrizSalas[i][j].getColaPersonajes().size();
					}
				}
				//si la celda de la derecha es adyacente no hay pared, sino s�
				if(this.g.adyacente(i*this.ancho+j, i*this.ancho+j+1)) {
					System.out.print(" ");
					loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+" ";
				}else {
					if(j!=this.ancho-1) {
						System.out.print("|");
						loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+"|";
					}	
				}
			}
			System.out.println("|");
			loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+"|"+"\n";
		}
		//System.out.println();
		escritura.write(loQueVoyaVolvarEnElFichero);

	}

	/**
	 * @PRE
	 * @Explicación Se devuelve el valor de la instancia del mapa
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public static Mapa obtenerInstancia() {
		if(mapa==null) {
			return mapa=new Mapa();
		}
		return mapa;
	}
	
	
	/**
	 * @PRE El alto y ancho deben ser mayor o igual que 0 , la salaHombrePuerta debe estar 
	 * entre el rango de valores que se generan del alto y ancho y la alturaCerradura
	 * debe ser mayor o igual que 0
	 * @param alto = numero de filas de la matriz
	 * @param ancho = numero de columnas de la matriz
	 * @Explicación Se genera un mapa con los parametros proporcionados por los parametros
	 * de entrada
	 * @Post Los valores del mapa deben coincidir con los parametros de entrada que se le
	 * asigna a cada atributo
	 * @Complejidad: 0(n2)
	 */
	private Mapa(int alto, int ancho) {
		/*
		 * Breve descripción del atributo
		 */
		int id=0;
		this.alto = alto;
		this.ancho = ancho;
		//preguntar Jose
		this.hP=new HombrePuerta();
		this.matrizSalas= new Sala[alto][ancho];
		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++) {
				this.matrizSalas[i][j]=new Sala(id);
				this.g.nuevoNodo(id);
				id++;
				
			}
			
		}
		this.setTeseracto(new Sala(1111));
	}
	
	/**
	 * @PRE el mapa debe estar inicializado
	 * @Explicación Se devuelve el valor de la sala de la matriz situado en la esquina inferior izquierda
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public int obtenerEsquinaAbajoIzquierda() {
		int x;
		x=this.getAlto()*this.getAncho()-1-(this.getAncho()-1);
		return x;
	}
	
	/**
	 * @PRE el mapa debe estar inicializado
	 * @Explicación Se devuelve el valor de la sala de la matriz situado en la esquina superior derecha
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public int obtenerEsquinaSuperiorDerecha() {
		int x;
		x=this.getAncho()-1;
		return x;
	}
	
	
	
	
	
	/**
	 * @PRE 
	 * @Explicacion Se genera un mapa con los valores por defecto
	 * @Post 
	 * @Complejidad: 0(n2)
	 */
	private Mapa() {
		int id=0;
		this.turno=0;
		this.alto = 0;
		this.ancho = 0;
		this.matrizSalas= new Sala[0][0];
		this.g=new Grafo();
		this.hP=new HombrePuerta();

		for (int i = 0; i < alto; i++) {
			for (int j = 0; j < ancho; j++) {
				this.matrizSalas[i][j]=new Sala(id);
				this.g.nuevoNodo(id);
				id++;
			}
			
		}
		this.Teseracto=new Sala(1111);
//		this.setTeseracto(new Sala(1111));
	}

	/**
	 * @PRE el mapa debe estar inicializado
	 * @Explicación Se devuelve los datos del mapa por medio de cadenas de texto
	 * @Complejidad: 0(1)
	 */
	@Override
	public String toString() {
		return "Mapa [alto=" + alto + ", ancho=" + ancho + ", matrizSalas=" + Arrays.toString(matrizSalas) + ", hP="
				+ hP + ", Teseracto=" + Teseracto + ", g=" + g + "]";
	}

	/**
	 * @PRE el mapa debe estar inicializado
	 * @Explicación Devuelve el alto del mapa
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public int getAlto() {
		return alto;
	}

	/**
	 * @PRE El parametro alto debe ser mayor o igual que 0
	 * @param alto = alto del mapa
	 * @throws MapaException 
	 * @Explicación Establece el alto del mapa a partir del parametro de entrada alto
	 * @Post El alto del mapa tiene que coincidir con el parametro de entrada
	 * @Complejidad: 0(1)
	 */
	public void setAlto(int alto) throws MapaException {
		if(alto<=0) {
			throw(new MapaException ("El alto no puede ser <= que 0"));
		}
		this.alto = alto;
	}
	
	/**
	 * @PRE el mapa debe estar inicializado
	 * @Explicación Devuelve el valor de la sala Teseracto del mapa
	 * @Complejidad: 0(1)
	 */
	public Sala getTeseracto() {
		return Teseracto;
	}

	/**
	 * @PRE el alto y el ancho deben ser mayor que 0
	 * @param alto= alto del mapa
	 * @param ancho= ancho del mapa
	 * @Explicación Reserva espacio para las salas de la matriz y las inicializa, adem�s, inicializa los nodos del grafo
	 * @Complejidad: 0(n2)
	 */
	public void inicializarSalas(int alto, int ancho) {
		int id=0;
		matrizSalas= new Sala[alto][ancho];
		for (int i = 0; i < alto; i++) {
			for (int j = 0; j < ancho; j++) {
					matrizSalas[i][j] = new Sala(id);
					//nodos de la matriz
					this.g.nuevoNodo(id);
					id++;	
			}
			
		}
	}
	
	/**
	 * @PRE el mapa debe estar inicializado
	 * @Explicación Metodo que crea los arcos entre las salas que sean adyacentes
	 * @Post Devuelve una lista con los arcos creados
	 * @Complejidad: 0(n2)
	 */
	public ArrayList<Arcos> crearArcos() {
		ArrayList <Arcos> listaArcos= new ArrayList <Arcos>();
		int origen;
		int destino;
		for(int i=0;i<this.alto;i++) {
			for(int j=0;j<this.ancho;j++) {
				//NORTE de la casilla
				if(i>0) {
					origen=i*this.ancho+j;
					destino=i*this.ancho+j-(this.ancho);		//una fila menos
					listaArcos.add(new Arcos(origen,destino));
				}
				//ESTE de la casilla
				if(j<this.ancho-1) {
					origen=i*this.ancho+j;
					destino=i*this.ancho+j+1;		//una m�s hacia la derecha
					listaArcos.add(new Arcos(origen,destino));
				}
				//SUR de la casilla
				if(i<this.alto-1) {
					origen=i*this.ancho+j;
					destino=i*this.ancho+j+(this.ancho);		//una fila m�s
					listaArcos.add(new Arcos(origen,destino));
				}
				//OESTE de la casilla
				if(j>0) {
					origen=i*this.ancho+j;
					destino=i*this.ancho+j-1;				//una menos hacia la izquierda
					listaArcos.add(new Arcos(origen,destino));
				}
			}
		}
		
		return listaArcos;
	}
	
	/**
	 * @PRE el mapa debe estar inicializado
	 * @param marcaNueva = marca de Kruskal que se almacena en las salas donde su marca anterior se "marcaNueva"
	 * @param marcaAntigua = marca de Kruskal que se quiere eliminar de la/s sala/s
	 * @Explicación Metodo que cambia la marca de Kruskal a aquellas salas que sean iguales a la que se introduce por parametro de entrada
	 *  "marcaAntigua" y las cambia por el valor del parametro de entrada "marcaNueva"
	 * @Post Las salas que se tratan cambiaran su marca por la de marcaNueva
	 * @Complejidad: 0(n2)
	 */
	public void cambiarMarcaKruskal(int marcaNueva, int marcaAntigua ) {
		for(int i=0; i<this.alto;i++) {
			for(int j=0; j<this.ancho;j++) {
				if(this.matrizSalas[i][j].getKruskal()==marcaAntigua) {
					this.matrizSalas[i][j].setKruskal(marcaNueva);
				}
			}
		}
	}
	
	
	/**
	 * @PRE el mapa debe estar inicializado
	 * @Explicación Metodo que conecta todas las salas del mapa y propaga la marca de Kruskal por todas las salas garantizando que
	 *  todas las salas quedan conectadas, es decir, que se puede acceder a cualquier sala a partir de otra.  
	 * @Post Todas las salas quedar�n con la misma marca de Kruskal
	 * @Complejidad: 0(n3)
	 */	
	public void kruskal() {
		ArrayList <Arcos> listaArcos;
		listaArcos=crearArcos();
		while(!listaArcos.isEmpty()) {
			int numeroAleatorio= GenAleatorios.generarNumero(listaArcos.size());
			Arcos x=listaArcos.get(numeroAleatorio);
			//cambiar aqui para que me muestre origen y destino los identificadores
			listaArcos.remove(numeroAleatorio);
			Sala origen=getSala(x.getOrigen());
			Sala destino=getSala(x.getDestino());	
			if(origen.getKruskal()!=destino.getKruskal() && !this.g.adyacente(x.getOrigen(), x.getDestino())) {
				this.g.nuevoArco(x.getOrigen(), x.getDestino(), 1);
				this.g.nuevoArco(x.getDestino(), x.getOrigen(), 1);
				cambiarMarcaKruskal(origen.getKruskal(), destino.getKruskal());
			}
		}
	}
	
	/**
	 * @PRE el mapa debe estar inicializado
	 * @Explicación Metodo que dado los nodos del grafo crea arcos nuevos entre los nodos equivalente al 5 por ciento del total de nodos de forma aleatoria. 
	 * @Post Se a�adir� al grafo nuevos arcos
	 * @Complejidad: 0(n)
	 */	
	public void crearAtajos() {
		int numeroAleatorio;
		int cincoPorCientoAtajos = (int) (0.05 * (this.alto * this.ancho));
		//System.out.println(cincoPorCientoAtajos);
		while (cincoPorCientoAtajos != 0) {
			numeroAleatorio = GenAleatorios.generarNumero(this.alto * this.ancho);
			
			// NORTE
			if (!this.g.adyacente(numeroAleatorio, numeroAleatorio - this.ancho)&& getCoordenadas(numeroAleatorio)[0]-1>=0 && numeroAleatorio-this.ancho>=0)  {
				// hay pared con la del Norte
				if (!comprobarSiSeCreanEspaciosEnBlancoNorte(numeroAleatorio)) {
					tirarPared(numeroAleatorio, numeroAleatorio-this.ancho);
					cincoPorCientoAtajos--;
				}
			}

			// SUR
			if (!this.g.adyacente(numeroAleatorio, numeroAleatorio + this.ancho)&& getCoordenadas(numeroAleatorio)[0]+1<=this.alto && numeroAleatorio+this.ancho<this.alto*this.ancho) {
				// hay pared con el Sur
				if (!comprobarSiSeCreanEspaciosEnBlancoSur(numeroAleatorio)) {
					tirarPared(numeroAleatorio, numeroAleatorio+this.ancho);
					cincoPorCientoAtajos--;
				}
			}

			// OESTE
			if (!this.g.adyacente(numeroAleatorio, numeroAleatorio - 1)&& getCoordenadas(numeroAleatorio)[1]-1>=0 && numeroAleatorio-1>=0) {
				// hay pared con el oeste
				if (!comprobarSiSeCreanEspaciosEnBlancoOeste(numeroAleatorio)) {
					tirarPared(numeroAleatorio, numeroAleatorio-1);
					cincoPorCientoAtajos--;
				}
			}
			// ESTE
			if (!this.g.adyacente(numeroAleatorio, numeroAleatorio + 1) && getCoordenadas(numeroAleatorio)[1]+1<this.ancho && numeroAleatorio+1<this.alto*this.ancho) {
				// hay pared con el este
				if (!comprobarSiSeCreanEspaciosEnBlancoEste(numeroAleatorio)) {
					tirarPared(numeroAleatorio, numeroAleatorio + 1);
					cincoPorCientoAtajos--;
				}
			}
		}
		g.warshall();
		g.floyd();
	}
	
	/**
	 * @PRE el mapa debe estar inicializado
	 * @param origen = origen del arco
	 * @param destino = destino del arco
	 * @Explicación Metodo auxiliar que crea dos nuevos arcos al derribar una pared del mapa
	 * @Post El grafo tendr� dos nuevos arcos
	 * @Complejidad: 0(1)
	 */	
	private void tirarPared(int origen, int destino) {
		this.g.nuevoArco(origen, destino, 1);
		this.g.nuevoArco(destino, origen, 1);
	}
	
	
	/**
	 * @PRE el mapa debe estar inicializado
	 * @param numero = id sala de referencia para saber si se crean espacios en blanco en el norte
	 * @Explicación Metodo auxiliar que comprueba si se crean espacios en blanco al tirar la pared norte de la sala que se pasa por parametros
	 * @Post Devuelve true si se crea un espacio en blanco y false en caso contrario
	 * @Complejidad: 0(1)
	 */	
	private boolean comprobarSiSeCreanEspaciosEnBlancoNorte(int numero) {
		boolean aux=false;
		//por arriba a la izquierda
		if(this.g.adyacente(numero-this.ancho-1, numero-this.ancho)&&this.g.adyacente(numero-this.ancho-1, numero-1)&&this.g.adyacente(numero-1, numero)) {
			aux=true;
		}
		//por arriba a la derecha
		if(this.g.adyacente(numero-this.ancho+1, numero-this.ancho)&&this.g.adyacente(numero-this.ancho+1, numero+1)&&this.g.adyacente(numero+1, numero)) {
			aux=true;
		}
		
		return aux;
		
	}
	
	/**
	 * @PRE el mapa debe estar inicializado
	 * @param numero = id sala de referencia para saber si se crean espacios en blanco en el sur
	 * @Explicación Metodo auxiliar que comprueba si se crean espacios en blanco al tirar la pared sur de la sala que se pasa por parametros
	 * @Post Devuelve true si se crea un espacio en blanco y false en caso contrario
	 * @Complejidad: 0(1)
	 */	
	private boolean comprobarSiSeCreanEspaciosEnBlancoSur(int numero) {
		boolean aux=false;
		//por abajo a la izquierda
		if(this.g.adyacente(numero+this.ancho-1, numero+this.ancho)&&this.g.adyacente(numero+this.ancho-1, numero-1)&&this.g.adyacente(numero-1, numero)) {
			aux=true;
		}
		//por abajo a la derecha
		if(this.g.adyacente(numero+this.ancho+1, numero+this.ancho)&&this.g.adyacente(numero+this.ancho+1, numero+1)&&this.g.adyacente(numero+1, numero)) {
			aux=true;
		}
		return aux;
		
	}
	
	/**
	 * @PRE el mapa debe estar inicializado
	 * @param numero = id sala de referencia para saber si se crean espacios en blanco en el oeste
	 * @Explicación Metodo auxiliar que comprueba si se crean espacios en blanco al tirar la pared oeste de la sala que se pasa por parametros
	 * @Post Devuelve true si se crea un espacio en blanco y false en caso contrario
	 * @Complejidad: 0(1)
	 */	
	private boolean comprobarSiSeCreanEspaciosEnBlancoOeste(int numero) {
		boolean aux=false;
		if(this.g.adyacente(numero-this.ancho-1, numero-this.ancho)&&this.g.adyacente(numero-this.ancho-1,numero-1)&&this.g.adyacente(numero-this.ancho, numero)) {
			aux=true;
		}
		
		if(this.g.adyacente(numero+this.ancho-1, numero+this.ancho)&&this.g.adyacente(numero+this.ancho-1, numero-1)&&this.g.adyacente(numero+this.ancho, numero)) {
			aux=true;
		}
		return aux;
		
	}
	
	/**
	 * @PRE el mapa debe estar inicializado
	 * @param numero = id sala de referencia para saber si se crean espacios en blanco en el este
	 * @Explicación Metodo auxiliar que comprueba si se crean espacios en blanco al tirar la pared este de la sala que se pasa por parametros
	 * @Post Devuelve true si se crea un espacio en blanco y false en caso contrario
	 * @Complejidad: 0(1)
	 */	
	private boolean comprobarSiSeCreanEspaciosEnBlancoEste(int numero) {
		boolean aux=false;
		
		if(this.g.adyacente(numero-this.ancho+1, numero-this.ancho)&&this.g.adyacente(numero-this.ancho+1,numero+1)&&this.g.adyacente(numero-this.ancho, numero)) {
			aux=true;
		}
		
		if(this.g.adyacente(numero+this.ancho+1, numero+this.ancho)&&this.g.adyacente(numero+this.ancho+1, numero+1)&&this.g.adyacente(numero+this.ancho, numero)) {
			aux=true;
		}
		return aux;
		
	}

	/**
	 * @PRE 
	 * @param teseracto = instancia de un objeto Sala
	 * @Explicación Metodo que almacena en el atributo del mapa Teseracto la instancia de sala que se pasa por el parametro teseracto
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	
	public void setTeseracto(Sala teseracto) {
		Teseracto = teseracto;
	}

	/**
	 * @PRE 
	 * @Explicacion Devuelve el ancho del mapa
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * @PRE El ancho debe ser mayor o igual que 0
	 * @param ancho = numero de filas de la matriz
	 * @throws MapaException 
	 * @Explicacion Establece el ancho del mapa
	 * @Post El ancho del mapa tiene que coincidir con el parametro de entrada
	 * @Complejidad: 0(1)
	 */
	public void setAncho(int ancho) throws MapaException {
		if(ancho<=0) {
			throw(new MapaException ("El ancho no puede ser <= que 0"));
		}
		this.ancho = ancho;
	}






	/**
	 * @PRE el mapa tiene que estar inicializado
	 * @param idSalasConArmas = vector de enteros que representan las salas donde va a haber armas
	 * @param armasSalas = vector de armas que almacena las armas que se van a repartir por el mapa
	 * @Explicacion Dado un vector de enteros que indican las salas donde hay armas y otro vector 
	 * con las armas, se repartir� por cada sala 5 armas
	 * @Post Las armas deben estar almacenadas en sus respectivas salas
	 * @Complejidad: 0(n3)
	 */
   	public void distribuirArmas(int[] idSalasConArmas, Arma[] armasSalas) {
   		int j=0;	//indice para recorrer el vector de armasSalas
   		int x;
		for(int i=0; i<idSalasConArmas.length; i++) {
			int[] aux = new int [2];
			aux=getCoordenadas(idSalasConArmas[i]);	//se almacena las coordenadas de la sala
			x =5;
			while(j<armasSalas.length && x!=0) {
				this.matrizSalas[aux[0]][aux[1]].insertarArma(armasSalas[j]);
				x--;	//decremento para meter solo 5 armas por sala
				j++;	//siguiente ARMA
			}
			
		}
	}
				
	
	/**
	 * @PRE 
	 * @param doorMan = instancia de un objeto hombrePuerta
	 * @Explicación Metodo que almacena en el atributo del mapa hP la instancia de sala que se pasa por el parametro doorMan
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public void insertarHombrePuerta(HombrePuerta doorMan) {
		this.hP=doorMan;
	}
	
	public void mostrarInformacionTurno(BufferedWriter escritura) throws IOException {
		String loQueVoyaVolvarEnElFichero = "";
		System.out.println("(turn:" + getTurno() + ")");
		loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+"(turn:" + getTurno() + ")"+"\n";
		System.out.println("(map:" + gethP().getId() + ")");
		loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+"(map:" + gethP().getId() + ")"+"\n";
		if(gethP().getEstadoPuerta()==false) {
			System.out.println("(doorman:closed:"
					+ this.gethP().getAlturaAperturaCerradura() + ":" + this.gethP().getArbolArmas().toString() + ")");
			loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+"(doorman:closed:"
					+ this.gethP().getAlturaAperturaCerradura() + ":" + this.gethP().getArbolArmas().toString() + ")"+"\n";
		}else {
			System.out.println("(doorman:open:"
					+ this.gethP().getAlturaAperturaCerradura() + ":" + this.gethP().getArbolArmas().toString() + ")");
			loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+"(doorman:open:"
					+ this.gethP().getAlturaAperturaCerradura() + ":" + this.gethP().getArbolArmas().toString() + ")"+"\n";
		}
		escritura.write(loQueVoyaVolvarEnElFichero);
		
		
	}
	
	public void mostrarArmasTurno(BufferedWriter escritura) throws IOException {
		int i, j;
		// recorro cada sala de la matriz
		i = 0;
		// aqu� muestro las armas de la sala
		while (i < this.alto) {
			j = 0;
			while (j < this.ancho) {
				// si hay armas en la sala, las muestro
				if (!this.matrizSalas[i][j].getListaArmas().isEmpty()) {
					this.matrizSalas[i][j].mostrarArmas(escritura);
				}

				j++;
			}
			i++;
		}
	}
	
	public void mostrarPersonajesTurno(BufferedWriter escritura) throws IOException {
		int i, j;
		i = 0;
		while (i < this.alto) {
			j = 0;
			while (j < this.ancho) {
				// si hay armas en la sala, las muestro
				if (!this.matrizSalas[i][j].getColaPersonajes().isEmpty()) {
					this.matrizSalas[i][j].mostrarPersonajes(escritura);
				}
				j++;
			}
			i++;
		}

	}
	
	public void procesarPersonajes() throws ArmaException {
		int i,j;
		// interacciones de cada turno
		i = 0;
		while (i < this.alto) {
			j = 0;
			while (j < this.ancho) {
				// si hay armas en la sala, las muestro
				if (!this.matrizSalas[i][j].getColaPersonajes().isEmpty()) {
					this.matrizSalas[i][j].procesar();
				}
				j++;
			}
			i++;
		}
		
	}
	
	/**
	 * @param escritura 
	 * @throws MapaException 
	 * @throws IOException 
	 * @throws ArmaException 
	 * @PRE En el mapa debe de haber elementos en las salas que lo forman
	 * @Explicación Se simula la interaccion de los elementos del mapa durante una
	 *               serie de turnos
	 * @Post
	 * @Complejidad: 0(n3)
	 */
	public void simulacion(BufferedWriter escritura) throws MapaException, IOException, ArmaException {
			
		// Muestra la ruta de los personajes
		mostrarInformacionPersonaje(escritura);
		
		while (!this.hP.getEstadoPuerta() && getTurno() < MAXTURNOS) {
			procesarPersonajes();
			//1
			mostrarInformacionTurno(escritura);
			//2
			dibujarMapa(escritura);
			//3
			mostrarArmasTurno(escritura);
			//4
			mostrarPersonajesTurno(escritura);
			
			// aumento turno
			setTurno(getTurno() + 1);
			escritura.flush();
			
			
		}
		int i;
		String loQueVoyaVolvarEnElFichero=new String();
		if(!mapa.getTeseracto().getColaPersonajes().isEmpty()) {
			i=mapa.getTeseracto().getColaPersonajes().size();
			System.out.println("(teseractomembers)");
			loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+"(teseractomembers)"+"\n";
			for(int j=0;j<i;j++) {
				Personaje aux;
				if(j==0) {
					aux=mapa.getTeseracto().getColaPersonajes().poll();
					System.out.println("(owneroftheworld:"+aux.toString()+")");
					loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+"(owneroftheworld:"+aux.toString()+"\n";
					mapa.getTeseracto().insertarPersonaje(aux);
				}else {
					aux=mapa.getTeseracto().getColaPersonajes().poll();
					System.out.println(aux.toString());
					loQueVoyaVolvarEnElFichero=loQueVoyaVolvarEnElFichero+aux.toString()+"\n";
					mapa.getTeseracto().insertarPersonaje(aux);
				}
				
			}
			
			
		}
		escritura.flush();
		escritura.write(loQueVoyaVolvarEnElFichero);
		
		
	}
	
	public int obtenerSalaSinSalidaEste() {
		int x;
		
			for(int j=getAncho()-1; j>=0; j--) {
				for(int i=0; i<getAlto(); i++) {
				x=0;
				//No adyacente con oeste

			if(!mapa.getG().adyacente(i*getAncho()+j, i*getAncho()+j -1)) {
					x++;
				}
				//no adyacente con este

			if(!mapa.getG().adyacente(i*getAncho()+j, i*getAncho()+j+1)) {
					x++;
			}
				//no adyacente con Norte

				if(!mapa.getG().adyacente(i*getAncho()+j, i*getAncho()+j - getAncho())) {
					x++;
				}
				//no adyacente con Sur

			if(!mapa.getG().adyacente(i*getAncho()+j, i*getAncho()+j + getAncho())) {
				x++;
				}
				if(x==3) {
					int solucion = i*getAncho()+j ;
					return solucion;
				}
			}
		}
		return 0;
	}
	
	/**
	 * @PRE el id de la sala tiene que estar en el rango correspondiente de valores de 0 a alto por ancho
	 * @param idSala = numero que identifica la sala del mapa
	 * @Explicación Devuelve un vector de dos posiciones que corresponden al alto y ancho de la matriz de salas del mapa.
	 * @Post Se almacena el numero de filas en la posicion 0 (alto) y el numero de columnas en la posicion 1 (ancho)
	 * @Complejidad: 0(1)
	 */
	//alto=aux[0] = y (fila) 
	//ancho=aux[1] = x (columna)
	public int[] getCoordenadas(int idSala) {
		int aux[]= new int[2];
		aux[0]=idSala/this.ancho;
		aux[1]=idSala%this.ancho;
		return aux;
	}
	
	/**
	 * @PRE el id de la sala tiene que estar en el rango correspondiente de valores de 0 a alto por ancho
	 * @param numSalaDondeSeEncuentra = numero que identifica la sala del mapa 
	 * @Explicación Devuelve la sala de la matriz con el id que se pasa por parametro
	 * @Post Explicación de la postCondición
	 * @Complejidad: 0(1)
	 */
	public Sala getSala(int numSalaDondeSeEncuentra) {
		int[] aux= new int[2];
		aux=getCoordenadas(numSalaDondeSeEncuentra);
		return this.matrizSalas[aux[0]][aux[1]];
	}

	/**
	 * @PRE El personaje debe estar inicializado y la salaDailyPlanet debe estar entre el rango de salas de la matriz del mapa
	 * @param ironMan = Personaje que se inserta
	 * @param sala = sala del mapa
	 * @Explicación Guarda en la sala el personaje
	 * @Post se almacena en la salaDailyPlanet el personaje
	 * @Complejidad: 0(1)
	 */
	public void insertarPersonaje(Personaje ironMan, int sala) {
		int aux[]= new int[2];
		aux[0]=sala/this.ancho;
		aux[1]=sala%this.ancho;
		this.matrizSalas[aux[0]][aux[1]].insertarPersonaje(ironMan);
		ironMan.setNumSalaDondeSeEncuentra(sala);
	}
	
	
	
	
	//metodo que rellene el mapa con la lista de lista
	//recorro las salas mas frecuentadas y guardo el identificador de la lista en la sala los 12 primeros que va a ser donde se guarden las armas
	//y despues distribuyo las armas por las salas que he guardado que son las m�s transitadas


	public void repartoArmas() throws MapaException {
		int i, j;
		int idSalaPisada;
		int frecuencia;
		Queue<Integer> aux=new LinkedList<Integer>();
		Queue<Integer> camino=new LinkedList<Integer>();
		Queue<Queue> colaDeColas=new LinkedList<Queue>();
		generarPosiblesRutas(0, camino, colaDeColas);
		//se llenaria el colaDeColas con todos los caminos
		Map<Integer, Integer> tabla = new TreeMap<Integer, Integer>();
		//inicializo los elementos a 0
		for(i=0;i<(getAlto()*getAncho());i++) {
			tabla.put(i, 0);
		}
		//inserto los idDeSalas y su frecuencia en tabla
		for(i=0; i<colaDeColas.size();i++) {
			aux=colaDeColas.poll();
			for(j=0;j<aux.size();j++) {
				idSalaPisada=aux.poll();
				frecuencia=tabla.get(idSalaPisada);	//
//				tabla.remove(idSalaPisada);		
				frecuencia++;
				tabla.put(idSalaPisada, frecuencia);	//se renueva la frecuencia si ya existe ese indice en la tabla
				aux.add(idSalaPisada);
			}
			colaDeColas.add(aux);
		}
		//tabla queda rellena con los indices de los caminos y sus respectivos caminos
		//System.out.println(tabla.toString());
		//Consigo las 12 salas con mas frecuencia, reuso la variable auxiliar frecuencia para almacenar la mayor frecuencia de las salas
		frecuencia=0;
		for(i=0;i<(getAlto()*getAncho());i++) {
			if(tabla.get(i)>frecuencia) {
				frecuencia=tabla.get(i);
			}
		}
		
		Queue<Integer> listaSalas = new LinkedList<Integer>();
		
		
		//ahora tengo la maxima frecuencia de la sala, desde la que parto para insertar
		//creo la cola de 12 elementos que se corresponder� con las salas m�s transitadas
		//frecuencia=FRECUENCIA MAXIMA DESDE LA QUE EMPIEZO A COMPARAR

		while(listaSalas.size()<12 && frecuencia>=0) {	//mientras que no haya 12 elementos en la lista
			for(i=0;i<(getAlto()*getAncho());i++) {
				if(tabla.get(i)==frecuencia && listaSalas.size()<12) {
					listaSalas.add(i);
				}
			}
			frecuencia--;
		}
		
		//System.out.println(listaSalas.toString());
		
		int[] idSalasConArmas= new int[12];
		for(i=0;i<12;i++) {
			idSalasConArmas[i]=listaSalas.poll();
		}
		
		// poner armas en las salas
				Arma[] armasSalas = { new Arma("Mjolnir", 29), new Arma("Anillo", 1), new Arma("Garra", 27),
						new Arma("Armadura", 3), new Arma("Red", 25), new Arma("Escudo", 5), new Arma("Lucille", 23),
						new Arma("Lawgiver", 7), new Arma("GuanteInfinito", 21), new Arma("LazoVerdad", 9),
						new Arma("CadenaFuego", 19), new Arma("Capa", 11), new Arma("Flecha", 17), new Arma("Tridente", 13),
						new Arma("Antorcha", 15), new Arma("Baston", 28), new Arma("Latigo", 2), new Arma("MazaOro", 26),
						new Arma("CampoMagnetico", 4), new Arma("Tentaculo", 24), new Arma("CampoEnergia", 6),
						new Arma("Cetro", 22), new Arma("RayoEnergia", 8), new Arma("Laser", 20), new Arma("Bola", 10),
						new Arma("Espada", 18), new Arma("Sable", 12), new Arma("Acido", 16), new Arma("Gema", 14),
						new Arma("Nullifier", 23), new Arma("Mjolnir", 1), new Arma("Anillo", 29), new Arma("Garra", 3),
						new Arma("Armadura", 27), new Arma("Red", 5), new Arma("Escudo", 25), new Arma("Lucille", 7),
						new Arma("Lawgiver", 23), new Arma("GuanteInfinito", 9), new Arma("LazoVerdad", 21),
						new Arma("CadenaFuego", 11), new Arma("Capa", 19), new Arma("Flecha", 13), new Arma("Tridente", 17),
						new Arma("Antorcha", 28), new Arma("Baston", 15), new Arma("Latigo", 26), new Arma("MazaOro", 2),
						new Arma("CampoMagnetico", 24), new Arma("Tentaculo", 4), new Arma("CampoEnergia", 22),
						new Arma("Cetro", 6), new Arma("RayoEnergia", 20), new Arma("Laser", 8), new Arma("Bola", 18),
						new Arma("Espada", 10), new Arma("Sable", 16), new Arma("Acido", 12), new Arma("Gema", 1),
						new Arma("Nullifier", 3) };
		
		distribuirArmas(idSalasConArmas, armasSalas);
		
		
	}

	public void generarPosiblesRutas(int idSala, Queue<Integer> sol, Queue<Queue> colaDeColas)throws MapaException {
		int salaTransitada;
		if(idSala>=(getAlto()*getAncho()) || idSala<0){
			throw(new MapaException ("El idSala no se puede salir del rango de salas de la matriz"));
		}
		//retomo lo que habia hasta que llegue a la sala final, sala Dailyplanet
		Queue<Integer> solucionFinal = new LinkedList<Integer>(sol);
		solucionFinal.add(idSala);
		if(idSala==mapa.getAlto()*mapa.getAncho()-1) {
			Queue<Integer> solucion = new LinkedList<Integer>(sol);
			solucion.add(idSala);
			colaDeColas.add(solucion);
		}
		Set<Integer> Adyacentes = new LinkedHashSet<Integer>(sol);
		//guardo en Adyacentes los nodos adyacentes de idSala
		getG().adyacente(idSala, Adyacentes);
		java.util.Iterator<Integer> iter = Adyacentes.iterator();
		
		while(iter.hasNext()) {
			salaTransitada=iter.next();
			if(!sol.contains(salaTransitada)) {
				generarPosiblesRutas(salaTransitada,solucionFinal, colaDeColas);
			}
		}
		
		
	}
	
	
	

	/**
       * Programa principal - EC1.
       * @param args Argumentos que recibe el programa principal
       * @return Retorna la salida del programa
	 * @throws MapaException 
       */
       public static void main (String args[]) throws MapaException {
      
             // Creación del mapa
          // Parámetros: sala del Daily Planet, nº columnas, nº filas y profundidad de apertura del portal
          int dimX = 6;
          int dimY = 6;
          int salaDailyPlanet = (dimX * dimY) - 1;
          int alturaApertura = 4;
    //Mapa mapa = new Mapa(dimY, dimX, salaDailyPlanet, alturaApertura);
   
    //Esto se hace por el Singleton
          Mapa mapa = Mapa.obtenerInstancia();
          mapa.setAlto(dimX);
          mapa.setAncho(dimY);

          mapa.inicializarSalas(dimX, dimY);
          
          

    // Creación de las armas para el hombre puerta
             Arma [] armasPuerta = {new Arma("CampoEnergia", 5), new Arma("Armadura",13), new 
                                        Arma("Anillo",11), new Arma("Acido",1), new Arma("Antorcha",5), new 
                                        Arma("Bola",3), new Arma("Baston",22), new Arma("CadenaFuego",11), new 
                                        Arma("Espada",11), new Arma("Cetro",20), new Arma("Capa",10), new 
                                        Arma("CampoMagnetico",5), new Arma("Escudo",3), new Arma("Garra",22), new  
                                        Arma("Flecha",12), new Arma("Gema",4)};

             // Creación del hombre puerta y configuración
             HombrePuerta doorMan = new HombrePuerta();
    // Configurar el hombre puerta introduciendo la combinación de armas
          doorMan.configurar(armasPuerta);
          doorMan.setAlturaAperturaCerradura(alturaApertura);
          

          // Cerrar el portal, por si inicialmente está abierto
          doorMan.cerrar();
          
             // Añadir el hombre puerta al mapa 
          mapa.insertarHombrePuerta(doorMan);
          
          //meto en hombrePuerta en la ultima sala del mapa
          mapa.gethP().setId(salaDailyPlanet);
          // Creación de las armas para repartir en salas
          //int numArmasSalas = 60;
                Arma [] armasSalas = {new Arma("Mjolnir",29), new Arma("Anillo",1), new Arma("Garra",27), 
                            new Arma("Armadura",3), new Arma("Red",25), new Arma("Escudo",5), 
                            new Arma("Lucille",23), new Arma("Lawgiver",7), new Arma("GuanteInfinito",21), 
                            new Arma("LazoVerdad",9), new Arma("CadenaFuego",19), new Arma("Capa",11), 
                            new Arma("Flecha",17), new Arma("Tridente",13), new  Arma("Antorcha",15), 
                            new Arma("Baston",28), new Arma("Latigo",2), new  Arma("MazaOro",26), 
                            new Arma("CampoMagnetico",4), new Arma("Tentaculo",24), 
                            new Arma ("CampoEnergia",6), new Arma("Cetro",22), new Arma("RayoEnergia",8), 
                            new Arma("Laser",20), new Arma("Bola",10), new Arma("Espada",18), 
                            new Arma("Sable",12),  new Arma("Acido",16), new Arma("Gema",14), 
                            new Arma("Nullifier",23), new Arma("Mjolnir",1), new Arma("Anillo",29), 
                            new Arma("Garra",3), new Arma("Armadura",27),  new Arma("Red",5), 
                            new Arma("Escudo",25), new Arma("Lucille",7), new  Arma("Lawgiver",23), 
                            new Arma("GuanteInfinito",9), new Arma("LazoVerdad",21), 
                            new Arma("CadenaFuego",11), new Arma("Capa",19), new Arma("Flecha",13), 
                            new Arma("Tridente",17), new Arma("Antorcha",28), new Arma("Baston",15), 
                            new Arma("Latigo",26), new Arma("MazaOro",2), new Arma("CampoMagnetico",24), 
                            new Arma("Tentaculo",4), new Arma("CampoEnergia",22), new Arma("Cetro",6), 
                            new Arma("RayoEnergia",20), new Arma("Laser",8), new Arma("Bola",18), 
                            new Arma("Espada",10), new Arma("Sable",16), new Arma("Acido",12), 
                            new Arma("Gema",1), new Arma("Nullifier",3)};

    int [] idSalasConArmas = {1, 2, 8, 14, 15, 21, 27, 35, 28, 29, 33, 34};     
    mapa.distribuirArmas(idSalasConArmas, armasSalas); 
   // La distribución de armas quedará de la siguiente forma:
   // (sala:1: {Mjolnir,29}, {Anillo,1}, {Garra,27}, {Armadura,3}, {Red,25},)
   // (sala:2: {Escudo,5}, {Lucille,23}, {Lawgiver,7}, {GuanteInfinito,21}, {LazoVerdad,9},)
   // (sala:8: {CadenaFuego,19}, {Capa,11}, {Flecha,17}, {Tridente,13}, {Antorcha,15},)
   // (sala:14: {Baston,28}, {Latigo,2}, {MazaOro,26}, {CampoMagnetico,4}, {Tentaculo,24},)
   // (sala:15: {CampoEnergia,6}, {Cetro,22}, {RayoEnergia,8}, {Laser,20}, {Bola,10},)
   // (sala:21: {Espada,18}, {Sable,12}, {Acido,16}, {Gema,14}, {Nullifier,23},)
   // (sala:27: {Mjolnir,1}, {Anillo,29}, {Garra,3}, {Armadura,27}, {Red,5},)
   // (sala:35: {Escudo,25}, {Lucille,7}, {Lawgiver,23}, {GuanteInfinito,9}, {LazoVerdad,21},)
   // (sala:28: {CadenaFuego,11}, {Capa,19}, {Flecha,13}, {Tridente,17}, {Antorcha,28},)
   // (sala:29: {Baston,15}, {Latigo,26}, {MazaOro,2}, {CampoMagnetico,24}, {Tentaculo,4},)
   // (sala:33: {CampoEnergia,22}, {Cetro,6}, {RayoEnergia,20}, {Laser,8}, {Bola,18},)
   // (sala:34: {Espada,10}, {Sable,16}, {Acido,12}, {Gema,1}, {Nullifier,3})

           // Creación de varios personajes
//         //  SuperHeroe thor = new SuperHeroe("Thor", 'T');
//           //New: Thor"s weapons 
//           thor.addWeapon(new Arma(25, "Baston"));
//           thor.addWeapon(new Arma(15, "Armadura"));
//           thor.addWeapon(new Arma(50, "Mjolnir"));
//           thor.addWeapon(new Arma(12, "Lawgiver"));
//           mapa.insertarPersonaje(thor,salaDailyPlanet);
//
//           SuperHeroe ironMan = new SuperHeroe("IronMan", 'I');
//           //New: IronMan"s weapons 
//           ironMan.addWeapon(new Arma(20, "Escudo"));
//           ironMan.addWeapon(new Arma(10, "Garra"));
//           ironMan.addWeapon(new Arma(15, "Gema"));
//           mapa.insertarPersonaje(ironMan,salaDailyPlanet);
//
//           SuperHeroe storm = new SuperHeroe("Storm", 'S');
//           //New: Storm"s weapons 
//           storm.addWeapon(new Arma(25, "Baston"));
//           storm.addWeapon(new Arma(10, "Anillo"));
//           storm.addWeapon(new Arma(15, "Capa"));
//           mapa.insertarPersonaje(storm,salaDailyPlanet);
//
//           SuperHeroe captainAmerica = new SuperHeroe("Capitan América", 'C');
//           //New: Captain America"s weapons 
//           captainAmerica.addWeapon(new Arma(22, "Cetro"));
//           captainAmerica.addWeapon(new Arma(15, "Bola"));
//           captainAmerica.addWeapon(new Arma(24, "Garra"));
//           mapa.insertarPersonaje(captainAmerica,salaDailyPlanet);
//
//           Villano deadPool = new Villano("Dead Pool", 'D', new Arma ("Sable",17));
//           mapa.insertarPersonaje(deadPool,salaDailyPlanet);
//
//           Villano kurtConnnors = new Villano("Kurt Connors", 'K', new Arma ("CampoEnergia",15));
//           mapa.insertarPersonaje(kurtConnnors,salaDailyPlanet);
//
//           Villano nebula = new Villano("Nebula", 'N', new Arma ("RayoEnergia",15));
//           mapa.insertarPersonaje(nebula,salaDailyPlanet);
//
//          mapa.simulacion();
//          mapa.pintar();  //se mostrará en este caso únicamente la información del mapa
         // Realizar más pruebas
          }

	









}
