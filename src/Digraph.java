import java.util.ArrayList;

/*
 * Reflection Questions:
 * When did you choose to swap between the matrix and the list?
 * Explain and defend your threshold selection process.
 *
 * I decided to swap from a list to a matrix at .5 density and from a matrix to a list at .25 density.
 * The two thresholds need to not be equivalent, otherwise if you are adding and deleting right around the
 * threshold, you will be converting back and forth a bunch, which is a very time-consuming procedure.
 * .5 is right about where the graph goes from being considered more sparse to more dense as there are roughly
 * the same number of edges as there are vertices at that point. Since the matrix takes less time to perform operations,
 * it makes more sense to use it for denser graphs than the list even though it takes more memory. I decided to switch
 * back to a list if the density drops to .25 or below as the graph would then be quite sparse; it would make more
 * sense to use a list at that point to save space, and there are few enough edges that the difference in operation
 * time is negligible.
 */

public interface Digraph
{
    /**
     * Adds the specified node to the graph
     *
     * @param key the node to add
     * @return true if added, false if node is already in the graph
     */
    boolean add(String key);

    /**
     * Adds the specified edge to the graph. Adds the nodes if they don't already exist.
     *
     * @param src the source node
     * @param dest the destination node
     * @param weight the weight of the edge
     * @return true if added, false if edge is already in the graph
     */
    boolean add(String src, String dest, Double weight);

    /**
     * Deletes the specified node and all its edges (both outbound and inbound edges).
     *
     * @param key the node to delete
     * @return the name of the deleted node, or null if the node doesn't exist
     */
    String delete(String key);

    /**
     * Deletes an edge from the graph.
     *
     * @param src the source node
     * @param dest the destination node
     * @return the weight of the deleted edge, or null if it doesn't exist
     */
    Double delete(String src, String dest);

    /**
     * Returns a list of the names of all the nodes in the graph
     *
     * @return ArrayList of String node names
     */
    ArrayList<String> nodes();

    /**
     * Returns a list of all outbound edges from a node.
     *
     * @param key the queried node
     * @return ArrayList of String node names of connecting nodes
     */
    ArrayList<String> edges(String key);

    /**
     * Gets the weight of the specified edge
     *
     * @param src the source node
     * @param dest the destination node
     * @return returns the weight of the edge if it exists, null if not
     */
    Double weight(String src, String dest);

    /**
     * Calculates the unweighted density of the entire graph
     *
     * @return the density of the graph
     */
    double density();

    /**
     * Calculates the unweighted density of a specific node
     *
     * @param key the node
     * @return the density of the node
     */
    double density(String key);

    /**
     * The size of the graph
     *
     * @return the number of nodes in the graph
     */
    int size();

    /**
     * A human-readable String representation of the graph
     *
     * @return String representing the graph
     */
    String toString();

    /**
     * A JSON serialization of the graph
     *
     * @return JSON String with the graph contents
     */
    String toJSON();
}
