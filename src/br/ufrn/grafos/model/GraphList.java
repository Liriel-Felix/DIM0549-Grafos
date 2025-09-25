package br.ufrn.grafos.model;

import java.util.*;

public class GraphList implements Graph {
    private boolean directed;
    private Map<String, Set<String>> adj;

    public GraphList(boolean directed) {
        this.directed = directed;
        this.adj = new TreeMap<>();
    }

    @Override
    public boolean isDirected() {
        return directed;
    }

    @Override
    public Set<String> getVertices() {
        return adj.keySet();
    }

    @Override
    public void addVertex(String v) {
        adj.putIfAbsent(v, new TreeSet<>());
    }

    @Override
    public void addEdge(String u, String v) {
        addVertex(u);
        addVertex(v);
        adj.get(u).add(v);
        if (!directed) {
            adj.get(v).add(u);
        }
    }

    @Override
    public Set<String> getNeighbors(String v) {
        return adj.getOrDefault(v, new TreeSet<>());
    }
}
