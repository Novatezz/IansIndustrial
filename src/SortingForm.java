import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Form for showing sorted data from grid
 */
public class SortingForm extends JFrame
{
    SpringLayout layout = new SpringLayout();

    /**
     * Form for showing sorted data from grid
     * @param detailsList List to be passed in from main form holding data from the grid
     */
    public SortingForm(LinkedList<CellDetails> detailsList)
    {
        setTitle("Sorted Grid Data");
        this.getContentPane().setBackground(Color.decode("#599467"));

        //array of Jlabels to populate a table
        JLabel[][] detailsFields = new JLabel[detailsList.size()][3];
        JLabel lblY,lblX,lblData;

        Collections.sort(detailsList);
        Collections.reverse(detailsList);

        //calculate the height of the form for a label height of 22(20 + 2 margin) as well as some extra padding
        // for either side of your grid
        int formHeight = detailsList.size() * 22 + 80;
        setSize(225,formHeight);
        setLocationRelativeTo(null);
        setLayout(layout);
        Border greyLine = BorderFactory.createLineBorder(Color.darkGray);

        //Set Table headers
        lblY = UIBuilderLibrary.BuildJLabelWithNorthWestAnchor("Y-val",20,25,layout,this);
        add(lblY);
        lblX = UIBuilderLibrary.BuildJLabelWithNorthWestAnchor("X-val",60,25,layout,this);
        add(lblX);
        lblData = UIBuilderLibrary.BuildJLabelWithNorthWestAnchor("Data",120,25,layout,this);
        add(lblData);

        //Loop through list and set y column, x column and data column
        for (int y = 0; y < detailsList.size(); y++)
        {
            //set y position for labels to use
            int yPos = y * 20 + 50;

            //Y column
            detailsFields[y][0] = UIBuilderLibrary.BuildJLabelWithNorthWestAnchor("",20,yPos,layout,this);
            detailsFields[y][0].setText(detailsList.get(y).row + "");
            detailsFields[y][0].setPreferredSize(new Dimension(40,20));
            detailsFields[y][0].setOpaque(true);
            detailsFields[y][0].setBackground(detailsList.get(y).background);
            detailsFields[y][0].setForeground(Color.darkGray);
            detailsFields[y][0].setBorder(greyLine);
            add(detailsFields[y][0]);

            //X column
            detailsFields[y][1] = UIBuilderLibrary.BuildJLabelWithNorthWestAnchor("",60,yPos,layout,this);
            detailsFields[y][1].setText(detailsList.get(y).column + "");
            detailsFields[y][1].setPreferredSize(new Dimension(40,20));
            detailsFields[y][1].setOpaque(true);
            detailsFields[y][1].setBackground(detailsList.get(y).background);
            detailsFields[y][1].setForeground(Color.darkGray);
            detailsFields[y][1].setBorder(greyLine);
            add(detailsFields[y][1]);

            //Data column
            detailsFields[y][2] = UIBuilderLibrary.BuildJLabelWithNorthWestAnchor("",100,yPos,layout,this);
            detailsFields[y][2].setText(detailsList.get(y).value + "");
            detailsFields[y][2].setPreferredSize(new Dimension(80,20));
            detailsFields[y][2].setOpaque(true);
            detailsFields[y][2].setBackground(detailsList.get(y).background);
            detailsFields[y][2].setForeground(Color.darkGray);
            detailsFields[y][2].setBorder(greyLine);
            add(detailsFields[y][2]);
        }
    }
}
