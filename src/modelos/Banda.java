package modelos;

public class Banda {
	
	//Datos de una banda
	private int codigo;
	private String nombre;
	private String anioActuacion;
	private String lugarOrigen;
	private String genero;
	
	//Crea una banda a partir de cinco parámetros
	public Banda(int codigo, String nombre, String anioActuacion, String lugarOrigen, String genero) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.anioActuacion = anioActuacion;
		this.lugarOrigen = lugarOrigen;
		this.genero = genero;
	}
	

	public Banda(String nombre, String anioActuacion, String lugarOrigen, String genero) {
		this.nombre = nombre;
		this.anioActuacion = anioActuacion;
		this.lugarOrigen = lugarOrigen;
		this.genero = genero;
	}
	
	public Banda(int codigo) {
		this.codigo=codigo;
	}
	
	public Banda(int codigo, String nombre) {
		this.codigo=codigo;
		this.nombre=nombre;
	}


	//Devuelve el estado de la banda
	@Override
	public String toString() {
		return "Banda [Código = " + codigo + ", Nombre = " + nombre + ", Año de actuación = " + anioActuacion
				+ ", Lugar de Origen = " + lugarOrigen + ", Género = " + genero + "]";
	}

	//Devuelve el código de la banda
	public int getCodigo() {
		return codigo;
	}

	//Modifica el código de la banda
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	//Devuelve el nombre de la banda
	public String getNombre() {
		return nombre;
	}

	//Modifica el nombre de la banda
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	//Devuelve el año de actuación de la banda
	public String getAnioActuacion() {
		return anioActuacion;
	}

	//Modifica en año de actuación de la banda
	public void setAnioActuacion(String anioActuacion) {
		this.anioActuacion = anioActuacion;
	}

	//Devuelve el lugar de origen de la banda
	public String getLugarOrigen() {
		return lugarOrigen;
	}

	//Modifica de lugar de origen de la banda
	public void setLugarOrigen(String lugarOrigen) {
		this.lugarOrigen = lugarOrigen;
	}

	//Devuelve el género de la banda
	public String getGenero() {
		return genero;
	}

	//Modifica el género de la banda
	public void setGenero(String genero) {
		this.genero = genero;
	}	
}