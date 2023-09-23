package combinaciones;

import java.util.Scanner;

public class Grafos {
	static int matrizAdyacencia[][];
	static int matrizPesos[][];
	int tamano;
	
	
	public static void main (String[]args) {
		Grafos Funciones = new Grafos();
		Funciones.pedirDatos();
		Funciones.imprimirMatriz(matrizAdyacencia,"adyacencia");
		int mayor = Funciones.imprimirMatriz(matrizPesos, "pesos");
		Funciones.encontrarValorMenor(matrizPesos,mayor);
	}
	
	private void pedirDatos() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Cantidad de nodos del grafo: ");
		tamano = sc.nextInt();
		matrizAdyacencia = new int[tamano][tamano];
		matrizPesos = new int[tamano][tamano];
		for(int i=0;i<tamano;i++) {
			for(int j=0; j<tamano;j++) {
				do {
					System.out.println("El nodo "+(i+1)+" tiene conexion con el nodo "+(j+1)+" (1=si/0=no): " );
					matrizAdyacencia[i][j]=sc.nextInt();
					if(matrizAdyacencia[i][j]!=0 && matrizAdyacencia[i][j]!=1) {
						System.out.println("El valor solo puede ser 0 o 1 ");
					}
				}while(matrizAdyacencia[i][j]!=0 && matrizAdyacencia[i][j]!=1);
			}
		}
		for(int i=0;i<tamano;i++) {
			for(int j=0; j<tamano;j++) {
				if(matrizAdyacencia[i][j]==1) {
					do {
						System.out.println("Valor del arco del nodo "+(i+1)+" al "+(j+1)+": ");
						matrizPesos[i][j]=sc.nextInt();
						if(matrizPesos[i][j]<=0) {
							System.out.println("El valor del arco no puede ser menor a 0 o 0");
						}
					}while(matrizPesos[i][j]<=0);
				}else {
					matrizPesos[i][j]=0;
				}
			}
		}
		sc.close();
	}
	
	private int imprimirMatriz(int[][]matriz,String titulo) {
		int mayor=0;
		System.out.println("Matriz de "+titulo);
		for (int i=0;i<tamano;i++) {
			for (int j=0;j<tamano;j++) {
				System.out.print(matriz[i][j]+"\t");
				if(matriz[i][j]>mayor) {
					mayor=matriz[i][j];
				}
			}
			System.out.println();
		}
		System.out.println();
		return mayor;
	}
	
	private void encontrarValorMenor(int[][]matriz, int mayor) {
		int menor_fila=mayor;
		int menor_columna=mayor;
		for (int i=0;i<tamano;i++) {
			menor_fila=mayor;
			menor_columna=mayor;
			for (int j=0;j<tamano;j++) {
				if(matriz[i][j]<menor_fila && matriz[i][j]!=0) {
					menor_fila=matriz[i][j];	
				}
				if(matriz[j][i]<menor_columna && matriz[j][i]!=0) {
					menor_columna=matriz[j][i];
				}
			}
			System.out.println("El valor menor de la fila "+(i+1)+ " es: "+menor_fila);
			System.out.println("El valor menor de la columna "+(i+1)+ " es: "+menor_columna);
		}
		System.out.println();
	}
}
