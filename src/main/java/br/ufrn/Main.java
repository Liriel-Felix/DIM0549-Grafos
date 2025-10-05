package br.ufrn;

import br.ufrn.modelo.Grafo;
import br.ufrn.util.DotReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            DotReader reader = new DotReader();

            System.out.println("Lendo grafo não direcionado...");
            Grafo grafoNaoDirecionado = reader.lerArquivo("samples/undirected_graph.gv");

            // TODO: Chamar algoritmo de BFS

            System.out.println("\nLendo grafo direcionado...");
            Grafo grafoDirecionado = reader.lerArquivo("samples/directed_graph.gv");

            // TODO: Chamar algoritmo de DFS

        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao ler o arquivo do grafo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}