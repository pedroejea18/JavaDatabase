package gestion;

import java.sql.SQLException;
import java.util.List;

import acceso.AccesoBanda;
import entrada.Teclado;
import modelos.Banda;

public class Main_Banda {
	
	//Menu de opciones
	public static void escribirMenuOpciones() {
		System.out.println();
		System.out.println("(0) Volver a la seleccion");
		System.out.println("(1) Insertar una banda.");
		System.out.println("(2) Actualizar una banda.");
		System.out.println("(3) Eliminar una banda.");
		System.out.println("(4) Consultar una banda.");
		System.out.println("(5) Consultar todas las bandas");
		System.out.println("(6) Exportar la tabla.");
		System.out.println("(7) Importar la tabla.");
		System.out.println();
	}
	
	//Escribir lista de bandas
	public static void escribirListaBandas(List<Banda> listaBandas) {
		for (Banda banda : listaBandas) {
			System.out.println(banda.toString());
		}
		System.out.println("Se han consultado " + listaBandas.size() + 
				" bandas de la base de datos.");
	}

	public static void main(String[] args) {
		int opcion, codigo, filasAfectadas;
		String nombre,anioActuacion, lugarOrigen, genero;
		Banda banda;
		List<Banda> listaBandas;
		do {
			escribirMenuOpciones();
			opcion = Teclado.leerEntero("¿Opción (0-7)? ");	
			try {
				switch (opcion) {
				// Salir del programa.
				case 0:
					break;

					// Insertar una banda.
				case 1:
					nombre = Teclado.leerCadena("¿Nombre?");
					anioActuacion = Teclado.leerCadena("¿Años de actuación?");
					lugarOrigen = Teclado.leerCadena("¿Lugar de origen?");
					genero = Teclado.leerCadena("¿Género?");
					banda = new Banda(nombre, anioActuacion, lugarOrigen, genero);
					filasAfectadas = AccesoBanda.insertarBanda(banda);
					if (filasAfectadas == 1) {
						System.out.println("Se ha insertado una pelicula en la base de datos.");
					}
					break;

					// Actualizar una banda.
				case 2:
					codigo = Teclado.leerNatural("¿Código?");
					banda = AccesoBanda.consultarBanda(codigo);
					if (banda == null) {
						System.out.println("No se ha encontrado ninguna banda con ese código");
					}
					else {
						nombre = Teclado.leerCadena("¿Nombre? ");
						anioActuacion = Teclado.leerCadena("¿Años de actuación?");
						lugarOrigen = Teclado.leerCadena("¿Lugar de origen?");
						genero = Teclado.leerCadena("¿Género?");
						banda.setNombre(nombre);
						banda.setAnioActuacion(anioActuacion);
						banda.setLugarOrigen(lugarOrigen);
						banda.setGenero(genero);
						filasAfectadas = AccesoBanda.actualizarBanda(codigo, banda);
						if (filasAfectadas == 1) {
							System.out.println("Se ha actualizado una banda de la base de datos");
						}
					}
					break;

					// Eliminar una banda.
				case 3:
					codigo = Teclado.leerEntero("¿Código? ");
					if (!AccesoBanda.eliminarBanda(codigo)) {
						System.out.println("Error al eliminar la banda.");
					}
					else {
						System.out.println("Banda eliminada correctamente.");
					}
					break;

					// Consultar una banda.
				case 4:
					codigo = Teclado.leerNatural("¿Código?");
					banda = AccesoBanda.consultarBanda(codigo);
					if (banda == null) {
						System.out.println("No se ha encontrado ninguna banda con este código");
					}
					else {
						System.out.println(banda.toString());
					}
					break;

					// Consultar todas las bandas
				case 5:
					listaBandas = AccesoBanda.consultarTodo();
					if (listaBandas.isEmpty()) {
						System.out.println("No se ha encontrado ninguna banda.");
					}
					else {
						escribirListaBandas(listaBandas);
					}
					break;
					
					// Exportar la tabla.
				case 6:
					
					break;
					
					// Importar la tabla.
				case 7:
					
					break;

					// opcion no valida
				default:
					System.out.println("La opci�n de men� debe estar comprendida entre 0 y 5.");
				}
			}
			catch (ClassNotFoundException cnfe) {
				System.out.println("Error al cargar el controlador de SQLite.");
				cnfe.printStackTrace();
			} 
			catch (SQLException sqle) {
				System.out.println("Error al acceder a la base de datos SQLite.");
				sqle.printStackTrace();
			}
		}
		while (opcion != 0);
		System.out.println("Programa finalizado sin errores.");

}
}
