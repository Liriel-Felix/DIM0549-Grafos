package br.ufrn.modelo;

import java.util.List;
import java.util.Map;

public class ResultadoBuscaDFS {
    private final List<String> ordemVisitacao;
    private final Map<String, String> arestasDoCaminho;
    private final Map<String, Integer> tempoInicio;
    private final Map<String, Integer> tempoFim;

    public ResultadoBuscaDFS(List<String> ordemVisitacao, Map<String, String> arestasDoCaminho, Map<String, Integer> tempoInicio, Map<String, Integer> tempoFim) {
        this.ordemVisitacao = ordemVisitacao;
        this.arestasDoCaminho = arestasDoCaminho;
        this.tempoInicio = tempoInicio;
        this.tempoFim = tempoFim;
    }

    public List<String> getOrdemVisitacao() {
        return ordemVisitacao;
    }

    public Map<String, String> getArestasDoCaminho() {
        return arestasDoCaminho;
    }

    public Map<String, Integer> getTempoInicio() {
        return tempoInicio;
    }

    public Map<String, Integer> getTempoFim() {
        return tempoFim;
    }
}