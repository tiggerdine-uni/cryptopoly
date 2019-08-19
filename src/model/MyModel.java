package model;

import util.CircularList;

import java.util.ArrayList;
import java.util.List;

/**
 * A model that implements Model.
 */
public class MyModel implements Model {

    /*
     * The players.
     */
    private CircularList<Player> players;

    /*
     * The player whose turn it is.
     */
    private Player player;

    public MyModel() {
        Board.addSpaces();
        players = new CircularList<>();
    }

    /*
     *                                                 ┏━━━━━━━┓
     *                                                 ┃ SPACE ┃
     *                                                 ┗━━━━━━━┛
     */

    @Override
    public int getSpaceIndex() {
        return player.getSpace().getIndex();
    }

    @Override
    public int getNewIndex(int numberOfSpaces) {
        return (getSpaceIndex() + numberOfSpaces + Board.getNumberOfSpaces()) % Board.getNumberOfSpaces();
    }

    @Override
    public String getPropertyOwnerName() {
        return player.getProperty().getOwner().getName();
    }

    @Override
    public boolean propertyIsUnowned() {
        return player.getProperty().getOwner() == null;
    }

    @Override
    public Type getSpaceType() {
        return player.getSpace().getType();
    }

    @Override
    public String getSpaceName() {
        return player.getSpace().getName();
    }

    @Override
    public String inspectSpace(int index) {
        return Board.getSpace(index).inspect();
    }

    @Override
    public int getPropertyRent() {
        return player.getProperty().getRent();
    }

    @Override
    public boolean isStreet(int space) {
        return Board.getSpace(space).isStreet();
    }

    /*
     *                                                 ┏━━━━━━━━┓
     *                                                 ┃ PLAYER ┃
     *                                                 ┗━━━━━━━━┛
     */

    @Override
    public void addPlayer(String name) {
        players.add(new Player(name));
    }

    @Override
    public void shufflePlayers() {
        players.shuffle();
    }

    @Override
    public boolean playerCanAffordProperty() {
        return player.has(player.getProperty().getPrice());
    }

    @Override
    public boolean playerIsInJail() {
        return player.isInJail();
    }

    @Override
    public boolean playerHasCard() {
        return player.canGetOutOfJailFree();
    }

    @Override
    public void getOutOfJailFree(boolean b) {
        player.setJailCard(b);
    }

    @Override
    public void setInJail(boolean b) {
        player.setInJail(b);
    }

    @Override
    public String getPlayerName() {
        return player.getName();
    }

    @Override
    public String getNameOfPlayer(int i) {
        return players.get(i).getName();
    }

    @Override
    public boolean playerOwnsProperty() {
        return player.getProperty().getOwner() == player;
    }

    @Override
    public int getPlayerMoney() {
        return player.getMoney();
    }

    @Override
    public int getPlayerIndex() {
        return players.getIndex();
    }

    @Override
    public String inspectPlayer(String name) {
        for (Player player : players.get()) {
            if (player.getName().equals(name)) {
                return player.inspect();
            }
        }
        return "";
    }

    @Override
    public void nextPlayer() {
        player = players.next();
    }

    @Override
    public void move(int numberOfSpaces) {
        Space oldSpace = player.getSpace();
        oldSpace.removePlayer(player);
        Space newSpace = Board.getSpace(getNewIndex(numberOfSpaces));
        player.setSpace(newSpace);
        newSpace.addPlayer(player);
    }

    @Override
    public int getNumberOfPlayers() {
        return players.size();
    }

    /*
     *                                              ┏━━━━━━━━━━━━━━┓
     *                                              ┃ TRANSACTIONS ┃
     *                                              ┗━━━━━━━━━━━━━━┛
     */

    @Override
    public void payOwner(int amount) {
        Property property = player.getProperty();
        System.out.print(player.getName() + " " + player.getMoney() + " - " + amount + " = ");
        player.subtractMoney(amount);
        System.out.println(player.getMoney());
        System.out.print(property.getOwner().getName() + " " + property.getOwner().getMoney() + " + " + amount + " = ");
        property.getOwner().addMoney(amount);
        System.out.println(property.getOwner().getMoney());
    }

    @Override
    public void collect(int amount) {
        System.out.print(player.getName() + " " + player.getMoney() + " + " + amount + " = ");
        player.addMoney(amount);
        System.out.println(player.getMoney());
    }

    @Override
    public void pay(int amount) {
        System.out.print(player.getName() + " " + player.getMoney() + " - " + amount + " = ");
        player.subtractMoney(amount);
        System.out.println(player.getMoney());
    }

    @Override
    public void payRent() {
        Property property = player.getProperty();
        int rent = property.getRent();
        payOwner(rent);
    }

    @Override
    public int buyProperty() {
        Property property = player.getProperty();
        int price = property.getPrice();
        System.out.print(player.getName() + " " + player.getMoney() + " - " + price + " = ");
        player.subtractMoney(price);
        System.out.println(player.getMoney());
        property.setOwner(player);
        return price;
    }

