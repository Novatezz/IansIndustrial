import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

/**
 * Main form of Ian's Industrial
 * holds logic for building UI components
 * Basic functionality for UI
 */
public class MainForm extends JFrame implements ActionListener
{
    /**
     * Initial variables for main form to use
     * UI components (JTextField,label,button)
     * import FileManager class (file)
     * set variables for various objects (gridColumn/Row, dangerous, concerning, acceptable,array to hold levels)
     */
    SpringLayout layout = new SpringLayout();
    JTextField[][] formGrid;
    JLabel lblTitle,lblGasTitle,lblGasLabel,lblLegend,lblDangerous,lblConcerning,lblAcceptable,
            lblLocation,lblDate,lblTime,lblRecorded,lblExport;
    JTextField txtDate,txtTime,txtLocation,txtRecordedLevel;
    JButton btnExport,btnClose,btnSulphur,btnNitrogen,btnCarbon,btnObstruction,btnLoad,btnSave,btnSort;
    FileManager file = new FileManager( );
    int gridColumns;
    int gridRows;
    int dangerous;
    int concerning;
    int acceptable;
    String[] tempDanger;

    /**
     * Main form Constructor
     */
    public MainForm()
    {
        setTitle("Ian's Industrial");
        setLayout(layout);
        //sets background colour of the window
        this.getContentPane().setBackground(Color.decode("#599467"));
        //listener to end application when closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //Function call to build UI components (labels, text fields and buttons)
        BuildUIComponents();

        //Load initial File
        String filePath = "Ians_W07_NO2_202710201500.csv";

        //Build Grid passing in the initial file path
        BuildGrid(filePath);

        //set location of window in the middle of the screen
        setLocationRelativeTo(null);

        //Show Window to user
        setVisible(true);
    }

    /**
     * Method to build UI Components
     */

    private void BuildUIComponents() {
        lblTitle = UIBuilderLibrary.BuildJLabelWithNorthWestAnchor("Ian's Industrial Installation"
                ,20,20,layout,this);
        lblTitle.setFont(new Font("Courier", Font.BOLD,25));
        lblTitle.setPreferredSize(new Dimension(350,30));
        lblTitle.setOpaque(true);
        lblTitle.setBackground(Color.decode("#365c3f"));
        lblTitle.setForeground(Color.white);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle);

        lblLocation = UIBuilderLibrary.BuildJLabelInlineToRight("Location:",20,layout,lblTitle);
        add(lblLocation);

        txtLocation = UIBuilderLibrary.BuildJTextFieldInlineToRight(16,5,layout,lblLocation);
        add(txtLocation);

        lblDate = UIBuilderLibrary.BuildJLabelInlineToRight("Date:", 50,layout,txtLocation);
        add(lblDate);
        txtDate = UIBuilderLibrary.BuildJTextFieldInlineToRight(8,5,layout,lblDate);
        add(txtDate);

        lblTime = UIBuilderLibrary.BuildJLabelInlineToRight("Time",5,layout,txtDate);
        add(lblTime);
        txtTime = UIBuilderLibrary.BuildJTextFieldInlineToRight(8,5,layout,lblTime);
        add(txtTime);

        lblGasTitle = UIBuilderLibrary.BuildJLabelInlineBelow("'Gas' Levels",20,layout,lblTitle);
        add(lblGasTitle);
        lblGasLabel = UIBuilderLibrary.BuildJLabelInlineBelow("(Gas name)",5,layout,lblGasTitle);
        add(lblGasLabel);

