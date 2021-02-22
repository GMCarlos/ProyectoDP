package EstructurasDatos;

/**
 * @name Cola
 * @description Clase de la estructura de datos cola
 * @group: GMCarlos_8
 * @author Carlos Guillén Moreno
 * @date
 * @version
 */

public class Cola<T> {

	/** Puntero al primer elemento de la Cola */
	private Nodo<T> primero;

	/** Puntero al �ltimo elemento de la Cola */
	private Nodo<T> ultimo;

	/** Tama�o de la cola */
	private int size;

	public static class Nodo<T> {

		/** Dato almacenado en cada nodo */
		private T dato;

		/** Enlace al siguiente elemento */
		private Nodo<T> siguiente;

		Nodo(T dato, Nodo<T> siguiente) {
			this.dato = dato;
			this.siguiente = siguiente;
		}
	}

	/**
	 * Constructor por defecto de la clase Cola.
	 */
	public Cola() {
		this.primero = this.ultimo = null;
		this.size = 0;
	}

	/**
	 * Constructor parametrizado de la clase Cola.
	 * 
	 * @param dato
	 *            El elemento a insertar.
	 */
	Cola(T dato) {
		this.primero = this.ultimo = null;
		encolar(dato);
		this.size++;
	}

	/**
	 * M�todo que devuelve el elemento del inicio de la Cola.
	 * 
	 * @return El elemento primero. Complejidad: O(1).
	 */
	public T primero() {
		return this.primero.dato;
	}

	/**
	 * M�todo para comprobar si la Cola est� vac�a o no.
	 * 
	 * @return True si est� vac�a � False en caso contrario. Complejidad: O(1).
	 */
	public boolean estaVacia() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * M�todo que retorna el tama�o de la Cola.
	 * 
	 * @return Tama�o de la Cola. Complejidad: O(1).
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * M�todo que permite encolar un elemento.
	 * 
	 * @param dato
	 *            Elemento que se va a insertar. Complejidad: O(1).
	 */
	public void encolar(T dato) {
		Nodo<T> aux = ultimo;
		Nodo<T> nodo = new Nodo<T>(dato, null);
		if (aux == null) {
			primero = nodo;
		} else {
			aux.siguiente = nodo;
		}
		ultimo = nodo;
		size++;
	}

	/**
	 * M�todo que permite desencolar un elemento. Complejidad: O(1).
	 */
	public void Desencolar() {
		Nodo<T> aux = primero;
		primero = primero.siguiente;
		aux.siguiente = null;
		if (size == 1) {
			ultimo = null;
		}
		size--;
	}

	/**
	 * M�todo toString() de la clase Cola. Complejidad: O(n).
	 */
	public String toString() {
		Nodo<T> aux = primero;
		String S = "";
		while (aux != null) {
			if (aux != ultimo) {
				S = S + "" + aux.dato.toString();
				aux = aux.siguiente;
			} else {
				S = S + "" + aux.dato.toString();
				aux = aux.siguiente;
			}
		}
		return S;
	}

}
