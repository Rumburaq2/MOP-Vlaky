import java.util.*;

import static java.lang.System.exit;

public class vlaky {

    // Helper Edge class to describe edges in the graph
    static class Edge {
        int from, to, weight;

        public Edge(int f, int t, int w) {
            from = f;
            to = t;
            weight = w;
        }
    }

    // Helper method that performs a depth first search on the graph to give
    // us the topological ordering we want. Instead of maintaining a stack
    // of the nodes we see we simply place them inside the ordering array
    // in reverse order for simplicity.
    private static int dfs(
            int i, int at, boolean[] visited, int[] ordering, Map<Integer, List<Edge>> graph) {

        visited[at] = true;

        List<Edge> edges = graph.get(at);

        if (edges != null)
            for (Edge edge : edges) if (!visited[edge.to]) i = dfs(i, edge.to, visited, ordering, graph);

        ordering[i] = at;
        return i - 1;
    }

    // Finds a topological ordering of the nodes in a Directed Acyclic Graph (DAG)
    // The input to this function is an adjacency list for a graph and the number
    // of nodes in the graph.
    //
    // NOTE: 'numNodes' is not necessarily the number of nodes currently present
    // in the adjacency list since you can have singleton nodes with no edges which
    // wouldn't be present in the adjacency list but are still part of the graph!
    //
    public static int[] topologicalSort(Map<Integer, List<Edge>> graph, int numNodes) {

        int[] ordering = new int[numNodes];
        boolean[] visited = new boolean[numNodes];

        int i = numNodes - 1;
        for (int at = 0; at < numNodes; at++)
            if (!visited[at]) i = dfs(i, at, visited, ordering, graph);

        return ordering;
    }

    // A useful application of the topological sort is to find the shortest path
    // between two nodes in a Directed Acyclic Graph (DAG). Given an adjacency list
    // this method finds the shortest path to all nodes starting at 'start'
    //
    // NOTE: 'numNodes' is not necessarily the number of nodes currently present
    // in the adjacency list since you can have singleton nodes with no edges which
    // wouldn't be present in the adjacency list but are still part of the graph!
    //
    public static Integer[] dagShortestPath(Map<Integer, List<Edge>> graph, int start, int numNodes) {

        int[] topsort = topologicalSort(graph, numNodes);
        Integer[] dist = new Integer[numNodes];
        dist[start] = 0;

        for (int i = 0; i < numNodes; i++) {

            int nodeIndex = topsort[i];
            if (dist[nodeIndex] != null) {
                List<Edge> adjacentEdges = graph.get(nodeIndex);
                if (adjacentEdges != null) {
                    for (Edge edge : adjacentEdges) {

                        int newDist = dist[nodeIndex] + edge.weight;
                        if (dist[edge.to] == null) dist[edge.to] = newDist;
                        else dist[edge.to] = Math.min(dist[edge.to], newDist);
                    }
                }
            }
        }

        return dist;
    }