        lblLegend = UIBuilderLibrary.BuildJLabelInlineBelow("Legend",40,layout,lblGasLabel);
        add(lblLegend);
        lblDangerous = UIBuilderLibrary.BuildJLabelInlineBelow("Dangerous",5,layout,lblLegend);
        lblDangerous.setPreferredSize(new Dimension(100,25));
        lblDangerous.setOpaque(true);
        lblDangerous.setBackground(Color.red);
        lblDangerous.setForeground(Color.black);
        lblDangerous.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblDangerous);
        lblConcerning = UIBuilderLibrary.BuildJLabelInlineBelow("Concerning",5,layout,lblDangerous);
        lblConcerning.setPreferredSize(new Dimension(100,25));
        lblConcerning.setOpaque(true);
        lblConcerning.setBackground(Color.orange);
        lblConcerning.setForeground(Color.BLACK);
        lblConcerning.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblConcerning);
        lblAcceptable = UIBuilderLibrary.BuildJLabelInlineBelow("Acceptable",5,layout,lblConcerning);
        lblAcceptable.setPreferredSize(new Dimension(100,25));
        lblAcceptable.setOpaque(true);
        lblAcceptable.setBackground(Color.GREEN);
        lblAcceptable.setForeground(Color.BLACK);
        lblAcceptable.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblAcceptable);

        btnSulphur = UIBuilderLibrary.BuildJButtonInlineBelow(120,25,"Sulphur Dioxide",40,
                this,layout,lblAcceptable);
        btnSulphur.setMargin(new Insets(0,0,0,0));
        add(btnSulphur);
        btnNitrogen = UIBuilderLibrary.BuildJButtonInlineBelow(120,25,"Nitrogen Dioxide",5,
                this,layout,btnSulphur);
        btnNitrogen.setMargin(new Insets(0,0,0,0));
        add(btnNitrogen);
        btnCarbon = UIBuilderLibrary.BuildJButtonInlineBelow(120,25,"Carbon Monoxide",5,
                this,layout,btnNitrogen);
        btnCarbon.setMargin(new Insets(0,0,0,0));
        add(btnCarbon);
        btnObstruction = UIBuilderLibrary.BuildJButtonInlineBelow(120,25,"Obstructions",5,
                this,layout,btnCarbon);
        btnObstruction.setMargin(new Insets(0,0,0,0));
        add(btnObstruction);
        btnLoad = UIBuilderLibrary.BuildJButtonInlineBelow(120,25,"Load From File",20,
                this,layout,btnObstruction);
        btnLoad.setMargin(new Insets(0,0,0,0));
        add(btnLoad);
        btnSave = UIBuilderLibrary.BuildJButtonInlineBelow(120,25,"Save as CSV/RAF",5,
                this,layout,btnLoad);
        btnSave.setMargin(new Insets(0,0,0,0));
        add(btnSave);
    }

    /**
     * Method to Build Grid
     * @param filePath passes in the file path of the file used to build the grid
     */
    private void BuildGrid(String filePath)
    {
        //New file data object initially set to null
        FileData data = null;

        //If filepath is not null and is a CSV file run "ReadFromCSV" function setting the 'data' variable
        if(filePath != null && filePath.endsWith(".csv"))
        {
            data = file.ReadFromCSV(filePath);
        }
        //If filepath is not null and is a RAF file run "ReadFromRAF" function setting the 'data' variable
        else if (filePath != null && filePath.endsWith(".raf"))
        {
            data = file.ReadFromRAF(filePath);
        }

        //data variable is not null set all text boxes and labels to object variables
        if (data != null)
        {
            txtLocation.setText(data.locationName);
            txtDate.setText(data.date);
            txtTime.setText(data.time);
            lblGasTitle.setText(data.typeLevel);
            lblGasLabel.setText(data.gasName);
            gridColumns = data.columnCount;
            gridRows = data.rowCount;

            //set danger variables (used to colour grid correctly)
            dangerous = Integer.parseInt(data.dangerLevels[0]);
            concerning = Integer.parseInt(data.dangerLevels[1]);
            acceptable = Integer.parseInt(data.dangerLevels[2]);
            tempDanger = data.dangerLevels;

            //gets window size values from loaded in grid
            int setWidth = 100+(gridColumns*30);
            int setHeight = 200+(gridRows*25);

            //checks height and width before setting to grid size
            // if smaller than minimum size sets to minimum size
            if(setWidth < 950 && setHeight < 500)
            {
                setSize(950, 500);
            }
            else if(setHeight<500)
            {
                setSize(setWidth, 500);
            }
            else if (setWidth<950)
            {
                setSize(950, setHeight);
            }
            else
            {
                setSize(setWidth, setHeight);
            }
        }
        //if data variable is null show error message
        else
        {
            JOptionPane.showMessageDialog(this,"Error loading file. Loading blank form.");
        }

        //sets form grid size
        formGrid = new JTextField[gridRows][gridColumns];

        //set value for positioning UI components under Grid
        int yVal = 0;

        //Grid builder logic (nested for loops for 2D array)
        for (int y = 0; y < formGrid.length; y++)
        {
            //Y position variable
            //Formula = {loop number} * {size of component} + {upper margin}
            int yPos = y*24+70;

            for (int x = 0; x < formGrid[y].length; x++)
            {
                //X position variable
                int xPos = x*24+220;

                //position text box at x and y position
                formGrid[y][x] = UIBuilderLibrary.BuildJTextFieldWithNorthWestAnchor(0,xPos,yPos,layout,this);

                //sets dimension of text box
                formGrid[y][x].setPreferredSize(new Dimension(25,25));

                //if data variable is not null sets data inside grid values
                if (data != null)
                {
                    //set text in text box to data values
                    formGrid[y][x].setText(data.gridData[y][x]);

                    //If the value in the cell is a valid number
                    if (IsAnInteger(formGrid[y][x].getText()))
                    {
                        //gets colour based on danger variables
                        Color colour = DetermineCellColour(formGrid[y][x].getText());

                        //sets background and text colour to danger colour
                        formGrid[y][x].setBackground(colour);
                        formGrid[y][x].setForeground(colour);
                    }
                }
                //runs method to change colour of text boxes when values are changed
                AddFocusListenerToGridField(formGrid[y][x]);
                //runs method to add focus listener (will show data at bottom of screen when hovered over)
                AddMouseListenerToJTextField(formGrid[y][x]);

                //adds grid components to the screen
                add(formGrid[y][x]);
            }
            //increments yVal variable (for positioning UI components under grid)
            yVal++;
        }
        //UI components positioned under grid using the bottom left grid textbox to position itself
        lblRecorded = UIBuilderLibrary.BuildJLabelInlineBelow("Recorded Level:",20,layout,formGrid[yVal-1][0]);
        add(lblRecorded);
        txtRecordedLevel = UIBuilderLibrary.BuildJTextFieldInlineToRight(8,5,layout,lblRecorded);
        add(txtRecordedLevel);
        btnSort = UIBuilderLibrary.BuildJButtonInlineToRight(120,25,"Sort",125,this,layout,txtRecordedLevel);
        add(btnSort);
        btnExport = UIBuilderLibrary.BuildJButtonInlineToRight(120,25,"Export",250,this,layout,txtRecordedLevel);
        add(btnExport);
        lblExport = UIBuilderLibrary.BuildJLabelInlineBelow("   As: RAF/DAT/RPT",5,layout,btnExport);
        add(lblExport);
        btnClose = UIBuilderLibrary.BuildJButtonInlineToRight(120,25,"Close",5,this,layout,btnExport);
        add(btnClose);
    }

    /**
     * Method to clear and remove grid components (when loading a new grid in after)
     */
    private void ClearFormGrid()
    {
        //Nested for loops to remove each text box
        for (int y = 0; y < formGrid.length; y++) {
            for (int x = 0; x < formGrid[y].length; x++) {
                remove(formGrid[y][x]);
            }
        }
        //remove UI components under grid (to be re-added with new grid)
        remove(txtRecordedLevel);
        remove(btnClose);
        remove(btnSort);
        remove(btnExport);
        remove(lblExport);
        remove(lblRecorded);
        //reset UI
        repaint();
    }


    /**
     * Method to save data to file
     * @param filePath filepath to save data to
     * @param danger danger level array of selected object
     */
    private void SaveToFile(String filePath,String[] danger)
    {
        //new file data object to grab all the text field data and object data
        FileData data = new FileData();
        data.locationName = txtLocation.getText();
        data.date = txtDate.getText();
        data.time = txtTime.getText();
        data.typeLevel = lblGasTitle.getText();
        data.gasName = lblGasLabel.getText();
        data.dangerLevels = danger;
        data.rowCount = gridRows;
        data.columnCount = gridColumns;
        data.gridData = new String[gridRows][gridColumns];

        //loop grabs all text out of the grid and stores them in new object 2D array
        for (int y = 0; y < formGrid.length; y++)
        {
            for (int x = 0; x < formGrid[y].length; x++)
            {
                data.gridData[y][x] = formGrid[y][x].getText();
            }
        }

        //Determine which write function to use depending on file extension
        if(filePath.endsWith(".raf"))
        {
            file.WriteToRaf(data, filePath);
        }
        else if (filePath.endsWith(".csv"))
        {
            file.WriteToCSVFile(data,filePath);
        }
        else if (filePath.endsWith(".dat"))
        {
            file.WriteToDATFile(data,filePath);
        }
        else if (filePath.endsWith(".rpt"))
        {
            file.WriteToRPTFile(data,filePath);
        }

        //Show message confirming data has been written
        JOptionPane.showMessageDialog(this,"Data Saved!\nIN: "+filePath);
    }

    /**
     * Boolean Method to check if string is an integer
     * @param input String to be checked
     * @return true if integer false if not
     */
    private boolean IsAnInteger(String input)
    {
        try
        {
            Integer.parseInt(input);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    /**
     * Method to return colour on grid cell
     * @param input passes in the value of the grid cell
     * @return colour of grid cell
     */
    private Color DetermineCellColour(String input)
    {
        //check if data is a number
        int number = Integer.parseInt(input);

        //check number against danger values to return correct colour to grid
        if (number >= dangerous)
        {
            return Color.red;
        }
        else if(number >= concerning)
        {
            return Color.orange;
        }
        else if(number >= acceptable)
        {
            return Color.green;
        }
        else
        {
            return Color.white;
        }
    }

    /**
     * Add mouse listener to text fields in grid
     * this will be used to show value of field at the bottom of the grid when hovered over
     * @param field passes in the text field being created
     */
    private void AddMouseListenerToJTextField(JTextField field)
    {
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                //get text from passed in text field
                JTextField textField = (JTextField) e.getSource();
                //sets text box to value from text field
                txtRecordedLevel.setText(textField.getText());
            }
        });
    }

    /**
     * Add focus listener to text fields in grid
     * this will be used to change the colour of grid based on new values being entered
     * @param field passes in the text field being created
     */
    private void AddFocusListenerToGridField(JTextField field)
    {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                //get text from passed in text field and check if its a number
                if (IsAnInteger(field.getText()))
                {
                    //determine its calour and set new colour
                    Color colour = DetermineCellColour(field.getText());
                    field.setBackground(colour);
                    field.setForeground(colour);
                }
                else
                {
                    //if not a number show error message to user
                    JOptionPane.showMessageDialog(null,"The value provided was not a valid number.");
                }
            }
        });
    }

    /**
     * Method to open a dialog box for user to choose a save location
     * @return a file path for saving file
     */
    private String ChooseSaveFileLocation()
    {
        //create new file chooser object
        JFileChooser fileChooser = new JFileChooser();

        //sets file filters of the file chooser
        fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV File","csv"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("RAF File","raf"));

        //opens dialog box, waits for "ok" to be pressed
        if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            //if user doesn't specify filter somehow use default of csv
            if(fileChooser.getFileFilter() == null)
            {
                return fileChooser.getSelectedFile().getAbsolutePath() + ".csv";
            }
            //sets file filters to be used
            FileNameExtensionFilter filter = (FileNameExtensionFilter) fileChooser.getFileFilter();
            String extension = filter.getExtensions()[0];

            //saves users selected file path in variable
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            //if user returns file path with correct file extension return that filepath
            if(filePath.endsWith(".csv")||filePath.endsWith(".raf"))
            {
                return filePath;
            }
            //if not adds extension onto file path
            return filePath+"."+extension;
        }
        //if user presses cancel return nothing
        return null;
    }

    /**
     * Method to open a dialog box for user to choose a  file path to load a new file
     * @return a file path for loading file
     */
    private String ChooseLoadFileLocation()
    {
        //create new file chooser object
        JFileChooser fileChooser = new JFileChooser();

        //sets file filters of the file chooser
        fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV File","csv"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("RAF File","raf"));

        //make it so user cant select multiple file paths
        fileChooser.setMultiSelectionEnabled(false);

        //opens dialog box, waits for "ok" to be pressed
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            //gets filepath from dialog box
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            //check file path has correct file extension
            if(filePath.endsWith(".csv")||filePath.endsWith(".raf"))
            {
                return filePath;
            }
        }
        //if canceled or selected file with incorrect file extension return nothing
        return null;
    }

    /**
     * Method to open the sorting form and display sorted data from the main form grid
     */
    private void OpenSortingForm()
    {
        //create new linked list for data to be inserted into
        LinkedList<CellDetails> cellData = new LinkedList<>();

        //Loop through grid
        for (int y = 0; y < formGrid.length; y++)
        {
            for (int x = 0; x < formGrid[y].length; x++)
            {
                //if grid cell contains a number make a new CellDetails
                // store grid values inside and add it to the list
                if(IsAnInteger(formGrid[y][x].getText()) && !formGrid[y][x].getText().equals("0"))
                {
                    CellDetails newCell = new CellDetails();
                    newCell.row = y;
                    newCell.column = x;
                    newCell.value = Integer.parseInt(formGrid[y][x].getText());
                    newCell.background = formGrid[y][x].getBackground();

                    cellData.add(newCell);
                }
            }
        }
        //Create sorting form and populate it
        SortingForm sortForm = new SortingForm(cellData);
        sortForm.setVisible(true);
    }


    /**
     * action listeners for button presses
     * @param e passes in button press event
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //buttons to load the default file for different gasses/obstructions
        if(e.getSource()== btnNitrogen)
        {
            //sets button colours to show which form is selected
            btnNitrogen.setBackground(Color.BLACK);
            btnSulphur.setBackground(Color.decode("#365c3f"));
            btnCarbon.setBackground(Color.decode("#365c3f"));
            btnObstruction.setBackground(Color.decode("#365c3f"));
            //clears old grid
            ClearFormGrid();
            //builds grid with default filepath for this gas/obstruction
            BuildGrid("Ians_W07_NO2_202710201500.csv");
            //revalidate new grid and UI components
            revalidate();
        }
        if(e.getSource()==btnSulphur)
        {
            //sets button colours to show which form is selected
            btnNitrogen.setBackground(Color.decode("#365c3f"));
            btnSulphur.setBackground(Color.BLACK);
            btnCarbon.setBackground(Color.decode("#365c3f"));
            btnObstruction.setBackground(Color.decode("#365c3f"));
            //clears old grid
            ClearFormGrid();
            //builds grid with default filepath for this gas/obstruction
            BuildGrid("Ians_W07_SO2_202604061100.csv");
            //revalidate new grid and UI components
            revalidate();
        }
        if (e.getSource() == btnCarbon)
        {
            //sets button colours to show which form is selected
            btnNitrogen.setBackground(Color.decode("#365c3f"));
            btnSulphur.setBackground(Color.decode("#365c3f"));
            btnCarbon.setBackground(Color.BLACK);
            btnObstruction.setBackground(Color.decode("#365c3f"));
            //clears old grid
            ClearFormGrid();
            //builds grid with default filepath for this gas/obstruction
            BuildGrid("Ians_W07_CO_202604061100.csv");
            //revalidate new grid and UI components
            revalidate();
        }
        if(e.getSource()==btnLoad)
        {
            //sets button colours to show which form is selected
            btnNitrogen.setBackground(Color.decode("#365c3f"));
            btnSulphur.setBackground(Color.decode("#365c3f"));
            btnCarbon.setBackground(Color.decode("#365c3f"));
            btnObstruction.setBackground(Color.decode("#365c3f"));
            //opens load dialog box and stores the filepath selected
            String filePath = ChooseLoadFileLocation();
            //if file path is not null delete old grid create new grid
            if(filePath != null)
            {
                //clears old grid
                ClearFormGrid();
                //builds grid with new filepath for this gas/obstruction
                BuildGrid(filePath);
                //revalidate new grid and UI components
                revalidate();
            }
        }
        if(e.getSource()==btnObstruction)
        {
            //sets button colours to show which form is selected
            btnNitrogen.setBackground(Color.decode("#365c3f"));
            btnSulphur.setBackground(Color.decode("#365c3f"));
            btnCarbon.setBackground(Color.decode("#365c3f"));
            btnObstruction.setBackground(Color.BLACK);
            //clears old grid
            ClearFormGrid();
            //builds grid with default filepath for this gas/obstruction
            BuildGrid("Ians_W07_OB_202604061100.csv");
            //revalidate new grid and UI components
            revalidate();
        }
        //Close button
        if(e.getSource()==btnClose)
        {
            //open dialog box to ask user if they want to close
            int a = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);

            //if yes close, if no do nothing.
            if (a == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        //Save button
        if(e.getSource()==btnSave)
        {
            //Open dialog box to let user select file name and file path to save to
            String filePath = ChooseSaveFileLocation();
            //if filepath is not null run save method if not, do nothing
            if (filePath != null)
            {
                SaveToFile(filePath,tempDanger);
            }
        }
        //Export Button
        if(e.getSource()==btnExport)
        {
            //Save DAT File passing in filepath and danger values array
            SaveToFile("Test.dat",tempDanger);

            //Save RPT File passing in filepath and danger values array
            SaveToFile("Test.rpt",tempDanger);

            //Save RAF File passing in filepath and danger values array
            SaveToFile("Test.raf",tempDanger);
        }
        //Sort Button
        if(e.getSource()==btnSort)
        {
            //call sorting form method
            OpenSortingForm();
        }
    }
}
