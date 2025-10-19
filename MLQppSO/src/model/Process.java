package model;

/**
 *Clase Process
 * ----------------
 *Representa un proceso dentro del sistema operativo.
 *Contiene toda la información relacionada con su ejecución:
 *datos iniciales, métricas de rendimiento y estado actual.
 */
public class Process {
    //Entrada, datos basicos del proceso
    private String label;       //Nombre/Etiqueta proceso
    private int burstTime;      //BT
    private int arrivalTime;    //AT
    private int queue;          //Q, cola a la que pertenece dentro del MLQ
    private int priority;       //Prioridad del proceso

    //Salida, Metricas Calculdas
    private int waitingTime;      //WT: Tiempo total que el proceso esperó para ser atendido
    private int completionTime;   // CT: Momento en el que el proceso terminó completamente
    private int responseTime;     // RT: Tiempo desde 0 hasta su primera ejecución
    private int turnaroundTime;   // TAT: Tiempo total desde que llega hasta que termina
    private int remainingTime;    // Tiempo restante por ejecutar (se usa en Round Robin)

    //Control de la primera ejecución
    private boolean started = false;      //Indica si el proceso ya comenzó a ejecutarse
    private int firstResponseTime = 0;    //Guarda el tiempo en que usó la CPU por primera vez



    /**
     * Inicializa un proceso con sus parámetros básicos.
     *
     * @param label  nombre del proceso (ej: "P1")
     * @param bt     tiempo de ráfaga (Burst Time)
     * @param at     tiempo de llegada (Arrival Time)
     * @param q      número de cola (para MLQ)
     * @param pr     prioridad (en caso de usar planificación por prioridad)
     */
    public Process(String label, int bt, int at, int q, int pr) {
        this.label = label;
        this.burstTime = bt;
        this.arrivalTime = at;
        this.queue = q;
        this.priority = pr;
        this.remainingTime = bt;
    }

    //Getters, Setters basicos
    public int getBurstTime() { return burstTime; }
    public int getArrivalTime() { return arrivalTime; }
    public int getQueue() { return queue; }
    public int getRemainingTime() { return remainingTime; }
    public void setRemainingTime(int remainingTime) { this.remainingTime = remainingTime; }


    //Guardamos todas las métricas finales del proceso.
    public void setTimes(int wt, int ct, int rt, int tat) {
        this.waitingTime = wt;
        this.completionTime = ct;
        this.responseTime = rt;
        this.turnaroundTime = tat;
    }

    //Getters para las métricas (lectura desde el Scheduler o para mostrar resultados)
    public int getWaitingTime() { return waitingTime; }
    public int getCompletionTime() { return completionTime; }
    public int getResponseTime() { return responseTime; }
    public int getTurnaroundTime() { return turnaroundTime; }

    //Control de inicio de CPU (usado por los algoritmos)
    public boolean isStarted() { return started; }
    public void setStarted(boolean started) { this.started = started; }

    public int getFirstResponseTime() { return firstResponseTime; }
    public void setFirstResponseTime(int firstResponseTime) { this.firstResponseTime = firstResponseTime; }

    /**
     *Convierte la información del proceso a una cadena formateada para guardar en archivo.
     *Se usa al generar el archivo de salida (mlq_output.txt) para cada valor de entrada.
     */
    @Override
    public String toString() {
        return label + ";" + burstTime + ";" + arrivalTime + ";" + queue + ";" + priority +
                ";" + waitingTime + ";" + completionTime + ";" + responseTime + ";" + turnaroundTime;
    }
}
