package gestion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import acceso.AccesoCancion;
import accesoFicheros.AccesoFicherosCancion;
import entrada.Teclado;
import modelos.Album;
import modelos.Cancion;
import modelosMultitabla.MultitablaCancion;

public class Main_Cancion {

	//Escribe el submenu de la clase Cancion en consola
	public static void subMenuCancion() {
		System.out.println("Menu Opciones Cancion");
		System.out.println("1) Insertar una fila en la tabla.");
		System.out.println("2) Actualizar una fila, por clave primaria, de la tabla.");
		System.out.println("3) Eliminar una fila, por clave primaria, de la tabla.");
		System.out.println("4) Consultar una fila, por clave primaria, de la tabla.");
		System.out.println("5) Consultar todas las filas de la tabla, ordenadas por una columna que no sea o que no forme parte de la clave primaria.");
		System.out.println("6) Exportar la tabla al fichero de texto.");
		System.out.println("7) Importar la tabla desde el fichero de texto.");
	}

	// Escribe en consola la lista de Canciones.
	public static void escribirListaCanciones(List<Cancion> listaCanciones) {
		for (Cancion cancion : listaCanciones) {
			System.out.println(cancion.toString());
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
		}
		System.out.println("Se han consultado " + listaCanciones.size() + 
                           " canciones de la base de datos.");
	}

	//Escribe en consola la lista de Albumes
	public static void escribirListaAlbumes(List<Album> listaAlbumes) {
		for (Album album : listaAlbumes) {
			System.out.println(album.toString2());
			System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
		}
		System.out.println("Se han consultado " + listaAlbumes.size() + 
                           " albumes de la base de datos.");
	}

	//Escribe en consola la lista del resultado MultiTabla
	public static void escribirListaMultiTabla(List<MultitablaCancion> listaMultiTabla) {
		for (MultitablaCancion multi : listaMultiTabla) {
			System.out.println(multi.toString());
			System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
		}
		System.out.println("Se han consultado " + listaMultiTabla.size() + 
                           " albumes de la base de datos.");
	}
	
