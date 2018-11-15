package helper;

public class Move {
    private int source;
    private int target;

    public Move(int s, int t) {
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

    public String toString() {
        return "(" + source + "," + target + ")";
    }
}