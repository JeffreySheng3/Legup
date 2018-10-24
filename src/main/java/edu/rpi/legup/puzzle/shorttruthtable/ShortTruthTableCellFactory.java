package edu.rpi.legup.puzzle.shorttruthtable;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.ElementFactory;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.save.InvalidFileFormatException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.awt.*;

public class ShortTruthTableCellFactory extends ElementFactory
{
    /**
     * Creates a puzzleElement based on the xml document Node and adds it to the board
     *
     * @param node node that represents the puzzleElement
     * @param board board to add the newly created cell
     * @return newly created cell from the xml document Node
     * @throws InvalidFileFormatException
     */
    @Override
    public ShortTruthTableCell importCell(Node node, Board board) throws InvalidFileFormatException
    {
        try
        {
            if(!node.getNodeName().equalsIgnoreCase("premise") && !node.getNodeName().equalsIgnoreCase("conclusion"))
            {
                throw new InvalidFileFormatException("shorttruthtable Factory: unknown puzzleElement puzzleElement");
            }

            ShortTruthTableBoard shortTruthTableBoard = (ShortTruthTableBoard)board;
            int width = shortTruthTableBoard.getWidth();
            int height = shortTruthTableBoard.getHeight();
            
            int y = Integer.valueOf(node.getAttributes().getNamedItem("index").getNodeValue());
            if(y >= height)
            {
                throw new InvalidFileFormatException("shorttruthtable Factory: cell location out of bounds");
            }

            ShortTruthTableCell cell = new ShortTruthTableCell(0);
            return cell;
        }
        catch(NumberFormatException e)
        {
            throw new InvalidFileFormatException("shorttruthtable Factory: unknown value where integer expected");
        }
        catch(NullPointerException e)
        {
            throw new InvalidFileFormatException("shorttruthtable Factory: could not find attribute(s)");
        }
    }

    /**
     * Creates a xml document puzzleElement from a cell for exporting
     *
     * @param document xml document
     * @param data PuzzleElement cell
     * @return xml PuzzleElement
     */
    public org.w3c.dom.Element exportCell(Document document, PuzzleElement data)
    {
        org.w3c.dom.Element cellElement = document.createElement("cell");

        ShortTruthTableCell cell = (ShortTruthTableCell)data;

        cellElement.setAttribute("value", String.valueOf(cell.getData()));

        return cellElement;
    }
}
