package sw.melody.algorithm.dijkstra;

import java.util.*;

/**
 * @author ping
 * @create 2019-05-06 16:49
 **/

public class LifetimeGraph implements GraphInterface {
    private HashMap<Integer, Vertex> adjacencyList;

    public LifetimeGraph() {
        this.adjacencyList = new HashMap<>();
    }


    @Override
    public void addEdge(int a, int b, int lifetime) {
        // 创建a、b点，先从集合判重，不存在则创建点
        Vertex aVertex, bVertex;
        if (this.adjacencyList.get(a) == null) {
            aVertex = new Vertex(a);
            this.adjacencyList.put(a, aVertex);
        } else {
            aVertex = this.adjacencyList.get(a);
        }
        if (this.adjacencyList.get(b) == null) {
            bVertex = new Vertex(b);
            this.adjacencyList.put(b, bVertex);
        } else {
            bVertex = this.adjacencyList.get(b);
        }

        // 构造ab边，
        Edge ab = new Edge(aVertex, bVertex, lifetime);

        // 添加点的连线，从当前点出发到下个点连线，是否存在
        if (aVertex.getEdgeTo(bVertex) == null) {
            aVertex.addEdge(bVertex, ab);
        }
        if (bVertex.getEdgeTo(aVertex) == null) {
            bVertex.addEdge(aVertex, ab);
        }
    }


    @Override
    public Edge[] edges() {
        Map<String, Edge> map = new LinkedHashMap<>();
        Set<Integer> keySet = this.adjacencyList.keySet();
        TreeSet<Integer> sortedSet = new TreeSet<>();
        sortedSet.addAll(keySet);
        Iterator<Integer> iterator = sortedSet.iterator();
        while (iterator.hasNext()) {
            Vertex vertex = this.adjacencyList.get(iterator.next());
            if (vertex != null) {
                Map<Integer, Edge> edgeMap = vertex.getEdges();
                TreeSet<Integer> sortedEdge = new TreeSet<>();
                sortedEdge.addAll(edgeMap.keySet());
                for (Integer item : sortedEdge) {
                    Edge edge = edgeMap.get(item);
                    map.put(edge.toString(), edge);
                }
            }
        }

        Edge[] array = new Edge[map.size()];
        int idx = 0;
        for (String key : map.keySet()) {
            array[idx++] = map.get(key);
        }
        return array;
    }




