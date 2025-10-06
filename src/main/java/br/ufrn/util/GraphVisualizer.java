package br.ufrn.util;

import br.ufrn.modelo.Grafo;
import br.ufrn.modelo.ResultadoBuscaBFS;
import br.ufrn.modelo.ResultadoBuscaDFS;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GraphVisualizer {
    public static void gerarImagemGrafo(Grafo grafo, String outputPath) throws IOException {
        MutableGraph mg = buildMutableGraphFromGrafo(grafo, null, null, null, null);
        renderToPng(mg, outputPath);
    }

    public static void gerarImagemBuscaBFS(Grafo grafo, ResultadoBuscaBFS resultado, String outputPath) throws IOException {
        Set<String> nosVisitados = resultado.getNosVisitados() == null ? Collections.emptySet() : resultado.getNosVisitados();
        Map<String, String> arestasCaminho = resultado.getArestasDoCaminho() == null ? Collections.emptyMap() : resultado.getArestasDoCaminho();
        Map<String, Integer> distancia = resultado.getDistancia() == null ? Collections.emptyMap() : resultado.getDistancia();

        MutableGraph mg = buildMutableGraphFromGrafo(grafo, nosVisitados, arestasCaminho, distancia, null);
        renderToPng(mg, outputPath);
    }

    public static void gerarImagemBuscaDFS(Grafo grafo, ResultadoBuscaDFS resultado, String outputPath) throws IOException {
        List<String> ordem = resultado.getOrdemVisitacao() == null ? Collections.emptyList() : resultado.getOrdemVisitacao();
        Map<String, String> arestasCaminho = resultado.getArestasDoCaminho() == null ? Collections.emptyMap() : resultado.getArestasDoCaminho();

        Map<String, Integer> ordemMap = new HashMap<>();
        for (int i = 0; i < ordem.size(); i++) {
            ordemMap.put(ordem.get(i), i + 1);
        }

        MutableGraph mg = buildMutableGraphFromGrafo(grafo, new HashSet<>(ordem), arestasCaminho, null, ordemMap);
        renderToPng(mg, outputPath);
    }

    private static MutableGraph buildMutableGraphFromGrafo(Grafo grafo, Set<String> nosParaColorir, Map<String, String> arestasDoCaminho, Map<String, Integer> distanciaMap, Map<String, Integer> ordemMap) {
        MutableGraph mg = guru.nidi.graphviz.model.Factory.mutGraph("grafo").setDirected(grafo.isDirected());

        Map<String, MutableNode> nodes = new HashMap<>();
        for (String v : grafo.getVerticesAsList()) {
            String label = v;
            if (distanciaMap != null && distanciaMap.containsKey(v)) {
                label = v + " (d=" + distanciaMap.get(v) + ")";
            } else if (ordemMap != null && ordemMap.containsKey(v)) {
                label = v + " (ord=" + ordemMap.get(v) + ")";
            }

            MutableNode mn = guru.nidi.graphviz.model.Factory.mutNode(v).add(Label.of(label));
            if (nosParaColorir != null && nosParaColorir.contains(v)) {
                mn.add(Color.rgb("ffd86e"));
                mn.add(Style.FILLED);
            }
            nodes.put(v, mn);
            mg.add(mn);
        }

        Map<String, Set<String>> lista = grafo.getListaAdjacencia();
        for (Map.Entry<String, Set<String>> entry : lista.entrySet()) {
            String origem = entry.getKey();
            Set<String> vizinhos = entry.getValue();
            MutableNode nOrigem = nodes.get(origem);
            if (nOrigem == null) continue;

            for (String dest : vizinhos) {
                MutableNode nDest = nodes.get(dest);
                if (nDest == null) continue;

                boolean isTreeEdge = false;
                if (arestasDoCaminho != null) {
                    String parentOfDest = arestasDoCaminho.get(dest);
                    if (parentOfDest != null && parentOfDest.equals(origem)) {
                        isTreeEdge = true;
                    }
                }

                if (isTreeEdge) {
                    nOrigem.addLink(nDest).add(Color.BLUE).add(Style.BOLD);
                } else {
                    nOrigem.addLink(nDest);
                }
            }
        }

        return mg;
    }

    private static void renderToPng(MutableGraph mg, String outputPath) throws IOException {
        File out = new File(outputPath);
        File parent = out.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();

        Graphviz.fromGraph(mg).render(Format.PNG).toFile(out);
        System.out.println("Imagem gerada em: " + out.getAbsolutePath());
    }
}