    /*
     *                                             ┏━━━━━━━━━━━━━━━┓
     *                                             ┃ HOUSES/HOTELS ┃
     *                                             ┗━━━━━━━━━━━━━━━┛
     */

    @Override
    public int getNumberOfHousesOwned() {
        int n = 0;
        for (Street street : Board.getStreets()) {
            if (street.isOwnedBy(player)) {
                n += street.getNumberOfHouses();
            }
        }
        return n;
    }

    @Override
    public int getNumberOfHotelsOwned() {
        int n = 0;
        for (Street street : Board.getStreets()) {
            if (street.isOwnedBy(player) && street.hasHotel()) {
                n++;
            }
        }
        return n;
    }

    @Override
    public List<String> getNamesOfHousableStreets() {
        List<String> names = new ArrayList<>();
        for (Street street : Board.getStreets()) {
            if (isHousable(street)) {
                names.add(street.getName());
            }
        }
        return names;
    }

    @Override
    public List<String> getNamesOfHotelableStreets() {
        List<String> names = new ArrayList<>();
        for (Street street : Board.getStreets()) {
            if (isHotelable(street)) {
                names.add(street.getName());
            }
        }
        return names;
    }

    @Override
    public List<String> getNamesOfUnhouseableStreets() {
        List<String> names = new ArrayList<>();
        for (Street street : Board.getStreets()) {
            if (isUnhousable(street)) {
                names.add(street.getName());
            }
        }
        return names;
    }

    @Override
    public List<String> getNamesOfUnhotelableStreets() {
        List<String> names = new ArrayList<>();
        for (Street street : Board.getStreets()) {
            if (isUnhotelable(street)) {
                names.add(street.getName());
            }
        }
        return names;
    }

    @Override
    public int[] improve(String s) {
        Street street = Board.getStreetWithName(s);
        if(street != null) {
            street.improve();
            pay(street.buildingPrice);
            return new int[]{street.getIndex(), street.building.ordinal(), street.buildingPrice};
        }
        return null;
    }

    @Override
    public int[] degrade(String s) {
        Street street = Board.getStreetWithName(s);
        if(street != null) {
            street.building = street.building.previous();
            collect(street.buildingPrice / 2);
            return new int[]{street.getIndex(), street.building.ordinal() + 1, street.buildingPrice / 2};
        }
        return null;
    }

    private boolean isHousable(Street street) {
        return street.isOwnedBy(player) && player.has(street.getBuildingPrice()) && street.isHousable();
    }

    private boolean isHotelable(Street street) {
        return street.isOwnedBy(player) && player.has(street.getBuildingPrice()) && street.isHotelable();
    }

    private boolean isUnhousable(Street street) {
        return street.isOwnedBy(player) && street.isUnhousable();
    }

    private boolean isUnhotelable(Street street) {
        return street.isOwnedBy(player) && street.isUnhotelable();
    }

    /*
     *                                               ┏━━━━━━━━━━━┓
     *                                               ┃ MORTGAGES ┃
     *                                               ┗━━━━━━━━━━━┛
     */

    @Override
    public boolean propertyIsMortgaged() {
        return player.getProperty().isMortgaged();
    }

    @Override
    public boolean playerCanMortgageProperty() {
        return !namesOfMortgageableProperties().isEmpty();
    }

    @Override
    public List<String> namesOfMortgageableProperties() {
        return mortgageSearch(false);
    }

    @Override
    public boolean playerCanUnmortgageProperty() {
        return !namesOfUnmortgageableProperties().isEmpty();
    }

    @Override
    public List<String> namesOfUnmortgageableProperties() {
        return mortgageSearch(true);
    }

    @Override
    public int mortgage(String name) {
        Property property = Board.getPropertyWithName(name);
        property.setMortgaged(true);
        System.out.print(getPlayerName() + " " + player.getMoney() + " -> ");
        int amount = property.getValueOfMortgaging();
        player.addMoney(amount);
        System.out.println(player.getMoney());
        return amount;
    }

    @Override
    public int unmortgage(String name) {
        Property property = Board.getPropertyWithName(name);
        property.setMortgaged(false);
        System.out.print(getPlayerName() + " " + player.getMoney() + " -> ");
        int amount = property.getCostOfLiftingMortgage();
        player.subtractMoney(amount);
        System.out.println(player.getMoney());
        return amount;
    }

    private List<String> mortgageSearch(boolean b) {
        // b => lifting mortgage
        // !b => mortgating property
        List<String> names = new ArrayList<>();
        for (Property property : Board.getProperties()) {
            if (property.isOwnedBy(player) && property.isMortgaged() == b) {
                if (!b || player.has(property.getCostOfLiftingMortgage())) {
                    if (property.isRailroad() || property.isUtility()) {
                        names.add(property.getName());
                    } else if (property.isStreet()) {
                        Street street = (Street) property;
                        if (street.isUnimproved()) {
                            names.add(property.getName());
                        }
                    }
                }
            }
        }
        return names;
    }

}
