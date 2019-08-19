package controller;

import crypto.Crypto;
import model.Model;
import view.View;

import javax.swing.*;
import java.util.*;

/**
 * The controller.
 */
public class Controller {

    /*
     * Enables/disables crypto mode.
     */
    public static final boolean CRYPTO = true;

    /*
     * Currency symbol.
     */
    public static final char $ = CRYPTO ? 'ℳ' : '£';

    /*
     * Number of players.
     */
    private static final int PLAYERS = 2; // 2

    private Model m;
    private View v;

    /*
     * Random number generator.
     */
    private Random r;

    private Crypto c;
    private Questions q;

    /*
     * "If you throw doubles three times in succession, move your token immediately to the space marked "In Jail"."
     */
    private int moveDoubles;

    /*
     * "If you do not throw doubles by your third turn, you must pay the $50 fine."
     */
    private int[] jailDoubles;

    public Controller(Model m, View v) {
        this.m = m;
        this.v = v;
        v.setController(this);

        if (CRYPTO) {
            c = new Crypto();
            q = new Questions();
        }

        r = new Random();

        moveDoubles = 0;
        jailDoubles = new int[m.getNumberOfPlayers()];

        setUp();
    }

    public void setUp() {
        List<String> names = new ArrayList<>();

        for (int i = 0; i < PLAYERS; i++) {
            String name;
            do {
                name = JOptionPane.showInputDialog("What is player " + (i + 1) + "'s name?");
                if (name != null) {
                    name = name.strip();
                }
            } while (name == null || name.length() == 0 || names.contains(name));
            names.add(name);
        }

        for (String name : names) {
            m.addPlayer(name);
            if (CRYPTO) {
                c.add(name);
            }
        }

        m.shufflePlayers();

        jailDoubles = new int[m.getNumberOfPlayers()];

        if (CRYPTO) {
            q.add("The block chain is a _ ledger.", new String[]{"shared public", "private"});
            q.add("All _ transactions are included in the block chain", new String[]{"confirmed", "unconfirmed"});
            q.add("A _ key is used to sign transactions.", new String[]{"private", "public"});
            q.add("Transactions are confirmed through a process called _.", new String[]{"mining", "drilling", "prospecting"});
            q.add("Bitcoin is vulnerable to _ attacks.", new String[]{"51%", "SWK", "99%", "JNO"});
            q.add("The block chain uses the _ model.", new String[]{"peer-to-peer", "client-server"});
            q.add("A _ algorithm is used to mine cryptocurrency.", new String[]{"proof of work", "hashing"});
            q.add("Users have _ keys.", new String[]{"public and private", "only public", "only private"});
            q.add("_ is the dominant cryptocurrency.", new String[]{"Bitcoin", "Ethereum", "XRP"});
            q.add("Bitcoin was invented by _.", new String[]{"Satoshi Nakamoto", "Akazawa Yoshitaka", "Seta Sotaro"});
        }
    }

    public void tearDown() {
    }

    /*
     *                                                 ┏━━━━━━━┓
     *                                                 ┃ LOGIC ┃
     *                                                 ┗━━━━━━━┛
     */

    public void startTurn() {
        if (moveDoubles == 0) {
            m.nextPlayer();
        }

        v.reset();
        updateView();

        if (m.playerIsInJail()) {
           v.showJailDialog(m.getPlayerName());
        } else {
            int roll = Dice.roll();
            boolean move = true;
            if (Dice.equ()) {
                moveDoubles++;
                if (moveDoubles == 3) {
                    move = false;
                    goToJail();
                    startTurn();
                }
                v.extraTurn();
            } else {
                moveDoubles = 0;
            }
            if (move) {
                move(roll);
            }
        }
    }

    private boolean bankrupt(int amount) {
        if (playerCanAfford(amount)) {
            return false;
        }
        gameOver();
        return true;
    }

    private void gameOver() {
        // This assumes there are only two players.
        v.disableAllButtons();
        v.say("\n" + m.getPlayerName() + " loses");
        tearDown();
    }

    /*
     *                                               ┏━━━━━━━━━━━┓
     *                                               ┃ MORTGAGES ┃
     *                                               ┗━━━━━━━━━━━┛
     */