	public static void main(String []args) {
		int opcion, pararInsertar;
		int codigo, posicion, codigo_album;
		int filasInsertadas, filasActualizadas, filasEliminadas;
		String titulo, compositor, duracion;
		int posicionMaxima = 0;
		Cancion cancion;
		Album album;
		List<Cancion> listaCanciones = new LinkedList<>();
		List<Album> listaAlbumes = new LinkedList<>();
	

		try {
			do {
				subMenuCancion();
				opcion = Teclado.leerEntero("Introduce una opcion (0-8): ");
				
				switch(opcion) {
					case 0:
						System.out.println("Saliendo del programa...");
						break;
					
					//Inserta una canciones a un album elegido por el usuario, si ese album no se encuentra sale un mensaje, 
					//inserta canciones hasta que el usuario introduce 0 en consola cuando se le pregunta si quiere seguir o no.
					case 1:
						System.out.println("ALBUMES QUE EXISTEN EN LA BASE DE DATOS");
						System.out.println("---------------------------------------");
						listaAlbumes = AccesoCancion.consultarAlbumes();
						escribirListaAlbumes(listaAlbumes);
						codigo_album = Teclado.leerEntero("¿Codigo del album que pertenece la cancion?: ");
						if (AccesoCancion.consultarAlbumPorCodigoAlbum(codigo_album) == null){
							System.out.println("No se ha encontrado ningun Album con ese codigo.");
							break;
						}
						//Consultar la posicion maxima del album, para que al insertar empieze a partir de la siguiente a la maxima
						//Si no hay canciones en el album, la posicion maxima sera 0, por lo cual empezara a insertar a con 1 en adelante
						posicionMaxima = AccesoCancion.consultarMaximoPosicion(codigo_album);
						Cancion.setPosicionIncremental(posicionMaxima);

						//Insertar cancion
						titulo = Teclado.leerCadena("¿Titulo de la cancion?: ");
						compositor = Teclado.leerCadena("¿Compositor de la cancion?: ");
						duracion = Teclado.leerCadena("¿Duracion de la cancion?: ");
						album = new Album(codigo_album);
						cancion = new Cancion(titulo, compositor, duracion, album);
						filasInsertadas = AccesoCancion.insertarCancionAlbum(cancion, album);
						if (filasInsertadas == 1){
							System.out.println("Se ha insertado la cancion en el album.");
						}
						//Bucle para seguir insertando canciones en el album
						pararInsertar = Teclado.leerEntero("¿Insertar otra cancion? SI(1) NO(0): ");
						while(pararInsertar != 0){
							titulo = Teclado.leerCadena("¿Titulo de la cancion?: ");
							compositor = Teclado.leerCadena("¿Compositor de la cancion?: ");
							duracion = Teclado.leerCadena("¿Duracion de la cancion?: ");
							cancion = new Cancion(titulo, compositor, duracion, album);
							filasInsertadas = AccesoCancion.insertarCancionAlbum(cancion, album);
							if (filasInsertadas == 1){
								System.out.println("Se ha insertado la cancion en el album.");
							}
							pararInsertar = Teclado.leerEntero("¿Insertar otra cancion? SI(1) NO(0): ");
						}
						break;
						

					//Acutaliza una fila de cancion, primero busca si el codigo de cancion existe, si existe pide los nuevos datos
					//y los coloca en la cancion la cual el usuario ha puesto el codigo
					case 2:
						System.out.println("CANCIONES QUE EXISTEN EN LA BASE DE DATOS");
						System.out.println("-----------------------------------------");
						listaCanciones = AccesoCancion.consultarTodasCanciones();
						escribirListaCanciones(listaCanciones);
						codigo = Teclado.leerEntero("¿Introduce el codigo de la cancion a cambiar?: ");
						if (AccesoCancion.consultarCancionPorCodigo(codigo) == null){
							System.out.println("No se ha encontrado ninguna cancion con ese codigo");
						}
						else{
							posicion = Teclado.leerEntero("¿Nueva Posicion de la cancion?: ");
							titulo = Teclado.leerCadena("¿Nuevo Titulo de la cancion?: ");
							compositor = Teclado.leerCadena("¿Nuevo Compositor de la cancion?: ");
							duracion = Teclado.leerCadena("¿Nueva Duracion de la cancion?: ");
							codigo_album = Teclado.leerEntero("¿Nuevo codigo del album que pertenece la cancion?: ");
							album = new Album(codigo_album);
							filasActualizadas = AccesoCancion.actualizarCancionPorCodigo(codigo, posicion, titulo, compositor, duracion, album);
							if (filasActualizadas == 1){
								System.out.println("Se ha actualizado la cancion.");
							}
						}
						break;
						
					//Elimina una fila de cancion mediante el codigo que da el usuario, si el codigo no existe muestra 
					//un mensaje, si existe elimina la cancion
					case 3:
						System.out.println("CANCIONES QUE EXISTEN EN LA BASE DE DATOS");
						System.out.println("-----------------------------------------");
						listaCanciones = AccesoCancion.consultarTodasCanciones();
						escribirListaCanciones(listaCanciones);
						codigo = Teclado.leerEntero("¿Codigo de la cancion a eliminar?: ");
						if (AccesoCancion.consultarCancionPorCodigo(codigo) == null){
							System.out.println("No se ha encontrado ninguna cancion con ese codigo");
						}
						else{
							filasEliminadas = AccesoCancion.eliminarCancionPorCodigo(codigo);
							if (filasEliminadas == 1){
								System.out.println("Se ha eliminado la cancion.");
							}
						}
						break;
						
					//Consulta una fila de cancion por la clave primaria codigo, si no existe una cancion con ese codigo muestra
					//un mensaje, si existe muestra la fila de la cancion consultado con ese codigo
					case 4:
						System.out.println("CANCIONES QUE EXISTEN EN LA BASE DE DATOS");
						System.out.println("-----------------------------------------");
						listaCanciones = AccesoCancion.consultarTodasCanciones();
						escribirListaCanciones(listaCanciones);
						codigo = Teclado.leerEntero("Introduce el codigo de la cancion: ");
						cancion = AccesoCancion.consultarCancionPorCodigo(codigo);
						if (cancion == null){
							System.out.println("No se ha encontrado ninguna cancion con ese codigo.");
						}
						else{
							System.out.println("CANCION CONSULTADA CON ESE CODIGO");
							System.out.println("---------------------------------");
							System.out.println(cancion.toString());
							System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
						}
						break;
						
					//Consulta todas las canciones de la base de datos y las ordena por la duracion de las canciones
					//por lo cual la que menos dure sera la primera y asi hasta la que mas
					case 5:
						listaCanciones = AccesoCancion.consultarTodasCancionesOrdPorDuracion();
						if (listaCanciones.isEmpty()){
							System.out.println("No se han encontrado canciones.");
						}
						else{
							System.out.println("CANCIONES ORDENADAS POR DURACION (MENOR A MAYOR)");
							System.out.println("------------------------------------------------");
							escribirListaCanciones(listaCanciones);
						}
						break;
					
					//Consulta todas las canciones de la base de datos y las introduce en una listaCanciones, despues
					//las pasa al metodo insertarTodos, el cual inserta los datos de estas canciones en un fichero de txt
					case 6:
						listaCanciones = AccesoCancion.consultarTodasCanciones();
						if (listaCanciones.isEmpty()){
							System.out.println("No se han encontrado canciones.");
						}
						else{
							escribirListaCanciones(listaCanciones);
							int insertarListaAFichero = Teclado.leerEntero("Se van a insertar estas canciones en el fichero. ¿Estas de acuerdo? SI(1) NO(0): ");
							if (insertarListaAFichero == 1){
								AccesoFicherosCancion.insertarTodos(listaCanciones);
								System.out.println("Se han insertado los datos de la lista al fichero txt.");
							}
							else{
								System.out.println("No se han insertado los datos de la lista en el fichero.");
							}
						}
						break;
						
					//Consulta todos los datos de canciones de el fichero de txt y las introduce en una listaCanciones, despues
					//las pasa al metodo insertarListaEnBaseDatos, el cual coge los datos de la lista y los mete en la base de datos
					case 7:
						listaCanciones = AccesoFicherosCancion.consultarTodos();
						if (listaCanciones.isEmpty()){
							System.out.println("No se han encontrado canciones.");
						}
						else{
							if (AccesoCancion.borrarDatosCancion()){
								System.out.println("Se han borrado las filas de Canciones.");
							}
							else{
								System.out.println("La tabla cancion esta vacia.");
							}
							System.out.println("CANCIONES CONSULTADAS DEL FICHERO");
							System.out.println("---------------------------------");
							escribirListaCanciones(listaCanciones);
							int insertarListaABd = Teclado.leerEntero("Se van a insertar estas canciones en la base de datos. ¿Estas de acuerdo? SI(1) NO(0): ");
							if (insertarListaABd == 1){
								AccesoFicherosCancion.insertarListaEnBaseDatos(listaCanciones);
								System.out.println("Se han insertado los datos del fichero a la base de datos.");
							}
							else{
								System.out.println("No se han insertado los datos de la lista en la base de datos.");
							}
						}
						break;
					default:
						System.out.println("Introduce una opcion entre (0-8)");
				}
				
			}
			while (opcion != 0);
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("Error al cargar el conector de SQLite: " + cnfe.getMessage());
			cnfe.printStackTrace();
		} 
		catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}	
}
