package edu.rpi.legup.puzzle.shorttruthtable;

import edu.rpi.legup.model.gameboard.PuzzleElement;
import java.awt.*;

public class ShortTruthTableCell extends PuzzleElement<Integer>
{
    private char letter;
    private char connective;
    private boolean isAtomic;
    private Boolean val;
    private Point loc;

    public ShortTruthTableCell(int data)
    {
        super(data);
        this.loc = new Point();
    }

    public void setLetter(char letter) {
        this.isAtomic = true;
        this.letter = letter;
        this.setData(0);
    }

    public void setValue(boolean val) { this.val = val; }
    public Boolean getValue() { return this.val; }

    public char getLetter() {
        return this.letter;
    }

    public void setConnective(char c) {
        this.isAtomic = !(c=='(' || c==')');
        this.connective = c;
        this.setData(-1);
    }

    public boolean isAtomic(){return isAtomic;}

    public char getConnective() {
        return this.connective;
    }

    public ShortTruthTableCellType getType()
    {
        switch(data)
        {
            case -2:
                return ShortTruthTableCellType.EMPTY;
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

    public void setLocation(int x, int y) {
        loc.x = x;
        loc.y = y;
    }

    public Point getLocation() {
        return loc;
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
