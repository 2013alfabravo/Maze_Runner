package maze;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
class GraphTest {

    public static Graph graph = new Graph();

    @BeforeAll
    public static void init() {
        graph.addNode("A");     // "A" --6-- "B" --4-- "C"
        graph.addNode("B");     //  |         |         |
        graph.addNode("C");     //  3         2         12
        graph.addNode("D");     //  |         |         |
        graph.addNode("E");     // "D" --1-- "E" --7-- "F"
        graph.addNode("F");     //  |         |         |
        graph.addNode("G");     //  8         9         10
        graph.addNode("H");     //  |         |         |
        graph.addNode("I");     // "G" --11- "H" --5-- "I"

        graph.addLink("A", "B", 6);
        graph.addLink("A", "D", 3);
        graph.addLink("B", "C", 4);
        graph.addLink("B", "E", 2);
        graph.addLink("C", "F", 12);
        graph.addLink("D", "E", 1);
        graph.addLink("D", "G", 8);
        graph.addLink("E", "F", 7);
        graph.addLink("E", "H", 9);
        graph.addLink("F", "I", 10);
        graph.addLink("G", "H", 11);
        graph.addLink("H", "I", 5);

    }
    @Test
    void buildMST() {
        Graph mst = new Graph();
        mst.addNode("A");
        mst.addNode("D");
        mst.addNode("E");
        mst.addNode("B");
        mst.addNode("C");
        mst.addNode("F");
        mst.addNode("G");
        mst.addNode("H");
        mst.addNode("I");
        mst.addLink("A", "D", 3);
        mst.addLink("D", "E", 1);
        mst.addLink("D", "G", 8);
        mst.addLink("E", "F", 7);
        mst.addLink("E", "H", 9);
        mst.addLink("B", "E", 2);
        mst.addLink("B", "C", 4);
        mst.addLink("H", "I", 5);

        assertEquals(mst, graph.buildMST());
    }

    @Test
    void getTotalMSTWeight_Test() {
        assertEquals(39, graph.buildMST().getTotalGraphWeight(), "MST Weight is not correct");
    }
}