package Cargador;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Clases.Dir;
import Clases.Mapa;
import Excepciones.MapaException;
import Movimiento.CaminoMinimo;
import Movimiento.ManoDerecha;
import Movimiento.ManoIzquierda;
import Movimiento.Movimiento;
import Movimiento.MovimientoDefensa;
import Movimiento.Profundidad;
import Personajes.SHFlightEast;
import Personajes.SuperHeroeExtrasensorial;
import Personajes.SuperHeroeFisico;
import Personajes.SuperHeroeVolador;
import Personajes.Villano;

/**
 * Clase creada para ser usada en la utilidad cargador
 * contiene el main del cargador. Se crea una instancia de la clase Estacion, una instancia de la clase Cargador
 * y se procesa el fichero de inicio, es decir, se leen todas las líneas y se van creando todas las instancias de la simulación
 * 
 * @version 6.0 -  17/11/2017 
 * @author Profesores DP
 */
public class Cargador {
	//hacer lo de encolar las direcciones de los personajes cada una la suya
	/**  
	número de elementos distintos que tendrá la simulación:
	Mapa, SHPhysical, SHExtraSensorial, SHFlight, Villain
	*/
	static final int NUMELTOSCONF = 6; //num de elementos de la config
	/**  
	atributo para almacenar el mapeo de los distintos elementos
	*/
	static private DatoMapeo [] mapeo;
	
	/**
	 *  constructor parametrizado 
	 *  @param e referencia a la instancia del patrón Singleton
	 */
	Cargador()  {
		mapeo = new DatoMapeo[NUMELTOSCONF];
		mapeo[0]= new DatoMapeo("MAP", 5);
		mapeo[1]= new DatoMapeo("SHPHYSICAL", 4);
		mapeo[2]= new DatoMapeo("SHEXTRASENSORIAL", 4);
		mapeo[3]= new DatoMapeo("SHFLIGHT", 4);
		mapeo[4]= new DatoMapeo("VILLAIN", 4);
		mapeo[5]= new DatoMapeo("SHFLIGHTEAST", 4);
		
	}
	
	/**
	 *  busca en mapeo el elemento leído del fichero inicio.txt y devuelve la posición en la que está 
	 *  @param elto elemento a buscar en el array
	 *  @return res posición en mapeo de dicho elemento
	 */
	private int queElemento(String elto)  {
	    int res=-1;
	    boolean enc=false;

	    for (int i=0; (i < NUMELTOSCONF && !enc); i++)  {
	        if (mapeo[i].getNombre().equals(elto))      {
	            res=i;
	            enc=true;
	        }
	    }
	    return res;
	}
	
	/**
	 *  método que crea las distintas instancias de la simulación 
	 *  @param elto nombre de la instancia que se pretende crear
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo de la instancia
	 * @param escritura 
	 * @throws MapaException 
	 * @throws IOException 
	 */
	public void crear(String elto, int numCampos, List<String> vCampos, BufferedWriter escritura) throws MapaException, IOException	{
	    //Si existe elemento y el número de campos es correcto, procesarlo... si no, error
	    int numElto = queElemento(elto);

	    //Comprobación de datos básicos correctos
	    if ((numElto!=-1) && (mapeo[numElto].getCampos() == numCampos))   {
	        //procesar
	        switch(numElto){
	        case 0:	   
	            crearMap(numCampos,vCampos, escritura);
	            break;
	        case 1:
	            crearSHPhysical(numCampos,vCampos);
	            break;
	        case 2:
	            crearSHExtraSensorial(numCampos,vCampos);
	            break;
	        case 3:
	            crearSHFlight(numCampos,vCampos);
	            break;
	        case 4:
	            crearVillain(numCampos,vCampos);
	            break;
	        case 5:
	            crearSHFlightEast(numCampos, vCampos);
	            break;
	     	}
	    }
	    else {
	    	System.out.println("ERROR Cargador::crear: Datos de configuración incorrectos... " + elto + "," + numCampos+"\n");
	    }     
	}

	/**
	 *  método que crea una instancia de la clase Planta
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 * @param escritura 
	 * @throws MapaException 
	 * @throws IOException 
	 */
	private void crearMap(int numCampos, List<String> vCampos, BufferedWriter escritura) throws MapaException, IOException{
	    //System.out.println("Creado Map: " + vCampos.get(1) + "\n");
	    //inicializar mapa
	    Mapa mapa = Mapa.obtenerInstancia();
	    int ancho = Integer.parseInt(vCampos.get(1));
	    int alto = Integer.parseInt(vCampos.get(2));
	    int salaDailyPlanet = Integer.parseInt(vCampos.get(3));
	    int alturaApertura = Integer.parseInt(vCampos.get(4));

	    mapa.iniciarMapa(ancho, alto, salaDailyPlanet, alturaApertura, escritura);
	    
	}

	/**
	 *  método que crea una instancia de la clase SHPhysical
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 * @throws MapaException 
	 */
	private void crearSHPhysical(int numCampos, List<String> vCampos) throws MapaException{
	    //System.out.println("Creado SHPhysical: " + vCampos.get(1) + "\n");
	    //Registrar SHPhysical en el mapa
	    //nombre, marca, turno
	    String nombre = vCampos.get(1);
	    char marca = vCampos.get(2).charAt(0);
	    int turno = Integer.parseInt(vCampos.get(3));
	    
	    SuperHeroeFisico x = new SuperHeroeFisico(nombre, marca, turno);
	    Movimiento mov = new Profundidad();
	    Queue<Dir> cola=new LinkedList<Dir>();
	    cola=x.transformarColaDeNodosEnDirecciones(mov.mover(0));
	    int tama�o=cola.size();
	    for(int i=0;i<tama�o;i++) {
	    	x.encolarDireccion(cola.poll());
	    }
	
		
		
	    Mapa mapa =Mapa.obtenerInstancia();
	    //comienzan en la sala 0
	    mapa.insertarPersonaje(x, 0);    
	}

