import java.util.ArrayList;

/*
 * Reflection Questions: TODO answer questions
 * When did you choose to swap between the matrix and the list?
 * Explain and defend your threshold selection process.
 */

public interface Digraph
{
    /**
     *
     * @param key
     * @return
     */
    boolean add(String key);

    /**
     *
     * @param src
     * @param dest
     * @param weight
     * @return
     */
    boolean add(String src, String dest, Double weight);

    /**
     *
     * @param key
     * @return
     */
    public String delete(String key);

    /**
     *
     * @param src
     * @param dest
     * @return
     */
    public Double delete(String src, String dest);

    /**
     *
     * @return
     */
    public ArrayList<String> nodes();

    /**
     *
     * @param key
     * @return
     */
    public ArrayList<String> edges(String key);

    /**
     *
     * @param src
     * @param dest
     * @return
     */
    public Double weight(String src, String dest);

    /**
     *
     * @return
     */
    public double density();

    /**
     *
     * @param key
     * @return
     */
    public double density(String key);

    /**
     *
     * @return
     */
    public int size();

    /**
     *
     * @return
     */
    public String toString();

    /**
     *
     * @return
     */
    public String toJSON();

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
