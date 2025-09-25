# Trabalho 1 – DIM0549 Grafos (UFRN)

Implementação em **Java** dos algoritmos de **Busca em Largura (BFS)** e **Busca em Profundidade (DFS)**, conforme enunciado da disciplina DIM0549 – Grafos (Turma 2, 2025.2).

📄 O programa:
- Lê grafos direcionados ou não-direcionados no formato **DOT** (`.gv` / `.dot`).
- Executa **BFS** usando **matriz de adjacência**, iniciando pelo vértice com menor rótulo (ordem lexicográfica).
- Executa **DFS** usando **lista de adjacência**, cobrindo todo o grafo (floresta), respeitando ordem lexicográfica, e imprime tempos de **descoberta e término** de cada vértice.

---

## 📂 Estrutura do Projeto
```
TrabalhoGrafos/
├─ README.md
├─ samples/ # arquivos DOT de teste
│ ├─ undirected_graph.gv
│ └─ directed_graph.gv
└─ src/br/ufrn/grafos/
├─ app/
│ └─ Main.java # ponto de entrada
├─ io/
│ └─ DotParser.java # leitura de arquivo DOT
├─ model/
│ ├─ Graph.java # interface
│ ├─ GraphMatrix.java # matriz de adjacência (BFS)
│ └─ GraphList.java # lista de adjacência (DFS)
└─ algo/
├─ BFS.java # busca em largura
└─ DFS.java # busca em profundidade
```

 ## 📋To-do

1. Leitura de Arquivos DOT
- [ ] Escolher/implementar parser DOT (ex: graphviz-java ou parser próprio)
- [ ] Ler vértices e arestas do arquivo
- [ ] Identificar se o grafo é direcionado ou não

2. Representação do Grafo
- [ ] Classe para matriz de adjacência
- [ ] Classe para lista de adjacência
- [ ] Método para ordenação lexicográfica dos vértices

3. Algoritmo BFS
- [ ] Implementar BFS usando matriz de adjacência
- [ ] Identificar e usar o vértice de menor ordem lexicográfica como fonte
- [ ] Garantir que a iteração sobre os vizinhos siga a ordem lexicográfica
- [ ] Imprimir resultado da BFS

4. Algoritmo DFS
- [ ] Implementar DFS usando lista de adjacência
- [ ] Controle de tempos de início e fim para cada vértice
- [ ] Gerar e imprimir floresta DFS
- [ ] Ordenação lexicográfica no processamento

5. Saída e Formatação
- [ ] Formatar saída conforme exemplos do enunciado
- [ ] Exibir tempos de DFS no estilo Cormen (Seção 20.3)

6. Testes
- [ ] Testar com graphname.gv (não direcionado)
- [ ] Testar com digraphname.gv (direcionado)
- [ ] Testar grafo com componentes desconexos.
- [ ] Validar saída com as figuras do enunciado

7. Documentação e Entrega
- [ ] Comentar código
- [ ] Escrever README com instruções de execução