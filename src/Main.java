import helper.Move;

public class Main {

    public static void main(String[] args) {
        Graph G = new Graph(6);
        Player player = new Player();
        int color = 1;
        while(!G.completeGraph()){
            Move makersMove =  player.chooseMove(G,color);
            G.addEdge(makersMove.getSource(),makersMove.getTarget(), 1);
            System.out.println("Makers Move : " + makersMove.toString());
            Move breakerMove = player.chooseMove(G,color*-1);
            G.addEdge(breakerMove.getSource(),breakerMove.getTarget(),-1);
            System.out.println("Breaker's Move : " + breakerMove.toString());
        }
    }
}
