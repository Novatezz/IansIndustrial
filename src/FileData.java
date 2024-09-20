/**
 * File Data class
 * Holds all of our data in the FileData object
 */

public class FileData
{
    //Header data
    public String locationName;
    public String date;
    public String time;
    public String typeLevel;
    public String gasName;

    //Danger levels (for colouring grid)
    public String[]dangerLevels;

    //Grid counts
    public int rowCount;
    public int columnCount;

    //2D array of strings to hold data in our grid
    public String[][] gridData;
}
