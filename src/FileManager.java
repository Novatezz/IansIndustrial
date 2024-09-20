import java.awt.*;
import java.io.*;

/**
 * File Manager class
 * Holds all of our methods for reading and writing files
 */
public class FileManager
{
    /**
     * Method for writing CSV Files from File Data objects
     * @param fileData File Data object to be written from
     * @param filePath name of file and where we want the file to be written
     */
    public void WriteToCSVFile(FileData fileData, String filePath)
    {
    try
    {
        //new buffered writer object passing in filepath
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));

        //Write headings with location,date,time, names of gases/obstructions and danger levels
        bufferedWriter.write(fileData.locationName + "\n");
        bufferedWriter.write(fileData.date + "\n");
        bufferedWriter.write(fileData.time + "\n");
        bufferedWriter.write(fileData.typeLevel + "\n");
        bufferedWriter.write(fileData.gasName + "\n");
        bufferedWriter.write(fileData.dangerLevels[0]+","+fileData.dangerLevels[1]+","+fileData.dangerLevels[2]+"\n");

        //For loop to loop through data array and write values to file
        for (int y = 0; y < fileData.gridData.length; y++)
        {
            for (int x = 0; x < fileData.gridData[y].length; x++)
            {
                //if data empty write nothing add delimiter
                if (fileData.gridData[y][x].isEmpty( ))
                {
                    bufferedWriter.write(" ,");
                }
                else
                {
                    //if data has value write that value and add delimiter
                    bufferedWriter.write(fileData.gridData[y][x] + ",");
                }
            }
            //tell writer to go to a new line after every full line in array
            bufferedWriter.newLine( );
        }
        //close writer
        bufferedWriter.close( );
    }
    catch (Exception ex) {
        System.out.println(ex.getMessage());
    }
    }

    /**
     * Method for writing DAT file from file data object
     * @param fileData File Data object to be written from
     * @param filePath name of file and where we want the file to be written
     */
    public void WriteToDATFile(FileData fileData, String filePath)
    {
        try
        {
            //new buffered writer object passing in filepath
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));

            //For loop to loop through data array and write values to file
            for (int y = 0; y < fileData.gridData.length; y++)
            {
                for (int x = 0; x < fileData.gridData[y].length; x++)
                {
                    if (fileData.gridData[y][x].isEmpty( ))
                    {
                        //if data empty write nothing add delimiter
                        bufferedWriter.write(" ,");
                    }
                    else
                    {
                        //variable to hold colour value temporarily for writing to file
                        String tempC;
                        //if grid value is above danger level set tempC to red or "R"
                        if (Integer.parseInt(fileData.gridData[y][x]) >= Integer.parseInt(fileData.dangerLevels[0]))
                        {
                            tempC = "R";
                        }
                        //if grid value is above concerning level set tempC to orange or "O"
                        else if(Integer.parseInt(fileData.gridData[y][x]) >= Integer.parseInt(fileData.dangerLevels[1]))
                        {
                            tempC = "O";
                        }
                        //if grid value is above acceptable level set tempC to Green or "G"
                        else if(Integer.parseInt(fileData.gridData[y][x]) >= Integer.parseInt(fileData.dangerLevels[2]))
                        {
                            tempC = "G";
                        }
                        //if grid value is below all danger levels set tempC to White or "W"
                        else
                        {
                            tempC = "W";
                        }
                        //write cell x and y values followed by corresponding colour value then go to a new line
                        bufferedWriter.write(y+","+x+","+tempC+"\n");
                    }
                }
            }
            //close writer
            bufferedWriter.close( );
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Method for writing DAT file from file data object
     * @param fileData File Data object to be written from
     * @param filePath name of file and where we want the file to be written
     */
    public void WriteToRPTFile(FileData fileData, String filePath)
    {
        try
        {
            //new buffered writer object passing in filepath
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));

            //Variables to hold current colour value and previous colour value
            String currentColour;
            String previousColour = null;

            //variable to hold count
            int count = 0;

            //Loops through file data array to get values from it
            for (int y = 0; y < fileData.gridData.length; y++)
            {
                for (int x = 0; x < fileData.gridData[y].length; x++)
                {
                    //Check value and set current colour to correct value
                    if (Integer.parseInt(fileData.gridData[y][x]) >= Integer.parseInt(fileData.dangerLevels[0]))
                    {
                        currentColour = "R";
                    }
                    else if(Integer.parseInt(fileData.gridData[y][x]) >= Integer.parseInt(fileData.dangerLevels[1]))
                    {
                        currentColour = "O";
                    }
                    else if(Integer.parseInt(fileData.gridData[y][x]) >= Integer.parseInt(fileData.dangerLevels[2]))
                    {
                        currentColour = "G";
                    }
                    else
                    {
                        currentColour = "W";
                    }
                    //check initial cell and sets count and previous colour
                    if(y == 0 && x == 0)
                    {
                        count = 1;
                        previousColour = currentColour;
                    }
                    //checks from cell 2 onwards
                    //checks if current colour and previous colour are the same
                    if(currentColour.equals(previousColour))
                    {
                        //increment counter and move on
                        count++;
                        //if the cell is the last cell in its row
                        if(x == fileData.gridData[y].length-1)
                        {
                            //write the count and colour + go to new line
                            bufferedWriter.write(currentColour + "," + count + "\n");
                            // then reset counter
                            count = 1;
                        }
                    }
                    //if the current colour and previous colour are different
                    else
                    {
                        //write colour and count
                        bufferedWriter.write(previousColour + "," + count + ",");
                        //set a previous colour to the new colour
                        previousColour = currentColour;
                        //reset counter
                        count = 1;
                        //if the cell is the last cell in its row
                        if(x == fileData.gridData[y].length-1)
                        {
                            //write the count and colour + go to new line
                            bufferedWriter.write(currentColour + "," + count + "\n");
                        }
                    }
                }
            }
            //close writer
            bufferedWriter.close( );
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Method for reading CSV file from file path
     * @param filePath name of file and where we want the file to be read from
     * @return File data object populated by data from read file
     */
    public FileData ReadFromCSV(String filePath)
    {
        //New File data object
        FileData fileData = new FileData();
        //Sets length of 2D array - 30 is max length we want to read in
        fileData.gridData = new String[30][];
        try
        {
            //new buffered reader object passing in filepath
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            //reads header data and sets object variable to read data
            fileData.locationName = reader.readLine();
            fileData.date = reader.readLine();
            fileData.time = reader.readLine();
            fileData.typeLevel = reader.readLine();
            fileData.gasName = reader.readLine();
            fileData.dangerLevels = reader.readLine().split(",");

            //temporary variable to hold data from each line
            String line;
            //counter of lines
            int count = 0;
            //read each line and split them by the delimiter ','
            while ((line = reader.readLine()) != null)
            {
                String[] temp = line.split(",");
                //sets row at column position
                fileData.gridData[count] = temp;
                //increment counter
                count++;
            }
            //set row and column counts
            fileData.columnCount = fileData.gridData[0].length;
            fileData.rowCount = count;
            //close reader
            reader.close();
        }
        catch (Exception ex)
        {
            //if we get an error mid read return null
            fileData = null;
            System.out.println(ex.getMessage());
        }
        return fileData;
    }

    /**
     * Method for Writing to RAF file
     * @param fileData File data object that holds data to be written
     * @param filePath File name and path where we want file to be written
     */
    public void WriteToRaf (FileData fileData,String filePath)
    {
        try
        {
            //variable to hold our pointer position
            int tempPos;
            //Create the random access file class for reading and writing to RAF files.
            //The 'rw' indicates it is being set to writing mode
            RandomAccessFile raf = new RandomAccessFile(filePath,"rw");
            //Rest the length of the raf file before writing to stop any risk of redundant data being left at the end of
            // the file if the number of entries being written is less than previous writes.
            raf.setLength(0);
            //Go to the first index in the file and write the first piece of header data(region name) to that location.
            raf.seek(0);
            raf.writeUTF(fileData.locationName);
            //set temp location counter to track position (String overhead (2) + (1) each character in string)
            tempPos = 2+fileData.locationName.length();
            //Go to the next index and write
            raf.seek(tempPos);
            raf.writeUTF(fileData.date);
            //add to temp location counter to track position (String overhead (2) + (1) each character in string)
            tempPos += (2+fileData.date.length());
            //Go to the next index and write
            raf.seek(tempPos);
            raf.writeUTF(fileData.time);
            //add to temp location counter to track position (String overhead (2) + (1) each character in string)
            tempPos += (2+fileData.time.length());
            //Go to the next index and write
            raf.seek(tempPos);
            raf.writeUTF(fileData.typeLevel);
            //add to temp location counter to track position (String overhead (2) + (1) each character in string)
            tempPos += (2+fileData.typeLevel.length());
            //Go to the next index and write
            raf.seek(tempPos);
            raf.writeUTF(fileData.gasName);
            //add to temp location counter to track position (String overhead (2) + (1) each character in string)
            tempPos += (2+fileData.gasName.length());
            //Go to the next index and write
            raf.seek(tempPos);
            //concatenate danger level array into a string before writing
            String temp = fileData.dangerLevels[0]+","+fileData.dangerLevels[1]+","+fileData.dangerLevels[2];
            raf.writeUTF(temp);
            //add to temp location counter to track position (String overhead (2) + (1) each character in string)
            tempPos += (2+temp.length());
            //Go to the next index and write
            raf.seek(tempPos);
            raf.writeInt(fileData.rowCount);
            //add to temp location counter to track position (integer 4 bytes)
            tempPos += 4;
            //Go to the next index and write
            raf.seek(tempPos);
            raf.writeInt(fileData.columnCount);
            //add to temp location counter to track position (integer 4 bytes)
            //this is our final position after header data has been written
            tempPos += 4;

            //counter used to track writing position when writing grid to file
            int count = 0;
            //nested for loops to step through grid data
            for (int y = 0; y < fileData.gridData.length; y++) {
                for (int x = 0; x < fileData.gridData[y].length; x++)
                {
                    //only execute writing to file if there is data in the grid
                    if(!fileData.gridData[y][x].isEmpty())
                    {
                        //set our writing index {position in grid * 20 + file position}
                        int index = count * 20 + tempPos;
                        //go to writing position
                        raf.seek(index);
                        //write y position
                        raf.writeInt(y);
                        raf.seek(index+4);
                        //write x position
                        raf.writeInt(x);
                        raf.seek(index+8);
                        //write data from grid
                        raf.writeUTF(fileData.gridData[y][x]);
                        //increment count for next grid position
                        count++;
                    }
                }
            }
            //close raf writer
            raf.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Method for reading from RAF file
     * @param filePath File name and path where we want file to be read from
     * @return File data object populated with variables from read file
     */
    public FileData ReadFromRAF(String filePath)
    {
        //make new file data object
        FileData fileData = new FileData();
        try
        {
            //variable to store reader position
            int tempPos;
            //make new RAF reader
            RandomAccessFile raf = new RandomAccessFile(filePath,"r");
            //Go to the first index in the file and write the first piece of header data(region name) to that location.
            raf.seek(0);
            //set file data location name
            fileData.locationName = raf.readUTF();
            //set temp location counter to track position (String overhead (2) + (1) each character in string)
            tempPos = (2+fileData.locationName.length());
            //go to next reader position
            raf.seek(tempPos);
            //set file data date
            fileData.date = raf.readUTF();
            //add to temp location counter to track position (String overhead (2) + (1) each character in string)
            tempPos += (2+fileData.date.length());
            //go to next reader position
            raf.seek(tempPos);
            //set file data time
            fileData.time = raf.readUTF();
            //add to temp location counter to track position (String overhead (2) + (1) each character in string)
            tempPos += (2+fileData.time.length());
            //go to next reader position
            raf.seek(tempPos);
            //set file data typeLevel
            fileData.typeLevel = raf.readUTF();
            //add to temp location counter to track position (String overhead (2) + (1) each character in string)
            tempPos += (2+fileData.typeLevel.length());
            //go to next reader position
            raf.seek(tempPos);
            //set file data gas name
            fileData.gasName = raf.readUTF();
            //add to temp location counter to track position (String overhead (2) + (1) each character in string)
            tempPos += (2+fileData.gasName.length());
            //go to next reader position
            raf.seek(tempPos);
            //String temp = fileData.dangerLevels[0]+","+fileData.dangerLevels[0]+","+fileData.dangerLevels[0];
            String temp = raf.readUTF();
            //set file data danger levels by splitting the line into an array of strings by delimiter
            fileData.dangerLevels = temp.split(",");
            //add to temp location counter to track position (String overhead (2) + (1) each character in string)
            tempPos += (2+temp.length());
            //go to next reader position
            raf.seek(tempPos);
            //set file data row count
            fileData.rowCount = raf.readInt();
            //add to temp location counter to track position (Integer 4 bytes)
            tempPos += 4;
            //go to next reader position
            raf.seek(tempPos);
            //set file data column count
            fileData.columnCount = raf.readInt();
            //add to temp location counter to track position (Integer 4 bytes)
            //this will be our starting position for data grid
            tempPos += 4;

            //make new 2D array for grid data to be read into
            fileData.gridData = new String[fileData.rowCount][fileData.columnCount];

            //set initial index and counter
            int index;
            int count = 0;
            //while our index is inside the RAF file size
            while((index = count * 20 + tempPos) < raf.length())
            {
                //go to next reader position
                raf.seek(index);
                //get row number
                int row = raf.readInt();
                //go to next reader position
                raf.seek(index + 4);
                //get column number
                int column = raf.readInt();
                //go to next reader position
                raf.seek(index+8);
                //get data value
                String value = raf.readUTF();
                //set data value on grid position
                fileData.gridData[row][column] = value;
                //increment counter for next grid position
                count++;
            }

            //close RAF reader
            raf.close();
        }
        catch(Exception ex)
        {
            //if we have any problems while reading file set file data to null
            fileData = null;
            System.out.println(ex.getMessage());
        }
        //return our filled out object (fileData)
        return fileData;
    }
}

