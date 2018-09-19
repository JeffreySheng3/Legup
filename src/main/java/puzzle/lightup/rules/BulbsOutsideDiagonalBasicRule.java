package puzzle.lightup.rules;

import model.rules.BasicRule;
import model.tree.TreeNode;
import model.tree.TreeTransition;
import puzzle.lightup.LightUpBoard;
import puzzle.lightup.LightUpCell;
import puzzle.lightup.LightUpCellType;

import java.awt.*;

public class BulbsOutsideDiagonalBasicRule extends BasicRule
{

    public BulbsOutsideDiagonalBasicRule()
    {
        super("Bulbs Outside Diagonal", "Cells on the external edges of a 3 diagonal to a 1 block must be bulbs.", "images/lightup/rules/BulbsOutsideDiagonal.png");
    }

    /**
     * Checks whether the child node logically follows from the parent node
     * at the specific element index using this rule
     *
     * @param transition   transition to check
     * @param elementIndex index of the element
     *
     * @return null if the child node logically follow from the parent node at the specified element,
     * otherwise error message
     */
    @Override
    public String checkRuleAt(TreeTransition transition, int elementIndex)
    {
        LightUpBoard initialBoard = (LightUpBoard)transition.getParentNode().getBoard();
        LightUpBoard finalBoard = (LightUpBoard)transition.getBoard();
        LightUpCell cell = (LightUpCell)finalBoard.getElementData(elementIndex);

        Point location = cell.getLocation();

        LightUpCell numCell = getNumberCell(finalBoard,location);
        if(numCell == null) return "No number cell.";

        int numberDiagonal = numDiagonal(finalBoard, numCell.getLocation());
        if (numberDiagonal != 1) return "More/less than 1 cell diagonal";

        LightUpCell diagonalCell = getDiagonalCell(finalBoard,numCell.getLocation());

        return null;
    }

    /**
     * Checks whether the child node logically follows from the parent node using this rule
     * and if so will perform the default application of the rule
     *
     * @param transition transition to apply default application
     *
     * @return true if the child node logically follow from the parent node and accepts the changes
     * to the board, otherwise false
     */
    @Override
    public boolean doDefaultApplication(TreeTransition transition)
    {
        return false;
    }

    /**
     * Checks whether the child node logically follows from the parent node at the
     * specific element index using this rule and if so will perform the default application of the rule
     *
     * @param transition   transition to apply default application
     * @param elementIndex
     *
     * @return true if the child node logically follow from the parent node and accepts the changes
     * to the board, otherwise false
     */
    @Override
    public boolean doDefaultApplicationAt(TreeTransition transition, int elementIndex)
    {
        return false;
    }

    private LightUpCell getNumberCell(LightUpBoard b, Point original) {
        LightUpCell cell = b.getCell(original.x,original.y+1);
        LightUpCell result = null;
        if (cell != null && cell.getType() == LightUpCellType.NUMBER) result = cell;
        cell = b.getCell(original.x,original.y-1);
        if (cell != null && cell.getType() == LightUpCellType.NUMBER) result = cell;
        cell = b.getCell(original.x+1,original.y);
        if (cell != null && cell.getType() == LightUpCellType.NUMBER) result = cell;
        cell = b.getCell(original.x-1,original.y);
        if (cell != null && cell.getType() == LightUpCellType.NUMBER) result = cell;
        return result;
    }

    private int numDiagonal(LightUpBoard b, Point original) {
        int ret = 0;
        LightUpCell cell = b.getCell(original.x+1, original.y+1);
        if (cell != null && cell.getType() == LightUpCellType.NUMBER) ret++;
        cell = b.getCell(original.x-1, original.y+1);
        if (cell != null && cell.getType() == LightUpCellType.NUMBER) ret++;
        cell = b.getCell(original.x+1, original.y-1);
        if (cell != null && cell.getType() == LightUpCellType.NUMBER) ret++;
        cell = b.getCell(original.x-1, original.y-1);
        if (cell != null && cell.getType() == LightUpCellType.NUMBER) ret++;
        return ret;
    }

    private LightUpCell getDiagonalCell(LightUpBoard b, Point original) {
        LightUpCell cell = b.getCell(original.x+1, original.y+1);
        if (cell != null && cell.getType() == LightUpCellType.NUMBER) return cell;
        cell = b.getCell(original.x-1, original.y+1);
        if (cell != null && cell.getType() == LightUpCellType.NUMBER) return cell;
        cell = b.getCell(original.x+1, original.y-1);
        if (cell != null && cell.getType() == LightUpCellType.NUMBER) return cell;
        cell = b.getCell(original.x-1, original.y-1);
        if (cell != null && cell.getType() == LightUpCellType.NUMBER) return cell;
        return null;
    }
}
