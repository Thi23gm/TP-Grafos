package Algoritmos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import Algoritmos.Grafo.Aresta;

public class Gabow {
    private Grafo grafo;
    private int numVertices;
    private List<Aresta> arvoreMinima; // Para armazenar a arborescência mínima
    private int[] pai; // Para rastrear os pais dos vértices na arborescência
    private int[] profundidade; // Para rastrear a profundidade dos vértices
    private PriorityQueue<Aresta> arestasAtivas; // Usado para manter as arestas ativas com prioridade

    public Gabow(Grafo grafo) {
        this.grafo = grafo;
        this.numVertices = grafo.getV();
        this.arvoreMinima = new ArrayList<>();
        this.pai = new int[numVertices];
        this.profundidade = new int[numVertices];
        this.arestasAtivas = new PriorityQueue<>(); // Use a implementação adequada do heap Fibonacci
    }

    private void construirCaminhoInicial(int verticeInicial) {
        // Inicialize as estruturas de dados necessárias
        arvoreMinima = new ArrayList<>();
        pai = new int[numVertices];
        profundidade = new int[numVertices];
        PriorityQueue<Aresta> arestasAtivas = new PriorityQueue<>(Comparator.comparingInt(Aresta::getPeso));
        Set<Integer> visitados = new HashSet<>();

        // Inicialize os arrays de pai e profundidade
        for (int i = 0; i < numVertices; i++) {
            pai[i] = i;
            profundidade[i] = 0;
        }

        // Marque o vértice inicial como visitado
        visitados.add(verticeInicial);

        // Pré-calcule as arestas saindo de cada vértice
        List<List<Aresta>> arestasSaindo = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            arestasSaindo.add(grafo.getArestasSaindoDe(i));
        }

        // Adicione as arestas do vértice inicial à fila de prioridade
        for (Aresta aresta : arestasSaindo.get(verticeInicial)) {
            arestasAtivas.add(aresta);
        }

        // Continue até que a arborescência mínima esteja completa
        while (arvoreMinima.size() < numVertices - 1) {
            // Encontre a aresta de menor peso na fila de prioridade
            Aresta arestaAtual = arestasAtivas.poll();

            int origem = arestaAtual.getOrigem();
            int destino = arestaAtual.getDestino();

            // Verifique se a aresta forma um ciclo
            if (!visitados.contains(destino)) {
                // Adicione a aresta à arborescência mínima
                arvoreMinima.add(arestaAtual);
                visitados.add(destino);

                // Atualize as estruturas de pai e profundidade
                int paiOrigem = encontrar(origem);
                int paiDestino = encontrar(destino);

                if (paiOrigem != paiDestino) {
                    if (profundidade[paiOrigem] < profundidade[paiDestino]) {
                        pai[paiOrigem] = paiDestino;
                    } else if (profundidade[paiOrigem] > profundidade[paiDestino]) {
                        pai[paiDestino] = paiOrigem;
                    } else {
                        pai[paiOrigem] = paiDestino;
                        profundidade[paiDestino]++;
                    }
                }

                // Adicione as arestas do vértice destino à fila de prioridade
                for (Aresta aresta : arestasSaindo.get(destino)) {
                    arestasAtivas.add(aresta);
                }
            }
        }
    }

    private int encontrar(int vertice) {
        if (pai[vertice] != vertice) {
            pai[vertice] = encontrar(pai[vertice]);
        }
        return pai[vertice];
    }

    private void extenderCaminho() {
        // Encontre a aresta de entrada mais barata que liga o caminho atual à floresta
        // ativa
        Aresta arestaMaisBarata = arestasAtivas.poll();

        int origem = arestaMaisBarata.getOrigem();
        int destino = arestaMaisBarata.getDestino();

        // Verifique se a origem da aresta já está no caminho de crescimento
        if (encontrar(origem) != encontrar(destino)) {
            // A origem não está no caminho, então ela se torna o novo início do caminho
            // Atualize as estruturas de dados
            pai[encontrar(origem)] = encontrar(destino);

            // Adicione a aresta à arborescência mínima
            arvoreMinima.add(arestaMaisBarata);

            // Adicione as arestas saindo do destino à floresta ativa
            for (Aresta aresta : grafo.getArestasSaindoDe(destino)) {
                if (encontrar(aresta.getDestino()) != encontrar(destino)) {
                    arestasAtivas.add(aresta);
                }
            }
        }
    }

    private void contrairCiclo() {
        // Encontre o ciclo no caminho de crescimento
        List<Aresta> ciclo = new ArrayList<>();
        int verticeCiclo = -1;

        for (Aresta aresta : arvoreMinima) {
            int origem = aresta.getOrigem();
            int destino = aresta.getDestino();

            if (encontrar(origem) == encontrar(destino)) {
                verticeCiclo = encontrar(origem);
                ciclo.add(aresta);
                break;
            }
        }

        // Encontre o ciclo completo
        for (Aresta aresta : arvoreMinima) {
            if (aresta.getOrigem() == verticeCiclo && aresta.getDestino() != verticeCiclo) {
                ciclo.add(aresta);
            }
        }

        // Encontre a aresta de menor peso no ciclo
        Aresta arestaMenorPeso = ciclo.get(0);

        for (Aresta aresta : ciclo) {
            if (aresta.getPeso() < arestaMenorPeso.getPeso()) {
                arestaMenorPeso = aresta;
            }
        }

        // Remova o ciclo do caminho e da arborescência mínima
        arvoreMinima.remove(arestaMenorPeso);

        // Atualize as estruturas de dados para contrair o ciclo
        for (Aresta aresta : ciclo) {
            if (aresta != arestaMenorPeso) {
                int origem = aresta.getOrigem();
                int destino = aresta.getDestino();

                pai[encontrar(origem)] = encontrar(destino);
                arvoreMinima.remove(aresta);

                // Adicione as arestas saindo do destino à floresta ativa
                for (Aresta arestaSaida : grafo.getArestasSaindoDe(destino)) {
                    if (encontrar(arestaSaida.getDestino()) != encontrar(destino)) {
                        arestasAtivas.add(arestaSaida);
                    }
                }
            }
        }
    }

    private Aresta consultarArestaMaisBarata() {
        // Consulte a aresta ativa mais barata na estrutura de dados de arestas ativas.
        return arestasAtivas.peek();
    }

    public void encontrarArborescenciaMinima() {
        construirCaminhoInicial(0); // Construir o caminho inicial a partir de um vértice inicial

        while (!arestasAtivas.isEmpty()) {
            Aresta arestaMaisBarata = consultarArestaMaisBarata();

            int origem = arestaMaisBarata.getOrigem();
            int destino = arestaMaisBarata.getDestino();

            if (encontrar(origem) != encontrar(destino)) {
                extenderCaminho(); // Se não, estenda o caminho de crescimento
            } else {
                contrairCiclo(); // Caso contrário, contraia o ciclo formado
            }
        }
        imprimirArvoreMinima();
    }

    public void imprimirArvoreMinima() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Saida_Gabow.txt"));

            for (Aresta aresta : arvoreMinima) {
                int origem = aresta.getOrigem();
                int destino = aresta.getDestino();
                int peso = aresta.getPeso();

                writer.write(origem + " -> " + destino + " (" + peso + ")");
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo ");
            e.printStackTrace();
        }
    }

    public List<Aresta> obterArvoreMinima() {
        return arvoreMinima;
    }
}
