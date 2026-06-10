/*
 * How did you implement your negative edge weights?
 *
 *
 * What are the benefits and detriments of your design decision?
 */

public interface Search
{
    /**
     *  String src, String dest, a double cost, the graph object, and String[] path consisting of the names of vertices on the shortest path from src to dest
     */
    public record Path(String src, String dest, double cost, Digraph graph, String[] path)
    {

    }
}
