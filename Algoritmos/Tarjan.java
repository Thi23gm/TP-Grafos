package Algoritmos;

import java.io.*;
import java.util.*;

import Algoritmos.Grafo.Aresta;

public class Tarjan {

    public class DisjointSetUnion {
        private int[] parent;
        private int[] rank;

        public DisjointSetUnion(int size) {
            parent = new int[size];
            rank = new int[size];

            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public int union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) {
                return rootX; // Os elementos já estão no mesmo conjunto
            }

            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
                return rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
                return rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
                return rootX;
            }
        }
    }

    private Grafo g;
    List<Aresta> result;
    int[] parent;

    public Tarjan(int v) {
        this.g = new Grafo(v);
        this.parent = new int[v];
        Arrays.fill(parent, -1);
    }

    public Tarjan(Grafo g) {
        this.g = g;
        int v = g.getV();
        this.parent = new int[v];
        Arrays.fill(parent, -1);
    }

    public void encontrarArborescenciaMinima() {
        result = new ArrayList<>();
        int n = g.getV();

        PriorityQueue<Grafo.Aresta> conjuntosArestas = new PriorityQueue<>(n, (a1, a2) -> a1.getPeso() - a2.getPeso());

        for (Grafo.Aresta aresta : g.getArestas()) {
            conjuntosArestas.add(aresta);
        }

        DisjointSetUnion componentesConectados = new DisjointSetUnion(n);

        while (!conjuntosArestas.isEmpty() && result.size() < n - 1) {
            Grafo.Aresta aresta = conjuntosArestas.poll();
            int origem = aresta.getOrigem();
            int destino = aresta.getDestino();

            if (componentesConectados.find(origem) != componentesConectados.find(destino)) {
                componentesConectados.union(origem, destino);
                result.add(aresta);
            }
        }

        imprimirArborescenciaMinima(result);
    }

    void contrairCiclo(DisjointSetUnion contrações, Set<Aresta> arvoresEscolhidas, Aresta edge) {
        // Obtenha os vértices do ciclo
        int v1 = edge.getOrigem();
        int v2 = edge.getDestino();

        // Realize a contração dos vértices do ciclo
        int novoVertice = contrações.union(v1, v2);

        // Adicione a aresta do ciclo às árvores escolhidas
        arvoresEscolhidas.add(edge);

        // Atualize as conexões e arestas no grafo contraído
        for (Aresta aresta : arvoresEscolhidas) {
            int origem = contrações.find(aresta.getOrigem());
            int destino = contrações.find(aresta.getDestino());
            if (origem == v1 || origem == v2) {
                aresta.setOrigem(novoVertice);
            }
            if (destino == v1 || destino == v2) {
                aresta.setDestino(novoVertice);
            }
        }
    }

    public Aresta acharArestaBarata(int v, DisjointSetUnion componentesConectados, MinHeap conjuntosArestas) {
        Aresta arestaBarata = null;
        List<Aresta> arestasConectadas = g.getArestasSaindoDe(v); // Obtenha as arestas saindo de 'v' do grafo

        for (Aresta aresta : arestasConectadas) {
            if (componentesConectados.find(aresta.getOrigem()) != componentesConectados.find(v)) {
                if (arestaBarata == null || aresta.getPeso() < arestaBarata.getPeso()) {
                    arestaBarata = aresta;
                }
            }
        }

        return arestaBarata;
    }

    public boolean formaCiclo(Set<Aresta> arvoresEscolhidas, Aresta aresta) {
        // Crie uma estrutura de dados para rastrear os vértices visitados
        Set<Integer> verticesVisitados = new HashSet<>();

        // Adicione o vértice de origem da aresta à estrutura de dados
        verticesVisitados.add(aresta.origem);

        // Inicie a busca em profundidade a partir do vértice de destino da aresta
        if (dfsFormaCiclo(arvoresEscolhidas, verticesVisitados, aresta.destino)) {
            return true; // A aresta forma um ciclo
        }

        return false; // A aresta não forma um ciclo
    }

    private boolean dfsFormaCiclo(Set<Aresta> arvoresEscolhidas, Set<Integer> verticesVisitados, int vertice) {
        // Verifique se o vértice atual já foi visitado
        if (verticesVisitados.contains(vertice)) {
            return true; // O ciclo foi encontrado
        }

        // Marque o vértice como visitado
        verticesVisitados.add(vertice);

        // Procure por todas as arestas que saem do vértice
        for (Aresta aresta : arvoresEscolhidas) {
            if (aresta.origem == vertice) {
                // Recursivamente, continue a busca em profundidade a partir do vértice de
                // destino da aresta
                if (dfsFormaCiclo(arvoresEscolhidas, verticesVisitados, aresta.destino)) {
                    return true; // O ciclo foi encontrado
                }
            }
        }

        // Se nenhum ciclo foi encontrado, retorne falso
        return false;
    }

    public List<Aresta> getArborescenciaMinima() {
        return result;
    }

    public void imprimirArborescenciaMinima(List<Aresta> arborescenciaMinima) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("Saida_Tarjan.txt"));

            writer.println("Arestas da Arborescência Mínima:");
            for (Aresta aresta : arborescenciaMinima) {
                writer.println(aresta.getOrigem() + " -> " + aresta.getDestino() + "( " + aresta.peso + " )");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
