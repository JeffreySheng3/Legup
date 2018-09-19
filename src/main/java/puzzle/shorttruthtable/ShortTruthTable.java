package puzzle.shorttruthtable;

import model.Puzzle;
import model.gameboard.Board;
import model.gameboard.ElementData;
import model.tree.Tree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import puzzle.nurikabe.NurikabeBoard;
import puzzle.nurikabe.NurikabeCell;
import puzzle.nurikabe.NurikabeView;
import puzzle.shorttruthtable.ShortTruthTableBoard;
import puzzle.shorttruthtable.ShortTruthTableCell;
import ui.Selection;
import ui.boardview.PuzzleElement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ShortTruthTable extends Puzzle {

    public ShortTruthTable() {
        super();
        this.name = "ShortTruthTable";

        this.importer = new ShortTruthTableImporter(this);
        this.exporter = new ShortTruthTableExporter(this);

        this.factory = new ShortTruthTableCellFactory();

//        basicRules.add(new AdvancedDeductionBasicRule());
//        basicRules.add(new LastCellForNumberBasicRule());
//        basicRules.add(new LastNumberForCellBasicRule());
//
//        caseRules.add(new PossibleCellCaseRule());
//        caseRules.add(new PossibleNumberCaseRule());
//
//        contradictionRules.add(new NoSolutionContradictionRule());
//        contradictionRules.add(new RepeatedNumberContradictionRule());
    }

    @Override
    public void initializeView() {
        ShortTruthTableBoard board= (ShortTruthTableBoard)currentBoard;
        boardView = new ShortTruthTableView(board.getDimension());
        for(PuzzleElement element: boardView.getPuzzleElements())
        {
            int index = element.getIndex();
            ShortTruthTableCell cell = (ShortTruthTableCell)currentBoard.getElementData(index);

            cell.setIndex(index);
            element.setData(cell);
        }
    }
    /**
     * Generates a random puzzle based on the difficulty
     *
     * @param difficulty level of difficulty (1-10)
     *
     * @return board of the random puzzle
     */
    @Override
    public Board generatePuzzle(int difficulty)
    {
        return null;
    }

    /**
     * Determines if the current board is a valid state
     *
     * @param board board to check for validity
     *
     * @return true if board is valid, false otherwise
     */
    @Override
    public boolean isBoardComplete(Board board)
    {
        return false;
    }

    /**
     * Callback for when the board data changes
     *
     * @param board the board that has changed
     */
    @Override
    public void onBoardChange(Board board)
    {

    }

    /**
     * Callback for when the tree selection changes
     *
     * @param newSelection
     */
    @Override
    public void onTreeSelectionChange(ArrayList<Selection> newSelection)
    {

    }

    /**
     * Imports the board using the file stream
     *
     * @param fileName
     *
     * @return
     */
    @Override
    public void importPuzzle(String fileName) throws IOException, ParserConfigurationException, SAXException
    {
        if(fileName != null)
        {
            InputStream inputStream = new FileInputStream(fileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            ShortTruthTableBoard shortTruthTableBoard;

            Element rootNode = document.getDocumentElement();
            Element puzzleElement = (Element)rootNode.getElementsByTagName("puzzle").item(0);
            Element boardElement = (Element)puzzleElement.getElementsByTagName("board").item(0);
            Element dataElement = (Element)boardElement.getElementsByTagName("data").item(0);
            NodeList elementDataList = dataElement.getElementsByTagName("element");

            int width = Integer.valueOf(boardElement.getAttribute("width"));
            int height = Integer.valueOf(boardElement.getAttribute("height"));
            shortTruthTableBoard = new ShortTruthTableBoard(width,height);

            ArrayList<ElementData> shortTruthTableData = new ArrayList<>();
            for(int i = 0; i < width * height; i++)
            {
                shortTruthTableData.add(null);
            }

            for(int i = 0; i < elementDataList.getLength(); i++)
            {
                NamedNodeMap attributeList = elementDataList.item(i).getAttributes();
                int value = Integer.valueOf(attributeList.getNamedItem("value").getNodeValue());
                int x = Integer.valueOf(attributeList.getNamedItem("x").getNodeValue());
                int y = Integer.valueOf(attributeList.getNamedItem("y").getNodeValue());
                ShortTruthTableCell cell = new ShortTruthTableCell(value, new Point(x, y));
                shortTruthTableBoard.setCell(x, y, cell);
                if(cell.getValueInt() != -2)
                {
                    cell.setModifiable(false);
                    cell.setGiven(true);
                }
            }

            for(int y = 0; y < width; y++)
            {
                for(int x = 0; x < height; x++)
                {
                    if(shortTruthTableBoard.getCell(x, y) == null)
                    {
                        ShortTruthTableCell cell = new ShortTruthTableCell(-2, new Point(x, y));
                        cell.setModifiable(true);
                        shortTruthTableBoard.setCell(x, y, cell);
                    }
                }
            }
            this.currentBoard = shortTruthTableBoard;
            this.tree = new Tree(currentBoard);
        }
    }
}
