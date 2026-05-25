import java.util.ArrayList;
import java.util.HashMap;

/*
 * Reflection Questions:
 *
 * When should you use a list to represent a directed weighted graph?
 *      You should use a list to represent a directed weighted graph when the graph is sparse.
 *
 * Why?
 *      Operations on an adjacency list are slower for dense graphs than on an adjacency matrix, however adjacency
 *      lists take less space to store. So, if the graph is sparse, it makes more sense to use an adjacency list
 *      than a matrix since the operations will take a similar time on a sparse graph, but the list takes much less
 *      memory to store.
 *
 * Does this reasoning hold true for other types of graphs?
 *      Yes. Whether using a directed, undirected, weighted, or unweighted graph, if the graph is sparse, the adjacency
 *      list is a better choice than the adjacency matrix.
 */

public class AdjList implements Digraph
{
    private HashMap<String, HashMap<String, Double>> connections;
    private int                                      totalEdges;

    /**
     * Default Constructor
     */
    public AdjList()
    {
        this.connections = new HashMap<>();
        this.totalEdges  = 0;
    }

    /**
     * Constructs an adjacency list with the specified nodes (keys)
     *
     * @param nodes the nodes to add to the graph
     */
    public AdjList(ArrayList<String> nodes)
    {
        this();

        for (String node : nodes)
        {
            add(node);
        }
    }

    /**
     * Adds the specified node to the graph
     *
     * @param key the node to add
     * @return true if added, false if node is already in the graph
     */
    @Override
    public boolean add(String key)
    {
        if (key == null)
        {
            return false;
        }

        if (this.connections.containsKey(key))
        {
            return false;
        }

        this.connections.put(key, new HashMap<>());

        return true;
    }

    /**
     * Adds the specified edge to the graph. Adds the nodes if they don't already exist.
     *
     * @param src    the source node
     * @param dest   the destination node
     * @param weight the weight of the edge
     * @return true if added, false if edge is already in the graph or if src or dest are null
     */
    @Override
    public boolean add(String src, String dest, Double weight)
    {
        HashMap<String, Double> outgoingNodes;

        if (src == null || dest == null)
        {
            return false;
        }

        if (!this.connections.containsKey(src))
        {
            this.connections.put(src, new HashMap<>());
        }

        if (!this.connections.containsKey(dest))
        {
            this.connections.put(dest, new HashMap<>());
        }

        outgoingNodes = this.connections.get(src);

        if (outgoingNodes.containsKey(dest))
        {
            return false;
        }

        outgoingNodes.put(dest, weight);

        this.totalEdges++;

        return true;
    }

    /**
     * Deletes the specified node and all its edges (both outbound and inbound edges).
     *
     * @param key the node to delete
     * @return the name of the deleted node, or null if the node doesn't exist or key is null
     */
    @Override
    public String delete(String key)
    {
        if (key == null)
        {
            return null;
        }

        if (!this.connections.containsKey(key))
        {
            return null;
        }

        this.totalEdges -= this.connections.remove(key).size(); // remove node and its outgoing edges, subtract from total edge count

        for (HashMap<String, Double> edges : this.connections.values()) // remove incoming edges to the node
        {
            if (edges != null)
            {
                edges.remove(key);

                this.totalEdges--;
            }
        }

        return key;
    }

    /**
     * Deletes an edge from the graph.
     *
     * @param src  the source node
     * @param dest the destination node
     * @return the weight of the deleted edge, or null if it doesn't exist or src or dest are null
     */
    @Override
    public Double delete(String src, String dest)
    {
        HashMap<String, Double> srcEdges;

        if (src == null || dest == null)
        {
            return null;
        }

        srcEdges = this.connections.get(src);

        if (srcEdges == null || srcEdges.isEmpty() || !srcEdges.containsKey(dest))
        {
            return null;
        }

        this.totalEdges--;

        return srcEdges.remove(dest);
    }

    /**
     * Returns a list of the names of all the nodes in the graph
     *
     * @return ArrayList of String node names
     */
    @Override
    public ArrayList<String> nodes()
    {
        ArrayList<String> nodes;

        nodes = new ArrayList<>(this.connections.keySet());

        return nodes;
    }

    /**
     * Returns a list of all outbound edges from a node.
     *
     * @param key the queried node
     * @return ArrayList of String node names of connecting nodes, returns null if key doesn't
     * exist or is null
     */
    @Override
    public ArrayList<String> edges(String key)
    {
        ArrayList<String> edges;

        if (key == null || !this.connections.containsKey(key))
        {
            return null;
        }

        edges = new ArrayList<>(this.connections.get(key).keySet());

        return edges;
    }

    /**
     * Gets the weight of the specified edge
     *
     * @param src  the source node
     * @param dest the destination node
     * @return returns the weight of the edge if it exists, null if not or if src or dest is null
     */
    @Override
    public Double weight(String src, String dest)
    {
        HashMap<String, Double> outEdges;

        if (src == null || dest == null || !this.connections.containsKey(src))
        {
            return null;
        }

        outEdges = this.connections.get(src);

        if (!outEdges.containsKey(dest))
        {
            return null;
        }

        return outEdges.get(dest);
    }

    /**
     * Calculates the unweighted density of the entire graph
     *
     * @return the density of the graph
     */
    @Override
    public double density()
    {
        int totalNodes;

        totalNodes = this.connections.size();

        if (totalNodes < 2)
        {
            return 0;
        }

        return (double) this.totalEdges / (totalNodes * (totalNodes - 1));
    }

    /**
     * Calculates the unweighted density of a specific node
     *
     * @param key the node
     * @return the density of the node, -1 indicates failure
     */
    @Override
    public double density(String key)
    {
        int totalPossible, totalEdges;

        if (key == null || !this.connections.containsKey(key))
        {
            return -1;
        }

        totalPossible = this.connections.size() - 1; // count outgoing edges only, no self edges
        totalEdges    = this.connections.get(key).size();

        if (totalPossible < 1)
        {
            return 0;
        }

        return (double) totalEdges / totalPossible;
    }

    /**
     * The size of the graph
     *
     * @return the number of nodes in the graph
     */
    @Override
    public int size()
    {
        return this.connections.size();
    }

    /**
     * A human-readable String representation of the graph
     *
     * @return String representing the graph
     */
    @Override
    public String toString()
    {
        return  null;
        // TODO Implement human-readable string representation of graph
    }

    /** TODO implement JSON to string for adjlist
     * A JSON serialization of the graph
     *
     * @return JSON String with the graph contents
     */
    @Override
    public String toJSON()
    {
        return "Not Yet Implemented";
    }
}
