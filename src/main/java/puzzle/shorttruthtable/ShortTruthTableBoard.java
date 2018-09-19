package puzzle.shorttruthtable;

import model.gameboard.GridBoard;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ShortTruthTableBoard extends GridBoard
{
    private final static Logger LOGGER = Logger.getLogger(ShortTruthTableBoard.class.getName());

    private int width;
    private int height;
    private int groupSize;

    public ShortTruthTableBoard(int width, int height)
    {
        super(width, height);
        this.width = width;
        this.height = height;
        this.groupSize = (int)Math.sqrt(dimension.width);
    }

    /**
     * Gets the ShortTruthTableCell in the specified group index at the x and y location given
     * The group index must be by less than the width (or height) of the board and the
     * x and y location is relative to the group. This means the x and y values must be
     * less the square root of the width (or height) of the board.
     *
     * @param groupIndex group index of the cell
     * @param x x location relative to the group
     * @param y y location relative to the group
     * @return cell in the specified group index at the given x and y location
     */
    public ShortTruthTableCell getCell(int groupIndex, int x, int y)
    {
        if(groupIndex >= dimension.width || x >= groupSize || y >= groupSize)
        {
            LOGGER.log(Level.SEVERE, "ShortTruthTable: Attempting to access an out of bounds cell: Group Index: " +
                    groupIndex + ", x: " + x + ", y: " + y);
            return null;
        }
        else
        {
            return (ShortTruthTableCell) getCell(x + (groupIndex % groupSize) * groupSize, y + (groupIndex / groupSize) * groupSize);
        }
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getGroupSize()
    {
        return groupSize;
    }

    /**
     * Performs a deep copy of the Board
     *
     * @return a new copy of the board that is independent of this one
     */
    @Override
    public ShortTruthTableBoard copy()
    {
        ShortTruthTableBoard newGridBoard = new ShortTruthTableBoard(width, height);
        for(int x = 0; x < this.dimension.width; x++)
        {
            for(int y = 0; y < this.dimension.height; y++)
            {
                newGridBoard.setCell(x, y, getCell(x, y).copy());
            }
        }
        return newGridBoard;
    }
}
