package MIllerSun.tankwar;

import javax.swing.*;
import java.awt.*;

class Missile {
    private static final int SPEED = 10;
    private int x;
    private int y;

    private final boolean enemy;
    private final Direction direction;

    Missile(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    private Image getImage() {
        String prefix = enemy ? "e" : "";
        switch (direction) {
            case UP:
                return new ImageIcon("assets/images/" + prefix + "missileU.gif").getImage();
            case DOWN:
                return new ImageIcon("assets/images/" + prefix + "missileD.gif").getImage();
            case LEFT:
                return new ImageIcon("assets/images/" + prefix + "missileL.gif").getImage();
            case RIGHT:
                return new ImageIcon("assets/images/" + prefix + "missileR.gif").getImage();
            case UPLEFT:
                return new ImageIcon("assets/images/" +prefix + "missileLU.gif").getImage();
            case UPRIGT:
                return new ImageIcon("assets/images/" + prefix + "missileRU.gif").getImage();
            case DOWNLEFT:
                return new ImageIcon("assets/images/" +prefix + "missileLD.gif").getImage();
            case DOWNRIGHT:
                return new ImageIcon("assets/images/" +prefix + "missileRD.gif").getImage();
        }
        return null;
    }

   void  move() {
       switch (direction) {
           case UP:
               y -= SPEED;
               break;
           case DOWN:
               y += SPEED;
               break;
           case LEFT:
               x -= SPEED;
               break;
           case RIGHT:
               x += SPEED;
               break;
           case UPLEFT:
               x -= SPEED;
               y -= SPEED;
               break;
           case UPRIGT:
               x += SPEED;
               y -= SPEED;
               break;
           case DOWNLEFT:
               x -= SPEED;
               y += SPEED;
               break;
           case DOWNRIGHT:
               x += SPEED;
               y += SPEED;
               break;
       }
   }

    void draw(Graphics g) {
        move();
        if (x < 0 || x > 800 || y < 0 || y > 600) {
            return;
        }
        g.drawImage(getImage(), x, y, null);
    }
}
