package br.ufrn.algoritmo;

import br.ufrn.modelo.Grafo;
import br.ufrn.modelo.ResultadoBuscaDFS;
import java.util.*;

public class DFS {
    private int tempo;
    private List<String> ordemVisitacao;
    private Map<String, String> predecessores;
    private Map<String, Integer> tempoInicio;
    private Map<String, Integer> tempoFim;

    public ResultadoBuscaDFS executar(Grafo grafo) {
        this.tempo = 0;
        this.ordemVisitacao = new ArrayList<>();
        this.predecessores = new HashMap<>();
        this.tempoInicio = new HashMap<>();
        this.tempoFim = new HashMap<>();

        List<String> vertices = grafo.getVerticesAsList();

        for (String u : vertices) {
            if (!tempoInicio.containsKey(u)) {
                dfsVisit(u, grafo);
            }
        }

        System.out.println("\nBusca DFS finalizada!");
        imprimirResultado(vertices);

        return new ResultadoBuscaDFS(ordemVisitacao, predecessores, tempoInicio, tempoFim);
    }

    private void dfsVisit(String u, Grafo grafo) {
        Map<String, Set<String>> listaAdj = grafo.getListaAdjacencia();

        this.tempo++;
        this.tempoInicio.put(u, this.tempo);
        this.ordemVisitacao.add(u);
        System.out.println("Visitando: " + u + " (Início: " + this.tempo + ")");

        if (listaAdj.containsKey(u)) {
            for (String v : listaAdj.get(u)) {
                if (!tempoInicio.containsKey(v)) {
                    this.predecessores.put(v, u);
                    dfsVisit(v, grafo);
                }
            }
        }

        this.tempo++;
        this.tempoFim.put(u, this.tempo);
        System.out.println("Finalizando: " + u + " (Fim: " + this.tempo + ")");
    }

    private void imprimirResultado(List<String> vertices) {
        System.out.println("\n--- Resultado da Busca por Profundidade ---");
        System.out.println("Ordem de visitação: " + String.join(" -> ", this.ordemVisitacao));
        System.out.println("\nDetalhes por Vértice (em ordem lexicográfica):");

        for(String vertice : vertices) {
            String pai = this.predecessores.getOrDefault(vertice, "Nenhum (raiz)");
            int tInicio = this.tempoInicio.get(vertice);
            int tFim = this.tempoFim.get(vertice);
            System.out.println(
                    "  - Vértice: " + vertice +
                            ", Predecessor: " + pai +
                            ", Tempo Início/Fim: " + tInicio + "/" + tFim
            );
        }
        System.out.println("-----------------------------------------");
    }
}