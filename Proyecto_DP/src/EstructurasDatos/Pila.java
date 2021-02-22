/** 

/**
 * @name Pila
 * @description Clase de la estructura de datos Pila
 * @group: GMCarlos_8
 * @author Carlos Guillén Moreno
 */

package EstructurasDatos;

public class Pila<T> {

	/** Puntero a la cima de la Pila */
	private Nodo<T> cima;

	/** Guarda el n�mero de elementos de la Pila */
	private int size;

	private static class Nodo<T> {

		/** Dato almacenado en cada nodo */
		private T dato;

		/** Enlace al siguiente elemento */
		private Nodo<T> siguiente;

		Nodo(T dato) {
			this.dato = dato;
			this.siguiente = null;
		}
	}

	/**
	 * Metodo constructor por defecto de la clase Pila
	 */
	public Pila() {
		this.cima = null;
	}

	/**
	 * Metodo constructor parametrizado de la clase Pila
	 * 
	 * @param dato
	 *            es el nuevo elemento en la pila
	 */
	Pila(T dato) {
		Nodo<T> nodo = new Nodo<T>(dato);
		nodo.siguiente = cima;
		this.cima = nodo;
		this.size = 0;
	}

	/**
	 * Metodo que devuelve el elemento en la cima de la pila
	 * 
	 * @return la cima de la pila Complejidad: O(1).
	 */
	public T cima() {
		return this.cima.dato;
	}

	/**
	 * Metodo para comprobar si la pila esta vacia o no
	 * 
	 * @return true si esta vacia o false en caso contrario Complejidad: O(1).
	 */
	public boolean estaVacia() {
		return (cima == null);
	}

	/**
	 * M�todo que devuelve el tama�o de la Pila.
	 * 
	 * @return El tama�o. Complejidad: O(1).
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Metodo que permite insertar un dato
	 * 
	 * @param dato
	 *            valor que se va a insertar Complejidad: O(1).
	 */
	public void apilar(T dato) {
		Nodo<T> nodo = new Nodo<T>(dato);
		nodo.siguiente = cima;
		cima = nodo;
		size++;
	}

	/**
	 * Elimina un dato de la pila. Se elimina el dato que est� en la cima.
	 * Complejidad: O(1).
	 */
	public void desapilar() {
		if (!estaVacia()) {
			cima = cima.siguiente;
		}
		size--;
	}

	/**
	 * M�todo toString() de la clase Pila. Complejidad: O(n).
	 */
	public String toString() {
		Nodo<T> aux = cima;
		String S = "";
		for (int i = 0; i < this.size; i++) {
			S = S + "" + aux.dato.toString();
			aux = aux.siguiente;
		}
		return S;
	}

}