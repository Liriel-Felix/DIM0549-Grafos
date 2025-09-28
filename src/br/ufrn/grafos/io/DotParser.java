package br.ufrn.grafos.io;

import br.ufrn.grafos.model.Graph;
import br.ufrn.grafos.model.GraphList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DotParser {
    public static Graph parseDotFile(String filename) throws IOException {
        boolean directed = false;
        String graphName = "";

        if (filename.contains("directed")) {
            directed = true;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("digraph")) {
                    directed = true;
                    break;
                } else if (line.startsWith("graph")) {
                    directed = false;
                    break;
                }
            }
        }

        Graph graph = new GraphList(directed);

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("//") || line.startsWith("#") ||
                        line.startsWith("graph") || line.startsWith("digraph") ||
                        line.equals("{") || line.equals("}")) {
                    continue;
                }

                if (line.contains("--") || line.contains("->")) {
                    String delimiter = directed ? "->" : "--";
                    String[] parts = line.split(delimiter);

                    if (parts.length == 2) {
                        String source = cleanVertexName(parts[0]);
                        String target = cleanVertexName(parts[1]);

                        graph.addEdge(source, target);
                    }
                }
            }
        }

        return graph;
    }

    private static String cleanVertexName(String vertex) {
        return vertex.trim()
                .replace(";", "")
                .replace("\"", "")
                .replace("[", " ")
                .split(" ")[0]
                .trim();
    }
}