    private void unmortgage(String s) {
        v.say("\n" + m.getPlayerName() + " lifts mortgage on " + s);
        int amount = m.unmortgage(s);
        if (CRYPTO) {
            c.newTransaction(null, m.getPlayerName(), amount);
        }
        updateView();
    }

    private void mortgage(String s) {
        v.say("\n" + m.getPlayerName() + " mortgages " + s);
        int amount = m.mortgage(s);
        if (CRYPTO) {
            c.newTransaction(m.getPlayerName(), null, amount);
        }
        updateView();
    }

    /*
     *                                                  ┏━━━━━━┓
     *                                                  ┃ VIEW ┃
     *                                                  ┗━━━━━━┛
     */

    private void updateView() {
        v.enableBuyPropertyButton(false);

        v.enableBuyHouseButton(!m.getNamesOfHousableStreets().isEmpty());

        v.enableBuyHotelButton(!m.getNamesOfHotelableStreets().isEmpty());

        v.enableMortgagePropertyButton(m.playerCanMortgageProperty());

        v.enableUnmortgagedPropertyButton(m.playerCanUnmortgageProperty());

        v.enableSellHouseButton(!m.getNamesOfUnhouseableStreets().isEmpty());

        v.enableSellHotelButton(!m.getNamesOfUnhotelableStreets().isEmpty());

        v.refresh();

        if (CRYPTO) {
            Crypto.dispose();
        }
    }

    private void card(String s) {
        v.say("\n" + s);
    }

    /*
     *                                                 ┏━━━━━━━┓
     *                                                 ┃ SPACE ┃
     *                                                 ┗━━━━━━━┛
     */

    private void land() {

        v.showDetailsAboutSpace(m.getSpaceIndex());
        switch (m.getSpaceType()) {
            case STREET:
            case RAILROAD:
            case UTILITY:
                property();
                break;

            case GO:
            case JAIL:
            case FREE_PARKING:
                break;

            case CHANCE:
                if (CRYPTO) {
                    question();
                } else {
                    chance();
                }
                break;

            case COMMUNITY_CHEST:
                if (CRYPTO) {
                    question();
                } else {
                    communityChest();
                }
                break;

            case INCOME_TAX:
                pay(200);
                break;

            case GO_TO_JAIL:
                goToJail();
                break;

            case SUPER_TAX:
                pay(100);
        }
    }

    private void property() {
        if (m.propertyIsUnowned()) {

            if (m.playerCanAffordProperty()) {
                v.enableBuyPropertyButton(true);
            }

        } else if (!m.playerOwnsProperty() && !m.propertyIsMortgaged()) {

            if (!bankrupt(m.getPropertyRent())) {
                v.say("\n" + m.getPlayerName() + " pays " + m.getPropertyOwnerName() + " " + $ + m.getPropertyRent());
                m.payRent();

                if (CRYPTO) {
                    c.newTransaction(m.getPlayerName(), m.getPropertyOwnerName(), m.getPropertyRent());
                }
            }
        }
    }

