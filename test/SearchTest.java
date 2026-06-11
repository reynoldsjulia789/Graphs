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

    @Nested
    @DisplayName("Verify BellmanFord works")
    class VerifyBellmanFord
    {
        @Test
        @DisplayName("Finds path simple adjList")
        public void simple()
        {
            Digraph     testGraph;
            BellmanFord bellmanFord;
            Search.Path shortestPath;

            testGraph    = new AdjList();

            testGraph.add("src", "dest", 1.0);

            bellmanFord  = new BellmanFord();
            shortestPath = bellmanFord.search("src", "dest", testGraph);

            assertEquals("[src, dest]", Arrays.toString(shortestPath.path()));
        }

        @Test
        @DisplayName("Finds path simple adjList")
        public void simple2()
        {
            Digraph     testGraph;
            BellmanFord bellmanFord;
            Search.Path shortestPath;

            testGraph    = new AdjList();

            testGraph.add("src", "v1", 0.5);
            testGraph.add("v1", "dest", 2.0);

            testGraph.add("src", "v2", 1.0);
            testGraph.add("v2", "dest", 1.0);

            bellmanFord  = new BellmanFord();
            shortestPath = bellmanFord.search("src", "dest", testGraph);

            assertEquals("[src, v2, dest]", Arrays.toString(shortestPath.path()));
            assertEquals(2.0, shortestPath.cost());
        }
    }

    @Nested
    @DisplayName("Verify FloydWarshall works")
    class VerifyFloydWarshall
    {
        @Test
        @DisplayName("Finds path simple adjList")
        public void simple()
        {
            Digraph       testGraph;
            FloydWarshall floydWarshall;
            Search.Path   shortestPath;

            testGraph    = new AdjList();

            testGraph.add("src", "dest", 1.0);

            floydWarshall  = new FloydWarshall();
            shortestPath   = floydWarshall.search("<ALL>", "<ALL>", testGraph);

            assertEquals("[src, dest]", Arrays.toString(shortestPath.allPaths().get("src").get("dest").path()));
        }

        @Test
        @DisplayName("Finds path simple adjList")
        public void simple2()
        {
            Digraph     testGraph;
            FloydWarshall floydWarshall;
            Search.Path shortestPath;

            testGraph    = new AdjList();

            testGraph.add("src", "v1", 0.5);
            testGraph.add("v1", "dest", 2.0);

            testGraph.add("src", "v2", 1.0);
            testGraph.add("v2", "dest", 1.0);

            floydWarshall  = new FloydWarshall();
            shortestPath   = floydWarshall.search("<ALL>", "<ALL>", testGraph);

            assertEquals("[src, v2, dest]", Arrays.toString(shortestPath.allPaths().get("src").get("dest").path()));
            assertEquals(2.0, shortestPath.allPaths().get("src").get("dest").cost());
        }
    }

    @Nested
    @DisplayName("Verify DWGraph calls search")
    class VerifyDWGraphImplementation
    {
        @Test
        @DisplayName("Finds path FloydWarshall")
        public void fw()
        {
            DWGraph     testGraph;
            Search.Path shortestPath;

            testGraph    = new DWGraph();

            testGraph.add("src", "v1", 0.5);
            testGraph.add("v1", "dest", 2.0);

            testGraph.add("src", "v2", 1.0);
            testGraph.add("v2", "dest", 1.0);

            shortestPath   = testGraph.search("<ALL>", "<ALL>");

            assertEquals("[src, v2, dest]", Arrays.toString(shortestPath.allPaths().get("src").get("dest").path()));
            assertEquals(2.0, shortestPath.allPaths().get("src").get("dest").cost());
        }

        @Test
        @DisplayName("Finds path Dijkstra")
        public void d()
        {
            DWGraph     testGraph;
            Search.Path shortestPath;

            testGraph    = new DWGraph();

            testGraph.add("src", "v1", 0.5);
            testGraph.add("v1", "dest", 2.0);

            testGraph.add("src", "v2", 1.0);
            testGraph.add("v2", "dest", 1.0);

            shortestPath   = testGraph.search("src", "dest");

            assertEquals("[src, v2, dest]", Arrays.toString(shortestPath.path()));
            assertEquals(2.0, shortestPath.cost());
        }

        @Test
        @DisplayName("Finds path BellmanFord")
        public void bf()
        {
            DWGraph     testGraph;
            Search.Path shortestPath;

            testGraph    = new DWGraph();

            testGraph.add("src", "v1", -0.5);
            testGraph.add("v1", "dest", 2.0);

            testGraph.add("src", "v2", 1.0);
            testGraph.add("v2", "dest", 1.0);

            shortestPath   = testGraph.search("src", "dest");

            assertEquals("[src, v1, dest]", Arrays.toString(shortestPath.path()));
            assertEquals(1.5, shortestPath.cost());
        }
    }
}
