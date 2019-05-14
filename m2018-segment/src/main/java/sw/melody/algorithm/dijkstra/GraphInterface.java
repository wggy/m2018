package sw.melody.algorithm.dijkstra;

public interface GraphInterface {
    /**
     * Adds an edge with a lifetime between two vertices.
     * @param a - Index/ID of Vertex A in the edge.
     * @param b - Index/ID of Vertex B in the edge.
     * @param lifetime - Lifetime between vertex A and vertex B.
     */
    void addEdge(int a, int b, int lifetime);

    /**
     * Get the edges in the graph.
     * @return list of edges.
     */
    Edge[] edges();

    /**
     * Get the "max lifetime path" between two vertices, return
     * the path.
     * @param start: int/index of the vertex - The vertex to begin the path.
     * @param end: int/index of the vertex - The vertex to end the path.
     * @return: The list of edges in the path.
     */
    Edge[] lifetimePath(int start, int end);
}
