package clasesgeneticas;

import java.util.LinkedList;

public class Individuo {
    protected int fitness;
    

    public Individuo() {
        fitness = 0; //inicializa el fitness en 0
    }
    
    public String toString(){
        return "";
    }
    
    public int getFitness() {
        return fitness;
    }
}
