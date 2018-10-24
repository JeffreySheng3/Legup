package edu.rpi.legup.puzzle.shorttruthtable;

import edu.rpi.legup.controller.BoardController;
import edu.rpi.legup.model.gameboard.CaseBoard;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.tree.TreeElement;
import edu.rpi.legup.ui.boardview.DataSelectionView;
import edu.rpi.legup.ui.boardview.GridBoardView;
import edu.rpi.legup.ui.boardview.SelectionItemView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ShortTruthTableView extends GridBoardView
{
    static Image lightImage;
    static
    {
        try
        {
            lightImage = ImageIO.read(ClassLoader.getSystemClassLoader().getResource("edu/rpi/legup/images/lightup/light.png"));
        }
        catch(IOException e)
        {

        }
    }

    public ShortTruthTableView(ShortTruthTableBoard board)
    {
        super(new BoardController(), new ShortTruthTableCellController(), board.getDimension());

        for(PuzzleElement puzzleElement : board.getPuzzleElements())
        {
            ShortTruthTableCell cell = (ShortTruthTableCell) puzzleElement;
            ShortTruthTableElementView elementView = new ShortTruthTableElementView(cell);
            elementView.setIndex(cell.getIndex());
            elementView.setSize(elementSize);
            elementViews.add(elementView);
        }
    }
    /**
     * Called when the tree element has changed.
     *
     * @param treeElement tree element
     */
    @Override
    public void onTreeElementChanged(TreeElement treeElement)
    {
        super.onTreeElementChanged(treeElement);
        ShortTruthTableBoard shortTruthTableBoard = board instanceof CaseBoard ? (ShortTruthTableBoard)((CaseBoard)board).getBaseBoard() : (ShortTruthTableBoard)board;
        repaint();
    }

    public DataSelectionView getSelectionPopupMenu()
    {
        DataSelectionView selectionView = new DataSelectionView(elementController);
        GridLayout layout = new GridLayout(3,1);
        selectionView.setLayout(layout);

        Dimension iconSize = new Dimension(32,32);
        Point loc = new Point(0,0);

        ShortTruthTableElementView element1 = new ShortTruthTableElementView(new ShortTruthTableCell(-2));
        element1.setSize(iconSize);
        element1.setLocation(loc);
        SelectionItemView item1 = new SelectionItemView(element1.getPuzzleElement(), new ImageIcon(element1.getImage()));
        item1.addActionListener(elementController);
        item1.setHorizontalTextPosition(SwingConstants.CENTER);
        selectionView.add(item1);

        ShortTruthTableElementView element2 = new ShortTruthTableElementView(new ShortTruthTableCell(-4));
        element2.setSize(iconSize);
        element2.setLocation(loc);
        SelectionItemView item2 = new SelectionItemView(element2.getPuzzleElement(), new ImageIcon(element2.getImage()));
        item2.addActionListener(elementController);
        item2.setHorizontalTextPosition(SwingConstants.CENTER);
        selectionView.add(item2);

        ShortTruthTableElementView element3 = new ShortTruthTableElementView(new ShortTruthTableCell(-3));
        element3.setSize(iconSize);
        element3.setLocation(loc);
        SelectionItemView item3 = new SelectionItemView(element3.getPuzzleElement(), new ImageIcon(element3.getImage()));
        item3.addActionListener(elementController);
        item3.setHorizontalTextPosition(SwingConstants.CENTER);
        selectionView.add(item3);

        return selectionView;
    }
}
