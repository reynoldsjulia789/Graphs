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
     * Verifies that the source node and destination node exist in the list of the graph's nodes
     * and puts all nodes into a hashmap
     *
     * @param nodes the nodes in the graph
     * @param src the source node
     * @param dest the destination node
     * @return a map of nodes if src and dest were found, null if not
     */
    private HashMap<String, Node> setup(ArrayList<String> nodes, String src, String dest)
    {
        int                   count;
        Node                  curr;
        HashMap<String, Node> nodeMap;

        nodeMap = new HashMap<>();
        count   = 0;

        for (String node : nodes)
        {
            curr = new Node(node);

            if (node.equals(src))
            {
                curr.cost  = 0;

                count++;
            }

            if (node.equals(dest))
            {
                count++;
            }

            nodeMap.put(node, curr);
        }

        if (count != 2)
        {
            return null;
        }

        return nodeMap;
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
        ArrayList<String>     knownNodes;
        PriorityQueue<Node>   unknownNodes;
        HashMap<String, Node> nodeMap;
        Node                  curr, source, lowestCost;
        double                cost;

        if (src == null || dest == null || graph == null)
        {
            return null;
        }

        nodeMap = setup(graph.nodes(), src, dest);

        if ((nodeMap == null))
        {
            return null;
        }

        unknownNodes = new PriorityQueue<>();
        knownNodes   = new ArrayList<>();
        source       = nodeMap.get(src);

        unknownNodes.add(source);

        while (!unknownNodes.isEmpty())
        {
            lowestCost = unknownNodes.poll();

            knownNodes.add(lowestCost.name);

            if (lowestCost.name.equals(dest))
            {
                return assemblePath(lowestCost);
            }

            for (String edge : graph.edges(lowestCost.name))
            {
                if (!knownNodes.contains(edge))
                {
                    curr = nodeMap.get(edge);

                    cost = lowestCost.cost + graph.weight(lowestCost.name, edge);

                    if (cost < curr.cost)
                    {
                        curr.cost      = cost;
                        curr.backtrace = lowestCost;
                        unknownNodes.add(curr);
                    }
                }
            }
        }

        return null;
    }

    /**
     * Inner node class to store info about each node in the graph
     */
    private class Node implements Comparable<Node>
    {
        private final String            name;
        private double                  cost;
        private Node                    backtrace;

        /**
         * Constructor for Node class
         * @param name The name of the node
         */
        private Node(String name)
        {
            this.name      = name;
            this.cost      = Integer.MAX_VALUE;
            this.backtrace = null;
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
    }
}
