import java.io.IOException;

import Algoritmos.Edmonds;
import Algoritmos.Gabow;
import Algoritmos.Grafo;
import Algoritmos.Tarjan;
import Components.Arquivo;

class Main {
    public static void main(String[] args) throws IOException {
        Grafo g = Arquivo.lerGrafo("Grafos-tests/graph-test-100.txt");
        System.out.println("Algoritmo Edmonds: ");
        Long tempoDeExecucao_Edmonds = System.currentTimeMillis();
        Edmonds grafoEdmonds = new Edmonds(g);
        grafoEdmonds.encontrarArborescenciaMinima();
        System.out.println(System.currentTimeMillis() - tempoDeExecucao_Edmonds + " ms");
        Long tempoDeExecucao_Tarjan = System.currentTimeMillis();
        System.out.println("Algoritmo Trajan: ");
        Tarjan grafoTarjan = new Tarjan(g);
        grafoTarjan.encontrarArborescenciaMinima();
        System.out.println(System.currentTimeMillis() - tempoDeExecucao_Tarjan + " ms");
        Long tempoDeExecucao_Gabow = System.currentTimeMillis();
        System.out.println("Algoritmo Gabow: ");
        Gabow grafGabow = new Gabow(g);
        grafGabow.encontrarArborescenciaMinima();
        System.out.println(System.currentTimeMillis() - tempoDeExecucao_Gabow + " ms");
    }
}