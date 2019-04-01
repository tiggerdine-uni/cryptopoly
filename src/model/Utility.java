package model;

public class Utility extends Property {

    Utility(String name, int price) {
        super(name, price);
    }

    int getRent() {
        /* utilitiesOwned   6 * utilitiesOwned - 2   utilitiesOwned * (utilitiesOwned + 3)
           1                4                        4
           2                10                       10
           3*               16                       18
           4*               22                       28                                    */
        int utilitiesOwned = Board.utilitiesOwned(owner);
        return utilitiesOwned * (utilitiesOwned + 3);
    }

}
