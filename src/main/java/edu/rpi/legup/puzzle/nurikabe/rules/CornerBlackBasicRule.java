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
    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) {
        NurikabeBoard destBoardState = (NurikabeBoard) transition.getBoard();
        NurikabeBoard origBoardState = (NurikabeBoard) transition.getParents().get(0).getBoard();

        int width = destBoardState.getWidth();
        int height = destBoardState.getHeight();

        NurikabeCell cell = (NurikabeCell) destBoardState.getPuzzleElement(puzzleElement);
//        System.out.println(cell.getLocation().x + ", " + cell.getLocation().y);


        ContradictionRule tooFewContra = new TooFewSpacesContradictionRule();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!destBoardState.getCell(x, y).equalsData(origBoardState.getCell(x, y))) {
                    if (destBoardState.getCell(x, y).getType() != NurikabeType.BLACK) {
                        System.out.println("Only black cells are allowed for this rule! The cell is " + x + " and " + y);
                        return "Only black cells are allowed for this rule!";
                    }

                    NurikabeBoard modified = origBoardState.copy();

                    boolean validPoint = false;

                    // Check each corner of the changed cell
                    //d= -1, 1      (x,y) = (0,0)  -> (-1,-1) (1,1)
                    for (int d = -1; d < 2; d += 2) {
                        System.out.println("Marker 1");
                        if ((x + d >= 0 && x + d < width) && (y + d >= 0 && y + d < height) && modified.getCell(x + d, y + d).getData() >= NurikabeType.WHITE.ordinal())    // >= is used to account for numbered cells
                        {
                            System.out.println("Marker 2");
                            // Series of if statements to check conditions of rule
                            // First check: cells adjacent to changed cell and white region corner are empty
//                            if (modified.getCell(x + d, y).getData() == NurikabeType.UNKNOWN.ordinal() && modified.getCell(x, y + d).getData() == NurikabeType.UNKNOWN.ordinal()) {
                           if (modified.getCell(x + d, y).getType() == NurikabeType.UNKNOWN && modified.getCell(x, y + d).getType() == NurikabeType.UNKNOWN) {
                                modified.getCell(y + d, x).setData(NurikabeType.BLACK.ordinal());
                                modified.getCell(y, x + d).setData(NurikabeType.BLACK.ordinal());
                                System.out.println("Marker 4");
//                                 Second check: corner is only way to escape from the white region
                               System.out.println("Contra check " + tooFewContra.checkContradiction(modified));
                                if (tooFewContra.checkContradiction(modified) != null) {
                                    System.out.println("Marker 5");
                                    Set<Point> reg = ConnectedRegions.getRegionAroundPoint(new Point(x + d, y + d), NurikabeType.BLACK.ordinal(), modified.getIntArray(), modified.getWidth(), modified.getHeight());
                                    int regionNum = 0;
                                    for (Point p : reg) {
                                        if (modified.getCell(p.x, p.y).getType() == NurikabeType.NUMBER) {
                                            System.out.println("Marker 6");

                                            if (regionNum == 0) {
                                                regionNum = modified.getCell(p.x, p.y).getData();
                                            } else {
                                                System.out.println("There is a MultipleNumbers Contradiction on the board.");
                                                return "There is a MultipleNumbers Contradiction on the board.";
                                            }


                                        }
                                    }
                                    System.out.println("1Region number is " + regionNum + " reg size is " + reg.size());

                                    //Third check: The white region kittycorner to this currently has one less cell than required
                                    if (regionNum > 0 && reg.size() == regionNum - 11) {
                                        validPoint = true;
                                        break;
                                    }
                                }
                            }
                        }
                        //d = -1, 1 (x,y) = (0,0) ->
                        if ((x + d >= 0 && x + d < width) && (y - d >= 0 && y - d < height) && modified.getCell(x + d, y - d).getData() >= NurikabeType.WHITE.ordinal()) {
                            System.out.println("Marker 3");

                            // Series of if statements to check conditions of rule
                            // First check: cells adjacent to changed cell and white region corner are empty
                            if (modified.getCell(x + d, y).getData() == NurikabeType.UNKNOWN.ordinal() && modified.getCell(x, y - d).getData() == NurikabeType.UNKNOWN.ordinal()) {
                                modified.getCell(y - d, x).setData(NurikabeType.BLACK.ordinal());
                                modified.getCell(y, x + d).setData(NurikabeType.BLACK.ordinal());
                                // Second check: corner is only way to escape from the white region
                                if (tooFewContra.checkContradiction(modified) != null) {
                                    System.out.println("Marker 7");
                                    Set<Point> reg = ConnectedRegions.getRegionAroundPoint(new Point(x + d, y - d), NurikabeType.BLACK.ordinal(), modified.getIntArray(), modified.getWidth(), modified.getHeight());
                                    int regionNum = 0;
                                    for (Point p : reg) {
                                        System.out.println("Marker 8");
                                        if (modified.getCell(p.x, p.y).getType() == NurikabeType.NUMBER) {

                                            if (regionNum == 0) {
                                                regionNum = modified.getCell(p.x, p.y).getData();
                                            } else {
                                                System.out.println("There is a MultipleNumbers Contradiction on the board.");
                                                return "There is a MultipleNumbers Contradiction on the board!";
                                            }


                                        }
                                    }
                                    System.out.println("2Region number is " + regionNum + " reg size is " + reg.size());

                                    // Third check: The white region kittycorner to this currently has one less cell than required
                                    if (regionNum > 0 && reg.size() == regionNum - 11) {
                                        System.out.println("Marker 9");
                                        validPoint = true;
                                        System.out.println("2Region number is " + regionNum + " reg size is " + reg.size());

                                        break;
                                    }
                                }
                            }
                        }


                    }

                    if (!validPoint) {
                        System.out.println("This is not a valid use of the corner black rule!");
                        return "This is not a valid use of the corner black rule!";
                    }


                }
            }
        }
        return null;
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
