    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesgeneticas;

public class IndividuMochila {
      int []GenesIndividuo;
      float [][] Genes;
      float fitnesIndividuo;
      float maxPeso;
      
    public int[] getGenesIndividuo() {
        return GenesIndividuo;
    }

    public float[][] getGenes() {
        return Genes;
    }

    public float getFitnesIndividuo() {
        if(fitnesIndividuo>maxPeso){
        return (float) 0.1;
        }
        return fitnesIndividuo;
    }
      

    public IndividuMochila(int[] GenesIndividuo, float[][] Genes, float fitnesIndividuo,float maxPeso) {
        this.GenesIndividuo = GenesIndividuo;
        this.Genes = Genes;
        this.fitnesIndividuo = fitnesIndividuo;
        this.maxPeso=maxPeso;
    }
    
    public void imprimirIndividuo(){
        System.out.println("");
        System.out.print("[");
        for(int i=0;i<GenesIndividuo.length;i++){
            if(i==GenesIndividuo.length-1){
                System.out.print(GenesIndividuo[i]);
                break;
            }
            System.out.print(GenesIndividuo[i]+" , ");
        }
        System.out.print("]");
    }
  
      public void imprimirGenes(){
        System.out.println("");
        System.out.print("[");
        for(int i=0;i<GenesIndividuo.length;i++){
            if(i==GenesIndividuo.length-1){
                System.out.print("["+Genes[i][0]+","+Genes[i][1]+"]");
                break;
            }
            System.out.print("["+Genes[i][0]+","+Genes[i][1]+"]"+" , ");
        }
        System.out.print("]");
    }
    
    
    public float getpeso(){
        float peso=0;
        for(int i=0;i<GenesIndividuo.length;i++){
            if(GenesIndividuo[i]==1){
                peso=(float)peso+(float)Genes[i][0];
            }
            
        }
        return (float)peso;
    }
      
    public float getcost(){
        float costo=0;
        for(int i=0;i<GenesIndividuo.length;i++){
            if(GenesIndividuo[i]==1){
                costo=(float)costo+(float)Genes[i][1];
            }
            
        }
        return (float)costo;
    }
    
    public String genestostring(){
        String genesString="\n";
        genesString+="[";
        for(int i=0;i<GenesIndividuo.length;i++){
            if(i==GenesIndividuo.length-1){
                genesString+="["+Genes[i][0]+","+Genes[i][1]+"]";
                break;
            }
            genesString+="["+Genes[i][0]+","+Genes[i][1]+"]"+" , ";
        }
        genesString+="]";
        return genesString;
    }
    
    public String individuotostring(){
        String genesString="\n";
        genesString+="[";
        for(int i=0;i<GenesIndividuo.length;i++){
            if(i==GenesIndividuo.length-1){
                genesString+=GenesIndividuo[i];
                break;
            }
            genesString+=GenesIndividuo[i]+" , ";
        }
        genesString+="]";
        return genesString;
    }
    
    



}
