package edu.rpi.legup.puzzle.treetent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import edu.rpi.legup.model.Puzzle;
import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.ContradictionRule;
import edu.rpi.legup.ui.boardview.BoardView;

public class TreeTent extends Puzzle {

    public TreeTent() {
        super();

        this.name = "TreeTent";

        this.importer = new TreeTentImporter(this);
        this.exporter = new TreeTentExporter(this);

        this.factory = new TreeTentCellFactory();
    }

    /**
     * Initializes the game board. Called by the invoker of the class
     */
    @Override
    public void initializeView() {
        TreeTentBoard board = (TreeTentBoard) currentBoard;
        boardView = new TreeTentView((TreeTentBoard) currentBoard);
    }

    /**
     * Generates a random edu.rpi.legup.puzzle based on the difficulty
     *
     * @param difficulty level of difficulty (1-10)
     * @return board of the random edu.rpi.legup.puzzle
     */
    @Override
    public Board generatePuzzle(int difficulty) {
        return null;
    }

    /**
     * Determines if the current board is a valid state
     *
     * @param board board to check for validity
     * @return true if board is valid, false otherwise
     */
    @Override
    public boolean isBoardComplete(Board board) {
        TreeTentBoard treeTentBoard = (TreeTentBoard) board;

//        String out = "Calling checker, contRules = " + Integer.toString(contradictionRules.size());
//        JFrame f;
//        f=new JFrame();
//        JOptionPane.showMessageDialog(f,out); // Maybe don't do this check at all?
//        for (ContradictionRule rule : contradictionRules) {
//            out = "Checking rule: " + rule.getRuleName();
//            f=new JFrame();
//            JOptionPane.showMessageDialog(f,out);
//            if (rule.checkContradiction(treeTentBoard) == null) {
//                out = "returning false for rule: " + rule.getRuleName();
//                f=new JFrame();
//                JOptionPane.showMessageDialog(f,out);
//                return false;
//            }
//        }
//        out = "Checked all cont rules";
//        f=new JFrame();
//        JOptionPane.showMessageDialog(f,out);

        for (PuzzleElement puzzleElement : treeTentBoard.getPuzzleElements()) {
            TreeTentCell cell = (TreeTentCell) puzzleElement;
            if (cell.getType() == TreeTentType.UNKNOWN ) {
                return false;
            }
        }

        /* Make sure every tree is linked */
        int lines = treeTentBoard.getLines().size();
        int trees = 0;
        int tents = 0;
        for (PuzzleElement puzzleElement : treeTentBoard.getPuzzleElements()) {
            TreeTentCell cell = (TreeTentCell) puzzleElement;
            if (cell.getType() == TreeTentType.TREE ) { trees += 1; }
            if (cell.getType() == TreeTentType.TENT ) { tents += 1; }

        }

        if(trees != lines || trees != tents || tents != lines) { return false; } //indicates an unlinked tree

        return true;
    }
    /**TODO:
     *      Check the checker code when using case rule branching, should ret valid for a single valid branch
     */

    /**
     * Callback for when the board puzzleElement changes
     *
     * @param board the board that has changed
     */
    @Override
    public void onBoardChange(Board board) {

    }
}
