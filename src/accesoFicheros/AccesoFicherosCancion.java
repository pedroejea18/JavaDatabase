package accesoFicheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import acceso.ConfigBD;
import modelos.Cancion;
import modelosMultitabla.MultitablaCancion;

public class AccesoFicherosCancion {
    public static final String NOMBRE_FICHERO_INSERTAR_BD__AL_FICHERO = "src/FicherosTXT/FicheroCancionesListToTXT.txt";
	public static final String NOMBRE_FICHERO_INSERTAR_DATOS_A_BD ="src/FicherosTXT/FicheroCancionesDatosToList.txt";
	public static final String NOMBRE_FICHERO_INSERTAR_MULTITABLA_FICHERO = "src/FicherosTXT/FicheroMultiTabla.txt";

    //Inserta la lista de datos de la consulta multitabla a un fichero
	public static boolean insertarMultiTabla(List<MultitablaCancion> listaMultiTabla) throws IOException{
		BufferedWriter flujoEscritura = null;	
		try{
			flujoEscritura = new BufferedWriter(new FileWriter(NOMBRE_FICHERO_INSERTAR_MULTITABLA_FICHERO));
			for (MultitablaCancion cancion : listaMultiTabla){
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
	
	// Inserta los empleados de una lista al principio del fichero.
	public static boolean insertarTodos(List<Cancion> listaCanciones) 
	throws IOException {
		BufferedWriter flujoEscritura = null;
		try {
			flujoEscritura = new BufferedWriter(new FileWriter(NOMBRE_FICHERO_INSERTAR_BD__AL_FICHERO));
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


    // Consulta todos los empleados del fichero.
	// Devuelve una lista con los empleados consultados.
	public static List<Cancion> consultarTodos() 
	throws IOException {
		BufferedReader flujoLectura = null;
		try {
			List<Cancion> listaEmpleados = new LinkedList<Cancion>();
			flujoLectura = new BufferedReader(new FileReader(NOMBRE_FICHERO_INSERTAR_DATOS_A_BD));
			String linea = flujoLectura.readLine();
			while (linea != null) {
				Cancion cancion = new Cancion(linea);
				listaEmpleados.add(cancion);
				linea = flujoLectura.readLine();
			}
			return listaEmpleados;
		}
		finally {
			if (flujoLectura != null) {
				flujoLectura.close();
			}
		}
	}


    //Inserta los datos de una lista, a la base de datos
    //Toma por parametros la lista
    public static boolean insertarListaEnBaseDatos(List<Cancion> listaCanciones) throws ClassNotFoundException, SQLException {
        Connection conexion = null;
        PreparedStatement sentenciaPreparada = null;

        try{
            conexion = ConfigBD.abrirConexion();
            System.out.println("Conectado");

            String sentenciaInsertarCancion = "INSERT INTO cancion (posicion, titulo, compositor, duracion, codigo_album) " +
                                              "VALUES (?, ?, ?, ?, ?)";
            
            for (Cancion cancion : listaCanciones){
                sentenciaPreparada = conexion.prepareStatement(sentenciaInsertarCancion);
                sentenciaPreparada.setInt(1, cancion.getPosicion());
                sentenciaPreparada.setString(2, cancion.getTitulo());
                sentenciaPreparada.setString(3, cancion.getCompositor());
                sentenciaPreparada.setString(4, cancion.getDuracion());
                sentenciaPreparada.setInt(5, cancion.getAlbum().getCodigo());
                sentenciaPreparada.executeUpdate();
            }
			return true;
        }
        finally {
            sentenciaPreparada.close();
            ConfigBD.cerrarConexion(conexion);
        }

    }

    


}
