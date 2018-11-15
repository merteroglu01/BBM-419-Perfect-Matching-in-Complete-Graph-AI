package helper;

public class Edge {

    private int source;
    private int target;

    Edge(int s, int t) {
        source = s;
        target = t;
    }

    public String toString() {
        return "Edge = (" + source + "," + target + ")";
    }

    public boolean equals(Edge edge) {
        if(edge == this)
            return true;
        return (this.source != edge.source && this.target != edge.target && this.source != edge.target && this.target != edge.source);

    }
}
