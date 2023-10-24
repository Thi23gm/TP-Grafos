import java.io.IOException;

import Algoritmos.Edmonds;
import Algoritmos.Grafo;
import Components.Arquivo;

class Main {
    public static void main(String[] args) throws IOException {
        Grafo g = Arquivo.lerGrafo("Grafos-tests/graph-test-100.txt");
        Edmonds grafoEdmonds = new Edmonds(g);
        grafoEdmonds.encontrarArborescenciaMinima();
    }
}