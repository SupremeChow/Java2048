package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Controller
{
    private GridController grid;
    /*
    @FXML
    private ArrayList<ArrayList<Label>> gridLabelList;
    @FXML
    private ArrayList<Label> row1Labels;
    @FXML
    private ArrayList<Label> row2Labels;
    @FXML
    private ArrayList<Label> row3Labels;
    @FXML
    private ArrayList<Label> row4Labels;
        */
    @FXML
    private ArrayList<Label> gridLabelList;

    private Label[][] labelArray = new Label[4][4];
    private boolean runGame;
    private boolean turnDone;
    private boolean pause;

    public void initialize()
    {
        runGame = true;
        grid = new GridController();
        grid.initStartGrid();
        turnDone = false;
        /*
        gridLabelList.add(row1Labels);
        gridLabelList.add(row2Labels);
        gridLabelList.add(row3Labels);
        gridLabelList.add(row4Labels);
        */

        int count = 0;

        int boxValue;
        for(int i = 0; i < 4; i++)
        {
            for (int j = 0; j <4; j++)
            {
                labelArray[i][j] = gridLabelList.get(count);
                boxValue = grid.getBoxContent(i,j).getNumber();
                if(boxValue > 0)
                    labelArray[i][j].setText(boxValue+"");
                else
                    labelArray[i][j].setText("");
                count++;
            }
        }


    }
    public void endTurn()
    {
        turnDone=true;
    }

    public boolean getRun()
    {
        return runGame;
    }
    public GridController getGrid() {
        return grid;
    }

    public void quit(ActionEvent event)
    {
        System.out.println("Quiting Time!!");
        System.exit(0);
    }

    public void restart(ActionEvent event)
    {
        grid.clearAll();
        grid.initStartGrid();

        updateGrid();
        runGame=true;
    }

    public void updateGrid()
    {
        int boxValue;
        for(int i = 0; i < 4; i++)
        {
            for (int j = 0; j <4; j++)
            {
                boxValue = grid.getBoxContent(i,j).getNumber();
                if(grid.getBoxContent(i,j).isVisible())
                    labelArray[i][j].setText(boxValue+"");
                else
                    labelArray[i][j].setText("");

            }
        }
        grid.updateHighestNum();
        if(grid.getHighestNum() == 2048)
        {
            runGame = false;
            System.out.println("Congradulation, YOU WIN!!! Continue?");

        }
    }

    public boolean checkIfMoveAvailable()
    {
        if(grid.getNumFreeContainers()>0)
        {
            return true;
        }
        else
            return grid.findPossibleMerge();

    }
    public void iterateTurn()
    {
        boolean moveAvailable=checkIfMoveAvailable();
        if(runGame)
        {
            grid.createBox();
            updateGrid();
        }


    }


}
