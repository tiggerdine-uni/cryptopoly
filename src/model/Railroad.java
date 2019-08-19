package model;

import java.util.List;

public class Railroad extends Property {

    Railroad(int index, String name, int price) {
        super(index, name, price, Type.RAILROAD);
    }

    public int getRent() {
        List<Railroad> railroads = Board.getRailroads();
        int railroadsOwned = 0;
        for (Railroad railroad : railroads) {
            if (railroad.isOwnedBy(getOwner())) {
                railroadsOwned++;
            }
        }
        /* railroadsOwned |  25 * 2 ^ (railroadsOwned - 1)
           ---------------+-------------------------------
           1              | 25
           2              | 50
           3              | 100
           4              | 200
           5*             | 400
           6*             | 800                           */
        return (int) (25 * Math.pow(2, railroadsOwned - 1));
    }

    @Override
    boolean isRailroad() {
        return true;
    }
}
