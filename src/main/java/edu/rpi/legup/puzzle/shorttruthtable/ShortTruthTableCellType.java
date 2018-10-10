package edu.rpi.legup.puzzle.shorttruthtable;

public enum ShortTruthTableCellType
{
    BULB(-4), EMPTY(-3), UNKNOWN(-2), BLACK(-1), NUMBER(0);

    public int value;
    ShortTruthTableCellType(int value) {
        this.value = value;
    }
}
