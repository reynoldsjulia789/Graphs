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
        // do nothing???
    }

    public static Path search(String src, String dest, Digraph graph)
    {
        PriorityQueue<Vertex> q;
        ArrayList<String>     nodes, edges;

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


    private class Vertex implements Comparable<Vertex>
    {
        private String  name;
        private boolean known;
        private Integer cost;
        private Vertex  backtrace;

        private Vertex(String name)
        {
            this.name = name;
        }

        /**
         * @param other the object to be compared.
         * @return int representing ordering
         */
        @Override
        public int compareTo(Vertex other)
        {
            return this.cost - other.cost;
        }
    }
}
