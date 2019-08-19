package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A token.
 */
class Token extends JButton {

    static final int WIDTH = 20;
    @SuppressWarnings("SuspiciousNameCombination")
    static final int HEIGHT = WIDTH;

    private RotatedIcon icon;

    int player;
    int space;

    private Board board;

    Token(int player, String pathname, Board board) {
        this.board = board;
        this.player = player;
        try {
            File input = new File(pathname);
            BufferedImage image = ImageIO.read(input);
            icon = new RotatedIcon(new ImageIcon(image));
            setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBorderPainted(false);
    }

    /**
     * Moves this token.
     *
     * @param space
     * @param players
     */
    void moveTo(int space, int players) {
        this.space = space;
        Point p1 = getLocation(space, players);
        setLocation(p1.x, p1.y);
        icon.setDegrees(space / 10 * 90);
    }

    private Point getLocation(int space, int player) {
        int x;
        int y;
        int offset = isStreet(space) ? House.WIDTH : 0;
        if (space >= 0 && space <= 9) {
            x = player * Token.WIDTH;
            y = offset;
        } else if (space == 10) {
            x = 0;
            y = player * Token.HEIGHT;
        } else if (space >= 11 && space <= 19) {
            x = Space.HEIGHT - Token.WIDTH - offset;
            y = player * Token.HEIGHT;
        } else if (space == 20) {
            x = Space.HEIGHT - (player + 1) * Token.WIDTH;
            y = Space.HEIGHT - Token.HEIGHT;
        } else if (space >= 21 && space <= 29) {
            x = (2 - player) * Token.WIDTH;
            y = Space.HEIGHT - Token.HEIGHT - offset;
        } else if (space == 30) {
            x = 0;
            y = Space.HEIGHT - (player + 1) * Token.HEIGHT;
        } else if (space >= 31 && space <= 39) {
            x = offset;
            y = (2 - player) * Token.HEIGHT;
        } else {
            return null;
        }
        return new Point(x, y);
    }

    private boolean isStreet(int space) {
        return board.c.isStreet(space);
    }

//    private Point[][] points = new Point[][]{
//            // 0
//            {new Point(0, 0), new Point(20, 0)},
//
//            // 1-9
//            {new Point(0, 15), new Point(20, 15)},
//            {new Point(0, 0), new Point(20, 0)},
//            {new Point(0, 15), new Point(20, 15)},
//            {new Point(0, 0), new Point(20, 0)},
//            {new Point(0, 0), new Point(20, 0)},
//            {new Point(0, 15), new Point(20, 15)},
//            {new Point(0, 0), new Point(20, 0)},
//            {new Point(0, 15), new Point(20, 15)},
//            {new Point(0, 15), new Point(20, 15)},
//
//            // 10
//            {new Point(0, 0), new Point(0, 20)},
//
//            // 11-19
//            {new Point(55, 0), new Point(55, 20)},
//            {new Point(70, 0), new Point(70, 20)},
//            {new Point(55, 0), new Point(55, 20)},
//            {new Point(55, 0), new Point(55, 20)},
//            {new Point(70, 0), new Point(70, 20)},
//            {new Point(55, 0), new Point(55, 20)},
//            {new Point(70, 0), new Point(70, 20)},
//            {new Point(55, 0), new Point(55, 20)},
//            {new Point(55, 0), new Point(55, 20)},
//
//            // 20
//            {new Point(70, 70), new Point(50, 70)},
//
//            // 21-29
//            {new Point(40, 55), new Point(20, 55)},
//            {new Point(40, 70), new Point(20, 70)},
//            {new Point(40, 55), new Point(20, 55)},
//            {new Point(40, 55), new Point(20, 55)},
//            {new Point(40, 70), new Point(20, 70)},
//            {new Point(40, 55), new Point(20, 55)},
//            {new Point(40, 55), new Point(20, 55)},
//            {new Point(40, 70), new Point(20, 70)},
//            {new Point(40, 55), new Point(20, 55)},
//
//            // 30
//            {new Point(0, 70), new Point(0, 50)},
//
//            // 31-39
//            {new Point(15, 40), new Point(15, 20)},
//            {new Point(0, 40), new Point(0, 20)},
//            {new Point(0, 40), new Point(0, 20)},
//            {new Point(15, 40), new Point(15, 20)},
//            {new Point(0, 40), new Point(0, 20)},
//            {new Point(0, 40), new Point(0, 20)},
//            {new Point(15, 40), new Point(15, 20)},
//            {new Point(0, 40), new Point(0, 20)},
//            {new Point(15, 40), new Point(15, 20)}
//    };

}
