/*
 * Why did you select this graph type?
 * To explore the differences in implementation between a directed weighted graph and an undirected weighted one.
 *
 * How did you manipulate the inputs into the Digraph to achieve the implementation?
 * Main change I made was adding an edge between the src node and destination as well as the dest node and src node
 * on the add (deleting both on delete as well).
 *
 * Did you change your thresholds or algorithm for converting between a matrix and a list? If so, why?
 * I did not change my thresholds.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Undirected Weighted Graph
 */
public class UWGraph extends Graph
{
    /**
     * Constructor
     */
    public UWGraph()
    {
        super(true);
    }

    /**
     * Constructs graph from JSON file
     *
     * @param filepath path to JSON file
     */
    public UWGraph(String filepath)
    {
        UWGraph graph;

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
    public boolean add(String src, String dest, Double weight)
    {
        boolean result, result2;

        result  = this.graph.add(src, dest, weight);
        result2 = this.graph.add(dest, src, weight);

        convert();

        return (result && result2);
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
        this.graph.delete(dest, src);

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
    public static UWGraph load(String filepath)
    {
        UWGraph graph;

        graph = new UWGraph();

        try (Scanner fileReader = new Scanner(new File(filepath)))
        {
            parseJSON(readJSON(fileReader), graph);
        }
        catch (FileNotFoundException caught)
        {
            System.out.println("UWGraph - An error occurred when attempting to read the file:  "
                    + caught.getMessage());
        }

        return graph;
    }
}
