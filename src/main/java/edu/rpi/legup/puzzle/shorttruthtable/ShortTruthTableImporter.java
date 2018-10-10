package edu.rpi.legup.puzzle.shorttruthtable;

import edu.rpi.legup.model.PuzzleImporter;
import edu.rpi.legup.save.InvalidFileFormatException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;

public class ShortTruthTableImporter extends PuzzleImporter
{
    public ShortTruthTableImporter(ShortTruthTable shortTruthTable)
    {
        super(shortTruthTable);
    }

    /**
     * Creates the board for building
     *
     * @param node xml document node
     * @throws InvalidFileFormatException
     */
    @Override
    public void initializeBoard(Node node) throws InvalidFileFormatException
    {
        try
        {
            if(!node.getNodeName().equalsIgnoreCase("board"))
            {
                throw new InvalidFileFormatException("lightup Importer: cannot find board puzzleElement");
            }
            Element boardElement = (Element)node;
            if(boardElement.getElementsByTagName("cells").getLength() == 0)
            {
                throw new InvalidFileFormatException("lightup Importer: no puzzleElement found for board");
            }
            Element dataElement = (Element)boardElement.getElementsByTagName("cells").item(0);
            NodeList elementDataList = dataElement.getElementsByTagName("cell");

            ShortTruthTableBoard shortTruthTableBoard = null;
//           w
            if(!boardElement.getAttribute("width").isEmpty() && !boardElement.getAttribute("height").isEmpty())
            {
                int width = Integer.valueOf(boardElement.getAttribute("width"));
                int height = Integer.valueOf(boardElement.getAttribute("height"));
                shortTruthTableBoard = new ShortTruthTableBoard(width, height);
            }

            if(shortTruthTableBoard == null)
            {
                throw new InvalidFileFormatException("lightup Importer: invalid board dimensions");
            }

            int width = shortTruthTableBoard.getWidth();
            int height = shortTruthTableBoard.getHeight();

            for(int i = 0; i < elementDataList.getLength(); i++)
            {
                ShortTruthTableCell cell = (ShortTruthTableCell)puzzle.getFactory().importCell(elementDataList.item(i), shortTruthTableBoard);
                Point loc = cell.getLocation();
                if(cell.getData() != -2)
                {
                    cell.setModifiable(false);
                    cell.setGiven(true);
                }
                shortTruthTableBoard.setCell(loc.x, loc.y, cell);
            }

            for(int y = 0; y < height; y++)
            {
                for(int x = 0; x < width; x++)
                {
                    if(shortTruthTableBoard.getCell(x, y) == null)
                    {
                        ShortTruthTableCell cell = new ShortTruthTableCell(-2, new Point(x, y));
                        cell.setIndex(y * height + x);
                        cell.setModifiable(true);
                        shortTruthTableBoard.setCell(x, y, cell);
                    }
                }
            }
            puzzle.setCurrentBoard(shortTruthTableBoard);
        }
        catch(NumberFormatException e)
        {
            throw new InvalidFileFormatException("shorttruthtable Importer: unknown value where integer expected");
        }
    }
}
