import java.util.ArrayList;
import java.util.HashMap;

/*
 * Reflection Questions: TODO answer questions
 * When should you use a list to represent a directed weighted graph?
 * Why?
 * Does this reasoning hold true for other types of graphs?
 */

public class AdjList implements Digraph
{
    private HashMap<String, HashMap<String, Double>> m_graph;

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

        if (m_graph.containsKey(key))
        {
            return false;
        }

        m_graph.put(key, new HashMap<>());

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

        if (!m_graph.containsKey(src))
        {
            m_graph.put(src, new HashMap<>());
        }

        outgoingNodes = m_graph.get(src);

        if (outgoingNodes.containsKey(dest))
        {
            return false;
        }

        outgoingNodes.put(dest, weight);

        return true;
    }

    /**
     * Deletes the specified node and all it's edges (both outbound and inbound edges).
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

        if (!m_graph.containsKey(key))
        {
            return null;
        }

        m_graph.remove(key); // remove node and its outgoing edges

        for (HashMap<String, Double> edges : m_graph.values()) // remove incoming edges to the node
        {
            if (edges != null)
            {
                edges.remove(key);
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
        Double                  deletedWeight;
        HashMap<String, Double> srcEdges;

        if (src == null || dest == null)
        {
            return null;
        }

        srcEdges = m_graph.get(src);

        if (srcEdges == null || srcEdges.isEmpty() || !srcEdges.containsKey(dest))
        {
            return null;
        }

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

        nodes = new ArrayList<>(m_graph.keySet());

        return nodes;
    }

    /**
     * Returns a list of all of the outbound edges from a node.
     * TODO ask if wanting both edge weight and connecting node or just connecting nodes
     *
     * @param key the queried node
     * @return ArrayList of String node names of connecting nodes, returns null if key doesn't
     * exist or is null
     */
    @Override
    public ArrayList<String> edges(String key)
    {
        ArrayList<String> edges;

        if (key == null || !m_graph.containsKey(key))
        {
            return null;
        }

        edges = new ArrayList<>(m_graph.get(key).keySet());

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

        if (src == null || dest == null || !m_graph.containsKey(src))
        {
            return null;
        }

        outEdges = m_graph.get(src);

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
        int totalNodes, totalEdges;

        totalNodes = m_graph.size();
        totalEdges = 0;

        if (totalNodes < 2)
        {
            return 0;
        }

        for (HashMap<String, Double> nodeEdges : m_graph.values())
        {
            totalEdges += nodeEdges.size();
        }

        return (double) totalEdges / (totalNodes * (totalNodes - 1));
    }

    /**
     * Calculates the unweighted density of a specific node
     *
     * @param key the node
     * @return the density of the node
     */
    @Override
    public double density(String key)
    {
        int totalPossible, totalEdges;

        if (key == null || !m_graph.containsKey(key))
        {
            return -1; // TODO should I return -1 or 0 if key doesn't exist
        }

        totalPossible = ((m_graph.size() - 1) * 2) + 1; // 2 * number of nodes other than self + 1 self edge
        totalEdges    = m_graph.get(key).size();

        for (HashMap<String, Double> node : m_graph.values())
        {
            if (node.containsKey(key))
            {
                totalEdges++;
            }
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
        return m_graph.size();
    }

    /**
     * A JSON serialization of the graph
     *
     * @return JSON String with the graph contents
     */
    @Override
    public String toJSON()
    {
        return "";
    }
}