    // Example usage of topological sort
    public static void main(String[] args) {

        // Graph setup
        /*
        final int N = 7;
        Map<Integer, List<Edge>> graph = new HashMap<>();
        for (int i = 0; i < N; i++) graph.put(i, new ArrayList<>());
        graph.get(0).add(new Edge(0, 1, 3));
        graph.get(0).add(new Edge(0, 2, 2));
        graph.get(0).add(new Edge(0, 5, 3));
        graph.get(1).add(new Edge(1, 3, 1));
        graph.get(1).add(new Edge(1, 2, 6));
        graph.get(2).add(new Edge(2, 3, 1));
        graph.get(2).add(new Edge(2, 4, 10));
        graph.get(3).add(new Edge(3, 4, 5));
        graph.get(5).add(new Edge(5, 4, 7));

         */
        // Graph setup test 1
        /*
        final int N = 4;
        Map<Integer, List<Edge>> graph = new HashMap<>();
        for (int i = 0; i < N; i++) graph.put(i, new ArrayList<>());
        graph.get(2).add(new Edge(2, 0, 3));
        graph.get(1).add(new Edge(1, 0, 2));
        graph.get(3).add(new Edge(3, 1, 3));
         */


        /*
        final int N = 6;
        Map<Integer, List<Edge>> graph = new HashMap<>();
        for (int i = 0; i < N; i++) graph.put(i, new ArrayList<>());
        graph.get(5).add(new Edge(5, 0, 5));
        graph.get(5).add(new Edge(5, 2, 2));
        graph.get(4).add(new Edge(4, 1, 6));
        graph.get(4).add(new Edge(4, 0, 2));
        graph.get(3).add(new Edge(3, 1, 1));
        graph.get(2).add(new Edge(2, 3, 1));
         */

        /*
        final int N = 6;
        Map<Integer, List<Edge>> graph = new HashMap<>();
        for (int i = 0; i < N; i++) graph.put(i, new ArrayList<>());
        graph.get(3).add(new Edge(3, 1, 2));
        graph.get(1).add(new Edge(1, 2, 4));
        graph.get(3).add(new Edge(3, 5, 4));
        graph.get(5).add(new Edge(5, 0, 5));
        graph.get(0).add(new Edge(0, 4, 1));
         */

        /*
        final int N = 4;
        Map<Integer, List<Edge>> graph = new HashMap<>();
        for (int i = 0; i < N; i++) graph.put(i, new ArrayList<>());
        graph.get(1).add(new Edge(1, 2, 0));
        graph.get(2).add(new Edge(2, 3, 0));
        graph.get(3).add(new Edge(3, 1, 1));
         */

        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        int O = scanner.nextInt();

        //System.out.println(T);//pocet vlaku aka nodes
        //System.out.println(O);//pocet omezeni
        final int N = T+1;
        Map<Integer, List<Edge>> graph = new HashMap<>();
        for (int i = 0; i < T+1; i++) graph.put(i, new ArrayList<>());
        for (int k = 0; k < O; k++) {
            int A = scanner.nextInt();
            int B = scanner.nextInt();
            int m = scanner.nextInt();
            graph.get(B).add(new Edge(B, A, m));

            // Output the values of A, B, and m (for testing purposes)
            //System.out.println("A: " + A + ", B: " + B + ", m: " + m);
        }

        if (hasCycle(graph, N)) {
            System.out.print("nelze");
            exit(0);
        }

        int[] ordering = topologicalSort(graph, N);

        //nastavime departureTime u vsech nodes na 0
        int[] departureTime = new int[ordering.length];
        for (int j = 0; j < ordering.length; j++){
            departureTime[j] = 0;
        }

        //for every node u
        for (int i = 0; i < ordering.length; i++){
            int nodeU = ordering[i];
            //for each outgoing edge from u to v
            List<Edge> adjacentEdges = graph.get(nodeU);
            if (adjacentEdges != null) {
                for (Edge edge : adjacentEdges) {
                    departureTime[edge.to] = max(departureTime[edge.to], (departureTime[edge.from] + edge.weight));
                }
            }


        }

        // Prints: [6, 0, 5, 1, 2, 3, 4]
        // System.out.println("ordering");
        // System.out.println(Arrays.toString(ordering));

        //System.out.println("departure time");
        //System.out.println(Arrays.toString(departureTime));
        for (int p = 1; p < departureTime.length; p++) {
            System.out.print(departureTime[p] + " ");
        }
        exit(0);

    }

    private static int max(int i, int i1) {
        if (i > i1) return i;
        if (i1 > i) return i1;
        return i1;
    }


    public static boolean hasCycle(Map<Integer, List<Edge>> graph, int numNodes) {
        boolean[] visited = new boolean[numNodes];
        boolean[] recStack = new boolean[numNodes];

        // Call the DFS helper function for each node if not visited
        for (int node = 0; node < numNodes; node++) {
            if (!visited[node]) {
                if (dfsCycleCheck(node, visited, recStack, graph)) {
                    return true; // Cycle detected
                }
            }
        }
        return false; // No cycle found
    }

    private static boolean dfsCycleCheck(int node, boolean[] visited, boolean[] recStack,
                                         Map<Integer, List<Edge>> graph) {
        // Mark the current node as visited and add it to the recursion stack
        visited[node] = true;
        recStack[node] = true;

        // Process all adjacent edges
        if (graph.containsKey(node)) {
            for (Edge edge : graph.get(node)) {
                int neighbor = edge.to;

                // If the neighbor is in the recursion stack, there's a cycle
                if (recStack[neighbor]) {
                    return true;
                }
                // If the neighbor hasn't been visited, recurse on it
                if (!visited[neighbor]) {
                    if (dfsCycleCheck(neighbor, visited, recStack, graph)) {
                        return true;
                    }
                }
            }
        }

        // Remove the node from the recursion stack before returning
        recStack[node] = false;
        return false;
    }






}