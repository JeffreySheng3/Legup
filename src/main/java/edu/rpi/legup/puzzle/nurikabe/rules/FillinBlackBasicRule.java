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

import java.util.LinkedHashSet;
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
        Set<ContradictionRule> contras = new LinkedHashSet<>();
        contras.add(new BlackSquareContradictionRule());

        NurikabeBoard destBoardState = (NurikabeBoard) transition.getBoard();
        NurikabeBoard origBoardState = (NurikabeBoard) transition.getParents().get(0).getBoard();

        NurikabeCell cell = (NurikabeCell) destBoardState.getPuzzleElement(puzzleElement);

        if(cell.getType() != NurikabeType.BLACK){
            return "Only black cells are allowed for this rule!";
        }

        int x = cell.getLocation().x;
        int y = cell.getLocation().y;
        int width = destBoardState.getWidth();
        int height = destBoardState.getHeight();

        NurikabeType upCell;
        NurikabeType rightCell;
        NurikabeType downCell;
        NurikabeType leftCell;

        if(y-1 < 0){
            upCell = null;
        }else{
            upCell = destBoardState.getCell(x, y - 1).getType();
        }

        if(x-1 < 0){
            leftCell = null;
        }else{
            leftCell = destBoardState.getCell(x - 1, y).getType();
        }

        if(x+1 >= width){
            rightCell = null;
        }else{
            rightCell = destBoardState.getCell(x + 1, y).getType();
        }

        if(y+1 >= height){
            downCell = null;
        }else{
            downCell = destBoardState.getCell(x, y + 1).getType();
        }


        NurikabeBoard modified = origBoardState.copy();
        modified.getCell(x, y).setData(NurikabeType.BLACK.toValue());

        if( (upCell == NurikabeType.BLACK || upCell == null)  &&
                (rightCell == NurikabeType.BLACK || rightCell == null) &&
                (downCell == NurikabeType.BLACK || downCell == null) &&
                (leftCell == NurikabeType.BLACK || leftCell == null) ){
            //Check for contradiction here?
            return null;
        }
        System.out.println("ERROR");
        return "Does not follow the rule.";

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