    private void chance() {
        v.say("\n" + m.getPlayerName() + " draws a chance card");
        int amount;
        switch (r.nextInt(16)) {
//        switch (1) {
            case 0:
                // Advance to "Go"
                card("Advance to \"Go\"");
                moveTo(0);
                break;

            case 1:
                // Go to jail. Move directly to jail. Do not pass "Go". Do not collect £200
                card("Go to jail. Move directly to jail. Do not pass \"Go\". Do not collect " + $ + "200");
                goToJail();
                break;

            case 2:
                // Advance to Pall Mall. If you pass "Go" collection £200
                card("Advance to Pall Mall. If you pass \"Go\" collection " + $ + "200");
                moveTo(11);
                break;

            case 3:
                // Take a trip to Marylebone Station and if you pass "Go" collect £200
                card("Take a trip to Marylebone Station and if you pass \"Go\" collect " + $ + "200");
                moveTo(15);
                break;

            case 4:
                // Advance to Trafalgar Square. If you pass "Go" collect £200
                card("Advance to Trafalgar Square. If you pass \"Go\" collect " + $ + "200");
                moveTo(24);
                break;

            case 5:
                // Advance to Mayfair
                card("Advance to Mayfair");
                moveTo(39);
                break;

            case 6:
                // Go back three spaces
//                card("Go back three spaces");
//                m.movePlayer(-3);
//                v.say("\n" + m.playerName() + " moves to " + m.getSpaceName());
//                land();
//                break;

            case 7:
                // Make general repairs on all of your houses. For each house pay £25. For each hotel pay £100
                amount = 25;
                int amount2 = 100;
                card("Make general repairs on all of your houses. For each house pay " + $ + amount + ". For each hotel pay " + $ + amount2);
                payForHousesAndHotels(amount, amount2);
                break;

            case 8:
                // You are assessed for street repairs–£40 per house–£115 per hotel
                amount = 40;
                amount2 = 115;
                card("You are assessed for street repairs–" + $ + amount + " per house–" + $ + amount2 + " per hotel");
                payForHousesAndHotels(amount, amount2);
                break;

            case 9:
                // Pay school fees of £150
                amount = 150;
                card("Pay school fees of " + $ + amount);
                pay(amount);
                break;

            case 10:
                // "Drunk in charge" fine £20
                amount = 20;
                card("\"Drunk in charge\" fine " + $ + amount);
                pay(amount);
                break;

            case 11:
                // Speeding fine £15
                amount = 15;
                card("Speeding fine " + $ + amount);
                pay(amount);
                break;

            case 12:
                // Your building loan matures. Receive £150
                amount = 150;
                card("Your building loan matures. Receive " + $ + amount);
                collect(amount);
                break;

            case 13:
                // You have won a crossword competition. Collect £100
                amount = 100;
                card("You have won a crossword competition. Collect " + $ + amount);
                collect(amount);
                break;

            case 14:
                // Bank pays you dividend of £50
                amount = 50;
                card("Bank pays you dividend of " + $ + 50);
                collect(amount);
                break;

            case 15:
                // Get out of jail free. This card may be kept until needed or sold
                card("Get out of jail free. This card may be kept until needed or sold");
                getOutOfJailFree();
                break;
        }
    }

    private void communityChest() {
        v.say("\n" + m.getPlayerName() + " draws a community chest card");
        int amount;
        switch(r.nextInt(17)) {
//        switch(5) {
            case 0:
                // Advance to Go (Collect £200)
                card("Advance to Go (Collect " + $ + "200)");
                moveTo(0);
                break;

            case 1:
                // Bank error in your favor—Collect £200
                amount = 200;
                card("Bank error in your favor—Collect " + $ + amount);
                collect(amount);
                break;

            case 2:
                // Doctor's fee—Pay £50
                amount = 50;
                card("Doctor's fee—Pay " + $ + amount);
                pay(amount);
                break;

            case 3:
                // From sale of stock you get £50
                amount = 50;
                card("From sale of stock you get " + $ + amount);
                collect(amount);
                break;

            case 4:
                // Get Out of Jail Free
                card("Get Out of Jail Free");
                getOutOfJailFree();
                break;

            case 5:
                // Go to Jail–Go directly to jail–Do not pass Go–Do not collect £200
                card("Go to Jail–Go directly to jail–Do not pass Go–Do not collect " + $ + "200");
                goToJail();
                break;

            case 6:
                // Grand Opera Night—Collect £50 from every player for opening night seats

            case 7:
                // Holiday Fund matures—Receive £100
                amount = 100;
                card("Holiday Fund matures—Receive " + $ + amount);
                collect(amount);
                break;

            case 8:
                // Income tax refund–Collect £20
                amount = 20;
                card("Income tax refund–Collect " + $ + amount);
                collect(amount);
                break;

            case 9:
                // It is your birthday—Collect £10
                amount = 10;
                card("It is your birthday—Collect " + $ + amount);
                collect(amount);
                break;

            case 10:
                // Life insurance matures–Collect £100
                amount = 100;
                card("Life insurance matures–Collect " + $ + amount);
                collect(amount);
                break;

            case 11:
                // Pay hospital fees of £100
                amount = 100;
                card("Pay hospital fees of " + $ + amount);
                pay(amount);
                break;

            case 12:
                // Pay school fees of £150
                amount = 150;
                card("Pay school fees of " + $ + amount);
                pay(amount);
                break;

            case 13:
                // Receive £25 consultancy fee
                amount = 25;
                card("Receive " + $ + amount + " consultancy fee");
                collect(amount);
                break;

            case 14:
                // You are assessed for street repairs–£40 per house–£115 per hotel
                amount = 40;
                int amount2 = 115;
                card("You are assessed for street repairs–" + $ + amount + " per house–" + $ + amount2 + " per hotel");
                payForHousesAndHotels(amount, amount2);
                break;

            case 15:
                // You have won second prize in a beauty contest–Collect £10
                amount = 10;
                card("You have won second prize in a beauty contest–Collect " + $ + amount);
                collect(amount);
                break;

            case 16:
                // You inherit £100
                amount = 100;
                card("You inherit " + $ + amount);
                collect(amount);
                break;
        }
    }

