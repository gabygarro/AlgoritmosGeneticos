package clasesgeneticas;

public class IndividuoViajero extends Individuo {
    private int[] gen;
    private int fitness;
    private float fitnessAbsoluto;
    private float valorEnRuleta;
    
    public IndividuoViajero(int[] pGen, int[][] distancias) {
        this.gen = pGen;
        for (int i = 0; i < gen.length - 1; i++) {
            this.fitness += distancias[gen[i]-1][gen[i+1]-1];
        }
        this.fitness += distancias[gen[gen.length-1]-1][gen[0]-1];
        fitnessAbsoluto = ((float) 1 / (float) fitness) * 100;
    }
    
    public String toString() {
        String string = "[";
        for (int entero : gen) {
            string += entero;
            string += ", ";
        }
        string = string.substring(0,string.length()-2);
        string += "] fitness: ";
        string += this.fitness;
        return string;
    }
    
    public int getFitness() {
        return this.fitness;
    }
    
    public float getFitnessAbs() {
        return fitnessAbsoluto;
    }
    
    public float getValorEnRuleta() {
        return valorEnRuleta;
    }
    
    public void setValorEnRuleta(float valor) {
        valorEnRuleta = valor;
    }
    
    public int[] getGen() {
        return gen;
    }
    
    public void mutar(int alelo1, int alelo2) {
        int tmp = gen[alelo1];
        gen[alelo1] = gen[alelo2];
        gen[alelo2] = tmp;
    }
}
