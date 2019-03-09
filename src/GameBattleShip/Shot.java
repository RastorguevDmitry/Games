package GameBattleShip;

import java.awt.*;

class Shot {
    private int x, y;
    private boolean shot;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isShot() {
        return shot;
    }

    Shot(int x, int y, boolean shot) {
        this.x = x;
        this.y = y;
        this.shot = shot;
    }

    void paint(Graphics g, int cellSize) {
        g.setColor(Color.GRAY);
        if(shot) //если выстрел
            g.fillRect(x * cellSize + cellSize / 2 - 3, y * cellSize + cellSize / 2 - 3, 8, 8);
        else g.drawRect(x * cellSize + cellSize / 2 - 3, y * cellSize + cellSize / 2 - 3, 8, 8); //иначе маркер
    }

}
