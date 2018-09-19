package puzzle.shorttruthtable;

import model.gameboard.GridCell;

import java.awt.*;

public class ShortTruthTableCell extends GridCell
{
    public ShortTruthTableCell(int value, Point location)
    {
        super(value, location);
    }

    /**
     * Performs a deep copy on the ShortTruthTableCell
     *
     * @return a new copy of the ShortTruthTableCell that is independent of this one
     */
    @Override
    public ShortTruthTableCell copy()
    {
        ShortTruthTableCell copy = new ShortTruthTableCell(valueInt, (Point)location.clone());
        copy.setIndex(index);
        copy.setModifiable(isModifiable);
        copy.setGiven(isGiven);
        return copy;
    }
}
