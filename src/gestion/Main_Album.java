package gestion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import acceso.AccesoAlbum;
import acceso.AccesoBanda;
import acceso.AccesoCancion;
import accesoFicheros.AccesoFicheroAlbum;
import entrada.Teclado;
import modelos.Album;
import modelos.Banda;
import modelos.Cancion;
import modelos.Musico;

public class Main_Album {

	public static void escribirMenu() {
		System.out.println();
		System.out.println("(0) Volver a la seleccion");
		System.out.println("(1) Insertar una fila en la tabla. ");
		System.out.println("(2) Actualizar una fila, por clave primaria, de la tabla.");
		System.out.println("(3) Eliminar una fila, por clave primaria, de la tabla. ");
		System.out.println("(4) Consultar una fila, por clave primaria, de la tabla. ");
		System.out.println("(5) Consultar todas las filas de la tabla, ordenadas por una columna que no sea o que no forme parte de la clave primaria");
		System.out.println("(6) Exportar la tabla al fichero de texto. ");
		System.out.println("(7) Importar la tabla desde el fichero de texto.");
		System.out.println();
	}
	public static void leerListaAlbumes(List<Album> listaAux) {
		for(Album x : listaAux) {
			System.out.println(x.toString());
		}
	}

	public static void main(String[] args) {
		int opcion;
		Album albumAux;
		Banda bandaAux;
		Musico musicoAux;
		int filas;
		List<Musico> listaMusicos;
		List<Banda> listaBandas;
		List<Cancion> listaCanciones;
		//caso 1
		String autor, titulo, tipo, duracion;
		int codigo , anioPublicacion, codigoBanda, codigoMusico;
		//caso 2
		List<Album> listaAux;
		//caso 8
		do {
			escribirMenu();
			opcion= Teclado.leerEntero("opcion ");
			try {
				switch(opcion) {
				case 0:
					break;
					//-----------------------------------------------
					// Insertar una fila en la tabla. 
				case 1:
					autor = Teclado.leerCadena("Autor debe ser banda o un musico ");
					if(autor.equalsIgnoreCase("banda")) {
						titulo = Teclado.leerCadena("Titulo? ");
						anioPublicacion = Teclado.leerEntero("Año publicacion? ");
						tipo = Teclado.leerCadena("Tipo debe ser estudio, directo o recopilatorio ");
						if(AccesoAlbum.tipoCorrecta(tipo)) {
							duracion= Teclado.leerCadena("Duracion formato HH:MM:SS ");
							listaBandas = AccesoBanda.consultarTodo();
							for (Banda x : listaBandas) {
								System.out.println(x.toString());
							}
							codigoBanda = Teclado.leerEntero("Asigna un codigo de banda entre las siguientes ");

							if( AccesoAlbum.claveAjenaBanda(codigoBanda)) {
								bandaAux = new Banda(codigoBanda);
								albumAux = new Album(autor, titulo, anioPublicacion, tipo, duracion, bandaAux, null );
								if(AccesoAlbum.insertarAlbum(albumAux)) {
									System.out.println("Se ha insertado correctamente el album  de una banda");
								}
								else {
									System.out.println("Error al insertar el album de una banda");
								}
							}
							else{
								System.out.println("No existe ninguna banda con ese codigo de banda en la base de datos");
							}
						}
						else {
							System.out.println("El tipo debe ser estudio, directo o recopilatorio ");
						}

					}
					else if(autor.equalsIgnoreCase("musico")){
						titulo = Teclado.leerCadena("Titulo? ");
						anioPublicacion = Teclado.leerEntero("Año publicacion? ");
						tipo = Teclado.leerCadena("Tipo debe ser estudio, directo o recopilatorio ");
						if(AccesoAlbum.tipoCorrecta(tipo)) {
							duracion= Teclado.leerCadena("Duracion formato HH:MM:SS ");
							listaMusicos = AccesoAlbum.consultarTodos();
							for (Musico x : listaMusicos) {
								System.out.println(x.toString());
							}
							codigoMusico = Teclado.leerEntero("Asigna un codigo de musico entre los siguientes ");
							if( AccesoAlbum.claveAjenaMusico(codigoMusico)) {
								musicoAux = new Musico(codigoMusico);
								albumAux = new Album(autor, titulo, anioPublicacion, tipo, duracion, null, musicoAux);
								if(AccesoAlbum.insertarAlbum(albumAux)) {
									System.out.println("Se ha insertado correctamente el album de un musico ");
								}
								else {
									System.out.println("Error al insertar el album de un musico");
								}
							}
							else{
								System.out.println("No existe ningun musico con ese codigo de musico en la base de datos");
							}
						}
						else {
							System.out.println("El tipo debe ser estudio, directo o recopilatorio ");
						}
					}
					else {
						System.out.println("Error El autor debe ser banda o musico");
					}
					break;
					//-----------------------------------------------
				case 2:
					//Actualizar una fila, por clave primaria, de la tabla. 
					listaAux = AccesoAlbum.consultarTodosAlbumes();
					if(listaAux.isEmpty()) {
						System.out.println("primero debe haber un album en la base de datos");
					}
					else {
						System.out.println("Escoge una fila para actualizar los datos: ");
						leerListaAlbumes (listaAux = AccesoAlbum.consultarTodosAlbumes());
						codigo = Teclado.leerEntero("Codigo de la tabla a actualizar: ");
						if( AccesoAlbum.albumCodigo(codigo)) {
							autor = Teclado.leerCadena("Autor es una banda o un musico ");
							if(autor.equalsIgnoreCase("banda")) {
								titulo = Teclado.leerCadena("Titulo? ");
								anioPublicacion = Teclado.leerEntero("Año publicacion? ");
								tipo = Teclado.leerCadena("Tipo debe ser estudio, directo o recopilatorio ");
								if(AccesoAlbum.tipoCorrecta(tipo)) {
									duracion= Teclado.leerCadena("Duracion formato HH:MM:SS");
									listaBandas = AccesoBanda.consultarTodo();
									for (Banda x : listaBandas) {
										System.out.println(x.toString());
									}
									codigoBanda = Teclado.leerEntero("Asigna un codigo de banda entre las siguientes  ");
									if( AccesoAlbum.claveAjenaBanda(codigoBanda)) {
										bandaAux = new Banda(codigoBanda);
										albumAux = new Album(codigo,autor, titulo, anioPublicacion, tipo, duracion, bandaAux, null );
										filas = AccesoAlbum.actualizarAlbum(albumAux);
										if(filas > 0) {
											System.out.println("Se ha actualizado correctamente el album");
										}
										else {
											System.out.println("Error al actualizar el album");
										}
									}
									else{
										System.out.println("No existe ninguna banda con ese codigo de banda en la base de datos");
									}
								}
								else {
									System.out.println("El tipo debe ser estudio, directo o recopilatorio ");

								}

							}
							else if (autor.equalsIgnoreCase("musico")){
								titulo = Teclado.leerCadena("Titulo? ");
								anioPublicacion = Teclado.leerEntero("Año publicacion? ");
								tipo = Teclado.leerCadena("Tipo debe ser estudio, directo o recopilatorio ");
								if(AccesoAlbum.tipoCorrecta(tipo)) {
									duracion= Teclado.leerCadena("Duracion formato HH:MM:SS");
									listaMusicos = AccesoAlbum.consultarTodos();
									for (Musico x : listaMusicos) {
										System.out.println(x.toString());
									}

									codigoMusico = Teclado.leerEntero("Asigna un codigo de musico entre las siguiente");
									if( AccesoAlbum.claveAjenaMusico(codigoMusico)) {
										musicoAux = new Musico(codigoMusico);
										albumAux = new Album(codigo,autor, titulo, anioPublicacion, tipo, duracion, null, musicoAux);
										filas = AccesoAlbum.actualizarAlbum(albumAux);
										if(filas > 0) {
											System.out.println("Se ha actualizado correctamente el album");
										}
										else {
											System.out.println("Error al actualizar el album");
										}
									}
									else{
										System.out.println("No existe ningun musico con ese codigo de musico en la base de datos");
									}
								}
								else {
									System.out.println("El tipo debe ser estudio, directo o recopilatorio ");

								}

							}
							else {
								System.out.println("Error El autor debe ser banda o musico");
							}
						}
						else{
							System.out.println("No existe ningun album con ese codigo en la base de datos");
						}
					}

					break;
					//-----------------------------------------------
				case 3:
					listaAux = AccesoAlbum.consultarTodosAlbumes();
					if(listaAux.isEmpty()) {
						System.out.println("primero debe haber un album en la base de datos");
					}
					else {
						System.out.println("Escoge un album para eliminar los datos: ");
						leerListaAlbumes (listaAux = AccesoAlbum.consultarTodosAlbumes());

						codigo = Teclado.leerEntero("Codigo de la tabla a elimnar ");
						if( AccesoAlbum.albumCodigo(codigo)) {
							if( AccesoAlbum.eliminarCancionAlbum(codigo)) {
								filas = AccesoAlbum.eliminarAlbum(codigo);
								if (filas > 0) {
									System.out.println("Se ha eliminado un album de la base de datos");
								}
								else {
									System.out.println("Ha habido un error al eliminar un album de la base de datos");
								}
							}
							else {
								System.out.println("Ha habido un error al eliminar un album de la base de datos");
							}
						}

						else {
							System.out.println("No hay ningun album con ese codigo");
						}
					}

					break;
					//-----------------------------------------------
				case 4:
					//Consultar una fila, por clave primaria, de la tabla.
					listaAux = AccesoAlbum.consultarTodosAlbumes();
					if(listaAux.isEmpty()) {
						System.out.println("primero debe haber un album en la base de datos");
					}
					else {
						System.out.println("Escoge una fila a consultar: ");
						leerListaAlbumes (listaAux = AccesoAlbum.consultarTodosAlbumes());
						codigo = Teclado.leerEntero("Codigo de la tabla a consultar: ");
						if( AccesoAlbum.albumCodigo(codigo)) {
							albumAux = AccesoAlbum.consultarFila(codigo);
							if(albumAux == null) {
								System.out.println("No hay ningun album con ese codigo");
							}
							else {
								System.out.println(albumAux.toString());
							}
						}
						else {
							System.out.println("No hay ningun album con ese codigo");
						}
					}
					break;
					//-----------------------------------------------
				case 5:
					//Consultar todas las filas de la tabla, ordenadas por una columna que no sea o que no 
					//forme parte de la clave primaria
					listaAux = AccesoAlbum.consultarTodosAlbumes();
					if(listaAux.isEmpty()) {
						System.out.println("primero debe haber un album en la base de datos");
					}
					else {
						System.out.println("Ordenados por Titulo descendente: ");
						leerListaAlbumes (listaAux = AccesoAlbum.consultarTodosAlbumesOrdenados());
					}
					break;
					//-----------------------------------------------
				case 6:
					//Exportar la tabla al fichero de texto.
					listaAux = AccesoAlbum.consultarTodosAlbumes();
					if(listaAux.isEmpty()) {
						System.out.println("primero debe haber un album en la base de datos");
					}
					else {
						if(AccesoFicheroAlbum.escribirEnFichero(listaAux)) {
							System.out.println("Se escribio correctamente los albumes en el fichero");
						}
						else {
							System.out.println("Error no se pudo importar los datos al fichero");
						}
					}
					break;
					//-----------------------------------------------
				case 7:
					//Importar la tabla desde el fichero de texto.
					System.out.println("Antes de importar asegurate de que el fichero contenga bandas o musicos que exitan"
							+ " si no dara error de foreign key");
					listaCanciones = AccesoCancion.consultarTodasCanciones();
					AccesoFicheroAlbum.insertarTodos(listaCanciones);
					if(AccesoFicheroAlbum.eliminarCancionAlbumes()) {
						if ( AccesoFicheroAlbum.eliminarAlbumes()) {
							listaAux = AccesoFicheroAlbum.importarFichero();
							if(listaAux == null) {
								System.out.println("El fichero esta vacio");
							}
							else {
								if(AccesoFicheroAlbum.insertarListaEnBaseDatos(listaAux)) {
									/*este paso de insertar canciones no funciona porque el codigo de  album cambia ya que al eliminar y exportar aumenta
									el codigo y deja de ser el mismo que antes de elimanr album*/
									System.out.println("Se han insertado correctamente los datos del fichero en la base de datos");
								}
								else {
									System.out.println("No hay ningun dato en el fichero");
								}
							}
						}
						else {
							System.out.println("Ha habido un error en el importe de la tabla desde el fichero");
						}
					}
					else {
						System.out.println("Ha habido un error en el importe de la tabla desde el fichero");
					}


					break;
					//-----------------------------------------------

					//-----------------------------------------------
				default:
					System.out.println("Escoge una opcion correcta");
					break;
				}
			}
			catch(ClassNotFoundException cnfe){
				System.out.println("Error al cargar el conector de SQLite");
				cnfe.printStackTrace();
			}
			catch(SQLException sqle) {
				System.out.println("Error al acceder a la base de datos");
				sqle.printStackTrace();
			}
			catch(IOException ioe) {
				System.out.println("Error al escribir en el fichero: " + ioe.getMessage());
				ioe.printStackTrace();
			}
		}while(opcion!= 0);
	}


}
