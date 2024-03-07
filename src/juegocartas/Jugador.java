package juegocartas;

import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Jugador {
    
   private int TOTALCARTAS = 10;
   private int MARGEN = 20;
   private int DISTANCIA = 50;
   private Random r = new Random();
   private Carta [] cartas = new Carta [TOTALCARTAS];
   
   public void repartir(){
       for (int i = 0; i < TOTALCARTAS; i++){
           cartas[i] = new Carta(r);
       }
   }
   
   public void mostrar(JPanel pnl){
       pnl.removeAll();
       int x = MARGEN;
       for (Carta c: cartas){
           c.mostrar(pnl, x, MARGEN);
           x += DISTANCIA;
       }
       
       pnl.repaint();
   }
   
   public String getGrupos(){
       String mensaje = "No se encontraron grupos";
       int [] contadores = new int [NombreCarta.values().length];
       for (Carta c: cartas){
           contadores[c.getNombre().ordinal()]++;
       }
       int totalGrupos = 0;
       for (int c: contadores){
           if (c>=2){
               totalGrupos++;
           }
       }
       if (totalGrupos >0){
           mensaje = "Los grupos encontrados fueron: \n";
           for ( int i= 0; i<contadores.length; i++){
               if(contadores[i]>1){
                   mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] +  "\n";
               } 
           }
       }
       return mensaje;
   }
   
   //Está para organizar
   public String getEscaleras(){
       String mensaje = "No se encontraron escaleras";
       
       int [] contadores = new int [NombreCarta.values().length];
       for (Carta c: cartas){
           contadores[c.getNombre().ordinal()]++;
       }
       int totalEscaleras = 0;
       for (int c: contadores){
           if (c>=2){
               totalEscaleras++;
           }
       }
       if (totalEscaleras >0){
           mensaje = "Los grupos encontrados fueron: \n";
           for ( int i= 0; i<contadores.length; i++){
               if(contadores[i]>1){
                   mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] +  "\n";
               }
               
           }
       }
       return mensaje;
   }
   
  public void ordenar() {
    for (int j = 0; j < TOTALCARTAS - 1; j++) {
        int menorIndice = j;

        for (int i = j + 1; i < TOTALCARTAS; i++) {
            if (cartas[i].obtenerIndice() < cartas[menorIndice].obtenerIndice()) {
                menorIndice = i;
            }
        }

        // Intercambiar las cartas en las posiciones j y menorIndice
        Carta temp = cartas[j];
        cartas[j] = cartas[menorIndice];
        cartas[menorIndice] = temp;
    }
}

   
   
}
