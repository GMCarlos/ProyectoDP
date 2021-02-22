package Cargador;
/**
 * Clase creada para ser usada en la utilidad cargador
 * estudiada previamente en sesión práctica "Excepciones"
 * 
 * @version 2.0 -  17/11/2017
 * @author Profesores DP
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;


import Excepciones.MapaException;

public class FicheroCarga {
	/**  
	buffer para almacenar el flujo de entrada
	*/
	private static BufferedReader bufferIn;
	/**
	 * Metodo para procesar el fichero. Sin excepciones
	 * @param escritura 
	 * @return codigoError con el error que se ha producido
	
	
	 * @throws MapaException 
	 * @throws Exception. Puede lanzar una excepción en la apertura del buffer de lectura
	 */
	 public static void procesarFichero(String nombreFichero, Cargador cargador, BufferedWriter escritura) throws  FileNotFoundException, IOException, MapaException {
		 List<String> vCampos = new ArrayList<String>();
	     String S=new String();
	     int numCampos=0;

	     //System.out.println( "Procensando el fichero..." );
	     bufferIn = new BufferedReader(new FileReader(nombreFichero));//creación del filtro asociado al flujo de datos

         while((S=bufferIn.readLine())!= null) {
	     	 //System.out.println( "S: "+S );
  	 		 if (!S.startsWith("--"))  {
  	 			 vCampos.clear();
  	 			 numCampos = trocearLinea(S, vCampos);
  	 			 cargador.crear(vCampos.get(0), numCampos, vCampos, escritura);
	 		}
	     }
	     bufferIn.close();	     //cerramos el filtro
	 }

	 /**
	  * Metodo para trocear cada línea y separarla por campos
	  * @param S cadena con la línea completa leída
	  * @param vCampos. Array de String que contiene en cada posición un campo distinto
	  * @return numCampos. Número campos encontrados
	 */
	 private static int trocearLinea(String S,List<String> vCampos) {
		 boolean eol = false;
		 int pos=0,posini=0, numCampos=0;

   	     while (!eol) {
	    			pos = S.indexOf("#");
	     	    	if(pos!=-1) {
	     	    		vCampos.add(new String(S.substring(posini,pos)));
	     	    		S=S.substring(pos+1, S.length());
	     	    		numCampos++;
	     	    	}
	     	    	else eol = true;
		  }
		  return numCampos;
	 }

}