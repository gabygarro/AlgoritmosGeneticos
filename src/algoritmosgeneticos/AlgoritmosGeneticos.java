package algoritmosgeneticos;
 
import clasesalgoritmosgeneticos.Mochila;//deberia de importar un paquete completo y no la clase
import clasesalgoritmosgeneticos.Viajero;
import clasesgeneticas.GeneracionMochila;
import clasesgeneticas.IndividuoViajero;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class AlgoritmosGeneticos {
    public static String genetico;
    public static String entradaTxt;//nombre del archivo de datos de entrada
    public static int generaciones;
    public static int tamanoPoblacion;
    public static int mutacion;
    public static int algoritmoGenetico; //0 = simple al azar, 1 = rueda de la fortuna, 2 torneo
    public static int elitismo; //un numero que si es mayor a cero significa la cantidad de individuos de la poblaci´on, empezando por los m´as aptos, a los cuales se les garantiza sobrevivir a la siguiente generaci´on.
    public static String salidaTxt; //nombre del archivo de datos de salida
    public static int errorVerificar=0;
    public static String DatosAescribir;
    
    public static void main(String[] args) throws IOException {
        Verificar();
        
        if (errorVerificar==1){
           System.out.println ("Error detectado se termino el programa");
           return;
        }
        else{
            if (genetico.equals("Mochila")){
                StringBuffer datosDeArchivo2 = cargarDesdeArchivo(entradaTxt);
                GeneracionMochila GeneracionNueva = new GeneracionMochila(tamanoPoblacion, generaciones, algoritmoGenetico, elitismo, mutacion);
                GeneracionNueva.cargarDatos(datosDeArchivo2, false);
                GeneracionNueva.GenerarIndividuosRandom();
                for(int i=0;i<=generaciones;i++){
                    GeneracionNueva.Cruce();
                
                GeneracionNueva.ordenarPorFitness();
                    System.out.println("");
                System.out.println("\n*********************************************************\n");
                System.out.print("ELEMENTOS DE LA MOCHILA: ");
                GeneracionNueva.get(0).imprimirGenes();
                System.out.println("");
                System.out.print("Peor Individuo: ");
                System.out.print("Peso Total: "+GeneracionNueva.generacion.get(0).getpeso()+"  ");
                System.out.print("Costo Total: "+GeneracionNueva.generacion.get(0).getcost()+"  ");
                GeneracionNueva.generacion.get(0).imprimirIndividuo();
                System.out.println("");
                System.out.print("Mejor Individuo: ");
                System.out.print("Peso Total: "+GeneracionNueva.generacion.get(GeneracionNueva.tamanoPoblacion-1).getpeso()+"  ");
                System.out.print("Costo Total: "+GeneracionNueva.generacion.get(GeneracionNueva.tamanoPoblacion-1).getcost()+"  ");
                GeneracionNueva.generacion.get(GeneracionNueva.tamanoPoblacion-1).imprimirIndividuo();
                }
                System.out.println("");
                
                DatosAescribir=cargarDatos(datosDeArchivo2).toString();
                DatosAescribir+="\n*********************************************************\n";
                DatosAescribir+="ELEMENTOS DE LA MOCHILA: ";
                DatosAescribir+=GeneracionNueva.get(0).genestostring()+"\n";
                DatosAescribir+="Peor Individuo: ";
                DatosAescribir+="Peso Total: "+GeneracionNueva.generacion.get(0).getpeso()+"  ";
                DatosAescribir+="Costo Total: "+GeneracionNueva.generacion.get(0).getcost()+"  \n";
                DatosAescribir+="Genes: "+GeneracionNueva.generacion.get(0).individuotostring()+"\n";
                
                DatosAescribir+=GeneracionNueva.get(0).genestostring()+"\n";
                DatosAescribir+="Mejor Individuo: ";
                DatosAescribir+="Peso Total: "+GeneracionNueva.generacion.get(GeneracionNueva.tamanoPoblacion-1).getpeso()+"  ";
                DatosAescribir+="Costo Total: "+GeneracionNueva.generacion.get(GeneracionNueva.tamanoPoblacion-1).getcost()+"  \n";
                DatosAescribir+="Genes: "+GeneracionNueva.generacion.get(GeneracionNueva.tamanoPoblacion-1).individuotostring();
                
                EscribirArchivo(args, DatosAescribir, salidaTxt);
            }
            
            if (genetico.equals("Viajero")){
                //carga desde archivo e instanciacion de problema
                StringBuffer datosDeArchivo = cargarDesdeArchivo(entradaTxt); //cambiar capibara por archivoDeEntrada
                Viajero problemaViajero = new Viajero(datosDeArchivo);
                
                //chequear que la matriz ha sido cargada correctamente
                System.out.println("Matriz de distancias entre ciudades:");
                for (int[] fila : problemaViajero.getDistanciaEntreCiudades()) {
                    for (int distancia : fila) {
                        System.out.print(distancia + " ");
                    }
                    System.out.print("\n");
                }
                
                int numeroGeneracion = 1;
                
                if (tamanoPoblacion == 0) {
                    problemaViajero.cargarGeneracionesDesdeArchivo();
                }
                else {
                    problemaViajero.generarGeneracionInicial(tamanoPoblacion);
                }
                
                while (numeroGeneracion <= generaciones) { //repetir por la cant de generaciones
                    //imprimir los recorridos iniciales en generacionActual
                    System.out.println("\nGeneracion numero " + numeroGeneracion);
                    System.out.println(problemaViajero.getGeneracionActual().toString());

                    //imprimir los cosos ordenados por fitness
                    problemaViajero.ordenarPorFitness();
                    //System.out.println(problemaViajero.getGeneracionActual().toString());
                    System.out.print("Mejor fitness: " + problemaViajero.getGeneracionActual().get(0).getFitness());
                    System.out.println(" Peor fitness: " + problemaViajero.getGeneracionActual().get(tamanoPoblacion-1).getFitness());

                    while (problemaViajero.getGeneracionActual().len() > 0) { //mientras no este vacia la generacionActual
                        //System.out.println(tamanoPoblacion);
                        IndividuoViajero padre = null;
                        IndividuoViajero madre = null;
                        if (algoritmoGenetico == 0) {
                            padre = problemaViajero.azar();
                            madre = problemaViajero.azar();
                        }
                        if (algoritmoGenetico == 1) {
                            padre = problemaViajero.ruleta();
                            madre = problemaViajero.ruleta();
                        }
                        if (algoritmoGenetico == 2) {
                            padre = problemaViajero.torneo();
                            madre = problemaViajero.torneo();
                        }
                        //System.out.println(padre.toString());
                        try {
                            problemaViajero.cruce(padre.getGen(), madre.getGen());
                        }
                        catch (NullPointerException e) {
                            //e.printStackTrace();
                        }
                        catch (Exception e) {
                            System.out.println("Se cayo por algo que no es NullPointerException");
                            e.printStackTrace();
                        }
                    }
                    problemaViajero.nuevaGeneracion(); //copia elementos de generacionSiguiente a generacionActual
                    problemaViajero.mutar(mutacion);
                    numeroGeneracion++;
                    System.out.println("");
                }
                String stringParaImprimir = "";
                String[] lineas = datosDeArchivo.toString().split("\n"); //lo parte en el arreglo
                int cantCiudades = Integer.parseInt(lineas[0]); //saca la cant de ciudades, cant de lineas por leer y cargar
                int i = 0;
                for (String linea : lineas) {
                    if (cantCiudades == i) break;
                    stringParaImprimir += linea;
                    stringParaImprimir += "\n";
                    i++;
                }
                stringParaImprimir += problemaViajero.generacionAString();
                //System.out.println(stringParaImprimir);
                 EscribirArchivo(args, stringParaImprimir, salidaTxt);
                
            }
        }
    }

    public static void Verificar() {
        String palabraActual = " "; 
        //COMIENZA EL ALGORITMO
        System.out.println("--------------------------EJEMPLO-----------------------------------------------------------------------------------------------------------");
        System.out.println ("Orden de los datos: Mochila-Viajero ArchivoEntrada Generaciones TamañoPoblacion PorcentajeMutacion (0:Azar 1:Ruleta 2:Torneo) elitismo ArchivoSalida"); 
        System.out.println ("Cada dato debe ser separado por un espacio en el orden dicho");
        System.out.println ("»»Ejemplo: Mochila entradaMochila.txt 100 1000 3 2 0 output.txt");
        System.out.println ("»»Ejemplo: Viajero capibara.txt 100 100 5 2 0 output.txt");
        
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println ("Inserte el comando para iniciar la Aplicacion:");   
        Scanner in = new Scanner(System.in);

        String nombre = in.nextLine();
        char espacio=' ';
        int contadorEstado =0;
        for(int i = 0; i < nombre.length(); i++) {
            //System.out.println(nombre.charAt(i));
            if(nombre.charAt(i)==' '){
                if(contadorEstado==0){
                    System.out.println("Inicio el problema :"+palabraActual);
                    if (palabraActual.equals(" Viajero")){
                        genetico="Viajero";
                    }
                    if (palabraActual.equals(" Mochila")){
                        genetico="Mochila";
                    }
                    if (palabraActual.equals("Viajero") && palabraActual.equals("Mochila")){
                        System.out.println("Error en el formato: en el primer estado porfavor inserte la palabra Mochila o Viajero");
                        errorVerificar=1;
                        break;
                    }
                    palabraActual="";
                }
                if(contadorEstado==1){
                     System.out.println("Archivo de Entrada:"+palabraActual);
                     entradaTxt = palabraActual;
                     palabraActual="";
                 }
                if(contadorEstado==2){
                     System.out.println("Generaciones: "+palabraActual);
                     if (isInteger(palabraActual)){
                         generaciones=Integer.parseInt(palabraActual);
                         palabraActual="";
                     }
                     else{
                         System.out.println("------------------------------------------------------------------------");
                         System.out.println("Error: la cantidad de generaciones indicada no es un numero");
                         errorVerificar=1;
                         break;
                     }
                 }
                if(contadorEstado==3){
                     System.out.println("Tamaño de poblacion: "+palabraActual);
                     if (isInteger(palabraActual)){
                         tamanoPoblacion=Integer.parseInt(palabraActual);
                         palabraActual="";
                     }
                     else{
                         System.out.println("------------------------------------------------------------------------");
                         System.out.println("Error: el tamaño de la poblacion no es un numero");
                         errorVerificar=1;
                         break;
                     }
                 }
                if(contadorEstado==4){
                     System.out.println("Porcentaje de Mutacion: "+palabraActual);
                     if (isInteger(palabraActual)){
                         mutacion = Integer.parseInt(palabraActual);
                         palabraActual="";
                     }
                     else{
                         System.out.println("------------------------------------------------------------------------");
                         System.out.println("Error: el porcentaje de mutacion indicada no es un numero");
                         errorVerificar=1;
                         break;
                     }
                 }
                if(contadorEstado==5){
                     System.out.println("Codigo del algoritmo: "+palabraActual);
                     if (isInteger(palabraActual)){
                         algoritmoGenetico=Integer.parseInt(palabraActual);
                         palabraActual="";
                     }
                     else{
                         System.out.println("------------------------------------------------------------------------");
                         System.out.println("Error: el codigo del algoritmo genetico indicado no es un numero");
                         errorVerificar=1;
                         break;
                     }
                 }
                if(contadorEstado==6){
                     System.out.println("Elitismo: "+palabraActual);
                     if (isInteger(palabraActual)){
                         elitismo=Integer.parseInt(palabraActual);
                         palabraActual="";
                     }
                     else{
                         System.out.println("------------------------------------------------------------------------");
                         System.out.println("Error: el elitismo indicado no es un numero");
                         errorVerificar=1;
                         break;
                     }
                 }
                contadorEstado=contadorEstado+1;
            }
            else{
                if(!(nombre.charAt(i)==' ')){
                    palabraActual=palabraActual+Character.toString(nombre.charAt(i));
                }
            }
        }
        if(contadorEstado==7){
            System.out.println("Archivo de salida: "+palabraActual);
            salidaTxt=palabraActual;
            palabraActual="";
        }
        if(contadorEstado!=7){
            System.out.println("******Insuficientes datos, Imposible correr el programa******");
            errorVerificar=1;
        }
    }

   public static boolean isInteger(String data) {
      try {
       Integer.parseInt( data );
       return true;
       }
      catch( Exception e ) {
       return false;
       }
    }
    
    public static StringBuffer cargarDesdeArchivo(String pArchivoDeEntrada) throws FileNotFoundException, IOException{
        //archivoDeEntrada = pArchivoDeEntrada;
        try {
            File file = new File (pArchivoDeEntrada);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                stringBuffer.append(linea);
                stringBuffer.append("\n");
            }
            fileReader.close();
            System.out.println("\nContenido del archivo:");
            System.out.println(stringBuffer.toString());
            return stringBuffer;
        }
        catch (IOException e) {
            System.out.println("Archivo no encontrado. Stack trace a continuación:");
            e.printStackTrace();
        }
        return null;
    }
    
        public static StringBuffer cargarDatos(StringBuffer pStringBuffer) {
            StringBuffer sbf = new StringBuffer();
            String[] lineas = pStringBuffer.toString().split("\n"); 
            sbf.append(lineas[0]);
            sbf.append('\n');
            sbf.append(lineas[1]);
            sbf.append('\n');
            int posicionEnLineas = 2;
            int cantidaPaquetesMax = Integer.parseInt(lineas[1]);
            for (int i = 0; i < cantidaPaquetesMax; i++) {
                sbf.append(lineas[posicionEnLineas]);
                sbf.append('\n');
                posicionEnLineas++;
            }
            System.out.println(sbf.toString());
            return sbf;
        }
    
    
    
    
           public static void EscribirArchivo(String[] args,String DatosImprimir,String NombreArchivo) throws IOException {
               
               try{
                   

                    File file =new File(NombreArchivo);
 
    		//if file doesnt exists, then create it
    		if(!file.exists()){
    			file.createNewFile();
    		}
 
    		//true = append file
                FileWriter fw = new FileWriter(file.getName());
                fw.write(DatosImprimir);
                fw.close();
                
////    		FileWriter fileWritter = new FileWriter(file.getName(),true);
////    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
////    	        bufferWritter.write(DatosImprimir);
////    	        bufferWritter.close();
////                
                
                
                System.out.println("Los datos se an guardado en el archivo de nombre: "+NombreArchivo);
 
                }catch(IOException e){
                        e.printStackTrace();
                }
                
        }
}
