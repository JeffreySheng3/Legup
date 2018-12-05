package edu.rpi.legup.puzzle.shorttruthtable;

public enum ShortTruthTableCellType
{
    EMPTY(-2), SYMBOL(-1), VARIABLE(0);

    public int value;
    ShortTruthTableCellType(int value) {
        this.value = value;
    }
}
