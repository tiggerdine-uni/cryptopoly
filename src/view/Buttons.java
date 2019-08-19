package view;

import controller.Controller;
import crypto.Crypto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Some buttons.
 */
class Buttons extends JPanel {

    JButton buyPropertyButton;
    JButton buyHouseButton;
    JButton sellHouseButton;
    JButton buyHotelButton;
    JButton sellHotelButton;
    JButton mortgagePropertyButton;
    JButton liftMortgageButton;
    JButton endTurnButton;
    JButton quitButton;

    /**
     * Creates some buttons.
     */
    Buttons() {
        buyPropertyButton = new JButton("Buy Property");
        buyPropertyButton.setEnabled(false);
        add(buyPropertyButton);

        buyHouseButton = new JButton("Buy House...");
        buyHouseButton.setEnabled(false);
        add(buyHouseButton);

        sellHouseButton = new JButton("Sell House...");
        sellHouseButton.setEnabled(false);
        add(sellHouseButton);

        buyHotelButton = new JButton("Buy Hotel...");
        buyHotelButton.setEnabled(false);
        add(buyHotelButton);

        sellHotelButton = new JButton("Sell Hotel...");
        sellHotelButton.setEnabled(false);
        add(sellHotelButton);

        mortgagePropertyButton = new JButton("Mortgage Property...");
        mortgagePropertyButton.setEnabled(false);
        add(mortgagePropertyButton);

        liftMortgageButton = new JButton("Lift Mortgage...");
        liftMortgageButton.setEnabled(false);
        add(liftMortgageButton);

        endTurnButton = new JButton("End Turn");
        add(endTurnButton);

        if (Controller.CRYPTO) {
            JButton cryptoButton = new JButton("View Blockchain...");
            cryptoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Crypto.view();
                }
            });
            add(cryptoButton);
        }

        quitButton = new JButton("Quit...");
        add(quitButton);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

}
