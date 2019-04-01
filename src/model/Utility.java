package model;

public class Utility extends Property {

    Utility(String name, int price) {
        super(name, price);
    }

    int getRent() {
        Utility[] utilities = Board.getUtilities();
        int utilitiesOwned = 0;
        for (Utility utility : utilities) {
            if (utility.owner == owner) {
                utilitiesOwned++;
            }
        }
        /* utilitiesOwned   6 * utilitiesOwned - 2   utilitiesOwned * (utilitiesOwned + 3)
           1                4                        4
           2                10                       10
           3*               16                       18
           4*               22                       28                                    */
        return utilitiesOwned * (utilitiesOwned + 3); // TODO * 2d6
    }

}
