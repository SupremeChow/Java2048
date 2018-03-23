package sample;

public class NumberBox
{
    private int heldNum;
    private boolean isVisible;


    public NumberBox()
    {
        heldNum = 0;
        isVisible = false;
    }



    public boolean isVisible() {
        return isVisible;
    }


    public int getNumber()
    {
        return heldNum;
    }

    public void setNum(int newNum)
    {
        heldNum = newNum;
        if(heldNum == 0)
            isVisible = false;
    }
    public void setVisible(boolean visible) {
        isVisible = visible;
    }


    /**
     * This Method will handle random assignment of either 2 or 4
     * */
    public void randInit()
    {
        int randChance = (int)Math.random()*10;
        System.out.println(randChance);

        if(randChance<9)
            heldNum= 2;
        else
            heldNum = 4;

        isVisible = true;
    }

    /**
     * Resets a number box to be an empty value after either a merge or shift
     */
    public void resetToEmpty()
    {
        heldNum = 0;
        isVisible = false;
    }



    /**
     * Completes a merge on this box by iterating to it's doubled value
     */
    public void iterateMerge()
    {

    }
}
