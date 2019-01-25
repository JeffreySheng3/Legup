package edu.rpi.legup.puzzle.shorttruthtable;

import edu.rpi.legup.ui.boardview.ElementView;

import java.awt.*;

public class ShortTruthTableElementView extends ElementView
{
    private static final Color LITE = new Color(0xFFF176);
    private static final Font FONT = new Font("TimesRoman", Font.BOLD, 16);

    private static final Color BLACK_COLOR = new Color(0x212121);
    private static final Color WHITE_COLOR = new Color(0xF5F5F5);
    private static final Color RED_HIGHLIGHT = new Color(0xFF817B);
    private static final Color GREEN_HIGHLIGHT = new Color(0x98FB98);
    private static final Color GRAY_COLOR = new Color(0x9E9E9E);

    public ShortTruthTableElementView(ShortTruthTableCell cell)
    {
        super(cell);
    }

    /**
     * Gets the PuzzleElement associated with this view
     *
     * @return PuzzleElement associated with this view
     */
    @Override
    public ShortTruthTableCell getPuzzleElement() {
        return (ShortTruthTableCell) super.getPuzzleElement();
    }

    @Override
    public void drawElement(Graphics2D graphics2D)
    {
        ShortTruthTableCell cell = (ShortTruthTableCell) puzzleElement;
        ShortTruthTableCellType type = cell.getType();
        int highlight = -1;
        if (cell.isAtomic() && cell.getValue() != null) {
            if (cell.getValue()) highlight = 1;
            else highlight = 0;
        }
        if (type == ShortTruthTableCellType.VARIABLE)
        {
            graphics2D.setStroke(new BasicStroke(1));
            graphics2D.setColor(WHITE_COLOR);
            graphics2D.fillRect(location.x, location.y, size.width, size.height);

            graphics2D.setColor(BLACK_COLOR);
            graphics2D.setFont(FONT);
            FontMetrics metrics = graphics2D.getFontMetrics(FONT);
            String value = String.valueOf(cell.getLetter());
            int xText = location.x + (size.width - metrics.stringWidth(value)) / 2;
            int yText = location.y + ((size.height - metrics.getHeight()) / 2) + metrics.getAscent();
            graphics2D.drawString(value, xText, yText);
        }
        else if(type == ShortTruthTableCellType.EMPTY)
        {
            graphics2D.setStroke(new BasicStroke(1));
            graphics2D.setColor(WHITE_COLOR);
            graphics2D.fillRect(location.x, location.y, size.width, size.height);

//            graphics2D.fillRect(location.x + size.width * 7 / 16, location.y + size.height * 7 / 16, size.width / 8, size.height / 8);
            graphics2D.drawRect(location.x, location.y, size.width, size.height);
        }
        else if(type == ShortTruthTableCellType.SYMBOL)
        {
            graphics2D.setStroke(new BasicStroke(1));
            if (highlight==-1) graphics2D.setColor(WHITE_COLOR);
            else if (highlight==0) graphics2D.setColor(RED_HIGHLIGHT);
            else if (highlight==1) graphics2D.setColor(GREEN_HIGHLIGHT);
            graphics2D.fillRect(location.x, location.y, size.width, size.height);
            graphics2D.drawRect(location.x, location.y, size.width, size.height);

            graphics2D.setColor(BLACK_COLOR);
            graphics2D.setFont(FONT);
            FontMetrics metrics = graphics2D.getFontMetrics(FONT);
            String value = String.valueOf(cell.getConnective());
            if (cell.getConnective()=='-') value = String.valueOf("\u2192");
            else if (cell.getConnective()=='<') value = String.valueOf("\u2194");
            else if (cell.getConnective()=='~') value = String.valueOf("\u00AC");
            else if (cell.getConnective()=='^') value = String.valueOf("\u2227");
            int xText = location.x + (size.width - metrics.stringWidth(value)) / 2;
            int yText = location.y + ((size.height - metrics.getHeight()) / 2) + metrics.getAscent();
            graphics2D.drawString(value, xText, yText);
        }
    }
}
