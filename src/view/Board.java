package view;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A board.
 */
@SuppressWarnings("SuspiciousNameCombination")
class Board extends JPanel {

    static final int WIDTH = 720;
    static final int HEIGHT = WIDTH;

    private MyView v;
    Controller c;

    List<JButton> spaces;
    List<Token> tokens;
    private House[][] houses;
    private Hotel[] hotels;

    /**
     * Creates a board.
     *
     * @param v
     */
    Board(MyView v) {
        this.v = v;

        setLayout(null);

        spaces();
        tokens();
        houses = new House[40][4];
        hotels = new Hotel[40];

        try {
            File file = new File("res\\board.bmp");
            BufferedImage image = ImageIO.read(file);
            ImageIcon icon = new ImageIcon(image);
            JLabel label = new JLabel(icon);
            add(label);
            label.setSize(WIDTH, HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void spaces() {
        spaces = new ArrayList<>();

        // 0
        JButton button = new JButton();
        button.setName("0");
        button.setBounds(Board.WIDTH - Space.HEIGHT, Board.HEIGHT - Space.HEIGHT, Space.HEIGHT, Space.HEIGHT);
        spaces.add(button);

        // 1-9
        for (int i = 1; i <= 9; i++) {
            button = new JButton();
            button.setName(Integer.toString(i));
            button.setBounds(Board.WIDTH - Space.HEIGHT - i * Space.WIDTH, Board.HEIGHT - Space.HEIGHT, Space.WIDTH, Space.HEIGHT);
            spaces.add(button);
        }

        // 10
        button = new JButton();
        button.setName("10");
        button.setBounds(0, Board.HEIGHT - Space.HEIGHT, Space.HEIGHT, Space.HEIGHT);
        spaces.add(button);

        // 11-19
        for (int i = 11; i <= 19; i++) {
            button = new JButton();
            button.setName(Integer.toString(i));
            button.setBounds(0, Board.HEIGHT - Space.HEIGHT - (i - 10) * Space.WIDTH, Space.HEIGHT, Space.WIDTH);
            spaces.add(button);
        }

        // 20
        button = new JButton();
        button.setName("20");
        button.setBounds(0, 0, Space.HEIGHT, Space.HEIGHT);
        spaces.add(button);

        // 21-29
        for (int i = 21; i <= 29; i++) {
            button = new JButton();
            button.setName(Integer.toString(i));
            button.setBounds(Space.HEIGHT + (i - 21) * Space.WIDTH, 0, Space.WIDTH, Space.HEIGHT);
            spaces.add(button);
        }

        // 30
        button = new JButton();
        button.setName("30");
        button.setBounds(Board.WIDTH - Space.HEIGHT, 0, Space.HEIGHT, Space.HEIGHT);
        spaces.add(button);

        // 31-39
        for (int i = 31; i <= 39; i++) {
            button = new JButton();
            button.setName(Integer.toString(i));
            button.setBounds(Board.WIDTH - Space.HEIGHT, Space.HEIGHT + (i - 31) * Space.WIDTH, Space.HEIGHT, Space.WIDTH);
            spaces.add(button);
        }

        for (JButton b : spaces) {
            add(b);
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int space = Integer.parseInt(((JComponent) e.getSource()).getName());
                    v.showDetails(c.inspectSpace(space));
                    v.setPlayer("");
                    v.setSpace(space);
                }
            });
            b.setOpaque(false);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.setLayout(null);
        }
    }

    private void tokens() {
        tokens = new ArrayList<>();

        Token token = new Token(0, "res\\token1.bmp", this);
        token.setSize(Token.WIDTH, Token.HEIGHT);
        tokens.add(token);

        token = new Token(1, "res\\token2.bmp", this);
        token.setSize(Token.WIDTH, Token.HEIGHT);
        tokens.add(token);

        for (Token t : tokens) {
            t.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String player = c.getNameOfPlayer(t.player);
                    v.showDetails(c.inspectPlayer(player));
                    v.setPlayer(player);
                    v.setSpace(-1);
                }
            });
        }
    }

    void addHouse(int space, int house) {
        House h = new House(space);
        h.setLocation(House.getLocation(space, house));
        h.setSize(House.WIDTH, House.HEIGHT);
        add(h, 1);
        houses[space][house] = h;

        spaces.get(space).repaint();
    }

    void addHotel(int space) {
        for (House house : houses[space]) {
            remove(house);
        }

        repaint();
        Hotel hotel = new Hotel(space);

        if (space < 20) {
            hotel.setLocation(House.getLocation(space, 1));
        } else {
            hotel.setLocation(House.getLocation(space, 2));
        }

        if (space < 10 || (space >= 20 && space < 30)) {
            hotel.setSize(Hotel.WIDTH, Hotel.HEIGHT);
        } else {
            hotel.setSize(Hotel.HEIGHT, Hotel.WIDTH);
        }

        add(hotel, 1);
        hotels[space] = hotel;

        spaces.get(space).repaint();
    }

    void removeHouse(int space, int house) {
        remove(houses[space][house]);
        houses[space][house] = null;
        spaces.get(space).repaint();
    }

    void removeHotel(int space) {
        remove(hotels[space]);
        for (int i = 0; i < 4; i++) {
            addHouse(space, i);
        }
        hotels[space] = null;
        spaces.get(space).repaint();
    }
}
