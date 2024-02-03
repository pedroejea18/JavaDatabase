package modelos;

public class Musico {
	
	//Genero un separador para que imprima correctamente los datos de la base en el fichero 
	private static final String SEPARADOR = ";";

	private int codigo;
	private String nombre;
	private String fechaNacim;
	private String lugarResi;
	private String nacionalidad;
	private String instrumento;
	private Banda banda;
	
	//constructor para leer los datos de aquellos músicos que si tengan una banda
	public Musico(int codigo, String nombre, String fechaNacim, String lugarResi, String nacionalidad,
			String instrumento, Banda banda) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.fechaNacim = fechaNacim;
		this.lugarResi = lugarResi;
		this.nacionalidad = nacionalidad;
		this.instrumento = instrumento;
		this.banda = banda;
	}
	
	//constructor para leer los datos de aquellos músicos que no tengan una banda y devuelva todos los datos a excepción del codigo de la banda
	public Musico(int codigo, String nombre, String fechaNacim, String lugarResi, String nacionalidad,
			String instrumento) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.fechaNacim = fechaNacim;
		this.lugarResi = lugarResi;
		this.nacionalidad = nacionalidad;
		this.instrumento = instrumento;
	}
	
	//constructor para leer los datos de un fichero
	public Musico(String linea) {
		String[] datos = linea.split(SEPARADOR); 
		this.codigo = Integer.parseInt(datos[0]);
		this.nombre = datos[1];
		this.fechaNacim = datos[2];
		this.lugarResi = datos[3];
		this.nacionalidad = datos[4];
		this.instrumento = datos [5];
		
		if (datos[6].equals("null")) {
			this.banda = null;
		}
		else {
			int codigoBanda = Integer.parseInt(datos[6]);
			this.banda = new Banda(codigoBanda);
		}
		
		
		
	}
	
	//constructor que devuelva únicamente la clave primaria
	public Musico (int codigo) {
		this.codigo = codigo;
	}
	
	public Musico (int codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}

	
	
	
	
	@Override
	
	public String toString() {
		String cadena = "";
		cadena = cadena + "Musico [Código = " + codigo + ", Nombre = " + nombre + ", Fecha de nacimiento = " + fechaNacim + ", Lugar de residencia = "	
		+ lugarResi + ", Nacionalidad = " + nacionalidad + ", Instrumento = " + instrumento;
		if (banda != null) {
			cadena = cadena + ", Código de banda = " + banda.getCodigo() + "]";
		}
		else {
			cadena = cadena + "]";
		}
		return cadena;
	}

	//Aquí se pone un toString con separadores para escribir el alumno en el fichero con el separador arriba indicado
	public String toStringWithSeparators( ) {
		String cadena = "";
		cadena = cadena + this.codigo + SEPARADOR + this.nombre + SEPARADOR + this.fechaNacim + SEPARADOR + this.lugarResi + SEPARADOR + this.nacionalidad + SEPARADOR + this.instrumento + SEPARADOR;
		
		if (banda != null) {
			cadena = cadena + banda.getCodigo();
		}
		else {
			cadena = cadena + " null";
		}
		return cadena;
				
				
	}
	
	
	
	
	//GETTERS & SETTERS
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaNacim() {
		return fechaNacim;
	}

	public void setFechaNacim(String fechaNacim) {
		this.fechaNacim = fechaNacim;
	}

	public String getLugarResi() {
		return lugarResi;
	}

	public void setLugarResi(String lugarResi) {
		this.lugarResi = lugarResi;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getInstrumento() {
		return instrumento;
	}

	
	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}

	public Banda getBanda() {
		return banda;
	}

	public void setCod_banda(Banda banda) {
		this.banda = banda;
	}
	
	
	
}
