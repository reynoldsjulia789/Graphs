import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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

/** TODO: Should I move all methods that are the same between DWGraph, DUGraph, UUGraph, and UWGraph to an abstract Graph class and have these inherit them and only implement the methods that are different
 * Directed Weighted Graph
 */
public class DWGraph extends Graph
{
    private Digraph graph;
    private int     size;           // I would like to remove this as it is unnecessary, but it is in the assigment requirements...
    private double  mtxThreshold;   // matrix threshold
    private double  lstThreshold;   // list threshold

    /**
     * Constructor
     */
    public DWGraph()
    {
        this.graph = new AdjList();
        this.mtxThreshold = 0.5;
        this.lstThreshold = 0.25;
    }

    /**
     * Constructs graph from JSON file
     *
     * @param filepath path to JSON file
     */
    public DWGraph(String filepath)
    {
        this();

        try (Scanner fileReader = new Scanner(new File(filepath)))
        {
            parseJSON(readJSON(fileReader));
        }
        catch (FileNotFoundException caught)
        {
            System.out.println("DWGraph - An error occurred when attempting to read the file:  "
                    + caught.getMessage());
        }
    }

    /**
     * Parses the nodes and edges from the JSON and adds them to the current graph
     *
     * @param JSON String with the contents of the JSON file
     */
    private void parseJSON(String JSON)
    {
        String            node;
        String[]          nodeChunks, nodeAndEdges, edges, edgeAndWeight;
        ArrayList<String> nodes;

        JSON = jsonTrimmer(JSON);

        nodeChunks = JSON.split("},");

        for (String nodeChunk : nodeChunks)
        {
            nodeAndEdges = nodeChunk.split("[{]");
            node         = jsonTrimmer(nodeAndEdges[0].replace(":", ""));
            edges        = nodeAndEdges[1].split(",");

            for (String edge : edges)
            {
                edgeAndWeight = edge.split(":");

                add(node, jsonTrimmer(edgeAndWeight[0]), Double.parseDouble(edgeAndWeight[1].trim()));
            }
        }
    }

    private String jsonTrimmer(String jsonSubstring)
    {
        jsonSubstring = jsonSubstring.trim()
                .substring(1, jsonSubstring.length() - 1);

        return jsonSubstring;
    }

    /**
     * Reads a JSON file into a String.
     *
     * @param fileReader Scanner object wrapped around a file to be read
     * @return String holding the contents of the JSON
     */
    private String readJSON(Scanner fileReader)
    {
        StringBuilder builder;

        builder = new StringBuilder();

        while (fileReader.hasNext())
        {
            builder.append(fileReader.nextLine());
        }

        return builder.toString();
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
        return this.graph.size();
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
     * Populates current graph from a JSON file?
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
            convert(1);
        }

        if ((density < this.lstThreshold) && !(this.graph instanceof AdjList))
        {
            convert(0);
        }
    }

    /**
     * converts the graph stored as an adjacency list to an adjacency matrix
     * @param matrixType 0 for an adjacency list, 1 for an adjacency matrix
     */
    private void convert(int matrixType)
    {
        ArrayList<String> nodes, edges;
        Digraph           newGraph;

        nodes = this.graph.nodes();

        if (matrixType == 0)
        {
            newGraph = new AdjList(nodes);
        }
        else if (matrixType == 1)
        {
            newGraph = new AdjMatrix(nodes);
        }
        else
        {
            return; // invalid type
        }

        for (String node : nodes)
        {
            edges = this.graph.edges(node);

            for (String edge : edges)
            {
                 newGraph.add(node, edge, this.graph.weight(node, edge));
            }
        }

        this.graph = newGraph;
    }
}
