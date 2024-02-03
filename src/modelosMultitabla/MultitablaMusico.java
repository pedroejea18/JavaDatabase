package modelosMultitabla;

public class MultitablaMusico {
		
	//creamos una clase aparte de músico para hacer la consulta de multitabla con los atributos correspondientes.
	//Ponemos su propio constructor y to String.
	
	
		private String nombre;
		private String instrumento;
		private String titulo;
		private int vecesTitulo = 0;
		
		public MultitablaMusico(String nombre, String instrumento, String titulo, int vecesTitulo) {
			this.nombre = nombre;
			this.instrumento = instrumento;
			this.titulo = titulo;
			this.vecesTitulo = vecesTitulo;
			
		}

		@Override
		public String toString() {
			return "ConsultaMultitablaMusico [Nombre del músico = " + nombre + ", Instrumento del músico = " + instrumento + ""
					+ ", Titulo del álbum = " + titulo
					+ ", Total de canciones del álbum = " + vecesTitulo + "]";
		}
		
		public String toStringWithSeparators( ) {
			String cadena="El/la músico " ;
			cadena = cadena + this.nombre + " toca el instrumento(o realiza labores de cantante) " + this.instrumento +
					". El título del álbum es " + this.titulo + " y tiene un total de "
					+ this.vecesTitulo + " canciones.";
			return cadena;
		}
}
