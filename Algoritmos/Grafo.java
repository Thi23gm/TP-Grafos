package Algoritmos;

import java.util.*;

public class Grafo {
    private int V; // Número de vértices
    private List<Aresta> arestas; // Lista de arestas

    // Classe para representar uma aresta
    class Aresta {
        int origem, destino, peso;

        Aresta(int origem, int destino, int peso) {
            this.origem = origem - 1;
            this.destino = destino - 1;
            this.peso = peso;
        }
    }

    // Construtor do grafo
    public Grafo(int v) {
        this.V = v;
        this.arestas = new ArrayList<>();
    }

    // Método para adicionar uma aresta ao grafo
    public void add(int origem, int destino, int peso) {
        Aresta aresta = new Aresta(origem, destino, peso);
        arestas.add(aresta);
    }

    // Método para encontrar o conjunto que contém um vértice
    int buscarVertice(int[] parente, int i) {
        if (parente[i] == -1)
            return i;
        return buscarVertice(parente, parente[i]);
    }

    // Método para unir dois conjuntos
    void uniao(int[] parente, int x, int y) {
        int xset = buscarVertice(parente, x);
        int yset = buscarVertice(parente, y);
        parente[xset] = yset;
    }

    // Método para obter a raiz de um conjunto
    int obterRaiz(int[] parente, int i) {
        if (parente[i] == -1)
            return i;
        return obterRaiz(parente, parente[i]);
    }

    public int getV() {
        return V;
    }

    public void setV(int v) {
        V = v;
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public void setArestas(List<Aresta> arestas) {
        this.arestas = arestas;
    }
}
