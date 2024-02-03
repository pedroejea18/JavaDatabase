package acceso;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelos.Banda;

public class AccesoBanda {
	
	//Insertar banda
	public static int insertarBanda(Banda banda)
			throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL = "INSERT INTO banda " +
					"(nombre, años_actuacion, lugar_origen, genero) " +
					"VALUES ('" +  banda.getNombre() + "', '" + banda.getAnioActuacion() + 
					"', '" + banda.getLugarOrigen() + "', '" + banda.getGenero() + "')";
			Statement sentencia = (Statement) conexion.createStatement();
			int filasInsertadas = sentencia.executeUpdate(sentenciaSQL);
			return filasInsertadas;
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}

	//Actualizar banda
	public static int actualizarBanda(int codigo, Banda banda)
			throws SQLException, ClassNotFoundException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaActualizar = "UPDATE banda " +
					"SET nombre = '" + banda.getNombre() +
					"', años_actuacion = '" + banda.getAnioActuacion() + 
					"', lugar_origen = '" + banda.getLugarOrigen() +
					"', genero = '" + banda.getGenero() + "' " + "WHERE codigo = " + codigo;
			Statement sentencia = conexion.createStatement();
			int filasActualizadas = sentencia.executeUpdate(sentenciaActualizar);
			return filasActualizadas;
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	
	//Eliminar banda
	public static boolean eliminarBanda(int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		int filaEliminada = 0;
		
		try {
			conexion = ConfigBD.abrirConexion();
			
			String sentenciaEliminar = "DELETE FROM banda WHERE codigo = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaEliminar);
			
			sentencia.setInt(1, codigo);
			
			filaEliminada = sentencia.executeUpdate();
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
		return filaEliminada > 0;
	}
	
	//Consultar banda
	public static Banda consultarBanda(int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL = "SELECT * FROM banda WHERE codigo = " + codigo + ";";
			Statement sentencia = (Statement) conexion.createStatement();
			ResultSet resultados = ((java.sql.Statement) sentencia).executeQuery(sentenciaSQL);
			Banda banda = null;
			if (resultados.next()) {
				codigo = resultados.getInt("codigo");
				String nombre = resultados.getString("nombre");
				String anioActuacion = resultados.getString("años_actuacion");
				String lugarOrigen = resultados.getString("lugar_origen");
				String genero = resultados.getString("genero");
				banda = new Banda(codigo,nombre,anioActuacion,lugarOrigen,genero);
			}
			return banda;
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}

	//Consultar todo
	public static List<Banda> consultarTodo()
			throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL = "SELECT * FROM banda";
			Statement sentencia = (Statement) conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaSQL);
			List<Banda> listaBandas = new ArrayList<Banda>();
			while (resultados.next()) {
				int codigo = resultados.getInt("codigo");
				String nombre = resultados.getString("nombre");
				String anioActuacion = resultados.getString("años_actuacion");
				String lugarOrigen = resultados.getString("lugar_origen");
				String genero = resultados.getString("genero");
				Banda banda = new Banda(codigo,nombre,anioActuacion,lugarOrigen,genero);
				listaBandas.add(banda);
			}
			return listaBandas;
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
}
