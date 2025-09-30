package br.ufrn.grafos.algo;

import br.ufrn.grafos.model.Graph;
import java.util.*;

public class BFS {
    private final Graph g;

    private final Map<String, Integer> dist = new HashMap<>();
    private final Map<String, String> parent = new HashMap<>();
    private final List<String> order = new ArrayList<>();

    public BFS(Graph g) {
        this.g = Objects.requireNonNull(g, "graph não pode ser null");
    }

    public void execute(String source) {
        List<String> V = new ArrayList<>(g.getVertices());
        Collections.sort(V);
        if (V.isEmpty()) return;

        String s = (source != null) ? source : V.get(0);

        dist.clear();
        parent.clear();
        order.clear();

        for (String v : V) {
            dist.put(v, -1);
            parent.put(v, null);
        }

        Queue<String> q = new ArrayDeque<>();
        dist.put(s, 0);
        q.add(s);

        while (!q.isEmpty()) {
            String u = q.remove();
            order.add(u);

            for (String w : new TreeSet<>(g.getNeighbors(u))) {
                if (dist.get(w) == -1) {
                    dist.put(w, dist.get(u) + 1);
                    parent.put(w, u);
                    q.add(w);
                }
            }
        }
    }

    public Map<String, Integer> getDistances() { return dist; }
    public Map<String, String> getParents() { return parent; }
    public List<String> getVisitOrder() { return order; }

    public void printResults() {
        List<String> V = new ArrayList<>(g.getVertices());
        Collections.sort(V);

        System.out.println("=== BFS ===");
        System.out.println("Ordem de visita: " + order);
        System.out.printf("%-10s | %-9s | %-6s%n", "Vertice", "Distancia", "Pai");
        System.out.println("-----------|-----------|--------");
        for (String v : V) {
            Integer d = dist.getOrDefault(v, -1);
            String p = parent.get(v);
            System.out.printf("%-10s | %-9d | %-6s%n", v, d, (p == null ? "-" : p));
        }
    }
}