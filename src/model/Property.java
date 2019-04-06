package model;

public abstract class Property extends Space {

    String name;
    Player owner;
    int price;

    Property(String name, int price) {
        this.name = name;
        this.price = price;
    }

    abstract int getRent();

}
