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

/**
 * Directed Weighted Graph
 */
public class DWGraph extends Graph
{
    /**
     * Constructor
     */
    public DWGraph()
    {
        super(true);
    }

    /**
     * Constructs graph from JSON file
     *
     * @param filepath path to JSON file
     */
    public DWGraph(String filepath)
    {
        DWGraph graph;

        this();

        graph = load(filepath);

        this.graph = graph.graph;
    }

    /**
     * Adds the specified edge to the graph. Adds the nodes if they don't already exist.
     *
     * @param src the source node
     * @param dest the destination node
     * @param weight the weight of the edge
     * @return true if added, false if edge is already in the graph
     */
    @Override
    public boolean add(String src, String dest, Double weight)
    {
        boolean result;

        result = this.graph.add(src, dest, weight);

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
     * Populates current graph from a JSON file?
     *
     * @param filepath the path to the JSON file
     * @return the created DWGraph
     */
    public static DWGraph load(String filepath)
    {
        DWGraph graph;

        graph = new DWGraph();

        try (Scanner fileReader = new Scanner(new File(filepath)))
        {
            parseJSON(readJSON(fileReader), graph);
        }
        catch (FileNotFoundException caught)
        {
            System.out.println("DWGraph - An error occurred when attempting to read the file:  "
                    + caught.getMessage());
        }

        return graph;
    }
}
