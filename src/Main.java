import helper.Edge;
import helper.Move;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Graph G = new Graph(6);
        Player player = new Player();
        int color = -1;
        Random random = new Random();
        int source = random.nextInt(G.sizeOfGraph());
        int target = random.nextInt(G.sizeOfGraph());
        if (source == target) {
            if (source != 0) source -= 1;
            else source += 1;
        }
        System.out.println("Makers Move : (" + source + "," + target + ")");
        G.addEdge(source, target, color);
        Move move = null;
        while(!G.completeGraph()){
            color = color * -1;
            move = player.chooseMove(G, color);
            G.addEdge(move.getSource(), move.getTarget(), color);
            if (color == -1) {
                System.out.println("Makers Move : " + move.toString());
            } else
                System.out.println("Breakers Move : " + move.toString());
        }
        int foundEdge = 0;
        int exit = 1;
        for (Edge[] perfectMatches : G.getPerfectMatches()) {
            foundEdge = 0;
            for (Edge edge : perfectMatches) {
                if (G.isMakersEdge(edge.getSource(), edge.getTarget())) foundEdge++;
            }
            if (foundEdge == 3) {
                System.out.println("Perfect Match Found. Maker Wins with \n" + Arrays.toString(perfectMatches));
                exit = 0;
            }
        }
        if (exit == 1) System.out.println("Breaker Wins");
    }
}
