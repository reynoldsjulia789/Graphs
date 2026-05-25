import java.util.ArrayList;

/*
 * Reflection Questions:
 * What benefit does using a facade provide?
 *      It allows the user to make use of the functionality without worrying about what is happening 'under the hood.'
 *      In the case of the directed weighted graph, the user can simply create a graph and perform any operations they
 *      need to on the graph without having to worry about the actual implementation details of the graph.
 * What would happen if this file was omitted from the assignment specifications?
 *      The user would need to essentially create their own version of the directed weighted graph if they were wanting
 *      the same functionality from their graph. They would have to think about what structure to use to store the
 *      graph, when to switch, how to set up the graph, and everything else.
 */

/**
 * Directed Weighted Graph
 */
public class DWGraph
{
    private Digraph graph;
    private int     size;
    private double  mtxThreshold;
    private double  lstThreshold;

    /**
     * Constructor
     */
    public DWGraph()
    {
        this.graph = new AdjList();
        this.size  = 0;
        this.mtxThreshold = 0.6; // TODO determine good threshold & justification
        this.lstThreshold = 0.3;
    }

    /**
     * Constructs graph from JSON file
     *
     * @param filepath path to JSON file
     */
    public DWGraph(String filepath)
    {
        ArrayList<String> nodes;
        ArrayList<String> edges;

        // TODO read json file into graph
    }

    /**
     * Adds the specified node to the graph
     *
     * @param key the node to add
     * @return true if added, false if node is already in the graph
     */
    public boolean add(String key)
    {
        boolean result;

        result = this.graph.add(key);

        if (result)
        {
            this.size = this.graph.size();
        }

        return result;
    }

    /**
     * Adds the specified edge to the graph. Adds the nodes if they don't already exist.
     *
     * @param src the source node
     * @param dest the destination node
     * @param weight the weight of the edge
     * @return true if added, false if edge is already in the graph
     */
    public boolean add(String src, String dest, Double weight)
    {
        boolean result;

        result = this.graph.add(src, dest, weight);

        if (result)
        {
            this.size = this.graph.size();
        }

        convert();

        return result;
    }

    /**
     * Deletes the specified node and all its edges (both outbound and inbound edges).
     *
     * @param key the node to delete
     * @return the name of the deleted node, or null if the node doesn't exist
     */
    public String delete(String key)
    {
        String result;

        result = this.graph.delete(key);

        if (result != null)
        {
            this.size = this.graph.size();
        }

        convert();

        return result;
    }

    /**
     * Deletes an edge from the graph.
     *
     * @param src the source node
     * @param dest the destination node
     * @return the weight of the deleted edge, or null if it doesn't exist
     */
    public Double delete(String src, String dest)
    {
        Double result;

        result = this.graph.delete(src, dest);

        if (result != null)
        {
            this.size = this.graph.size();
        }

        convert();

        return result;
    }

    /**
     * Returns a list of the names of all the nodes in the graph
     *
     * @return ArrayList of String node names
     */
    public ArrayList<String> nodes()
    {
        return this.graph.nodes();
    }

    /**
     * Returns a list of all outbound edges from a node
     *
     * @param key the queried node
     * @return ArrayList of String node names of connecting nodes
     */
    public ArrayList<String> edges(String key)
    {
        return this.graph.edges(key);
    }

    /**
     * Gets the weight of the specified edge
     *
     * @param src the source node
     * @param dest the destination node
     * @return returns the weight of the edge if it exists, null if not
     */
    public Double weight(String src, String dest)
    {
        return this.graph.weight(src, dest);
    }

    /**
     * Calculates the unweighted density of the entire graph
     *
     * @return the density of the graph
     */
    public double density()
    {
        return this.graph.density();
    }

    /**
     * Calculates the unweighted density of a specific node
     *
     * @param key the node
     * @return the density of the node
     */
    public double density(String key)
    {
        return this.graph.density(key);
    }

    /**
     * The size of the graph
     *
     * @return the number of nodes in the graph
     */
    public int size()
    {
        return this.size;
    }

    /**
     * A human-readable String representation of the graph
     *
     * @return String representing the graph
     */
    public String toString()
    {
        return this.graph.toString();
    }

    /**
     * A JSON serialization of the graph
     *
     * @return JSON String with the graph contents
     */
    public String toJSON()
    {
        return this.graph.toJSON();
    }

    /**
     * Populates graph from a JSON file
     *
     * @param filepath the path to the JSON file
     * @return the created DWGraph
     */
    public static DWGraph load(String filepath)
    {
        return new DWGraph(filepath); // TODO is this valid???
    }

    /**
     * Swaps between adjacency list & adjacency matrix depending on graph density
     */
    private void convert()
    {
        double density;

        density = this.graph.density();

        if ((density > this.mtxThreshold) && !(this.graph instanceof AdjMatrix))
        {
            convertToMatrix();
        }

        if ((density < this.lstThreshold) && !(this.graph instanceof AdjList))
        {
            convertToList();
        }
    }

    /**
     * converts the graph stored as an adjacency list to an adjacency matrix
     */
    private void convertToMatrix()
    {
        ArrayList<String> nodes, edges;
        AdjMatrix adjMatrix;

        nodes     = this.graph.nodes();
        adjMatrix = new AdjMatrix(nodes);

        for (String node : nodes)
        {
            edges = this.graph.edges(node);

            for (String edge : edges)
            {
                adjMatrix.add(node, edge, this.graph.weight(node, edge));
            }
        }

        this.graph = adjMatrix;
    }

    /**
     * converts the graph stored as an adjacency matrix to an adjacency list
     */
    private void convertToList()
    {
        ArrayList<String> nodes, edges;
        AdjList adjList;

        nodes     = this.graph.nodes();
        adjList   = new AdjList(nodes);

        for (String node : nodes)
        {
            edges = this.graph.edges(node);

            for (String edge : edges)
            {
                adjList.add(node, edge, this.graph.weight(node, edge));
            }
        }

        this.graph = adjList;
    }
}
