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

        System.out.println("Iniciando DFS");
        System.out.println();

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

                List<String> vizinhos = new ArrayList<>(listaAdj.get(u));
                Collections.sort(vizinhos);

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
        System.out.println("\n════════════════════════════════════════════════");
        System.out.println("                RESULTADO DFS");
        System.out.println("════════════════════════════════════════════════");
        System.out.println("Ordem de visitação: " + String.join(" → ", this.ordemVisitacao));
        System.out.println();
        System.out.println("Vértice   Predecessor   Tempo Início/Fim");
        System.out.println("─────────────────────────────────────────");

        // Ordena os vértices lexicograficamente
        List<String> verticesOrdenados = new ArrayList<>(vertices);
        Collections.sort(verticesOrdenados);

        for(String vertice : verticesOrdenados) {
            String pai = this.predecessores.getOrDefault(vertice, "-");
            int tInicio = this.tempoInicio.get(vertice);
            int tFim = this.tempoFim.get(vertice);
            System.out.printf("%-9s %-13s %d/%-6d\n",
                    vertice, pai, tInicio, tFim);
        }
        System.out.println("════════════════════════════════════════════════\n");
    }
}