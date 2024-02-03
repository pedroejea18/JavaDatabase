package gestion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import acceso.AccesoMusico;
import accesoFicheros.AccesoFicherosMusico;
import entrada.Teclado;
import modelos.Musico;

public class Main_Musico {

	public static void escribirMenuOpciones() {
		
		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
		System.out.println("0) Volver al menú inicial");
		System.out.println("1) Insertar un músico en la base de datos");
		System.out.println("2) Actualizar un músico, por clave primaria, de la base de datos");
		System.out.println("3) Eliminar un músico, por clave primaria, de la base de datos");
		System.out.println("4) Consultar un músico, por clave primaria, de la base de datos");
		System.out.println("5) Consultar todos los músicos de la base de datos ordenados por una columna que no sea o que no forme parte de la clave primaria");
		System.out.println("6) Exportar a un músico a un fichero de texto");
		System.out.println("7) Importar a un músico desde un fichero de texto");
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
	}

	public static void escribirListaMusicos(List<Musico> listaMusicos) {
		for (Musico musico : listaMusicos) {
			System.out.println(musico.toString());
		}
		System.out.println("Se han consultado " + listaMusicos.size() + 
				" músicos de la base de datos.");
	}

	public static void main(String[] args) {

		int opcion, codigo, codBanda, tieneBanda, filaActualizada;
		String nombre, fechaNacim, lugarResi, nacionalidad, instrumento;
		Musico musicoBanda = null;
		Musico musicoSolo = null;
		List<Musico> listaMusicos;


		do {
			escribirMenuOpciones();
			opcion = Teclado.leerEntero("Introduce una opción: ");

			try {
				switch (opcion) {
				case 0:
					//Salir de programa.
					break;
				case 1:
					/*Inserción de un músico en la base de datos. Primero le pedimos al usuario cada dato propio de un músico
					 * y le preguntamos si posee una banda o no indicando un rango entre 1 (sí que posee banda) y otro número cualquiera
					 * (no tiene banda). Si la tiene, leerá el código de la banda a la que pertenece e insertará al músico en la
					 * base de datos mediante el primer método de insertar, en caso contrario, lo hará mediante el segundo método.
					 * Por último, se informará al usuario del resultado de la operación.					  
					*/
					nombre = Teclado.leerCadena("Introduce el nombre del músico: ");
					fechaNacim = Teclado.leerCadena("Introduce la fecha de nacimiento del músico(Usar el siguiente formato: dd/mm/yyyy): ");
					lugarResi = Teclado.leerCadena("Introduce el lugar de residencia del músico: ");
					nacionalidad = Teclado.leerCadena("Indique la nacionalidad del músico: ");
					instrumento = Teclado.leerCadena("Indique que instrumento toca el músico (en caso de ser cantante, indicarlo también): ");
					tieneBanda = Teclado.leerEntero("¿El músico pertenece a una banda? (1=SI, OTRO NÚMERO=NO) ");

					if (tieneBanda == 1) {
						codBanda = Teclado.leerEntero("Introduce el código de la banda a la que pertenece el músico: ");
						if (AccesoMusico.insertar(nombre, fechaNacim, lugarResi, nacionalidad, instrumento, codBanda)) {
							System.out.println("Se ha insertado un músico en la base de datos.");
						}
					}
					else {
						if (AccesoMusico.insertar2(nombre, fechaNacim, lugarResi, nacionalidad, instrumento)) {
							System.out.println("Se ha insertado un músico en la base de datos.");
						}
					}
					break;
				case 2:
					/*Actualizar, por clave primaria, un musico en la base de datos. Primero pedirá al usuario si tiene o no una banda.
					 * Después, se pedirá el código del músico y mediante el método de consultarMusicoConBanda o consultarMusicoSinBanda se comprobará si existe 
					 * o no. Tras ello se pedirán los nuevos datos del músico y se actualizarán. Confirmando la operación con el mensaje correspondiente. 
					 */
					tieneBanda = Teclado.leerEntero("¿El músico pertenece a una banda? (1=SI, OTRO NÚMERO=NO) ");
					if (tieneBanda == 1) {
						codigo = Teclado.leerEntero("Introduce el código del músico: ");
						musicoBanda = AccesoMusico.consultarMusicoConBanda(codigo);
						
						nombre = Teclado.leerCadena("Introduce el nombre del músico: ");
						fechaNacim = Teclado.leerCadena("Introduce la fecha de nacimiento del músico(Usar el siguiente formato: dd/mm/yyyy): ");
						lugarResi = Teclado.leerCadena("Introduce el lugar de residencia del músico: ");
						nacionalidad = Teclado.leerCadena("Indique la nacionalidad del músico: ");
						instrumento = Teclado.leerCadena("Indique que instrumento toca el músico (en caso de ser cantante, indicarlo también): ");
						codBanda = Teclado.leerEntero("Introduce el código de la banda a la que pertenece el músico: ");
						
						musicoBanda.setNombre(nombre);
						musicoBanda.setFechaNacim(fechaNacim);
						musicoBanda.setLugarResi(lugarResi);
						musicoBanda.setNacionalidad(nacionalidad);
						musicoBanda.setInstrumento(instrumento);
						musicoBanda.getBanda().setCodigo(codBanda);
						
						filaActualizada = AccesoMusico.actualizar(tieneBanda, musicoBanda);
						if (filaActualizada == 1) {
							System.out.println("Se han actualizado los datos del músico.");
						}
					}
					else {
						codigo = Teclado.leerEntero("Introduce el código del músico: ");
						musicoSolo = AccesoMusico.consultarMusicoSinBanda(codigo);
						
						nombre = Teclado.leerCadena("Introduce el nombre del músico: ");
						fechaNacim = Teclado.leerCadena("Introduce la fecha de nacimiento del músico(Usar el siguiente formato: dd/mm/yyyy): ");
						lugarResi = Teclado.leerCadena("Introduce el lugar de residencia del músico: ");
						nacionalidad = Teclado.leerCadena("Indique la nacionalidad del músico: ");
						instrumento = Teclado.leerCadena("Indique que instrumento toca el músico (en caso de ser cantante, indicarlo también): ");
						
						musicoSolo.setNombre(nombre);
						musicoSolo.setFechaNacim(fechaNacim);
						musicoSolo.setLugarResi(lugarResi);
						musicoSolo.setNacionalidad(nacionalidad);
						musicoSolo.setInstrumento(instrumento);
						
						filaActualizada = AccesoMusico.actualizar(tieneBanda, musicoSolo);
						if (filaActualizada == 1) {
							System.out.println("Se han actualizado los datos del músico.");
						}
					}
					break;
				case 3:
					/*Eliminar, por clave primaria, un musico de la base de datos. Se pide primero el código y si existe, se
					 * aplicará el método de eliminar con ese código como parámetro. Si la eliminación se completa, se pondrá
					 * el mensaje correspondiente, en caso contrario también.
					 */
					codigo = Teclado.leerEntero("Introduce el código del músico: ");
					if (AccesoMusico.eliminar(codigo)) {
						System.out.println("Se ha eliminado un músico de la base de datos correctamente");
					}
					else {
						System.out.println("Error al eliminar el músico de la base de datos. No existe el código introducido."); //EL DE INSERTAR Y ELIMINAR FUNCIONAN PERO NO COGE BIEN LA BASE DE DATOS Y NO PUEDO VER QUE FUNCIONE CORRECTAMENTE
					}
					break;
				case 4:
					/*Consultar un músico, por clave primaria, de la base de datos. Se pide el código del músico y se consulta
					 * si tienen o no banda. Si no, pondrá el mensaje correspondiente, en caso contrario, mostrará al músico mediante 
					 * el método toString
					*/
					codigo = Teclado.leerEntero("Introduce el código del músico: ");
					musicoBanda = AccesoMusico.consultarMusicoConBanda(codigo);
					if (musicoBanda == null) {
						musicoSolo = AccesoMusico.consultarMusicoSinBanda(codigo);
						if (musicoSolo == null) {
							System.out.println("No existe un músico con ese código.");
						}
						else {
							System.out.println(musicoSolo.toString());
						}
					}
					else {
						System.out.println(musicoBanda.toString());
					}
					break;
				case 5:
					/*Consultar a todos los músicos de la base de datos. Se crea una lista donde se pondrán todos los músicos
					 * obtenidos desde el método de consultarTodos y si no hay, se pondrá el mensaje correspondiente. En caso
					 * contrario se escribirá la lista con los músicos.
					*/
					listaMusicos = AccesoMusico.consultarTodos();
					if (listaMusicos.isEmpty()) {
						System.out.println("No se han encontrado músicos en la base de datos.");
					}
					else {
						escribirListaMusicos(listaMusicos);
					}
					break;
				case 6:
					/* Exportar los músicos de la base de datos a un fichero. Creamos una lista donde metemos a todos los músicos
					 * de la base de datos. Si no hay, se especificará con el mensaje correspondiente, en caso contrario usaremos
					 * el método de insertarTodos con esa lista de referencia para insertar a los músicos. 
					 */
					listaMusicos = AccesoMusico.consultarTodos();
					if (listaMusicos.isEmpty()) {
						System.out.println("No existen músicos en la base de datos.");
					}
					else {
						if (AccesoFicherosMusico.insertarTodos(listaMusicos)) {
							System.out.println("Se han insertado todos los datos de los músicos de la lista en el fichero 'musicos.txt' ");
						}
						else {
							System.out.println("Error. No se han insertado los datos en el fihcero.");
						}
					}
					break;
				case 7:
					/*Importar los músicos de un fichero a la base de datos. Creamos una lista para consultar los músicos
					 * para importar. Si no hay se pondrá el mensaje correspondiente y sino, se borrará primero la base de datos
					 * y se sustituirá por la lista que se meterá en la base de datos mediante el método insertarListaEnBase.
					 */
					listaMusicos = AccesoFicherosMusico.consultarMusicosParaImportar();
					if (listaMusicos.isEmpty()) {
						System.out.println("No se han encontrado musicos.");
					}
					else {
						AccesoFicherosMusico.borrarDatosBase();
						System.out.println("Tabla 'musico' borrada/vacía.");
						if(AccesoFicherosMusico.insertarListaEnBase(listaMusicos)) {
							System.out.println("Se han insertado los datos del fichero a la base de datos.");
						}
						else {
							System.out.println("Error. No se han insertado los datos de la lista en la base de datos.");
						}
					}
					break;
				default:
					System.out.println("La opción del menú debe esatr comprendida entre 0 y 7.");
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
		}while (opcion != 0);
	}

}
