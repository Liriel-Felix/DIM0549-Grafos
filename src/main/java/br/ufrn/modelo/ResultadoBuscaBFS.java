package br.ufrn.modelo;

import java.util.Map;
import java.util.Set;

public class ResultadoBuscaBFS {
    private final Set<String> nosVisitados;
    private final Map<String, String> arestasDoCaminho;
    private final Map<String, Integer> distancia;

    public ResultadoBuscaBFS(Set<String> nosVisitados, Map<String, String> arestasDoCaminho, Map<String, Integer> distancia) {
        this.nosVisitados = nosVisitados;
        this.arestasDoCaminho = arestasDoCaminho;
        this.distancia = distancia;
    }

    public Set<String> getNosVisitados() {
        return nosVisitados;
    }

    public Map<String, String> getArestasDoCaminho() {
        return arestasDoCaminho;
    }

    public Map<String, Integer> getDistancia() {
        return distancia;
    }
}