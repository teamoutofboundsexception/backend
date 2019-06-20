package pl.gov.hackathon.teamoutofboundsexception.server.placesGraph;

public class Edge {
    private Vertex parent, child;
    private float val;
    private boolean visited;

    public Edge(Vertex parent, Vertex child){
        this.parent = parent;
        this.child = child;
    }
    public Edge(Vertex parent, Vertex child, float val){
        this(parent, child);
        this.val = val;
    }

    public float getVal() {
        return val;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    public boolean isVisited() {
        return visited;
    }
}
