package br.ufrn.grafos;

import br.ufrn.modelo.Grafo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;

public class DotReader {

    private static final Pattern COMMENT_LINE = Pattern.compile("//.*?$", Pattern.MULTILINE);
    private static final Pattern COMMENT_BLOCK = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL);

    public static Grafo read(File file) throws IOException {
        String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
        return parse(content);
    }

    public static Grafo read(InputStream in) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return parse(sb.toString());
        }
    }

    public static Grafo readResource(String resourcePath) throws IOException {
        InputStream in = DotReader.class.getResourceAsStream(resourcePath);
        if (in == null) throw new FileNotFoundException("Recurso não encontrado: " + resourcePath);
        return read(in);
    }

    private static Grafo parse(String raw) {
        String s = COMMENT_LINE.matcher(raw).replaceAll("");
        s = COMMENT_BLOCK.matcher(s).replaceAll("");

        boolean directed = s.matches("(?s).*\\bdigraph\\b.*") ;

        int start = s.indexOf('{');
        int end = s.lastIndexOf('}');
        String body = start >= 0 && end > start ? s.substring(start + 1, end) : s;

        String[] statements = body.split(";");
        Set<String> vertices = new TreeSet<>();
        List<String[]> arestasTemp = new ArrayList<>();

        for (String stmtRaw : statements) {
            String stmt = stmtRaw.trim();
            if (stmt.isEmpty()) continue;
            stmt = stmt.replaceAll("\\s+", " ").trim();

            String edgeOp = null;
            if (stmt.contains("->")) edgeOp = "->";
            else if (stmt.contains("--")) edgeOp = "--";

            if (edgeOp != null) {
                String[] tokens = stmt.split(Pattern.quote(edgeOp));
                List<String> cleaned = new ArrayList<>();
                for (String t : tokens) {
                    String[] parts = t.split(",");
                    for (String p : parts) {
                        String node = unquote(p.trim());
                        if (!node.isEmpty()) cleaned.add(node);
                    }
                }
                for (String v : cleaned) vertices.add(v);
                for (int i = 0; i + 1 < cleaned.size(); i++) {
                    arestasTemp.add(new String[]{cleaned.get(i), cleaned.get(i + 1)});
                }
            } else {
                String[] parts = stmt.split(",");
                for (String p : parts) {
                    String node = unquote(p.trim());
                    if (!node.isEmpty()) vertices.add(node);
                }
            }
        }

        Grafo g = new Grafo(directed);
        for (String v : vertices) {
            g.adicionarVertice(v);
        }
        g.finalizarEstruturas();

        for (String[] e : arestasTemp) {
            String o = e[0], d = e[1];
            if (!g.getVertices().contains(o)) { g.adicionarVertice(o); }
            if (!g.getVertices().contains(d)) { g.adicionarVertice(d); }
        }
        g.finalizarEstruturas();

        for (String[] e : arestasTemp) {
            try {
                g.adicionarAresta(e[0], e[1]);
            } catch (Exception ex) {
                System.err.println("Erro ao adicionar aresta: " + Arrays.toString(e) + " -> " + ex.getMessage());
            }
        }

        return g;
    }

    private static String unquote(String s) {
        if (s == null) return "";
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            return s.substring(1, s.length() - 1);
        }
        if (s.startsWith("'") && s.endsWith("'") && s.length() >= 2) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}