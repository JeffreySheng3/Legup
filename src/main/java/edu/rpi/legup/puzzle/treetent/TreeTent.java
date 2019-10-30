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

        for (ContradictionRule rule : contradictionRules) {
            if (rule.checkContradiction(treeTentBoard) == null) {
                return false;
            }
        }

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

//        String out = "trees:"+Integer.toString(trees)+ "-- tents:"+Integer.toString(tents)+"-- lines:"+Integer.toString(lines);
//        JFrame f;
//        f=new JFrame();
//        JOptionPane.showMessageDialog(f,out);
        if(trees != lines || trees != tents || tents != lines) { return false; } //indicates an unlinked tree

        return true;
    }
    /**TODO:  Implement checker, fix tent for tree, isnt checking if there are empty spaces adjacent(should return false if there is)*
     *          -Also look into case rules
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
