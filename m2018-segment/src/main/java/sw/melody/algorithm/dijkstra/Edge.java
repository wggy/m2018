package sw.melody.algorithm.dijkstra;

import lombok.Getter;

/**
 * @author ping
 * @create 2019-05-06 16:49
 **/
@Getter
public class Edge {
    private Vertex a;
    private Vertex b;
    private int lifetime;

    /**
     * Initialises the edge with two vertices
     * @param a - The vertex to connect the edge to.
     * @param b - The vertex to connect the edge to.
     * @param lifetime - The lifetime between two vertices
     */
    public Edge(Vertex a, Vertex b, int lifetime) {
        this.a = a;
        this.b = b;
        this.lifetime = lifetime;
    }

    /**
     * ***DO NOT CHANGE***
     * ToString method, allows the edge to be printed in the results.
     * @return String representation of the edge.
     */
    @Override
    public String toString() {
        return String.format("V%s-%d-V%s", this.a.getId(), this.lifetime, this.b.getId());
    }

}
