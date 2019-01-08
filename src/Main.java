import helper.Edge;
import helper.Move;
import helper.Players;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Main {
    private static final int numCircles = 6;
    private static Graph G = new Graph(numCircles);
    private static JFrame frame;
    private static Color bg;
    private static JCanvas canvas;
    private static int centerX;
    private static int centerY;
    private static int radius;

    private static int diameter;

    private static int[] circlesX;    // location of vertices in camvas
    private static int[] circlesY;
    private static int[] xl;         // location of vertex labels
    private static int[] yl;

    private static int fontHeight = 14;
    private static int fontWidth = 10;

    public static void initDisplay() {

        frame = new JFrame("Perfect Matching Game");
        bg = new Color(255, 255, 220);
        canvas = new JCanvas();

        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setBackground(bg);
        frame.add(canvas);
        frame.setVisible(true);
        centerX = 300;
        centerY = 300;
        radius = 180;
        diameter = 20;

        circlesX = new int[numCircles];
        circlesY = new int[numCircles];
        xl = new int[numCircles];         // location of vertex labels
        yl = new int[numCircles];

        for (int i = 0; i < numCircles; ++i) {
            circlesX[i] = (int) (centerX - (diameter / 2) + radius * Math.cos(i * 2 * 3.1416 / numCircles));
            circlesY[i] = (int) (centerY - (diameter / 2) + radius * Math.sin(-i * 2 * 3.1416 / numCircles));
            xl[i] = (int) (centerX - (diameter / 2.0) - (fontWidth / 2) + (radius + 30) * Math.cos(i * 2 * 3.1416 / numCircles));
            yl[i] = (int) (centerY - (diameter / 2.0) + (fontHeight / 2) + (radius + 30) * Math.sin(-i * 2 * 3.1416 / numCircles));
        }

        canvas.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
        canvas.setStroke(new BasicStroke(2));

    }

    // update the display of the graph with whatever move has been made
    // edges with weight -1 (machine) will be drawn as blue, weight 1 (human)
    // as red

    public static void drawDisplay() {

        canvas.startBuffer();
        canvas.clear();

        canvas.setPaint(Color.BLACK);
        for (int i = 0; i < numCircles; ++i) {

            canvas.drawOval(circlesX[i] - (diameter / 2), circlesY[i] - (diameter / 2), diameter, diameter);
            canvas.drawString(i + "", xl[i], yl[i]);
        }

        for (int r = 0; r < numCircles; ++r) {
            for (int c = 0; c < r; ++c) {
                int e = G.getEdge(r, c);
                if (e != 0) {
                    if (e == 1)
                        canvas.setPaint(Color.RED);
                    else
                        canvas.setPaint(Color.BLUE);

                    canvas.drawLine(circlesX[r], circlesY[r],
                            circlesX[c], circlesY[c]);
                }
            }
        }

        canvas.endBuffer();

    }

    public static void main(String[] args) {
        initDisplay();
        drawDisplay();
        canvas.setPaint(Color.BLACK);
        Graph G;
        Player player = new Player();
        int color, moveCount;
        int foundEdge, exit, totalMakersWins = 0, totalBreakersWins = 0;
        Move move;
        for (int game = 0; game < 1; game++) {
            moveCount = 1;
            G = new Graph(6);
            color = -1;
            while (!G.completeGraph()) {
                if (color == -1) {
                    move = player.chooseMove(G, Players.COMP1, moveCount);
                    G.addEdge(move.getSource(), move.getTarget(), color);
                    color = 1;
                    //System.out.println("maker");[Edge = (0,2), Edge = (3,5), Edge = (4,1)]
                } else {
                    move = player.chooseMove(G, Players.COMP2, moveCount);
                    G.addEdge(move.getSource(), move.getTarget(), color);
                    color = -1;
                    //System.out.println("breaker");
                }
                drawDisplay();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (G.gameisOver()) {
                    canvas.drawString("Maker Won!", 100, 550);
                    break;
                }
                moveCount++;
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
                    System.out.println("Perfect Match Found. Maker Wins with \n" + Arrays.toString(perfectMatches) + " : " + totalBreakersWins);
                    //G.printEdges();
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
