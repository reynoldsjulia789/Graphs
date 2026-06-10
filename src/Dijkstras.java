import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

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
        PriorityQueue<Node>   unknownNodes;
        ArrayList<Node>       knownNodes;
        ArrayList<String>     edges;
        Node                  curr, source, node;

        if (src == null || dest == null || graph == null)
        {
            return null;
        }

        source = findSource(graph.nodes(), src, dest);

        if ((source == null))
        {
            return null;
        }

        unknownNodes = new PriorityQueue<>();

        unknownNodes.add(source);

        while (!unknownNodes.isEmpty())
        {
            curr = unknownNodes.poll();

            edges = graph.edges(curr.name);

            for (String edge : edges)
            {
                node = unknownNodes.

                unknownNodes.add()
            }
        }

        // For each node v != source,
        // set v.cost = infinity and v.known = false
        // set source.cost = 0 and source.known = true
        // while there are unknown nodes in the graph
        // select the unknown node v with the lowest cost
        // mark v as known
        // for each edge(v,u) with weight w,
        // c1 = v.cost + w (cost of best path through v to u)
        // c2 = u.cost (cost of best path to u previously known)
        // if (c1 < c2)
        // u.cost = c1
        // u.path = v
    }

    /**
     * Verifies that the source node and destination node exist in the list of the graph's nodes
     *
     * @param nodes the nodes in the graph
     * @param src the source node
     * @param dest the destination node
     * @return a Node - the source node if src and dest were found, null if not
     */
    private Node findSource(ArrayList<String> nodes, String src, String dest)
    {
        int  count;
        Node source;

        count      = 0;
        source     = null;

        for (String node : nodes)
        {
            if (node.equals(src))
            {
                source       = new Node(node);
                source.cost  = 0;

                count++;
            }

            if (node.equals(dest))
            {
                count++;
            }

            if (count == 2)
            {
                break;
            }
        }

        if ((source == null) || (count != 2))
        {
            return null;
        }

        return source;
    }


    /**
     * Inner node class to store info about each node in the graph
     */
    private class Node implements Comparable<Node>
    {
        private final String            name;
        private int                     cost;
        private Node                    Backtrace;

        /**
         * Constructor for Node class
         * @param name The name of the node
         */
        private Node(String name)
        {
            this.name      = name;
            this.cost      = Integer.MAX_VALUE;
            this.Backtrace = null;
        }

        /**
         * @param other the object to be compared.
         * @return int representing ordering
         */
        @Override
        public int compareTo(Node other)
        {
            return Integer.compare(this.cost, other.cost);
        }
    }
}
