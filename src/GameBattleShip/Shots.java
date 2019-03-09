package GameBattleShip;

import java.awt.*;
import java.util.ArrayList;

public class Shots {
    private final int CELL_SIZE;
    private ArrayList<Shot> shots;

    Shots(int cellSize) {
        CELL_SIZE = cellSize;
        shots = new ArrayList<Shot>();
    }

    void add(int x, int y, boolean shot) {
        shots.add(new Shot(x, y, shot));
    }

    boolean hitSamePlace(int x, int y) {
        for (Shot shot : shots)
            if (shot.getX() == x && shot.getY() == y && shot.isShot())
                return true;
        return false;
    }

    Shot getLabel(int x, int y) {
        for (Shot label : shots)
            if (label.getX() == x && label.getY() == y && (!label.isShot()))
                return label;
        return null;
    }

    void removeLable(Shot label) {
        shots.remove(label);
    }

    void paint(Graphics graphics) {
        for (Shot shot : shots) shot.paint(graphics, CELL_SIZE);
    }

}
