package maze;

import java.util.*;

public class Graph {
    List<Node> nodes;
    Node start;

    public Graph() {
        nodes = new ArrayList<>();
    }

    public void addNode(String label) {
        Node node = new Node(label);
        if (nodes.isEmpty()) {
            start = node;
        }

        nodes.add(node);
    }

    public void addNode(Node node) {
        if (nodes.isEmpty()) {
            start = node;
        }
        nodes.add(node);
    }

    public void addLink(Node from, Node to, int weight) {
        from.addLink(to, weight);
        to.addLink(from, weight);
    }

    public Graph buildMST() {
        // need to be completed
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Edge> queue = new PriorityQueue<>();

        Node current = start;
        visited.add(current);

        for (Edge edge : current.adjacencyList) {
            queue.add(edge);
        }


        while (!queue.isEmpty()) {
            if (visited.contains(current)) {
                continue;
            }
        }



        return null;
    }

    @Override
    public String toString() {
        return nodes.toString();
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        Node nodeA = new Node("A");
        graph.addNode(nodeA);
        Node nodeB = new Node("B");
        graph.addNode(nodeB);
        Node nodeC = new Node("C");
        graph.addNode(nodeC);

        graph.addLink(nodeA, nodeB, 1);
        graph.addLink(nodeB, nodeC, 1);
        graph.addLink(nodeC, nodeA, 1);

        System.out.println(graph);
    }
}

class Node {
    String label;
    List<Edge> adjacencyList;

    public Node(String label) {
        this.label = label;
        this.adjacencyList = new ArrayList<>();
    }

    public void addLink(Node other, int weight) {
        adjacencyList.add(new Edge(this, other, weight));
    }

    @Override
    public String toString() {
        return label + " " + adjacencyList;
    }
}

class Edge implements Comparable<Edge> {
    Node from;
    Node to;
    int weight;

    public Edge(Node from, Node to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return from.label + "->" + to.label + "[" + weight + "]";
    }

    @Override
    public int compareTo(Edge o) {
        return this.weight - o.weight;
    }
}
