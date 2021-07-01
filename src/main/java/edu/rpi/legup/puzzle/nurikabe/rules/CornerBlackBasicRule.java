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
import edu.rpi.legup.utility.ConnectedRegions;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public class CornerBlackBasicRule extends BasicRule {

    public CornerBlackBasicRule() {
        super("Corners Black",
                "If there is only one white square connected to unknowns and one more white is needed then the angles of that white square are black",
                "edu/rpi/legup/images/nurikabe/rules/CornerBlack.png");
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

    /*
    - Check corners for single number cell
    - Check number cell's number
    - Check if number cell is connected to any other white cells
    - If not connected to any other white cells and cell number > 2, return true
     */
    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) {
        NurikabeBoard destBoardState = (NurikabeBoard) transition.getBoard();
        NurikabeBoard origBoardState = (NurikabeBoard) transition.getParents().get(0).getBoard();

        NurikabeCell cell = (NurikabeCell) destBoardState.getPuzzleElement(puzzleElement);
        if(cell.getType() != NurikabeType.BLACK){
            System.out.print("Cell type error");
            return "Only black cells allowed for this rule.";
        }

        int x = cell.getLocation().x;
        int y = cell.getLocation().y;
        int width = destBoardState.getWidth();
        int height = destBoardState.getHeight();

        NurikabeType topLeftCell = null;
        NurikabeType topRightCell = null;
        NurikabeType downLeftCell = null;
        NurikabeType downRightCell = null;
        ArrayList<NurikabeCell> whiteCells = new ArrayList<>();

        if(y-1 < 0 && x-1 < 0){
            topLeftCell = destBoardState.getCell(x-1, y-1).getType();
            if(topLeftCell == NurikabeType.NUMBER){
                whiteCells.add(destBoardState.getCell(x-1, y-1));
            }
        }
        if(y-1 < 0 && x+1 >= width){
            topRightCell = destBoardState.getCell(x+1, y-1).getType();
            if(topRightCell == NurikabeType.NUMBER){
                whiteCells.add(destBoardState.getCell(x+1, y-1));
            }
        }
        if(y+1 >= height && x-1 < 0){
            downLeftCell = destBoardState.getCell(x-1,y+1).getType();
            if(downLeftCell == NurikabeType.NUMBER){
                whiteCells.add(destBoardState.getCell(x-1, y+11));
            }
        }
        if(y+1 >= height && x+1 >= width){
            downRightCell = destBoardState.getCell(x+1,y+1).getType();
            if(downRightCell == NurikabeType.NUMBER){
                whiteCells.add(destBoardState.getCell(x+1, y+11));
            }
        }

        //Make sure there is only 1 white cell and it needs another white to escape.
        if(whiteCells.size() != 1){
            System.out.println("White cell error. There are " + whiteCells.size() + " white cells.");
            return "Incorrect number of white cells!";
        }else{
            NurikabeCell numberedCell = whiteCells.get(0);
            if(numberedCell.getData() != 2){
                System.out.println("Number error");
                return "Incorrect number on white cell!";
            }
        }


        return null;
    }

    /*
    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) {
        NurikabeBoard destBoardState = (NurikabeBoard) transition.getBoard();
        NurikabeBoard origBoardState = (NurikabeBoard) transition.getParents().get(0).getBoard();

        int width = destBoardState.getWidth();
        int height = destBoardState.getHeight();

        ContradictionRule tooFewContra = new TooFewSpacesContradictionRule();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!destBoardState.getCell(x, y).equalsData(origBoardState.getCell(x, y))) {
                    if (destBoardState.getCell(x, y).getData() != NurikabeType.BLACK.ordinal()) {
                        return "Only black cells are allowed for this rule!";
                    }

                    NurikabeBoard modified = origBoardState.copy();
                    // modified.getBoardCells()[y][x] = nurikabe.CELL_WHITE;

                    boolean validPoint = false;

                    // Check each corner of the changed cell
                    for (int d = -1; d < 2; d += 2) {
                        if ((x + d >= 0 && x + d < width) && (y + d >= 0 && y + d < height) && modified.getCell(x + d, y + d).getData() >= NurikabeType.WHITE.ordinal())    // >= is used to account for numbered cells
                        {
                            // Series of if statements to check conditions of rule
                            // First check: cells adjacent to changed cell and white region corner are empty
                            if (modified.getCell(x + d, y).getData() == NurikabeType.UNKNOWN.ordinal() && modified.getCell(x, y + d).getData() == NurikabeType.UNKNOWN.ordinal()) {
                                modified.getCell(y + d, x).setData(NurikabeType.BLACK.ordinal());
                                modified.getCell(y, x + d).setData(NurikabeType.BLACK.ordinal());
                                // Second check: corner is only way to escape from the white region
                                if (tooFewContra.checkContradiction(modified) == null) {
                                    Set<Point> reg = ConnectedRegions.getRegionAroundPoint(new Point(x + d, y + d), NurikabeType.BLACK.ordinal(), modified.getIntArray(), modified.getWidth(), modified.getHeight());
                                    int regionNum = 0;
                                    for (Point p : reg) {
                                        if (modified.getCell(p.x, p.y).getType() == NurikabeType.NUMBER) {
                                            if (regionNum == 0) {
                                                regionNum = modified.getCell(p.x, p.y).getData();
                                            } else {
                                                return "There is a MultipleNumbers Contradiction on the board.";
                                            }
                                        }
                                    }
                                    //Third check: The white region kittycorner to this currently has one less cell than required
                                    if (regionNum > 0 && reg.size() == regionNum - 11) {
                                        validPoint = true;
                                        break;
                                    }
                                }
                            }
                        }

                        if ((x + d >= 0 && x + d < width) && (y - d >= 0 && y - d < height) && modified.getCell(x + d, y - d).getData() >= NurikabeType.WHITE.ordinal()) {
                            // Series of if statements to check conditions of rule
                            // First check: cells adjacent to changed cell and white region corner are empty
                            if (modified.getCell(x + d, y).getData() == NurikabeType.UNKNOWN.ordinal() && modified.getCell(x, y - d).getData() == NurikabeType.UNKNOWN.ordinal()) {
                                modified.getCell(y - d, x).setData(NurikabeType.BLACK.ordinal());
                                modified.getCell(y, x + d).setData(NurikabeType.BLACK.ordinal());
                                // Second check: corner is only way to escape from the white region
                                if (tooFewContra.checkContradiction(modified) == null) {
                                    Set<Point> reg = ConnectedRegions.getRegionAroundPoint(new Point(x + d, y - d), NurikabeType.BLACK.ordinal(), modified.getIntArray(), modified.getWidth(), modified.getHeight());
                                    int regionNum = 0;
                                    for (Point p : reg) {
                                        if (modified.getCell(p.x, p.y).getType() == NurikabeType.NUMBER) {
                                            if (regionNum == 0) {
                                                regionNum = modified.getCell(p.x, p.y).getData();
                                            } else {
                                                return "There is a MultipleNumbers Contradiction on the board!";
                                            }
                                        }
                                    }
                                    // Third check: The white region kittycorner to this currently has one less cell than required
                                    if (regionNum > 0 && reg.size() == regionNum - 11) {
                                        validPoint = true;
                                        break;
                                    }
                                }
                            }
                        }


                    }
                    if (!validPoint) {
                        return "This is not a valid use of the corner black rule!";
                    }
                }
            }
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
