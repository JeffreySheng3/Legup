package edu.rpi.legup.puzzle.shorttruthtable;

import edu.rpi.legup.model.gameboard.GridBoard;
import edu.rpi.legup.model.gameboard.PuzzleElement;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ShortTruthTableBoard extends GridBoard {
    public ShortTruthTableBoard(int width, int height) {
        super(width, height);
    }

//    public ShortTruthTableBoard(int size) {
//        super(size, size);
//    }

    public void fillWithLight() {
        for (int y = 0; y < this.dimension.height; y++) {
            for (int x = 0; x < this.dimension.width; x++) {
                getCell(x, y).setLite(false);
            }
        }

        for (int y = 0; y < this.dimension.height; y++) {
            for (int x = 0; x < this.dimension.width; x++) {
                ShortTruthTableCell cell = getCell(x, y);
                if (cell.getType() == ShortTruthTableCellType.BULB) {
                    cell.setLite(true);
                    for (int i = x + 1; i < this.dimension.width; i++) {
                        ShortTruthTableCell c = getCell(i, y);
                        if (c.getType() == ShortTruthTableCellType.NUMBER || c.getType() == ShortTruthTableCellType.BLACK) {
                            break;
                        }
                        c.setLite(true);
                    }
                    for (int i = x - 1; i >= 0; i--) {
                        ShortTruthTableCell c = getCell(i, y);
                        if (c.getType() == ShortTruthTableCellType.NUMBER || c.getType() == ShortTruthTableCellType.BLACK) {
                            break;
                        }
                        c.setLite(true);
                    }
                    for (int i = y + 1; i < this.dimension.height; i++) {
                        ShortTruthTableCell c = getCell(x, i);
                        if (c.getType() == ShortTruthTableCellType.NUMBER || c.getType() == ShortTruthTableCellType.BLACK) {
                            break;
                        }
                        c.setLite(true);
                    }
                    for (int i = y - 1; i >= 0; i--) {
                        ShortTruthTableCell c = getCell(x, i);
                        if (c.getType() == ShortTruthTableCellType.NUMBER || c.getType() == ShortTruthTableCellType.BLACK) {
                            break;
                        }
                        c.setLite(true);
                    }
                }
            }
        }
    }

    public Set<ShortTruthTableCell> getAdj(ShortTruthTableCell cell) {
        Set<ShortTruthTableCell> adjCells = new HashSet<>();
        cell = (ShortTruthTableCell) getPuzzleElement(cell);

        Point loc = cell.getLocation();
        ShortTruthTableCell up = getCell(loc.x, loc.y + 1);
        if (up != null) {
            adjCells.add(up);
        }
        ShortTruthTableCell down = getCell(loc.x, loc.y - 1);
        if (down != null) {
            adjCells.add(down);
        }
        ShortTruthTableCell right = getCell(loc.x + 1, loc.y);
        if (right != null) {
            adjCells.add(right);
        }
        ShortTruthTableCell left = getCell(loc.x - 1, loc.y);
        if (left != null) {
            adjCells.add(left);
        }
        return adjCells;
    }

    public int getNumAdj(ShortTruthTableCell cell, ShortTruthTableCellType type) {
        int num = 0;
        Set<ShortTruthTableCell> adjCells = getAdj(cell);
        for(ShortTruthTableCell c : adjCells) {
            if(c.getType() == type) {
                num++;
            }
        }
        return num;
    }

    public int getNumAdjLite(ShortTruthTableCell cell) {
        int num = 0;
        Set<ShortTruthTableCell> adjCells = getAdj(cell);
        for(ShortTruthTableCell c : adjCells) {
            if(c.isLite()) {
                num++;
            }
        }
        return num;
    }

    public int getNumPlacble(ShortTruthTableCell cell) {
        int num = 0;
        Set<ShortTruthTableCell> adjCells = getAdj(cell);
        for(ShortTruthTableCell c : adjCells) {
            if(c.getType() == ShortTruthTableCellType.UNKNOWN && !c.isLite()) {
                num++;
            }
        }
        return num;
    }

    @Override
    public ShortTruthTableCell getCell(int x, int y) {
        return (ShortTruthTableCell) super.getCell(x, y);
    }

    @Override
    public void notifyChange(PuzzleElement puzzleElement) {
        super.notifyChange(puzzleElement);
        fillWithLight();
    }

    @Override
    public ShortTruthTableBoard copy() {
        ShortTruthTableBoard copy = new ShortTruthTableBoard(dimension.width, dimension.height);
        for (int x = 0; x < this.dimension.width; x++) {
            for (int y = 0; y < this.dimension.height; y++) {
                copy.setCell(x, y, getCell(x, y).copy());
            }
        }
        copy.fillWithLight();
        return copy;
    }
}
