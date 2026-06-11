import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.HashSet;

/**
 * A class implementing Dijkstra's algorithm to search a graph for the shortest path from
 * source to destination.
 */
public class Dijkstras implements Search
{
    /**
     * Constructor
     */
    public Dijkstras()
    {
        // do nothing
    }

    /**
     * Puts all nodes of the graph into a hashmap
     *
     * @param nodes the nodes in the graph
     * @return a map of nodes in graph
     */
    private HashMap<String, Node> setup(ArrayList<String> nodes)
    {
        Node                  curr;
        HashMap<String, Node> nodeMap;

        nodeMap = new HashMap<>();

        for (String node : nodes)
        {
            curr = new Node(node);

            nodeMap.put(node, curr);
        }

        return nodeMap;
    } // end of setup

    /**
     * Finds the shortest path from the source to the destination.
     *
     * @param src the source node
     * @param dest the destination node
     * @param graph the graph the nodes are in
     * @return a record containing information about the shortest path, or null if src or dest don't exist in
     * the graph or there is no path
     */
    public Path search(String src, String dest, Digraph graph)
    {
        HashSet<String>       knownNodes;
        PriorityQueue<Node>   unknownNodes;
        HashMap<String, Node> nodeMap;
        Node                  curr, source, lowestCost;
        double                cost;

        if (src == null || dest == null || graph == null)
        {
            return null;
        }

        nodeMap = setup(graph.nodes());

        if (nodeMap.isEmpty() || !nodeMap.containsKey(src) || !nodeMap.containsKey(dest))
        {
            return null;
        }

        unknownNodes = new PriorityQueue<>();
        knownNodes   = new HashSet<>();
        source       = nodeMap.get(src);
        source.cost  = 0;

        unknownNodes.add(source);

        while (true)
        {
            lowestCost = unknownNodes.poll();

            if (lowestCost == null)
            {
                break;
            }

            knownNodes.add(lowestCost.name);

            if (lowestCost.name.equals(dest))
            {
                return new Path(src, dest, lowestCost.cost, graph, assemblePath(lowestCost));
            }

            for (String edge : graph.edges(lowestCost.name))
            {
                if (!knownNodes.contains(edge))
                {
                    curr = nodeMap.get(edge);

                    cost = lowestCost.cost + graph.weight(lowestCost.name, edge);

                    if (cost < curr.cost)
                    {
                        curr.cost        = cost;
                        curr.backtrace   = lowestCost;
                        curr.nodesInPath = lowestCost.nodesInPath + 1;

                        unknownNodes.add(curr);
                    }
                }
            }
        }

        return null;
    } // end of search

    /**
     * Assembles a String[] depicting the shortest path from source to destination.
     *
     * @param dest the destination node
     * @return String[] with the vertices of the path in order from source to destination.
     */
    private String[] assemblePath(Node dest)
    {
        int      idx;
        String[] path;
        Node     curr;

        path = new String[dest.nodesInPath];

        curr = dest;
        idx  = dest.nodesInPath - 1;

        while (curr != null)
        {
            path[idx] = curr.name;
            curr      = curr.backtrace;

            idx--;
        }

        return path;
    }

    /**
     * String representing Dijkstra's class
     *
     * @return String
     */
    @Override
    public String toString()
    {
        return "Dijkstra's Shortest Path Finder";
    }

    /**
     * Inner node class to store info about each node in the graph
     */
    private class Node implements Comparable<Node>
    {
        private final String            name;
        private double                  cost;
        private Node                    backtrace;
        private int                     nodesInPath;

        /**
         * Constructor for Node class
         * @param name The name of the node
         */
        private Node(String name)
        {
            this.name        = name;
            this.cost        = Double.POSITIVE_INFINITY;
            this.backtrace   = null;
            this.nodesInPath = 1;
        }

        /**
         * @param other the object to be compared.
         * @return int representing ordering
         */
        @Override
        public int compareTo(Node other)
        {
            return Double.compare(this.cost, other.cost);
        }

        /**
         * String representing node
         *
         * @return node, cost, and backtrace node (if exists)
         */
        @Override
        public String toString()
        {
            StringBuilder builder;

            builder = new StringBuilder();

            builder.append("\"")
                    .append(this.name)
                    .append("\" : ")
                    .append(this.cost);

            if (this.backtrace != null)
            {
                builder.append(" -> \"")
                        .append(this.backtrace.name)
                        .append("\"");
            }

            return builder.toString();
        }
    } // end of inner Node class
}
