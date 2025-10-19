package model;

import model.algorithms.*;
import java.util.List;

public class QueueLevel {
    private int id;     //ID de cada cola(1,2 o 3)
    private String policy;   //Politica de planificacion (Algoritmos)
    private int quantum;    //Quantum, para RR
    private List<Process> processes;    //Lista procesos pertenecientes a esta cola

    /**
     * Constructor que inicializa una cola con su ID, política, quantum y lista de procesos.
     *
     * @param id       ID de la cola (1, 2, 3, etc.)
     * @param policy   Política de planificación (RR, FCFS)
     * @param quantum  Quantum de tiempo para RR
     * @param processes Lista de procesos asignados a esta cola.
     */
    public QueueLevel(int id, String policy, int quantum, List<Process> processes) {
        this.id = id;
        this.policy = policy;
        this.quantum = quantum;
        this.processes = processes;
    }


    /**
     *Metodo que ejecuta la política de planificación asignada a esta cola.
     *
     * @param startTime Tiempo en el que comienza esta cola.
     * @return          El tiempo final después de ejecutar todos los procesos de esta cola.
     */
    public int execute(int startTime) {
        //Según la política de planificación, se elige el algoritmo correspondiente.
        switch (policy) {
            case "RR":
                //Si es RR se ejecuta este con el quantum dado
                return new RoundRobin(quantum).execute(processes, startTime);
            case "FCFS":
                //Si es FCFS, se ejecuta este algoritmo
                return new FCFS().execute(processes, startTime);
            default:
                return startTime;
        }
    }

    public List<Process> getProcesses() {
        return processes;
    }
}
