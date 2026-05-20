import java.util.ArrayList;

/*
 * Reflection Questions: TODO answer questions
 * When did you choose to swap between the matrix and the list?
 * Explain and defend your threshold selection process.
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
     * Deletes the specified node and all it's edges (both outbound and inbound edges).
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
     * Returns a list of all of the outbound edges from a node.
     * TODO ask if wanting both edge weight and connecting node or just connecting nodes
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
