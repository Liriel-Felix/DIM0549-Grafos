package br.ufrn;

import br.ufrn.algoritmo.BFS;
import br.ufrn.algoritmo.DFS;
import br.ufrn.modelo.ResultadoBuscaBFS;
import br.ufrn.modelo.ResultadoBuscaDFS;
import br.ufrn.modelo.Grafo;
import br.ufrn.util.DotReader;
import br.ufrn.util.GraphVisualizer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            DotReader reader = new DotReader();
            BFS bfs = new BFS();
            DFS dfs = new DFS();

            System.out.println("Lendo grafo não direcionado...");
            Grafo grafoNaoDirecionado = reader.lerArquivo("samples/undirected_graph.gv");

            ResultadoBuscaBFS resultadoBfsNaoDirecionado = bfs.executar(grafoNaoDirecionado);
            ResultadoBuscaDFS resultadoDfsNaoDirecionado = dfs.executar(grafoNaoDirecionado);

            GraphVisualizer.gerarImagemGrafo(grafoNaoDirecionado, "output/grafo_inicial_nao_direcionado.png");
            GraphVisualizer.gerarImagemBuscaBFS(grafoNaoDirecionado, resultadoBfsNaoDirecionado, "output/bfs_nao_direcionado.png");
            GraphVisualizer.gerarImagemBuscaDFS(grafoNaoDirecionado, resultadoDfsNaoDirecionado, "output/dfs_nao_direcionado.png");

            System.out.println("\nLendo grafo direcionado...");
            Grafo grafoDirecionado = reader.lerArquivo("samples/directed_graph.gv");

            ResultadoBuscaBFS resultadoBfsDirecionado = bfs.executar(grafoDirecionado);
            ResultadoBuscaDFS resultadoDfsDirecionado = dfs.executar(grafoDirecionado);

            GraphVisualizer.gerarImagemGrafo(grafoDirecionado, "output/grafo_inicial_direcionado.png");
            GraphVisualizer.gerarImagemBuscaBFS(grafoDirecionado, resultadoBfsDirecionado, "output/bfs_direcionado.png");
            GraphVisualizer.gerarImagemBuscaDFS(grafoDirecionado, resultadoDfsDirecionado, "output/dfs_direcionado.png");

            System.out.println("\nProcessamento concluído. Imagens salvas na pasta 'output/'.");

        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao ler o arquivo do grafo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}