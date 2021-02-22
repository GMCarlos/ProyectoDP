package EstructurasDatos;

/**
 * @name Arbol
 * @description Clase de la estructura de datos arbol
 * @group GMCarlos_8
 * @author Carlos Guillén Moreno
 * @date
 * @version 
 */

public class Arbol<T extends Comparable<T>> {

	/** Llave almacenada en cada nodo del �rbol */
	private T datoRaiz;

	/** Atributo que indica si el �rbol est� vac�o */
	private boolean esVacio;

	/** Hijo izquierdo del nodo actual */
	private Arbol<T> hIzq;

	/** Hijo derecho del nodo actual */
	private Arbol<T> hDer;

	/** Altura del �rbol */
	private int altura;

	/**
	 * Constructor por defecto de la clase Arbol.
	 */
	public Arbol() {
		this.esVacio = true;
		this.hIzq = null;
		this.hDer = null;
	}

	/**
	 * Constructor parametrizado de la clase Arbol. Crea un nuevo �rbol a partir
	 * de los datos pasados por par�metro.
	 * 
	 * @param hIzq
	 *            El hijo izquierdo del �rbol que se est� creando.
	 * @param datoRaiz
	 *            Ra�z del �rbol que se est� creando.
	 * @param hDer
	 *            El hijo derecho del �rbol que se est� creando.
	 */
	Arbol(Arbol<T> hIzq, T datoRaiz, Arbol<T> hDer) {
		this.datoRaiz = datoRaiz;
		this.hIzq = hIzq;
		this.hDer = hDer;
	}

	/**
	 * M�todo que devuelve el hijo izquierdo del �rbol.
	 * 
	 * @return El hijo izquierdo del �rbol. Complejidad: O(1).
	 */
	public Arbol<T> getHijoIzq() {
		return this.hIzq;
	}

	/**
	 * M�todo que devuelve el hijo derecho del �rbol.
	 * 
	 * @return El hijo derecho del �rbol. Complejidad: O(1).
	 */
	public Arbol<T> getHijoDer() {
		return this.hDer;
	}

	/**
	 * Devuelve la raiz del arbol
	 * 
	 * @return La raiz del arbol Complejidad: O(1).
	 */
	public T getRaiz() {
		return datoRaiz;
	}

	/**
	 * M�todo que comprueba si el �rbol est� vac�o.
	 * 
	 * @return True si el �rbol est� vac�o � False en caso contrario.
	 *         Complejidad: O(1).
	 */
	public boolean vacio() {
		return this.esVacio;
	}

	/**
	 * M�todo que inserta una nueva arma en el �rbol.
	 * 
	 * @param dato
	 *            La llave a insertar.
	 * @return True si la llave se ha insertado correctamente � False en caso
	 *         contrario. Complejidad: O(1).
	 */
	public boolean insertar(T dato) {
		boolean resultado = true;
		if (vacio()) {
			datoRaiz = dato;
			esVacio = false;
		} else {
			if (!(this.datoRaiz.equals(dato))) {
				Arbol<T> aux;
				if (dato.compareTo(this.datoRaiz) < 0) { // dato < datoRaiz
					if ((aux = getHijoIzq()) == null) {
						hIzq = aux = new Arbol<T>();
					}
				} else { // dato > datoRaiz
					if ((aux = getHijoDer()) == null) {
						hDer = aux = new Arbol<T>();
					}
				}
				resultado = aux.insertar(dato);
			} else {
				resultado = false;
			}
		}
		return resultado;
	}

	/**
	 * M�todo que comprueba si una llave se encuentra almacenada en el �rbol.
	 * 
	 * @param dato
	 *            La llave a buscar.
	 * @return True si la llave se encuentra en el �rbol � False en caso
	 *         contrario. Complejidad: O(1).
	 */
	public boolean pertenece(T dato) {
		Arbol<T> aux = null;
		boolean encontrado = false;
		if (!vacio()) {
			if (this.datoRaiz.equals(dato)) {
				encontrado = true;
			} else {
				if (dato.compareTo(this.datoRaiz) < 0) { // dato < datoRaiz
					aux = getHijoIzq();
				} else { // dato > datoRaiz
					aux = getHijoDer();
				}
				if (aux != null) {
					encontrado = aux.pertenece(dato);
				}
			}
		}
		return encontrado;
	}

