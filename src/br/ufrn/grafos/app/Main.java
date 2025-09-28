package br.ufrn.grafos.app;

import br.ufrn.grafos.algo.DFS;
import br.ufrn.grafos.io.DotParser;
import br.ufrn.grafos.model.Graph;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=== PROCESSANDO GRAFO NÃO DIRECIONADO ===");
            Graph undirectedGraph = DotParser.parseDotFile("samples/undirected_graph.gv");
            DFS dfsUndirected = new DFS(undirectedGraph);
            dfsUndirected.execute();
            dfsUndirected.printResults();

            System.out.println("\n" + "=".repeat(50) + "\n");

            System.out.println("=== PROCESSANDO GRAFO DIRECIONADO ===");
            Graph directedGraph = DotParser.parseDotFile("samples/directed_graph.gv");
            DFS dfsDirected = new DFS(directedGraph);
            dfsDirected.execute();
            dfsDirected.printResults();

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}