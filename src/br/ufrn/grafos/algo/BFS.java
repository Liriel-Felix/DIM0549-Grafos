package br.ufrn.grafos.algo;

import java.util.*;

public class BFS {
    private final boolean directed;
    private final List<String> vertices;           // índice -> nome
    private final Map<String, Integer> indexByVertex; // nome -> índice
    private int[][] matrix;

    public BFS(boolean directed, int capacity) {
        this.directed = directed;
        this.vertices = new ArrayList<>();
        this.indexByVertex = new HashMap<>();
        this.matrix = new int[Math.max(1, capacity)][Math.max(1, capacity)];
    }

    public boolean isDirected() {
        return directed;
    }

    public Set<String> getVertices() {
        return new TreeSet<>(vertices); // ordenado lexicograficamente
    }

    public void addVertex(String v) {
        if (indexByVertex.containsKey(v)) return;
        ensureCapacity(vertices.size() + 1);
        vertices.add(v);
        indexByVertex.put(v, vertices.size() - 1); // <-- índice é size-1
    }

    public void addEdge(String u, String v) {
        addVertex(u);
        addVertex(v);
        int i = indexByVertex.get(u);
        int j = indexByVertex.get(v);
        matrix[i][j] = 1;
        if (!directed) matrix[j][i] = 1;           // <-- espelha se não dirigido
    }

    public Set<String> getNeighbors(String v) {
        Integer i = indexByVertex.get(v);
        if (i == null) return new TreeSet<>();
        Set<String> neighbors = new TreeSet<>();
        for (int j = 0; j < vertices.size(); j++) {
            if (matrix[i][j] == 1) {
                neighbors.add(vertices.get(j));
            }
        }
        return neighbors;
    }

    private void ensureCapacity(int required) {
        int n = matrix.length;
        if (required <= n) return;
        int newN = Math.max(required, n * 2);
        int[][] newMatrix = new int[newN][newN];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, n);
        }
        matrix = newMatrix;
    }

    public void run() {
        Set<String> ordered = getVertices();
        if (ordered.isEmpty()) return;

        String source = ordered.iterator().next();   // menor vértice

        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();
        Queue<String> q = new ArrayDeque<>();

        visited.add(source);
        dist.put(source, 0);
        parent.put(source, null);
        q.add(source);

        while (!q.isEmpty()) {
            String u = q.poll();
            for (String w : getNeighbors(u)) {      // vizinhos já em ordem
                if (!visited.contains(w)) {
                    visited.add(w);
                    parent.put(w, u);
                    dist.put(w, dist.get(u) + 1);
                    q.add(w);
                }
            }
        }

        System.out.println("--- Resultado BFS ---");
        for (String v : ordered) {
            String p = parent.get(v);
            int d = dist.getOrDefault(v, -1);
            System.out.printf("Vértice: %s, Distância: %d, Pai: %s%n",
                    v, d, (p == null ? "-" : p));
        }
    }
}