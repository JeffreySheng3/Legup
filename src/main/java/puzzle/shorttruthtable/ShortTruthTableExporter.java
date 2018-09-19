package puzzle.shorttruthtable;

import model.PuzzleExporter;
import model.gameboard.ElementData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import puzzle.lightup.LightUp;
import puzzle.lightup.LightUpBoard;

public class ShortTruthTableExporter extends PuzzleExporter
{

    public ShortTruthTableExporter(ShortTruthTable shorttruthtable)
    {
        super(shorttruthtable);
    }

    @Override
    protected Element createBoardElement(Document newDocument)
    {
        ShortTruthTableBoard board = (ShortTruthTableBoard) puzzle.getTree().getRootNode().getBoard();

        Element boardElement = newDocument.createElement("board");
        boardElement.setAttribute("width", String.valueOf(board.getWidth()));
        boardElement.setAttribute("height", String.valueOf(board.getHeight()));

        Element cellsElement = newDocument.createElement("cells");
        for(ElementData data : board.getElementData())
        {
            if(data.getValueInt() != 0)
            {
                Element cellElement = puzzle.getFactory().exportCell(newDocument, data);
                cellsElement.appendChild(cellElement);
            }
        }

        boardElement.appendChild(cellsElement);
        return boardElement;
    }
}
