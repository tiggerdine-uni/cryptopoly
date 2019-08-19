package model;

import controller.Controller;

public class Player implements Inspectable {

    private String name;
    private Space space;
    private int money;
    private boolean inJail;
    private boolean jailCard;

    public Player(String name) {
        this.name = name;
        space = Board.getSpace(0);
        this.money = 1500;
        inJail = false;
        jailCard = false;
    }

    @Override
    public String inspect() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append('\n');
        sb.append("Space: ").append(space.getName()).append('\n');
        sb.append("Money: ").append(Controller.$).append(money).append('\n');
        sb.append("In Jail: ").append(inJail ? "Yes" : "No").append('\n');
        sb.append("Get Out of Jail Free Card: ").append(jailCard ? "Yes" : "No");
        return sb.toString();
    }

    boolean has(int money) {
        return this.money >= money;
    }

    Space getSpace() {
        return space;
    }

    void setSpace(Space space) {
        this.space = space;
    }

    public Property getProperty() {
        if (space.isProperty()) {
            return (Property) space;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    int getMoney() {
        return money;
    }

    void subtractMoney(int amount) {
        money -= amount;
    }

    void addMoney(int amount) {
        money += amount;
    }

    void setInJail(boolean b) {
        inJail = b;
    }

    boolean isInJail() {
        return inJail;
    }

    boolean canGetOutOfJailFree() {
        return jailCard;
    }

    void setJailCard(boolean b) {
        jailCard = b;
    }
}
