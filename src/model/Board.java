package model;

public class Board {

    private static Board instance = new Board();

    Space[] spaces;

    private Board() {
        spaces = new Space[40];
        spaces[1] = new Street("Old Kent Road", 60, Colour.BROWN, 2);
        spaces[5] = new Railroad("King's Cross Station", 200);
        spaces[12] = new Utility("Electric Company", 150);
        spaces[15] = new Railroad("Marylebone Station", 200);
        spaces[25] = new Railroad("Fenchurch St Station", 200);
        spaces[28] = new Utility("Water Works", 150);
        spaces[35] = new Railroad("Liverpool Street Station", 200);
    }

    public static void main(String[] args) {
        Player player = new Player();
        ((Property) instance.spaces[5]).owner = player;
        ((Property) instance.spaces[15]).owner = player;
        ((Property) instance.spaces[25]).owner = player;
        ((Property) instance.spaces[35]).owner = player;
        System.out.println(((Property) instance.spaces[5]).getRent());
        ((Property) instance.spaces[12]).owner = player;
        ((Property) instance.spaces[28]).owner = player;
        System.out.println(((Property) instance.spaces[12]).getRent());
    }

    // TODO getRailroads()
    static Railroad[] getRailroads() {
        return new Railroad[]{(Railroad) instance.spaces[5],  (Railroad) instance.spaces[15],
                              (Railroad) instance.spaces[25], (Railroad) instance.spaces[35]};
    }

    // TODO getUtilities()
    static Utility[] getUtilities() {
        return new Utility[]{(Utility) instance.spaces[12], (Utility) instance.spaces[28]};
    }
}
