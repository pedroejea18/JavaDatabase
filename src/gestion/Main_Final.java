package gestion;

import entrada.Teclado;

public class Main_Final {
	public static void escribirMenuFinal() {
		System.out.println("---------------------------------------------------------------------");
		System.out.println("(0) Salir del programa");
		System.out.println("(1) Acceso a  Banda");
		System.out.println("(2) Acceso a  Musico");
		System.out.println("(3) Acceso a  Album");
		System.out.println("(4) Acceso a  Cancion");
		System.out.println("(5) Acceso Multitblas");
		System.out.println("---------------------------------------------------------------------");
	}

	public static void main(String[] args) {
		int opcion;
		do {
			escribirMenuFinal();
			opcion = Teclado.leerEntero("Elige una opcion:  ");
			switch(opcion) {
			case 0:
				break;
			case 1:
				Main_Banda.main(args);
				break;
			case 2:
				Main_Musico.main(args);
				break;
			case 3:
				Main_Album.main(args);
				break;
			case 4:
				Main_Cancion.main(args);
				break;
			case 5:
				Main_Multitablas.main(args);
				break;
			default:
				System.out.println("Elige una opcion correcta.");
				break;
			}
		}
		while(opcion != 0);

	}

}
