
package Cargador;
import java.io.BufferedWriter;
import java.io.File;
/**
 * Clase creada para ser usada en la utilidad cargador
 * contiene el main del cargador. Se crea una instancia de la clase Cargador
 * y se procesa el fichero de inicio, es decir, se leen todas las líneas y se van creando todas las instancias de la simulación
 * 
 * @version 4.0 -  15/10/2014 
 * @author Profesores DP
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import Clases.Mapa;
import Excepciones.ArmaException;
import Excepciones.MapaException;

public class ClasePrincipal {
	
	public static void main(String[] args) throws MapaException, IOException, ArmaException {
		File ficheroEscritura = new File("Nombre.log");
		BufferedWriter reset = new BufferedWriter(new OutputStreamWriter (new FileOutputStream(ficheroEscritura, false), "UTF-8"));
		reset.close();
		BufferedWriter escritura = null;
		try {
			escritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ficheroEscritura, false),"UTF-8"));
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		/**  
		instancia asociada al fihero de entrada inicio.txt
		*/
		Cargador cargador = new Cargador();
		try {
			/**  
			Método que procesa línea a línea el fichero de entrada inicio.txt
			*/
		     FicheroCarga.procesarFichero("init.txt", cargador, escritura);
			Mapa mapa = Mapa.obtenerInstancia();
			mapa.simulacion(escritura);
			escritura.close();

		}
		catch (FileNotFoundException valor)  {
			System.err.println ("Excepción capturada al procesar fichero: "+valor.getMessage());
		}
		catch (IOException valor)  {
			System.err.println ("Excepción capturada al procesar fichero: "+valor.getMessage());
		}
	}
}
