package Algoritmos;

import java.util.ArrayList;
import java.util.List;

import Algoritmos.Grafo.Aresta;

public class MinHeap {
    private List<Aresta> heap;

    public MinHeap() {
        heap = new ArrayList<>();
    }

    public MinHeap(int capacity) {
        heap = new ArrayList<>(capacity);
    }

    public void insert(Aresta aresta) {
        heap.add(aresta);
        int index = heap.size() - 1;
        siftUp(index);
    }

    public Aresta extractMin() {
        if (isEmpty()) {
            return null; // Heap vazio
        }

        Aresta minAresta = heap.get(0);
        int lastIndex = heap.size() - 1;
        heap.set(0, heap.get(lastIndex));
        heap.remove(lastIndex);
        siftDown(0);
        return minAresta;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void siftUp(int index) {
        int parentIndex = (index - 1) / 2;
        while (index > 0 && heap.get(index).peso < heap.get(parentIndex).peso) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    private void siftDown(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int smallest = index;

        if (leftChildIndex < heap.size() && heap.get(leftChildIndex).peso < heap.get(smallest).peso) {
            smallest = leftChildIndex;
        }

        if (rightChildIndex < heap.size() && heap.get(rightChildIndex).peso < heap.get(smallest).peso) {
            smallest = rightChildIndex;
        }

        if (index != smallest) {
            swap(index, smallest);
            siftDown(smallest);
        }
    }

    private void swap(int i, int j) {
        Aresta temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
