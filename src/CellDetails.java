import java.awt.*;

/**
 * Class to hold cell details for Sorting form to use
 */
public class CellDetails implements Comparable<CellDetails>
{
    public int row;
    public int column;
    public int value;
    public Color background;

    /**
     * Override for the compare function to compare cells together
     * @param o the object to be compared.
     * @return where to position current item -1 = before, 1 = after, 0 is equal
     */
    @Override
    public int compareTo(CellDetails o)
    {
        if(value < o.value)
        {
            return -1;
        }
        else if(value > o.value)
        {
            return 1;
        }
        return 0;
    }
}