	/**
	 * M�todo que borra una llave del �rbol.
	 * 
	 * @param dato
	 *            La llave que se quiere borrar. Complejidad: O(1).
	 */
	public void borrar(T dato) {
		if (!vacio()) {
			if (dato.compareTo(this.datoRaiz) < 0) { // dato<datoRaiz
				hIzq = hIzq.borrarOrden(dato);
			} else {
				if (dato.compareTo(this.datoRaiz) > 0) { // dato>datoRaiz
					hDer = hDer.borrarOrden(dato);
				} else { // En este caso el dato es datoRaiz
					if (hIzq == null && hDer == null) {
						esVacio = true;
					} else {
						borrarOrden(dato);
					}
				}
			}
		}
	}

	/**
	 * Borrar una llave. Este m�todo es utilizado por el m�todo borrar anterior.
	 * 
	 * @param dato
	 *            La llave a borrar.
	 * @return El �rbol resultante despu�s de haber realizado el borrado.
	 *         Complejidad: O(n).
	 */
	private Arbol<T> borrarOrden(T dato) {
		T datoaux = null;
		Arbol<T> retorno = this, aborrar, candidato, antecesor;
		if (!vacio()) {
			if (dato.compareTo(this.datoRaiz) < 0) {
				if (hIzq != null) {
					hIzq = hIzq.borrarOrden(dato);
				}
			} else {
				if (dato.compareTo(this.datoRaiz) > 0) {
					if (hDer != null) {
						hDer = hDer.borrarOrden(dato);
					}
				} else {
					aborrar = this;
					if ((hDer == null) && (hIzq == null)) { /* si es hoja */
						aborrar = null;
						retorno = null;
					} else {
						if (hDer == null) { /* Solo hijo izquierdo */
							aborrar = hIzq;
							datoaux = datoRaiz;
							datoRaiz = hIzq.datoRaiz;
							hIzq.datoRaiz = datoaux;
							hIzq = hIzq.hIzq;
							hDer = aborrar.hDer;
							retorno = this;
						} else {
							if (hIzq == null) { /* Solo hijo derecho */
								aborrar = hDer;
								datoaux = datoRaiz;
								datoRaiz = hDer.datoRaiz;
								hDer.datoRaiz = datoaux;
								hDer = hDer.hDer;
								hIzq = aborrar.hIzq;
								retorno = this;
							} else { /* Tiene dos hijos */
								candidato = hIzq;
								antecesor = this;
								while (candidato.hDer != null) {
									antecesor = candidato;
									candidato = candidato.hDer;
								}
								/* Intercambio de datos de candidato */
								datoaux = datoRaiz;
								datoRaiz = candidato.datoRaiz;
								candidato.datoRaiz = datoaux;
								aborrar = candidato;
								if (antecesor == this) {
									hIzq = candidato.hIzq;
								} else {
									antecesor.hDer = candidato.hIzq;
								}
							} // Eliminar solo ese nodo, no todo el subarbol
						}
						aborrar.hIzq = null;
						aborrar.hDer = null;
					}
				}
			}
		}
		return retorno;
	}

	/**
	 * M�todo que hace un recorrido inOrden del �rbol. Complejidad: O(1).
	 */
	public void inOrden() {
		Arbol<T> aux = null;
		if (!vacio()) {
			if ((aux = getHijoIzq()) != null) {
				aux.inOrden();
			}
			System.out.print(this.datoRaiz.toString() + " ");
			if ((aux = getHijoDer()) != null) {
				aux.inOrden();
			}
		}
	}

