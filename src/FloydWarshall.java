
/**
 * A class implementing FloydWarshall algorithm to find all pairs shortest path.
 */
public class FloydWarshall implements Search
{
    /**
     * Constructor
     */
    public FloydWarshall()
    {
        // do nothing
    }

    /**
     * Finds the shortest path from the source to the destination.
     *
     * @param src   the source node
     * @param dest  the destination node
     * @param graph the graph the nodes are in
     * @return a record containing information about the shortest path, or null if src or dest don't exist in
     * the graph or there is no path
     */
    @Override
    public Path search(String src, String dest, Digraph graph)
    {
        if (!src.equals("<ALL>") || !dest.equals("<ALL>"))
        {
            return null;
        }

        return null;
    }
}
