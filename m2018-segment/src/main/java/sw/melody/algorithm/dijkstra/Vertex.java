package sw.melody.algorithm.dijkstra;

import lombok.Getter;
import java.util.HashMap;

/**
 * @author ping
 * @create 2019-05-06 16:48
 **/
@Getter
public class Vertex {
    private int id;
    private HashMap<Integer, Edge> edges;

    /**
     * Initialises the vertex and the empty set of edges.
     * @param id: the vertex ID.
     */
    public Vertex(int id) {
        this.id = id;
        this.edges = new HashMap<>();
    }


    /**
     * Return the edge from this vertex to the given
     * vertex if exists.
     * @param v (Vertex class) - The destination for the edge.
     * @return: The edge to the vertex or null.
     */
    public Edge getEdgeTo(Vertex v) {
        return this.edges.get(v.getId());
    }

    /**
     * Add the edge to the "Adjacency List"
     * @param v: The vertex this edge is connected to.
     * @param e: The edge between this vertex and the given vertex.
     */
    public void addEdge(Vertex v, Edge e) {
        this.edges.put(v.getId(), e);
    }
}
