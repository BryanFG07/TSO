package combinaciones;

import java.util.Scanner;

public class MochilaMultiUnidades {
	int cantidad_objetos;
	int[] peso;
	int[] beneficio;
	double [] resultado;
	int capacidad;
	double [] BP;
	int lugar_beneficio;
	public static void main(String []args) {
		MochilaMultiUnidades fun = new MochilaMultiUnidades();
		fun.PedirDatos();
		fun.calcularMejorOpcion();
		fun.espacioPrograma();
		
	}
	
	private void PedirDatos() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese la cantidad de objetos: ");
		cantidad_objetos = sc.nextInt();
		peso = new int[cantidad_objetos];
		beneficio = new int[cantidad_objetos];
		resultado = new double[cantidad_objetos];
		BP = new double[cantidad_objetos];
		for(int i=0; i<cantidad_objetos;i++) {
			System.out.println("Peso del objeto "+ (i+1)+": ");
			peso[i] = sc.nextInt();
			System.out.println("Beneficio del objeto "+ (i+1)+": ");
			beneficio[i] = sc.nextInt();
		}
		System.out.println("Ingresar capacidad de mochila: ");
		capacidad = sc.nextInt();
		sc.close();
	}
	private void calcularMejorOpcion() {
		double mayor_beneficio = 0;
		System.out.println("\nObjeto\tPeso\tBeneficio\t(C/B)+1\tB/P");
		for(int i=0; i<resultado.length; i++) {
			resultado[i] = capacidad/peso[i];
			resultado[i] = (int)Math.round(resultado[i] * 100.0) / 100.0;
			resultado[i]++;
			BP[i] = (double)beneficio[i]/peso[i]; 
			if(BP[i]>mayor_beneficio) {
				mayor_beneficio = BP[i];
				lugar_beneficio = i;
			}
			System.out.println((i+1)+"\t"+peso[i]+"\t"+beneficio[i]+"\t\t"+resultado[i]+"\t"+BP[i]);
		}
		System.out.println("Mejor relacion Beneficio/Peso: "+mayor_beneficio);
	}
	private void espacioPrograma() {
		int[] pes = new int[cantidad_objetos];
		int []bene = new int[cantidad_objetos];
		int []cant_actual = new int[cantidad_objetos];
		System.out.println("\nTamano del espacio");
		System.out.println("\t\tObjetos");
		for(int i=0;i<beneficio.length;i++) {
			System.out.print("  "+(i+1));
		}
		System.out.println(" Peso Beneficio");
		for(int i=0; i<beneficio.length;i++) {
			
			if(i==0) {
				cant_actual[lugar_beneficio]=((int) resultado[lugar_beneficio])-1;
			}else {
				cant_actual[lugar_beneficio]=((int) resultado[lugar_beneficio])-2;
				cant_actual[i]=1;
			}
			for(int j=0;j<beneficio.length;j++) {
				if(j!= i && j!=lugar_beneficio) {
					cant_actual[j]=0; 
				}
				System.out.print(cant_actual[j]+"  ");
			}
			pes[i]=0;
			bene[i]=0;
			for(int y=0;y<beneficio.length;y++) {
				pes[i]+= cant_actual[y]*peso[y];
				bene[i]+= cant_actual[y]*beneficio[y];
			}
			System.out.print(pes[i]+"\t");
			System.out.print(bene[i]);
			if(pes[i]>capacidad) {
				System.out.print("\t //Sobrepasa la capacidad de mochila");
				
			}
			System.out.println();
			
		}
	}
}
