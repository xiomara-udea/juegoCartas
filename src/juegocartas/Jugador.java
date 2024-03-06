package juegocartas;

import java.util.Random;
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
}
