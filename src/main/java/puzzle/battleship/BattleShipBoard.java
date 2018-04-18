package puzzle.battleship;

import model.gameboard.GridBoard;

import java.util.ArrayList;

public class BattleShipBoard extends GridBoard {
    private int right[];
    private int bottom[];
    private ArrayList<Ship> ships;

    public  BattleShipBoard(int width, int height) {
        super(width, height);
        right = new int[height];
        bottom = new int[width];
        ships = new ArrayList<>();
    }

    public BattleShipBoard(int size) {
        this(size, size);
    }

    @Override
    public BattleShipCell getCell(int x, int y) {
        return (BattleShipCell) super.getCell(x, y);
    }

    public int[] getRight() {
        return right;
    }

    public void setRight(int[] right) {

        this.right = right;
    }

    public int[] getBottom() {
        return bottom;
    }

    public void setBottom(int[] bottom) {
        this.bottom = bottom;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }
}