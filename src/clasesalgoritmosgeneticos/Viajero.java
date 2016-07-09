package clasesalgoritmosgeneticos;

import clasesgeneticas.Generacion;
import clasesgeneticas.IndividuoViajero;
import java.util.LinkedList;
import java.util.Random;

public class Viajero extends Genetico {
    private int cantDeCiudades;
    private int[][] distanciaEntreCiudades; //las cantidades pueden ser floats...
    private int[][] solucionesIniciales;
    private Generacion generacionActual;
    private Generacion generacionSiguiente;
    private String[] lineas; //string[] de los datos en el archivo
    private int indexEnLineas;
    
    public Viajero(StringBuffer pStringBuffer) {
        cargarDatos(pStringBuffer);
        generacionActual = new Generacion();
        generacionSiguiente = new Generacion();
    }
    
    public void cargarDatos(StringBuffer pStringBuffer) { 
        lineas = pStringBuffer.toString().split("\n"); //separa en array cuando hay "\n"
        indexEnLineas = 0; //posicion de lo que estoy analizando en array 'lineas'
        //cargar primera linea (cant de ciudades)
        cantDeCiudades = Integer.parseInt(lineas[indexEnLineas]); //envolver con try-catch
        //cargar matriz de ciudades
        indexEnLineas++;
        distanciaEntreCiudades = new int[cantDeCiudades][cantDeCiudades]; //declara el tam de la matriz
        for (int i = 0; i < cantDeCiudades-1; i++) {
            String[] lineaActual = lineas[indexEnLineas].split(" "); //para tener un array con cada valor
            int indiceEnLineaActual = 0; //el indice se ira corriendo con respecto al lugar en la matriz en el que tiene que llenar
            for (int j = indexEnLineas-1; j < cantDeCiudades; j++) {
                if (i == j) {
                    distanciaEntreCiudades[i][j] = 0;
                }
                else {
                    distanciaEntreCiudades[i][j] = Integer.parseInt(lineaActual[indiceEnLineaActual]); //envolver con try-catch
                    distanciaEntreCiudades[j][i] = distanciaEntreCiudades[i][j];
                    indiceEnLineaActual++;
                }
            }
            indexEnLineas++;
        }
        /*//chequear que la matriz ha sido cargada correctamente
        System.out.println("Matriz de distancias entre ciudades:");
        for (int[] fila : distanciaEntreCiudades) {
            for (int numero : fila) {
                System.out.print(numero);
                System.out.print(" ");
            }
            System.out.print("\n");
        }*/
        /*//imprimir los recorridos iniciales en generacionActual
        System.out.println("\nRecorridos iniciales:");
        System.out.println(generacionActual.toString());*/
    }
    
    public void cargarGeneracionesDesdeArchivo() {
        generacionActual = new Generacion();
        //cargar soluciones iniciales desde el archivo
        for (int i = indexEnLineas; i < lineas.length; i++){
            String[] solucionInicial = lineas[indexEnLineas].split(" ");
            int[] individuo = new int[solucionInicial.length];
            for (int j = 0; j < solucionInicial.length; j++) {
                individuo[j] = Integer.parseInt(solucionInicial[j]);
            }
            generacionActual.add(individuo, distanciaEntreCiudades);
            indexEnLineas++;
        }
    }
    
    public void generarGeneracionInicial(int pTamanoPoblacion) {
        LinkedList<Integer> ciudades = new LinkedList<>();
        
        for (int i = 0; i < pTamanoPoblacion; i++) {
            LinkedList<Integer> ciudadesTemplate = new LinkedList<>();
            for (int index = 1; index <= cantDeCiudades; index++) {
                ciudades.add(index);
            }
            Random rand = new Random();
            //ciudades = ciudadesTemplate;
            //hace el gen de un nuevo individuo
            int[] gen = new int[cantDeCiudades];
            //despues elegir un index aleatorio para tomar una ciudad
            for (int j = 0; j < cantDeCiudades; j++) {
                int randomNum = rand.nextInt(ciudades.size()); //numeros entre 0 (inclusivo) y long de 'ciudades' (exclusivo)
                //quita de ciudades el indice del numero aleatorio y pone ese valor en gen
                gen[j] = ciudades.remove(randomNum);
            }
            generacionActual.add(gen, distanciaEntreCiudades);
        }
    }
    
    public int[][] getDistanciaEntreCiudades() {
        return distanciaEntreCiudades;
    }
    
    public Generacion getGeneracionActual() {
        return generacionActual;
    }
    
    public void ordenarPorFitness() {
        generacionActual.ordenarPorFitness();
    }
    
    public IndividuoViajero ruleta() {
        if (generacionActual.len() == 1 || generacionActual.len() == 2) {
            return generacionActual.remove(0);
        }
        Random rand = new Random();
        int randomNum = rand.nextInt(101); //genera numero aleatorio entre 0 y 100
        //System.out.println(randomNum);
        float acumuladorPorcentaje = 0;
        generacionActual.calcularFitnessAbsTotal(); //calcula la suma del fitness absoluto de toda la generacion
        float fitnessAbsTotal = generacionActual.getFitnessAbsTotal(); // lo agarra
        float porcentajeAnterior = 0;
        int tamanoGeneracion = generacionActual.len();
        for (int i = 0; i < tamanoGeneracion; i++) {
        //for (IndividuoViajero individuo : generacionActual.getGeneracionActual()) {
            float fitnessAbsIndividuo = generacionActual.get(i).getFitnessAbs(); //agarra el fitness absoluto
            acumuladorPorcentaje += (fitnessAbsIndividuo / fitnessAbsTotal) * 100;
            
            /*System.out.print(fitnessAbsIndividuo + " ");
            System.out.print(fitnessAbsTotal + " ");
            System.out.println(acumuladorPorcentaje);*/
            
            generacionActual.get(i).setValorEnRuleta(acumuladorPorcentaje);
            if (porcentajeAnterior <= (float) randomNum && 
                   (float) randomNum <= acumuladorPorcentaje) {
                return generacionActual.remove(i);
            }
            else {
                porcentajeAnterior = acumuladorPorcentaje;
            }
        }
        //System.out.println("no funciona ruleta...");
        return generacionActual.remove(0); //asi para que no se caiga, pero ruleta realmente no esta funcionando
        //return null;
    }
    
