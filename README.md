# AlgoritmosGeneticos
Genetic algorithms - Knapsack and Traveling Salesman problems implemented in Java (Spanish)

Instituto Tecnológico de Costa Rica - Ingeniería en Computación
Curso Análisis de Algoritmos - I Semestre 2015

Algoritmos genéticos - Knapsack y TSP - Java

<b>Especificación del archivo para Knapsack</b>
El archivo de datos vendra con los datos en en siguiente orden, un dato en
cada linea:
1. La capacidad total W de nuestro contenedor.
2. Un numero N indicando la cantidad de paquetes.
3. para cada paquete pi
(a) El tama~no del paquete wi.
(b) El valor del paquete vi.
4. cantidad de individuos en la poblacion
5. para cada individuo:
(a) lista de valores xi 2 f0; 1g con i 2 f1; 2; : : : ;Ng
Por ejemplo el siguiente archivo de texto:
7.2
3
1.4 2.8
6.5 3.2
1.1 3.1
1
0 1 0
Indica que la capacidad del contendor es W = 7:2, hay N = 3 paquetes p1,
p2 y p3, tal que el tama~no de p1 es w1 = 1:4, y su valor es v1 = 2:8, el tama~no
de p2 es w2 = 6:5 y su valor v2 = 3:2 y el tama~no de p3 es w3 = 1:1 con un
valor de v3 = 3:1. Por ultimo, el 0 indica que la cantidad de individuos en
la poblacion inicial es de 1 individuo y que este para este individuo se tiene
que x1 = 0, x2 = 1 y x3 = 0

