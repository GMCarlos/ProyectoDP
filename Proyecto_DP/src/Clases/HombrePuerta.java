package Clases;

import EstructurasDatos.Arbol;

public class HombrePuerta {
	/*Estado de la puerta, true abierto, false cerrado*/
	private boolean estadoPuerta; 
	/*Identificador del hombrePuerta*/
	private int id;
	/*Altura de apertura de la puerta del hP*/
	private int alturaAperturaCerradura;
	/*Arbol de armas del hP*/
	private Arbol<Arma> arbolArmas;
	
	/**
	 * @PRE 
	 * @param estadoPuerta = true si est· abierta y false si est· cerrada
	 * @param id = 
	 * @param alturaAperturaCerradura = 
	 * @Explicaci√≥n Constructor parametrizado de una instancia del objeto HombrePuerta
	 * @Post El valor de los atributos de la instancia deben coincidir con los pasados por parametro.
	 * @Complejidad: 0(1)
	 */
	public HombrePuerta(boolean estadoPuerta, int id, int alturaAperturaCerradura) {
		this.estadoPuerta = estadoPuerta;
		this.id = id;
		this.alturaAperturaCerradura = alturaAperturaCerradura;
		this.arbolArmas=new Arbol<Arma>();
	}

	/**
	 * @PRE  
	 * @Explicaci√≥n Constructor por defecto de una instancia del objeto HombrePuerta
	 * @Post
	 * @Complejidad: 0(1)
	 */
	public HombrePuerta() {
		this.estadoPuerta = false;		//cerrado=false
		this.id = 0;
		this.alturaAperturaCerradura = 0;
		this.arbolArmas=new Arbol<Arma>();
	}

	/**
	 * @PRE 
	 * @Explicaci√≥n Muestra la informacion del HombrePuerta
	 * @Post
	 * @Complejidad: 0(1)
	 */
	@Override
	public String toString() {
		return "(owneroftheworld: [estadoPuerta=" + estadoPuerta + ", id=" + id + ", alturaAperturaCerradura="
				+ alturaAperturaCerradura+", "+"\n" +this.arbolArmas.toString()+ ")";
	}
	
	

	/**
	 * @PRE 
	 * @Explicaci√≥n Se devuelve el estado de la puerta
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public boolean getEstadoPuerta() {
		return estadoPuerta;
	}

	/**
	 * @PRE 
	 * @param estadoPuerta = estado de la puerta 
	 * @Explicaci√≥n Se le asigna al atributo estadoPuerta de la instancia el valor que se pasa por parametro
	 * @Post El valor del estadoPuerta de la instancia debe coincidir con el pasado por parametro
	 * @Complejidad: 0(1)
	 */
	public void setEstadoPuerta(boolean estadoPuerta) {
		this.estadoPuerta = estadoPuerta;
	}

	/**
	 * @PRE 
	 * @Explicaci√≥n Se devuelve el id del HombrePuerta
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public int getId() {
		return id;
	}

	/**
	 * @PRE 
	 * @param id =  
	 * @Explicaci√≥n Se le asigna al atributo id de la instancia el valor que se pasa por parametro
	 * @Post El valor del id de la instancia debe coincidir con el pasado por parametro
	 * @Complejidad: 0(1)
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @PRE 
	 * @Explicaci√≥n Se devuelve la altura de la apertura de la cerradura
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public int getAlturaAperturaCerradura() {
		return alturaAperturaCerradura;
	}

	/**
	 * @PRE 
	 * @param alturaAperturaCerradura = altura en el arbol de la apertura de la cerradura 
	 * @Explicaci√≥n Se le asigna al atributo alturaAperturaCerradura de la instancia el valor que se pasa por parametro
	 * @Post El valor del alturaAperturaCerradura de la instancia debe coincidir con el pasado por parametro
	 * @Complejidad: 0(1)
	 */
	public void setAlturaAperturaCerradura(int alturaAperturaCerradura) {
		this.alturaAperturaCerradura = alturaAperturaCerradura;
	}
	
