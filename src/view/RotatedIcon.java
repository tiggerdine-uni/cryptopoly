package view;

import javax.swing.*;
import java.awt.*;

/**
 * The RotatedIcon allows you to change the orientation of an Icon by
 * rotating the Icon before it is painted. This class supports the following
 * orientations:
 *
 * R0 (default) - the icon is not rotated.
 * R90 - rotated 90 degrees
 * R180 - rotated 180 degrees
 * R270 - rotated 270 degrees
 *
 * Based on https://tips4java.wordpress.com/2009/04/06/rotated-icon/.
 */
public class RotatedIcon implements Icon {

    public enum Rotate {
        R0,
        R90,
        R180,
        R270
    }

    private Icon icon;

    private Rotate rotate;

    private double degrees;

    /**
     * Convenience constructor to create a RotatedIcon that is rotated R0.
     *
     * @param icon the Icon to rotate
     */
    RotatedIcon(Icon icon) {
        this(icon, Rotate.R0);
    }

    /**
     * Create a RotatedIcon
     *
     * @param icon   the Icon to rotate
     * @param rotate the direction of rotation
     */
    RotatedIcon(Icon icon, Rotate rotate) {
        this.icon = icon;
        this.rotate = rotate;
    }

    /**
     * Set the degrees of rotation. Only used for Rotate.R0.
     * This method only sets the degress of rotation, it will not cause
     * the Icon to be repainted. You must invoke repaint() on any
     * component using this icon for it to be repainted.
     *
     * @param degrees the degrees of rotation
     */
    void setDegrees(double degrees) {
        this.degrees = degrees;
    }

//
//  Implement the Icon Interface
//

    /**
     * Gets the width of this icon.
     *
     * @return the width of the icon in pixels.
     */
    @Override
    public int getIconWidth() {
        if (rotate == Rotate.R0) {
            double radians = Math.toRadians(degrees);
            double sin = Math.abs(Math.sin(radians));
            double cos = Math.abs(Math.cos(radians));
            int width = (int) Math.floor(icon.getIconWidth() * cos + icon.getIconHeight() * sin);
            return width;
        } else if (rotate == Rotate.R180)
            return icon.getIconWidth();
        else
            return icon.getIconHeight();
    }

    /**
     * Gets the height of this icon.
     *
     * @return the height of the icon in pixels.
     */
    @Override
    public int getIconHeight() {
        if (rotate == Rotate.R0) {
            double radians = Math.toRadians(degrees);
            double sin = Math.abs(Math.sin(radians));
            double cos = Math.abs(Math.cos(radians));
            int height = (int) Math.floor(icon.getIconHeight() * cos + icon.getIconWidth() * sin);
            return height;
        } else if (rotate == Rotate.R180)
            return icon.getIconHeight();
        else
            return icon.getIconWidth();
    }

    /**
     * Paint the icons of this compound icon at the specified location
     *
     * @param c The component on which the icon is painted
     * @param g the graphics context
     * @param x the X coordinate of the icon's top-left corner
     * @param y the Y coordinate of the icon's top-left corner
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();

        int cWidth = icon.getIconWidth() / 2;
        int cHeight = icon.getIconHeight() / 2;
        int xAdjustment = (icon.getIconWidth() % 2) == 0 ? 0 : -1;
        int yAdjustment = (icon.getIconHeight() % 2) == 0 ? 0 : -1;

        if (rotate == Rotate.R90) {
            g2.translate(x + cHeight, y + cWidth);
            g2.rotate(Math.toRadians(90));
            icon.paintIcon(c, g2, -cWidth, yAdjustment - cHeight);
        } else if (rotate == Rotate.R270) {
            g2.translate(x + cHeight, y + cWidth);
            g2.rotate(Math.toRadians(-90));
            icon.paintIcon(c, g2, xAdjustment - cWidth, -cHeight);
        } else if (rotate == Rotate.R180) {
            g2.translate(x + cWidth, y + cHeight);
            g2.rotate(Math.toRadians(180));
            icon.paintIcon(c, g2, xAdjustment - cWidth, yAdjustment - cHeight);
        } else if (rotate == Rotate.R0) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setClip(x, y, getIconWidth(), getIconHeight());
            g2.translate((getIconWidth() - icon.getIconWidth()) / 2, (getIconHeight() - icon.getIconHeight()) / 2);
            g2.rotate(Math.toRadians(degrees), x + cWidth, y + cHeight);
            icon.paintIcon(c, g2, x, y);
        }

        g2.dispose();
    }
}