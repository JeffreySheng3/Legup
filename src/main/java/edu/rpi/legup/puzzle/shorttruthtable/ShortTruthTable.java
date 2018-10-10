package edu.rpi.legup.puzzle.shorttruthtable;

import edu.rpi.legup.model.Puzzle;
import edu.rpi.legup.model.RegisterPuzzle;
import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.ContradictionRule;

@RegisterPuzzle
public class ShortTruthTable extends Puzzle {

    public ShortTruthTable() {
        super();
        name = "ShortTruthTable";

        importer = new ShortTruthTableImporter(this);
        exporter = new ShortTruthTableExporter(this);

        factory = new ShortTruthTableCellFactory();
    }

    /**
     * Initializes the game board. Called by the invoker of the class
     */
    @Override
    public void initializeView() {
        boardView = new ShortTruthTableView((ShortTruthTableBoard) currentBoard);
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
        ShortTruthTableBoard shortTruthTableBoard = (ShortTruthTableBoard) board;
        shortTruthTableBoard.fillWithLight();

        for (ContradictionRule rule : contradictionRules) {
            if (rule.checkContradiction(shortTruthTableBoard) == null) {
                return false;
            }
        }
        for (PuzzleElement data : shortTruthTableBoard.getPuzzleElements()) {
            ShortTruthTableCell cell = (ShortTruthTableCell) data;
            if ((cell.getType() == ShortTruthTableCellType.UNKNOWN || cell.getType() == ShortTruthTableCellType.EMPTY) && !cell.isLite()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Callback for when the board puzzleElement changes
     *
     * @param board the board that has changed
     */
    @Override
    public void onBoardChange(Board board) {

    }
}
