package br.ufrn.grafos.model;

import java.util.*;

public class GraphMatrix implements Graph {
    private boolean directed;
    private List<String> vertices;
    private int[][] matrix;

    public GraphMatrix(boolean directed, int capacity) {
        this.directed = directed;
        this.vertices = new ArrayList<>();
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
        if (!vertices.contains(v)) {
            vertices.add(v);
        }
    }

    @Override
    public void addEdge(String u, String v) {
        int i = vertices.indexOf(u);
        int j = vertices.indexOf(v);
        matrix[i][j] = 1;
        if (!directed) matrix[j][i] = 1;
    }

    @Override
    public Set<String> getNeighbors(String v) {
        Set<String> neighbors = new TreeSet<>();
        int i = vertices.indexOf(v);
        for (int j = 0; j < vertices.size(); j++) {
            if (matrix[i][j] == 1) {
                neighbors.add(vertices.get(j));
            }
        }
        return neighbors;
    }
}
