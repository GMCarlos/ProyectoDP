package Clases;

import Excepciones.ArmaException;

public class Arma implements Comparable <Arma> {
	/*Nombre del arma*/
	private String nombreArma;
	/*Poder del arma*/
	private int poder;
	
	/**
	 * @PRE El poder del arma debe ser >=0
	 * @param nombreArma = nombre de la Arma
	 * @param poder = poder del Arma 
	 * @Explicación Se genera un arma con los parametros proporcionados por los parametros
	 * de entrada
	 * @Post Los valores del mapa deben coincidir con los parametros de entrada que se le
	 * asigna a cada atributo
	 * @Complejidad: 0(1)
	 */
	public Arma(String nombreArma, int poder) {
		this.nombreArma = nombreArma;
		this.poder = poder;
	}
	
	/**
	 * @PRE El poder del arma debe ser >=0
	 * @param nombreArma = nombre de la Arma
	 * @param poder = poder del Arma 
	 * @Explicación Se genera un arma con los parametros proporcionados por los parametros
	 * de entrada
	 * @Post Los valores del mapa deben coincidir con los parametros de entrada que se le
	 * asigna a cada atributo
	 * @Complejidad: 0(1)
	 */
	public Arma(int poder, String nombreArma) {
		this.nombreArma = nombreArma;
		this.poder = poder;
	}
	/**
	 * @PRE 
	 * @Explicación Se genera por defecto sin nombre y con poder = 0
	 * @Post
	 * @Complejidad: 0(1)
	 */
	public Arma() {
		nombreArma = "";
		poder = 0;		
	}

	/**
	 * @PRE 
	 * @Explicación Muestra la informacion del arma
	 * @Post
	 * @Complejidad: 0(1)
	 */	
	@Override
	public String toString() {
		String x="("+this.getNombreArma()+","+this.getPoder()+")";
		//System.out.println(x);
		return x;
	}
	
	/**
	 * @PRE 
	 * @Explicación Se devuelve el nombre de la arma
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public String getNombreArma() {
		return nombreArma;
	}

	/**
	 * @PRE 
	 * @param nombreArma = nombre del Arma 
	 * @Explicación Se le asigna al arma el nombre que se pasa por parametro
	 * @Post El valor del nombre del arma debe coincidir con el pasado por parametro
	 * @Complejidad: 0(1)
	 */
	public void setNombreArma(String nombreArma) {
		
		this.nombreArma = nombreArma;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreArma == null) ? 0 : nombreArma.hashCode());
		result = prime * result + poder;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arma other = (Arma) obj;
		if (nombreArma == null) {
			if (other.nombreArma != null)
				return false;
		} else if (!nombreArma.equals(other.nombreArma))
			return false;
		if (poder != other.poder)
			return false;
		return true;
	}

	/**
	 * @PRE 
	 * @Explicación Se devuelve el poder del arma
	 * @Post 
	 * @Complejidad: 0(1)
	 */
	public int getPoder() {
		return poder;
	}

	/**
	 * @PRE 
	 * @param poder = poder del Arma 
	 * @throws ArmaException 
	 * @Explicación Se le asigna al arma el poder que se pasa por parametro
	 * @Post El valor del poder del arma debe coincidir con el pasado por parametro
	 * @Complejidad: 0(1)
	 */
	public void setPoder(int poder) {

		this.poder = poder;
	}

	@Override
	public int compareTo(Arma o) {
		if(this.nombreArma.equals(o.getNombreArma())) {
			if(this.poder==o.getPoder()) {
				return 0; 	//cuando son iguales los poderes
			}else {
				if(this.poder>o.getPoder()){
					return 1;
				}else {
					return -1;
				}
			}
			
		}else {
			if(this.nombreArma.compareTo(o.getNombreArma())>0) {
				return 1;
			}else {
				return -1;
			}
		}
	}

	
	
	
}