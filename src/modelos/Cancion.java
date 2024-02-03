package modelos;

public class Cancion {
	
	//Separador para Ficheros
	private static final String SEPARADOR = ";"; 

	//Codigo autoincremental posicion
	private static int posicionIncremental = 0;

	private int codigo;
	private int posicion; //posicion en el album
	private String titulo;
	private String compositor;
	private String duracion;
	private Album album;
	
	//Constructor con codigo
	public Cancion(int codigo, int posicion, String titulo, String compositor, String duracion, Album album) {
		this.codigo = codigo;
		this.posicion = posicion;
		this.titulo = titulo;
		this.compositor = compositor;
		this.duracion = duracion;
		this.album = album;
	}
	
	//Constructor sin el codigo autoincremental de la base de datos
	public Cancion(String titulo, String compositor, String duracion, Album album) {
		posicionIncremental++;
		this.posicion = posicionIncremental;
		this.titulo = titulo;
		this.compositor = compositor;
		this.duracion = duracion;
		this.album = album;
	}
 
	
	public Cancion(int posicion) {
		this.posicion = posicion;
	}

	//Constructor para leer los datos de un fichero
	public Cancion(String linea) {
		String[] datos = linea.split(SEPARADOR);
		this.codigo = Integer.parseInt(datos[0]);
		this.posicion = Integer.parseInt(datos[1]);
		this.titulo = datos[2];
		this.compositor = datos[3];
		this.duracion = datos[4];
		int codigoAlbum = Integer.parseInt(datos[5]);
		this.album = new Album(codigoAlbum);
	}


	//toString para escribir los datos a un fichero
	public String toStringWithSeparator(){
		return
			codigo + SEPARADOR + posicion +
			SEPARADOR + titulo + SEPARADOR +
			compositor + SEPARADOR + duracion +
			SEPARADOR + album.getCodigo();

	}

	@Override
	public String toString() {
		return "cancion [codigo=" + codigo + ", posicion=" + posicion + ", titulo=" + titulo + ", compositor="
				+ compositor + ", duracion=" + duracion + ", codigo_album=" + album.getCodigo() + "]";
	}


	public static int getPosicionIncremental() {
		return posicionIncremental;
	}

	public static void setPosicionIncremental(int posicionIncremental) {
		Cancion.posicionIncremental = posicionIncremental;
	}

	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public int getPosicion() {
		return posicion;
	}


	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getCompositor() {
		return compositor;
	}


	public void setCompositor(String compositor) {
		this.compositor = compositor;
	}


	public String getDuracion() {
		return duracion;
	}


	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}


	public Album getAlbum(){
		return album;
	}

	public void setCod_banda(Album album) {
		this.album = album;
	}

	public static String getSeparador() {
		return SEPARADOR;
	}

}
