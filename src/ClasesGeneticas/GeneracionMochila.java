/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.  
 * 
 */
package clasesgeneticas;

import clasesalgoritmosgeneticos.Mochila;
import java.util.LinkedList;
import java.util.Random;

public class GeneracionMochila {
    //▄▄▄▄DATOS DE LA CREACION INDIVIDUO▄▄▄▄
    int cantidaPaquetesMax;
    float capacidadMax;
    float [][] ElementosMochila;
    int PoblacionCantIndiv;
    float SumaPesoMochila = 0;
    float SumaCostoMochila = 0;
    //▄▄▄▄DATOS DE LA GENERACION▄▄▄▄
    public int tamanoPoblacion;
    int cantidadGeneraciones;
    int algoritmoGenetico;
    int elitismo;
    float mutacion;
    boolean generacionYaCreada=false;
    //▄▄▄▄LISTA DE LA GENERACION▄▄▄▄
    public LinkedList<IndividuMochila> generacion;

public IndividuMochila get(int index) {
        return generacion.get(index);
    }
    
    
    
//▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄Constructor Mochila▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄  

    public GeneracionMochila(int tamanoPoblacion, int cantidadGeneraciones, int algoritmoGenetico, int elitismo, float mutacion) {
        this.tamanoPoblacion = tamanoPoblacion;
        this.cantidadGeneraciones = cantidadGeneraciones;
        this.algoritmoGenetico = algoritmoGenetico;
        this.elitismo = elitismo;
        this.mutacion = mutacion;
        this.generacion = new LinkedList<>();
    }
    
    
//▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄Generadores de Individuos de la Generacion▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄   
    public void GenerarIndividuosRandom(){
        for(int i=0;i<=tamanoPoblacion;i++){
            int[] NuevoIndividuo=IndividuoAzar();
            IndividuMochila newData=new IndividuMochila(NuevoIndividuo, ElementosMochila, calculaFitness(NuevoIndividuo), capacidadMax);
            generacion.add(newData);
        }
        ordenarPorFitness();
    }
    
    
    public float calculaFitness(int[] Genes){
        float Fitness=0;
        for(int i=0;i<Genes.length;i++){
            if(Genes[i]==1){
                Fitness=(float)Fitness+(float)ElementosMochila[i][1]/(float)ElementosMochila[i][0];
            }
        }
        if(Fitness>capacidadMax){
            Fitness=(float)0.1;
        }
        return Fitness;
    }
//▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄Genera Genes de un Individuo Random ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄

    public int[] IndividuoAzar(){
        float[][]ElementosCopia=new float[ElementosMochila.length][3];
        ElementosCopia=ElementosMochila;
        SumaPesoMochila = 0;
        SumaCostoMochila = 0;
        
        int []ListaSolucion=new int[ElementosMochila.length];
        for (int i=0;i<ElementosMochila.length-1;i++){
            ListaSolucion[i]=0;
        }
        

        while(ExisteElementoValido(ElementosCopia)){
            int padreSeleccionado=GenerarPadreAzar(ElementosCopia);
            SumaPesoMochila=SumaPesoMochila+(float)ElementosCopia[padreSeleccionado][0];
            SumaCostoMochila=SumaCostoMochila+(float)ElementosCopia[padreSeleccionado][0];
            
            
                for (int i=0;i<ElementosMochila.length;i++){
                    if((float)ElementosCopia[padreSeleccionado][0]==(float)ElementosMochila[i][0]&&(float)ElementosCopia[padreSeleccionado][1]==(float)ElementosMochila[i][1]){
                        if(ListaSolucion[i]==0){
                            ListaSolucion[i]=1;
                            break;
                        }
                    }
                }
             ElementosCopia=RecrearLista(ElementosCopia,padreSeleccionado);
        }
        
        return ListaSolucion;
    }

    private int GenerarPadreAzar(float[][] ElementosCopia) {
        boolean random=true;
        int contador =0;
        Random rand = new Random();
        int RandomAzar = rand.nextInt(ElementosCopia.length);
       
        while(random){
         
         if (ElementosCopia[RandomAzar][0]+SumaPesoMochila<=capacidadMax){
             break;
         }
         if (contador>=7){
             RandomAzar = getRandom(ElementosCopia);
             break;
         }
         if (contador<7){
             RandomAzar = rand.nextInt(ElementosCopia.length);
             contador=contador++;
         }
        
        }
        return RandomAzar;

    }    
   
    private int getRandom(float[][] ElementosCopia) {
            int random=0;
            for(int i=0;i<ElementosCopia.length;i++){
                if(ElementosCopia[i][0]+SumaPesoMochila<=capacidadMax){
                    random=i;
                    break;
                }
            }
            return random;
        }
        
