package model;

public class Street extends Property {

    Building building = Building.NOTHING;
    Colour colour;
    int[] rents;

    Street(String name, int price, Colour colour, int[] rents) {
        super(name, price);
        this.colour = colour;
        this.rents = rents;
    }

    int getRent() {
        return rents[building.ordinal()];
    }

}
