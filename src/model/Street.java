package model;

public class Street extends Property {

    Colour colour;
    int rent;

    Street(String name, int price, Colour colour, int rent) {
        super(name, price);
        this.colour = colour;
        this.rent = rent;
    }

    int getRent() {
        return rent;
    }
}
