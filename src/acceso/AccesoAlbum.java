package acceso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.Album;
import modelos.Banda;
import modelos.Musico;

public class AccesoAlbum {
	/*dvuelve verdader cuando el String q le pasemos sea estudioso, directo o recopilatorio 
	 * si no devuelve falso*/
	public static boolean tipoCorrecta(String tipo) {
		if(tipo.equalsIgnoreCase("estudio")) {
			return true;

		}
		else if(tipo.equalsIgnoreCase("directo")) {
			return true;

		}
		else if(tipo.equalsIgnoreCase("recopilatorio")) {
			return true;

		}
		else {
			return false;
		}
	}
	/*devuelve verdadero si existe la clave ajena banda*/
	public static boolean claveAjenaBanda(int codigo) throws ClassNotFoundException, SQLException{
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL = "SELECT * FROM banda WHERE codigo = (?) ";
			PreparedStatement sentenciaPreparada= conexion.prepareStatement(sentenciaSQL);
			sentenciaPreparada.setInt(1, codigo);
			ResultSet resultadoDepartamento =  sentenciaPreparada.executeQuery();

			if(resultadoDepartamento.next()){
				return true;
			}
			else {
				return false;
			}
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	/*devuelve verdadero si existe la clave ajena musico*/
	public static boolean claveAjenaMusico(int codigo) throws ClassNotFoundException, SQLException{
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL = "SELECT * FROM musico WHERE codigo = (?) ";
			PreparedStatement sentenciaPreparada= conexion.prepareStatement(sentenciaSQL);
			sentenciaPreparada.setInt(1, codigo);
			ResultSet resultadoDepartamento =  sentenciaPreparada.executeQuery();

			if(resultadoDepartamento.next()){
				return true;
			}
			else {
				return false;
			}
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	/*devuelve verdadero si exiaste  un album con ese codigo */
	public static boolean albumCodigo(int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL = "SELECT * FROM album WHERE codigo = (?) ";
			PreparedStatement sentenciaPreparada= conexion.prepareStatement(sentenciaSQL);
			sentenciaPreparada.setInt(1, codigo);
			ResultSet resultadoDepartamento =  sentenciaPreparada.executeQuery();

			if(resultadoDepartamento.next()){
				return true;
			}
			else {
				return false;
			}
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}

	/*Caso 1: Insertar una fila en la tabla.
	 * devuelve verdadero si pudo insertar el album*/
	public static boolean insertarAlbum(Album album) throws SQLException, ClassNotFoundException {
		Connection conexion = null;
		boolean booleano = false;
		try {
			conexion = ConfigBD.abrirConexion();
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
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
		return booleano;
	}
	/*Caso 3:  Hacer dos metodos uno elimina todas las canciones con el codigo album que le pasemos y LUEGO borra el album*/
	
	/*Este metodo se encarga de borrar todas las canciones de un album que pasamos con codigo, como un album puede no tener canciones
	 * uso un booleano para que me muestre que ejecuta la sentencia*/
	public static boolean eliminarCancionAlbum(int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		boolean booleano = false;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL ="DELETE FROM cancion WHERE codigo_album = ?";
			PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
			sentenciaPreparada.setInt(1, codigo);
			sentenciaPreparada.executeUpdate();
			booleano = true;
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
		return booleano;
	}
	/*Elimina el album que pasamos por codigo, devuelve un entero con el resultado */
	public static int eliminarAlbum(int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL ="DELETE FROM album WHERE codigo = ?";
			PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
			sentenciaPreparada.setInt(1, codigo);
			return sentenciaPreparada.executeUpdate();
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	/*Caso 2*/
	/*Actualiza el album que le pasamos, devulve un entero con el resultado*/
	public static int actualizarAlbum(Album album) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			if(album.getAutor().equalsIgnoreCase("banda")) {
				String sentenciaSQL ="UPDATE album SET autor = ?, titulo = ?, año_publicacion = ?, "
						+ " tipo = ?, duracion = ?, codigo_banda = ?, codigo_musico = null WHERE codigo = ?";
				PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
				sentenciaPreparada.setString(1, album.getAutor());	
				sentenciaPreparada.setString(2, album.getTitulo());
				sentenciaPreparada.setInt(3, album.getAnioPublicacion());
				sentenciaPreparada.setString(4, album.getTipo());
				sentenciaPreparada.setString(5, album.getDuracion());
				sentenciaPreparada.setInt(6, album.getBanda().getCodigo());
				sentenciaPreparada.setInt(7, album.getCodigo());
				return sentenciaPreparada.executeUpdate();
			}
			else {
				String sentenciaSQL ="UPDATE album SET autor = ?, titulo = ?, año_publicacion = ?, "
						+ " tipo = ?, duracion = ?, codigo_banda = null, codigo_musico = ? WHERE codigo = ?";
				PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
				sentenciaPreparada.setString(1, album.getAutor());	
				sentenciaPreparada.setString(2, album.getTitulo());
				sentenciaPreparada.setInt(3, album.getAnioPublicacion());
				sentenciaPreparada.setString(4, album.getTipo());
				sentenciaPreparada.setString(5, album.getDuracion());
				sentenciaPreparada.setInt(6, album.getMusico().getCodigo());
				sentenciaPreparada.setInt(7, album.getCodigo());

				return sentenciaPreparada.executeUpdate();
			}
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	/*Caso 4*/
	/*Devuelve el album que hemos consultado por codigo*/
	public static Album consultarFila(int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Album albumAux = null;
		Banda bandaAux;
		Musico musicoAux;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL= "SELECT a.codigo, a.autor, a.titulo, a.año_publicacion, a.tipo, a.duracion, "
					+ "       m.codigo AS codigo_musico, m.nombre AS nombre_musico, m.fecha_nacimiento, m.lugar_residencia, m.nacionalidad, m.instrumento, "
					+ "       b.codigo AS codigo_banda, b.nombre AS nombre_banda, b.años_actuacion, b.lugar_origen, b.genero "
					+ " FROM album a"
					+ " LEFT JOIN musico m ON a.codigo_musico = m.codigo "
					+ " LEFT JOIN banda b ON a.codigo_banda = b.codigo"
					+ "	WHERE a.codigo = ?;";


			PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
			sentenciaPreparada.setInt(1, codigo);

			ResultSet resultadoAlbum = sentenciaPreparada.executeQuery();

			while(resultadoAlbum.next()) {
				String autor = resultadoAlbum.getString("autor");
				String titulo = resultadoAlbum.getString("titulo");
				int anioPublicacion = resultadoAlbum.getInt("año_publicacion");
				String tipo = resultadoAlbum.getString("tipo");
				String duracion = resultadoAlbum.getString("duracion");
				int codigoBanda = resultadoAlbum.getInt("codigo_banda");
				String nombreBanda = resultadoAlbum.getString("nombre_banda");
				int codigoMusico = resultadoAlbum.getInt("codigo_musico");
				String nombreMusico = resultadoAlbum.getString("nombre_musico");
				bandaAux = new Banda(codigoBanda, nombreBanda);
				musicoAux = new Musico(codigoMusico, nombreMusico);
				albumAux = new Album(codigo,autor,titulo,anioPublicacion, tipo, duracion, bandaAux, musicoAux);


			}
		}
		finally{
			ConfigBD.cerrarConexion(conexion);

		}
		return albumAux;
	}
	/*caso para elegir entre los albumes*/
	/*Devuelve  una lista con todos los albumes que hay en la base de datos */
	public static List<Album> consultarTodosAlbumes() throws ClassNotFoundException, SQLException{
		Connection conexion = null;
		List<Album> listaAux = new ArrayList<>();
		Album albumAux;
		Banda bandaAux;
		Musico musicoAux;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL= "SELECT a.codigo, a.autor, a.titulo, a.año_publicacion, a.tipo, a.duracion, "
					+ "       m.codigo AS codigo_musico, m.nombre AS nombre_musico, m.fecha_nacimiento, m.lugar_residencia, m.nacionalidad, m.instrumento, "
					+ "       b.codigo AS codigo_banda, b.nombre AS nombre_banda, b.años_actuacion, b.lugar_origen, b.genero "
					+ " FROM album a"
					+ " LEFT JOIN musico m ON a.codigo_musico = m.codigo "
					+ " LEFT JOIN banda b ON a.codigo_banda = b.codigo;";
			PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
			ResultSet resultadoAlbum = sentenciaPreparada.executeQuery();

			while(resultadoAlbum.next()) {
				int codigo = resultadoAlbum.getInt("codigo");
				String autor = resultadoAlbum.getString("autor");
				String titulo = resultadoAlbum.getString("titulo");
				int anioPublicacion = resultadoAlbum.getInt("año_publicacion");
				String tipo = resultadoAlbum.getString("tipo");
				String duracion = resultadoAlbum.getString("duracion");
				int codigoBanda = resultadoAlbum.getInt("codigo_banda");
				String nombreBanda = resultadoAlbum.getString("nombre_banda");
				int codigoMusico = resultadoAlbum.getInt("codigo_musico");
				String nombreMusico = resultadoAlbum.getString("nombre_musico");

				bandaAux = new Banda(codigoBanda, nombreBanda);
				musicoAux = new Musico(codigoMusico, nombreMusico);
				albumAux = new Album(codigo,autor,titulo,anioPublicacion, tipo, duracion, bandaAux, musicoAux);
				listaAux.add(albumAux);
			}
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
		return listaAux;
	}
	/*Caso 5*/
	/*Devuelve  una lista con todos los albumes que hay en la base de datos.*/
	public static List<Album> consultarTodosAlbumesOrdenados() throws ClassNotFoundException, SQLException{
		Connection conexion = null;
		List<Album> listaAux = new ArrayList<>();
		Album albumAux;
		Banda bandaAux;
		Musico musicoAux;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL= "SELECT a.codigo, a.autor, a.titulo, a.año_publicacion, a.tipo, a.duracion, "
					+ "       m.codigo AS codigo_musico, m.nombre AS nombre_musico, m.fecha_nacimiento, m.lugar_residencia, m.nacionalidad, m.instrumento, "
					+ "       b.codigo AS codigo_banda, b.nombre AS nombre_banda, b.años_actuacion, b.lugar_origen, b.genero "
					+ " FROM album a"
					+ " LEFT JOIN musico m ON a.codigo_musico = m.codigo "
					+ " LEFT JOIN banda b ON a.codigo_banda = b.codigo"
					+ " ORDER BY titulo desc;";
			PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
			ResultSet resultadoAlbum = sentenciaPreparada.executeQuery();

			while(resultadoAlbum.next()) {
				int codigo = resultadoAlbum.getInt("codigo");
				String autor = resultadoAlbum.getString("autor");
				String titulo = resultadoAlbum.getString("titulo");
				int anioPublicacion = resultadoAlbum.getInt("año_publicacion");
				String tipo = resultadoAlbum.getString("tipo");
				String duracion = resultadoAlbum.getString("duracion");
				int codigoBanda = resultadoAlbum.getInt("codigo_banda");
				String nombreBanda = resultadoAlbum.getString("nombre_banda");

				int codigoMusico = resultadoAlbum.getInt("codigo_musico");
				String nombreMusico = resultadoAlbum.getString("nombre_musico");

				bandaAux = new Banda(codigoBanda, nombreBanda);
				musicoAux = new Musico(codigoMusico, nombreMusico);
				albumAux = new Album(codigo,autor,titulo,anioPublicacion, tipo, duracion, bandaAux, musicoAux);
				listaAux.add(albumAux);
			}
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
		return listaAux;
	}
	/*Creamos un método con una lista de Músico. Tras abrir la conexión, ejecutaremos la sentencia correspondiente para obtener todos
	 * los datos de músico y de banda donde existan los codigos o sean nulo. Tras ello
	 * se creará una lista donde se insertarán los datos del músico y se devolverá ésta misma. 
	 */
	public static List<Musico> consultarTodos()
			throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL = "SELECT DISTINCT (m.codigo), m.nombre as nombreMusico, m.fecha_nacimiento, m.lugar_residencia, m.nacionalidad, "
					+ "m.instrumento, m.codigo_banda, b.nombre as nombreBanda, b.años_actuacion, "
					+ "b.lugar_origen, b.genero "
					+ "FROM musico m, banda b WHERE m.codigo_banda = b.codigo OR m.codigo_banda IS NULL "
					+ "GROUP BY (m.codigo)"; 


			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaSQL);
			List<Musico> listaMusicos = new ArrayList<Musico>();
			while (resultados.next()) {
				int codigo = resultados.getInt("codigo");
				String nombre = resultados.getString("nombreMusico");
				String fechaNacimiento = resultados.getString("fecha_nacimiento");
				String lugarResidencia = resultados.getString("lugar_residencia");
				String nacionalidad = resultados.getString("nacionalidad");
				String instrumento = resultados.getString("instrumento");


				int codigoBanda = resultados.getInt("codigo_banda");
				String nombreBanda = resultados.getString("nombreBanda");
				String añosActuacion = resultados.getString("años_actuacion");
				String lugarOrigen = resultados.getString("lugar_origen");
				String genero = resultados.getString("genero");


				Banda banda = new Banda (codigoBanda, nombreBanda, añosActuacion, lugarOrigen, genero);

				Musico musico = new Musico(codigo, nombre, fechaNacimiento, lugarResidencia, nacionalidad, instrumento, banda);
				listaMusicos.add(musico);
			}
			return listaMusicos;
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}

}