	/**
	 * PRE: debe haber armas en el vector
	 * @param armasPuerta = vector de armas 
	 * @Explicacion Se inserta las armas del vector en el arbol del HP
	 * @Post Debe de haber el mismo numero de elementos en el vector y en el arbol
	 * @Complejidad: 0(n)
	 */
	
	public void configurar(Arma[] armasPuerta) {
		for (int i = 0; i < armasPuerta.length; i++) {
			this.arbolArmas.insertar(armasPuerta[i]);
			
		}
	}
	
	/**
	 * PRE: debe de estar abierto para poder cerrar
	 * @Explicacion Se pone a falso el estadoPuerta de la instancia del HP
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public void cerrar() {
		this.estadoPuerta=false;
	}

	/**
	 * PRE: se introduce un arma con valores validos
	 * @param a = arma 
	 * @Explicacion Busca en el arbol del hombrePuerta si se encuentra un arma con el mismo nombre que la que se introduce por parametro de entrada
	 * @Post Devuelve el arma con el mismo nombre en el caso de que exista
	 * @Complejidad: log(n)
	 */
	public Arma buscarArmaEnArbol(Arma a) {
		Arma[] aux = new Arma [1];
		buscarArmaEnArbolRecursivo(this.arbolArmas, a, aux);
		return aux[0];
	}
	

	/**
	 * PRE: se introduce un arma con valores validos, un arbol que no estÈ vacio y un vector de armas para almacenar el arma que se busca
	 * @param arbolArmas = estructura arbol que almacena objetos de tipo arma
	 * @param a = arma que se quiere buscar en el arbol
	 * @param aux = vector de armas que se usa para guardar el arma que se busca en caso de encontrarla
	 * @Explicacion Busca en el arbol del hombrePuerta si se encuentra un arma con el mismo nombre que la que se introduce
	 *  por parametro(a) y la almacena en el vector aux
	 * @Post 
	 * @Complejidad: log(n)
	 */
	private void buscarArmaEnArbolRecursivo(Arbol<Arma> arbolArmas, Arma a, Arma[] aux) {
		Arbol<Arma> arbolAux;
		if (!arbolArmas.vacio()) {
			if (arbolArmas.getRaiz().getNombreArma() == a.getNombreArma()) {
				aux[0] = arbolArmas.getRaiz();
			}

			if (arbolArmas.getHijoIzq() != null) {
				arbolAux = arbolArmas.getHijoIzq();
				buscarArmaEnArbolRecursivo(arbolAux, a, aux);
			}
			if (arbolArmas.getHijoDer() != null) {
				arbolAux = arbolArmas.getHijoDer();
				buscarArmaEnArbolRecursivo(arbolAux, a, aux);
			}

		}
		
	}
	
	/**
	 * PRE: se introduce un arma con valores validos
	 * @param a = arma 
	 * @Explicacion Busca en el arbol del hombrePuerta si se encuentra un arma con el mismo nombre que la que se introduce
	 *  por parametro de entrada
	 * @Post Devuelve true en caso de encontrar un arma con el mismo nombre y false en caso contrario
	 * @Complejidad: log(n)
	 */
	public boolean pertenece(Arma a) {
		boolean aux[]= new boolean[1];
		aux[0]=false;
		perteneceRecursivo(this.arbolArmas, a, aux);
		return aux[0];
	}
	
