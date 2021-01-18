package maze;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private final List<Node> nodes = new ArrayList<>();
    private Node start;

    private static class Node {
        final String label;
        final List<Edge> adjacencyList = new ArrayList<>();

        private Node(String label) {
            this.label = label;
        }

        private void addLink(Node other, int weight) {
            adjacencyList.add(new Edge(this, other, weight));
        }

        @Override
        public String toString() {
            return label + ": " + adjacencyList;
        }
    }

    private static class Edge implements Comparable<Edge> {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;

            if (weight != edge.weight) return false;
            if (!this.from.label.equals(edge.from.label)) return false;
            return to.label.equals(edge.to.label);
        }

        @Override
        public int hashCode() {
            int result = from.hashCode();
            result = 31 * result + to.hashCode();
            result = 31 * result + weight;
            return result;
        }
    }

    public List<String> labels() {
        return nodes.stream().map(node -> node.label).collect(Collectors.toList());
    }

    // TODO ignores edge weights for now, needs to take them into account in a general case
    public Graph findPath(String from, String to) {
        assert findNode(from) != null && findNode(to) != null;

        Set<String> visited = new HashSet<>();
        Map<String, String> backtrack = new HashMap<>();
        Queue<Edge> queue = new ArrayDeque<>(findNode(from).adjacencyList);

        visited.add(from);
        while (!queue.isEmpty()) {
            Edge current = queue.poll();
            if (visited.contains(current.to.label)) {
                continue;
            }

            backtrack.put(current.to.label, current.from.label);

            if (to.equals(current.to.label)) {
                break;
            }

            queue.addAll(current.to.adjacencyList);
            visited.add(current.from.label);
        }

        List<Node> path = getBacktrackList(to, backtrack);
        Collections.reverse(path);

        return linkedListOf(path);
    }

    private Graph linkedListOf(List<Node> path) {
        Graph linkedList = new Graph();
        linkedList.addNode(path.get(0).label);
        for (int i = 1; i < path.size(); i++) {
            linkedList.addNode(path.get(i).label);
            linkedList.addLink(path.get(i).label, path.get(i - 1).label, 1);
        }
        return linkedList;
    }

    private List<Node> getBacktrackList(String end, Map<String, String> backtrack) {
        List<Node> endToStartPath = new ArrayList<>();
        String pointer = end;
        endToStartPath.add(new Node(pointer));

        while (true) {
            String prevNodeLabel = backtrack.get(pointer);
            if (prevNodeLabel == null) {
                break;
            }

            endToStartPath.add(new Node(prevNodeLabel));
            pointer = prevNodeLabel;
        }
        return endToStartPath;
    }

    public void addNode(String label) {
        Node node = new Node(label);
        if (nodes.isEmpty()) {
            start = node;
        }
        nodes.add(node);
    }

    @Override
    public boolean equals(Object obj) {
        List<Edge> graph1Edges = new ArrayList<>();
        for (Node node : nodes) {
            graph1Edges.addAll(node.adjacencyList);
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Graph graph2 = (Graph) obj;
        List<Edge> graph2Edges = new ArrayList<>();

        for (Node node : graph2.nodes) {
            graph2Edges.addAll(node.adjacencyList);
        }

        return graph1Edges.containsAll(graph2Edges);
    }

    public void addLink(String from, String to, int weight) {
        assert findNode(from) != null && findNode(to) != null;

        Node fromNode = findNode(from);
        Node toNode = findNode(to);

        fromNode.addLink(toNode, weight);
        toNode.addLink(fromNode, weight);
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

            if (visited.contains(edge.to.label)) {
                continue;
            }

            mst.addNode(edge.to.label);
            mst.addLink(edge.from.label, edge.to.label, edge.weight);
            edgeCount++;

            visited.add(edge.to.label);
            queue.addAll(edge.to.adjacencyList);
        }

        return mst;
    }

    public boolean isConnected(String from, String to) {
        assert findNode(from) != null && findNode(to) != null;

        return findNode(from).adjacencyList
                .stream()
                .anyMatch(edge -> edge.to.label.equals(to));
    }

    public int getTotalGraphWeight() {
        return nodes.stream()
                .flatMap(node -> node.adjacencyList.stream())
                .mapToInt(edge -> edge.weight)
                .sum() / 2;
    }

    @Override
    public String toString() {
        return nodes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
    }
}
