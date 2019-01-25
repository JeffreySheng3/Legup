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
            if(!node.getNodeName().equalsIgnoreCase("board")) {
                throw new InvalidFileFormatException("shorttruthtable Importer: cannot find board puzzleElement");
            }
            Element boardElement = (Element)node;
            if (boardElement.getElementsByTagName("premises").getLength() == 0 && boardElement.getElementsByTagName("sentence").getLength() == 0) {
                throw new InvalidFileFormatException("shorttruthtable Importer: no premises or sentences found in board");
            }
            Element dataElement = (Element)boardElement.getElementsByTagName("premises").item(0);
            NodeList sentenceDataList = boardElement.getElementsByTagName("sentence");
            NodeList premiseDataList = null;
            if (dataElement.getElementsByTagName("premise")!=null) premiseDataList = dataElement.getElementsByTagName("premise");
            Element conclusion = (Element)boardElement.getElementsByTagName("conclusion").item(0);

            int height = 0;
            int width = 0;

            for (int i=0; i<premiseDataList.getLength()+sentenceDataList.getLength()+1; ++i) {
                int width_current = 0;
                height++;
                String s;
                if (i==premiseDataList.getLength()+sentenceDataList.getLength()) s = conclusion.getAttribute("sentence");
                else if (i>=premiseDataList.getLength()) s = ((Element)sentenceDataList.item(i-premiseDataList.getLength())).getAttribute("sen");
                else s = ((Element)premiseDataList.item(i)).getAttribute("sentence");
                for (int j=0; j < s.length(); j++) {
                    char c = s.charAt(j);
                    if (65 <= c && c <= 90) {
                        width_current++;
                    }
                    else if (c=='<' || c=='-') {
                        width_current++;
                        j+=2;
                    }
                    else if (c=='^' || c=='v' || c=='~' || c=='(' || c==')' ) {
                        width_current++;
                    }
                    else if (c!=' ') throw new InvalidFileFormatException("shorttruthtable Importer: invalid character found in sentence " + s);
                }
                // maximum sentence width, therefore "board" width
                System.out.println("Current Sentence '" + s + "' has length " + width_current);
                width = (width_current > width) ? width_current : width;
            }

            ShortTruthTableBoard shortTruthTableBoard = new ShortTruthTableBoard(width, height);

            for(int i = 0; i < height; i++) {
                String s;
                if (i==premiseDataList.getLength()+sentenceDataList.getLength()) s = conclusion.getAttribute("sentence");
                else if (i>=premiseDataList.getLength()) s = ((Element)sentenceDataList.item(i-premiseDataList.getLength())).getAttribute("sen");
                else s = ((Element)premiseDataList.item(i)).getAttribute("sentence");
                // remove spaces from the string, so as not to create blank tiles
                s = s.replaceAll("\\s","");

                for (int j = 0,k = 0; k < width; j++,k++) {
                    ShortTruthTableCell cell = new ShortTruthTableCell(0);
                    cell.setModifiable(true);

                    char c = ' ';
                    try {
                        c = s.charAt(j);
                    }
                    catch(StringIndexOutOfBoundsException ex) {
                        cell.setData(-2);
                    }
                    if (c==' ') {
                        cell.setModifiable(false);
                    }
                    else if (65 <= c && c <= 90) {
                        cell.setLetter(c);
                    }
                    else if (c=='<' || c=='-') {
                        j+=2;
                        cell.setConnective(c);
                    }
                    else if (c=='^' || c=='v' || c=='~' || c=='(' || c==')' ) {
                        cell.setConnective(c);
                    }
                    cell.setIndex(i*width+k);
                    cell.setLocation(k,i);
                    System.out.println("Setting cell (" + k + "," + i + "): " + cell);
                    shortTruthTableBoard.setCell(k,i,cell);
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
