package br.ufrn.grafos.algo;

import br.ufrn.grafos.model.Graph;
import java.util.*;

public class DFS {
    private Graph graph;
    private Map<String, Integer> discoveryTime;
    private Map<String, Integer> finishTime;
    private Map<String, String> parent;
    private List<List<String>> dfsForest;
    private int time;

    public DFS(Graph graph) {
        this.graph = graph;
        this.discoveryTime = new HashMap<>();
        this.finishTime = new HashMap<>();
        this.parent = new HashMap<>();
        this.dfsForest = new ArrayList<>();
        this.time = 0;
    }

    public void execute() {
        Set<String> vertices = graph.getVertices();
        Set<String> visited = new HashSet<>();

        for (String vertex : vertices) {
            parent.put(vertex, null);
        }

        for (String vertex : vertices) {
            if (!visited.contains(vertex)) {
                List<String> tree = new ArrayList<>();
                dfsVisit(vertex, visited, tree);
                dfsForest.add(tree);
            }
        }
    }

    private void dfsVisit(String v, Set<String> visited, List<String> tree) {
        time++;
        discoveryTime.put(v, time);
        visited.add(v);
        tree.add(v);

        Set<String> neighbors = graph.getNeighbors(v);
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                parent.put(neighbor, v);
                dfsVisit(neighbor, visited, tree);
            }
        }

        time++;
        finishTime.put(v, time);
    }

    public void printResults() {
        System.out.println("=== RESULTADO DA BUSCA EM PROFUNDIDADE (DFS) ===");
        System.out.println("Tipo do grafo: " + (graph.isDirected() ? "Direcionado" : "Não Direcionado"));
        System.out.println();

        System.out.println("FLORESTA DFS:");
        for (int i = 0; i < dfsForest.size(); i++) {
            System.out.println("Árvore " + (i + 1) + ": " + dfsForest.get(i));
        }
        System.out.println();

        System.out.println("TEMPOS DE PROCESSAMENTO:");
        System.out.println("Vértice | Descoberta | Finalização | Pai");
        System.out.println("--------|------------|-------------|-----");

        List<String> vertices = new ArrayList<>(graph.getVertices());
        Collections.sort(vertices);

        for (String v : vertices) {
            System.out.printf("%-7s | %-10d | %-11d | %s\n",
                    v,
                    discoveryTime.get(v),
                    finishTime.get(v),
                    parent.get(v) != null ? parent.get(v) : "null"
            );
        }
    }

    public Map<String, Integer> getDiscoveryTime() {
        return discoveryTime;
    }

    public Map<String, Integer> getFinishTime() {
        return finishTime;
    }

    public Map<String, String> getParent() {
        return parent;
    }

    public List<List<String>> getDfsForest() {
        return dfsForest;
    }
}