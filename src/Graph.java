import helper.Edge;

import java.util.*;

public class Graph {

    //Class arrays for Graph
    private static int[][] B;               // 0 = no edge; 1 = red edge; -1 = blue edge
    private static boolean [] visited;
    private static int numCircles = 6;
    private static ArrayList<Edge> edges = new ArrayList<>();
    private static Set<Edge[]> perfectMatches = new HashSet<>();

    Graph(int N){                            // a constructor for a instance of the class with N vertices
        B = new int[N][N];
        visited = new boolean[B.length];
        initializeEdges(N);
        numCircles = N;
    }

    private void initializeEdges(int N){
        for(int vertex1 = 0; vertex1 < N; vertex1++){
            for (int vertex2 = 0; vertex2 < N; vertex2++) {
                if (vertex1 != vertex2)
                    edges.add(new Edge(vertex1, vertex2));
            }
        }

        for (Edge edge1 : edges) {
            for(Edge edge2 : edges){
                for(Edge edge3 : edges){
                    if(!(edge1.checkEdgeHasSameSourceOrDestinationVertex(edge2) || edge2.checkEdgeHasSameSourceOrDestinationVertex(edge3)
                            || edge1.checkEdgeHasSameSourceOrDestinationVertex(edge3))){
                        Edge[] perfectMatch = new Edge[]{edge1,edge2,edge3};
                        Arrays.sort(perfectMatch, new Comparator<Edge>() {
                            @Override
                            public int compare(Edge edge1, Edge edge2) {
                                return Integer.compare(edge1.getSource(), edge2.getSource());
                            }
                        });
                        boolean same = false;
                        for(Edge[] perfectMatch2: perfectMatches){
                            if(Arrays.equals(perfectMatch2,perfectMatch)){
                                same = true;
                            }
                        }
                        if(!same) perfectMatches.add(perfectMatch);
                    }
                }
            }
        }
    }

    public Set<Edge[]> getPerfectMatches() {
        return perfectMatches;
    }

    public void setPerfectMatches(Set<Edge[]> perfectMatches) {
        Graph.perfectMatches = perfectMatches;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        Graph.edges = edges;
    }

    public void addEdge(int u, int v, int w){       // add an edge from vertex u to v with value w (which in this hw will be  only 0, -1, or 1)
        B[u][v] = w;
        B[v][u] = w;
    }

    public void removeEdge(int u, int v){         // remove the edge from u to v and the (duplicate) edge from v to u
        B[u][v] = 0;
        B[v][u] = 0;
    }

    public int getEdge(int u, int v){               // return the value (-1, 0, or 1) of the edge that goes from u to v
        return B[u][v];
    }

    public boolean isEdge(int u, int v){            // return true or false depending on whether there is an edge (of either color) from u to v
        return B[u][v] == 1 || B[u][v] == -1;
    }

    public boolean isMakersEdge(int u, int v) {
        return B[u][v] == -1;
    }

    public int degree(int v){                       // return the number of edges of either color connected to vertex v
        int counter = 0;
        for(int i = 0; i < B[v].length; ++i){
            if(B[v][i] != 0){
                counter++;
            }
        }
        return counter;
    }

    public int degree(int v, int makerOrBreaker){                // return the number of edges of maker or breaker connected to vertex v
        int counter = 0;
        for(int i = 0; i < B[v].length; ++i){
            if(B[v][i] == makerOrBreaker){
                counter++;
            }
        }
        return counter;
    }

    public int sizeOfGraph(){
        return B.length;
    }

    public boolean isFull(){
        for(int i = 0; i < B.length; i++){
            if(degree(i) < B.length - 1)
                return false;
        }
        return true;
    }

    public void printEdges(){                       // print out the edge matrix, as shown above; this is only for debugging
        System.out.print("     ");
        for(int i = 1; i < B.length +1; ++i){
            System.out.print(i+"   ");
        }
        System.out.println();
        System.out.print("     ");
        for(int i = 0; i < B.length; ++i){
            System.out.print("-   ");
        }
        System.out.println();
        for(int i = 0; i < B.length; ++i){
            System.out.print((i+1)+" |  ");
            for(int n = 0; n < B.length; ++n){
                if(n<5 && B[i][n+1] == -1){
                    System.out.print(B[i][n]+"  ");
                }else{
                    System.out.print(B[i][n]+"   ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }



    public boolean completeGraph() {
        for(int r = 0; r < numCircles; ++r) {
            for(int c = 0; c < r; ++c) {
                if(!isEdge(r,c))
                    return false;
            }
        }
        return true;



    }
}