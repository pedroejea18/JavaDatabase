package accesoFicheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import acceso.ConfigBD;
import modelos.Musico;

public class AccesoFicherosMusico {

	public static final String NOMBRE_FICHERO = "src/FicherosTXT/musicos.txt";
	public static final String NOMBRE_FICHERO2 = "src/FicherosTXT/musicosImportar.txt";
	
	//Inserción de todos los empleados de la base de datos al principio del fichero
	public static boolean insertarTodos(List<Musico> listaMusicos) throws IOException {
		BufferedWriter flujoEscritura = null;
		try {
			flujoEscritura = new BufferedWriter(new FileWriter(NOMBRE_FICHERO));
			for (Musico musico : listaMusicos) {
				flujoEscritura.write(musico.toStringWithSeparators());
				flujoEscritura.newLine();
			}
			return true;
		}
		finally{
			if (flujoEscritura != null) {
				flujoEscritura.close();
			}
		}
	}
	
	
	
	//Comprobación y consulta de todos los musicos en el fichero devolviendo una lista con los musicos consultados
	public static List<Musico> consultarMusicosParaExportar() throws IOException {
		BufferedReader flujoLectura =null;
		try {
			List<Musico> listaMusicos = new ArrayList<Musico>();
			flujoLectura = new BufferedReader(new FileReader(NOMBRE_FICHERO));
			
			String linea = flujoLectura.readLine();
			while (linea != null) {
				Musico musico = new Musico(linea);
				listaMusicos.add(musico);
				linea = flujoLectura.readLine();
			}
			return listaMusicos;
		}
		finally {
			if (flujoLectura != null) {
				flujoLectura.close();
			}
		}
	}
	
	//Método para consultar los músicos que hay en el fichero desde el fichero del que se importarán los músicos.
	public static List<Musico> consultarMusicosParaImportar() throws IOException {
		BufferedReader flujoLectura =null;
		try {
			List<Musico> listaMusicos = new ArrayList<Musico>();
			flujoLectura = new BufferedReader(new FileReader(NOMBRE_FICHERO2));
			
			String linea = flujoLectura.readLine();
			while (linea != null) {
				Musico musico = new Musico(linea);
				listaMusicos.add(musico);
				linea = flujoLectura.readLine();
			}
			return listaMusicos;
		}
		finally {
			if (flujoLectura != null) {
				flujoLectura.close();
			}
		}
	}
	
	
	//Método de tipo booleano para borrar todos los datos de la tabla en la base de datos que establece
	//que si las filas de músico donde se ejecuta la sentencia es mayor a 0 debe devolverse true.
	public static boolean borrarDatosBase() throws SQLException, ClassNotFoundException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaMusico = "DELETE FROM musico"; 
			Statement sentencia = conexion.createStatement();
			int filasMusico = sentencia.executeUpdate(sentenciaMusico);
			if (filasMusico > 0) {
				return true;
			}
			return false;
		}
		finally{
			ConfigBD.cerrarConexion(conexion);
		}
	}
	
	//Inserción de los datos de un fichero a la base de datos tomando una lista como parámetro.
	public static boolean insertarListaEnBase(List<Musico> listaMusicos) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		PreparedStatement sentenciaPreparada = null, sentenciaPreparada2 = null;
		
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaInsertarConBanda = "INSERT INTO musico (codigo, nombre, fecha_nacimiento, lugar_residencia, nacionalidad, instrumento, codigo_banda) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			String sentenciaInsertarSinBanda = "INSERT INTO musico (codigo, nombre, fecha_nacimiento, lugar_residencia, nacionalidad, instrumento, codigo_banda) "
					+ "VALUES (?, ?, ?, ?, ?, ?, null)";
			
			sentenciaPreparada = conexion.prepareStatement(sentenciaInsertarConBanda);
			sentenciaPreparada2 = conexion.prepareStatement(sentenciaInsertarSinBanda);
			
			for (Musico musico : listaMusicos) {
				
				if (musico.getBanda() == null) {
					sentenciaPreparada2.setInt(1, musico.getCodigo());
					sentenciaPreparada2.setString(2, musico.getNombre());
					sentenciaPreparada2.setString(3, musico.getFechaNacim());
					sentenciaPreparada2.setString(4, musico.getLugarResi());
					sentenciaPreparada2.setString(5, musico.getNacionalidad());
					sentenciaPreparada2.setString(6, musico.getInstrumento());
					sentenciaPreparada2.executeUpdate();
				}
				else {
					sentenciaPreparada.setInt(1, musico.getCodigo());
					sentenciaPreparada.setString(2, musico.getNombre());
					sentenciaPreparada.setString(3, musico.getFechaNacim());
					sentenciaPreparada.setString(4, musico.getLugarResi());
					sentenciaPreparada.setString(5, musico.getNacionalidad());
					sentenciaPreparada.setString(6, musico.getInstrumento());
					sentenciaPreparada.setInt(7, musico.getBanda().getCodigo());
					sentenciaPreparada.executeUpdate();
				}
				
				
			}
			return true;
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
}
