package br.ufrn.modelo;

import java.util.*;

public class Grafo {
    private final boolean directed;
    private final List<String> vertices;
    private final Map<String, Integer> indiceVertices;
    private boolean[][] matrizAdjacencia;
    private final Map<String, Set<String>> listaAdjacencia;

    public Grafo(boolean directed) {
        this.indiceVertices = new HashMap<>();
        this.vertices = new ArrayList<>();
        this.listaAdjacencia = new HashMap<>();
        this.directed = directed;
    }

    public void adicionarVertice(String nome) {
        if (!indiceVertices.containsKey(nome)) {
            int novoIndice = vertices.size();
            vertices.add(nome);
            indiceVertices.put(nome, novoIndice);
            listaAdjacencia.put(nome, new TreeSet<>());
        }
    }

    public void finalizarEstruturas() {
        int numVertices = this.vertices.size();
        this.matrizAdjacencia = new boolean[numVertices][numVertices];
    }

    public void adicionarAresta(String origem, String destino) {
        int indiceOrigem = indiceVertices.get(origem);
        int indiceDestino = indiceVertices.get(destino);

        matrizAdjacencia[indiceOrigem][indiceDestino] = true;

        listaAdjacencia.get(origem).add(destino);

        if (!this.directed) {
            matrizAdjacencia[indiceDestino][indiceOrigem] = true;
            listaAdjacencia.get(destino).add(origem);
        }
    }

    public Set<String> getVertices() {
        return this.indiceVertices.keySet();
    }

    public List<String[]> getArestas() {
        List<String[]> arestas = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : listaAdjacencia.entrySet()) {
            String origem = entry.getKey();
            for (String destino : entry.getValue()) {
                arestas.add(new String[]{origem, destino});
            }
        }
        return arestas;
    }

    public boolean[][] getMatrizAdjacencia() {
        return this.matrizAdjacencia;
    }

    public Map<String, Set<String>> getListaAdjacencia() {
        return this.listaAdjacencia;
    }

    public List<String> getVerticesAsList() {
        return this.vertices;
    }
}