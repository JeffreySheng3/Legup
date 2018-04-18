package puzzle.battleship;

import ui.boardview.GridElement;

import java.awt.*;

public class BattleShipElement extends GridElement {
    private static final Color WATER_COLOR = Color.BLUE; // CAN CHANGE THIS TO ANY BLUE YOU WANT
    private static final Color SHIP_COLOR = Color.DARK_GRAY;
    private static final Color UNKNOWN_COLOR = Color.LIGHT_GRAY;
    private int clue;

    public BattleShipElement(BattleShipCell cell) {super(cell); }

    @Override public void drawElement(Graphics2D graphics2D) {
        BattleShipCell cell = (BattleShipCell) data;
        BattleShipCellType type = cell.getType();

        graphics2D.setColor(WATER_COLOR);
        graphics2D.fillRect(location.x, location.y, size.width, size.height);

        switch (type) {
            case UNKNOWN:
                graphics2D.setColor(UNKNOWN_COLOR);
                graphics2D.fillRect(location.x, location.y, size.width, size.height);
                break;
            default:
                drawShipPiece(graphics2D, type);
        }

        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.drawRect(location.x, location.y, size.width, size.height);
    }

    public void drawShipPiece(Graphics2D graphics2D, BattleShipCellType type) {
        graphics2D.setColor(SHIP_COLOR);
        switch (type) {
            case SHIP_SEGMENT:
                graphics2D.fillRect(location.x+size.width/4, location.y+size.height/4, size.width/2, size.height/2);
                break;
            case SHIP_NORTH_END:
                graphics2D.fillOval(location.x, location.y, size.width, size.height);
                graphics2D.fillRect(location.x, location.y+size.width/2, size.width, size.height-size.width/2);
                break;
            case SHIP_SOUTH_END:
                graphics2D.fillOval(location.x, location.y, size.width, size.height);
                graphics2D.fillRect(location.x, location.y, size.width, size.height-size.width/2);
                break;
            case SHIP_WEST_END:
                graphics2D.fillOval(location.x, location.y, size.width, size.height);
                graphics2D.fillRect(location.x+size.height/2, location.y, size.width-size.height/2, size.height);
                break;
            case SHIP_EAST_END:
                graphics2D.fillOval(location.x, location.y, size.width, size.height);
                graphics2D.fillRect(location.x, location.y, size.width-size.height/2, size.height);
                break;
            case SHIP_CENTER:
                graphics2D.fillRect(location.x, location.y, size.width, size.height);
                break;
            case SHIP_SINGLE:
                graphics2D.fillOval(location.x, location.y, size.width, size.height);
                break;
            default:
                graphics2D.setColor(WATER_COLOR);
                graphics2D.fillRect(location.x, location.y, size.width, size.height);
        }
    }

    public int getClue() {
        return clue;
    }

    public void setClue(int clue) {
        this.clue = clue;
    }
}