package placesGraph;

import java.sql.Time;
import java.time.LocalTime;

public class Edge {
    private Vertex v1, v2;
    private LocalTime val;
    private boolean visited;

    public Edge(Vertex v1, Vertex v2){
        this.v1 = v1;
        this.v2 = v2;
    }
    public Edge(Vertex parent, Vertex child, LocalTime val){
        this(parent, child);
        this.val = val;
    }

    public LocalTime getVal() {
        return val;
    }
    public Vertex getV1() {
        return v1;
    }
    public Vertex getV2() {
        return v2;
    }
    public Vertex getOtherV(Vertex notTheOne){
        if(this.v1.equals(notTheOne))
            return this.v2;
        return this.v1;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    public boolean isVisited() {
        return visited;
    }
}
