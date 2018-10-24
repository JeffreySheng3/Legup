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
                throw new InvalidFileFormatException("shorttruthtable Importer: cannot find board puzzleElement");
            }
            Element boardElement = (Element)node;
            if(boardElement.getElementsByTagName("premises").getLength() == 0)
            {
                throw new InvalidFileFormatException("shorttruthtable Importer: no premises found in board");
            }
            Element dataElement = (Element)boardElement.getElementsByTagName("premises").item(0);
            NodeList premiseDataList = dataElement.getElementsByTagName("premise");
            Element conclusion = (Element)boardElement.getElementsByTagName("conclusion").item(0);

            int height = 0;
            int width = 0;

            for (int i=0; i<premiseDataList.getLength()+1; ++i) {
                int width_current = 0;
                height++;
                String s;
                if (i==premiseDataList.getLength()) s = conclusion.getAttribute("sentence");
                else s = ((Element)premiseDataList.item(i)).getAttribute("sentence");
                for (int j=0; j < s.length(); j++) {
                    char c = s.charAt(j);
                    if (c==' ') continue;
                    else if (65 <= c && c <= 90) {
                        width_current++;
                    }
                    else if (c=='<' || c=='-') {
                        width_current++;
                        j+=2;
                    }
                    else if (c=='^' || c=='v' || c=='~' || c=='(' || c==')' ) {
                        width_current++;
                    }
                    else throw new InvalidFileFormatException("shorttruthtable Importer: invalid character found in sentence " + s);
                }
                // maximum sentence width, therefore "board" width
                width = (width_current > width) ? width_current : width;
            }

            ShortTruthTableBoard shortTruthTableBoard = new ShortTruthTableBoard(width, height);

            for(int i = 0; i < width; i++) {
                for(int j = 0; j < height; j++) {
                    ShortTruthTableCell cell = new ShortTruthTableCell(0);

                    String s;
                    if (j==premiseDataList.getLength()) s = conclusion.getAttribute("sentence");
                    else s = ((Element)premiseDataList.item(i)).getAttribute("sentence");
                    char c = ' ';
                    try {
                        c = s.charAt(i);
                    }
                    catch(StringIndexOutOfBoundsException ex) {

                    }
                    if (c==' ') continue;
                    else if (65 <= c && c <= 90) {
                        cell.setData(0);
                    }
                    else if (c=='<' || c=='-') {
                        width_current++;
                        j+=2;
                    }
                    else if (c=='^' || c=='v' || c=='~' || c=='(' || c==')' ) {
                        width_current++;
                    }
                    cell.setIndex(i*j);
                    cell.setData();
                    cell.setModifiable(true);
                    shortTruthTableBoard.setCell(i,j,cell);
                }
            }

            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    if(shortTruthTableBoard.getCell(x, y) == null) {
                        ShortTruthTableCell cell = new ShortTruthTableCell(-2);
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