    @Override
    public Edge[] lifetimePath(int start, int end) {
        // TODO implement this
        Vertex startVertex = this.adjacencyList.get(start);
        if (startVertex == null) {
            return null;
        }
        Vertex endVertex = this.adjacencyList.get(end);
        if (endVertex == null) {
            return null;
        }
        Map<String, Integer> visitMap = new HashMap<>();

        for (Integer key : startVertex.getEdges().keySet()) {
            visitMap.put(start + "-" + key, startVertex.getEdges().get(key).getLifetime());
        }

        for (Integer key : startVertex.getEdges().keySet()) {
            Map<Integer, Edge> edgeMap = this.adjacencyList.get(key).getEdges();



        }

        Queue<Vertex> queue = new LinkedList<>();
        queue.offer(startVertex);

        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            for (Integer e : v.getEdges().keySet()) {
                if (e == Integer.MAX_VALUE) {
                    queue.offer(this.adjacencyList.get(e));
                }
            }
        }

//        compute(startVertex, endVertex, visitMap);
        System.out.println(visitMap);
        return null;
    }

    private void compute(Vertex startVertex, Vertex endVertex, Map<String, Integer> visitMap) {
        HashMap<Integer, Edge> startMap = startVertex.getEdges();
        for (Integer child : startMap.keySet()) {
            Vertex next = getNextVertex(startMap.get(child), startVertex, visitMap.keySet());
            if (next == null) {
                continue;
            }
            flag:
            for (String pathKey : visitMap.keySet()) {
                String newKey;
                if (child < startVertex.getId()) {
                    if (!visitMap.keySet().contains(child + "-" + startVertex.getId()) && pathKey.endsWith("-" + child)) {
                        newKey = pathKey + startVertex.getId();
                        visitMap.put(newKey, visitMap.get(pathKey) + startMap.get(child).getLifetime());
                        compute(next, endVertex, visitMap);
                        continue flag;
                    }
                } else {
                    if (!visitMap.keySet().contains(startVertex.getId() + "-" + child) && pathKey.endsWith("-" + startVertex.getId())) {
                        newKey = pathKey + child;
                        visitMap.put(newKey, visitMap.get(pathKey) + startMap.get(child).getLifetime());
                        compute(next, endVertex, visitMap);
                        continue flag;
                    }
                }

            }
            String newKey;
            if (child < startVertex.getId()) {
                newKey = child + "-" + startVertex.getId();
            } else {
                newKey = startVertex.getId() + "-" + child;
            }
            if (!visitMap.containsKey(newKey)) {
                visitMap.put(newKey, startMap.get(child).getLifetime());
            }
            compute(next, endVertex, visitMap);

        }
//        Vertex longestVertex = getLongestVertex(startVertex, visitMap);
//        if (longestVertex == null) {
//            return;
//        }
//
//        // 到达终点
//        if (longestVertex.getId() == endVertex.getId()) {
//            for (String key : visitMap.keySet()) {
//                if (key.endsWith("-" + startVertex.getId())) {
//                    visitMap.put(key + "-" + longestVertex.getId(), visitMap.get(key) + longestVertex.getEdgeTo(startVertex).getLifetime());
//                }
//            }
//            return;
//        }
//        Map<Integer, Edge> children = longestVertex.getEdges();
//        for (Integer child : children.keySet()) {
//            String key = getKey(child, longestVertex.getId(), visitMap.keySet());
//            if (!visitMap.containsKey(key)) {
//                String oldKey = getKey(longestVertex.getId(), startVertex.getId(), visitMap.keySet());
//                int newCompute = visitMap.get(oldKey) + children.get(child).getLifetime();
//                visitMap.put(key, newCompute);
//                compute(getNextVertex(children.get(child), longestVertex), endVertex, visitMap);
//            }
//        }
//        compute(startVertex, endVertex, visitMap);
    }

    private Vertex getNextVertex(Edge edge, Vertex start, Set<String> keySet) {

        for (String key : keySet) {
            if (edge.getA().getId() > edge.getB().getId()) {
                if (!key.contains(edge.getB().getId() + "-" + edge.getA().getId())) {
                    if (edge.getA().getId() == start.getId()) {
                        return edge.getB();
                    }
                    return edge.getA();
                }
            } else {
                if (!key.contains(edge.getA().getId() + "-" + edge.getB().getId())) {
                    if (edge.getA().getId() == start.getId()) {
                        return edge.getB();
                    }
                    return edge.getA();
                }
            }
        }
        if (edge.getA().getId() == start.getId()) {
            return edge.getB();
        }
        return edge.getA();
    }

    private Vertex getLongestVertex(Vertex vertex, Map<String, Integer> visitMap) {
        int lifetime = 0;
        Edge maxEdge = null;
        Map<Integer, Edge> startEdgesMap = vertex.getEdges();
        for (Integer child : startEdgesMap.keySet()) {
            String key = getKey(child, vertex.getId(), visitMap.keySet());
            if (!visitMap.containsKey(key)) {
                Edge edge = startEdgesMap.get(child);
                if (lifetime < edge.getLifetime()) {
                    lifetime = edge.getLifetime();
                    maxEdge = edge;
                }
            }
        }

        if (maxEdge == null) {
            return null;
        }

        String key = getKey(maxEdge.getA().getId(), maxEdge.getB().getId(), visitMap.keySet());
        visitMap.put(key, lifetime);
        if (maxEdge.getA().getId() == vertex.getId()) {
            return maxEdge.getB();
        } else {
            return maxEdge.getA();
        }
    }

    private String getKey(int child, int start, Set<String> keyset) {
        if (child > start) {
            int temp = child;
            child = start;
            start = temp;
        }
        for (String key : keyset) {
            if (!keyset.contains(child + "-" + start) && key.endsWith("-" + child)) {
                return key + "-" + start;
            }
        }
        return child + "-" + start;
    }


    public static void main(String[] args) {
        LifetimeGraph lifetimeGraph = new LifetimeGraph();
        // a
        lifetimeGraph.addEdge(1, 2, 2);
        lifetimeGraph.addEdge(1, 3, 1);

        // b
        lifetimeGraph.addEdge(2, 3, 3);
        lifetimeGraph.addEdge(2, 1, 2);

        // c
        lifetimeGraph.addEdge(3, 1, 1);
        lifetimeGraph.addEdge(3, 2, 3);
        lifetimeGraph.addEdge(3, 4, 4);

        // d
        lifetimeGraph.addEdge(4, 3, 4);
        lifetimeGraph.addEdge(4, 5, 2);
        lifetimeGraph.addEdge(4, 6, 5);

        // e
        lifetimeGraph.addEdge(5, 4, 2);
        lifetimeGraph.addEdge(5, 6, 7);

        // f
        lifetimeGraph.addEdge(6, 5, 7);
        lifetimeGraph.addEdge(6, 4, 5);


        Edge[] edges = lifetimeGraph.lifetimePath(1, 4);
    }
}
