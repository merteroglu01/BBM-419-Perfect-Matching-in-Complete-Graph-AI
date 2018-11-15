import helper.Edge;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Graph G = new Graph(6);
        for (Edge[] edges : G.getPerfectMatches()){
            System.out.println(Arrays.toString(edges));
        }
    }
}