    public IndividuoViajero azar() {
        Random rand = new Random();
        int randomNum = rand.nextInt(generacionActual.len()); //genera numero aleatorio entre 0 (inclusivo) y n (exclusivo)
        return generacionActual.remove(randomNum);
    }
    
    public IndividuoViajero torneo() {
        if (generacionActual.len() == 0) {
            System.out.println("Torneo: no hay individuos");
            return null;
        }
        else if (generacionActual.len() == 1 || generacionActual.len() == 2) {
            return generacionActual.remove(0);
        }
        Random rand = new Random();
        int randomNum = rand.nextInt(generacionActual.len() - 1) + 1; //tiene que agarrar entre uno o todos
        //System.out.print("Presion de seleccion: " + randomNum + " ");
        Generacion generacionTorneo = new Generacion();
        for (int i = 0; i < randomNum; i++) {
            int randomIndex = rand.nextInt(generacionActual.len()); //toma un indice aleatorio existente en generacionActual
            //int randomIndex = rand.nextInt(randomNum);
            generacionTorneo.addIndividuo(generacionActual.remove(randomIndex)); //los saca de generacionActual
        }
        generacionTorneo.ordenarPorFitness();
        int tamanoTorneo = generacionTorneo.len();
        for (int i = 1; i < tamanoTorneo; i++) {
            generacionActual.addIndividuo(generacionTorneo.remove(1)); //los vuelve a meter a generacionActual
            //lo hice asi, no se como funciona, pero tiene que ver con que el tamano del torneo cambia en cada iteracion
        }
        //generacionActual.ordenarPorFitness(); //los vuelve a ordenar, no se si es necesario, pero hacerlo no hace dano
        return generacionTorneo.remove(0);//podria no escoger el primero, sino escoger a incluso a este individuo por ruleta... (probabilidad)
    }
    
    public void cruce(int[] padre, int[] madre) throws NullPointerException {
        if (padre == null || madre == null) {
            System.out.println("Cruce: padre o madre nulos");
            throw new NullPointerException();
        }
        Random rand = new Random();
        //cruce sobre el padre
        int indiceCruce = rand.nextInt(padre.length - 2) + 1; //para que no sea ni 0 ni el ultimo indice
        //System.out.print("Punto de cruce: " + indiceCruce + " ");
        int[] hijo1 = padre.clone(); //le pasa una copia de sus elementos, no la referencia a si mismo
        for (int i = 0; i < indiceCruce; i++) {
            //metodo de reemplazar
            for(int j = 0; j < hijo1.length; j++) {
                if (hijo1[j] == madre[i]) {
                    int tmp = hijo1[i];
                    hijo1[i] = hijo1[j];
                    hijo1[j] = tmp;
                    break;
                }
            }
        }
        /*System.out.print("\nPrimer hijo:\n[");
        for (int alelo : hijo1) {
            System.out.print(alelo + ", ");
        }
        System.out.println("]");*/

        //cruce sobre la madre
        int[] hijo2 = madre.clone();
        for (int i = 0; i < indiceCruce; i++){
            //metodo de reemplazar
            for(int j = 0; j < hijo2.length; j++) {
                if (hijo2[j] == padre[i]) {
                    int tmp = hijo2[i];
                    hijo2[i] = hijo2[j];
                    hijo2[j] = tmp;
                    break;
                }
            }
        }
        /*System.out.print("\nSegundo hijo:\n[");
        for (int alelo : hijo2) {
            System.out.print(alelo + ", ");
        }
        System.out.println("]");*/
        
        generacionSiguiente.add(hijo1, distanciaEntreCiudades);
        generacionSiguiente.add(hijo2, distanciaEntreCiudades);
    }
    
    public void nuevaGeneracion() {
        generacionActual.clear(); //por si acaso  
        generacionActual.copyFrom(generacionSiguiente.getGeneracionActual());//copia cada elemento de la generacionSiguiente
        generacionSiguiente.clear();
    }
    
    public void mutar(int porcentaje) {
        //System.out.println(generacionActual.len());
        int cantPorMutar = (int) ((float) generacionActual.len() * ((float) porcentaje/ 100.0));
        Random rand = new Random();
        //System.out.println(cantPorMutar);
        for (int i = 0; i < cantPorMutar; i++) {
            int individuoPorMutar = rand.nextInt(generacionActual.len()); //sacar el indice del individuo afortunado
            int alelo1 = rand.nextInt(cantDeCiudades); //primer indice para intercambiar
            int alelo2 = rand.nextInt(cantDeCiudades); //segundo indice para intercambiar
            while (alelo1 == alelo2) { //chequear si no son el mismo
                alelo2 = rand.nextInt(generacionActual.len()); //volver a computar el segundo si es el caso
            }
            
        }
    }
    
    public String generacionAString() {
        String string = "";
        for (IndividuoViajero individuo : generacionActual.getGeneracionActual()) {
            for(int ciudad : individuo.getGen()) {
                string += ciudad;
                string += " ";
            }
            string = string.substring(0,string.length()-1); //le quita a cada gen el ultimo espacio
            string += "\n";
        }
        string = string.substring(0,string.length()-1); //le quita el ultimo salto de linea
        return string;
    }
}