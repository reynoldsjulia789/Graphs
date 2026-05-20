import java.util.ArrayList;
import java.util.HashMap;

/*
 * Reflection Questions: TODO answer questions
 * When should you use a list to represent a directed weighted graph?
 * Why?
 * Does this reasoning hold true for other types of graphs?
 */


public class AdjList implements Digraph
{
    private HashMap<String, HashMap<String, Double>> m_graph;

    /**
     *
     * @param key
     * @return
     */
    @Override
    public boolean add(String key)
    {
        return false;
    }

    /**
     *
     * @param src
     * @param dest
     * @param weight
     * @return
     */
    @Override
    public boolean add(String src, String dest, Double weight)
    {
        return false;
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    public String delete(String key)
    {
        return "";
    }

    /**
     *
     * @param src
     * @param dest
     * @return
     */
    @Override
    public Double delete(String src, String dest)
    {
        return 0.0;
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<String> nodes()
    {
        return null;
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    public ArrayList<String> edges(String key)
    {
        return null;
    }

    /**
     *
     * @param src
     * @param dest
     * @return
     */
    @Override
    public Double weight(String src, String dest)
    {
        return 0.0;
    }

    /**
     *
     * @return
     */
    @Override
    public double density()
    {
        return 0;
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    public double density(String key)
    {
        return 0;
    }

    /**
     *
     * @return
     */
    @Override
    public int size()
    {
        return 0;
    }

    /**
     *
     * @return
     */
    @Override
    public String toJSON()
    {
        return "";
    }
}
