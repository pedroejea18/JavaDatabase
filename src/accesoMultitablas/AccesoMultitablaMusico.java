package accesoMultitablas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import acceso.ConfigBD;
import modelosMultitabla.MultitablaMusico;


public class AccesoMultitablaMusico {
	
	public static final String NOMBRE_FICHERO = "src/FicherosTXT/consultaMusico.txt";

	/*Método para consultar a los músicos con los datos pedidos ordenados por instrumento. Se crea una lista donde se insertarán
	 * los músicos con los datos pedidos y se devolverá.
	 */
	public static List<MultitablaMusico> consultaMultitablaMusico() throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		MultitablaMusico musico = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL = "SELECT m.nombre, m.instrumento, a.titulo, count(a.titulo) as TotalCanciones "
					+ "FROM musico m JOIN album a ON (m.codigo = a.codigo_musico) "
					+ "GROUP BY a.titulo "
					+ "ORDER BY m.instrumento";
			
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaSQL);
			List<MultitablaMusico> listaMusicos = new ArrayList<MultitablaMusico>();
			while(resultados.next()) {
				String nombre = resultados.getString("nombre");
				String instrumento = resultados.getString("instrumento");
				
				String titulo = resultados.getString("titulo");
				int totalCan = resultados.getInt("TotalCanciones");	
				
				musico = new MultitablaMusico(nombre, instrumento, titulo, totalCan);
				listaMusicos.add(musico);
			}
			return listaMusicos;
			
		}
		finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	
	
	public static boolean insertarConsulta(List<MultitablaMusico> listaMusicos) throws IOException {
		BufferedWriter flujoEscritura = null;
		try {
			flujoEscritura = new BufferedWriter(new FileWriter(NOMBRE_FICHERO));
			for (MultitablaMusico musico : listaMusicos) {
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
	
}