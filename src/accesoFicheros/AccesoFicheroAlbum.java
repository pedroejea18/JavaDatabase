package accesoFicheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import acceso.ConfigBD;
import modelos.Album;
import modelos.Cancion;

public class AccesoFicheroAlbum {
	public static final String NOMBRE_FICHERO = "src/FicherosTXT/album.txt";
	public static final String NOMBRE_FICHERO2 = "src/FicherosTXT/importarAlbum.txt";
	//este otro es para exportar las cambionoes para luego importarlas  pero importar las canciones a la tabla no funciona por lo q expliqe en el main
	public static final String NOMBRE_FICHERO4 = "src/FicherosTXT/importarCancionesAlbum.txt";


	/*caso 6:*/
	/*Devuleve verdadero si se escriben los datos la lista de album en el fichero*/
	public static boolean escribirEnFichero(List<Album> listaAux) throws IOException {
		BufferedWriter flujoEscritura = null;
		Boolean booleano= false;
		try {
			flujoEscritura = new BufferedWriter(new FileWriter(NOMBRE_FICHERO));
			for(Album x : listaAux) {
				flujoEscritura.write(x.toStringWithSeparators());
				flujoEscritura.newLine();
			}
			booleano = true;
		}
		finally {
				if(flujoEscritura != null) {
					flujoEscritura.close();
				}
		}
		return booleano;
	}
	/*Caso 7: eliminamos primero las canciones ya que son una foreign key de album*/
	/*Devuelve verdadero si se ejecuta la sentencia de borrar todas las canciones
	 * esta hecho con un boolean por si hay 0 canciones se pueda ejecutar igualmente*/
	public static boolean eliminarCancionAlbumes() throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		boolean booleano = false;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL ="DELETE FROM cancion ";
			PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
			sentenciaPreparada.executeUpdate();
			booleano = true;
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
		return booleano;
	}
	/*Ahora borramos todos los albumes para poder insertaren la base dedatos los albumes del fichero*/
	/*Devuelve verdadero si se ejecuta la sentencia de borrar todas los albumes
	 * esta hecho con un boolean por si hay 0 albumes se pueda ejecutar igualmente*/
	public static boolean eliminarAlbumes() throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		boolean booleano = false;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL ="DELETE FROM album ";
			PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
			sentenciaPreparada.executeUpdate();
			booleano = true;

		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
		return booleano;

	}
	/*Devuelve una lista con los albumes que haya en el fichero de texto*/
	public static List<Album> importarFichero() throws IOException{
		List<Album> listaAux = new ArrayList<>();
		BufferedReader flujoLectura = null;
		try {
			flujoLectura = new BufferedReader(new FileReader(NOMBRE_FICHERO2));
			String linea = flujoLectura.readLine();
			while(linea!= null) {
				Album x = new Album(linea);
				listaAux.add(x);
				linea = flujoLectura.readLine();
			}
		}
		finally {
			if(flujoLectura != null) {
				flujoLectura.close();
			}

		}


		return listaAux;
	}
	/*Devuelve verdadero si inserta los datosde la lista de albumes en la base de datos*/
	public static boolean insertarListaEnBaseDatos(List<Album> listaAux) throws SQLException, ClassNotFoundException{
		Connection conexion = null;
		boolean booleano = false;
		try {
			conexion = ConfigBD.abrirConexion();
			for(Album album : listaAux) {
				if(album.getAutor().equalsIgnoreCase("banda")) {
					String sentenciaSQL ="INSERT INTO album (autor, titulo, año_publicacion, tipo, duracion, codigo_banda) "
							+"VALUES (?, ?, ?, ?, ? , ?)";
					PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
					sentenciaPreparada.setString(1, album.getAutor());
					sentenciaPreparada.setString(2,album.getTitulo());
					sentenciaPreparada.setInt(3, album.getAnioPublicacion());
					sentenciaPreparada.setString(4,album.getTipo());
					sentenciaPreparada.setString(5,album.getDuracion());
					sentenciaPreparada.setInt(6, album.getBanda().getCodigo());

					sentenciaPreparada.executeUpdate();
					booleano = true;
				}
				else {

					String sentenciaSQL ="INSERT INTO album (autor, titulo, año_publicacion, tipo, duracion ,codigo_banda, codigo_musico) "
							+"VALUES (?, ?, ?, ?, ? , null, ?)";
					PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
					sentenciaPreparada.setString(1, album.getAutor());
					sentenciaPreparada.setString(2,album.getTitulo());
					sentenciaPreparada.setInt(3, album.getAnioPublicacion());
					sentenciaPreparada.setString(4,album.getTipo());
					sentenciaPreparada.setString(5,album.getDuracion());
					sentenciaPreparada.setInt(6, album.getMusico().getCodigo());

					sentenciaPreparada.executeUpdate();
					booleano =  true;
				}
			}

		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
		return booleano;
	}
	
	//para importar cacniones 
	// Inserta los empleados de una lista al principio del fichero.
	public static boolean insertarTodos(List<Cancion> listaCanciones) 
	throws IOException {
		BufferedWriter flujoEscritura = null;
		try {
			flujoEscritura = new BufferedWriter(new FileWriter(NOMBRE_FICHERO4));
			for (Cancion cancion : listaCanciones) {
				flujoEscritura.write(cancion.toStringWithSeparator());
				flujoEscritura.newLine();
			}
			return true;
		}
		finally {
			if (flujoEscritura != null) {
				flujoEscritura.close();
			}
		}
	}
}