import java.util.ArrayList;
import java.util.HashMap;

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
     * @param src   the source node (Must input "<ALL>")
     * @param dest  the destination node (Must input "<ALL>")
     * @param graph the graph the nodes are in
     * @return a record containing information about the shortest paths, or null on failure
     */
    @Override
    public Path search(String src, String dest, Digraph graph)
    {
        ArrayList<String>                      nodes;
        HashMap<String, HashMap<String, Path>> allPaths;
        Path                                   individualPath;
        Integer[][]                            next;
        Double[][]                             dist;
        String[]                               path;
        String                                 node, edge, srcNode, destNode;
        Double                                 edgeWeight;
        double                                 newDist;
        int                                    source, destination, curr, nodeIdx, edgeIdx, numOfnodes;

        if (!src.equals("<ALL>") || !dest.equals("<ALL>"))
        {
            return null;
        }

        nodes      = graph.nodes();
        numOfnodes = nodes.size();

        // Initialize cost and next-node matrices
        dist = new Double[numOfnodes][numOfnodes];
        next = new Integer[numOfnodes][numOfnodes];

        for (nodeIdx = 0; nodeIdx < numOfnodes; nodeIdx++)
        {
            for (edgeIdx = 0; edgeIdx < numOfnodes; edgeIdx++)
            {
                node = nodes.get(nodeIdx);
                edge = nodes.get(edgeIdx);

                if (nodeIdx == edgeIdx)
                {
                    dist[nodeIdx][edgeIdx] = 0.0;
                }
                else
                {
                    edgeWeight = graph.weight(node, edge);

                    if (edgeWeight != null)
                    {
                        dist[nodeIdx][edgeIdx] = edgeWeight;
                        next[nodeIdx][edgeIdx] = edgeIdx;
                    }
                    else
                    {
                        dist[nodeIdx][edgeIdx] = null;
                    }
                }
            }
        }

        for (curr = 0; curr < numOfnodes; curr++)
        {
            for (source = 0; source < numOfnodes; source++)
            {
                for (destination = 0; destination < numOfnodes; destination++)
                {
                    if (dist[source][curr] != null && dist[curr][destination] != null)
                    {
                        newDist = dist[source][curr] + dist[curr][destination];

                        if ((dist[source][destination] == null) || (newDist < dist[source][destination]))
                        {
                            dist[source][destination] = newDist;
                            next[source][destination] = next[source][curr];
                        }
                    }
                }
            }
        }

        allPaths = new HashMap<>();

        for (source = 0; source < numOfnodes; source++)
        {
            srcNode = nodes.get(source);

            allPaths.put(srcNode, new HashMap<>());

            for (destination = 0; destination < numOfnodes; destination++)
            {
                destNode = nodes.get(destination);

                if (dist[source][destination] != null)
                {
                    path = assemblePath(source, destination, nodes, next);

                    individualPath = new Path
                            (
                                srcNode,
                                destNode,
                                dist[source][destination],
                                graph,
                                path,
                                null
                            );

                    allPaths.get(srcNode).put(destNode, individualPath);
                }
            }
        }

        return new Path(src, dest, null, graph, null, allPaths);
    }

    /**
     * Constructs a String[] of the nodes of the shortest path from source to destination.
     *
     * @param src the source node
     * @param dest the destination node
     * @param nodes a list of the nodes in the graph in the idx's corresponding to the 2D array row/column
     * @param next the 2D array of backtraces
     * @return String[] of the nodes of the shortest path
     */
    private String[] assemblePath(int src, int dest, ArrayList<String> nodes, Integer[][] next)
    {
        ArrayList<String> pathList;
        int               curr;

        if ((next[src][dest] == null) && (src != dest))
        {
            return new String[0];
        }

        pathList = new ArrayList<>();

        pathList.add(nodes.get(src));

        curr = src;

        while (curr != dest)
        {
            curr = next[curr][dest];

            pathList.add(nodes.get(curr));
        }

        return pathList.toArray(new String[0]);
    }
}
