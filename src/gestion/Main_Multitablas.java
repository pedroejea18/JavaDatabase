package gestion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import acceso.AccesoAlbum;
import accesoFicheros.AccesoFicherosCancion;
import accesoMultitablas.AccesoMultiTablaAlbum;
import accesoMultitablas.AccesoMultitablaCancion;
import accesoMultitablas.AccesoMultitablaMusico;
import entrada.Teclado;
import modelos.Album;
import modelosMultitabla.MultitablaAlbum;
import modelosMultitabla.MultitablaCancion;
import modelosMultitabla.MultitablaMusico;

public class Main_Multitablas {

	public static void escribirMenuOpciones() {

		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
		System.out.println("(0) Salir del programa");
		System.out.println("(1) Consulta Multitabla de la tabla Músico");
		System.out.println("(2) Consulta Multitabla de la tabla Album");
		System.out.println("(3) Consulta Multitabla de la tabla Cancion");
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
	}
	public static void escribirListaMultiTabla(List<MultitablaCancion> listaMultiTabla) {
		for (MultitablaCancion multi : listaMultiTabla) {
			System.out.println(multi.toString());
			System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
		}
		System.out.println("Se han consultado " + listaMultiTabla.size() + 
                           " albumes de la base de datos.");
	}
	public static void escribirListaMusicos(List<MultitablaMusico> listaMusicos) {
		for (MultitablaMusico musico : listaMusicos) {
			System.out.println(musico.toString());
		}
		System.out.println("Se han consultado " + listaMusicos.size() + 
				" músicos de la base de datos.");
	}

	public static void main(String[] args) {

		int opcion;
		List<MultitablaMusico> listaMusico;
		//album 
		List<Album> listaAux;
		List <MultitablaAlbum>listaMultitabla;
		//cancion 
		List<MultitablaCancion> listaMultiTabla = new LinkedList<>();


		do {
			escribirMenuOpciones();
			opcion = Teclado.leerEntero("Introduce una opción: ");

			try {
				switch (opcion) {
				case 0:
					//salida de programa
					break;
				case 1:
					/*Consulta Multitabla de la tabla Músico. Primero se creará una lista donde se almacenarán los músicos
					 * que se consulten mediante el método de consultaMultitablaMusico y tras ello se mostrarán los resultados o
					 * el mensaje en caso de no haber músicos. Tras ello, insertará el resultado de la consulta en el fichero correspondiente.
					 */ 
					listaMusico = AccesoMultitablaMusico.consultaMultitablaMusico();
					if (listaMusico.isEmpty()) {
						System.out.println("No se han encontrado músicos.");
					}
					else {
						System.out.println("Resultado de la consulta que se insertará:");
						escribirListaMusicos(listaMusico);
						if (AccesoMultitablaMusico.insertarConsulta(listaMusico)) {
							System.out.println("Se ha insertado el resultado de la consulta en el fichero.");
						}
						else {
							System.out.println("Ha habido un error a la hora de insertar la consulta.");
						}
					}
					break;
				case 2: 
					listaAux = AccesoAlbum.consultarTodosAlbumes();
					if(listaAux.isEmpty()) {
						System.out.println("primero debe haber un album en la base de datos");
					}
					else {
						listaMultitabla = AccesoMultiTablaAlbum.consultaMultiTabla();
						if(listaMultitabla.isEmpty()) {
							//esto es ya que al insertar canciones t obliga a escoger un album en el que insertar esa cancion
							System.out.println("Faltan datos en canciones para poder hacer la consulta multitabla");
						}
						else {
							if(AccesoMultiTablaAlbum.escribirEnFichero(listaMultitabla)) {
								System.out.println("Se ha insertado correctamente los datos dela multitabla al fichero");
							}
							else {
								System.out.println("Hubo un error al insertar los datos  de la multitabla en el fichero");
							}
						}
					}
					break;
				case 3: 

					listaMultiTabla = AccesoMultitablaCancion.consultaMultiTabla();
					if (listaMultiTabla.isEmpty()){
						System.out.println("No se ha encontrado ningun dato.");
					}
					else{
						System.out.println("CONSULTA MULTITABLA");
						System.out.println("-------------------");
						escribirListaMultiTabla(listaMultiTabla);
						int insertarListaAFichero = Teclado.leerEntero("¿Quieres insertar estos datos en el fichero multitabla?: SI (1) NO(0): ");
						if (insertarListaAFichero == 1){
							AccesoFicherosCancion.insertarMultiTabla(listaMultiTabla);
							System.out.println("Se han insertado los datos al fichero.");
						} 
						else{
							System.out.println("No se han insertado los datos al fichero.");
						}
					}
					break;

				}
			}
			catch (ClassNotFoundException cnfe) {
				System.out.println("Error al cargar el conector de SQLite: " + cnfe.getMessage());
				cnfe.printStackTrace();
			} 
			catch (SQLException sqle) {
				System.out.println("Error de SQL: " + sqle.getMessage());
				sqle.printStackTrace();
			}
			catch (IOException ioe) {
				System.out.println("Error al leer/escribir en el fichero");
				ioe.printStackTrace();
			}
		}while(opcion != 0);
	}
}
