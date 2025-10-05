package br.ufrn.util;

import br.ufrn.modelo.Grafo;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.IOException;
import java.io.InputStream;

public class DotReader {
    public Grafo lerArquivo(String nomeArquivo) throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(nomeArquivo);
        if (stream == null) {
            throw new IOException("Arquivo '" + nomeArquivo + "' não encontrado na pasta resources.");
        }

        MutableGraph grafoDaBiblioteca = new Parser().read(stream);
        boolean isDirected = grafoDaBiblioteca.isDirected();
        Grafo nossoGrafo = new Grafo(isDirected);
        grafoDaBiblioteca.nodes().forEach(node -> {
            String nomeVertice = node.name().toString();
            nossoGrafo.adicionarVertice(nomeVertice);
        });

        nossoGrafo.finalizarEstruturas();
        grafoDaBiblioteca.edges().forEach(edge -> {
            String origem = edge.from().name().toString();
            String destino = edge.to().name().toString();
            nossoGrafo.adicionarAresta(origem, destino);
        });

        System.out.println("Leitura e processamento do arquivo '" + nomeArquivo + "' concluídos.");
        return nossoGrafo;
    }
}