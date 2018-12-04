import helper.Move;

import java.util.ArrayList;
import java.util.Random;

public class Player{
    //chooseMove - This method will utilize eval and minMax to decide what move
    // would be the best decision by the computer. It assesses different values for
    // every positibility of that the game could go to, and keep track of the best decision.
    public Move chooseMove(Graph G, int player) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        Random random = new Random();
        ArrayList<Move> bestMoves = new ArrayList<>();
        Move best = new Move(0, 1);

        for(int i = 0; i < G.sizeOfGraph(); i++){
            for(int j = i+1; j < G.sizeOfGraph(); j++){
                if(i != j && !G.isEdge(i, j)){
                    //System.out.println("(" + i + "," + j +")" + " are I and J");
                    G.addEdge(i, j, player);
                    Move m = new Move(i, j);
                    int val = minMax(G, 1, 0, 0);
                    //System.out.println(val + " | " + i +  " " + j + "player" + player);
                    if (player < 0) {
                        if (val >= max) {
                            bestMoves.add(m);
                            max = val;
                        }
                    } else {
                        if (val <= min) {
                            bestMoves.add(m);
                            min = val;
                        }
                    }


                    G.removeEdge(i, j);
                }
            }
        }
        return bestMoves.get(random.nextInt(bestMoves.size()));
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
    private int minMax(Graph G, int depth, int alpha, int beta) {
        if (G.isFull() || depth == 10)
            return eval(G); // stop searching and return eval
        else if(depth%2 == 0) {
            int val = -100000000;
            for(int i = 0; i < G.sizeOfGraph(); i++){
                for(int j = i+1; j < G.sizeOfGraph(); j++){
                    if(!G.isEdge(i, j)){
                        alpha = Math.max(alpha, val); // update alpha with max so far
                        if(beta < alpha) break; // terminate loop
                        G.addEdge(i, j, -1);
                        val = Math.max(val, minMax(G, depth + 1, alpha, beta));
                        G.removeEdge(i, j);
                    }
                }
            }
            return val;
        } else { // is a min node
            int val = 10000000;
            for(int i = 0; i < G.sizeOfGraph(); i++){
                for(int j = i+1; j < G.sizeOfGraph(); j++){
                    if(!G.isEdge(i, j)){
                        beta = Math.min(beta, val); // update beta with min so far
                        if(beta < alpha) break; // terminate loop
                        G.addEdge(i, j, 1);
                        val = Math.min(val, minMax(G, depth + 1, alpha, beta));
                        G.removeEdge(i, j);
                    }
                }
            }
            return val;
        }
    }
}