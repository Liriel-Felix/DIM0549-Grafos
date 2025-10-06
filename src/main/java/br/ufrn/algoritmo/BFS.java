package br.ufrn.algoritmo;

import br.ufrn.modelo.Grafo;
import br.ufrn.modelo.ResultadoBuscaBFS;
import java.util.*;

public class BFS {
    public ResultadoBuscaBFS executar(Grafo grafo) {
        boolean[][] matriz = grafo.getMatrizAdjacencia();

        List<String> vertices = grafo.getVerticesAsList();

        Map<String, Integer> indices = grafo.getMapaDeIndices();

        if (vertices.isEmpty()) {
            return new ResultadoBuscaBFS(Collections.emptySet(), Collections.emptyMap(), Collections.emptyMap());
        }

        Queue<String> fila = new LinkedList<>();
        Set<String> visitados = new LinkedHashSet<>();
        Map<String, String> predecessores = new HashMap<>();
        Map<String, Integer> distancia = new HashMap<>();
        String verticeInicial = vertices.get(0);
        visitados.add(verticeInicial);
        distancia.put(verticeInicial, 0);
        fila.add(verticeInicial);

        System.out.println("Iniciando BFS a partir de: " + verticeInicial);

        while (!fila.isEmpty()) {
            String u = fila.poll();
            int uIndex = indices.get(u);
            System.out.println("Visitando: " + u);
            for (int vIndex = 0; vIndex < vertices.size(); vIndex++) {
                if (matriz[uIndex][vIndex]) {
                    String v = vertices.get(vIndex);
                    if (!visitados.contains(v)) {
                        visitados.add(v);
                        distancia.put(v, distancia.get(u) + 1);
                        predecessores.put(v, u);
                        fila.add(v);
                        System.out.println("  -> Descobriu " + v);
                    }
                }
            }
        }

        System.out.println("\nBusca BFS finalizada!");
        imprimirResultado(visitados, distancia, predecessores);

        return new ResultadoBuscaBFS(visitados, predecessores, distancia);
    }

    private void imprimirResultado(Set<String> visitados, Map<String, Integer> distancia, Map<String, String> predecessores) {
        System.out.println("\n--- Resultado da Busca por Largura ---");
        System.out.println("Ordem de visitação: " + String.join(" -> ", visitados));
        System.out.println("\nDetalhes por Vértice:");
        for (String vertice : visitados) {
            String pai = predecessores.getOrDefault(vertice, "Nenhum (raiz)");
            int dist = distancia.get(vertice);
            System.out.println("  - Vértice: " + vertice + ", Distância da raiz: " + dist + ", Predecessor: " + pai);
        }
        System.out.println("------------------------------------");
    }
}