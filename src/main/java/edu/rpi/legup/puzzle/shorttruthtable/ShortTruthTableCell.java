package edu.rpi.legup.puzzle.shorttruthtable;

import edu.rpi.legup.model.gameboard.GridCell;

import java.awt.*;

public class ShortTruthTableCell extends GridCell<Integer>
{
    private boolean isLite;

    public ShortTruthTableCell(int valueInt, Point location)
    {
        super(valueInt, location);
        this.isLite = false;
    }

    public ShortTruthTableCellType getType()
    {
        switch(data)
        {
            case -4:
                return ShortTruthTableCellType.BULB;
            case -3:
                return ShortTruthTableCellType.EMPTY;
            case -2:
                return ShortTruthTableCellType.UNKNOWN;
            case -1:
                return ShortTruthTableCellType.BLACK;
            default:
                if(data >= 0)
                {
                    return ShortTruthTableCellType.NUMBER;
                }
        }
        return null;
    }

    public boolean isLite()
    {
        return isLite;
    }

    public void setLite(boolean isLite)
    {
        this.isLite = isLite;
    }

    @Override
    public ShortTruthTableCell copy()
    {
        ShortTruthTableCell copy = new ShortTruthTableCell(data, (Point)location.clone());
        copy.setIndex(index);
        copy.setModifiable(isModifiable);
        copy.setGiven(isGiven);
        return copy;
    }
}
