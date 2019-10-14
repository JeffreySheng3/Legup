package edu.rpi.legup.puzzle.treetent.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.BasicRule;
import edu.rpi.legup.model.tree.TreeNode;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.treetent.TreeTentBoard;
import edu.rpi.legup.puzzle.treetent.TreeTentCell;
import edu.rpi.legup.puzzle.treetent.TreeTentLine;
import edu.rpi.legup.puzzle.treetent.TreeTentType;

import java.awt.*;
import java.util.List;

public class LastCampingSpotBasicRule extends BasicRule {

    public LastCampingSpotBasicRule() {
        super("Last Camping Spot",
                "If an unlinked tree is adjacent to only one blank cell and not adjacent to any unlinked tents, the blank cell must be a tent.",
                "edu/rpi/legup/images/treetent/oneTentPosition.png");
    }

    /**
     * Checks whether the child node logically follows from the parent node
     * at the specific puzzleElement index using this rule
     *
     * Behavior:
     *    If the rule is valid for this transition, the tent placement is validated and a link is automatically generated
     *    to the unlinked neighboring tree (valid since there is no other possible tree to link to in this case)
     * @param transition    transition to check
     * @param puzzleElement equivalent puzzleElement
     * @return null if the child node logically follow from the parent node at the specified puzzleElement,
     * otherwise error message
     */
    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) {
        if (puzzleElement instanceof TreeTentLine) {
            return "Line is not valid for this rule.";
        }
        TreeTentBoard initialBoard =    (TreeTentBoard) transition.getParents().get(0).getBoard();
        TreeTentCell initCell =         (TreeTentCell) initialBoard.getPuzzleElement(puzzleElement);
        TreeTentBoard finalBoard =      (TreeTentBoard) transition.getBoard();
        TreeTentCell finalCell =        (TreeTentCell) finalBoard.getPuzzleElement(puzzleElement);
        if (!(initCell.getType() == TreeTentType.UNKNOWN && finalCell.getType() == TreeTentType.TENT)) { //must be placing a tent at an unknown cell
            return "This cell must be a tent.";
        }
//        return null;
        if (isForced(initialBoard, initCell)) {
            return null;
        } else {
            return "This cell is not forced to be tent.";
        }
    }
//**** Change image to only have tent placement (shouldn't give the idea that a link should be drawn)
    private boolean isForced(TreeTentBoard board, TreeTentCell cell) {
        /* Check only one empty space is adjacent to this tree, and no unlinked tents
         * "unlinked tree adjacent to only one blank cell, and not adjacent to any unlinked tents, blank cell must be a tent."*/

        /* Get list of adjacent trees to proposed tent */
        List<TreeTentCell> adjTrees = board.getAdjacent(cell, TreeTentType.TREE); //get adjacent trees to the placed tent
        /* isolate the tree in this list with only one empty adjacent space: */
        TreeTentCell tree = null;
        for (TreeTentCell c : adjTrees){
            if(board.getAdjacent(c, TreeTentType.UNKNOWN).size() == 1){
                tree = c;   //Found the valid tree
                break;
            }
        }
        if (tree == null) { return false; } //no adjacent tree with only one empty space
        /* Verify this tree is unlinked */
        for (TreeTentLine line : board.getLines()) {
            if (line.getC1().getLocation().equals(tree.getLocation()) || line.getC2().getLocation().equals(tree.getLocation())) {
                return false;
            }
        }
        /* Validate that this tree has no other unlinked tents*/
        List<TreeTentCell> adjTents = board.getAdjacent(tree, TreeTentType.TENT); //get adjacent tents to the placed tent
//        for (TreeTentCell c : adjTents) {
//            /* Verify this cell is linked */
//            Point loc = tree.getLocation();
//            boolean linked = false;
//            for (TreeTentLine line : board.getLines()) {
//                if (line.getC1().getLocation().equals(loc) || line.getC2().getLocation().equals(loc)) {
//                    linked = true;
//                    break;
//                }
//            }
//            if(!linked) {return false;}
//        }

        return true; //validated that this tree has only one empty adjacent space, and no unlinked tents

        //*******************************************************************
//        List<TreeTentCell> adjTents = board.getAdjacent(cell, TreeTentType.Tent); //get adjacent tents to the placed tent
//        for (TreeTentCell c : adjTents) {
//            Point loc = c.getLocation();
//            for (TreeTentLine line : board.getLines()) {
//                if (line.getC1().getLocation().equals(loc) || line.getC2().getLocation().equals(loc)) {
//                    return false;
//                }
//            }
//        }
    }

    /**
     * Creates a transition {@link Board} that has this rule applied to it using the {@link TreeNode}.
     *
     * @param node tree node used to create default transition board
     * @return default board or null if this rule cannot be applied to this tree node
     */
    @Override
    public Board getDefaultBoard(TreeNode node) {
        TreeTentBoard treeTentBoard = (TreeTentBoard) node.getBoard().copy();
        for (PuzzleElement element : treeTentBoard.getPuzzleElements()) {
            TreeTentCell cell = (TreeTentCell) element;
            if (cell.getType() == TreeTentType.UNKNOWN && isForced(treeTentBoard, cell)) {
                cell.setData(TreeTentType.TENT.value);
                treeTentBoard.addModifiedData(cell);
            }
        }
        if (treeTentBoard.getModifiedData().isEmpty()) {
            return null;
        } else {
            return treeTentBoard;
        }
    }
}
