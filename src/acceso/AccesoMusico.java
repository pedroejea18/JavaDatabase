package acceso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.Banda;
import modelos.Musico;

public class AccesoMusico {

	//INSERTAR
	
	/*Método de inserción de músicos que posean una banda. El método tendrá todos los parámetros del músico junto al código de su banda
	 * y al abrir la conexión, insertará a éste músico. El método será de tipo booleano, por lo que si la inserción tiene éxito, devolverá
	 * si las filas insertadas son más de 0. 
	 */
	public static boolean insertar(String nombre, String fechaNacim, String lugarResi, String nacionalidad, String instrumento, int codBanda) 
			throws ClassNotFoundException, SQLException{
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaInsertar = "INSERT INTO musico(nombre, fecha_nacimiento, lugar_residencia, nacionalidad, instrumento, codigo_banda) " +
					"VALUES ('" + nombre + "', '" + fechaNacim + "', '" + lugarResi + 
					"', '" + nacionalidad + "', '" + instrumento + "', " + codBanda + ")";
			Statement sentencia = conexion.createStatement();
			int filasInsertadas = sentencia.executeUpdate(sentenciaInsertar);
			return (filasInsertadas > 0);

		}
		finally {	
			ConfigBD.cerrarConexion(conexion);
		}
	}
	
	/*Método de inserción de músicos que no posean una banda. El método tendrá todos los parámetros del músico sin el código de banda,
	 * y al abrir la conexión, insertará a éste músico. El método será de tipo booleano, por lo que si la inserción tiene éxito, devolverá
	 * si las filas insertadas son más de 0. 
	 */
	public static boolean insertar2(String nombre, String fechaNacim, String lugarResi, String nacionalidad, String instrumento) 
			throws ClassNotFoundException, SQLException{
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaInsertar = "INSERT INTO musico(nombre, fecha_nacimiento, lugar_residencia, nacionalidad, instrumento) " +
					"VALUES ('" + nombre + "', '" + fechaNacim + "', '" + lugarResi + 
					"', '" + nacionalidad + "', '" + instrumento + "')";
			Statement sentencia = conexion.createStatement();
			int filasInsertadas = sentencia.executeUpdate(sentenciaInsertar);
			return (filasInsertadas > 0);

		}
		finally {	
			ConfigBD.cerrarConexion(conexion);
		}
	}

	
	
	
	
	
	
	//ACTUALIZAR
	
	/*Método de actualizar de tipo entero que tiene como parámetro si el músico tiene banda y un objeto músico. Al abrir la conexión,
	 * el método comprobará si el músico tiene una banda o no y en caso de que sí, lo actualizará con el código de la banda incluido
	 * devolviendo las filas actualizadas siempre que el código. En caso de que no, lo hará sin el código de la banda.
	 */
	public static int actualizar (int tieneBanda, Musico musico) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();

			if (tieneBanda == 1) {
				String sentenciaActualizar = "UPDATE musico " + "SET nombre = '" + musico.getNombre() + "', fecha_nacimiento = '" + musico.getFechaNacim() + 
						"', lugar_residencia = '" + musico.getLugarResi() + "', nacionalidad = '" + musico.getNacionalidad() + "', instrumento = '" +
						musico.getInstrumento() + "', codigo_banda = " + musico.getBanda().getCodigo() + " WHERE codigo = " + musico.getCodigo();
				Statement sentencia = conexion.createStatement();
				int filasActualizadas = sentencia.executeUpdate(sentenciaActualizar);
				return filasActualizadas;
			}
			else {
				String sentenciaActualizar = "UPDATE musico " + "SET nombre = '" + musico.getNombre() + "', fecha_nacimiento = '" + musico.getFechaNacim() + 
						"', lugar_residencia = '" + musico.getLugarResi() + "', nacionalidad = '" + musico.getNacionalidad() + "', instrumento = '" +
						musico.getInstrumento() + "' " + " WHERE codigo = " + musico.getCodigo();
				Statement sentencia = conexion.createStatement();
				int filasActualizadas = sentencia.executeUpdate(sentenciaActualizar);
				return filasActualizadas;
			}
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}

	
	
	
	
	
	//ELIMINAR
	/*Método de eliminar. Es de tipo booleano y pide un código como parámetro. Tras abrir la conexión, se usará la sentencia
	 * de borrado cuando el código sea el mismo que el código usado de parámetro sea el mismo que el de la base de datos. Tras
	 * ello se vevolverán las filas actualizadas en caso de que sea mayor de 0.
	*/
	public static boolean eliminar (int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaEliminar = "DELETE FROM musico " + "WHERE codigo = " + codigo;
			Statement sentencia = conexion.createStatement();
			int filasActualizadas = sentencia.executeUpdate(sentenciaEliminar);
			return (filasActualizadas > 0);
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}

	
	
	
	
	
	//MÉTODOS DE CONSULTA
	/*Método para consutar los músicos que no poseen una banda. Es del tipo del objeto Musico y pide de parámetro un código.
	 * Después de abrir la conexión, hacemos la consulta con ese código dentro de la base de datos y devuelve todos los datos 
	 * de ese músico.
	 */
	public static Musico consultarMusicoSinBanda (int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Musico musico = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaConsultar = "SELECT * FROM musico WHERE codigo = " + codigo;
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);	
			while(resultados.next()) {
				int codigoAux = resultados.getInt("codigo");
				String nombre = resultados.getString("nombre");
				String fecha_nacimiento = resultados.getString("fecha_nacimiento");
				String lugar_residencia = resultados.getString("lugar_residencia");
				String nacionalidad = resultados.getString("nacionalidad");
				String instrumento = resultados.getString("instrumento");

				musico = new Musico(codigoAux, nombre, fecha_nacimiento, lugar_residencia, nacionalidad, instrumento);

			}
			return musico;
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}

	/*Método para consutar los músicos que poseen una banda. Es del tipo del objeto Musico y pide de parámetro un código.
	 * Después de abrir la conexión, hacemos la consulta con ese código dentro de la base de datos y devuelve todos los datos 
	 * de ese músico junto al código de banda que será el mismo código del objeto Banda.
	 */
	public static Musico consultarMusicoConBanda (int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Musico musico = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaConsultar = "SELECT * FROM musico WHERE codigo = " + codigo;
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);	
			while(resultados.next()) {
				codigo = resultados.getInt("codigo");
				String nombre = resultados.getString("nombre");
				String fechaNacimiento = resultados.getString("fecha_nacimiento");
				String lugarResidencia = resultados.getString("lugar_residencia");
				String nacionalidad = resultados.getString("nacionalidad");
				String instrumento = resultados.getString("instrumento");
				int codigoBanda = resultados.getInt("codigo_banda");
				Banda banda = new Banda(codigoBanda);
				
				musico = new Musico(codigo, nombre, fechaNacimiento, lugarResidencia, nacionalidad, instrumento, banda);

			}
			return musico;
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}

	/*Creamos un método con una lista de Músico. Tras abrir la conexión, ejecutaremos la sentencia correspondiente para obtener todos
	 * los datos de músico y de banda donde existan los codigos o sean nulos. Además de ordenarlos por el nombre de músico. Tras ello
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
					+ "GROUP BY (m.codigo) ORDER BY m.nombre"; 


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
