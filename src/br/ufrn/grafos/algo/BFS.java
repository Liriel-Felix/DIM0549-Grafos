package br.ufrn.grafos.algo;

import br.ufrn.grafos.model.Graph;

import java.util.*;

public class BFS {
    private final Graph graph;

    private final Map<String, Integer> dist = new HashMap<>();
    private final Map<String, String> parent = new HashMap<>();
    private final List<String> visitOrder = new ArrayList<>();

    public BFS(Graph graph) {
        this.graph = Objects.requireNonNull(graph, "graph não pode ser null");
    }

    public void execute() {
        execute(null);
    }

    public void execute(String source) {
        List<String> vertices = new ArrayList<>(graph.getVertices());
        Collections.sort(vertices);

        if (vertices.isEmpty()) return;

        String s = (source != null) ? source : vertices.get(0);
        if (!graph.getVertices().contains(s)) {
            throw new IllegalArgumentException("Fonte '" + s + "' não existe no grafo.");
        }

        dist.clear();
        parent.clear();
        visitOrder.clear();
        for (String v : vertices) {
            dist.put(v, -1);
            parent.put(v, null);
        }

        Deque<String> q = new ArrayDeque<>();
        dist.put(s, 0);
        q.add(s);

        while (!q.isEmpty()) {
            String u = q.remove();
            visitOrder.add(u);

            List<String> neighbors = new ArrayList<>(graph.getNeighbors(u));
            Collections.sort(neighbors);

            for (String w : neighbors) {
                if (dist.get(w) == -1) {
                    dist.put(w, dist.get(u) + 1);
                    parent.put(w, u);
                    q.add(w);
                }
            }
        }
    }

    public Map<String, Integer> getDistances() {
        return Collections.unmodifiableMap(dist);
    }

    public Map<String, String> getParents() {
        return Collections.unmodifiableMap(parent);
    }

    public List<String> getVisitOrder() {
        return Collections.unmodifiableList(visitOrder);
    }

    public void printResults() {
        List<String> vertices = new ArrayList<>(graph.getVertices());
        Collections.sort(vertices);

        System.out.println("=== RESULTADO BFS ===");
        System.out.println("Ordem de visita: " + visitOrder);
        System.out.println();
        System.out.println(String.format("%-10s | %-9s | %-6s", "Vertice", "Distancia", "Pai"));
        System.out.println("-----------|-----------|--------");
        for (String v : vertices) {
            String p = parent.get(v);
            Integer d = dist.getOrDefault(v, -1);
            System.out.println(String.format("%-10s | %-9d | %-6s", v, d, p == null ? "-" : p));
        }
    }
}