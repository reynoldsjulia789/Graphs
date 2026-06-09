/*
 * Why did you select this graph type?
 * To explore the differences in implementation between a directed and undirected unweighted graph.
 *
 * How did you manipulate the inputs into the Digraph to achieve the implementation?
 * I made two major changes. One was inputting a default weight of 1.0 for every edge since the graph is unweighted.
 * I also changed the weight method into a edgeExists method. The other change was adding an edge between the src node
 * and destination as well as the dest node and src node on the add (deleting both on delete as well).
 *
 * Did you change your thresholds or algorithm for converting between a matrix and a list? If so, why?
 * I did not change my thresholds
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Undirected Unweighted Graph
 */
public class UUGraph extends Graph
{
    private double  edgeExists;

    /**
     * Constructor
     */
    public UUGraph()
    {
        super(false);

        this.edgeExists   = 1.0;
    }

    /**
     * Constructs graph from JSON file
     *
     * @param filepath path to JSON file
     */
    public UUGraph(String filepath)
    {
        UUGraph graph;

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
        boolean result, result2;

        result  = this.graph.add(src, dest, this.edgeExists);
        result2 = this.graph.add(dest, src, this.edgeExists);

        convert();

        return (result && result2);
    }

    /**
     * Deletes an edge from the graph.
     *
     * @param src the source node
     * @param dest the destination node
     * @return true if an edge existed, false if not
     */
    public boolean delete(String src, String dest)
    {
        Double result;

        result = this.graph.delete(src, dest);
        this.graph.delete(dest, src);

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
    public static UUGraph load(String filepath)
    {
        UUGraph graph;

        graph = new UUGraph();

        try (Scanner fileReader = new Scanner(new File(filepath)))
        {
            parseJSON(readJSON(fileReader), graph);
        }
        catch (FileNotFoundException caught)
        {
            System.out.println("UUGraph - An error occurred when attempting to read the file:  "
                    + caught.getMessage());
        }

        return graph;
    }
}
