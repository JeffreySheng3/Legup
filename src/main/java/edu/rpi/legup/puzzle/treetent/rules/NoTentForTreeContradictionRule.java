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

public class NoTentForTreeContradictionRule extends ContradictionRule {

    public NoTentForTreeContradictionRule() {
        super("No Tent For Tree",
                "Each tree must link to a tent.",
                "edu/rpi/legup/images/treetent/contra_NoTentForTree.png");
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
        if (cell.getType() != TreeTentType.TREE) {
            return "This cell does not contain a contradiction at this location.";
        }
        int adjUnknown = treeTentBoard.getAdjacent(cell, TreeTentType.UNKNOWN).size();
        List<TreeTentCell> adjTents = treeTentBoard.getAdjacent(cell, TreeTentType.TENT);
        List<TreeTentCell> un_Tents = treeTentBoard.getAdjacent(cell, TreeTentType.TENT);
        //Remove all tents that are part of a link
        for (TreeTentCell adj_tent : adjTents) {
            for (TreeTentLine line : treeTentBoard.getLines()) {
                if (line.getC1().getLocation().equals(adj_tent.getLocation()) || line.getC2().getLocation().equals(adj_tent.getLocation())) {
                    un_Tents.remove(adj_tent);
                }
            }
        }
        if (un_Tents.size() == 0 && adjUnknown == 0) {
            return null;
        } else {
            return "This cell does not contain a contradiction at this location.";
        }
    }
}
