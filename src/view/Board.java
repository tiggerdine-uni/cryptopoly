package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JPanel implements ActionListener {

    public static final int FRAME_WIDTH = 720;
    public static final int FRAME_HEIGHT = FRAME_WIDTH;
    public static final int BUTTON_WIDTH = FRAME_WIDTH / 12;
    public static final int BUTTON_HEIGHT = FRAME_HEIGHT / 8;

    public Board() {
        super(new BorderLayout());
        setLayout(null);

        JButton button = new JButton("0");
        button.setBounds(FRAME_WIDTH - BUTTON_HEIGHT, FRAME_HEIGHT - BUTTON_HEIGHT, BUTTON_HEIGHT, BUTTON_HEIGHT);
        add(button);
        button.addActionListener(this);

        for (int i = 1; i <= 9; i++) {
            button = new JButton(String.valueOf(i));
            button.setBounds(FRAME_WIDTH - BUTTON_HEIGHT - i * BUTTON_WIDTH, FRAME_HEIGHT - BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
            add(button);
            button.addActionListener(this);
        }

        button = new JButton("10");
        button.setBounds(0, FRAME_HEIGHT - BUTTON_HEIGHT, BUTTON_HEIGHT, BUTTON_HEIGHT);
        add(button);
        button.addActionListener(this);

        for (int i = 11; i <= 19; i++) {
            button = new JButton(String.valueOf(i));
            button.setBounds(0, FRAME_HEIGHT - BUTTON_HEIGHT - (i - 10) * BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_WIDTH);
            add(button);
            button.addActionListener(this);
        }

        button = new JButton("20");
        button.setBounds(0, 0, BUTTON_HEIGHT, BUTTON_HEIGHT);
        add(button);
        button.addActionListener(this);

        for (int i = 21; i <= 29; i++) {
            button = new JButton(String.valueOf(i));
            button.setBounds(BUTTON_HEIGHT + (i - 21) * BUTTON_WIDTH, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
            add(button);
            button.addActionListener(this);
        }

        button = new JButton("30");
        button.setBounds(FRAME_WIDTH - BUTTON_HEIGHT, 0, BUTTON_HEIGHT, BUTTON_HEIGHT);
        add(button);
        button.addActionListener(this);

        for (int i = 31; i <= 39; i++) {
            button = new JButton(String.valueOf(i));
            button.setBounds(FRAME_WIDTH - BUTTON_HEIGHT, BUTTON_HEIGHT + (i - 31) * BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_WIDTH);
            add(button);
            button.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.print(e.getActionCommand() + " ");
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Monopoly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent board = new Board();
        frame.setContentPane(board);

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
