package clasesalgoritmosgeneticos;

import clasesgeneticas.IndividuMochila;
import java.util.Random;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;


public class Mochila extends Genetico {
    public LinkedList<IndividuMochila> generacion;
          
//▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄Ordena la Generacion Una vez Creada▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄    
   
    public int ruedaDeLaFortuna(LinkedList<IndividuMochila> NewData){
        generacion=NewData;
        LinkedList<IndividuMochila> generacionCopia=NewData;
        
       
        int padreSeleccionado=GenerarPadreRuleta(generacionCopia);

        return padreSeleccionado;
        
    };
   
    public float SumaFitness(LinkedList<IndividuMochila> generacionCopia ,int Parada){
        float sumaFit=0;
        if (Parada==0){
            sumaFit=(float)sumaFit+generacion.get(0).getFitnesIndividuo();
            return sumaFit;
        }
        for (int i=0;i<Parada;i++){
            sumaFit=(float)sumaFit+(float)generacion.get(i).getFitnesIndividuo();
            
        }
        return sumaFit;
    }
    
    private int GenerarPadreRuleta(LinkedList<IndividuMochila> generacionCopia) {
        float FitnesMax=SumaFitness(generacionCopia,generacionCopia.size());
        Random rand = new Random(); 
        float RandomRuleta = (FitnesMax) * rand.nextFloat();

        for (int i=0;i<generacionCopia.size();i++){
            if (SumaFitness(generacionCopia,i+1)>=RandomRuleta){
                return i;
            }
        }
        return 0;
    }
    
    public int Azar(LinkedList<IndividuMochila> generacionCopia){
        Random rand = new Random(); 
        int RandomAzar = rand.nextInt(generacionCopia.size());
       
        return RandomAzar;
    }
    
    public int Torneo(LinkedList<IndividuMochila> generacionCopia){
        Random rand = new Random(); 
        int Cuenta = rand.nextInt(generacionCopia.size());
        
        int CantidadIndividuos = rand.nextInt(generacionCopia.size());
        
        int contador=0;
        float MejorFitness=0;
        float individuoSelec=0;
        for(int i=0;i<CantidadIndividuos;i++){
            if(Cuenta==generacionCopia.size()){
                Cuenta=0;
            }
            if (generacionCopia.get(Cuenta).getFitnesIndividuo()>=MejorFitness){
                MejorFitness=generacionCopia.get(Cuenta).getFitnesIndividuo();
                individuoSelec=Cuenta;
            }         
        }
       
        return Cuenta;
        
    }

  
}
