import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A class implementing Dijkstra's algorithm to search a graph for the shortest path from
 * source to destination.
 */
public class Dijkstras implements Search
{
    public Dijkstras()
    {
        // do nothing
    }

    public Path search(String src, String dest, Digraph graph)
    {
        PriorityQueue<Node>   q;
        ArrayList<String>     nodes, edges;
        Node                  n;

        nodes = graph.nodes();
        q     = new PriorityQueue<>();

        // For each node v != source,
        for (String node : nodes)
        {
            n = new Node(node); // set v.cost = infinity and v.known = false

            // set source.cost = 0 and source.known = true
            if (node.equals(src))
            {
                n.cost  = 0;
                n.known = true;
            }

            q.add(n);
        }

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


    private class Node implements Comparable<Node>
    {
        private String  name;
        private boolean known;
        private int     cost;
        private Node    backtrace;

        private Node(String name)
        {
            this.name  = name;
            this.cost  = Integer.MAX_VALUE;
            this.known = false;
        }

        /**
         * @param other the object to be compared.
         * @return int representing ordering
         */
        @Override
        public int compareTo(Node other)
        {
            return this.cost - other.cost;
        }
    }
}
