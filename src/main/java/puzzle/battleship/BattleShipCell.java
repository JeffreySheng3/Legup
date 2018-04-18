package puzzle.battleship;

import model.gameboard.GridCell;

import java.awt.*;

import static puzzle.battleship.BattleShipCellType.CLUE;

public class BattleShipCell extends GridCell {
    private boolean isShipSegment;

    public BattleShipCell(int valueInt, Point location) {
        super(valueInt, location);
        switch (getType()) {
            case WATER:
                this.isShipSegment = false;
                break;
            case CLUE:
                this.isShipSegment = false;
                break;
            case UNKNOWN:
                this.isShipSegment = false;
            default:
                this.isShipSegment = true;
        }
    }

    public BattleShipCellType getType() {
        switch (valueInt) {
            case 0:
                return BattleShipCellType.WATER;
            case 1:
                return BattleShipCellType.SHIP_SEGMENT;
            case 2:
                return BattleShipCellType.SHIP_NORTH_END;
            case 3:
                return BattleShipCellType.SHIP_SOUTH_END;
            case 4:
                return BattleShipCellType.SHIP_WEST_END;
            case 5:
                return BattleShipCellType.SHIP_EAST_END;
            case 6:
                return BattleShipCellType.SHIP_CENTER;
            case 7:
                return BattleShipCellType.SHIP_SINGLE;
            case 8:
                return CLUE;
            case 9:
                return BattleShipCellType.UNKNOWN;
        }
        return null;
    }

    public boolean isShipSegment() { return isShipSegment; }

    public void setShipSegment(boolean isShipSegment) { this.isShipSegment = isShipSegment; }
}
