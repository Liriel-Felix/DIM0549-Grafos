package br.ufrn.grafos.io;

import br.ufrn.grafos.model.Graph;
import br.ufrn.grafos.model.GraphList;
import br.ufrn.grafos.model.GraphMatrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public final class DotParser {

    private DotParser() {}

    public static Graph parseToList(String filename) throws IOException {
        return parse(filename, false);
    }

    public static Graph parseToMatrix(String filename) throws IOException {
        return parse(filename, true);
    }

    private static Graph parse(String filename, boolean asMatrix) throws IOException {
        boolean directed = false;
        Graph graph = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String raw;
            while ((raw = br.readLine()) != null) {
                String line = sanitize(raw);
                if (line.isEmpty()) continue;

                if (line.startsWith("digraph")) directed = true;
                if (line.startsWith("graph") || line.startsWith("digraph")) {
                    continue;
                }

                if (line.equals("{") || line.equals("}")) continue;

                if (line.startsWith("//") || line.startsWith("#")) continue;

                if (line.contains("->") || line.contains("--")) {
                    String delimiter = line.contains("->") ? "->" : "--";
                    String[] parts = line.split(delimiter);
                    for (int i = 0; i + 1 < parts.length; i++) {
                        String u = cleanVertexName(parts[i]);
                        String v = cleanVertexName(parts[i + 1]);
                        if (u.isEmpty() || v.isEmpty()) continue;

                        if (graph == null) graph = newGraph(asMatrix, directed);
                        graph.addVertex(u);
                        graph.addVertex(v);
                        graph.addEdge(u, v);
                    }
                    continue;
                }

                String maybeVertex = cleanVertexName(line);
                if (!maybeVertex.isEmpty()) {
                    if (graph == null) graph = newGraph(asMatrix, directed);
                    graph.addVertex(maybeVertex);
                }
            }
        }

        if (graph == null) {
            graph = asMatrix ? new GraphMatrix(false, 1) : new GraphList(false);
        }
        return graph;
    }

    private static Graph newGraph(boolean asMatrix, boolean directed) {
        return asMatrix ? new GraphMatrix(directed, 1) : new GraphList(directed);
    }

    private static String sanitize(String s) {
        String line = s.trim();
        int commentPos = Math.min(
                idxOrLen(line, "//"),
                idxOrLen(line, "#")
        );
        line = line.substring(0, commentPos).trim();
        return line;
    }

    private static int idxOrLen(String s, String token) {
        int i = s.indexOf(token);
        return (i >= 0) ? i : s.length();
    }

    private static String cleanVertexName(String v) {
        String s = v.trim();
        int attr = s.indexOf('[');
        if (attr >= 0) s = s.substring(0, attr);
        s = s.replace(";", "").replace("\"", "").trim();

        int sp = firstSpaceOrEnd(s);
        s = s.substring(0, sp).trim();
        return s;
    }

    private static int firstSpaceOrEnd(String s) {
        int i1 = s.indexOf(' ');
        int i2 = s.indexOf('\t');
        int i = (i1 == -1) ? i2 : (i2 == -1 ? i1 : Math.min(i1, i2));
        return (i == -1) ? s.length() : i;
    }
}