package maze;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    List<Node> nodes = new ArrayList<>();
    Node start;

    public void addNode(String label) {
        Node node = new Node(label);
        addNode(node);
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

    public void addLink(String from, String to, int weight) {
        Node fromNode = findNode(from);
        Node toNode = findNode(to);

        if (fromNode == null || toNode == null) {
            throw new IllegalArgumentException();
        }

        addLink(fromNode, toNode, weight);
    }

    private Node findNode(String label) {
        return nodes.stream()
                .filter(node -> node.label.equals(label))
                .findAny()
                .orElse(null);
    }

    public Graph buildMST() {
        Graph mst = new Graph();
        Set<String> visited = new HashSet<>();
        int edgeCount = 0;

        mst.addNode(start.label);
        visited.add(start.label);

        PriorityQueue<Edge> queue = new PriorityQueue<>(start.adjacencyList);

        while (!queue.isEmpty() && edgeCount != nodes.size() - 1) {
            Edge edge = queue.poll();
            Node from = edge.from;
            Node current = edge.to;

            if (visited.contains(current.label)) {
                continue;
            }

            mst.addNode(current.label);
            mst.addLink(from.label, current.label, edge.weight);
            edgeCount++;

            visited.add(current.label);
            queue.addAll(current.adjacencyList);
        }

        return mst;
    }

    // Works correctly for MST only, not suitable to other graphs
    public int getMSTTotalWeight() {
        Set<String> visited = new HashSet<>();
        int totalWeight = 0;

        Queue<Edge> queue = new ArrayDeque<>(start.adjacencyList);
        visited.add(start.label);

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            Node current = edge.to;
            if (visited.contains(current.label)) {
                continue;
            }

            totalWeight += edge.weight;
            visited.add(current.label);
            queue.addAll(current.adjacencyList);
        }

        return totalWeight;
    }

    @Override
    public String toString() {
        return nodes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
    }
}

class Node {
    final String label;
    final List<Edge> adjacencyList = new ArrayList<>();

    public Node(String label) {
        this.label = label;
    }

    public void addLink(Node other, int weight) {
        adjacencyList.add(new Edge(this, other, weight));
    }

    @Override
    public String toString() {
        return label + ": " + adjacencyList;
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
        return from.label + " --" + weight + "-> " + to.label;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}
