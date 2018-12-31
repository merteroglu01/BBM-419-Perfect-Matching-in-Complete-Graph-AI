import helper.Edge;
import helper.Move;
import helper.Players;

import java.util.ArrayList;
import java.util.Random;

public class Player{
    //chooseMove - This method will utilize eval and minMax to decide what move
    // would be the best decision by the computer. It assesses different values for
    // every positibility of that the game could go to, and keep track of the best decision.
    public Move chooseMove(Graph G, Players player) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        Random random = new Random();
        ArrayList<Move> bestMoves = new ArrayList<>();

        for(int i = 0; i < G.sizeOfGraph(); i++){
            for (int j = 0; j < G.sizeOfGraph(); j++) {
                if(i != j && !G.isEdge(i, j)){
                    //System.out.println("(" + i + "," + j +")" + " are I and J");
                    if (player == Players.COMP1) G.addEdge(i, j, -1);
                    else G.addEdge(i, j, 1);
                    Move m = new Move(i, j);
                    int val = minMax(G, player, 0, 0, 0);
                    //System.out.println(val + " | " + i +  " " + j + "player" + player);

                    if (player == Players.COMP1) {
                        if (val > max) {
                            System.out.println("new best max " + val + " " + max);
                            System.out.println(m);
                            bestMoves.clear();
                            bestMoves.add(m);
                            max = val;
                        } else if (val == max) {
                            System.out.println("best max");
                            System.out.println(m);
                            bestMoves.add(m);
                        }
                    } else {
                        if (val < min) {
                            System.out.println("new best min");
                            System.out.println(m);
                            bestMoves.clear();
                            bestMoves.add(m);
                            min = val;
                        } else if (val == min) {
                            System.out.println("best min");
                            System.out.println(m);
                            bestMoves.add(m);
                        }
                    }


                    G.removeEdge(i, j);
                }
            }
        }
        Move best = bestMoves.get(random.nextInt(bestMoves.size()));
        System.out.println("best return : " + best);
        return best;
    }
    private int eval2(Graph G){
        int maxMatching = 0;
        int foundEdge;
        for (Edge[] perfectMatches : G.getPerfectMatches()) {
            foundEdge = 0;
            for (Edge edge : perfectMatches) {
                if (G.isMakersEdge(edge.getSource(), edge.getTarget())) foundEdge++;
            }
            if(foundEdge > maxMatching)
                maxMatching = foundEdge;
        }
        if (maxMatching == 3) {
            return 100000000;
        } else return -100000000;

    }
    //eval - This method will assign values to each possibility within the tree and
    // dictate which move would be the best for the computer to make.
    private int eval(Graph G) {
        int i = 0;
        for (int j = 0; j < G.sizeOfGraph(); j++) {
            if (G.degree(j, 1) == 1)
                i += 1;
            if (G.degree(j, 1) > 1)
                i += 6;
            if (G.degree(j, -1) == 1)
                i -= 1;
            if (G.degree(j, -1) > 1)
                i -= 4;
        }
        return i;
    }

    //minMax - This method will take in a couple of different parameters and construct
    // a tree with the different possibilities. This method will also preform alpha beta
    // pruning to make the tree traversing more efficient.
    private int minMax(Graph G, Players player, int depth, int alpha, int beta) {
        //System.out.println("minmax");
        if (G.isFull() || depth == 10)
            return eval2(G); // stop searching and return eval
        else if (player == Players.COMP1) {
            int val = -10;
            for(int i = 0; i < G.sizeOfGraph(); i++){
                for(int j = i+1; j < G.sizeOfGraph(); j++){
                    if(!G.isEdge(i, j)){
                        alpha = Math.max(alpha, val); // update alpha with max so far
                        if (beta < alpha) return alpha; // terminate loop
                        G.addEdge(i, j, -1);
                        val = Math.max(val, minMax(G, Players.COMP2, depth + 1, alpha, beta));
                        G.removeEdge(i, j);
                    }
                }
            }
            return alpha;
        } else { // is a min node
            int val = 10;
            for(int i = 0; i < G.sizeOfGraph(); i++){
                for(int j = i+1; j < G.sizeOfGraph(); j++){
                    if(!G.isEdge(i, j)){
                        beta = Math.min(beta, val); // update beta with min so far
                        if (beta < alpha) return beta; // terminate loop
                        G.addEdge(i, j, 1);
                        val = Math.min(val, minMax(G, Players.COMP1, depth + 1, alpha, beta));
                        G.removeEdge(i, j);
                    }
                }
            }
            return beta;
        }
    }
}