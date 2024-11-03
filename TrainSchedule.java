import java.util.*;

class Edge {
    int from;
    int to;
    int weight;

    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

public class TrainSchedule {

    public static Integer[] trainDepartureTimes(int numTrains, List<Edge> constraints, int startNode) {
        // Initialize departure times array, where each element is initially set to null (unknown time)
        Integer[] departureTimes = new Integer[numTrains];
        departureTimes[startNode] = 0; // Start node's departure time is 0

        // Run Bellman-Ford algorithm for numTrains - 1 iterations
        for (int i = 0; i < numTrains - 1; i++) {
            for (Edge edge : constraints) {
                if (departureTimes[edge.from] != null) { // Check if departure time of 'from' node is known
                    int newDepartureTime = departureTimes[edge.from] + edge.weight;
                    if (departureTimes[edge.to] == null || newDepartureTime > departureTimes[edge.to]) {
                        departureTimes[edge.to] = newDepartureTime;
                    }
                }
            }
        }

        // Check for negative cycles
        for (Edge edge : constraints) {
            if (departureTimes[edge.from] != null) {
                int newDepartureTime = departureTimes[edge.from] + edge.weight;
                if (departureTimes[edge.to] == null || newDepartureTime > departureTimes[edge.to]) {
                    // Negative cycle detected, which means the constraints are unsolvable
                    System.out.println("No feasible solution due to negative cycle.");
                    return null;
                }
            }
        }

        return departureTimes;
    }

    public static void main(String[] args) {
        // Example usage:
        List<Edge> constraints = new ArrayList<>();
        constraints.add(new Edge(1, 2, 4));   // Train 1 must depart at least 5 minutes after Train 0
        constraints.add(new Edge(2, 1, 4));  // Train 2 must depart at least -3 minutes after Train 1
        constraints.add(new Edge(1, 3, 2));   // Train 3 must depart at least 2 minutes after Train 2
        constraints.add(new Edge(3, 2, 1));   // Train 4 must depart at least 4 minutes after Train 3

        int numTrains = 5;
        int startNode = 0;  // Assume Train 0 starts at time 0

        Integer[] departureTimes = trainDepartureTimes(numTrains, constraints, startNode);
        if (departureTimes != null) {
            System.out.println("Departure Times:");
            for (int i = 0; i < departureTimes.length; i++) {
                System.out.println("Train " + i + ": " + departureTimes[i] + " minutes");
            }
        }
    }
}
