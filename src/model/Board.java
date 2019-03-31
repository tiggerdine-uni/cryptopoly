package model;

public class Board {

    Space[] spaces;

    Board() {
        spaces = new Space[40];
        spaces[1] = new Street("Old Kent Road", 60, Colour.BROWN, 2);
        spaces[5] = new Railroad("King's Cross Station", 200);
        spaces[12] = new Utility("Electric Company", 150);
    }

    public static void main(String[] args) {
        Board board = new Board();
    }

}
