package puzzle.battleship;

import controller.BoardController;
import model.Puzzle;
import model.gameboard.Board;
import model.gameboard.ElementData;
import ui.boardview.GridBoardView;
import ui.boardview.PuzzleElement;

import javax.lang.model.element.Element;
import java.awt.*;
import java.util.ArrayList;

public class BattleShipView extends GridBoardView {
    protected BattleShipElement rightClues[];
    protected BattleShipElement bottomClues[];
    protected ArrayList<Ship> ships;
    public BattleShipView(Dimension gridSize) {
        super(new BoardController(), new BattleShipCellController(), gridSize);
        rightClues = new BattleShipElement[gridSize.height];
        bottomClues = new BattleShipElement[gridSize.width];
        ships = new ArrayList<>();

        for (int i = 0; i < gridSize.height; i++) {
            Point rightLocation = new Point(gridSize.width, i);
            BattleShipElement rightElement = new BattleShipElement(new BattleShipCell(8, null));
            rightElement.setIndex(i);
            rightElement.setSize(elementSize);
            rightElement.setLocation(rightLocation);
            rightClues[i] = rightElement;
        }

        for (int i = 0; i < gridSize.width; i++) {
            Point bottomLocation = new Point(i, gridSize.height);
            BattleShipElement bottomElement = new BattleShipElement(new BattleShipCell(8,null));
            bottomElement.setIndex(i);
            bottomElement.setSize(elementSize);
            bottomElement.setLocation(bottomLocation);
            bottomClues[i] = bottomElement;
        }

        for (int i = 0; i < gridSize.width; i++) {
            for (int j = 0; j < gridSize.height; j++) {
                Point location = new Point(i*elementSize.width, j*elementSize.height);
                BattleShipElement element = new BattleShipElement(new BattleShipCell(9, null));
                element.setIndex(i*gridSize.width+j);
                element.setSize(elementSize);
                element.setLocation(location);
                puzzleElements.add(element);
            }
        }
    }

    @Override
    public void drawBoard(Graphics2D graphics2D)
    {
        int elementWidth = 0;
        int elementHeight = 0;
        for(PuzzleElement element: puzzleElements)
        {
            element.draw(graphics2D);
            elementWidth = element.getSize().width;
            elementHeight = element.getSize().height;
        }

        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(new Font("TimesRoman", Font.BOLD, 16));
        FontMetrics metrics = graphics2D.getFontMetrics(new Font("TimesRoman", Font.BOLD, 16));

        for (int i = 0; i < rightClues.length; i++) {
            String value = String.valueOf(rightClues[i].getClue());
            int xText = elementWidth*rightClues.length + (elementWidth - metrics.stringWidth(value)) / 2;
            int yText = elementHeight*i + ((elementHeight - metrics.getHeight()) / 2) + metrics.getAscent();
            graphics2D.drawString(value, xText, yText);
        }

        for (int i = 0; i < bottomClues.length; i++) {
            String value = String.valueOf(bottomClues[i].getClue());
            int xText = elementWidth*i + (elementWidth - metrics.stringWidth(value)) / 2;
            int yText = elementHeight*bottomClues.length + ((elementHeight - metrics.getHeight()) / 2) + metrics.getAscent();
            graphics2D.drawString(value, xText, yText);
        }

        ShipView sv = new ShipView(ships);
    }

    @Override
    public void updateBoard(Board board) {
        BattleShipBoard battleShipBoard = (BattleShipBoard) board;

        for (int i = 0; i < rightClues.length; i++) {
            rightClues[i].setClue(battleShipBoard.getRight()[i]);
            System.out.println(rightClues[i].getClue()+" ");
        }

        for (int i = 0; i < bottomClues.length; i++) {
            bottomClues[i].setClue(battleShipBoard.getBottom()[i]);
        }

        ships = battleShipBoard.getShips();

        for (PuzzleElement element: puzzleElements) {
            element.setData(battleShipBoard.getElementData(element.getIndex()));
        }
        repaint();
    }

    @Override
    protected Dimension getProperSize()
    {
        Dimension boardViewSize = new Dimension();
        // boardViewSize.width = (gridSize.width+2) * elementSize.width;
        // boardViewSize.height = (gridSize.height+2) * elementSize.height;

        boardViewSize.width = 500;
        boardViewSize.height = 500;
        return boardViewSize;
    }
}