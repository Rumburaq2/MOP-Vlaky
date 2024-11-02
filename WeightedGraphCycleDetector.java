import java.util.*;

public class WeightedGraphCycleDetector {

    // Edge class to represent an edge with a from node, to node, and weight
    static class Edge {
        int from, to, weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    // Function to detect cycle in a directed, weighted graph
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

    // Helper DFS function to detect a cycle
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

    // Example usage
    public static void main(String[] args) {
        // Define a sample weighted graph with 4 nodes
        /*
        Map<Integer, List<Edge>> graph = new HashMap<>();

        // Add edges to the graph
        graph.putIfAbsent(0, new ArrayList<>());
        graph.get(0).add(new Edge(0, 1, 10));

        graph.putIfAbsent(1, new ArrayList<>());
        graph.get(1).add(new Edge(1, 2, 5));

        graph.putIfAbsent(2, new ArrayList<>());
        graph.get(2).add(new Edge(2, 0, 2));  // Edge creating a cycle
        graph.get(2).add(new Edge(2, 3, 7));

        graph.putIfAbsent(3, new ArrayList<>());
        graph.get(3).add(new Edge(3, 3, 1));  // Self-loop on node 3, also a cycle
        
         */


        final int N = 6;
        Map<Integer, List<Edge>> graph = new HashMap<>();
        for (int i = 0; i < N; i++) graph.put(i, new ArrayList<>());
        graph.get(3).add(new Edge(3, 0, 2));
        graph.get(0).add(new Edge(0, 2, 3));
        graph.get(0).add(new Edge(0, 1, 1));
        graph.get(2).add(new Edge(2, 1, 4));
        graph.get(1).add(new Edge(1, 4, 2));
        //graph.get(4).add(new TopologicalSortAdjacencyList.Edge(4, 3, 5)); //cycle
        //graph.get(4).add(new Edge(4, 0, 5)); //cycle

        // Check for cycle
        if (hasCycle(graph, N)) {
            System.out.println("Cycle detected in the graph.");
        } else {
            System.out.println("No cycle detected in the graph.");
        }
    }
}
