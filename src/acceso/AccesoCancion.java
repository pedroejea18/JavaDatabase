package acceso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import modelos.Album;
import modelos.Banda;
import modelos.Cancion;
import modelos.Musico;

public class AccesoCancion {

	//Metodo para consultar la posicion Maxima de una cancion en un album, esto sera para cuando estemos insertando canciones,
	//al cambiar de album y volver al album que estabamos insertando en un principio, la posicion sigua desde el ultimo dato que hay
	public static int consultarMaximoPosicion(int codigoAlbum) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Statement sentenciaPreparada = null;
		ResultSet resultado = null;
		int maximo = 0;

		try{
			conexion = ConfigBD.abrirConexion();

			String sentenciaConsultarMaximo = "SELECT max(posicion) as posicion from cancion " +
					"WHERE codigo_album = " + codigoAlbum;

			sentenciaPreparada = conexion.createStatement();
			resultado = sentenciaPreparada.executeQuery(sentenciaConsultarMaximo);
			while (resultado.next()){
				maximo = resultado.getInt("posicion");
			}
			return maximo;
		}
		finally {
			sentenciaPreparada.close();
			resultado.close();
			ConfigBD.cerrarConexion(conexion);
		}
	}



	//Consultar todos los Albumes para mostrarle al usuario antes de insertar las canciones
	public static List<Album> consultarAlbumes() throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		PreparedStatement sentenciaPreparada = null;
		ResultSet resultado = null;
		List<Album> listaCanciones = new LinkedList<>();
		Album cancion;
		Banda bandaAux;
		Musico musicoAux;
		try{
			conexion = ConfigBD.abrirConexion();

			String sentenciaConsultarTodasCanciones = "SELECT * FROM album";
			sentenciaPreparada = conexion.prepareStatement(sentenciaConsultarTodasCanciones);
			resultado = sentenciaPreparada.executeQuery();
			while (resultado.next()){
				int codigo = resultado.getInt("codigo");
				String autor = resultado.getString("autor");
				String titulo = resultado.getString("titulo");
				int anioPublicacion = resultado.getInt("año_publicacion");
				String tipo = resultado.getString("tipo");
				String duracion = resultado.getString("duracion");
				int codigoBanda = resultado.getInt("codigo_banda");
				int codigoMusico = resultado.getInt("codigo_musico");
				bandaAux = new Banda(codigoBanda);
				musicoAux = new Musico(codigoMusico);
				cancion = new Album(codigo,autor,titulo,anioPublicacion, tipo, duracion, bandaAux, musicoAux);

				listaCanciones.add(cancion);
			}
			return listaCanciones;
		}
		finally {
			sentenciaPreparada.close();
			resultado.close();

			ConfigBD.cerrarConexion(conexion);
		}
	}


	//Consultar Album por codigo para ver si el album existe y se puede insertar canciones
	public static Album consultarAlbumPorCodigoAlbum(int codigoAlbum) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		PreparedStatement sentenciaPreparada = null;
		ResultSet resultado = null;
		Album album = null;
		Banda bandaAux;
		Musico musicoAux;
		try{
			conexion = ConfigBD.abrirConexion();

			String sentenciaConsultarAlbumPorCodigoAlbum = "SELECT * FROM album " +
					"WHERE codigo = " + codigoAlbum;
			sentenciaPreparada = conexion.prepareStatement(sentenciaConsultarAlbumPorCodigoAlbum);
			resultado = sentenciaPreparada.executeQuery();
			while (resultado.next()){
				int codigo = resultado.getInt("codigo");
				String autor = resultado.getString("autor");
				String titulo = resultado.getString("titulo");
				int anioPublicacion = resultado.getInt("año_publicacion");
				String tipo = resultado.getString("tipo");
				String duracion = resultado.getString("duracion");
				int codigoBanda = resultado.getInt("codigo_banda");
				int codigoMusico = resultado.getInt("codigo_musico");
				bandaAux = new Banda(codigoBanda);
				musicoAux = new Musico(codigoMusico);
				album = new Album(codigo,autor,titulo,anioPublicacion, tipo, duracion, bandaAux, musicoAux);

			}
			return album;

		}
		finally {
			sentenciaPreparada.close();
			resultado.close();
			ConfigBD.cerrarConexion(conexion);
		}
	}



	//Consultar todas las canciones
	//Metodo auxiliar para mostrar datos al usuario
	public static List<Cancion> consultarTodasCanciones() throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Statement sentenciaPreparada = null;
		ResultSet resultado = null;
		List<Cancion> listaCanciones = new LinkedList<>();
		Cancion cancion = null;
		Album album = null;

		try{
			conexion = ConfigBD.abrirConexion();

			String sentenciaConsultarTodasCanciones = "SELECT * FROM cancion";
			sentenciaPreparada = conexion.createStatement();
			resultado = sentenciaPreparada.executeQuery(sentenciaConsultarTodasCanciones);

			while (resultado.next()){
				int codigo = resultado.getInt("codigo");
				int posicion = resultado.getInt("posicion");
				String titulo = resultado.getString("titulo");
				String compositor = resultado.getString("compositor");
				String duracion = resultado.getString("duracion");
				int codigoAlbum = resultado.getInt("codigo_album");
				album = new Album(codigoAlbum);
				cancion = new Cancion(codigo, posicion, titulo, compositor, duracion, album);

				listaCanciones.add(cancion);
			}
			return listaCanciones;
		}
		finally {
			sentenciaPreparada.close();
			resultado.close();
			ConfigBD.cerrarConexion(conexion);
		}
	}




	//Inserta una cancion a un Album
	public static int insertarCancionAlbum(Cancion cancion, Album album) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		PreparedStatement sentenciaPreparada = null;

		try {
			conexion = ConfigBD.abrirConexion();

			//Sentencia
			String sentenciaInsertarCancion = "INSERT INTO cancion (posicion, titulo, compositor, duracion, codigo_album) " +
					"VALUES (?, ?, ?, ?, ?)";
			sentenciaPreparada = conexion.prepareStatement(sentenciaInsertarCancion);
			sentenciaPreparada.setInt(1, cancion.getPosicion());
			sentenciaPreparada.setString(2, cancion.getTitulo());
			sentenciaPreparada.setString(3, cancion.getCompositor());
			sentenciaPreparada.setString(4, cancion.getDuracion());
			sentenciaPreparada.setInt(5, album.getCodigo());
			int filasInsertadas = sentenciaPreparada.executeUpdate();
			return filasInsertadas;

		}
		finally {
			sentenciaPreparada.close();
			ConfigBD.cerrarConexion(conexion);
		}
	}


	//Acutaliza una cancion por codigo
	public static int actualizarCancionPorCodigo(int codigo, int posicion, String titulo, String compositor, String duracion, Album album) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Statement sentenciaPreparada = null;

		try {
			conexion = ConfigBD.abrirConexion();

			//Sentencia
			String sentenciaActualizarCancionPorCodigo = "UPDATE cancion " +
					"SET posicion = '" + posicion +
					"', titulo = '" + titulo +
					"', compositor = '" + compositor +
					"', duracion = '" + duracion +
					"', codigo_album = '" + album.getCodigo() +
					"' " + "WHERE codigo = " + codigo;
			sentenciaPreparada = conexion.createStatement();
			int filasActualizadas = sentenciaPreparada.executeUpdate(sentenciaActualizarCancionPorCodigo);
			return filasActualizadas;
		}
		finally {
			sentenciaPreparada.close();
			ConfigBD.cerrarConexion(conexion);
		}
	}


	//Elimina una cancion por codigo
	public static int eliminarCancionPorCodigo (int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Statement sentenciaPreparada = null;

		try{
			conexion = ConfigBD.abrirConexion();

			String sentenciaEliminarPorCodigo = "DELETE FROM cancion " +
					"WHERE codigo = " + codigo;
			sentenciaPreparada = conexion.createStatement();
			int filasEliminadas = sentenciaPreparada.executeUpdate(sentenciaEliminarPorCodigo);
			return filasEliminadas;
		}
		finally {
			sentenciaPreparada.close();
			ConfigBD.cerrarConexion(conexion);
		}
	}

	//Consulta por codigo una cancion
	public static Cancion consultarCancionPorCodigo (int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Statement sentenciaPreparada = null;
		ResultSet resultado = null;
		Cancion cancion = null;
		Album album = null;

		try{
			conexion = ConfigBD.abrirConexion();

			String sentenciaConsultarCancionPorCodigo = "SELECT * FROM cancion "
					+ "WHERE codigo = " + codigo;
			sentenciaPreparada = conexion.createStatement();
			resultado = sentenciaPreparada.executeQuery(sentenciaConsultarCancionPorCodigo);
			while (resultado.next()){
				codigo = resultado.getInt("codigo");
				int posicion = resultado.getInt("posicion");
				String titulo = resultado.getString("titulo");
				String compositor = resultado.getString("compositor");
				String duracion = resultado.getString("duracion");
				int codigoAlbum = resultado.getInt("codigo_album");
				album = new Album(codigoAlbum);
				cancion = new Cancion(codigo, posicion, titulo, compositor, duracion, album);
			}
			return cancion;

		}
		finally {
			sentenciaPreparada.close();
			resultado.close();
			ConfigBD.cerrarConexion(conexion);
		}
	}



	//Consultar todas las canciones ordenadas por la duracion
	public static List<Cancion> consultarTodasCancionesOrdPorDuracion() throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Statement sentenciaPreparada = null;
		ResultSet resultado = null;
		List<Cancion> listaCanciones = new LinkedList<>();
		Cancion cancion = null;
		Album album = null;

		try{
			conexion = ConfigBD.abrirConexion();

			String sentenciaConsultarTodasCanciones = "SELECT * FROM cancion " + "ORDER BY duracion";
			sentenciaPreparada = conexion.createStatement();
			resultado = sentenciaPreparada.executeQuery(sentenciaConsultarTodasCanciones);

			while (resultado.next()){
				int codigo = resultado.getInt("codigo");
				int posicion = resultado.getInt("posicion");
				String titulo = resultado.getString("titulo");
				String compositor = resultado.getString("compositor");
				String duracion = resultado.getString("duracion");
				int codigoAlbum = resultado.getInt("codigo_album");
				album = new Album(codigoAlbum);
				cancion = new Cancion(codigo, posicion, titulo, compositor, duracion, album);
				listaCanciones.add(cancion);
			}
			return listaCanciones;
		}
		finally {
			sentenciaPreparada.close();
			resultado.close();
			ConfigBD.cerrarConexion(conexion);
		}
	}



	//Borrar todos los datos de la tabla Cancion en la base de datos
	//Este metodo es exclusivamente para antes de insertar los datos de un fichero a la base de datos
	public static boolean borrarDatosCancion() throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Statement sentenciaPreparada = null;

		try{
			conexion = ConfigBD.abrirConexion();

			String sentenciaEliminarDatos = "DELETE FROM cancion";
			sentenciaPreparada = conexion.createStatement();
			int filasEliminadas = sentenciaPreparada.executeUpdate(sentenciaEliminarDatos);
			if (filasEliminadas > 1){
				return true;
			}
			return false;

		}
		finally {
			sentenciaPreparada.close();
			ConfigBD.cerrarConexion(conexion);
		}
	}

}