    public String inspectSpace(int index) {
        return m.inspectSpace(index);
    }

    private void question() {
        if (q.ask()) {
            correct();
        } else {
            incorrect();
        }
    }

    private void correct() {
        v.say("\nCorrect!");
        switch (r.nextInt(1)) {
            case 0:
                collect(100);
            // ...
        }
    }

    private void incorrect() {
        v.say("\nIncorrect!");
        switch (r.nextInt(1)) {
            case 0:
                pay(100);
            // ...
        }
    }

    /*
     *                                                 ┏━━━━━━━━┓
     *                                                 ┃ PLAYER ┃
     *                                                 ┗━━━━━━━━┛
     */

    public String inspectPlayer(String name) {
        return m.inspectPlayer(name);
    }

    public String getNameOfPlayer(int i) {
        return m.getNameOfPlayer(i);
    }

    private void move(int roll) {
        int newIndex = m.getNewIndex(roll);
        moveTo(newIndex);
    }

    private void moveTo(int i) {
        boolean passedGo = i < m.getSpaceIndex();
        int amount = 200;

        m.move((i - m.getSpaceIndex() + 40) % 40);
        v.say("\n" + m.getPlayerName() + " moves to " + m.getSpaceName());

        if(passedGo && !m.playerIsInJail()) {
            collect(amount);
            v.say(" for passing go");
        }

        v.move(m.getPlayerIndex(), m.getSpaceIndex());

        v.refresh();

        land();
    }

    private void pay(int amount) {
        if (!bankrupt(amount)) {
            v.say("\n" + m.getPlayerName() + " pays " + $ + amount);
            m.pay(amount);

            if (CRYPTO) {
                c.newTransaction(m.getPlayerName(), null, amount);
            }
        }
        updateView();
    }

    private void collect(int amount) {
        v.say("\n" + m.getPlayerName() + " collects " + $ + amount);
        m.collect(amount);

        if (CRYPTO) {
            c.newTransaction(null, m.getPlayerName(), amount);
        }
    }

    public boolean playerCanAfford(int amount) {
        return m.getPlayerMoney() >= amount;
    }

    /*
     *                                             ┏━━━━━━━━━━━━━━━┓
     *                                             ┃ HOUSES/HOTELS ┃
     *                                             ┗━━━━━━━━━━━━━━━┛
     */

    public void improve(String s) {
        int[] is = m.improve(s);
        v.say("\n" + m.getPlayerName() + " buys a ");
        if (is[1] < 5) {
            v.addHouse(is[0], is[1] - 1);
            v.say("house");
        } else {
            v.say("hotel");
            v.addHotel(is[0]);
        }
        v.say(" on " + s);

        updateView();

        if (CRYPTO) {
            c.newTransaction(m.getPlayerName(), null, is[2]);
        }
    }

    public void degrade(String s) {
        int[] is = m.degrade(s);
        v.say("\n" + m.getPlayerName() + " sells a ");
        if (is[1] < 5) {
            v.removeHouse(is[0], is[1] - 1);
            v.say("house");
        } else {
            v.say("hotel");
            v.removeHotel(is[0]);
        }
        v.say(" on " + s);

        updateView();

        if (CRYPTO) {
            c.newTransaction(null, m.getPlayerName(), is[2]);
        }
    }

