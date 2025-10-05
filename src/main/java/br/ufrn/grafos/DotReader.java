package br.ufrn.grafos;

import br.ufrn.modelo.Grafo;

import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.InputStream;
import java.io.IOException;

public class DotReader {

    /**
     * Lê um arquivo .gv ou .dot da pasta 'resources' e o converte para o nosso objeto Grafo.
     * @param nomeArquivo O nome do arquivo (ex: "meu_grafo.gv").
     * @return Um objeto da nossa classe Grafo com os dados preenchidos.
     */
    public Grafo lerArquivo(String nomeArquivo) throws IOException {
        // Procura o arquivo dentro da pasta 'src/main/resources'.
        // Esta é a forma correta de ler recursos internos em um projeto Java/Maven.
        InputStream stream = getClass().getClassLoader().getResourceAsStream(nomeArquivo);

        if (stream == null) {
            throw new IOException("Arquivo '" + nomeArquivo + "' não encontrado na pasta resources.");
        }

        // Usa a biblioteca graphviz-java para fazer o parsing do arquivo.
        MutableGraph grafoDaBiblioteca = new Parser().read(stream);

        // Agora, criamos nosso próprio objeto Grafo e o populamos.
        Grafo nossoGrafo = new Grafo();

        // Itera sobre todos os nós (vértices) lidos pela biblioteca
        grafoDaBiblioteca.nodes().forEach(node -> {
            String nomeVertice = node.name().toString();

            // TODO: Chamar o método do nossoGrafo para adicionar o vértice.
            // nossoGrafo.adicionarVertice(nomeVertice);
        });

        // Itera sobre todas as arestas lidas pela biblioteca
        grafoDaBiblioteca.edges().forEach(edge -> {
            String origem = edge.from().name().toString();
            String destino = edge.to().name().toString();

            // TODO: Chamar o método do nossoGrafo para adicionar a aresta.
            // nossoGrafo.adicionarAresta(origem, destino);
        });

        System.out.println("Leitura do arquivo concluída!");
        return nossoGrafo;
    }
}