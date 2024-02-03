package accesoMultitablas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import acceso.ConfigBD;
import modelosMultitabla.MultitablaCancion;


public class AccesoMultitablaCancion {
    

    //MultiTabla Pedro
    //Consultará todos los compositores diferentes de canciones, indicando para cada compositor, los
    //títulos de las canciones que han compuesto y los títulos de los álbumes a los que pertenecen.
    //esta ordenado por el compositor de la canción de forma ascendente. 

    public static List<MultitablaCancion> consultaMultiTabla() throws ClassNotFoundException, SQLException {
        Connection conexion = null;
        Statement sentenciaPreparada = null;
        ResultSet resultado = null;
        List<MultitablaCancion> listaMultiTabla = new LinkedList<>();
        MultitablaCancion multiTablaCancion = null;

        try{
            conexion = ConfigBD.abrirConexion();

            String sentenciaMultiTablaCancion = "SELECT DISTINCT c.compositor, c.titulo, a.titulo " +
                                                "FROM cancion c " +
                                                "JOIN album a ON a.codigo = c.codigo_album " +
                                                "ORDER BY c.compositor ASC";
            sentenciaPreparada = conexion.createStatement();
            resultado = sentenciaPreparada.executeQuery(sentenciaMultiTablaCancion);

            while (resultado.next()){
                String compositor = resultado.getString("compositor");
                String tituloCancion = resultado.getString("titulo");
                String tituloAlbum = resultado.getString("titulo");
                multiTablaCancion = new MultitablaCancion(compositor, tituloCancion, tituloAlbum);
                listaMultiTabla.add(multiTablaCancion);
            }
            return listaMultiTabla;
        }
        finally {
            sentenciaPreparada.close();
            resultado.close();
            ConfigBD.cerrarConexion(conexion);
		}
    } 
}