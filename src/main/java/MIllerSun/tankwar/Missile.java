package MIllerSun.tankwar;

import javax.swing.*;
import java.awt.*;

class Missile {
    private static final int SPEED = 10;
    private int x;
    private int y;

    private final boolean enemy;
    private final Direction direction;
    private boolean live = true;

    boolean isLive() {
        return live;
    }

    void setLive(boolean live) {
        this.live = live;
    }

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
            this.live = false;
            return;
        }

        Rectangle rectangle = this.getRectangle();
        for(Wall wall : GameClient.getInstance().getWa1ls()) {
            if (rectangle.intersects(wall.getRectangle())) {
                this.setLive(false);
                return;
            }
        }

        if (enemy) {
            Tank playTank = GameClient.getInstance().getPlayerTank();
            if (rectangle.intersects(playTank.getRectangle())) {
                playTank.setHp(playTank.getHp() - 20);
                if (playTank.getHp() <= 0) {
                    playTank.setLive(false);
                }
                this.setLive(false);
            }

        } else {
            for (Tank tank : GameClient.getInstance().getEnemyTanks()) {
                if (rectangle.intersects(tank.getRectangle())) {
                    tank.setLive(false);
                    this.setLive(false);
                    break;
                }
            }
        }


        g.drawImage(getImage(), x, y, null);
    }

    Rectangle getRectangle() {
        return new Rectangle(x, y, getImage().getWidth(null), getImage().getHeight(null));
    }
}
