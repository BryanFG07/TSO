package combinaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlgoritmoEdmonds {
    int[][] matrizAdyacencia;
    int[][] matrizPesos;
    int nodo_origen;
    int nodo_destino;
    int arcoMenor;
    List<Integer> caminoMayor;
    int arcoMayor;

    public static void main(String[] args) {
        AlgoritmoEdmonds fun = new AlgoritmoEdmonds();
        boolean valido = fun.ingresarDatos();
        if (valido) {
            List<List<Integer>> caminos = fun.encontrarCaminos();
            System.out.println("Caminos encontrados desde el origen al destino");
            for (List<Integer> camino : caminos) {
                System.out.print("Camino: ");
                for (int i = 0; i < camino.size(); i++) {
                    System.out.print(camino.get(i));
                    if (i < camino.size() - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println();
                System.out.print("Valores de los arcos: ");
                int menor= Integer.MAX_VALUE;
                for (int i = 0; i < camino.size() - 1; i++) {
                	
                    int origen = camino.get(i) - 1;
                    int destino = camino.get(i + 1) - 1;
                    int peso = fun.matrizPesos[origen][destino];
                    System.out.print(peso + " - ");
                    if(menor>peso) {
                    	menor=peso;
                    }
                }
                System.out.println();
                System.out.println("Min: " + menor);
                System.out.println();
            }
            System.out.println("-----------------------------------------------------------");
            System.out.println("Respuesta tiene un flujo minimo mayor");
            if (fun.caminoMayor != null) {
                System.out.print("Camino: ");
                for (int i = 0; i < fun.caminoMayor.size(); i++) {
                    System.out.print(fun.caminoMayor.get(i));
                    if (i < fun.caminoMayor.size() - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println();
                System.out.println("Valor del arco mayor: " + fun.arcoMayor);
            } else {
                System.out.println("No se encontró ningún camino.");
            }
        }
    }

    private boolean ingresarDatos() {
        boolean comparacion = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("Cantidad de nodos del grafo: ");
        int tamano = sc.nextInt();
        matrizAdyacencia = new int[tamano][tamano];
        matrizPesos = new int[tamano][tamano];
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                do {
                    System.out.println("El nodo " + (i + 1) + " tiene conexion con el nodo " + (j + 1) + " (1=si/0=no): ");
                    matrizAdyacencia[i][j] = sc.nextInt();
                    if (matrizAdyacencia[i][j] != 0 && matrizAdyacencia[i][j] != 1) {
                        System.out.println("El valor solo puede ser 0 o 1 ");
                    }
                } while (matrizAdyacencia[i][j] != 0 && matrizAdyacencia[i][j] != 1);
            }
        }
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                if (matrizAdyacencia[i][j] == 1 && matrizAdyacencia[j][i] == 1) {
                    comparacion = false;
                }
            }
        }
        if (comparacion) {
            for (int i = 0; i < tamano; i++) {
                for (int j = 0; j < tamano; j++) {
                    if (matrizAdyacencia[i][j] == 1) {
                        do {
                            System.out.println("Valor del arco del nodo " + (i + 1) + " al " + (j + 1) + ": ");
                            matrizPesos[i][j] = sc.nextInt();
                            if (matrizPesos[i][j] <= 0) {
                                System.out.println("El valor del arco no puede ser menor a 0 o 0");
                            }
                        } while (matrizPesos[i][j] <= 0);
                    } else {
                        matrizPesos[i][j] = 0;
                    }
                }
            }
            System.out.println("Cual es el nodo origen: ");
            nodo_origen = sc.nextInt();
            System.out.println("Cual es el nodo destino: ");
            nodo_destino = sc.nextInt();
        } else {
            System.out.println("El grafo no puede ser bidimensional");
        }

        sc.close();
        return comparacion;
    }

    private List<List<Integer>> encontrarCaminos() {
        arcoMenor = Integer.MAX_VALUE;
        caminoMayor = null;
        List<List<Integer>> caminos = new ArrayList<>();
        List<Integer> caminoActual = new ArrayList<>();
        boolean[] visitado = new boolean[matrizAdyacencia.length];
        encontrarCaminos(nodo_origen - 1, nodo_destino - 1, visitado, caminoActual, caminos);
        return caminos;
    }

    private void encontrarCaminos(int nodo, int destino, boolean[] visitado, List<Integer> caminoActual,
                                 List<List<Integer>> caminos) {
        visitado[nodo] = true;
        caminoActual.add(nodo + 1);

        if (nodo == destino) {
            caminos.add(new ArrayList<>(caminoActual));
            int arcoActual = calcularArcoMenor(caminoActual);
            if (caminoMayor == null || arcoActual > arcoMayor
                    || (arcoActual == arcoMayor && caminoActual.size() < caminoMayor.size())) {
                caminoMayor = new ArrayList<>(caminoActual);
                arcoMayor = arcoActual;
            }
        } else {
            for (int i = 0; i < matrizAdyacencia.length; i++) {
                if (matrizAdyacencia[nodo][i] == 1 && !visitado[i]) {
                    int peso = matrizPesos[nodo][i];
                    if (peso < arcoMenor) {
                        arcoMenor = peso;
                    }
                    encontrarCaminos(i, destino, visitado, caminoActual, caminos);
                }
            }
        }

        visitado[nodo] = false;
        caminoActual.remove(caminoActual.size() - 1);
    }

    private int calcularArcoMenor(List<Integer> camino) {
        int arcoMenor = Integer.MAX_VALUE;
        for (int i = 0; i < camino.size() - 1; i++) {
            int origen = camino.get(i) - 1;
            int destino = camino.get(i + 1) - 1;
            int peso = matrizPesos[origen][destino];
            if (peso < arcoMenor) {
                arcoMenor = peso;
            }
        }
        return arcoMenor;
    }
}

