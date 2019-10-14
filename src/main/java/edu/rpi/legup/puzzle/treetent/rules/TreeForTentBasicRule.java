package edu.rpi.legup.puzzle.treetent.rules;
import java.io.*;
import java.util.*;
import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.BasicRule;
import edu.rpi.legup.model.tree.TreeNode;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.treetent.TreeTentBoard;
import edu.rpi.legup.puzzle.treetent.TreeTentCell;
import edu.rpi.legup.puzzle.treetent.TreeTentType;
import edu.rpi.legup.puzzle.treetent.TreeTentLine;

public class TreeForTentBasicRule extends BasicRule {
    public TreeForTentBasicRule() {
        super("Tree for Tent",
                "If only one unlinked tree is adjacent to an unlinked tent, the unlinked tent must link to the unlinked tree.",
                "edu/rpi/legup/images/treetent/NewTentLink.png");
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
    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) {
//     return null; }
        if (!(puzzleElement instanceof TreeTentLine)) {
            return "Lines must be created for this rule.";
        }
        TreeTentBoard   initialBoard =  (TreeTentBoard) transition.getParents().get(0).getBoard();
        TreeTentLine    initLine =      (TreeTentLine) initialBoard.getPuzzleElement(puzzleElement);
        TreeTentBoard   finalBoard =    (TreeTentBoard) transition.getBoard();
        TreeTentLine    finalLine =     (TreeTentLine) finalBoard.getPuzzleElement(puzzleElement);
        TreeTentCell tree, tent;
        if (finalLine.getC1().getType() == TreeTentType.TREE || finalLine.getC2().getType() == TreeTentType.TENT) {
            tree = finalLine.getC1();
            tent = finalLine.getC2();
        } else if (finalLine.getC2().getType() == TreeTentType.TREE || finalLine.getC1().getType() == TreeTentType.TENT) {
            tree = finalLine.getC2();
            tent = finalLine.getC1();
        } else {
            return "This line must connect a tree to a tent.";
        }

        if (isForced(initialBoard, tree, tent)) {
            return null;
        } else {
            return "This cell is not forced to be tent.";
        }
    }
    //Rule is forced if only one tree is available for this tent
    private boolean isForced(Board board, PuzzleElement tree_element, PuzzleElement tent_element) {
        TreeTentBoard treeTentBoard = (TreeTentBoard) board;
        TreeTentCell tree = (TreeTentCell) tree_element;
        TreeTentCell tent = (TreeTentCell) tent_element;
        List<TreeTentCell> trees = treeTentBoard.getAdjacent(tent, TreeTentType.TREE);
        return (trees.size() != 1);
//        return !trees.isEmpty();
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
