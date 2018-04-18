package puzzle.fillapix.rules;

import model.gameboard.Board;
import model.rules.BasicRule;
import model.tree.TreeTransition;
import puzzle.fillapix.FillapixBoard;
import puzzle.fillapix.FillapixCell;

import java.util.ArrayList;

public class FinishWithWhiteBasicRule extends BasicRule {
    public FinishWithWhiteBasicRule() {
        super("Finish with White",
                "The remaining unknowns around and on a cell must be white to satisfy the number",
                "images/fillapix/rules/FinishWithWhite.png");
    }

    @Override
    public String checkRuleAt(TreeTransition transition, int elementIndex) {
        FillapixBoard fillapixBoard = (FillapixBoard) transition.getBoard();
        int width = fillapixBoard.getWidth();
        int height = fillapixBoard.getHeight();
        FillapixCell cell = fillapixBoard.getCell(elementIndex%width,elementIndex/width);

        FillapixBoard currentBoard = (FillapixBoard) transition.getParentNode().getBoard();
        // given the modified cell, cell
        // look at each cell in the applicable region
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int x = cell.getLocation().x + i;
                int y = cell.getLocation().y + j;
                // making sure it's not out of bounds
                if (x > -1 && x < width && y > -1 && y < height) {
                    FillapixCell cellInRegion = fillapixBoard.getCell(x,y);
                    // and if that cell is a clue
                    if (cellInRegion.isGiven()) {
                        int clue = cellInRegion.getClue();
                        // then check around that clue
                        int numBlackCells = 0;
                        for (int k = -1; k < 2; k++) {
                            for (int l = -1; l < 2; l++) {
                                int z = x+k;
                                int w = y+l;
                                if (z > -1 && z < width && w > -1 && w < height) {
                                    if (currentBoard.getCell(z,w).isBlack()) {
                                        numBlackCells++;
                                    }
                                }
                            }
                        }
                        // if the number of black cells
                        // equal the clue, then this is a valid application
                        if (numBlackCells<=clue) {
                            return null;
                        } else {
                            System.out.println("WHAT WENT WRONG??? "+clue+"   "+numBlackCells);
                        }
                    }
                }
            }
        }
        return "Incorrect use of Finish with White";
    }

    @Override
    public boolean doDefaultApplication(TreeTransition transition) {return false; }

    @Override
    public boolean doDefaultApplicationAt(TreeTransition transition, int elementIndex) {return false; }
}