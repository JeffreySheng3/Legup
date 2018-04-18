package puzzle.fillapix.rules;

import model.rules.ContradictionRule;
import model.tree.TreeTransition;
import puzzle.fillapix.FillapixBoard;
import puzzle.fillapix.FillapixCell;

public class TooManyBlackCellsContradictionRule extends ContradictionRule
{

    public TooManyBlackCellsContradictionRule()
    {
        super("Too Many Black Cells",
                "There may not be more black cells than the number",
                "images/fillapix/contradictions/TooManyBlackCells.png");
    }

    @Override
    public String checkContradictionAt(TreeTransition transition, int elementIndex)
    {
        FillapixBoard fillapixBoard = (FillapixBoard) transition.getBoard();
        int width = fillapixBoard.getWidth();
        FillapixCell cell = (FillapixCell) fillapixBoard.getElementData(elementIndex);
        if (cell.getValueInt() != -1) {
            int numBlackCells = fillapixBoard.getNumCells(cell, FillapixCell.BLACK);
            System.err.println("Clue: " + cell.getValueInt() + " num cells: " + numBlackCells);
            if (numBlackCells > cell.getValueInt()) {
                return null;
            }
        }
        return "Board does not contain a contradiction";
    }

    public String checkContradictionAt(FillapixBoard fillapixBoard, int elementIndex)
    {
        int width = fillapixBoard.getWidth();
        FillapixCell cell = fillapixBoard.getCell(elementIndex%width,elementIndex/width);
        if (cell.getValueInt() != -1) {
            int numBlackCells = fillapixBoard.getNumCells(cell, FillapixCell.BLACK);
            if (numBlackCells > cell.getValueInt()) {
                return null;
            }
        }
        return "Board does not contain a contradiction";
    }

    @Override
    public boolean doDefaultApplication(TreeTransition transition)
    {
        return false;
    }

    @Override
    public boolean doDefaultApplicationAt(TreeTransition transition, int elementIndex)
    {
        return false;
    }
}
