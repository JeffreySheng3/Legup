package edu.rpi.legup.puzzle.shorttruthtable;

import edu.rpi.legup.model.PuzzleExporter;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import org.w3c.dom.Document;

public class ShortTruthTableExporter extends PuzzleExporter
{

    public ShortTruthTableExporter(ShortTruthTable shortTruthTable)
    {
        super(shortTruthTable);
    }

    @Override
    protected org.w3c.dom.Element createBoardElement(Document newDocument)
    {
        ShortTruthTableBoard board = (ShortTruthTableBoard) puzzle.getTree().getRootNode().getBoard();

        org.w3c.dom.Element boardElement = newDocument.createElement("board");
        boardElement.setAttribute("width", String.valueOf(board.getWidth()));
        boardElement.setAttribute("height", String.valueOf(board.getHeight()));

        org.w3c.dom.Element cellsElement = newDocument.createElement("cells");
        for(PuzzleElement puzzleElement : board.getPuzzleElements())
        {
            ShortTruthTableCell cell = (ShortTruthTableCell) puzzleElement;
            if(cell.getData() != -2)
            {
                org.w3c.dom.Element cellElement = puzzle.getFactory().exportCell(newDocument, puzzleElement);
                cellsElement.appendChild(cellElement);
            }
        }

        boardElement.appendChild(cellsElement);
        return boardElement;
    }
}
