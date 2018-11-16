package helper;

public class Edge {

    private int source;
    private int target;

    public Edge() {

    }
    public Edge(int s, int t) {
        source = s;
        target = t;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object obj) {
        return this.source == ((Edge) obj).source && this.target == ((Edge) obj).target;
    }

    public String toString() {
        return "Edge = (" + source + "," + target + ")";
    }

    public boolean checkEdgeHasSameSourceOrDestinationVertex(Edge edge) {
        return (this.source == edge.source || this.target == edge.target || this.source == edge.target || this.target == edge.source);
    }
}
