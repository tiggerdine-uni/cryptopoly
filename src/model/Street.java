package model;

import controller.Controller;

import java.util.List;

import static model.Building.*;

public class Street extends Property {

    Building building = NOTHING;
    Colour colour;
    int[] rents;
    int buildingPrice;

    Street(int index, String name, int price, Colour colour, int[] rents, int buildingPrice) {
        super(index, name, price, Type.STREET);
        this.colour = colour;
        this.rents = rents;
        this.buildingPrice = buildingPrice;
    }

    public int getRent() {
        /*
         * "It is an advantage to hold all the Title Deed cards in a colorgroup because the owner may then charge double
         * rent for unimproved properties in that color-group."
         */
        if (isUnimproved()) {
            List<Street> sameColour = Board.getStreetsWithColour(colour);
            sameColour.remove(this);
            boolean allOwned = true;
            for (Street street : sameColour) {
                if (!street.isOwnedBy(getOwner())) {
                    allOwned = false;
                    break;
                }
            }
            if (allOwned) {
                return 2 * rents[0];
            }
        }

        return rents[building.ordinal()];
    }

    @Override
    public String inspect() {
        StringBuilder sb = new StringBuilder(super.inspect()).append('\n');
        sb.append("Buildings: ").append(building).append('\n');
        sb.append("Building Price: ").append(Controller.$).append(buildingPrice).append('\n');
        sb.append("Colour: ").append(colour);
        return sb.toString();
    }

    @Override
    boolean isStreet() {
        return true;
    }

    Colour getColour() {
        return colour;
    }

    int getNumberOfHouses() {
        if (building == HOTEL) {
            return 0;
        }
        return building.ordinal();
    }

    boolean hasHotel() {
        if (building == HOTEL) {
            return true;
        }
        return false;
    }

    boolean isHousable() {
        return building.ordinal() < FOUR_HOUSES.ordinal() && !isMortgaged();
    }

    int getBuildingPrice() {
        return buildingPrice;
    }

    boolean isHotelable() {
        return building == FOUR_HOUSES && !isMortgaged();
    }

    boolean isUnhousable() {
        return getNumberOfHouses() != 0 && !isMortgaged();
    }

    boolean isUnhotelable() {
        return hasHotel();
    }

    void improve() {
        building = building.next();
    }

    boolean isUnimproved() {
        return building == NOTHING;
    }
}
