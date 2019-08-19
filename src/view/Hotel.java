package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A hotel.
 */
class Hotel extends JLabel {

    static final int WIDTH = 2 * House.WIDTH;
    static final int HEIGHT = House.HEIGHT;

    /**
     * Creates a hotel.
     *
     * @param space
     */
    Hotel(int space) {
        try {
            File input = new File("res\\hotel.bmp");
            BufferedImage image = ImageIO.read(input);
            RotatedIcon icon = new RotatedIcon(new ImageIcon(image), RotatedIcon.Rotate.values()[space / 10]);
            setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
