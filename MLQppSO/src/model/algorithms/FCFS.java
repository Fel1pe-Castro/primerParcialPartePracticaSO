package model.algorithms;

import model.Process;
import java.util.*;


/**
 * Clase que implementa el algoritmo FCFS (First Come, First Served)
 * ---------------------------------------------------------------
 */
public class FCFS {

 /**
     * @param processes  Lista de procesos que pertenecen a esta cola FCFS
     * @param startTime  Tiempo en que comienza esta cola (puede ser 0 o el tiempo final de otra cola)
     * @return           El tiempo total después de ejecutar todos los procesos
     */
    public int execute(List<Process> processes, int startTime) {

        //Ordenamos los procesos por su tiempo de llegada (arrivalTime)
        //Con esto se cumple el FCFS
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        //reloj de la CPU con el tiempo en que arranca esta cola
        int time = startTime;

        //Recorremos todos los procesos
        for (Process p : processes) {

            //Si el CPU está libre antes de que el proceso llegue, esperamos hasta su llegada
            if (time < p.getArrivalTime()) time = p.getArrivalTime();

            //Registramos cuando el proceso se ejecuta por primera vez, desde t=0
            if (!p.isStarted()) {
                p.setStarted(true);
                p.setFirstResponseTime(time);
            }

            //Ejecutamos proceso -> Sumamos su Burst Time (BT) al reloj
            time += p.getBurstTime();

            //Calculamos las métricas principales del proceso
            int ct = time;                              //CT (Completion Time): cuando terminó
            int tat = ct - p.getArrivalTime();          //TAT (Turnaround Time): total desde que llegó
            int wt = tat - p.getBurstTime();            //WT (Waiting Time): lo que esperó antes de ejecutarse
            int rt = p.getFirstResponseTime();          //RT (Response Time): desde 0 hasta su primera ejecución
            p.setTimes(wt, ct, rt, tat);                //Guardamos estos tiempos dentro del objeto Process

        }

        // Devolvemos el tiempo total acumulado al final de esta cola
        return time;
    }
}