    private void payForHousesAndHotels(int costPerHouse, int costPerHotel) {
        int numberOfHouses = m.getNumberOfHousesOwned();
        int numberOfHotels = m.getNumberOfHotelsOwned();
        if(numberOfHouses + numberOfHotels != 0) {
            pay(numberOfHouses * costPerHouse +
                    numberOfHotels * costPerHotel);
        }
    }

    /*
     *                                                  ┏━━━━━━┓
     *                                                  ┃ JAIL ┃
     *                                                  ┗━━━━━━┛
     */

    private void goToJail() {
        moveDoubles = 0;
        v.say("\n" + m.getPlayerName() + " goes to jail");
        m.setInJail(true);
        moveTo(10);
        startTurn();
    }

    /*
     * "You get out of Jail by...
     *
     * (1) throwing doubles on any of your next three turns; if you succeed in doing this you immediately move forward
     * the number of spaces shown by your doubles throw; even though you had thrown doubles, you do not take another
     * turn;
     *
     * (2) using the "Get Out of Jail Free" card if you have it;
     *
     * (3) paying a fine of £50 before you roll the dice on either of your next two turns."
     *
     */

    public void throwDoubles () {
        v.say("\n" + m.getPlayerName() + " tries to roll doubles to get out of jail");
        int r = Dice.roll();

        if (Dice.equ()) {
            setOutOfJail();
            move(r);

        } else {
            int n = jailDoubles[m.getPlayerIndex()];
            v.say("\nThey fail for the " + (n == 0 ? "first" : (n == 1 ? "second" : "third")) + " time");

            if (n < 2) {
                jailDoubles[m.getPlayerIndex()]++;

            } else {
                jailDoubles[m.getPlayerIndex()] = 0;
                payFine(false);
                setOutOfJail();
                move(r);
            }
        }
    }

    public void useCard() {
        v.say("\n" + m.getPlayerName() + " uses their get out of jail free card");
        m.getOutOfJailFree(false);
        setOutOfJail();
        startTurn();
    }

    public void payFine(boolean go) {
        int amount = 50;
        pay(amount);
        v.say(" to get out of jail");

        setOutOfJail();
        if(go) {
            startTurn();
        }
    }

    private void setOutOfJail() {
        m.setInJail(false);
        moveDoubles = 0;
        jailDoubles[m.getPlayerIndex()] = 0;
    }

    public boolean playerHasCard() {
        return m.playerHasCard();
    }

    private void getOutOfJailFree() {
        v.say(m.getPlayerName() + " gets a get out of jail free card");
        m.getOutOfJailFree(true);
    }

    /*
     *                                                ┏━━━━━━━━━┓
     *                                                ┃ BUTTONS ┃
     *                                                ┗━━━━━━━━━┛
     */

    public void buyPropertyClicked() {
        v.say("\n" + m.getPlayerName() + " buys " + m.getSpaceName());
        int amount = m.buyProperty();

        if (CRYPTO) {
            c.newTransaction(m.getPlayerName(), null, amount);
        }

        v.enableBuyPropertyButton(false);
        updateView();
    }

    public void buyHouseClicked() {
        v.buyBuildingDialog(m.getNamesOfHousableStreets());
    }

    public void buyHotelClicked() {
        v.buyBuildingDialog(m.getNamesOfHotelableStreets());
        updateView();
    }

    public void sellHouseClicked() {
        v.sellBuildingDialog(m.getNamesOfUnhouseableStreets());
    }

    public void sellHotelClicked() {
        v.sellBuildingDialog(m.getNamesOfUnhotelableStreets());
    }

    public void mortgagePropertyClicked() {
        String s = v.mortgagePropertyDialog(m.namesOfMortgageableProperties());
        if (s != null) {
            mortgage(s);
        }
    }

    public void liftMortgageClicked() {
        String s = v.mortgagePropertyDialog(m.namesOfUnmortgageableProperties());
        if (s != null) {
            unmortgage(s);
        }
    }

    public boolean isStreet(int space) {
        return m.isStreet(space);
    }
}
