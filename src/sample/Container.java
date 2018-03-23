package sample;

public class Container
{
    private NumberBox numBox;
    private boolean isFilled;
    private boolean recentMerge; //Use to track if the box was recently merged for a turn

    public Container()
    {
        numBox = new NumberBox();
        isFilled= false;
        recentMerge = false;
    }

    public NumberBox getNumBox() {
        return numBox;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setNumBox(NumberBox numBox) {
        this.numBox = numBox;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }
    public void setMerged(boolean mergeState)
    {
        recentMerge = mergeState;
    }

    public void shiftNumBox(Container newContainer)
    {
        int newNum =newContainer.numBox.getNumber();
        numBox.setNum(newNum);
        if(newNum>0)
        {
            numBox.setVisible(true);
        }
        isFilled=true;
        recentMerge=newContainer.recentMerge;
    }
    public void initBox()
    {
        isFilled=true;
        numBox.randInit();
    }
    public void moveBox()
    {
        isFilled = false;
        numBox.setNum(0);
    }

    public boolean wasMerged()
    {
        return recentMerge;
    }
    public void iterateMerge()
    {
        int tempNum = numBox.getNumber();
        numBox.setNum(tempNum*2);
        recentMerge = true;
    }
    public void resetToEmpty()
    {
        numBox.resetToEmpty();
        isFilled = false;
        recentMerge = false; //Probably redundant, but could be used to reset game
    }
}
