package edu.rpi.legup.puzzle.shorttruthtable;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ShortTruthTableBoard extends Board {
    protected Dimension dimension;

    public ShortTruthTableBoard(int width, int height) {
        super(width * height);
        this.dimension = new Dimension();
        dimension.width = width;
        dimension.height = height;
    }

    public ShortTruthTableCell getCell(int x, int y) {
        if (x * dimension.width + y >= puzzleElements.size() || x >= dimension.width ||
                y >= dimension.height || x < 0 || y < 0) {
            return null;
        }
        return (ShortTruthTableCell) puzzleElements.get(y * dimension.width + x);
    }

    public void setCell(int x, int y, ShortTruthTableCell cell) {
        puzzleElements.set(x + y * dimension.width, cell);
    }

    public Dimension getDimension() {
        return dimension;
    }

    public int getWidth() {
        return dimension.width;
    }

    public int getHeight() {
        return dimension.height;
    }


    @Override
    public ShortTruthTableBoard copy() {
        ShortTruthTableBoard copy = new ShortTruthTableBoard(this.dimension.width, this.dimension.height);
        for (int x = 0; x < this.dimension.width; x++) {
            for (int y = 0; y < this.dimension.height; y++) {
                copy.setCell(x, y, getCell(x, y).copy());
            }
        }
        return copy;
    }
}
