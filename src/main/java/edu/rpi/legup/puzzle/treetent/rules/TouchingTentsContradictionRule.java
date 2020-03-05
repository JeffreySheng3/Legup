package edu.rpi.legup.puzzle.treetent.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.ContradictionRule;
import edu.rpi.legup.puzzle.treetent.TreeTentBoard;
import edu.rpi.legup.puzzle.treetent.TreeTentCell;
import edu.rpi.legup.puzzle.treetent.TreeTentType;

public class TouchingTentsContradictionRule extends ContradictionRule {

    public TouchingTentsContradictionRule() {
        super("Touching Tents",
                "Tents cannot touch other tents.",
                "edu/rpi/legup/images/treetent/contra_adjacentTents.png");
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
        int adjTent = treeTentBoard.getAdjacent(cell, TreeTentType.TENT).size(); //Get adjacent tents
        int diagTent = treeTentBoard.getDiagonals(cell, TreeTentType.TENT).size(); //Get adjacent tents
        if (adjTent > 0 || diagTent > 0) {
            return null;
        } else {
            return "This cell does not contain a contradiction at this location.";
        }
    }
}
