package model;

public abstract class Property extends Space {

    String name;
    Player owner;
    int price;

    public Property(String name, int price) {
        this.name = name;
        this.price = price;
    }

    String getName() {
        return name;
    }

    int getPrice() {
        return price;
    }

    abstract int getRent();

}
