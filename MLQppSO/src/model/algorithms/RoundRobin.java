package model.algorithms;

import model.Process;
import java.util.*;

/**
 *Clase que implementa el algoritmo Round Robin (RR)
 * -----------------------------------------------------
 */
public class RoundRobin {
    private int quantum;

    //Constructor que recibe el quantum que se usará en esta cola.
    public RoundRobin(int quantum) {
        this.quantum = quantum;
    }


    /**
     *Metodo principal que ejecuta la planificación Round Robin.
     *
     * @param processes  Lista de procesos que pertenecen a esta cola RR.
     * @param startTime  Tiempo inicial en el que empieza esta cola.
     * @return           El tiempo total después de ejecutar todos los procesos.
     */
    public int execute(List<Process> processes, int startTime) {
        //Creamos una cola (FIFO) para manejar los procesos en orden de llegada.
        Queue<Process> queue = new LinkedList<>();

        //Ordenamos los procesos por tiempo de llegada
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        int time = startTime; //Reloj con tiempo inicial recibido

        //Agregamos todos los procesos a la cola de ejecución.
        queue.addAll(processes);

        //Mientras hayan procesos en la cola
        while (!queue.isEmpty()) {

            //Tomamos el primer proceso de la cola.
            Process p = queue.poll();

            //Registrar la primera vez que entra a CPU (Siempre y cuando el proceso no fue ejecutado antes)
            if (!p.isStarted()) {
                p.setStarted(true);
                p.setFirstResponseTime(time); //RT desde t=0
            }

            //Verificacion de el proceso si necesita mas tiempo del quantum
            if (p.getRemainingTime() > quantum) {
                //Si el proceso no termina dentro del quantum, ejecutamos solo el quantum y lo interrupimos
                time += quantum;
                p.setRemainingTime(p.getRemainingTime() - quantum);

                //Lo mandamos al final de la cola para esperar su proximo turno
                queue.add(p);
            } else {
                //El proceso si termina dentro del quantum
                time += p.getRemainingTime(); //Se suma el tiempo restante
                p.setRemainingTime(0); //Marcamos que se termino

                //Calculamos las metricas
                int ct = time;                         //CT (Completion Time): cuando terminó
                int tat = ct - p.getArrivalTime();     //TAT (Turnaround Time): total desde que llegó
                int wt = tat - p.getBurstTime();         // WT (Waiting Time): lo que esperó antes de ejecutarse
                int rt = p.getFirstResponseTime();       // RT (Response Time): desde 0 hasta su primera ejecución
                p.setTimes(wt, ct, rt, tat);        //Guardamos los valores en el objeto Process
            }
        }
        //Retornamos el tiempo total al finalizar todos los procesos de esta cola
        return time;
    }
}
