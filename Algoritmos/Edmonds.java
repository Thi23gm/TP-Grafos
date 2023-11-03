package Algoritmos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import Algoritmos.Grafo.Aresta;

public class Edmonds {
    private Grafo g;
    List<Aresta> result;
    int[] parent;

    public Edmonds(int v) {
        this.g = new Grafo(v);
        this.parent = new int[v];
        Arrays.fill(parent, -1);
    }

    public Edmonds(Grafo g) {
        this.g = g;
        int v = g.getV();
        this.parent = new int[v];
        Arrays.fill(parent, -1);
    }

    public void encontrarArborescenciaMinima() throws IOException {
        result = new ArrayList<>();
        int n = g.getV();
        int comparacoes = 0; // Variável para contar as comparações

        // Mapa para manter as arestas de saída de cada vértice
        Map<Integer, Aresta> arestasSaida = new HashMap<>();

        // Preenche o mapa com as arestas de saída mais leves para cada vértice
        for (Aresta edge : g.getArestas()) {
            int v = edge.destino;

            comparacoes++; // Incrementa a contagem de comparações
            if (arestasSaida.containsKey(v) && edge.peso < arestasSaida.get(v).peso) {
                arestasSaida.put(v, edge);
                comparacoes++; // Incrementa a contagem de comparações
            } else if (!arestasSaida.containsKey(v)) {
                arestasSaida.put(v, edge);
                comparacoes++; // Incrementa a contagem de comparações
            }
        }

        for (int v = 0; v < n; v++) {
            Aresta bestEdge = arestasSaida.get(v);

            comparacoes++; // Incrementa a contagem de comparações
            if (bestEdge != null) {
                int u = bestEdge.origem;
                result.add(bestEdge);
                parent[u] = v;
            }
        }

        imprimirArborescenciaMinima();
        System.out.println("Número de comparações realizadas: " + comparacoes);
    }

    public List<Aresta> getArborescenciaMinima() {
        return result;
    }

    private void imprimirArborescenciaMinima() throws IOException {
        FileWriter fw = new FileWriter("Saida_Edmonds.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write("Arestas da Arborescência Mínima:");
        bw.newLine();

        for (Aresta aresta : result) {
            String linha = aresta.origem + " - " + aresta.destino + " (" + aresta.peso + ")";
            bw.write(linha);
            bw.newLine();
        }

        bw.close();
        fw.close();
    }
}
