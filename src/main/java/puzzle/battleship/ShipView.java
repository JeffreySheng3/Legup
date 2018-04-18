package puzzle.battleship;

import java.awt.*;
import java.util.ArrayList;

public class ShipView {
    protected ArrayList<Ship> ships;
    protected int SHIP_ELEMENT_SIZE;
    protected int longestShip;
    protected int numberOfShips;
    public ShipView(ArrayList<Ship> battleShips) {
        this.ships = new ArrayList<>();

        longestShip = 0;
        numberOfShips = 0;
        SHIP_ELEMENT_SIZE = 30;
        for (int i = 0; i < battleShips.size(); i++) {
            for (int j = 0; j < battleShips.get(i).count; j++) {
                ships.add(battleShips.get(i));
            }
            if (ships.get(i).length > longestShip) {
                longestShip = ships.get(i).length;
            }
            numberOfShips+=ships.get(i).count;
        }
    }

    public void drawShips(Graphics2D graphics2D)
    {
        graphics2D.setColor(Color.BLACK);

        /*
        int boardClearance = 300;
        int shipTableWidth = longestShip + 8;
        int shipTableHeight = numberOfShips*(SHIP_ELEMENT_SIZE+2) + 8;
        for (int i = 0; i < this.ships.size(); i++) {
            graphics2D.fillOval(0+boardClearance, i*(SHIP_ELEMENT_SIZE+2), SHIP_ELEMENT_SIZE, SHIP_ELEMENT_SIZE);
            graphics2D.fillRect(SHIP_ELEMENT_SIZE/2+boardClearance, i*(SHIP_ELEMENT_SIZE+2), SHIP_ELEMENT_SIZE-SHIP_ELEMENT_SIZE/2, SHIP_ELEMENT_SIZE);
            for (int j = 1; j < this.ships.get(i).length-1; j++) {
                graphics2D.fillRect(j*SHIP_ELEMENT_SIZE+boardClearance, i*(SHIP_ELEMENT_SIZE+2), SHIP_ELEMENT_SIZE, SHIP_ELEMENT_SIZE);
            }
            graphics2D.fillOval((this.ships.get(i).length-1)*SHIP_ELEMENT_SIZE+boardClearance, i*(SHIP_ELEMENT_SIZE+2), SHIP_ELEMENT_SIZE, SHIP_ELEMENT_SIZE);
            graphics2D.fillRect((this.ships.get(i).length-1)*SHIP_ELEMENT_SIZE+boardClearance, i*(SHIP_ELEMENT_SIZE+2), SHIP_ELEMENT_SIZE-SHIP_ELEMENT_SIZE/2, SHIP_ELEMENT_SIZE);
        }

        graphics2D.drawRect(0+boardClearance,0,longestShip*SHIP_ELEMENT_SIZE, numberOfShips*SHIP_ELEMENT_SIZE);
        */
    }
}