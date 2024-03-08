package juegocartas;

import java.util.Random;
import javax.swing.JPanel;

public class Jugador {

    private int TOTALCARTAS = 10;
    private int MARGEN = 20;
    private int DISTANCIA = 50;
    private Random r = new Random();
    private Carta[] cartas = new Carta[TOTALCARTAS];

    public void repartir() {
        for (int i = 0; i < TOTALCARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int x = MARGEN;
        for (Carta c : cartas) {
            c.mostrar(pnl, x, MARGEN);
            x += DISTANCIA;
        }

        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No se encontraron grupos";
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }
        int totalGrupos = 0;
        for (int c : contadores) {
            if (c >= 2) {
                totalGrupos++;
            }
        }
        if (totalGrupos > 0) {
            mensaje = "Los grupos encontrados fueron: \n";
            for (int i = 0; i < contadores.length; i++) {
                if (contadores[i] > 1) {
                    mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] + "\n";
                }
            }
        }
        ordenar();
        mensaje += "\n" + getEscalerasPinta(Pinta.TREBOL) +  getEscalerasPinta(Pinta.PICA) +  getEscalerasPinta(Pinta.CORAZON) +  getEscalerasPinta(Pinta.DIAMANTE);

        return mensaje;
    }

    public String getEscalerasPinta(Pinta pinta) {
        String mensaje = "";

        for (int i = 1; i < TOTALCARTAS; i++) {
        
            if (cartas[i].getPinta() == pinta && cartas[i].obtenerIndice() == cartas[i - 1].obtenerIndice() + 1) {
                StringBuilder escalera = new StringBuilder();
                escalera.append("Escalera de ").append(pinta).append(": ");
                escalera.append(cartas[i - 1].getNombre()).append(", ").append(cartas[i].getNombre());

                // Continuar verificando si hay más cartas en la escalera
                int j = i + 1;
                while (j < TOTALCARTAS && cartas[j].getPinta() == pinta && cartas[j].obtenerIndice() == cartas[j - 1].obtenerIndice() + 1) {
                    escalera.append(", ").append(cartas[j].getNombre());
                    j++;
                }
                mensaje = escalera.toString() + "\n" ;
                i = j - 1; 
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
