package EstructurasDatos;

/**
 * @name Lista
 * @description Clase de la estructura de datos lista
 * @group: GMCarlos_8
 * @author Carlos Guillén Moreno
 * @date
 * @version
 */


public class Lista<T extends Comparable<T>> {

	/** Puntero al primer elemento de la Lista */
	private Nodo<T> first;

	/** Puntero al �ltimo elemento de la Lista */
	private Nodo<T> last;

	/** Tama�o de la Lista */
	private int size;

	public static class Nodo<T> {

		/** Dato almacenado en cada nodo */
		private T dato;

		/** Enlace al siguiente elemento */
		private Nodo<T> next;

		/** Enlace al elemento anterior */
		private Nodo<T> prev;

		Nodo(T dato) {
			this.dato = dato;
			this.next = null;
			this.prev = null;
		}

		Nodo(Nodo<T> prev, T dato, Nodo<T> next) {
			this.dato = dato;
			this.next = next;
			this.prev = prev;
		}
	}

	/**
	 * Constructor por defecto de la clase Lista.
	 */
	public Lista() {
		first = last = null;
		size = 0;
	}

	/**
	 * Constructor parametrizado de la clase Lista.
	 * 
	 * @param dato
	 *            Es el nuevo elemento en la Lista.
	 */
	Lista(T dato) {
		insOrden(dato);
	}

	/**
	 * M�todo que devuelve el elemento del inicio de la Lista.
	 * 
	 * @return El elemento first. Complejidad: O(1).
	 */
	public T getFirst() {
		return first.dato;
	}

	/**
	 * M�todo para comprobar si la Lista est� vac�a o no.
	 * 
	 * @return True si est� vac�a � False en caso contrario. Complejidad: O(1).
	 */
	public boolean estaVacia() {
		return (size == 0);
	}

	/**
	 * M�todo que devuelve el tama�o de la Lista.
	 * 
	 * @return El tama�o. Complejidad: O(1).
	 */
	public int size() {
		return this.size;
	}

	/**
	 * M�todo que permite insertar al final de la Lista.
	 * 
	 * @param dato
	 *            Elemento que se va a insertar. Complejidad: O(1).
	 */
	private void addLast(T dato) {
		Nodo<T> l = last;
		Nodo<T> nodo = new Nodo<T>(l, dato, null);
		last = nodo;
		if (l == null) {
			first = nodo;
		} else {
			l.next = nodo;
		}
		// size++;
	}

	/**
	 * M�todo que a�ada un elemento ordenado a la Lista.
	 * 
	 * @param daton
	 *            Elemento a insertar. Complejidad: O(n).
	 */
	public void insOrden(T daton) {
		Nodo<T> aux = first;
		Nodo<T> nodo = new Nodo<T>(daton);
		if (aux == null) {
			last = first = nodo;
		} else {
			boolean insertado = false;
			while (aux != null) {
				if (aux.dato.equals(daton) || aux.dato.compareTo(daton) > 0) {
					insertado = true;
					if (aux.prev == null) {
						nodo.next = aux;
						aux.prev = nodo;
						first = nodo;
					} else {
						nodo.next = aux;
						nodo.prev = aux.prev;
						aux.prev.next = nodo;
						aux.prev = nodo;
					}
				}
				aux = aux.next;
			}
			if (!insertado) {
				addLast(daton);
			}
		}
		size++;
	}

	/**
	 * M�todo que permite eliminar un dato almacenado en la Lista.
	 * 
	 * @param dato
	 *            Elemento que se eliminar� de la Lista.
	 * @return La posici�n en la que se almacenaba el dato. Complejidad: O(n).
	 */
	public int removeDato(T dato) {
		T datoAux = null;
		Nodo<T> aux = first;
		int pos = 0;
		boolean encontrado = false;
		if (!estaVacia()) {
			while (pos < size && !encontrado) {
				datoAux = aux.dato;
				if (datoAux == dato) {
					encontrado = true;
					if (aux.prev != null && aux.next != null) {
						aux.prev = aux.next;
						aux.next.prev = aux.prev;
					} else {
						if (aux.prev == null && aux.next == null) {
							aux = null;
							first = null;
							last = null;
						} else if (aux.prev == null) {
							aux.next.prev = null;
							first = aux.next;
							aux.next = null;
						} else {
							aux.prev.next = null;
							last = aux.prev;
							aux.prev = null;
						}
					}
					size--;
				} else {
					aux = aux.next;
				}
				pos++;
			}
		}
		return pos;
	}

	/**
	 * M�todo toStrign() de la clase Lista. Complejidad: O(n).
	 */
	public String toString() {
		Nodo<T> aux = first;
		String S = "";
		while (aux != null) {
			S = S + "" + aux.dato;
			aux = aux.next;
		}
		return S;
	}

}