	/**
	 * PRE: se introduce un arma con valores validos, un arbol que no estÈ vacio y un vector de boolean para almacenar si sen encuentra el arma
	 * @param arbolArmas = estructura arbol que almacena objetos de tipo arma
	 * @param a = arma que se quiere buscar en el arbol
	 * @param vector = vector de boolean que se usa para guardar si se encuentra el arma con el mismo nombre
	 * @Explicacion MÈtodo auxiliar que busca en el arbol del hombrePuerta si se encuentra un arma con el mismo nombre que la que se introduce
	 *  por parametro(a) y si la encuentra, pondr· a true la posiciÛn 0 del vector de boolean(vector)
	 * @Post 
	 * @Complejidad: log(n)
	 */
	private void perteneceRecursivo(Arbol<Arma> arbolArmas, Arma a, boolean[] vector) {
		Arbol<Arma> aux;
		if (!arbolArmas.vacio()) {
			if (arbolArmas.getRaiz().getNombreArma()==a.getNombreArma()) {
				vector[0] = true;
			}
			if (arbolArmas.getHijoIzq() != null) {
				aux = arbolArmas.getHijoIzq();
				perteneceRecursivo(aux, a, vector);
			}
			if (arbolArmas.getHijoDer() != null) {
				aux = arbolArmas.getHijoDer();
				perteneceRecursivo(aux, a, vector);
			}
		}
	}
	
	
	/**
	 * PRE: debe de haber armas en el arbol del hombrePuerta
	 * @Explicacion Busca en el arbol del hombrePuerta el arma de mayor poder y lo almacena en el vector "aux" en la posicion 0
	 * @Post Devuelve el arma de mayor poder
	 * @Complejidad: log(n)
	 */
	public Arma getArmaMayorPoder() {
		Arma[] aux = new Arma[1];
		if(!this.arbolArmas.vacio()) {
			aux[0] = this.arbolArmas.getRaiz();
			this.ArmaMayorPoder(aux, this.arbolArmas);
		}
		return aux[0];
	}
	
	/**
	 * PRE: se introduce un arbol que no estÈ vacio y un vector de armas para almacenar el arma de mayor poder
	 * @param qwerty = estructura arbol que almacena objetos de tipo arma
	 * @param aux2 = vector de armas que almacenar· el arma de mayor poder
	 * @Explicacion MÈtodo auxiliar que busca en el arbol del hombrePuerta el arma de mayor poder y
	 *  la almacena en la posicion 0 del vector de armas "aux2"
	 * @Complejidad: log(n)
	 */
	private void ArmaMayorPoder(Arma[] aux2, Arbol<Arma> qwerty) {
		Arbol<Arma> aux=null;
		if(!qwerty.vacio()) {			
			if(aux2[0]==null) {
				aux2[0]=qwerty.getRaiz();
			}
			if(qwerty.getRaiz().getPoder()>aux2[0].getPoder()) {
				aux2[0]=qwerty.getRaiz();
			}
			
			if((qwerty.getHijoIzq())!= null) {
				aux=qwerty.getHijoIzq();
				ArmaMayorPoder(aux2, aux);
			}

			if((qwerty.getHijoDer())!=null) {
				aux=qwerty.getHijoDer();
				ArmaMayorPoder(aux2, aux);
			}
			
		}
		
	}
	
	/**
	 * @PRE 
	 * @Explicaci√≥n Se devuelve el arbolArmas del HombrePuerta
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public Arbol<Arma> getArbolArmas() {
		return arbolArmas;
	}

	/**
	 * @PRE 
	 * @param arbolArmas = estructura de tipo arbol con armas insertadas
	 * @Explicaci√≥n Se le asigna al atributo arbolArmas de la instancia el valor que se pasa por parametro
	 * @Post El valor del arbolArmas de la instancia debe coincidir con el pasado por parametro
	 * @Complejidad: 0(1)
	 */
	public void setArbolArmas(Arbol<Arma> arbolArmas) {
		this.arbolArmas = arbolArmas;
	}

	/**
	 * PRE: el arma que se quiere eliminar del arbol debe de estar en Èl
	 * @param dato = arma que se quiere borrar
	 * @Explicacion Busca en el arbol el arma y la borra de la estructura
	 * @Complejidad: log(n)
	 */
	public void eliminarArma(Arma dato) {
		this.arbolArmas.borrar(dato);
	}

}
