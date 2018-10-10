package edu.rpi.legup.puzzle.shorttruthtable;

public enum LightUpCellType
{
    BULB(-4), EMPTY(-3), UNKNOWN(-2), BLACK(-1), NUMBER(0);

    public int value;
    LightUpCellType(int value) {
        this.value = value;
    }
}
