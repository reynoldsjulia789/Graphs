import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

/*
 * Reflection Questions:
 *
 * When should you use a matrix to represent a directed weighted graph?
 *      You should use a matrix to represent a directed weighted graph when the graph is dense.
 *
 * Why?
 *      The adjacency matrix performs operations much faster on a dense graph than an adjacency list does, however it
 *      takes up more space in memory to store it. So, it should be used for a dense graph to get the faster operation
 *      times, but when the graph is sparse, an adjacency list should be used instead.
 *
 * Does this reasoning hold true for other types of graphs?
 *      Yes. Whether using a directed, undirected, weighted, or unweighted graph, if the graph is dense, the adjacency
 *      matrix is a better choice than the adjacency list.
 */

public class AdjMatrix implements Digraph
{
    private int                      m_totalEdges;
    private Double[][]               m_graph;       // [from node][to node]
    private HashMap<String, Integer> m_lookup;
    private Stack<Integer>           m_available;   // empty spaces in 2D array

    /**
     * Constructs an adjacency matrix with the specified nodes
     *
     * @param nodes an array list of nodes to add to the matrix
     */
    public AdjMatrix(ArrayList<String> nodes)
    {
        int arraySize, idx;

        arraySize    = nodes.size() * 2;
        m_lookup     = new HashMap<>();
        m_graph      = new Double[arraySize][arraySize];
        m_available  = new Stack<>();
        m_totalEdges = 0;

        for (idx = (arraySize - 1); idx >= 0; idx--)
        {
            m_available.push(idx);
        }

        for (String node : nodes)
        {
            add(node);
        }
    }

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

        if (m_available.isEmpty())
        {
            resizeUp();
        }

        m_lookup.put(key, m_available.pop());

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
            add(dest);

            destNode = m_lookup.get(dest);
        }

        if (m_graph[srcNode][destNode] != null)
        {
            return false;
        }

        m_graph[srcNode][destNode] = weight;

        m_totalEdges++;

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
        int idx, nodeLocation;

        if (key == null)
        {
            return null;
        }

        nodeLocation = m_lookup.remove(key);

        for (idx = 0; idx < m_graph.length; idx++)
        {
            m_graph[nodeLocation][idx] = null;
            m_graph[idx][nodeLocation] = null;
        }

        m_available.push(nodeLocation);

        if (m_lookup.size() < (m_graph.length / 4)) // resize if array is less than 1/4 full
        {
            resizeDown();
        }

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
        m_totalEdges--;

        return deletedWeight;
    }

    /**
     * Resizes the 2D array to double the amount of existing nodes.
     * Use this method if the array size needs to be enlarged.
     */
    private void resizeUp()
    {
        Double[][]     newGraph;
        int            arraySize, edgeIdx, nodeIdx, availableIdx;

        if (!m_available.isEmpty())
        {
            return;
        }

        arraySize    = m_lookup.size() * 2;
        newGraph     = new Double[arraySize][arraySize];

        for (nodeIdx = 0; nodeIdx < m_graph.length; nodeIdx++)
        {
            for (edgeIdx = 0; edgeIdx < m_graph.length; edgeIdx++)
            {
                newGraph[nodeIdx][edgeIdx] = m_graph[nodeIdx][edgeIdx];
            }
        }

        for (availableIdx = arraySize - 1; availableIdx > m_graph.length - 1; availableIdx--)
        {
            m_available.push(availableIdx);
        }

        m_graph = newGraph;
    }

    /** TODO how to resize smaller without doing a ton of extra work
     * Resizes the 2D array to double the amount of existing nodes.
     * Use this method if the array size needs to be shrunk.
     */
    private void resizeDown()
    {
        Double[][] newGraph;
        int        arraySize, newIdx, oldIdx;

        arraySize    = m_lookup.size() * 2;
        newGraph     = new Double[arraySize][arraySize];
        newIdx       = 0;

        // how to resize array from large to small...

//        m_graph = newGraph;
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
        int               src, dest;
        ArrayList<String> edges;
        String            destNode;

        if (key == null)
        {
            return null;
        }

        src   = m_lookup.get(key);
        edges = new ArrayList<>();

        for (dest = 0; dest < m_graph.length; dest++)
        {
            if (m_graph[src][dest] != null)
            {
                destNode = null;

                for (String node : m_lookup.keySet())
                {
                    if (Objects.equals(m_lookup.get(node), dest))
                    {
                        destNode = node;
                        break;
                    }
                }

                edges.addLast(destNode);
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
     *
     * @return the density of the graph
     */
    @Override
    public double density()
    {
        int    totalNodes;

        totalNodes = m_lookup.size();

        if (m_graph == null || totalNodes < 2)
        {
            return 0;
        }

        return (double) m_totalEdges / (totalNodes * (totalNodes - 1)); // current # of edges / total possible edges
    }

    /**
     * Calculates the unweighted density of the specified node.
     *
     * @param key the node to calculate the density of
     * @return the density of the node, -1 indicates failure
     */
    @Override
    public double density(String key)
    {
        int     node, edge, totalEdges, totalPossible;

        if (key == null || !m_lookup.containsKey(key))
        {
            return -1;
        }

        node          = m_lookup.get(key);
        totalPossible = m_lookup.size() - 1; // self edge not counted
        totalEdges    = 0;

        if (totalPossible < 1)
        {
            return 0;
        }

        for (edge = 0; edge < m_graph.length; edge++)
        {
            if (m_graph[node][edge] != null)
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
