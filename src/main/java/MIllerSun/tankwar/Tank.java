package MIllerSun.tankwar;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;

class Tank {

    private int x;
    private int y;
    private boolean enemy;

    private boolean live = true;

    private int hp = 100;

    int getHp() {
        return hp;
    }

    void setHp(int hp) {
        this.hp = hp;
    }

    boolean isLive() {
        return live;
    }

    void setLive(boolean live) {
        this.live = live;
    }

    boolean isEnemy() {
        return enemy;
    }

    private Direction direction;

    Tank(int x, int y, Direction direction) {
        this(x, y, false, direction);
    }

    Tank(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    private void  move() {
        if (this.stopped) return;

            switch (direction) {
                case UP:
                    y -= 5;
                    break;
                case DOWN:
                    y += 5;
                    break;
                case LEFT:
                    x -= 5;
                    break;
                case RIGHT:
                    x += 5;
                    break;
                case UPLEFT:
                    x -= 5;
                    y -= 5;
                    break;
                case UPRIGT:
                    x += 5;
                    y -= 5;
                    break;
                case DOWNLEFT:
                    x -= 5;
                    y += 5;
                    break;
                case DOWNRIGHT:
                    x += 5;
                    y += 5;
                    break;
            }

    }
    Image getImage() {
        String prefix = enemy ? "e" : "";
        switch (direction) {
            case UP:
                return new ImageIcon("assets/images/" + prefix + "tankU.gif").getImage();
            case DOWN:
                return new ImageIcon("assets/images/" + prefix + "tankD.gif").getImage();
            case LEFT:
                return new ImageIcon("assets/images/" + prefix + "tankL.gif").getImage();
            case RIGHT:
                return new ImageIcon("assets/images/" + prefix + "tankR.gif").getImage();
            case UPLEFT:
                return new ImageIcon("assets/images/" +prefix + "tankLU.gif").getImage();
            case UPRIGT:
                return new ImageIcon("assets/images/" + prefix + "tankRU.gif").getImage();
            case DOWNLEFT:
                return new ImageIcon("assets/images/" +prefix + "tankLD.gif").getImage();
            case DOWNRIGHT:
                return new ImageIcon("assets/images/" +prefix + "tankRD.gif").getImage();
        }
        return null;
    }

    void draw(Graphics g) {
        int oldX = x, oldY = y;
        this.determineDirection();
        this.move();

        if (x <0) x = 0;
        else if (x > 800 - getImage().getWidth(null)) x = 800 - getImage().getWidth(null);
        if (y < 0) y = 0;
        else if (y > 600 - getImage().getHeight(null)) y = 600 - getImage().getHeight(null);

        Rectangle rec = this.getRectangle();
        for (Wall wall: GameClient.getInstance().getWa1ls()) {
             if (rec.intersects(wall.getRectangle())) {
                 x = oldX;
                 y = oldY;
                 break;

             }
        }

        for (Tank tank: GameClient.getInstance().getEnemyTanks()) {
            if (rec.intersects(tank.getRectangle())) {
                x = oldX;
                y = oldY;
                break;

            }
        }

        g.drawImage(this.getImage(),this.x,this.y,null);
    }

    Rectangle getRectangle() {
        return new Rectangle(x, y, getImage().getWidth(null), getImage().getHeight(null));
    }

    private  boolean up, down, left, right;
    void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: up = true; break;
            case KeyEvent.VK_DOWN: down = true; break;
            case KeyEvent.VK_LEFT: left = true; break;
            case KeyEvent.VK_RIGHT: right = true; break;
            case KeyEvent.VK_CONTROL: fire(); break;
            case KeyEvent.VK_A: superFire(); break;
        }

    }

    private void superFire() {
        for (Direction direction : Direction.values()) {
            Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                    y + getImage().getHeight(null) / 2 - 6, enemy, direction);
            GameClient.getInstance().add(missile);
        }
        String audioFile = new Random().nextBoolean() ? "supershoot.aiff" : "shoot.wav";
        playAudio(audioFile);
    }

    private void playAudio(String fileName) {
        Media sound = new Media(new File("assets/audios/" + fileName).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    private void fire() {
        Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                y + getImage().getHeight(null) / 2 - 6, enemy, direction);
        GameClient.getInstance().add(missile);

        playAudio("shoot.wav");
    }

    private boolean stopped;

    private void  determineDirection() {
        if (!up && !right && !down && !left) {
            this.stopped = true;
        }

        else {
            if (up && left && !down && !right) this.direction = Direction.UPLEFT;
        else if (up && right && !down && !left) this.direction = Direction.UPRIGT;
        else if (down && left && !up && !right) this.direction = Direction.DOWNLEFT;
        else if (down && right && !up && !left) this.direction = Direction.DOWNRIGHT;

        else if (up && !right && !down && !left) this.direction = Direction.UP;
        else if (!up && right && !down && !left) this.direction = Direction.RIGHT;
        else if (!up && !right && down && !left) this.direction = Direction.DOWN;
        else if (!up && !right && !down && left) this.direction = Direction.LEFT;

        this.stopped = false;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: up = false; break;
            case KeyEvent.VK_DOWN: down = false; break;
            case KeyEvent.VK_LEFT: left = false; break;
            case KeyEvent.VK_RIGHT: right = false; break;
        }
    }
}
