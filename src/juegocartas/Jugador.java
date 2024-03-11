package juegocartas;

import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Jugador {

    private int TOTALCARTAS = 10;
    private int MARGEN = 20;
    private int DISTANCIA = 50;
    private Random r = new Random();
    private Carta[] cartas = new Carta[TOTALCARTAS];
    public int puntaje = 0;
    String mensaje = "";

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

        for (int i = 0; i < TOTALCARTAS; i++) {
            contadores[cartas[i].getNombre().ordinal()]++;
        }

        for (int i = 0; i < TOTALCARTAS; i++) {
            if (contadores[cartas[i].getNombre().ordinal()] > 1) {
                cartas[i].marcarCarta();
            }
        }

        // Verificar si hay grupos de más de una carta y mostrarlos en el mensaje
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

        // Marcar las cartas involucradas en escaleras
        for (Pinta p : Pinta.values()) {
            marcarCartasEscaleraPinta(p);
        }

        return mensaje + "\n" + getEscalerasPinta(Pinta.TREBOL) + "\n" + getEscalerasPinta(Pinta.PICA) + "\n" + getEscalerasPinta(Pinta.CORAZON) + "\n" + getEscalerasPinta(Pinta.DIAMANTE);
    }

    public void marcarCartasEscaleraPinta(Pinta pinta) {
        for (int i = 1; i < TOTALCARTAS; i++) {
            if (cartas[i].getPinta() == pinta && cartas[i].obtenerIndice() == cartas[i - 1].obtenerIndice() + 1) {
                cartas[i - 1].marcarCarta();
                cartas[i].marcarCarta();

                // Continuar verificando si hay más cartas en la escalera
                int j = i + 1;
                while (j < TOTALCARTAS && cartas[j].getPinta() == pinta && cartas[j].obtenerIndice() == cartas[j - 1].obtenerIndice() + 1) {
                    cartas[j].marcarCarta();
                    j++;
                }
                i = j - 1;
            }
        }
    }

    public String getEscalerasPinta(Pinta pinta) {
        StringBuilder mensaje = new StringBuilder();

        for (int i = 1; i < TOTALCARTAS; i++) {
            if (cartas[i].getPinta() == pinta && cartas[i].obtenerIndice() == cartas[i - 1].obtenerIndice() + 1) {
                StringBuilder escalera = new StringBuilder();
                escalera.append("Escalera de ").append(pinta).append(": ");
                escalera.append(cartas[i - 1].getNombre()).append(", ").append(cartas[i].getNombre());
                cartas[i - 1].marcarCarta();
                cartas[i].marcarCarta();

                // Continuar verificando si hay más cartas en la escalera
                int j = i + 1;
                while (j < TOTALCARTAS && cartas[j].getPinta() == pinta && cartas[j].obtenerIndice() == cartas[j - 1].obtenerIndice() + 1) {
                    escalera.append(", ").append(cartas[j].getNombre());
                    cartas[j].marcarCarta();
                    j++;
                }

                if (mensaje.length() > 0) {
                    mensaje.append("\n");
                }
                mensaje.append(escalera.toString());
                i = j - 1; 
            }
        }
        return mensaje.toString();
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

    public int ObtenerPuntaje() {
        puntaje = 0;
        getGrupos();
        for (Carta c : cartas) {
            if (!c.cartaMarcada) {
                puntaje += c.ObtenerValor();
                JOptionPane.showMessageDialog(null, puntaje);
            }
        }
        return puntaje;
    }
}

