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

**Entrada**
- Argumentos : [ cantidad de procesos ]

**Salida**
- Arreglos de procesos
```
[{"tiempoLlegada":0,"io":[42,97,89,13,59,0,98,58,74],"cpu":[22,82,69,83,45,96,70,69,29,45],"id":0,"prioridad":2},{"tiempoLlegada":70,"io":[60,37,84,20,12,52,58,51,73,19,96,47,30],"cpu":[36,14,58,26,22,4,94,17,60,79,44,90,37,69],"id":1,"prioridad":8},{"tiempoLlegada":165,"io":[52,48,67,3,5,47,30,45,23,98,44,64,47,11],"cpu":[76,4,58,44,75,74,52,98,36,72,96,0,80,20,4],"id":2,"prioridad":6}]
```

# Árbol Rojo y Negro

Métodos implementados: 
- Inserción y eliminación junto a todos los metodos auxiliares necesarios para manetener el 
arbol balanceado.
- Metodos auxiliares para verificacion de nodos
- Metodo para obtener el minimo del arbol

Basado en: https://github.com/KnIfER/RBTree-java


