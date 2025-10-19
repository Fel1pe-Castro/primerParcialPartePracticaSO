# CodigoImplementacionMLQ-ppSO

Este proyecto implementa un simulador del algoritmo **Multi-Level Queue (MLQ)** en Java, aplicando los principios de **Programación Orientada a Objetos (POO). El propósito es modelar cómo un sistema operativo organiza la ejecución de procesos mediante múltiples colas de prioridad, cada una con su propio algoritmo de planificación.

El simulador está diseñado para manejar tres niveles de colas, donde cada una representa un nivel de prioridad diferente. La **cola 1** utiliza el algoritmo **Round Robin con quantum 3**, la **cola 2** también usa **Round Robin pero con quantum 5**, y la **cola 3** aplica **FCFS (First Come, First Served)**. Esta estructura permite observar cómo el tiempo de espera, respuesta y retorno cambian dependiendo del algoritmo y del nivel de prioridad del proceso.

El uso de POO permite mantener el proyecto bien estructurado, modular y fácil de extender. La clase **Process** encapsula toda la información y métricas de cada proceso, mientras que las demás clases se encargan de coordinar la ejecución y la planificación. Gracias a esta separación de responsabilidades, el código es claro y cada componente tiene un propósito definido.

Durante la ejecución, el programa procesa varios archivos de entrada (por ejemplo, `mlq002.txt`, `mlq003.txt`, `mlq004.txt`) y genera archivos de salida con los resultados (`mlq002_output.txt`, etc.). En estos archivos se incluyen los valores de las métricas individuales de cada proceso y los promedios globales, lo que permite analizar el comportamiento del planificador bajo diferentes cargas de trabajo.

Los resultados obtenidos muestran que, a medida que aumenta el tiempo de ráfaga de los procesos, los tiempos de espera (WT) y retorno (TAT) también incrementan. Esto evidencia que el esquema MLQ prioriza las colas superiores, beneficiando a los procesos más importantes, pero al mismo tiempo aumentando la espera en los de menor prioridad. Es decir, MLQ es eficaz en entornos donde la prioridad es más importante que la equidad.

En conclusión, este simulador demuestra cómo funciona la planificación multinivel en un entorno controlado, destacando las ventajas y desventajas del enfoque MLQ. Aunque ofrece una clara jerarquía de prioridades, no optimiza el tiempo de espera total. Sin embargo, constituye una base sólida para implementar versiones más dinámicas como el **Multi-Level Feedback Queue (MLFQ)**, donde los procesos pueden cambiar de cola según su comportamiento.

