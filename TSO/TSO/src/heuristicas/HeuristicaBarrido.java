package heuristicas;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HeuristicaBarrido {
    double[] coordenadaX;
    double[] coordenadaY;
    double[] demanda;
    double capacidad;
    int numGrupos;
    List<Integer> mejorCamino;

    public static void main(String[] args) {
        HeuristicaBarrido fun = new HeuristicaBarrido();
        fun.ingresarDatos();
        fun.calculosGrados();
        List<Double>[] grupos = fun.formarGrupos();
        fun.busquedaTabu(grupos);
    }

    private void ingresarDatos() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingresar la cantidad de nodos: ");
        int nodos = sc.nextInt();
        coordenadaX = new double[nodos];
        coordenadaY = new double[nodos];
        demanda = new double[nodos];
        for (int i = 0; i < nodos; i++) {
            System.out.println("Ingresa la coordenada X del nodo " + (i + 1) + ": ");
            coordenadaX[i] = sc.nextDouble();
            System.out.println("Ingresa la coordenada Y del nodo " + (i + 1) + ": ");
            coordenadaY[i] = sc.nextDouble();
            System.out.println("Ingresa la demanda del nodo " + (i + 1) + ": ");
            demanda[i] = sc.nextDouble();
        }
        System.out.println("Capacidad de cada grupo: ");
        capacidad = sc.nextDouble();
        sc.close();
    }

    private double[] calculosGrados() {
        double[] tanAngulo = new double[coordenadaX.length];
        for (int i = 0; i < tanAngulo.length; i++) {
            tanAngulo[i] = Math.atan2(coordenadaY[i], coordenadaX[i]);
            tanAngulo[i] = Math.toDegrees(tanAngulo[i]);
            if (tanAngulo[i] < 0) {
                tanAngulo[i] += 360;
            }
        }

        for (int i = 0; i < tanAngulo.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < tanAngulo.length; j++) {
                if (tanAngulo[j] < tanAngulo[minIndex]) {
                    minIndex = j;
                }
            }
            double temp = tanAngulo[minIndex];
            tanAngulo[minIndex] = tanAngulo[i];
            tanAngulo[i] = temp;

            double tempX = coordenadaX[minIndex];
            coordenadaX[minIndex] = coordenadaX[i];
            coordenadaX[i] = tempX;

            double tempY = coordenadaY[minIndex];
            coordenadaY[minIndex] = coordenadaY[i];
            coordenadaY[i] = tempY;
        }

        return tanAngulo;
    }

    private List<Double>[] formarGrupos() {
        double suma = 0;
        List<Integer> grupoIndices = new ArrayList<>();
        for (int i = 0; i < demanda.length; i++) {
            suma += demanda[i];
            if (suma > capacidad) {
                suma = 0;
                grupoIndices.add(i);
            }
        }

        numGrupos = grupoIndices.size() + 1;
        List<Double>[] grupos = new ArrayList[numGrupos];
        for (int i = 0; i < grupos.length; i++) {
            grupos[i] = new ArrayList<>();
        }
        int j = 0;
        for (int i = 0; i < demanda.length; i++) {
            if (grupoIndices.contains(i)) {
                j++;
            }
            grupos[j].add(demanda[i]);
        }
        for (int a = 0; a < grupos.length; a++) {
            System.out.println("Grupo " + (a + 1));
            for (Double elemento : grupos[a]) {
                System.out.println(elemento);
            }
        }
        return grupos;
    }

    private void busquedaTabu(List<Double>[] grupos) {
        System.out.println("Distancias y camino óptimo para cada grupo:");
        for (int a = 0; a < grupos.length; a++) {
            System.out.println("Grupo " + (a + 1));
            List<Double> grupoActual = grupos[a];
            int n = grupoActual.size();
            double[][] distancias = new double[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int nodoI = i;
                    int nodoJ = j;
                    distancias[i][j] = calcularDistancia(nodoI, nodoJ, grupoActual);
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(distancias[i][j] + "\t");
                }
                System.out.println();
            }

            mejorCamino = busquedaTabuFilasColumnas(distancias, caminoOptimo);
            System.out.println("Mejor camino encontrado:");
            for (Integer nodo : mejorCamino) {
                System.out.print(nodo + " ");
            }
            System.out.println();
        }
    }


    private double calcularDistancia(int demandaI, int demandaJ, List<Double> grupoActual) {
        double coordXI = coordenadaX[demandaI];
        double coordYI = coordenadaY[demandaI];
        double coordXJ = coordenadaX[demandaJ];
        double coordYJ = coordenadaY[demandaJ];

        double distancia = Math.sqrt(Math.pow(coordXJ - coordXI, 2) + Math.pow(coordYJ - coordYI, 2));
        DecimalFormat df = new DecimalFormat("#.###");  //para redondear a 3 decimales
        distancia = Double.parseDouble(df.format(distancia));
        return distancia;
    }

    private List<Integer> encontrarCaminoOptimo(double[][] distancias) {
        int n = distancias.length;
        List<Integer> caminoOptimo = new ArrayList<>();
        boolean[] visitado = new boolean[n];
        int nodoInicial = 0;
        caminoOptimo.add(nodoInicial);
        visitado[nodoInicial] = true;

        while (caminoOptimo.size() < n) {
            int siguienteNodo = -1;
            double menorDistancia = Double.MAX_VALUE;
            int nodoActual = caminoOptimo.get(caminoOptimo.size() - 1);

            for (int i = 0; i < n; i++) {
                if (!visitado[i] && distancias[nodoActual][i] > 0 && distancias[nodoActual][i] < menorDistancia) {
                    siguienteNodo = i;
                    menorDistancia = distancias[nodoActual][i];
                }
            }

            if (siguienteNodo != -1) {
                caminoOptimo.add(siguienteNodo);
                visitado[siguienteNodo] = true;
            }
        }

        return caminoOptimo;
    }

    private List<Integer> busquedaTabuFilasColumnas(double[][] distancias, List<Integer> caminoInicial) {
        int n = distancias.length;
        List<Integer> mejorCamino = new ArrayList<>(caminoInicial);
        double mejorCosto = calcularCostoCamino(distancias, mejorCamino);

        int iteracionesSinMejora = 0;
        int maxIteracionesSinMejora = 10;

        while (iteracionesSinMejora < maxIteracionesSinMejora) {
            List<Integer> nuevoCamino = new ArrayList<>(mejorCamino);
            double nuevoCosto = mejorCosto;
            for (int i = 1; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    List<Integer> vecino = intercambiarNodos(nuevoCamino, i, j);
                    double costoVecino = calcularCostoCamino(distancias, vecino);

                    if (costoVecino < nuevoCosto) {
                        nuevoCamino = vecino;
                        nuevoCosto = costoVecino;
                    }
                }
            }

            if (nuevoCosto < mejorCosto) {
                mejorCamino = nuevoCamino;
                mejorCosto = nuevoCosto;
                iteracionesSinMejora = 0;
            } else {
                iteracionesSinMejora++;
            }
        }

        return mejorCamino;
    }

    private List<Integer> intercambiarNodos(List<Integer> camino, int i, int j) {
        List<Integer> nuevoCamino = new ArrayList<>(camino);
        int temp = nuevoCamino.get(i);
        nuevoCamino.set(i, nuevoCamino.get(j));
        nuevoCamino.set(j, temp);
        return nuevoCamino;
    }



    private double calcularCostoCamino(double[][] distancias, List<Integer> camino) {
        double costo = 0.0;
        int n = camino.size();

        for (int i = 0; i < n - 1; i++) {
            int nodoActual = camino.get(i);
            int nodoSiguiente = camino.get(i + 1);
            costo += distancias[nodoActual][nodoSiguiente];
        }

        costo += distancias[camino.get(n - 1)][camino.get(0)];  

        return costo;
    }
}
