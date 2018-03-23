package sample;
import java.util.Scanner;//Use this to test and debug, remove later

public class GridController
{
    private Container[][] grid;
    private int numFreeContainers;
    private int highestNum; //Use to track the highest number
    private Scanner keyboard;

    public GridController()
    {
        keyboard=new Scanner(System.in);
        highestNum=0;
        numFreeContainers= 16; //4x4 Grid
        grid = new Container[4][4];
        for (int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                grid[i][j] = new Container();
            }
        }
    }
    public NumberBox getBoxContent(int i, int j)
    {
        return grid[i][j].getNumBox();
    }

    public int getHighestNum()
    {
        return highestNum;
    }

    public int getNumFreeContainers()
    {
        return numFreeContainers;
    }

    public Container getContainer(int i, int j)
    {
        return grid[i][j];
    }
    public void initStartGrid()
    {
        int [] point = randPoint();
        grid[point[0]][point[1]].initBox();

        do {
            point = randPoint();
        }while(grid[point[0]][point[1]].isFilled()); //This will loop to find another non filled Container. Use this same method later, EXCEPT check with a count/flag to
        //ensure that a filled grid does not infinately loop and crash game (i.e. a Game Over scenario)
        grid[point[0]][point[1]].initBox();

        numFreeContainers = numFreeContainers - 2;
        updateHighestNum();

    }

    /**
     * Will return a boolean so that the main controller will have an idea if close to game over
     */
    public boolean createBox()
    {
        int [] point;
        if(numFreeContainers != 0) // only create a new random box if there is space
        {
            do {
                point = randPoint();
                System.out.println("Trying to make box at:"+ point[0] + ", "+point[1]);
            }while(grid[point[0]][point[1]].isFilled()); // loop through until a empty space is found
            System.out.println("Point selected at:"+ point[0] + ", "+point[1]);
            grid[point[0]][point[1]].initBox();
            numFreeContainers--;
            return true;
        }
        else
        {
            System.out.println("There are no more boxes available to make a new one!");
            return false;
        }


    }

    /**
     * Process of shifting boxes right if possible
     * Should note that Main Controler of game (as of now) will evaluate beforehand what valid moves are available(This will help in
     * controlling/disabling moves and evaluating game state) Whether the actual movement is possible is up to the Main Controller
     *
     * Returns a boolean of anything has changed
    */

    public boolean shiftRight() throws Exception
    {
        boolean hasChanged = false;
         //Order of things: Merge first, then shift
        Container target, compare;
        int downRowCheck;
        for(int i = 0; i < 4; i++)
        {
            for (int j = 2; j >= 0; j--) //will only check first three columns
            {

                target = grid[i][j];

                System.out.println("Target:: i: " + i + ", j: " + j + "; targetNum: " + target.getNumBox().getNumber());

                if(target.isFilled())
                {
                    downRowCheck = 1;
                    while ((j + downRowCheck) < 4) {
                        System.out.println("Compare:: i: " + i + ", j: " + (j + downRowCheck) + "; CompareNum: " + grid[i][j + downRowCheck].getNumBox().getNumber());
                       // keyboard.next();
                        if (!grid[i][j + downRowCheck].isFilled())
                            downRowCheck++;
                        else
                            break;
                    }
                    if (downRowCheck + j >= 4) //case where there is no other box to check against
                    {
                        System.out.println("There should be no comparable box");
                       // keyboard.next();
                        continue;
                    } else {
                        System.out.println("Comparable found at i:" + i + ", j:" + (j + downRowCheck));
                       // keyboard.next();
                        compare = grid[i][j + downRowCheck];
                    }

                    System.out.println(("TargetNum: " + target.getNumBox().getNumber() + ", Compare: " + compare.getNumBox().getNumber()));
                    //keyboard.next();
                    //Check if equal to merge
                    if (target.getNumBox().getNumber() == compare.getNumBox().getNumber() && !compare.wasMerged()) {
                        System.out.println("Equal!!! NOW MERGING!");
                        //keyboard.next();
                        compare.iterateMerge();
                        target.resetToEmpty();
                        numFreeContainers++;
                        hasChanged= true;
                        //Thread.sleep(2000);
                    } else {
                        System.out.println("NOT EQUAL, SKIPPING!");
                       // keyboard.next();
                    }
                }
            }
        }

        System.out.println("NOW SHIFTING!!!!!!!!!!!!!!!!!!!");
       // keyboard.next();
        //Shift movement
        for(int i = 0; i < 4; i++)
        {
            for (int j = 2; j >= 0; j--) //will only check first three columns
            {
                target = grid[i][j];
                System.out.println("i: " + i + ", j: " + j + "; targetNum: " + target.getNumBox().getNumber());
                if(target.isFilled())//only check if something to shift
                {
                    System.out.println("Box contains number, attempting to shift!");
                    //keyboard.next();
                    downRowCheck = 1;

                    while ((j + downRowCheck) < 4)
                    {
                        System.out.println("Compare:: i: " + i + ", j: " + (j+downRowCheck) + "; compareNum: " + grid[i][j+downRowCheck].getNumBox().getNumber());
                        //keyboard.next();
                        if(!grid[i][j + downRowCheck].isFilled())
                        {
                            downRowCheck++;
                        }

                        else
                            break;
                    }
                    if (downRowCheck + j >= 4) //case where there is no other box to check against
                    {
                        System.out.println("There should be no comparable box, shifting to last");
                        //keyboard.next();
                        compare = grid[i][3];

                        System.out.println("Compare:: i: " + i + ", j: " + (3) + "; compareNum: " + compare.getNumBox().getNumber());
                        compare.shiftNumBox(target);
                        target.resetToEmpty();
                        System.out.println("Boxes shifted");
                        System.out.println("Target:: i: " + i + ", j: " + (j) + "; targetNum: " + target.getNumBox().getNumber()+"isFIlled: " + String.valueOf(target.isFilled()));
                        System.out.println("Compare:: i: " + i + ", j: " + (3) + "; compareNum: " + compare.getNumBox().getNumber()+"isFIlled: " + String.valueOf(compare.isFilled()));

                        hasChanged = true;
                       // keyboard.next();
                    }

                    else if (downRowCheck - 1 == 0)//there is no space, so no shift
                    {
                        System.out.println("Compare:: i: " + i + ", j: " + (j+downRowCheck-1) + "; compareNum: ");
                        System.out.println("Target:: i: " + i + ", j: " + (j) + "; targetNum: " + target.getNumBox().getNumber());
                        System.out.println("Should be same position, so no shifting!!!!");
                       // keyboard.next();
                        //break;//Do nothing
                    }

                    else
                    {
                        System.out.println("Space found, attempting to shift");
                        compare = grid[i][j + downRowCheck - 1];

                        System.out.println("Compare:: i: " + i + ", j: " + (j+downRowCheck-1) + "; compareNum: "+ compare.getNumBox().getNumber());
                        System.out.println("Target:: i: " + i + ", j: " + (j) + "; targetNum: " + target.getNumBox().getNumber());
                        //keyboard.next();
                        compare.shiftNumBox(target);
                        target.resetToEmpty();
                        System.out.println("Boxes Shifted...");
                        System.out.println("Compare:: i: " + i + ", j: " + (j+downRowCheck-1) + "; compareNum: "+ compare.getNumBox().getNumber());
                        System.out.println("Target:: i: " + i + ", j: " + (j) + "; targetNum: " + target.getNumBox().getNumber());
                        hasChanged = true;
                        //keyboard.next();
                    }


                    //Thread.sleep(2000);
                }
            }
        }
        return hasChanged;
    }

    /**
     * Process of shifting boxes Left if possible
     * Should note that Main Controler of game (as of now) will evaluate beforehand what valid moves are available(This will help in
     * controlling/disabling moves and evaluating game state) Whether the actual movement is possible is up to the Main Controller
     */
    public boolean shiftLeft() throws Exception
    {
        //Order of things: Merge first, then shift
        boolean hasChanged = false;
        Container target, compare;
        int downRowCheck;
        for(int i = 0; i < 4; i++)
        {
            for (int j = 1; j <= 3; j++) //will only check first three columns
            {

                target = grid[i][j];
                if(target.isFilled())
                {
                    downRowCheck = 1;
                    while ((j - downRowCheck) >= 0) {
                        if (!grid[i][j - downRowCheck].isFilled())
                            downRowCheck++;
                        else
                            break;
                    }
                    if (j - downRowCheck < 0) //case where there is no other box to check against
                        continue;
                    else
                        compare = grid[i][j - downRowCheck];

                    //Check if equal to merge
                    if (target.getNumBox().getNumber() == compare.getNumBox().getNumber() && !compare.wasMerged()) {
                        compare.iterateMerge();
                        target.resetToEmpty();
                        numFreeContainers++;
                        hasChanged=true;
                        //Thread.sleep(2000);
                    }
                }
            }
        }

        //Shift movement
        for(int i = 0; i < 4; i++)
        {
            for (int j = 1; j <= 3; j++) //will only check first three columns
            {
                target = grid[i][j];
                if(target.isFilled())//only check if something to shift
                {
                    downRowCheck = 1;

                    while ((j - downRowCheck) >= 0)
                    {
                        if(!grid[i][j - downRowCheck].isFilled())
                            downRowCheck++;
                        else
                            break;
                    }
                    if (j-downRowCheck < 0) //case where there is no other box to check against
                    {
                        compare = grid[i][0];
                        compare.shiftNumBox(target);
                        target.resetToEmpty();
                        hasChanged=true;
                    }

                    else if (downRowCheck - 1 == 0)//there is no space, so no shift
                    {
                        //break; //do nothing
                    }

                    else
                    {
                        compare = grid[i][j - downRowCheck + 1];

                        compare.shiftNumBox(target);
                        target.resetToEmpty();
                        hasChanged=true;
                    }

                    //Thread.sleep(2000);
                }
            }
        }
        return hasChanged;
    }

    /**
     * Process of shifting boxes down if possible
     * Should note that Main Controler of game (as of now) will evaluate beforehand what valid moves are available(This will help in
     * controlling/disabling moves and evaluating game state) Whether the actual movement is possible is up to the Main Controller
     */

    public boolean shiftDown() throws Exception
        {
            boolean hasChanged = false;
        //Order of things: Merge first, then shift
        Container target, compare;
        int downRowCheck;
        for(int i = 0; i < 4; i++)
        {
            for (int j = 2; j >= 0; j--) //will only check first three columns
            {

                target = grid[j][i];
                System.out.println("Target:: i: " + j + ", j: " + i + "; targetNum: " + target.getNumBox().getNumber());
                if(target.isFilled())
                {
                    downRowCheck = 1;
                    while ((j + downRowCheck) < 4)
                    {
                        System.out.println("Compare:: i: " + (j + downRowCheck) + ", j: " + i + "; CompareNum: " + grid[j+downRowCheck][i].getNumBox().getNumber());
                        //keyboard.next();
                        if (!grid[j + downRowCheck][i].isFilled())
                            downRowCheck++;
                        else
                            break;
                    }
                    if (downRowCheck + j >= 4) //case where there is no other box to check against
                    {
                        System.out.println("There should be no comparable box");
                       // keyboard.next();
                        continue;
                    }

                    else
                    {
                        System.out.println("Comparable found at i:" + (j + downRowCheck) + ", j:" + i);
                        // keyboard.next();
                        compare = grid[j + downRowCheck][i];
                    }
                    //Check if equal to merge
                    if (target.getNumBox().getNumber() == compare.getNumBox().getNumber() && !compare.wasMerged())
                    {
                        System.out.println("Equal!!! NOW MERGING!");
                        //keyboard.next();
                        compare.iterateMerge();
                        target.resetToEmpty();
                        numFreeContainers++;
                        hasChanged=true;
                        //Thread.sleep(2000);
                    }
                    else
                    {
                        System.out.println("Not Equal!!! Skipping!");
                       // keyboard.next();
                    }
                }
            }
        }

        //Shift movement
            System.out.println("NOW SHIFTING!!!!!!!!!!!!!!!!!!!");
        for(int i = 0; i < 4; i++)
        {
            for (int j = 2; j >= 0; j--) //will only check first three columns
            {
                target = grid[j][i];
                System.out.println("Target:: i: " + j + ", j: " + i + "; targetNum: " + target.getNumBox().getNumber());
                if(target.isFilled())//only check if something to shift
                {
                    System.out.println("Box contains number, attempting to shift!");
                    //keyboard.next();
                    downRowCheck = 1;

                    while ((j + downRowCheck) < 4)
                    {
                        System.out.println("Compare:: i: " + (j + downRowCheck) + ", j: " + i + "; CompareNum: " + grid[j+downRowCheck][i].getNumBox().getNumber());
                        //keyboard.next();
                        if(!grid[j+downRowCheck][i].isFilled())
                            downRowCheck++;
                        else
                            break;
                    }
                    if (downRowCheck + j >= 4) //case where there is no other box to check against
                    {
                        System.out.println("There should be no comparable box, shifting to last");
                        //keyboard.next();
                        compare = grid[3][i];
                        System.out.println("Compare:: i: " + (3) + ", j: " + (i) + "; compareNum: " + compare.getNumBox().getNumber());
                        compare.shiftNumBox(target);
                        target.resetToEmpty();
                        System.out.println("Boxes shifted");
                        System.out.println("Target:: i: " + j + ", j: " + (i) + "; targetNum: " + target.getNumBox().getNumber());
                        System.out.println("Compare:: i: " + (3) + ", j: " + (i) + "; compareNum: " + compare.getNumBox().getNumber());
                        hasChanged=true;
                        //keyboard.next();
                    }

                    else if (downRowCheck - 1 == 0)//there is no space, so no shift
                    {
                        System.out.println("Compare:: i: " + (j+downRowCheck-1) + ", j: " + (i) + "; compareNum: ");
                        System.out.println("Target:: i: " + j + ", j: " + (i) + "; targetNum: " + target.getNumBox().getNumber());
                        System.out.println("Should be same position, so no shifting!!!!");
                       // keyboard.next();
                        //break;//do nothing
                    }

                    else
                    {
                        System.out.println("Space found, attempting to shift");
                        compare = grid[j+downRowCheck-1][i];

                        System.out.println("Compare:: i: " + (j+downRowCheck-1) + ", j: " + i + "; compareNum: "+ compare.getNumBox().getNumber());
                        System.out.println("Target:: i: " + j + ", j: " + (i) + "; targetNum: " + target.getNumBox().getNumber());
                       // keyboard.next();
                        compare.shiftNumBox(target);
                        target.resetToEmpty();
                        System.out.println("Boxes Shifted...");
                        System.out.println("Compare:: i: " + (j+downRowCheck-1) + ", j: " + i + "; compareNum: "+ compare.getNumBox().getNumber());
                        System.out.println("Target:: i: " + j + ", j: " + (i) + "; targetNum: " + target.getNumBox().getNumber());
                        hasChanged=true;
                        //keyboard.next();
                    }




                    //Thread.sleep(2000);
                }
            }
        }
        return hasChanged;
    }
    /**
     * Process of shifting boxes Up if possible
     * Should note that Main Controler of game (as of now) will evaluate beforehand what valid moves are available(This will help in
     * controlling/disabling moves and evaluating game state) Whether the actual movement is possible is up to the Main Controller
     */
    public boolean shiftUp() throws Exception
    {
        //Order of things: Merge first, then shift
        boolean hasChanged = false;
        Container target, compare;
        int downRowCheck;
        for(int i = 0; i < 4; i++)
        {
            for (int j = 1; j <= 3; j++) //will only check first three columns
            {

                target = grid[j][i];
                if(target.isFilled())
                {
                    downRowCheck = 1;
                    while ((j - downRowCheck) >= 0)
                    {
                        if (!grid[j - downRowCheck][i].isFilled())
                            downRowCheck++;
                        else
                            break;
                    }
                    if (j - downRowCheck < 0) //case where there is no other box to check against
                        continue;
                    else
                        compare = grid[j - downRowCheck][i];

                    //Check if equal to merge
                    if (target.getNumBox().getNumber() == compare.getNumBox().getNumber() && !compare.wasMerged())
                    {
                        compare.iterateMerge();
                        target.resetToEmpty();
                        numFreeContainers++;
                        hasChanged=true;
                        //Thread.sleep(2000);
                    }
                }
            }
        }

        //Shift movement
        for(int i = 0; i < 4; i++)
        {
            for (int j = 1; j <= 3; j++) //will only check first three columns
            {
                target = grid[j][i];
                if(target.isFilled())//only check if something to shift
                {
                    downRowCheck = 1;

                    while ((j - downRowCheck) >= 0)
                    {
                        if(!grid[j-downRowCheck][i].isFilled())
                            downRowCheck++;
                        else
                            break;
                    }
                    if (j-downRowCheck < 0) //case where there is no other box to check against
                    {
                        compare = grid[0][i];

                        compare.shiftNumBox(target);
                        target.resetToEmpty();
                        hasChanged=true;
                    }

                    else if (downRowCheck - 1 == 0)//there is no space, so no shift
                    {
                        //break;//do nothing
                    }

                    else
                    {
                        compare = grid[j - downRowCheck + 1][i];

                        compare.shiftNumBox(target);
                        target.resetToEmpty();
                        hasChanged=true;
                    }

                    //Thread.sleep(2000);
                }
            }
        }
        return hasChanged;
    }

    public int[] randPoint()
    {
        int randX = (int)(Math.random()*4);
        int randY = (int)(Math.random()*4);
        int[] randomPoint = {randX, randY};
        return randomPoint;
    }

    public void updateHighestNum()
    {
        int target;
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                target = grid[i][j].getNumBox().getNumber();
                if(target >= highestNum)
                    highestNum = target;
            }
        }
    }

    /**
     * Use this method to clear the merge boolean after a turn
     */
    public void flushMerge()
    {
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                grid[i][j].setMerged(false);
            }
        }
    }

    public void clearAll()
    {
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                grid[i][j].resetToEmpty();
            }
        }
        numFreeContainers=16;
    }

    public boolean findPossibleMerge()
    {
        boolean mergeFound = false;
        int targetNum;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                targetNum = grid[i][j].getNumBox().getNumber();
                if(targetNum == grid[i][j+1].getNumBox().getNumber()|| targetNum==grid[i+1][j].getNumBox().getNumber())
                {
                    mergeFound = true;
                }
            }
        }
        return mergeFound;
    }
}
