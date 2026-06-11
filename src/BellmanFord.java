import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class implementing BellmanFord algorithm to search a graph for the shortest path from
 * source to destination.
 */
public class BellmanFord implements Search
{
    /**
     * Constructor
     */
    public BellmanFord()
    {
        // do nothing
    }

    /**
     * Finds the shortest path from src to dest in a graph.
     *
     * @param src the starting node
     * @param dest the ending node
     * @param graph the graph to search
     * @return record containing information about the shortest path
     */
    public Path search(String src, String dest, Digraph graph)
    {
        HashMap<String, Double> distances;
        HashMap<String, String> predecessors;
        ArrayList<String>       nodes, edges;
        String[]                path;
        int                     idx, numVertices;
        double                  weight, newDist;
        Double                  totalCost, oldDist;
        boolean                 anyChange;

        if (src == null || dest == null || graph == null)
        {
            return null;
        }

        nodes        = graph.nodes();
        numVertices  = nodes.size();
        distances    = new HashMap<>();
        predecessors = new HashMap<>();

        if (!nodes.contains(src) || !nodes.contains(dest))
        {
            return null;
        }

        for (String node : nodes)
        {
            distances   .put(node, null);
            predecessors.put(node, null);
        }

        distances.put(src, 0.0);

        for (idx = 0; idx < (numVertices - 1); idx++)
        {
            anyChange = false;

            for (String node : nodes)
            {
                // Skip if the current vertex distance is unreachable
                if (distances.get(node) == null)
                {
                    continue;
                }

                edges = graph.edges(node);

                if (edges != null)
                {
                    for (String edge : edges)
                    {
                        weight  = graph.weight(node, edge);
                        newDist = distances.get(node) + weight;
                        oldDist = distances.get(edge);

                        if (oldDist == null || newDist < oldDist)
                        {
                            distances   .put(edge, newDist);
                            predecessors.put(edge, node);

                            anyChange = true;
                        }
                    }
                }
            }

            if (!anyChange)
            {
                break;
            }
        }

        totalCost = distances.get(dest);

        if (negativeCycle(graph, distances) || (totalCost == null))
        {
            return null;
        }

        path = assemblePath(predecessors, src, dest);

        return new Path(src, dest, totalCost, graph, path);
    }

    /**
     * Checks if graph contains negative weight cycle.
     *
     * @param graph the graph we are searching
     * @param distances distances from nodes
     * @return true if negative cycle exists, false if not
     */
    private boolean negativeCycle(Digraph graph, HashMap<String, Double> distances)
    {
        ArrayList<String> nodeList, edges;
        double            weight;

        nodeList = graph.nodes();

        for (String node : nodeList)
        {
            if (distances.get(node) == null)
            {
                continue;
            }

            edges = graph.edges(node);

            if (edges != null)
            {
                for (String edge : edges)
                {
                    weight = graph.weight(node, edge);

                    if (distances.get(node) + weight < distances.get(edge))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Assembles a String[] depicting the shortest path from source to destination.
     *
     * @param predecessors Map containing the backtrace from the search
     * @param src the source node
     * @param dest the destination node
     * @return String[] with the vertices of the path in order from source to destination.
     */
    private String[] assemblePath(Map<String, String> predecessors, String src, String dest) {
        ArrayList<String> path;
        String            curr;

        curr = dest;
        path = new ArrayList<>();

        while (curr != null)
        {
            path.add(curr);

            if (curr.equals(src))
            {
                break;
            }

            curr = predecessors.get(curr);
        }

        return path.reversed().toArray(new String[0]);
    }
}
