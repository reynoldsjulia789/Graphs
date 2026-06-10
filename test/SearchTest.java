import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the graph search algorithms.
 */
public class SearchTest
{
    @Nested
    @DisplayName("Verify Dijkstra's works")
    class Dijkstras
    {
        @Test
        @DisplayName("Finds path simple adjList")
        public void simple()
        {
            Digraph   testGraph;
            Dijkstras dijkstras;

            testGraph = new AdjList();
            dijkstras = new Dijkstras();

            testGraph.add("src", "dest", 1.0);

            assertEquals("[src, dest]", dijkstras.search("src", "dest", testGraph));
        }
    }
}
