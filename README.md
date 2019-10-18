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
- Opcional: IDE (se recomienda IntelliJ)

# Generador de Procesos

**Entrada**
- Argumentos : [ cantidad de cores ][ cantidad de procesos ][ cantidad de io por procesos ] [ quantum de tiempo ] [ velocidad ] 

Ej : 8 50 5 8 100

**Salida**
Ventana que contiene:
- Tabla de procesos
- Gráfico del uso de cores
- Gráfica cantidad de procesos corriendo, esperando y listos vs tiempo

# Árbol Rojo y Negro

Métodos implementados: 
- Inserción y eliminación junto a todos los metodos auxiliares necesarios para manetener el 
arbol balanceado.
- Metodos auxiliares para verificacion de nodos
- Metodo para obtener el minimo del arbol

Basado en: https://github.com/KnIfER/RBTree-java

# Detalles del Planificador de Linux

Clases:
- Process: contiene la información del proceso (pid, prioridad, tiempo de llegada, cpu, io, vruntime, tiempo ejecución, estado, tiempo io).
- ProcessGenerator: genera una cierta o infinita cantidad de procesos y los inserta en el árbol.
- Dispatcher: despacha a cierto core el proceso a ejecutar.
- IOHandler: se encarga del manejo de la interrupción.
- Timer: contabiliza el tiempo transcurrido en milisegundos.
- VruntimeCalculator: calcula el vruntime en base a la prioridad del proceso necesario para el CFS.
