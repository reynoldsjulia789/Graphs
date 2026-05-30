import java.util.*;

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
            resize();
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

        if (this.keyMap.size() < (this.weights.length / 4)) // resize if array is less than 1/4 full
        {
            resize();
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
        Double deletedWeight;

        if (src == null || dest == null)
        {
            return null;
        }

        srcNode       = this.keyMap.get(src);
        destNode      = this.keyMap.get(dest);

        deletedWeight = this.weights[srcNode][destNode];

        if (deletedWeight != null)
        {
            this.weights[srcNode][destNode] = null;
            this.totalEdges--;
        }

        return deletedWeight;
    }

    /**
     * Resizes the 2D array to double the amount of existing nodes.
     */
    private void resize()
    {
        Double[][]     newGraph;
        int            numOfNodes, arraySize, edgeIdx, nodeIdx, availableIdx;

        compact();

        numOfNodes   = this.keyMap.size();
        arraySize    = numOfNodes * 2;
        newGraph     = new Double[arraySize][arraySize];

        for (nodeIdx = 0; nodeIdx < numOfNodes; nodeIdx++)
        {
            for (edgeIdx = 0; edgeIdx < numOfNodes; edgeIdx++)
            {
                newGraph[nodeIdx][edgeIdx] = this.weights[nodeIdx][edgeIdx];
            }
        }

        this.available.clear();

        for (availableIdx = arraySize - 1; availableIdx > numOfNodes - 1; availableIdx--)
        {
            this.available.push(availableIdx);
        }

        this.weights = newGraph;
    }

    /**
     * Compacts the graph by finding the lowest unused index and moving the highest used index to the lowest unused
     * index. When finished, all unused indices are clustered at the high end of the 2D array.
     */
    private void compact()
    {
        int           highestUsed, lowestUnused;
        List<Integer> unusedIdxs, usedIdxs;

        unusedIdxs = new ArrayList<>(this.available);
        usedIdxs   = new ArrayList<>(this.keyMap.values());

        if ((unusedIdxs.isEmpty()) || (usedIdxs.isEmpty()))
        {
            return;
        }

        unusedIdxs.sort(null);
        usedIdxs  .sort(null);

        lowestUnused = unusedIdxs.removeFirst();
        highestUsed  = usedIdxs  .removeLast();

        while (lowestUnused < highestUsed)
        {
            moveNode(highestUsed, lowestUnused);

            usedIdxs  .add(lowestUnused);
            unusedIdxs.add(highestUsed);

            usedIdxs  .sort(null);
            unusedIdxs.sort(null);

            lowestUnused = unusedIdxs.removeFirst();
            highestUsed  = usedIdxs  .removeLast();
        }
    }

    /**
     * Relocates a node in the 2D array by moving it from the first index to the second.
     *
     * @param from the index to copy from
     * @param to the index to copy to
     */
    private void moveNode(int from, int to)
    {
        int idx;

        for (idx = 0; idx < this.weights.length; idx++)
        {
            this.weights[idx][to] = this.weights[idx][from];
            this.weights[to][idx] = this.weights[from][idx];
        }

        this.keyMap.replace(lookupKey(from), to);

        this.available.remove(to);
        this.available.push(from);
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

        return this.totalEdges / (double) (totalNodes * (totalNodes - 1)); // current # of edges / total possible edges
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

        return totalEdges / (double) totalPossible;
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
        ArrayList<String> nodes, edges;
        StringBuilder     builder;

        builder = new StringBuilder();
        nodes   = nodes();

        for (String node : nodes)
        {
            edges = edges(node);

            builder.append(node)
                    .append(" -> [");

            for (String edge : edges)
            {
                builder.append(edge)
                        .append(": ")
                        .append(weight(node, edge))
                        .append(", ");
            }

            builder.delete(builder.length() - 2, builder.length())
                    .append("]  ");
        }

        return  builder.toString().trim();
    }
}
