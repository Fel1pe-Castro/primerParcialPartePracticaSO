package main;

import model.Process;
import model.QueueLevel;
import model.Scheduler;
import java.io.*;
import java.util.*;

/**
 *Clase MLQSimulator
 * ---------------------
 *Clase principal del proyecto.
 *Se encarga de:
 *   - Leer los archivos de entrada con los procesos.
 *   - Crear las colas de planificación (RR, RR, FCFS).
 *   - Ejecutar el planificador (Scheduler).
 *   - Y guardar los resultados en archivos de salida.
 */
public class MLQSimulator {

    public static void main(String[] args) {

        // Lista de archivos a probar, cada archivo contiene los procesos con sus atributod
        String[] archivos = {
                "src/main/mlq002.txt",
                "src/main/mlq003.txt",
                "src/main/mlq004.txt"
        };

        // Carpeta donde se guardarán los resultados
        String outputDir = "./"; // raíz del proyecto

        // Iterar sobre cada archivo de entrada
        for (String fileName : archivos) {

            System.out.println("\n🔹 Ejecutando simulación para: " + fileName);

            //Nuevo planificador (Scheduler) para esta simulación
            Scheduler scheduler = new Scheduler();

            //Listas que representan las tres colas del MLQ
            List<Process> q1 = new ArrayList<>(); //Cola 1=RR(3)
            List<Process> q2 = new ArrayList<>(); //Cola 2=RR(5)
            List<Process> q3 = new ArrayList<>(); //Cola 3=FCFS

            //Leer cada archivo .txt
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("#") || line.trim().isEmpty()) continue;

                    //Se divide cada linea por ; para extraer cada atributo
                    String[] p = line.split(";");
                    Process proc = new Process(
                            p[0].trim(),                        //Etiqueta
                            Integer.parseInt(p[1].trim()),      //BT
                            Integer.parseInt(p[2].trim()),      //AT
                            Integer.parseInt(p[3].trim()),      //Q
                            Integer.parseInt(p[4].trim())       //Priority
                    );

                    //Clasificamos el proceso según su cola
                    switch (proc.getQueue()) {
                        case 1 -> q1.add(proc);
                        case 2 -> q2.add(proc);
                        case 3 -> q3.add(proc);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + fileName);
                e.printStackTrace();
                continue; //pasar al siguiente archivo si falla este
            }

            //Configurar el esquema MLQ: RR(3), RR(5), FCFS
            scheduler.addQueue(new QueueLevel(1, "RR", 3, q1));
            scheduler.addQueue(new QueueLevel(2, "RR", 5, q2));
            scheduler.addQueue(new QueueLevel(3, "FCFS", 0, q3));

            //Ejecutar la simulación
            scheduler.executeAll();

            //Nombre de salida
            String baseName = new File(fileName).getName();  // ej: mlq002.txt
            String outputName = baseName.replace(".txt", "_output.txt"); // ej: mlq002_output.txt
            String outputPath = outputDir + outputName;

            //Guardamos los resultados generados por el Scheduler
            try {
                scheduler.saveResults(outputName);
                System.out.println("Resultados guardados en: " + outputName);
            } catch (IOException e) {
                System.err.println("Error al guardar resultados en " + outputName);
                e.printStackTrace();
            }
        }
        //Simulaciones terminan
        System.out.println("\n🏁 Todas las simulaciones fueron completadas con éxito.");
    }
}
