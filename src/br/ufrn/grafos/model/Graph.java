package br.ufrn.grafos.model;

import java.util.Set;

public interface Graph {
    boolean isDirected();
    Set<String> getVertices();
    void addVertex(String v);
    void addEdge(String u, String v);
    Set<String> getNeighbors(String v);
}
