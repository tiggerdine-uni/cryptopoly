package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.BorderFactory.createEmptyBorder;

/**
 * A view that implements View.
 */
public class MyView implements View {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int SMALL_MARGIN = 20;
    private static final int BIG_MARGIN = 90;

    private Controller c;

    private JFrame f;
    private Board board;
    private JTextArea textArea;
    private Buttons buttons;
    private JTextArea textArea2;

    private int space = -1;
    private String player = "";

    public MyView() {
        f = new JFrame("Monopoly");

        JComponent p = new JPanel();
        p.setLayout(null);

        p.add(board());
        p.add(textArea());
        p.add(buttons());
        p.add(detailTextArea());

        f.setContentPane(p);
        f.pack();
        f.setSize(WIDTH, HEIGHT);
        f.dispose();
        f.setUndecorated(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private JScrollPane textArea() {
        textArea = new JTextArea("Welcome to Monopoly.") {
            @Override
            public void append(String str) {
                super.append(str);
                textArea.setCaretPosition(textArea.getDocument().getLength());
                try {
                    textArea.setCaretPosition(textArea.getLineStartOffset(textArea.getLineCount() - 1));
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        };
        JScrollPane sp = new JScrollPane(textArea);
        sp.setHorizontalScrollBar(null);
        sp.setBounds(Board.WIDTH + SMALL_MARGIN,
                SMALL_MARGIN,
                WIDTH - Board.WIDTH - 2 * SMALL_MARGIN,
                HEIGHT / 2 - 2 * SMALL_MARGIN);
        sp.setBorder(createEmptyBorder());
        textArea.setEditable(false);
        return sp;
    }

    private Board board() {
        board = new Board(this);
        board.setBounds(0,
                0,
                Board.WIDTH,
                Board.HEIGHT);
        return board;
    }

    private JTextArea detailTextArea() {
        textArea2 = new JTextArea();
        textArea2.setBounds(90 + BIG_MARGIN,
                90 + BIG_MARGIN,
                540 - 2 * BIG_MARGIN,
                540 - 2 * BIG_MARGIN);
        textArea2.setEditable(false);
        return textArea2;
    }

    private Buttons buttons() {
        buttons = new Buttons();
        buttons.setBounds(Board.WIDTH + SMALL_MARGIN,
                HEIGHT / 2 + SMALL_MARGIN,
                WIDTH - Board.WIDTH - 2 * SMALL_MARGIN,
                HEIGHT / 2 - 2 * SMALL_MARGIN);

        buttons.endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.startTurn();
            }
        });

        buttons.buyHouseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.buyHouseClicked();
            }
        });

        buttons.sellHouseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.sellHouseClicked();
            }
        });

        buttons.buyHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.buyHotelClicked();
            }
        });

        buttons.sellHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.sellHotelClicked();
            }
        });

        buttons.buyPropertyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.buyPropertyClicked();
            }
        });

        buttons.mortgagePropertyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.mortgagePropertyClicked();
            }
        });

        buttons.liftMortgageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.liftMortgageClicked();
            }
        });

        buttons.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(
                        f,
                        "Are you sure you want to quit?",
                        "Quit?",
                        JOptionPane.YES_NO_OPTION);
                if (n == 0) {
                    System.exit(0);
                }
            }
        });

        return buttons;
    }

    @Override
    public void setController(Controller c) {
        this.c = c;
        board.c = c;
    }

    /*
     *                                                  ┏━━━━━━┓
     *                                                  ┃ TEXT ┃
     *                                                  ┗━━━━━━┛
     */

    @Override
    public void say(String s) {
        textArea.append(s);
    }

    @Override
    public void reset() {
        buttons.endTurnButton.setText("End Turn");
        say("\n───────────────────────────────────────────────────────────────────────────────────────");
    }

    void showDetails(String details) {
        textArea2.setText(details);
    }

    @Override
    public void refresh() {
        if(!player.equals("")) {
            showDetails(c.inspectPlayer(player));
        } else if (space != -1) {
            showDetails(c.inspectSpace(space));
        }
        textArea2.repaint();
    }

    @Override
    public void showDetailsAboutSpace(int index) {
        space = index;
        showDetails(c.inspectSpace(index));
    }

    void setPlayer(String s) {
        this.player = s;
    }

    void setSpace(int i) {
        this.space = i;
    }

    /*
     *                                                 ┏━━━━━━━┓
     *                                                 ┃ BOARD ┃
     *                                                 ┗━━━━━━━┛
     */

    @Override
    public void move(int player, int space) {
        Token token = board.tokens.get(player);
        JButton oldSpace = board.spaces.get(token.space);
        JButton newSpace = board.spaces.get(space);

        int players;
        if(board.tokens.get(player == 0 ? 1 : 0).space == space) {
            players = 1;
        } else {
            players = 0;
        }

        token.moveTo(space, players);
        newSpace.add(token);
        newSpace.repaint();
        oldSpace.repaint();
    }

    @Override
    public void addHouse(int space, int house) {
        board.addHouse(space, house);
    }

    @Override
    public void addHotel(int space) {
        board.addHotel(space);
    }

    @Override
    public void removeHouse(int space, int house) {
        board.removeHouse(space, house);
    }

    @Override
    public void removeHotel(int space) {
        board.removeHotel(space);
    }

    /*
     *                                                ┏━━━━━━━━━┓
     *                                                ┃ BUTTONS ┃
     *                                                ┗━━━━━━━━━┛
     */

    @Override
    public void enableBuyPropertyButton(boolean b) {
        buttons.buyPropertyButton.setEnabled(b);
    }

    @Override
    public void enableEndTurnButton(boolean b) {
        buttons.endTurnButton.setEnabled(b);
    }

    @Override
    public void enableSellHouseButton(boolean b) {
        buttons.sellHouseButton.setEnabled(b);
    }

    @Override
    public void enableSellHotelButton(boolean b) {
        buttons.sellHotelButton.setEnabled(b);
    }

    @Override
    public void enableBuyHouseButton(boolean b) {
        buttons.buyHouseButton.setEnabled(b);
    }

    @Override
    public void enableBuyHotelButton(boolean b) {
        buttons.buyHotelButton.setEnabled(b);
    }

    @Override
    public void enableMortgagePropertyButton(boolean b) {
        buttons.mortgagePropertyButton.setEnabled(b);
    }

    @Override
    public void enableUnmortgagedPropertyButton(boolean b) {
        buttons.liftMortgageButton.setEnabled(b);
    }

    @Override
    public void extraTurn() {
        buttons.endTurnButton.setText("Extra Turn");
    }

    @Override
    public void disableAllButtons() {
        enableBuyPropertyButton(false);
        enableEndTurnButton(false);
        enableSellHouseButton(false);
        enableSellHotelButton(false);
        enableBuyHouseButton(false);
        enableBuyHotelButton(false);
        enableMortgagePropertyButton(false);
        enableUnmortgagedPropertyButton(false);
    }

    /*
     *                                                ┏━━━━━━━━━┓
     *                                                ┃ DIALOGS ┃
     *                                                ┗━━━━━━━━━┛
     */

    @Override
    public void showJailDialog(String name) {
        final String throwDoubles = "Throw Doubles";
        final String pay50 = "Pay " + Controller.$ + "50";
        final String useCard = "Use Card";

        ArrayList<String> optionsList = new ArrayList<>();

        optionsList.add(throwDoubles);

        if(c.playerCanAfford(50)) {
            optionsList.add(pay50);
        }

        if(c.playerHasCard()) {
            optionsList.add(useCard);
        }

        String[] options = optionsList.toArray(new String[]{});

        int n = -1;
        while(n == -1) {
            n = JOptionPane.showOptionDialog(f,
                    name + ", what would you like to do?",
                    "Get Out of Jail",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    null);
        }

        switch(options[n]) {
            case throwDoubles:
                c.throwDoubles();
                break;
            case pay50:
                c.payFine(true);
                break;
            case useCard:
                c.useCard();
                break;
        }
    }

    @Override
    public void buyBuildingDialog(List<String> names) {
        String name = getInput(names, "Which street would you like to buy a house/hotel on?", "Buy a House/Hotel");
        if (name != null) {
            c.improve(name);
        }
    }

    @Override
    public void sellBuildingDialog(List<String> names) {
        String name = getInput(names, "Which street would you like to sell a house/hotel on?", "Sell a House/Hotel");
        if (name != null) {
            c.degrade(name);
        }
    }

    @Override
    public String mortgagePropertyDialog(List<String> names) {
        return getInput(names, "Which property would you like to mortgage?", "Mortgage a Property");
    }

    private String getInput(List<String> selectionValues, String message, String title) {
        return (String) JOptionPane.showInputDialog(
                f,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                selectionValues.toArray(),
                null);
    }

}
