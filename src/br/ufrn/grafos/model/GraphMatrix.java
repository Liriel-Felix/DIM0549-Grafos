package br.ufrn.grafos.model;

import java.util.*;

public class GraphMatrix implements Graph {
    private final boolean directed;
    private List<String> vertices;
    private Map<String, Integer> indexByVertex;
    private int[][] matrix;

    public GraphMatrix(boolean directed, int capacity) {
        this.directed = directed;
        this.vertices = new ArrayList<>();
        this.indexByVertex = new HashMap<>();
        this.matrix = new int[capacity][capacity];
    }

    @Override
    public boolean isDirected() {
        return directed;
    }

    @Override
    public Set<String> getVertices() {
        return new TreeSet<>(vertices);
    }

    @Override
    public void addVertex(String v) {
        if (indexByVertex.containsKey(v)) return;
        ensureCapacity(vertices.size() + 1);
        vertices.add(v);
        indexByVertex.put(v, vertices.size() - 1);
    }

    @Override
    public void addEdge(String u, String v) {
        addVertex(u);
        addVertex(v);
        int i = indexByVertex.get(u);
        int j = indexByVertex.get(v);
        matrix[i][j] = 1;
        if (!directed) matrix[j][i] = 1;
    }

    @Override
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
}