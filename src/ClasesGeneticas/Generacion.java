package clasesgeneticas;

import java.util.LinkedList;
import java.util.Random;

public class Generacion {
    private LinkedList<IndividuoViajero> generacion;
    //int[][] distanciaEntreCiudades;
    private int fitnessTotal;
    private float fitnessAbsolutoTotal;
    
    public Generacion() {
        this.generacion = new LinkedList<>();
        //this.distanciaEntreCiudades = pDistanciaEntreCiudades;
    }
    
    //recibe los datos de un Individuo, lo crea y lo agrega
    public void add(int[] pIndividuo, int[][] pDistanciaEntreCiudades) {
        IndividuoViajero individuo = new IndividuoViajero(pIndividuo, pDistanciaEntreCiudades);
        generacion.add(individuo);
    }
    
    //recibe a un individuo como objeto y lo agrega
    public void addIndividuo(IndividuoViajero pIndividuo) {
        generacion.add(pIndividuo);
    }
    
    public String toString() {
        String string = "[";
        for (IndividuoViajero individuo : this.generacion) {
            string += individuo.toString();
            string += ", ";
        }
        string = string.substring(0,string.length()-2);
        string += "]";
        return string;
    }
    //por ahora voy a usar un algoritmo de ordenamiento bien mierda
    //si despues esto afecta la rapidez de la progra, lo cambio por alguno recursivo
    //es la hora de cambiarlo por uno recursivo ¬¬ .l.
    public void ordenarPorFitnessFeo() {
        for (int i = generacion.size()-1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (generacion.get(j).getFitness() > generacion.get(j+1).getFitness()) {
                    IndividuoViajero individuoAux = generacion.get(j);
                    generacion.set(j, generacion.get(j+1));
                    generacion.set(j+1, individuoAux);
                }
            }
        }            
    }
    
    public void ordenarPorFitness() {
        if (generacion == null || generacion.size() == 0) {
            return;
        }
        quickSort(0, generacion.size() - 1);
    }
    
    private void quickSort(int lowerIndex, int higherIndex) {
        int i = lowerIndex;
        int j = higherIndex;
        //calcular numero de pivote, en este caso es el del medio
        int pivot = generacion.get(lowerIndex + (higherIndex-lowerIndex)/2).getFitness();
        //dividir en dos arrays
        while (i <= j) {
            /**
             * En cada iteracion, se identifica el numero del lado izquierdo
             * que es mayor que el valor del pivote, y tambien se identifica
             * un numero del lado derecho que es menor que el valor del
             * pivote. Una vez que la busqueda esta hecha, se intercambian
             * ambos numeros.
             */
            while (generacion.get(i).getFitness() < pivot) {
                i++;
            }
            while (generacion.get(j).getFitness() > pivot) {
                j--;
            }
            if (i <= j) {
                exchangeElements(i, j);
                //mover el index a la siguiente posicion en ambos extremos
                i++;
                j--;
            }
        }
        //llamar a quickSort() recursivamente
        if (lowerIndex < j) {
            quickSort(lowerIndex, j);
        }
        if (i < higherIndex) {
            quickSort(i, higherIndex);
        }
    }
    
    private void exchangeElements(int i, int j) {
        IndividuoViajero temp = generacion.get(i);
        generacion.set(i, generacion.get(j));
        generacion.set(j, temp);
    }
    
    public void calcularFitnessTotal() {
        fitnessTotal = 0;
        for (IndividuoViajero individuo : generacion) {
            fitnessTotal += individuo.getFitness();
        }
    }
    
    public void calcularFitnessAbsTotal() {
        fitnessAbsolutoTotal = 0;
        for (IndividuoViajero individuo : generacion) {
            fitnessAbsolutoTotal += individuo.getFitnessAbs();
        }
    }
    
    public IndividuoViajero get(int index) {
        return generacion.get(index);
    }
    
    public int len() {
        return generacion.size();
    }
    
    public int getFitnessTotal(){
        return fitnessTotal;
    }
    
    public float getFitnessAbsTotal() {
        return fitnessAbsolutoTotal;
    }
    
    public void setGeneracionActual(LinkedList<IndividuoViajero> pGeneracion) {
        generacion = pGeneracion;
    }
    
    public LinkedList<IndividuoViajero> getGeneracionActual() {
        return generacion;
    }
    
    public IndividuoViajero remove(int index) {
        return generacion.remove(index);
    }
    
    public void clear() {
        generacion.clear();
    }
    
    public void copyFrom(LinkedList<IndividuoViajero> pGeneracion) {
        for (IndividuoViajero individuo : pGeneracion) {
            generacion.add(individuo);
        }
    }
    
    public void mutar(int indiceIndividuo, int alelo1, int alelo2) {
        generacion.get(indiceIndividuo).mutar(alelo1, alelo2);
    }
}
