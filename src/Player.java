import helper.Edge;
import helper.Move;

public class Player{
    //chooseMove - This method will utilize eval and minMax to decide what move
    // would be the best decision by the computer. It assesses different values for
    // every positibility of that the game could go to, and keep track of the best decision.
    public Move chooseMove(Graph G, int player) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        Move best = new Move(0, 1);

        for(int i = 0; i < G.sizeOfGraph(); i++){
            for(int j = i+1; j < G.sizeOfGraph(); j++){
                if(i != j && !G.isEdge(i, j)){
                    //System.out.println("(" + i + "," + j +")" + " are I and J");
                    G.addEdge(i, j, player);
                    Move m = new Move(i, j);
                    int val = minMax(G, 1, 0, 0, m, player);
                    //System.out.println(val + " | " + i +  " " + j + "player" + player);
                    if (player < 0) {
                        if (val >= max) {
                            //System.out.println("New best");
                            best = m;
                            max = val;
                        }
                    } else {
                        if (val <= min) {
                            //System.out.println("New best");
                            best = m;
                            min = val;
                        }
                    }


                    G.removeEdge(i, j);
                }
            }
        }
        //System.out.println(best);
        return best;
    }

    //eval - This method will assign values to each possibility within the tree and
    // dictate which move would be the best for the computer to make.
    private int eval(Graph G, Edge edge, int player) {
        int source = edge.getSource();
        int targert = edge.getTarget();
        int makerEdgeDegree = G.degree(source, -1) + G.degree(targert, -1);
        //System.out.println("Player = " + player + "makeredgedegree + " + makerEdgeDegree);
        int breakerDegree = G.degree(source, 1) + G.degree(targert, 1);
        //System.out.println("Player = " + player + "breakeredgedegree + " + breakerDegree);
        if (player < 0) return makerEdgeDegree * -1;
        return breakerDegree;
    }

    //minMax - This method will take in a couple of different parameters and construct
    // a tree with the different possibilities. This method will also preform alpha beta
    // pruning to make the tree traversing more efficient.
    private int minMax(Graph G, int depth, int alpha, int beta, Edge edge, int player) {
        if (G.isFull())
            return eval(G, edge, player); // stop searching and return eval
        else if(depth%2 == 0) {
            int val = -100000000;
            for(int i = 0; i < G.sizeOfGraph(); i++){
                for(int j = i+1; j < G.sizeOfGraph(); j++){
                    if(!G.isEdge(i, j)){
                        alpha = Math.max(alpha, val); // update alpha with max so far
                        if(beta < alpha) break; // terminate loop
                        G.addEdge(i, j, -1);
                        val = Math.max(val, minMax(G, depth + 1, alpha, beta, new Edge(i, j), player));
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
                        val = Math.min(val, minMax(G, depth + 1, alpha, beta, new Edge(i, j), player));
                        G.removeEdge(i, j);
                    }
                }
            }
            return val;
        }
    }
}