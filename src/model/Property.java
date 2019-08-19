package model;

import controller.Controller;

public abstract class Property extends Space {

    private Player owner;
    private int price;
    private boolean mortgaged;

    Property(int index, String name, int price, Type type) {
        super(index, name, type);
        mortgaged = false;
        this.price = price;
    }

    public abstract int getRent();

    @Override
    public String inspect() {
        StringBuilder sb = new StringBuilder(super.inspect()).append('\n');
        sb.append("Owner: ").append(owner == null ? "-" : owner.getName()).append('\n');
        sb.append("Price: ").append(Controller.$).append(price).append('\n');
        sb.append("Mortgaged: ").append(mortgaged ? "Yes" : "No").append('\n');
        if(mortgaged) {
            sb.append("Cost of Lifting Mortgage: ").append(Controller.$).append((int) (price / 2 * 1.1)).append('\n');
        } else {
            sb.append("Mortgage Value: ").append(Controller.$).append(price / 2).append('\n');
        }
        sb.append("Rent: ").append(Controller.$).append(getRent());
        return sb.toString();
    }

    @Override
    boolean isProperty() {
        return true;
    }

    boolean isOwnedBy(Player player) {
        return owner == player;
    }

    boolean isMortgaged() {
        return mortgaged;
    }

    void setMortgaged(boolean b) {
        mortgaged = b;
    }

    void setOwner(Player player) {
        owner = player;
    }

    Player getOwner() {
        return owner;
    }

    int getPrice() {
        return price;
    }

    int getValueOfMortgaging() {
        return price / 2;
    }

    int getCostOfLiftingMortgage() {
        return (int) (price / 2 * 1.1);
    }
}
