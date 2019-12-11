package edu.rpi.legup.puzzle.treetent.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.ContradictionRule;
import edu.rpi.legup.puzzle.treetent.TreeTentBoard;
import edu.rpi.legup.puzzle.treetent.TreeTentCell;
import edu.rpi.legup.puzzle.treetent.TreeTentType;
import edu.rpi.legup.puzzle.treetent.TreeTentLine;
import java.awt.*;
import java.util.List;

public class NoTreeForTentContradictionRule extends ContradictionRule {

    public NoTreeForTentContradictionRule() {
        super("No Tree For Tent",
                "Each tent must link to a tree.",
                "edu/rpi/legup/images/treetent/contra_NoTreeForTent.png");
    }

    /**
     * Checks whether the transition has a contradiction at the specific puzzleElement index using this rule
     *
     * @param board         board to check contradiction
     * @param puzzleElement equivalent puzzleElement
     * @return null if the transition contains a contradiction at the specified puzzleElement,
     * otherwise error message
     */
    @Override
    public String checkContradictionAt(Board board, PuzzleElement puzzleElement) {
        TreeTentBoard treeTentBoard = (TreeTentBoard) board;
        TreeTentCell cell = (TreeTentCell) puzzleElement;
        if (cell.getType() != TreeTentType.TENT) {
            return "This cell does not contain a contradiction at this location.";
        }

        //check unlinked trees
        List<TreeTentCell> adjTree = treeTentBoard.getAdjacent(cell, TreeTentType.TREE);
        List<TreeTentCell> un_Tree = treeTentBoard.getAdjacent(cell, TreeTentType.TREE);
        //Remove all trees that are part of a link
        for (TreeTentCell adj_tree : adjTree) {
            for (TreeTentLine line : treeTentBoard.getLines()) {
                if (line.getC1().getLocation().equals(adj_tree.getLocation()) || line.getC2().getLocation().equals(adj_tree.getLocation())) {
                    un_Tree.remove(adj_tree);
                }
            }
        }
        if (un_Tree.size() == 0) {
            return null;
        } else {
            return "This cell does not contain a contradiction at this location.";
        }
    }
}
