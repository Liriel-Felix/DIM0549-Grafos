package br.ufrn;

import br.ufrn.algoritmo.BFS;
import br.ufrn.algoritmo.DFS;
import br.ufrn.modelo.ResultadoBuscaBFS;
import br.ufrn.modelo.ResultadoBuscaDFS;
import br.ufrn.modelo.Grafo;
import br.ufrn.util.DotReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            DotReader reader = new DotReader();

            System.out.println("Lendo grafo não direcionado...");
            Grafo grafoNaoDirecionado = reader.lerArquivo("samples/undirected_graph.gv");

            BFS bfs = new BFS();
            ResultadoBuscaBFS resultadoBfsNaoDirecionado = bfs.executar(grafoNaoDirecionado);

            DFS dfs = new DFS();
            ResultadoBuscaDFS resultadoDfsNaoDirecionado = dfs.executar(grafoNaoDirecionado);

            System.out.println("\nLendo grafo direcionado...");
            Grafo grafoDirecionado = reader.lerArquivo("samples/directed_graph.gv");

            ResultadoBuscaBFS resultadoBfsDirecionado = bfs.executar(grafoDirecionado);

            ResultadoBuscaDFS resultadoDfsDirecionado = dfs.executar(grafoDirecionado);

        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao ler o arquivo do grafo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}