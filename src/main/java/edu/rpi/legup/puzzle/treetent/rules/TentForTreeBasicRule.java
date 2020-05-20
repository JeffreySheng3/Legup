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

import java.util.List;

import java.awt.*;
import java.util.List;
import javax.swing.*;


public class TentForTreeBasicRule extends BasicRule {

    public TentForTreeBasicRule() {
        super("Tent for Tree",
                "If only one unlinked tent and no blank cells are adjacent to an unlinked tree, the unlinked tree must link to the unlinked tent.",
                "edu/rpi/legup/images/treetent/NewTreeLink.png");
    }

    /**
     * Checks whether the child node logically follows from the parent node
     * at the specific puzzleElement index using this rule
     *
     * @param transition    transition to check
     * @param puzzleElement equivalent puzzleElement
     * @return null if the child node logically follow from the parent node at the specified puzzleElement,
     * otherwise error message
     *
     * TODO: Check at all neighboring adjacent trees, if they have any other trees adjacent to them return false
     */
    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) {
        if (!(puzzleElement instanceof TreeTentLine)) {
            return "Lines must be created for this rule.";
        }
        TreeTentBoard initialBoard = (TreeTentBoard) transition.getParents().get(0).getBoard();
        TreeTentLine initLine = (TreeTentLine) initialBoard.getPuzzleElement(puzzleElement);
        TreeTentBoard finalBoard = (TreeTentBoard) transition.getBoard();
        TreeTentLine finalLine = (TreeTentLine) finalBoard.getPuzzleElement(puzzleElement);
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


    //TODO Still looks to have an issue, check if linked tents are excluded properly    ** check picture for situation
    //TODO -- Also, must check this rule for ALL adjacent trees, return true if even one tree this is valid for
    //          can also try to apply contradiction rule here for each tree (prolly tent for tree)
    private boolean isForced(TreeTentBoard board, TreeTentCell tree_, TreeTentCell tent) {
        //check empty spaces
//        if (board.getAdjacent(tree_, TreeTentType.UNKNOWN).size() != 0) {
//            return false;
//        }
        //Get all trees adjacent to the placed tent, check if any of them are required to have this tent
        List<TreeTentCell> trees = board.getAdjacent(tent, TreeTentType.TREE);
        for (TreeTentCell tree : trees) {
            //skip if this has any unknown spaces
            if(board.getAdjacent(tree, TreeTentType.UNKNOWN).size() > 0) {continue;}

            //check unlinked tents
            List<TreeTentCell> tents = board.getAdjacent(tree, TreeTentType.TENT);
            List<TreeTentCell> un_tents = board.getAdjacent(tree, TreeTentType.TENT);

            //Remove all tents that are part of a link
            for (TreeTentCell adj_tent : tents) {
                for (TreeTentLine line : board.getLines()) {
                    if (line.getC1().getLocation().equals(adj_tent.getLocation()) || line.getC2().getLocation().equals(adj_tent.getLocation())) {
                        un_tents.remove(adj_tent);
                    }
                }
            }
//            String out = "*unlinked tents::"+Integer.toString(un_tents.size());
//            JFrame f;
//            f=new JFrame();
//            JOptionPane.showMessageDialog(f,out);
            if (un_tents.size() == 1) {return true;}
        }
        return false;
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
