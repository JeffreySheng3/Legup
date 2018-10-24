package edu.rpi.legup.puzzle.shorttruthtable;

import edu.rpi.legup.model.gameboard.PuzzleElement;

public class ShortTruthTableCell extends PuzzleElement<Integer>
{
    private char letter;
    private char connective;
    private boolean isAtomic;
    private Boolean val;

    public ShortTruthTableCell(int data)
    {
        super(data);
    }

    public ShortTruthTableCellType getType()
    {
        switch(data)
        {
            case -2:
                return ShortTruthTableCellType.SPACE;
            case -1:
                return ShortTruthTableCellType.SYMBOL;
            default:
                if(data >= 0)
                {
                    return ShortTruthTableCellType.VARIABLE;
                }
        }
        return null;
    }

    @Override
    public ShortTruthTableCell copy()
    {
        ShortTruthTableCell copy = new ShortTruthTableCell(data);
        copy.setIndex(index);
        copy.setModifiable(isModifiable);
        copy.setGiven(isGiven);
        return copy;
    }
}