	/**
	 * M�todo que te dice si un elemento est� en una hoja o no.
	 * 
	 * @param dato
	 *            Es el dato a comprobar.
	 * @return True si es hoja � False en caso contrario. Complejidad: O(1).
	 */
	private boolean esHoja(T dato) {
		boolean enc = false;
		if (!vacio()) {
			if (this.datoRaiz.equals(dato)) {
				if (hIzq == null && hDer == null) {
					enc = true;
				}
			} else {
				if (dato.compareTo(this.datoRaiz) > 0) { // dato>datoRaiz
					if (hDer != null) {
						hDer.esHoja(dato);
					}
				} else {
					if (dato.compareTo(this.datoRaiz) < 0) { // dato<datoRaiz
						if (hIzq != null) {
							hIzq.esHoja(dato);
						}
					}
				}
			}
		}
		return enc;
	}

	/**
	 * M�todo que nos dice el n�mero de nodos hoja que tiene el �rbol.
	 * 
	 * @return El n�mero de nodos hoja. Complejidad: O(1).
	 */
	public int numHojas() {
		int totali = 0;
		int totald = 0;
		if (!vacio()) {
			if (esHoja(datoRaiz)) {
				return 1;
			}
			if (hIzq != null) {
				totali = hIzq.numHojas();
			}
			if (hDer != null) {
				totald = hDer.numHojas();
			}
		}
		return (totald + totali);
	}

	/**
	 * M�todo que nos dice el n�mero de nodos internos que tiene el �rbol.
	 * 
	 * @return El n�mero de nodos internos. Complejidad: O(1).
	 */
	public int numNodosInternos() {
		int totali = 0;
		int totald = 0;
		if (!vacio()) {
			if (hIzq != null && hDer != null) {
				return hIzq.numNodosInternos() + hDer.numNodosInternos() + 1;
			}
			if (hIzq != null) {
				totali = 1 + hIzq.numNodosInternos();
			}
			if (hDer != null) {
				totald = 1 + hDer.numNodosInternos();
			}
		}
		return (totali + totald);
	}

	/**
	 * Devolver la profundidad de un arbol
	 */
	public int Profundidad() {
		int profI = 0;
		int profD = 0;
		int profundidad = 0;
		
		if(!vacio()) {
			if(hIzq != null) {
				profI = hIzq.Profundidad();
			}
			if(hDer != null) {
				profD = hDer.Profundidad();
			}
			if(profD > profI) {
				profundidad = profD;
			}
			else {
				profundidad = profI;
			}
		}
		return profundidad + 1;
		// System.out.println(profundidad);
	}
	
	
	
	/**
	 * M�todo que asigna la altura al Arbol.
	 * 
	 * @param arbol
	 *            Arbol del que se quiere medir la altura.
	 * @param nivel
	 *            Nivel por el que vamos explorando el Arbol. Complejidad: O(1).
	 */
	private void retornarAltura(Arbol<T> arbol, int nivel) {
		if (arbol != null) {
			retornarAltura(arbol.hIzq, nivel + 1);
			if (nivel > altura) {
				altura = nivel;
			}
			retornarAltura(arbol.hDer, nivel + 1);
		}
	}

	/**
	 * M�todo que devuelve la altura del Arbol.
	 * 
	 * @return Altura del Arbol. Complejidad: O(1).
	 */
	public int retornarAltura() {
		altura = 0;
		retornarAltura(this, 1);
		return altura;
	}

	/**
	 * M�todo toString() de la clase Arbol.
	 */
	public String toString() {
		String devolver = "";
		Arbol<T> aux = null;
		if (!vacio()) {
			if ((aux = getHijoIzq()) != null) {
				devolver = aux.toString() + devolver;
			}
			devolver = devolver + datoRaiz;
			if ((aux = getHijoDer()) != null) {
				devolver = devolver + aux.toString();
			}
		}
		
		return devolver;
	}

}