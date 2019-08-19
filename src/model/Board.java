package model;

import java.util.ArrayList;
import java.util.List;

import static model.Colour.*;
import static model.Type.*;

public class Board {

    private static volatile Board board = new Board();

    private List<Space> spaces;

    private Board() {
        spaces = new ArrayList<>();
    }

    static void addSpaces() {
        addSpace("Go", GO);
        addStreet("Old Kent Road", 60, BROWN, new int[]{2, 10, 30, 90, 160, 250}, 30);
        addSpace("Community Chest", COMMUNITY_CHEST);
        addStreet("Whitechapel Road", 60, BROWN, new int[]{4, 20, 60, 180, 320, 450}, 30);
        addSpace("Income Tax", INCOME_TAX);
        addRailroad("King's Cross Station", 200);
        addStreet("The Angel Islington", 100, LIGHT_BLUE, new int[]{6, 30, 90, 270, 400, 550}, 50);
        addSpace("Chance", CHANCE);
        addStreet("Euston Road", 100, LIGHT_BLUE, new int[]{6, 30, 90, 270, 400, 550}, 50);
        addStreet("Pentonville Road", 120, LIGHT_BLUE, new int[]{8, 40, 100, 300, 450, 600}, 50);
        addSpace("Jail", JAIL);
        addStreet("Pall Mall", 140, PINK, new int[]{10, 50, 150, 450, 625, 750}, 100);
        addUtility("Electric Company", 150);
        addStreet("Whitehall", 140, PINK, new int[]{10, 50, 150, 450, 625, 750}, 100);
        addStreet("Northumberland Avenue", 160, PINK, new int[]{12, 60, 180, 500, 700, 900}, 100);
        addRailroad("Marylebone Station", 200);
        addStreet("Bow Street", 180, ORANGE, new int[]{14, 70, 200, 550, 750, 950}, 100);
        addSpace("Community Chest", COMMUNITY_CHEST);
        addStreet("Marlborough Street", 180, ORANGE, new int[]{14, 70, 200, 550, 750, 950}, 100);
        addStreet("Vine Street", 200, ORANGE, new int[]{16, 80, 220, 600, 800, 1000}, 100);
        addSpace("Free Parking", FREE_PARKING);
        addStreet("Strand", 220, RED, new int[]{18, 90, 250, 700, 875, 1050}, 150);
        addSpace("Chance", CHANCE);
        addStreet("Fleet Street", 220, RED, new int[]{18, 90, 250, 700, 875, 1050}, 150);
        addStreet("Trafalgar Square", 240, RED, new int[]{20, 100, 300, 750, 925, 1100}, 150);
        addRailroad("Fenchurch St Station", 200);
        addStreet("Leicester Square", 260, YELLOW, new int[]{22, 110, 330, 800, 975, 1150}, 150);
        addStreet("Coventry Street", 260, YELLOW, new int[]{22, 110, 330, 800, 975, 1150}, 150);
        addUtility("Water Works", 150);
        addStreet("Picadilly", 280, YELLOW, new int[]{22, 120, 360, 850, 1025, 1200}, 150);
        addSpace("Go to Jail", GO_TO_JAIL);
        addStreet("Regent Street", 300, GREEN, new int[]{26, 130, 390, 900, 1100, 1275}, 150);
        addStreet("Oxford Street", 300, GREEN, new int[]{26, 130, 390, 900, 1100, 1275}, 150);
        addSpace("Community Chest", COMMUNITY_CHEST);
        addStreet("Bond Street", 320, GREEN, new int[]{28, 150, 450, 1000, 1200, 1400}, 150);
        addRailroad("Liverpool Street Station", 200);
        addSpace("Chance", CHANCE);
        addStreet("Park Lane", 350, DARK_BLUE, new int[]{35, 175, 500, 1100, 1300, 1500}, 200);
        addSpace("Super Tax", SUPER_TAX);
        addStreet("Mayfair", 400, DARK_BLUE, new int[]{50, 200, 600, 1400, 1700, 2000}, 200);
    }

    public static Board getBoard() {
        return board;
    }

    static boolean addSpace(String name, Type type) {
        if (type == null || type == STREET || type == RAILROAD || type == UTILITY) {
            return false;
        }
        if (type == GO || type == JAIL) {
            if (hasSpaceWithType(type)) {
                return false;
            }
        }
        board.spaces.add(new Space(board.spaces.size(), name, type));
        return true;
    }

    static boolean addStreet(String name, int price, Colour colour, int[] rents, int buildingPrice) {
        if (hasSquareWithName(name) || price <= 0 || colour == null || rents == null || rents.length != Building.values().length || buildingPrice <= 0) {
            return false;
        }
        for (int rent : rents) {
            if (rent <= 0) {
                return false;
            }
        }
        board.spaces.add(new Street(board.spaces.size(), name, price, colour, rents, buildingPrice));
        return true;
    }

    static boolean addRailroad(String name, int price) {
        if (hasSquareWithName(name) || price <= 0) {
            return false;
        }
        board.spaces.add(new Railroad(board.spaces.size(), name, price));
        return true;
    }

    static boolean addUtility(String name, int price) {
        if (hasSquareWithName(name) || price <= 0) {
            return false;
        }
        board.spaces.add(new Utility(board.spaces.size(), name, price));
        return true;
    }

    static int getNumberOfSpaces() {
        return board.spaces.size();
    }

    static Space getSpace(int index) {
        return board.spaces.get(index);
    }

    static List<Property> getProperties() {
        List<Property> properties = new ArrayList<>();
        for (Space space : board.spaces) {
            if (space.isProperty()) {
                properties.add((Property) space);
            }
        }
        return properties;
    }

    static List<Street> getStreets() {
        List<Street> streets = new ArrayList<>();
        for (Space space : board.spaces) {
            if (space.isStreet()) {
                streets.add((Street) space);
            }
        }
        return streets;
    }

    static List<Railroad> getRailroads() {
        List<Railroad> railroads = new ArrayList<>();
        for (Space space : board.spaces) {
            if (space.isRailroad()) {
                railroads.add((Railroad) space);
            }
        }
        return railroads;
    }

    static List<Utility> getUtilities() {
        List<Utility> utilities = new ArrayList<>();
        for (Space space : board.spaces) {
            if (space.isUtility()) {
                utilities.add((Utility) space);
            }
        }
        return utilities;
    }

    static Property getPropertyWithName(String name) {
        for (Property property : getProperties()) {
            if (property.getName().equals(name)) {
                return property;
            }
        }
        return null;
    }

    static Street getStreetWithName(String n) {
        return (Street) getPropertyWithName(n);
    }

    static List<Street> getStreetsWithColour(Colour c) {
        List<Street> streets = new ArrayList<>();
        for (Street street : getStreets()) {
            if (street.getColour() == c) {
                streets.add(street);
            }
        }
        return streets;
    }

    private static boolean hasSquareWithName(String n) {
        for (Space space : board.spaces) {
            if (space.getName().equals(n)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasSpaceWithType(Type t) {
        for (Space space : board.spaces) {
            if (space.getType() == t) {
                return true;
            }
        }
        return false;
    }







    /**
     * For BoardTest.
     */
    private static void reset() {
        board = new Board();
    }

}
