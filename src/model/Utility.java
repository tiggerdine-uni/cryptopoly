package model;

import controller.Dice;

import java.util.List;

public class Utility extends Property {

    Utility(int index, String name, int price) {
        super(index, name, price, Type.UTILITY);
    }

    public int getRent() {
        List<Utility> utilities = Board.getUtilities();
        int utilitiesOwned = 0;
        for (Utility utility : utilities) {
            if (utility.isOwnedBy(getOwner())) {
                utilitiesOwned++;
            }
        }
        /* utilitiesOwned | 6 * utilitiesOwned - 2 | utilitiesOwned * (utilitiesOwned + 3)
           ---------------+------------------------+--------------------------------------
           1              | 4                      | 4
           2              | 10                     | 10
           3*             | 16                     | 18
           4*             | 22                     | 28                                    */
        return utilitiesOwned * (utilitiesOwned + 3) * Dice.add();
    }

    @Override
    boolean isUtility() {
        return true;
    }

    @Override
    public String inspect() {
        return super.inspect() + "*";
    }
}
