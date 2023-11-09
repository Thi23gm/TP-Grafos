# Trabalho Prático de Grafos: Árvore Geradora Mínima em Java

Este projeto consiste na implementação de três algoritmos para encontrar a Árvore Geradora Mínima em grafos: Edmonds, Gabow e Tarjan. Cada algoritmo é capaz de encontrar a MST em diferentes contextos e com complexidades distintas.

## Características
- Implementação em Java dos algoritmos de Edmonds, Gabow e Tarjan para encontrar a Árvore Geradora Mínima.
- O programa aceita o caminho do arquivo de grafo a ser analisado como argumento para a execução.
- Gera saídas em arquivos de texto para cada algoritmo, exibindo a MST e informações adicionais como tempo de execução e número de comparações realizadas.

## Como Executar
1. Certifique-se de ter o JDK (Java Development Kit) instalado.
2. No terminal, navegue até o diretório contendo o código fonte.
3. Compile o código usando: `javac Main.java`.
4. Execute o programa utilizando: 
```
java Main <caminho_do_grafo>
```
Substitua `<caminho_do_grafo>` pelo caminho do arquivo do grafo, por exemplo: `Grafo-tests/graph-test-100.txt` ou `Grafo-tests/graph-test-500.txt`.

## Saídas
Após a execução, o programa gerará os seguintes arquivos de saída:
- `saida_Edmonds.txt`: Resultados do algoritmo de Edmonds (MST, tempo de execução, número de comparações).
- `saida_Gabow.txt`: Resultados do algoritmo de Gabow.
- `saida_Tarjan.txt`: Resultados do algoritmo de Tarjan.