import java.util.ArrayList;
import java.util.HashMap;

/*
 * Reflection Questions: TODO answer questions
 * When should you use a matrix to represent a directed weighted graph?
 * Why?
 * Does this reasoning hold true for other types of graphs?
 */

public class AdjMatrix implements Digraph
{
    private Double[][]               m_graph;       // [from node][to node]
    private HashMap<String, Integer> m_lookup;

    /**
     * Add the specified node to the graph.
     *
     * @param key the node to add
     * @return true if added, false if already in graph or if key is null
     */
    @Override
    public boolean add(String key)
    {
        if (key == null)
        {
            return false;
        }

        if (m_lookup.containsKey(key))
        {
            return false;
        }

        // TODO determine what int to add, and if need to add to [][] now
        m_lookup.put(key, null);

        return true;
    }

    /**
     * Adds the specified edge to the graph. Adds the nodes if they do not already exist.
     *
     * @param src the source node
     * @param dest the destination node
     * @param weight the edge weight
     * @return true if edge was added successfully, false if edge is already in graph or
     * caller passes null source or destination nodes
     */
    @Override
    public boolean add(String src, String dest, Double weight)
    {
        Integer srcNode, destNode;

        if (src == null || dest == null)
        {
            return false;
        }

        srcNode  = m_lookup.get(src);
        destNode = m_lookup.get(dest);

        if (srcNode == null)
        {
            add(src);

            srcNode = m_lookup.get(src);
        }

        if (destNode == null)
        {
            add(src);

            destNode = m_lookup.get(dest);
        }

        if (m_graph[srcNode][destNode] != null)
        {
            return false;
        }

        m_graph[srcNode][destNode] = weight;

        return true;
    }

    /**
     * Deletes the specified node and all its outbound and inbound edges from the graph.
     *
     * @param key the node to delete
     * @return the String name of the node deleted, or null if not removed
     */
    @Override
    public String delete(String key)
    {
        int nodeLocation;

        if (key == null)
        {
            return null;                // TODO throw exception or return null if caller passes null key?
        }

        nodeLocation = m_lookup.remove(key);

        // TODO do I delete the row & column in the 2D array????

        return key;
    }

    /**
     * Deletes the specified edge from the graph.
     *
     * @param src the source node
     * @param dest the destination node
     * @return the weight of the deleted edge or null if the edge doesn't exist
     */
    @Override
    public Double delete(String src, String dest)
    {
        int    srcNode, destNode;
        double deletedWeight;

        if (src == null || dest == null)
        {
            return null;
        }

        srcNode       = m_lookup.get(src);
        destNode      = m_lookup.get(dest);

        deletedWeight = m_graph[srcNode][destNode];

        m_graph[srcNode][destNode] = null;

        return deletedWeight;
    }

    /**
     * Returns a list of the nodes in the graph.
     *
     * @return An ArrayList of the node names in the graph
     */
    @Override
    public ArrayList<String> nodes()
    {
        ArrayList<String> nodes;

        nodes = new ArrayList<>(m_lookup.keySet());

        return nodes;
    }

    /**
     * Lists all outgoing edges from a node.
     *
     * @param key the node
     * @return ArrayList of Strings with the names of the nodes to which the
     * key has an outgoing edge, returns null if key doesn't exist
     */
    @Override
    public ArrayList<String> edges(String key)
    {
        int src, dest;
        ArrayList<String> edges;

        if (key == null)
        {
            return null;            // TODO should I return null or throw an exception?
        }

        src   = m_lookup.get(key);
        edges = new ArrayList<>();

        for (dest = 0; dest < m_graph.length; dest++)
        {
            if (m_graph[src][dest] != null)
            {
                edges.addLast(null); // TODO how to get node name from lookup based on int location efficiently?
            }
        }

        return edges;
    }

    /**
     * Finds the weight of the edge from the source node to the destination node if the edge
     * exists. If not, returns null.
     *
     * @param src the source node
     * @param dest the destination node
     * @return Double representing the weight of the edge, or null if no edge exists
     */
    @Override
    public Double weight(String src, String dest)
    {
        int srcNode, destNode;

        if (src == null || dest == null)
        {
            return null;
        }

        srcNode  = m_lookup.get(src);
        destNode = m_lookup.get(dest);

        return m_graph[srcNode][destNode];
    }

    /**
     * Calculates the unweighted density of the entire graph.
     * TODO ask if my density calculation is correct
     *
     * @return the density of the graph
     */
    @Override
    public double density()
    {
        int    totalNodes, totalEdges;

        totalNodes = m_lookup.size();
        totalEdges = 0;

        if (m_graph == null || totalNodes < 2)
        {
            return 0;
        }

        for (Double[] node : m_graph)
        {
            for (Double edge : node)
            {
                if (edge != null)
                {
                    totalEdges++;
                }
            }
        }

        return (double) totalEdges / (totalNodes * (totalNodes - 1)); // current # of edges / total possible edges
    }

    /**
     * Calculates the unweighted density of the specified node.
     * TODO how to calculate the density of a specific node? Inbound and outbound edges?
     *
     * @param key the node to calculate the density of
     * @return the density of the node
     */
    @Override
    public double density(String key)
    {
        int     node, edge, totalEdges, totalPossible;

        if (key == null || !m_lookup.containsKey(key))
        {
            return -1;  // TODO should I return -1 or 0
        }

        node          = m_lookup.get(key);
        totalPossible = ((m_lookup.size() - 1) * 2) + 1; // 2 * number of nodes other than self + 1 self edge
        totalEdges    = 0;

        for (edge = 0; edge < m_graph.length; edge++)
        {
            if (m_graph[node][edge] != null)
            {
                totalEdges++;
            }

            if (edge != node && m_graph[edge][node] != null) // don't want to double count self edge
            {
                totalEdges++;
            }
        }

        return (double) totalEdges / totalPossible;
    }

    /**
     * Returns the size of the graph.
     *
     * @return int number of nodes in the graph
     */
    @Override
    public int size()
    {
        return m_lookup.size();
    }

    /**
     * Returns a String JSON serialization of the graph.
     *
     * @return String JSON representation of the graph
     */
    @Override
    public String toJSON()
    {
        return "";
    }
}
