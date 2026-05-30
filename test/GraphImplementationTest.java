import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraphImplementationTest
{
    @Nested
    @DisplayName("DW Graph Tests")
    class DWGraphTests
    {
        @Test
        @DisplayName("Loads Graph From File")
        public void loadGraph()
        {
            DWGraph test;

            test = new DWGraph("jsonSamples/16-node-disjoint.json");

            assertEquals(16, test.size(), "Graph size");
        }
    }

    @Nested
    @DisplayName("Adjacency List Tests")
    class AdjListTests
    {
        @Test
        @DisplayName("List General Test")
        public void generaltest()
        {
            ArrayList<String> nodes;
            AdjList           test;
            int               idx, idx2;

            nodes = new ArrayList<>();

            for (idx = 1; idx < 6; idx++)
            {
                nodes.add("Node" + idx);
            }

            test = new AdjList(nodes);

            for (idx = 1; idx < 6; idx++)
            {
                for (idx2 = 1; idx2 < 6; idx2++)
                {
                    test.add("Node" + idx, "Node" + idx2, 1.0);
                }
            }

            assertEquals(5, test.size(), "Graph Size");
            assertEquals("[Node1, Node5, Node4, Node3, Node2]", test.nodes().toString(), "Nodes");
            assertEquals("[Node1, Node5, Node4, Node3, Node2]", test.edges("Node3").toString(), "Edges for Node3");
            assertEquals(1.25, test.density(), "Graph Density");

            test.delete("Node5");

            assertEquals(4, test.size(), "Graph Size after Delete");
            assertEquals("[Node1, Node4, Node3, Node2]", test.nodes().toString(), "Nodes after delete");

            test.delete("Node3", "Node1");

            assertEquals("[Node4, Node3, Node2]", test.edges("Node3").toString(), "Edges for Node3 after delete");
        }
    }

    @Nested
    @DisplayName("Adjacency Matrix Tests")
    class AdjMatrixTests
    {
        @Test
        @DisplayName("Matrix General Test")
        public void generaltest()
        {
            ArrayList<String> nodes;
            AdjMatrix         test;
            int               idx, idx2;

            nodes = new ArrayList<>();

            for (idx = 1; idx < 6; idx++)
            {
                nodes.add("Node" + idx);
            }

            test = new AdjMatrix(nodes);

            for (idx = 1; idx < 6; idx++)
            {
                for (idx2 = 1; idx2 < 6; idx2++)
                {
                    test.add("Node" + idx, "Node" + idx2, 1.0);
                }
            }

            assertEquals(5, test.size(), "Graph Size");
            assertEquals("[Node1, Node5, Node4, Node3, Node2]", test.nodes().toString(), "Nodes");
            assertEquals("[Node1, Node2, Node3, Node4, Node5]", test.edges("Node3").toString(), "Edges for Node3");
            assertEquals(1.25, test.density(), "Graph Density");

            test.delete("Node5");

            assertEquals(4, test.size(), "Graph Size after Delete");
            assertEquals("[Node1, Node4, Node3, Node2]", test.nodes().toString(), "Nodes after delete");

            test.delete("Node3", "Node1");

            assertEquals("[Node2, Node3, Node4]", test.edges("Node3").toString(), "Edges for Node3 after delete");
        }
    }
}
