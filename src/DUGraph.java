/*
 * Why did you select this graph type?
 * To explore the differences in implementation between weighted and unweighted directed graphs
 *
 * How did you manipulate the inputs into the Digraph to achieve the implementation?
 * The biggest change I made was inputting a default weight of 1.0 for every edge since the graph is unweighted.
 * I also changed the weight method into a edgeExists method.
 *
 * Did you change your thresholds or algorithm for converting between a matrix and a list? If so, why?
 * I did not change my thresholds.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Directed Unweighted Graph
 */
public class DUGraph extends Graph
{
    private double  edgeExists;

    /**
     * Constructor
     */
    public DUGraph()
    {
        super(false);

        this.edgeExists   = 1.0;            // This is an unweighted graph, so using a weight of 1 as a default weight to indicate there is an edge
    }

    /**
     * Constructs graph from JSON file
     *
     * @param filepath path to JSON file
     */
    public DUGraph(String filepath)
    {
        DUGraph graph;

        this();

        graph = load(filepath);

        this.graph = graph.graph;
    }

    /**
     * Adds the specified edge to the graph. Adds the nodes if they don't already exist.
     *
     * @param src the source node
     * @param dest the destination node
     * @return true if added, false if edge is already in the graph
     */
    public boolean add(String src, String dest)
    {
        boolean result;

        result = this.graph.add(src, dest, this.edgeExists);

        convert();

        return result;
    }

    /**
     * Deletes an edge from the graph.
     *
     * @param src the source node
     * @param dest the destination node
     * @return true if the edge existed, false if not
     */
    public boolean delete(String src, String dest)
    {
        Double result;

        result = this.graph.delete(src, dest);

        convert();

        return (result != null);
    }

    /**
     * Verifies if an edge exists between the specified nodes
     *
     * @param src the source node
     * @param dest the destination node
     * @return returns true if the edge exists, false if not
     */
    public boolean edgeExists(String src, String dest)
    {
        return Objects.equals(this.graph.weight(src, dest), this.edgeExists);
    }

    /**
     * Populates current graph from a JSON file?
     *
     * @param filepath the path to the JSON file
     * @return the created DWGraph
     */
    public static DUGraph load(String filepath)
    {
        DUGraph graph;

        graph = new DUGraph();

        try (Scanner fileReader = new Scanner(new File(filepath)))
        {
            parseJSON(readJSON(fileReader), graph);
        }
        catch (FileNotFoundException caught)
        {
            System.out.println("DUGraph - An error occurred when attempting to read the file:  "
                    + caught.getMessage());
        }

        return graph;
    }
}
