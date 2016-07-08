# AlgoritmosGeneticos
Genetic algorithms - Knapsack and Traveling Salesman problems implemented in Java (Spanish)

Instituto Tecnológico de Costa Rica - Ingeniería en Computación
Curso Análisis de Algoritmos - I Semestre 2015

Algoritmos genéticos - Knapsack y TSP - Java

<b>Especificación del archivo para Knapsack</b>

El archivo de datos vendra con los datos en en siguiente orden, un dato en cada linea:

1. La capacidad total W de nuestro contenedor.

2. Un numero N indicando la cantidad de paquetes.

3. Para cada paquete pi
 (a) El tama~no del paquete wi.
 (b) El valor del paquete vi.

4. cantidad de individuos en la poblacion

5. Para cada individuo:
 (a) lista de valores xi en {0,1} con i entre {1,2,...,N}

Por ejemplo el siguiente archivo de texto:

7.2

3

1.4 2.8

6.5 3.2

1.1 3.1

1

0 1 0


Indica que la capacidad del contendor es W = 7:2, hay N = 3 paquetes p1, p2 y p3, tal que el tama~no de p1 es w1 = 1:4, y su valor es v1 = 2:8, el tama~no de p2 es w2 = 6:5 y su valor v2 = 3:2 y el tama~no de p3 es w3 = 1:1 con un valor de v3 = 3:1. Por ultimo, el 0 indica que la cantidad de individuos en la poblacion inicial es de 1 individuo y que este para este individuo se tiene que x1 = 0, x2 = 1 y x3 = 0

<b>Especificación del archivo para TSP</b>

1. Un numero N indicando la cantidad de ciudades

2. Una matriz de distancias dij indicando la distancia de la ciudad i a la ciudad j. Las distancias son simetricas asi que solo debe indicar la mitad de la matriz.

3. cantidad de individuos en la poblacion

4. para cada individuo debe poner la secuencia de ciudades (cada ciudad se identica con un numero del 1 al n)

Por ejemplo el siguiente archivo de texto:

4

3 1 5

2 4

6
3 1 2 4

4 1 2 3

2 1 3 4

1 4 3 2


Indica que hay 4 ciudades donde las distancias son: d(1,2) = 3, d(1,3) = 1, d(1,4) = 5, d(2,3) = 2, d(2,4) = 4, y d(3,4) = 6. Las soluciones iniciales son los recorridos de las ciudades:

3 → 1 → 2 → 4 → 3.

4 → 1 → 2 → 3 → 4.

2 → 1 → 3 → 4 → 2.

1 → 4 → 3 → 2 → 1.

<b>Para más información, consulte el pdf "Documentación"</b>
