package combinaciones;

import java.util.Scanner;

public class MochilaBinaria {
	int [] beneficio;
	int [] peso;
	int capacidad_maxima;
	int objetos;
	int cant_combinaciones;
	int [][] combinaciones;
	int mayor_beneficio=0;
	int peso_min;
	
	public static void main(String[] args) {
		MochilaBinaria Funciones = new MochilaBinaria();
		Funciones.pedirDatos();
		Funciones.generarTabla();
		int optima_combinacion=Funciones.buscarCombinacionOptima();
		Funciones.mostrarResultado(optima_combinacion);
	}
	private void pedirDatos() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Cuantos objetos hay: ");
		objetos = sc.nextInt();
		beneficio = new int[objetos];
		peso = new int[objetos];
		for(int i=0;i<objetos;i++) {
			do {
				System.out.println("Ingresa el beneficio del objeto "+(i+1)+": " );
				beneficio[i] = sc.nextInt();
				if(beneficio[i]<0) {
					System.out.println("El beneficio no puede ser negativo");
				}
			}while(beneficio[i]<0);
		}
		for(int i=0;i<objetos;i++) {
			do {
				System.out.println("Ingresa el peso del objeto "+(i+1)+": " );
				peso[i] = sc.nextInt();
				if(peso[i]<=0) {
					System.out.println("El peso no puede ser negativo o 0");
				}
			}while(peso[i]<0);
		}
		System.out.println("Ingresa la capacidad maxima de la mochila: ");
		capacidad_maxima = sc.nextInt();
		cant_combinaciones=(int) Math.pow(2, objetos);
		combinaciones =  new int [cant_combinaciones][objetos];
		peso_min=capacidad_maxima+1;
		sc.close();
	}
	
	private void generarTabla() {
		boolean variable_Ayuda=false;
		int total=cant_combinaciones/2;
		int intercalado=cant_combinaciones/2;
		for(int i=0;i<objetos;i++) {
			for(int j=0;j<cant_combinaciones;j++) {
				if(variable_Ayuda==false) {
					combinaciones[j][i]=0;
					total--;
					if(total==0) {
						variable_Ayuda=true;
						total=intercalado;
					}
				}
				else {
					combinaciones[j][i]=1;
					total--;
					if(total==0) {
						variable_Ayuda=false;
						total=intercalado;
					}
				}
			}
			intercalado=intercalado/2;
			total=intercalado;
		}
	}
	
	private int buscarCombinacionOptima() {
		int beneficio_combinacion=0;
		int capacidad_combinacion=0;
		int combinacion_optima=0;
		for(int i=0;i<cant_combinaciones;i++) {
			for(int j=0;j<objetos;j++) {
				beneficio_combinacion=beneficio_combinacion+(combinaciones[i][j]*beneficio[j]);
				capacidad_combinacion=capacidad_combinacion+(combinaciones[i][j]*peso[j]);
			}
			if(capacidad_combinacion<=capacidad_maxima) {
				if(beneficio_combinacion>mayor_beneficio) {
						mayor_beneficio=beneficio_combinacion;
						combinacion_optima=i;
						peso_min=capacidad_combinacion;
				}
				else if(beneficio_combinacion==mayor_beneficio) 
				{
					if(peso_min>capacidad_combinacion) {
						mayor_beneficio=beneficio_combinacion;
						combinacion_optima=i;
						peso_min=capacidad_combinacion;
					}
				}
			}
			beneficio_combinacion=0;
			capacidad_combinacion=0;
		}
		return combinacion_optima;
	}
	
	private void mostrarResultado(int combinacion_optima) {
		System.out.print("La mejor combinacion es: ");
		for(int i=0;i<objetos;i++) {
			System.out.print(combinaciones[combinacion_optima][i]+ " ");
		}
		System.out.print("\nObjetos a llevar: ");
		for(int i=0;i<objetos;i++) {
			if(combinaciones[combinacion_optima][i]==1) 
			{
				System.out.print((i+1)+"  ");
			}
		}
		System.out.println("\nEl beneficio es: "+mayor_beneficio);
		System.out.println("El peso es: "+peso_min);
	}
}
