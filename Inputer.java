import java.util.*;

public class Inputer {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        int O = scanner.nextInt();

        System.out.println(T);
        System.out.println(O);
        final int N = O;
        Map<Integer, List<Edge>> graph = new HashMap<>();
        for (int i = 1; i <= T; i++) graph.put(i, new ArrayList<>());
        for (int k = 0; k < N; k++) {
            int A = scanner.nextInt();
            int B = scanner.nextInt();
            int m = scanner.nextInt();
            graph.get(B).add(new Edge(B, A, m));

            // Output the values of A, B, and m (for testing purposes)
            System.out.println("A: " + A + ", B: " + B + ", m: " + m);
        }
    }

    static class Edge {
        int from, to, weight;

        public Edge(int f, int t, int w) {
            from = f;
            to = t;
            weight = w;
        }
    }

}
