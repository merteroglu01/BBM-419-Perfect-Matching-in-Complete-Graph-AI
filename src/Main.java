import helper.Edge;
import helper.Move;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Graph G;
        Player player = new Player();
        int color = -1;
        Random random = new Random();
        int source, target, foundEdge, exit, totalMakersWins = 0, totalBreakersWins = 0;
        Move move;
        for (int game = 0; game < 100; game++) {
            G = new Graph(6);
            source = random.nextInt(G.sizeOfGraph());
            target = random.nextInt(G.sizeOfGraph());
            if (source == target) {
                if (source != 0) source -= 1;
                else source += 1;
            }
            //System.out.println("Makers Move : (" + source + "," + target + ")");
            G.addEdge(source, target, color);
            while (!G.completeGraph()) {
                color = color * -1;
                move = player.chooseMove(G, color);
                G.addEdge(move.getSource(), move.getTarget(), color);
            /*if (color == -1) {
                System.out.println("Makers Move : " + move.toString());
            } else
                System.out.println("Breakers Move : " + move.toString());*/
            }
            exit = 1;
            for (Edge[] perfectMatches : G.getPerfectMatches()) {
                foundEdge = 0;
                for (Edge edge : perfectMatches) {
                    if (G.isMakersEdge(edge.getSource(), edge.getTarget())) foundEdge++;
                }
                if (foundEdge == 3) {
                    System.out.println("Perfect Match Found. Maker Wins with \n" + Arrays.toString(perfectMatches));
                    exit = 0;
                    totalMakersWins++;
                    break;
                }
            }
            if (exit == 1) {
                System.out.println("Breaker Wins");
                totalBreakersWins++;
            }
        }

        System.out.println("Makers Wins : " + totalMakersWins + " Breakers Wins : " + totalBreakersWins);
    }
}
