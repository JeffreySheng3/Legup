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
import edu.rpi.legup.puzzle.nurikabe.NurikabeUtilities;
import edu.rpi.legup.utility.DisjointSets;

import java.util.HashSet;
import java.util.Set;

public class FillinBlackBasicRule extends BasicRule {

    public FillinBlackBasicRule() {
        super("Fill In Black",
                "If there an unknown region surrounded by black, it must be black.",
                "edu/rpi/legup/images/nurikabe/rules/FillInBlack.png");
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

    //Rule checks out when black is placed on any location besides adjacent to a number tile
    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement){
        NurikabeBoard board = (NurikabeBoard) transition.getBoard();
        NurikabeBoard origBoard = (NurikabeBoard) transition.getParents().get(0).getBoard();

        NurikabeCell cell = (NurikabeCell) board.getPuzzleElement(puzzleElement);

        if(cell.getType() != NurikabeType.BLACK){
            return "Only black cells are allowed for this rule!";
        }

        int x = cell.getLocation().x;
        int y = cell.getLocation().y;

        NurikabeType upCellType = board.getCell(x, y - 1).getType();
        NurikabeType rightCell = board.getCell(x + 1, y).getType();
        NurikabeType downCell = board.getCell(x, y + 1).getType();
        NurikabeType leftCell = board.getCell(x - 1, y).getType();

        if( (upCellType == NurikabeType.WHITE || upCellType == NurikabeType.UNKNOWN)  ||
                (rightCell == NurikabeType.WHITE || rightCell == NurikabeType.UNKNOWN) ||
                (downCell == NurikabeType.WHITE || downCell == NurikabeType.UNKNOWN) ||
                (leftCell == NurikabeType.WHITE || leftCell == NurikabeType.UNKNOWN) ){
            return "Does not follow the rule.";
        }

        return null;
    }
    /*
    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) {
        NurikabeBoard board = (NurikabeBoard) transition.getBoard();
        NurikabeBoard origBoard = (NurikabeBoard) transition.getParents().get(0).getBoard();
        ContradictionRule contraRule = new NoNumberContradictionRule();

        NurikabeCell cell = (NurikabeCell) board.getPuzzleElement(puzzleElement);

        if (cell.getType() != NurikabeType.BLACK) {
            return "Only black cells are allowed for this rule!";
        }
        NurikabeBoard modified = origBoard.copy();
        modified.getPuzzleElement(puzzleElement).setData(NurikabeType.WHITE.toValue());
        if (contraRule.checkContradictionAt(modified, puzzleElement) != null) {
            return "Black cells must be placed in a region of black cells!";
        }
        return null;
    }
     */

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
