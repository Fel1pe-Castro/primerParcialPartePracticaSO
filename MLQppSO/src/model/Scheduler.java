package model;

import java.io.*;
import java.util.*;

/**
 *Clase Scheduler (Planificador)
 * ---------------------------------
 *Esta clase es la clave del sistema de planificación MLQ.
 *Se encarga de:
 *   -Almacenar las colas del sistema (cada una con su algoritmo).
 *   -Ejecutarlas en orden.
 *   -Y finalmente guardar los resultados (métricas) en un archivo de salida.
 */
public class Scheduler {
    private List<QueueLevel> queues = new ArrayList<>(); //Lista que contiene las colas (QueueLevel)

    /**
     *Metodo para agregar una cola al planificador.
     * @param q Objeto de tipo QueueLevel (una cola con su política y procesos).
     */
    public void addQueue(QueueLevel q) {
        queues.add(q);
    }

    /**
     *Metodo que ejecuta todas las colas del MLQ en orden.
     *
     *Este metodo coordina la ejecución secuencial de cada nivel de cola, segun su prioridad:
     *primero Q1, luego Q2, luego Q3...
     */
    public void executeAll() {
        int time = 0;

        //Recorremos cada cola agregada al planificador
        for (QueueLevel q : queues) {
            //Ejecutamos la cola, y el metodo execute de cada cola devuelve el tiempo final de su ejecucion
            time = q.execute(time); // actualiza el tiempo global tras cada cola
        }
    }

    /**
     *Metodo que guarda los resultados finales de todos los procesos en un archivo.
     *
     *@param filename  nombre del archivo de salida
     */
    public void saveResults(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write("# etiqueta; BT; AT; Q; Pr; WT; CT; RT; TAT\n");
            //Variables para acumular los promedios globales
            double sumWT = 0, sumCT = 0, sumRT = 0, sumTAT = 0;
            int count = 0;

            //Recorremos cada cola y cada proceso dentro de ellas
            for (QueueLevel q : queues) {
                for (Process p : q.getProcesses()) {
                    bw.write(p.toString() + "\n");

                    //Se acumulan los valores
                    sumWT += p.getWaitingTime();
                    sumCT += p.getCompletionTime();
                    sumRT += p.getResponseTime();
                    sumTAT += p.getTurnaroundTime();
                    count++;
                }
            }

            //Calculamos los promedios finales de las métricas y los escribimos al final
            bw.write(String.format("\nWT=%.2f; CT=%.2f; RT=%.2f; TAT=%.2f;\n",
                    sumWT / count, sumCT / count, sumRT / count, sumTAT / count));
        }
    }
}
