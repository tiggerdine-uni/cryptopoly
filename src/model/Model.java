package model;

import java.util.List;

public interface Model {

    void addPlayer(String name);

    void shufflePlayers();

    int getSpaceIndex();

    int getNewIndex(int numberOfSpaces);

    String getPropertyOwnerName();

    boolean propertyIsUnowned();

    Type getSpaceType();

    String inspectSpace(int index);

    int getPropertyRent();

    boolean playerCanAffordProperty();

    boolean playerIsInJail();

    boolean playerHasCard();

    void getOutOfJailFree(boolean b);

    void setInJail(boolean b);

    String getPlayerName();

    int getPlayerIndex();

    String inspectPlayer(String name);

    String getNameOfPlayer(int i);

    boolean playerOwnsProperty();

    void nextPlayer();

    void move(int roll);

    String getSpaceName();

    int getPlayerMoney();

    void payRent();

    int buyProperty();

    boolean propertyIsMortgaged();

    void payOwner(int amount);

    void collect(int amount);

    void pay(int amount);

    int getNumberOfHousesOwned();

    int getNumberOfHotelsOwned();

    List<String> getNamesOfHousableStreets();

    int[] improve(String s);

    List<String> getNamesOfHotelableStreets();

    boolean playerCanMortgageProperty();

    List<String> namesOfMortgageableProperties();

    List<String> namesOfUnmortgageableProperties();

    int mortgage(String s);

    int unmortgage(String s);

    List<String> getNamesOfUnhouseableStreets();

    List<String> getNamesOfUnhotelableStreets();

    int[] degrade(String s);

    int getNumberOfPlayers();

    boolean playerCanUnmortgageProperty();

    boolean isStreet(int space);
}
