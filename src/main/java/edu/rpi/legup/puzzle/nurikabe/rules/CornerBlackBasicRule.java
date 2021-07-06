package edu.rpi.legup.puzzle.nurikabe.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.BasicRule;
import edu.rpi.legup.model.rules.ContradictionRule;
import edu.rpi.legup.model.tree.TreeNode;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.nurikabe.NurikabeBoard;
import edu.rpi.legup.puzzle.nurikabe.NurikabeCell;
import edu.rpi.legup.puzzle.nurikabe.NurikabeType;
import edu.rpi.legup.utility.ConnectedRegions;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public class CornerBlackBasicRule extends BasicRule {

    public CornerBlackBasicRule() {
        super("Corners Black",
                "If there is only one white square connected to unknowns and one more white is needed then the angles of that white square are black",
                "edu/rpi/legup/images/nurikabe/rules/CornerBlack.png");
    }

    /**
     * Checks whether the child node logically follows from the parent node
     * at the specific puzzleElement index using this rule
     *
     * @param transition    transition to check
     * @param puzzleElement equivalent puzzleElement
     * @return null if the child node logically follow from the parent node at the specified puzzleElement,
     * otherwise error message
     */

    /*
    - Check corners for single number cell
    - Check number cell's number
    - Check if number cell is connected to any other white cells
    - If not connected to any other white cells and cell number > 2, return true
     */
    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) {
        NurikabeBoard destBoardState = (NurikabeBoard) transition.getBoard();

        NurikabeCell cell = (NurikabeCell) destBoardState.getPuzzleElement(puzzleElement);
        if(cell.getType() != NurikabeType.BLACK){
            System.out.print("Cell type error");
            return "Only black cells allowed for this rule.";
        }

        int x = cell.getLocation().x;
        int y = cell.getLocation().y;
        int width = destBoardState.getWidth();
        int height = destBoardState.getHeight();

        NurikabeType topLeftCell ;
        NurikabeType topRightCell;
        NurikabeType downLeftCell;
        NurikabeType downRightCell;
        ArrayList<NurikabeCell> whiteCells = new ArrayList<>();

        //Check around new black cell for amount of number cells
        if(y-1 >= 0 && x-1 >= 0){
            topLeftCell = destBoardState.getCell(x-1, y-1).getType();
            if(topLeftCell == NurikabeType.NUMBER){
                whiteCells.add(destBoardState.getCell(x-1, y-1));
            }
        }
        if(y-1 >= 0 && x+1 < width){
            topRightCell = destBoardState.getCell(x+1, y-1).getType();
            if(topRightCell == NurikabeType.NUMBER){
                whiteCells.add(destBoardState.getCell(x+1, y-1));
            }
        }
        if(y+1 < height && x-1 >= 0){
            downLeftCell = destBoardState.getCell(x-1,y+1).getType();
            if(downLeftCell == NurikabeType.NUMBER){
                whiteCells.add(destBoardState.getCell(x-1, y+1));
            }
        }
        if(y+1 < height && x+1 < width){
            downRightCell = destBoardState.getCell(x+1,y+1).getType();

            if(downRightCell == NurikabeType.NUMBER){
                whiteCells.add(destBoardState.getCell(x+1, y+1));
            }
        }

        //Make sure there is only 1 white cell
        if(whiteCells.size() != 1){
            return "Incorrect number of white cells!";
        }else{
            //Make sure it needs more white cells to escape
            NurikabeCell numberedCell = whiteCells.get(0);
            if(numberedCell.getData() != 2){
                return "Incorrect number on white cell!";
            }else{
                return null;
            }
        }
    }

    /**
     * Creates a transition {@link Board} that has this rule applied to it using the {@link TreeNode}.
     *
     * @param node tree node used to create default transition board
     * @return default board or null if this rule cannot be applied to this tree node
     */
    @Override
    public Board getDefaultBoard(TreeNode node) {
        return null;
    }
}
