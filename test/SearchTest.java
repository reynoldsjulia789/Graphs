import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the graph search algorithms.
 */
public class SearchTest
{
    @Nested
    @DisplayName("Verify Dijkstra's works")
    class VerifyDijkstras
    {
        @Test
        @DisplayName("Finds path simple adjList")
        public void simple()
        {
            Digraph     testGraph;
            Dijkstras   dijkstras;
            Search.Path shortestPath;

            testGraph    = new AdjList();

            testGraph.add("src", "dest", 1.0);

            dijkstras    = new Dijkstras();
            shortestPath = dijkstras.search("src", "dest", testGraph);

            assertEquals("[src, dest]", Arrays.toString(shortestPath.path()));
        }

        @Test
        @DisplayName("Finds path simple adjList")
        public void simple2()
        {
            Digraph     testGraph;
            Dijkstras   dijkstras;
            Search.Path shortestPath;

            testGraph    = new AdjList();

            testGraph.add("src", "v1", 0.5);
            testGraph.add("v1", "dest", 2.0);

            testGraph.add("src", "v2", 1.0);
            testGraph.add("v2", "dest", 1.0);

            dijkstras    = new Dijkstras();
            shortestPath = dijkstras.search("src", "dest", testGraph);

            assertEquals("[src, v2, dest]", Arrays.toString(shortestPath.path()));
            assertEquals(2.0, shortestPath.cost());
        }
    }
}
