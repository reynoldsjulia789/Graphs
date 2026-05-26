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
    private int                      totalEdges;
    private Double[][]               weights;       // [from node][to node]
    private HashMap<String, Integer> keyMap;
    private Stack<Integer>           available;    // empty spaces in 2D array

    /**
     * Constructs an adjacency matrix with the specified nodes
     *
     * @param nodes an array list of nodes to add to the matrix
     */
    public AdjMatrix(ArrayList<String> nodes)
    {
        int arraySize, idx;

        arraySize       = nodes.size() * 2;
        this.keyMap     = new HashMap<>();
        this.weights    = new Double[arraySize][arraySize];
        this.available  = new Stack<>();
        this.totalEdges = 0;

        for (idx = (arraySize - 1); idx >= 0; idx--)
        {
            this.available.push(idx);
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

        if (this.keyMap.containsKey(key))
        {
            return false;
        }

        if (this.available.isEmpty())
        {
            resizeUp();
        }

        this.keyMap.put(key, this.available.pop());

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

        srcNode  = this.keyMap.get(src);
        destNode = this.keyMap.get(dest);

        if (srcNode == null)
        {
            add(src);

            srcNode = this.keyMap.get(src);
        }

        if (destNode == null)
        {
            add(dest);

            destNode = this.keyMap.get(dest);
        }

        if (this.weights[srcNode][destNode] != null)
        {
            return false;
        }

        this.weights[srcNode][destNode] = weight;

        this.totalEdges++;

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

        nodeLocation = this.keyMap.remove(key);

        for (idx = 0; idx < this.weights.length; idx++)
        {
            this.weights[nodeLocation][idx] = null;
            this.weights[idx][nodeLocation] = null;
        }

        this.available.push(nodeLocation);

        // TODO uncomment once resizeDown() works

//        if (this.keyMap.size() < (this.weights.length / 4)) // resize if array is less than 1/4 full
//        {
//            resizeDown();
//        }

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

        srcNode       = this.keyMap.get(src);
        destNode      = this.keyMap.get(dest);

        deletedWeight = this.weights[srcNode][destNode];

        this.weights[srcNode][destNode] = null;
        this.totalEdges--;

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

        if (!this.available.isEmpty())
        {
            return;
        }

        arraySize    = this.keyMap.size() * 2;
        newGraph     = new Double[arraySize][arraySize];

        for (nodeIdx = 0; nodeIdx < this.weights.length; nodeIdx++)
        {
            for (edgeIdx = 0; edgeIdx < this.weights.length; edgeIdx++)
            {
                newGraph[nodeIdx][edgeIdx] = this.weights[nodeIdx][edgeIdx];
            }
        }

        for (availableIdx = arraySize - 1; availableIdx > this.weights.length - 1; availableIdx--)
        {
            this.available.push(availableIdx);
        }

        this.weights = newGraph;
    }

    /**
     * Resizes the 2D array to double the amount of existing nodes.
     * Use this method if the array size needs to be shrunk.
     */
    private void resizeDown()
    {
        ArrayList<Integer> unusedIdxs;
        Double[][]         newGraph;
        int                arraySize, graphIdx, newIdx, oldIdx, idx;


        arraySize    = this.keyMap.size() * 2;
        newGraph     = new Double[arraySize][arraySize];
        unusedIdxs   = new ArrayList<>(this.available);

        available.clear();

        // find all used cells and copy them over
        for (newIdx = 0, oldIdx = 0; oldIdx < this.keyMap.size(); newIdx++, oldIdx++)
        {

            // find next used idx in old array
            while (unusedIdxs.contains(oldIdx))
            {
                oldIdx++;
            }

            // copy over cells..... I think this has issues if there are skipped rows/columns in the old graph TODO fix???
            for (graphIdx = 0; graphIdx <= newIdx; graphIdx++)
            {
                newGraph[newIdx][graphIdx] = this.weights[oldIdx][graphIdx];
                newGraph[graphIdx][newIdx] = this.weights[graphIdx][oldIdx];
            }

            // update the keymapping to ensure the node links to the correct row/column of weights
            if (newIdx != oldIdx)
            {
                this.keyMap.replace(lookupKey(oldIdx), newIdx);
            }
        }

        // add available indices to stack
        for (idx = arraySize - 1; idx >= newIdx; idx--)
        {
            this.available.push(idx);
        }

        this.weights = newGraph;
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

        nodes = new ArrayList<>(this.keyMap.keySet());

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

        src   = this.keyMap.get(key);
        edges = new ArrayList<>();

        for (dest = 0; dest < this.weights.length; dest++)
        {
            if (this.weights[src][dest] != null)
            {
                destNode = lookupKey(dest);

                edges.addLast(destNode);
            }
        }

        return edges;
    }

    /**
     * TODO is there a better way/faster way to do this?
     * Returns the key associated with the specified index in the 2D weights array.
     *
     * @param idx the index of the array to get the associated key for
     * @return the String key associated with that index of the array, null if not found
     */
    private String lookupKey(int idx)
    {
        if (idx < 0 || idx >= this.keyMap.size())
        {
            return null;
        }

        for (String node : this.keyMap.keySet())
        {
            if (Objects.equals(this.keyMap.get(node), idx))
            {
                return node;
            }
        }

        return null;
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

        srcNode  = this.keyMap.get(src);
        destNode = this.keyMap.get(dest);

        return this.weights[srcNode][destNode];
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

        totalNodes = this.keyMap.size();

        if (this.weights == null || totalNodes < 2)
        {
            return 0;
        }

        return (double) this.totalEdges / (totalNodes * (totalNodes - 1)); // current # of edges / total possible edges
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

        if (key == null || !this.keyMap.containsKey(key))
        {
            return -1;
        }

        node          = this.keyMap.get(key);
        totalPossible = this.keyMap.size() - 1; // self edge not counted
        totalEdges    = 0;

        if (totalPossible < 1)
        {
            return 0;
        }

        for (edge = 0; edge < this.weights.length; edge++)
        {
            if (this.weights[node][edge] != null)
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
        return this.keyMap.size();
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

    /**
     * Returns a String JSON serialization of the graph.
     *
     * @return String JSON representation of the graph
     */
    @Override
    public String toJSON()
    {
        return "";
        // TODO JSON serialization
    }
}
