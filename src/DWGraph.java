import java.util.ArrayList;

/*
 * Reflection Questions: TODO answer questions
 * What benefit does using a facade provide?
 * What would happen if this file was omitted from the assignment specifications?
 */

/**
 * Directed Weighted Graph
 */
public class DWGraph
{
    private Digraph graph;
    private int     size;
    private double  mtxThreshold;
    private double  lstThreshold;

    /**
     * Constructor
     */
    public DWGraph()
    {

    }

    /**
     * Constructor
     * @param filepath
     */
    public DWGraph(String filepath)
    {

    }

    /**
     *
     * @param key
     * @return
     */
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
    public boolean add(String src, String dest, Double weight)
    {
        return false;
    }

    /**
     *
     * @param key
     * @return
     */
    public String delete(String key)
    {
        return null;
    }

    /**
     *
     * @param src
     * @return
     */
    public Double delete(String src, String dest)
    {
        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> nodes()
    {
        return null;
    }

    /**
     *
     * @param key
     * @return
     */
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
    public Double weight(String src, String dest)
    {
        return null;
    }

    /**
     *
     * @return
     */
    public double density()
    {
        return -1;
    }

    /**
     *
     * @param key
     * @return
     */
    public double density(String key)
    {
        return -1;
    }

    /**
     *
     * @return
     */
    public int size()
    {
        return -1;
    }

    /**
     *
     * @return
     */
    public String toString()
    {
        return null;
    }

    /**
     *
     * @return
     */
    public String toJSON()
    {
        return null;
    }

    /**
     *
     * @param filepath
     * @return
     */
    public static DWGraph load(String filepath)
    {
        return null;
    }

    /**
     *
     */
    private void convert()
    {

    }
}
