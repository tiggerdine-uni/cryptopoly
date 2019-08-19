package view;

import controller.Controller;

import java.util.List;

public interface View {

    void setController(Controller controller);

    void enableBuyPropertyButton(boolean b);

    void enableEndTurnButton(boolean b);

    void say(String s);

    void reset();

    void showJailDialog(String name);

    void extraTurn();

    void buyBuildingDialog(List<String> streetNames);

    void enableBuyHouseButton(boolean b);

    void enableBuyHotelButton(boolean b);

    void enableMortgagePropertyButton(boolean b);

    void enableUnmortgagedPropertyButton(boolean b);

    String mortgagePropertyDialog(List<String> names);

    void move(int playerId, int newIndex);

    void addHouse(int spaceIndex, int houseIndex);

    void addHotel(int spaceIndex);

    void sellBuildingDialog(List<String> streetNames);

    void removeHouse(int spaceIndex, int houseIndex);

    void removeHotel(int spaceIndex);

    void enableSellHouseButton(boolean b);

    void enableSellHotelButton(boolean b);

    void refresh();

    void showDetailsAboutSpace(int spaceIndex);

    void disableAllButtons();
}
