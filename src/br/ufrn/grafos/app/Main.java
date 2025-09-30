package br.ufrn.grafos.app;

import br.ufrn.grafos.algo.BFS;
import br.ufrn.grafos.algo.DFS;
import br.ufrn.grafos.io.DotParser;
import br.ufrn.grafos.model.Graph;

public class Main {
    public static void main(String[] args) {
        try {
            String undirectedPath = "samples/undirected_graph.gv";
            String directedPath   = "samples/directed_graph.gv";

            System.out.println("=== GRAFO NÃO DIRECIONADO ===");

            Graph gListUnd = DotParser.parseToList(undirectedPath);
            DFS dfsUnd = new DFS(gListUnd);
            dfsUnd.execute();
            dfsUnd.printResults();

            Graph gMatUnd = DotParser.parseToMatrix(undirectedPath);
            BFS bfsUnd = new BFS(gMatUnd);
            bfsUnd.execute(null);
            bfsUnd.printResults();

            System.out.println("\n" + "=".repeat(50) + "\n");

            System.out.println("=== GRAFO DIRECIONADO ===");

            Graph gListDir = DotParser.parseToList(directedPath);
            DFS dfsDir = new DFS(gListDir);
            dfsDir.execute();
            dfsDir.printResults();

            Graph gMatDir = DotParser.parseToMatrix(directedPath);
            BFS bfsDir = new BFS(gMatDir);
            bfsDir.execute(null);
            bfsDir.printResults();

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