	/**
	 *  método que crea una instancia de la clase SHExtraSensorial
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 * @throws MapaException 
	 */
	private void crearSHExtraSensorial(int numCampos, List<String> vCampos) throws MapaException{
	    //System.out.println("Creado SHExtraSensorial: " + vCampos.get(1) + "\n");
	    //Registrar SHExtraSensorial en el mapa
	    String nombre = vCampos.get(1);
	    char marca = vCampos.get(2).charAt(0);
	    int turno = Integer.parseInt(vCampos.get(3));
	    
	    SuperHeroeExtrasensorial x = new SuperHeroeExtrasensorial(nombre, marca, turno);
	    Movimiento mov = new ManoDerecha();
	    Queue<Dir> cola=new LinkedList<Dir>();
//	    Queue<Integer> j= new LinkedList<Integer>();
//	    j=mov.mover();
	    cola=x.transformarColaDeNodosEnDirecciones(mov.mover(0));
	    int tama�o=cola.size();
	    for(int i=0;i<tama�o;i++) {
	    	x.encolarDireccion(cola.poll());
	    }
	    
			    
	    
	    Mapa mapa =Mapa.obtenerInstancia();
	    //no me dicen la sala por el momento, la meto por defecto en la 0
	    mapa.insertarPersonaje(x, 0);
	}	

	/**
	 *  método que crea una instancia de la clase SHFlight
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 * @throws MapaException 
	 */
	private void crearSHFlight(int numCampos, List<String> vCampos) throws MapaException{
		Mapa mapa =Mapa.obtenerInstancia();
	    //System.out.println("Creado SHFlight: " + vCampos.get(1) + "\n");
	    //Registrar SHFlight en el mapa
	    String nombre = vCampos.get(1);
	    char marca = vCampos.get(2).charAt(0);
	    int turno = Integer.parseInt(vCampos.get(3));
	    
	    SuperHeroeVolador x = new SuperHeroeVolador(nombre, marca, turno);
	    Movimiento mov = new CaminoMinimo();
	    Queue<Dir> cola=new LinkedList<Dir>();
	    cola=x.transformarColaDeNodosEnDirecciones(mov.mover(mapa.obtenerEsquinaAbajoIzquierda()));
	    int tama�o=cola.size();
	    for(int i=0;i<tama�o;i++) {
	    	x.encolarDireccion(cola.poll());
	    }

		
	    
	    //abajo a la izquierda empiezan
	    mapa.insertarPersonaje(x, mapa.obtenerEsquinaAbajoIzquierda());
	}	

	/**
	 *  método que crea una instancia de la clase Villain
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 * @throws MapaException 
	 */
	private void crearVillain(int numCampos, List<String> vCampos) throws MapaException{
		Mapa mapa =Mapa.obtenerInstancia();
	    //System.out.println("Creado Villain: " + vCampos.get(1) + "\n");
	    //Registrar Villain en el mapa
	    String nombre = vCampos.get(1);
	    char marca = vCampos.get(2).charAt(0);
	    int turno = Integer.parseInt(vCampos.get(3));
	    
	    Villano x = new Villano(nombre, marca, turno);
	    Movimiento mov = new ManoIzquierda();
	    Queue<Dir> cola=new LinkedList<Dir>();
//	    Queue<Integer> j= new LinkedList<Integer>();
//	    j=mov.mover();
	    cola=x.transformarColaDeNodosEnDirecciones(mov.mover(mapa.obtenerEsquinaSuperiorDerecha()));
	    int tama�o=cola.size();
	    for(int i=0;i<tama�o;i++) {
	    	x.encolarDireccion(cola.poll());
	    }

		
	    
	    //no me dicen la sala por el momento, la meto por defecto en la 0 �?
	    mapa.insertarPersonaje(x, mapa.obtenerEsquinaSuperiorDerecha());
	}
	
	private void crearSHFlightEast(int numCampos, List<String> vCampos) throws MapaException{
		Mapa mapa =Mapa.obtenerInstancia();
	    //System.out.println("Creado Villain: " + vCampos.get(1) + "\n");
	    //Registrar Villain en el mapa
	    String nombre = vCampos.get(1);
	    char marca = vCampos.get(2).charAt(0);
	    int turno = Integer.parseInt(vCampos.get(3));
	    
	    SHFlightEast x = new SHFlightEast(nombre, marca, turno);
	    Movimiento mov = new MovimientoDefensa();
	    Queue<Dir> cola=new LinkedList<Dir>();
//	    Queue<Integer> j= new LinkedList<Integer>();
//	    j=mov.mover();
	    cola=x.transformarColaDeNodosEnDirecciones(mov.mover(mapa.obtenerEsquinaAbajoIzquierda()));
	    int tama�o=cola.size();
	    for(int i=0;i<tama�o;i++) {
	    	x.encolarDireccion(cola.poll());
	    }

		
	    
	    //no me dicen la sala por el momento, la meto por defecto en la 0 �?
	    mapa.insertarPersonaje(x, mapa.obtenerEsquinaAbajoIzquierda());
	}


}
