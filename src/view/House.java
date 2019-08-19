package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A house.
 */
class House extends JLabel {

    static final int WIDTH = Space.WIDTH / 4;
    @SuppressWarnings("SuspiciousNameCombination")
    static final int HEIGHT = WIDTH;

    /**
     * Creates a house.
     *
     * @param space
     */
    House(int space) {
        try {
            File input = new File("res\\house.bmp");
            BufferedImage image = ImageIO.read(input);
            RotatedIcon icon = new RotatedIcon(new ImageIcon(image), RotatedIcon.Rotate.values()[space / 10]);
            setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Point getLocation(int space, int house) {
        if (house < 0 || house > 3) {
            return null;
        }
        int x;
        int y;
        if (space >= 1 && space <= 9) {
            x = Board.WIDTH - Space.HEIGHT - space * Space.WIDTH + house * House.WIDTH;
            y = Board.HEIGHT - Space.HEIGHT;
        } else if (space >= 11 && space <=19) {
            x = Space.HEIGHT - House.WIDTH;
            y = Board.HEIGHT - Space.HEIGHT - (space - 10) * Space.WIDTH + house * House.HEIGHT;
        } else if (space >= 21 && space <=29) {
            x = Space.HEIGHT + (space - 21) * Space.WIDTH + (3 - house) * House.WIDTH;
            y = Space.HEIGHT - House.HEIGHT;
        } else if (space >= 31 && space <=39) {
            x = Board.WIDTH - Space.HEIGHT;
            y = Space.HEIGHT + (space - 31) * Space.WIDTH + (3 - house) * House.WIDTH;
        } else {
            return null;
        }
        return new Point(x, y);
    }

//    static Point[][] points = new Point[][]{
//            // 0
//            null,
//
//            // 1-9
//            {new Point(570, 630), new Point(585, 630), new Point(600, 630), new Point(615, 630)},
//            null,
//            {new Point(450, 630), new Point(465, 630), new Point(480, 630), new Point(495, 630)},
//            null,
//            null,
//            {new Point(270, 630), new Point(285, 630), new Point(300, 630), new Point(315, 630)},
//            null,
//            {new Point(150, 630), new Point(165, 630), new Point(180, 630), new Point(195, 630)},
//            {new Point(90, 630), new Point(105, 630), new Point(120, 630), new Point(135, 630)},
//
//            // 10
//            null,
//
//            // 11-19
//            {new Point(75, 570), new Point(75, 585), new Point(75, 600), new Point(75, 615)},
//            null,
//            {new Point(75, 450), new Point(75, 465), new Point(75, 480), new Point(75, 495)},
//            {new Point(75, 390), new Point(75, 405), new Point(75, 420), new Point(75, 435)},
//            null,
//            {new Point(75, 270), new Point(75, 285), new Point(75, 300), new Point(75, 315)},
//            null,
//            {new Point(75, 150), new Point(75, 165), new Point(75, 180), new Point(75, 195)},
//            {new Point(75, 90), new Point(75, 105), new Point(75, 120), new Point(75, 135)},
//
//            // 20
//            null,
//
//            // 21-29
//            {new Point(135, 75), new Point(120, 75), new Point(105, 75), new Point(90, 75)},
//            null,
//            {new Point(255, 75), new Point(240, 75), new Point(225, 75), new Point(210, 75)},
//            {new Point(315, 75), new Point(300, 75), new Point(285, 75), new Point(270, 75)},
//            null,
//            {new Point(435, 75), new Point(420, 75), new Point(405, 75), new Point(390, 75)},
//            {new Point(495, 75), new Point(480, 75), new Point(465, 75), new Point(450, 75)},
//            null,
//            {new Point(615, 75), new Point(600, 75), new Point(585, 75), new Point(570, 75)},
//
//            // 30
//            null,
//
//            // 31-39
//            {new Point(630, 135), new Point(630, 120), new Point(630, 105), new Point(630, 90)},
//            {new Point(630, 195), new Point(630, 180), new Point(630, 165), new Point(630, 150)},
//            null,
//            {new Point(630, 315), new Point(630, 300), new Point(630, 285), new Point(630, 270)},
//            null,
//            null,
//            {new Point(630, 495), new Point(630, 480), new Point(630, 465), new Point(630, 450)},
//            null,
//            {new Point(630, 615), new Point(630, 600), new Point(630, 585), new Point(630, 570)}
//    };

}
