# Sistemas de Operación II
# Planificador de Linux

El planificardor de Linux se encarga de planificar la utilización de los recursos de ejecución por los procesos.

El planificador que simularemos es el planificador CFS, el cual sus siglas significan ‘Completely Fair Scheduler’, o en español, el Planificador Completamente Justo. Este planificador se basa en el manejo del tiempo de corrida virtual de cada proceso con un valor en nanosegundos, con el cual se puede determinar de forma precisa el tiempo esperado de CPU de cada proceso. Siempre se intenta correr el proceso que contenga el tiempo de corrida virtual más pequeño, es decir, el proceso que menor tiempo ha sido ejecutado.

La principal estructura para llevar a cabo estas tareas es un árbol rojo negro, en el cual cada proceso es representado como un nodo del árbol. Estos nodos contienen la información del tiempo de corrida virtual y están ordenados de tal forma que el nodo con el menor tiempo se encuentre en el lado izquierdo del árbol. Una vez que el proceso elegido es procesado por un cierto tiempo, este es registrado en como tiempo de corrida virtual y una vez que este se vuelva lo suficientemente alto tal que otro proceso se vuelva el nodo más izquierdo del árbol (añadiendo un poco de granularidad para evitar la sobre planificación de tareas y eliminar el cache), este nuevo proceso es elegido para ser ejecutado y el anterior se vuelve a introducir en el árbol de procesos.

# Integrantes 
- Carlos Pérez
- José Bracuto
- Fabiola Martínez

# Requerimientos
- Java 8
- Maven
- IDE (se recomienda IntelliJ)

# Generador de Procesos

- Argumentos : [ cantidad de procesos ] [ velocidad (segundos)] [ CPU ] [ IO ]

Nota: la velocidad está representada en segudos (ej. 10 minutos = 600 segundos ).

```
[{"tiempoLlegada":"23:55:2","io":"[17,20]","cpu":"[19,3,32]","id":0,"prioridad":6},{"tiempoLlegada":"23:55:3","io":"[17,20,38,11]","cpu":"[19,3,32,7,0,16]","id":1,"prioridad":5}]
```

# Árbol Rojo y Negro

Métodos implementados: inserción y eliminación.