    private float[][] RecrearLista(float[][] ElementosCopia, int padreSeleccionado) {
        float [][]NuevaLista;
        NuevaLista=new float[ElementosCopia.length-1][3];
        int NuevaListaContador=0;
        for (int i=0;i<ElementosCopia.length;i++){
            if (padreSeleccionado!=i){
                NuevaLista[NuevaListaContador][0]=ElementosCopia[i][0];
                NuevaLista[NuevaListaContador][1]=ElementosCopia[i][1];
                NuevaLista[NuevaListaContador][2]=ElementosCopia[i][2];                
                NuevaListaContador=NuevaListaContador+1;
                
            }
        }
        return NuevaLista;
    }

    private boolean ExisteElementoValido(float[][] ElementosCopia) {
        for (int i=0;i<ElementosCopia.length-1;i++){
            if (ElementosCopia[i][0]+SumaPesoMochila<=capacidadMax){
                return true;
            }
        }
        
        return false;
    
    }

//▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄Ordena la Generacion Una vez Creada▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄    

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
        float pivot = generacion.get(lowerIndex + (higherIndex-lowerIndex)/2).getFitnesIndividuo();
        //dividir en dos arrays
        while (i <= j) {
            /**
             * En cada iteracion, se identifica el numero del lado izquierdo
             * que es mayor que el valor del pivote, y tambien se identifica
             * un numero del lado derecho que es menor que el valor del
             * pivote. Una vez que la busqueda esta hecha, se intercambian
             * ambos numeros.
             */
            while (generacion.get(i).getFitnesIndividuo() < pivot) {
                i++;
            }
            while (generacion.get(j).getFitnesIndividuo() > pivot) {
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
        IndividuMochila temp = generacion.get(i);
        generacion.set(i, generacion.get(j));
        generacion.set(j, temp);
    }
    
//▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄Cargar Datos del archivo TXT▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄

    public void cargarDatos(StringBuffer pStringBuffer,boolean GeneracionTxT) {
        String[] lineas = pStringBuffer.toString().split("\n"); 
        capacidadMax = Float.parseFloat(lineas[0]);
        cantidaPaquetesMax = Integer.parseInt(lineas[1]);
        int posicionEnLineas = 2;
        ElementosMochila = new float[cantidaPaquetesMax][3];
        for (int i = 0; i < cantidaPaquetesMax; i++) {
            String[] lineaActual = lineas[posicionEnLineas].split(" ");
            ElementosMochila[i][0]=Float.parseFloat(lineaActual[0]);
            ElementosMochila[i][1]=Float.parseFloat(lineaActual[1]);
            ElementosMochila[i][2]=Float.parseFloat(lineaActual[1])/Float.parseFloat(lineaActual[0]);
            posicionEnLineas=posicionEnLineas+1;
        }
        if(GeneracionTxT){
        PoblacionCantIndiv=Integer.parseInt(lineas[posicionEnLineas]); 
        tamanoPoblacion=PoblacionCantIndiv;//▓▓▓SI LA GENERACION ES 0* GENERACION▓▓▓
        posicionEnLineas=posicionEnLineas+1;
        for (int i = 0; i < tamanoPoblacion; i++) {
            String[] lineaActual = lineas[posicionEnLineas].split(" ");
            int [] NuevoIndividuo=new int[cantidaPaquetesMax];
            
            
            for(int j=0;j<cantidaPaquetesMax;j++){
                NuevoIndividuo[j]=Integer.parseInt(lineaActual[j]);
            }
            IndividuMochila NewData= new IndividuMochila(NuevoIndividuo,ElementosMochila, calculaFitness(NuevoIndividuo), capacidadMax);
            generacion.add(NewData);
            posicionEnLineas=posicionEnLineas+1;
        }
        
        }  
    }
    
//▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄Metodos Sobre Escojencia de padre▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄ 

    public void Cruce(){
        boolean TamanoDos=false;
        boolean TamanoTres=false;
        
        if (generacion.size()<=1){
            return;
        }
        if (generacion.size()==2){
            
            TamanoDos=true;
        }
        if (generacion.size()==3){
            
            TamanoTres=true;
        }
        IndividuMochila[] ArrayHijos;
        ArrayHijos=new IndividuMochila[2];
        IndividuMochila Padre1;
        IndividuMochila Padre2;
            
        if (algoritmoGenetico==0){// AZAR**************************************************
            
            Padre1=PadreAlgoritmoAzar();
            Padre2=PadreAlgoritmoAzar();
            ArrayHijos= GenerarHijos(Padre1,Padre2);
            
            
            if(TamanoDos==true){
            generacion.add(ArrayHijos[0]);
            generacion.add(ArrayHijos[1]);
            return;
            }
            if(TamanoTres==true){
                generacion.add(ArrayHijos[0]);
                generacion.add(ArrayHijos[1]);
                generacion.add(Padre1);

            }
            ordenarPorFitness();
            generacion.remove(0);
            generacion.remove(1);
            generacion.add(ArrayHijos[0]);
            generacion.add(ArrayHijos[1]);
            generacion.add(Padre1);
            generacion.add(Padre2);
            return;
        }
        if (algoritmoGenetico==1){// RULETA**************************************************
            

            Padre1=PadreAlgoritmoRuleta();
            Padre2=PadreAlgoritmoRuleta();
            ArrayHijos= GenerarHijos(Padre1,Padre2);
            
            if(TamanoDos==true){
            generacion.add(ArrayHijos[0]);
            generacion.add(ArrayHijos[1]);
            return;
            }
            if(TamanoTres==true){
                generacion.add(ArrayHijos[0]);
                generacion.add(ArrayHijos[1]);
                generacion.add(Padre1);

            }
            ordenarPorFitness();
            generacion.remove(0);
            generacion.remove(1);
            generacion.add(ArrayHijos[0]);
            generacion.add(ArrayHijos[1]);
            generacion.add(Padre1);
            generacion.add(Padre2);
            return;
            
        }
        if (algoritmoGenetico==2){//TORNEO*************************************************
            
            Padre1=PadreAlgoritmoAzar();
            Padre2=PadreAlgoritmoAzar();
            ArrayHijos= GenerarHijos(Padre1,Padre2);
            

            
            if(TamanoDos==true){
            generacion.add(ArrayHijos[0]);
            generacion.add(ArrayHijos[1]);
            return;
            }
            if(TamanoTres==true){
                
                generacion.add(ArrayHijos[0]);
                generacion.add(ArrayHijos[1]);
                generacion.add(Padre1);
                generacion.remove(0);
                
                

                return;
            }
            
            ordenarPorFitness();
            generacion.remove(0);
            generacion.remove(0);
            generacion.add(ArrayHijos[0]);
            generacion.add(ArrayHijos[1]);
            generacion.add(Padre1);
            generacion.add(Padre2);
            
            
            return;
        }
        
        
    }
    
    public IndividuMochila PadreAlgoritmoRuleta(){
        Mochila DatosMochila=new Mochila();
        int padreSeleccionado=DatosMochila.ruedaDeLaFortuna(generacion);
        
        IndividuMochila Padre=generacion.get(padreSeleccionado);
        generacion.remove(padreSeleccionado);
        return Padre;

    }
    
    public IndividuMochila PadreAlgoritmoAzar(){     
         Mochila DatosMochila=new Mochila();
         int padreSeleccionado=DatosMochila.Azar(generacion);
         IndividuMochila Padre=generacion.get(padreSeleccionado);
         generacion.remove(padreSeleccionado);
         
         return Padre;
    }
    
    public IndividuMochila PadreAlgoritmoTorneo(){     
         Mochila DatosMochila=new Mochila();
         int padreSeleccionado=DatosMochila.Torneo(generacion);
         IndividuMochila Padre=generacion.get(padreSeleccionado);
         generacion.remove(padreSeleccionado);
         return Padre;
    }
    
    
    
//▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄Metodos Sobre Individuos▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄ 

    public IndividuMochila[] GenerarHijos(IndividuMochila Padre1, IndividuMochila Padre2) {
        int[] hijo1; 
        int[] hijo2; 
        hijo1 = new int[ElementosMochila.length];
        hijo2 = new int[ElementosMochila.length];
        
        
        
        int TamanoGenes=Padre1.GenesIndividuo.length;
        
        int[] Genespadre1=Padre1.GenesIndividuo;
        int[] Genespadre2=Padre2.GenesIndividuo;
        
        for (int i=0;i<TamanoGenes/2;i++){
            hijo1[i]=Genespadre1[i];
            hijo2[i]=Genespadre2[i];
        }
        
        for (int i=TamanoGenes/2;i<TamanoGenes;i++){
            hijo1[i]=Genespadre2[i];
            hijo2[i]=Genespadre1[i];
        }
        
        
        Random rand = new Random();
        int mutacionRand = rand.nextInt(101);
        
        if (mutacion>100){
            hijo1= mutar(hijo1);
            hijo2=mutar(hijo2);
        }
        if (mutacionRand<mutacion){
            hijo1= mutar(hijo1);
            hijo2=mutar(hijo2);
        }
        
        IndividuMochila[] ArrayHijos=new IndividuMochila[2];
        ArrayHijos[0]=new IndividuMochila(hijo1, ElementosMochila, calculaFitness(hijo1), capacidadMax);
        ArrayHijos[1]=new IndividuMochila(hijo2, ElementosMochila, calculaFitness(hijo2), capacidadMax);
        return ArrayHijos;
    }

    private int[] mutar(int[] hijo) {
        Random rand = new Random();
        int Randombitacamiar = rand.nextInt(hijo.length);
        if(hijo[Randombitacamiar]==0){
            hijo[Randombitacamiar]=1;
            return hijo;
        }
        if(hijo[Randombitacamiar]==1){
            hijo[Randombitacamiar]=0;
            return hijo;
        }
        return hijo;
    }


    
}
