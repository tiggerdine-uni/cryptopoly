package crypto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class the controller uses to do crypto things.
 */
@SuppressWarnings("SuspiciousNameCombination")
public class Crypto {

    private static final int TRANSACTIONS_PER_BLOCK = 10;

    private static final String BANK = "Bank";

    private static Blockchain blockchain;
    private Keys keys;

    private int i = 0;

    public Crypto() {
        blockchain = new Blockchain();
        keys = new Keys();

        keys.add(BANK);
    }

    public static void dispose() {
        if (f!= null) {
            f.dispose();
        }
    }

    public static void view() {
        viewBlockchain();
    }

    public void add(String name) {
        keys.add(name);
    }

    public void newTransaction(String sender, String recipient, int amount) {
        if (sender == null) {
            if (recipient == null) {
//                throw new SenderRecipientException();
            } else {
                sender = BANK;
            }
        } else if (recipient == null) {
            recipient = BANK;
        }
        if (amount <= 0) {
//            throw new AmountException();
        } else {
            blockchain.newTransaction(keys.getAddress(sender), keys.getAddress(recipient), amount);
            if (blockchain.currentTransactions.size() == TRANSACTIONS_PER_BLOCK) {
                Block lastBlock = blockchain.lastBlock();
                int proof = blockchain.proofOfWork(lastBlock);
                blockchain.newBlock(proof, null);
            }
        }
    }

    private static final int TEXT_WIDTH = 300;
    private static final int TEXT_HEIGHT = 75;

    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = TEXT_HEIGHT;

    private static final int BUTTONS_HEIGHT = 54;

    private static final int PADDING = 10;

    private static final int WIDTH = TEXT_WIDTH + BUTTON_WIDTH + 3 * PADDING;
    private static final int HEIGHT = TEXT_HEIGHT + BUTTONS_HEIGHT + 3 * PADDING;

    private static final int WIDTH_OFFSET = 16;
    private static final int HEIGHT_OFFSET = 39;

    private static final int SCROLL_BAR_UNIT_INCREMENT = 16;

    private static JFrame f;

    private static void viewBlockchain() {
        if (f != null) {
            f.dispose();
        }

        f = new JFrame("Blockchain");
        f.setLayout(null);

        JTextArea text = new JTextArea();
        text.setEditable(false);
        f.add(text);
        text.setBounds(PADDING, PADDING, WIDTH - 2 * PADDING, TEXT_HEIGHT);

        JScrollPane blocks = getBlocks();
        blocks.setBounds(PADDING, TEXT_HEIGHT + 2 * PADDING, WIDTH - 2 * PADDING, BUTTONS_HEIGHT);
        blocks.setMaximumSize(new Dimension(WIDTH, BUTTONS_HEIGHT));
        f.add(blocks);

        f.setSize(WIDTH + WIDTH_OFFSET, HEIGHT + HEIGHT_OFFSET);
        f.setLocationRelativeTo(null);

        f.setVisible(true);
    }

    private static JScrollPane getBlocks() {
        JPanel panel = new JPanel();

        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        for (Block block : blockchain.chain) {
            JButton button = new JButton("Block " + block.index);
            button.setName(String.valueOf(block.index - 1));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewBlock(Integer.parseInt(((JButton) e.getSource()).getName()));
                }
            });
            panel.add(button);
        }

        scrollPane.getHorizontalScrollBar().setUnitIncrement(SCROLL_BAR_UNIT_INCREMENT);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }

    private static void viewBlock(int block) {
        f.dispose();

        Block b = blockchain.chain.get(block);

        f = new JFrame("Block " + b.index);
        f.setLayout(null);


        JTextArea text = new JTextArea("Timestamp: " + b.timestamp +
                "\nProof: " + b.proof +
                "\nPrevious Hash: " + b.previousHash);
        text.setEditable(false);
        text.setLineWrap(true);
        f.add(text);
        text.setBounds(PADDING, PADDING, TEXT_WIDTH, TEXT_HEIGHT);

        JButton upButton = new JButton("Up");
        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBlockchain();
            }
        });
        f.add(upButton);
        upButton.setBounds(TEXT_WIDTH + 2 * PADDING, PADDING, BUTTON_WIDTH, BUTTON_HEIGHT);

        JScrollPane transactions = getTransactions(block);
        transactions.setBounds(PADDING, TEXT_HEIGHT + 2 * PADDING, WIDTH - 2 * PADDING, BUTTONS_HEIGHT);
        transactions.setMaximumSize(new Dimension(WIDTH - 2 * PADDING, BUTTONS_HEIGHT));
        f.add(transactions);

        f.setSize(WIDTH + WIDTH_OFFSET, HEIGHT + HEIGHT_OFFSET);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private static JScrollPane getTransactions(int block) {
        JPanel panel = new JPanel();

        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        Block b = blockchain.chain.get(block);

        for (Transaction transaction: b.transactions) {
            JButton button = new JButton("Transaction " + (b.transactions.indexOf(transaction) + 1));
            button.setName(String.valueOf(b.transactions.indexOf(transaction)));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewTransaction(block, Integer.parseInt(((JButton) e.getSource()).getName()));
                }
            });
            panel.add(button);
        }

        scrollPane.getHorizontalScrollBar().setUnitIncrement(SCROLL_BAR_UNIT_INCREMENT);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }

    private static void viewTransaction(int block, int transaction) {
        f.dispose();

        f = new JFrame("Transaction " + (transaction + 1));
        f.setLayout(null);

        Transaction t = blockchain.chain.get(block).transactions.get(transaction);

        JTextArea text = new JTextArea("Sender: " + t.sender +
                "\nRecipient: " + t.recipient +
                "\nAmount: " + t.amount);
        text.setEditable(false);
        text.setLineWrap(true);
        f.add(text);
        text.setBounds(PADDING, PADDING, TEXT_WIDTH, TEXT_HEIGHT);

        JButton upButton = new JButton("Up");
        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBlock(block);
            }
        });
        f.add(upButton);
        upButton.setBounds(TEXT_WIDTH + 2 * PADDING, PADDING, BUTTON_WIDTH, BUTTON_HEIGHT);

        f.setSize(WIDTH + WIDTH_OFFSET, HEIGHT + HEIGHT_OFFSET);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}
