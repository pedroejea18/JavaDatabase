package modelosMultitabla;



public class MultitablaCancion {
    
    private static final String SEPARADOR = ";"; 

    private String compositor;
    private String tituloCancion;
    private String tituloAlbum;


    //Constructor normal
    public MultitablaCancion(String compositor, String tituloCancion, String tituloAlbum) {
        this.compositor = compositor;
        this.tituloCancion = tituloCancion;
        this.tituloAlbum = tituloAlbum;
    }

    //toString para escribir los datos a un fichero
	public String toStringWithSeparator(){
		return
            compositor + SEPARADOR + tituloCancion +
			SEPARADOR + tituloAlbum;

	}

    //Getters
    public String getCompositor() {
        return compositor;
    }

    public String getTituloCancion() {
        return tituloCancion;
    }

    public String getTituloAlbum() {
        return tituloAlbum;
    }


    //ToString
    @Override
    public String toString() {
        return "MultiTablaCancion [Compositor=" + compositor + ", tituloCancion=" + tituloCancion + ", tituloAlbum="
                + tituloAlbum + "]";
    }


    
    
    
    
}
