package view;

import javax.swing.*;

public class Board {

    public static final int FRAME_WIDTH = 720;
    public static final int FRAME_HEIGHT = FRAME_WIDTH;
    public static final int BUTTON_WIDTH = FRAME_WIDTH / 12;
    public static final int BUTTON_HEIGHT = FRAME_HEIGHT / 8;

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Monopoly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton button = new JButton();
        button.setBounds(FRAME_WIDTH - BUTTON_HEIGHT, FRAME_HEIGHT - BUTTON_HEIGHT, BUTTON_HEIGHT, BUTTON_HEIGHT);
        frame.getContentPane().add(button);

        for (int i = 1; i <= 9; i++) {
            button = new JButton();
            button.setBounds(FRAME_WIDTH - BUTTON_HEIGHT - i * BUTTON_WIDTH, FRAME_HEIGHT - BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
            frame.getContentPane().add(button);
        }

        button = new JButton();
        button.setBounds(0, FRAME_HEIGHT - BUTTON_HEIGHT, BUTTON_HEIGHT, BUTTON_HEIGHT);
        frame.getContentPane().add(button);

        for (int i = 11; i <= 19; i++) {
            button = new JButton();
            button.setBounds(0, FRAME_HEIGHT - BUTTON_HEIGHT - (i - 10) * BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_WIDTH);
            frame.getContentPane().add(button);
        }

        button = new JButton();
        button.setBounds(0, 0, BUTTON_HEIGHT, BUTTON_HEIGHT);
        frame.getContentPane().add(button);

        for (int i = 21; i <= 29; i++) {
            button = new JButton();
            button.setBounds(BUTTON_HEIGHT + (i - 21) * BUTTON_WIDTH, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
            frame.getContentPane().add(button);
        }

        button = new JButton();
        button.setBounds(FRAME_WIDTH - BUTTON_HEIGHT, 0, BUTTON_HEIGHT, BUTTON_HEIGHT);
        frame.getContentPane().add(button);

        for (int i = 31; i <= 39; i++) {
            button = new JButton();
            button.setBounds(FRAME_WIDTH - BUTTON_HEIGHT, BUTTON_HEIGHT + (i - 31) * BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_WIDTH);
            frame.getContentPane().add(button);
        }

        frame.pack();
        frame.setSize(FRAME_WIDTH + 16, FRAME_HEIGHT + 39);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
