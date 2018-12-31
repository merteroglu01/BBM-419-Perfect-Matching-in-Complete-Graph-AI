import helper.Edge;
import helper.Move;
import helper.Players;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Graph G;
        Player player = new Player();
        int color;
        int foundEdge, exit, totalMakersWins = 0, totalBreakersWins = 0;
        Move move;
        for (int game = 0; game < 100; game++) {
            G = new Graph(6);
            color = -1;
            while (!G.completeGraph()) {
                if (color == -1) {
                    move = player.chooseMove(G, Players.COMP1);
                    color = 1;
                    //System.out.println("maker");
                } else {
                    move = player.chooseMove(G, Players.COMP2);
                    color = -1;
                    //System.out.println("breaker");
                }
                G.addEdge(move.getSource(), move.getTarget(), color);
                G.addEdge(move.getTarget(), move.getSource(), color);
                //System.out.println(move.toString());
                //System.out.println("--------");

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
                G.printEdges();
                totalBreakersWins++;
            }
        }

        System.out.println("Makers Wins : " + totalMakersWins + " Breakers Wins : " + totalBreakersWins);
    }
}
