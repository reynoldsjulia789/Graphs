import java.util.HashMap;

/*
 * How did you implement your negative edge weights?
 * I dealt with negative edge weights by storing a boolean in DWGraph that is changed from false to true if a negative
 * edge is added.
 *
 * What are the benefits and detriments of your design decision?
 * I considered having DWGraph contain a HashMap with lists of all negative edges entered into the graph.
 * This proved complicated to implement without possible bugs. I considered having Dijkstra's throw an exception
 * if it encounters a negative edge weight, then having DWGraph catch the exception and switch to BellmanFord.
 * This would work, but if a negative edge is found near the end of running Dijsktra's, that could possibly be quite
 * costly in run time for large graphs. I decided to use a boolean to just flag if there had been a negative weight
 * added at some point. While this is simple to implement and doesn't have the time cost of throwing an exception or
 * iterating through the entire graph to look for any existing negative edges, once a negative edge is added it will
 * forever be flagged for the graph, even if that negative edge is ever removed.
 */

/**
 * Search interface used in the implementation of the Strategy design pattern
 */
public interface Search
{
    /**
     * Finds the shortest path from the source to the destination.
     *
     * @param src the source node
     * @param dest the destination node
     * @param graph the graph the nodes are in
     * @return a record containing information about the shortest path, or null if src or dest don't exist in
     * the graph or there is no path
     */
    Path search(String src, String dest, Digraph graph);

    /**
     *  String src, String dest, a double cost, the graph object, and String[] path consisting of the names of vertices on the shortest path from src to dest
     */
    record Path(String src, String dest, double cost, Digraph graph, String[] path, HashMap<String, HashMap<String, Path>> allPaths) {}
}
