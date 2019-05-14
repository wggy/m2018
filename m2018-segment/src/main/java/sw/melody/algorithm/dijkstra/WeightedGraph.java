package sw.melody.algorithm.dijkstra;

import org.apache.commons.collections.BinaryHeap;

import java.util.*;

/**
 * @author ping
 * @create 2019-05-07 16:22
 **/

public class WeightedGraph {
    private class Vertex implements Comparable<Vertex> {
        private String vertexLabel;//顶点标识
        private List<Edge> adjEdges;//顶点的所有邻接边(点)
        private int dist;//顶点到源点的最短距离
        private Vertex preNode;//前驱顶点

        public Vertex(String vertexLabel) {
            this.vertexLabel = vertexLabel;
            adjEdges = new LinkedList<>();
            dist = Integer.MAX_VALUE;
            preNode = null;
        }

        @Override
        public int compareTo(Vertex v) {
            if (this.dist > v.dist) {
                return 1;
            }
            else if (this.dist < v.dist) {
                return -1;
            }
            return 0;
        }
    }

    private class Edge {
        private int weight;//边的权值(带权图)
        private Vertex endVertex;

        public Edge(int weight, Vertex endVertex) {
            this.weight = weight;
            this.endVertex = endVertex;
        }
    }

    private Map<String, Vertex> weightedGraph;//存储图(各个顶点)
    private Vertex startVertex;//单源最短路径的起始顶点


    //图的信息保存在文件中,从文件中读取成字符串graphContent
    public WeightedGraph(String graphContent) {
        weightedGraph = new LinkedHashMap<String, Vertex>();
        buildGraph(graphContent);//解析字符串构造图
    }

    private void buildGraph(String graphContent) {
        String[] lines = graphContent.split("\n");

        String startNodeLabel, endNodeLabel;
        Vertex startNode, endNode;
        int weight;
        for (int i = 0; i < lines.length; i++) {
            String[] nodesInfo = lines[i].split(",");
            startNodeLabel = nodesInfo[1];
            endNodeLabel = nodesInfo[2];
            weight = Integer.valueOf(nodesInfo[3]);

            endNode = weightedGraph.get(endNodeLabel);
            if (endNode == null) {
                endNode = new Vertex(endNodeLabel);
                weightedGraph.put(endNodeLabel, endNode);
            }

            startNode = weightedGraph.get(startNodeLabel);
            if (startNode == null) {
                startNode = new Vertex(startNodeLabel);
                weightedGraph.put(startNodeLabel, startNode);
            }
            Edge e = new Edge(weight, endNode);
            //对于无向图而言,起点和终点都要添加边
//            endNode.adjEdges.add(e);
            startNode.adjEdges.add(e);
        }
        startVertex = weightedGraph.get(lines[0].split(",")[1]);//总是以文件中第一行第二列的那个标识顶点作为源点
    }


    public void dijkstra() {
        PriorityQueue<Vertex> heap = new PriorityQueue<>();
        init(heap);//inital heap

        while (!heap.isEmpty()) {
            Vertex v = heap.remove();
            List<Edge> adjEdges = v.adjEdges;//获取v的所有邻接点
            for (Edge e : adjEdges) {
                Vertex adjNode = e.endVertex;
                //update
                if (adjNode.dist > e.weight + v.dist) {
                    adjNode.dist = e.weight + v.dist;
                    adjNode.preNode = v;
                }
            }//end for

            //更新之后破坏了堆序性质,需要进行堆调整,这里直接重新构造堆(相当于decreaseKey)
        }

    }

    private void init(PriorityQueue<Vertex> heap) {
        startVertex.dist = 0;//源点到其自身的距离为0
        for (Vertex v : weightedGraph.values()) {
            heap.add(v);
        }
    }

    public void showDistance() {
        for (Vertex v : weightedGraph.values()) {
            printPath(v);
            System.out.println();
            System.out.println("顶点 " + v.vertexLabel + "到源点" + startVertex.vertexLabel + " 的距离: " + v.dist);
        }
    }

    //打印源点到 end 顶点的 最短路径
    private void printPath(Vertex end) {
        if (end.preNode != null) {
            printPath(end.preNode);
        }
        System.out.print(end.vertexLabel + "--> ");
    }
}
