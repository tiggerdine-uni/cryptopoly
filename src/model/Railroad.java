package model;

public class Railroad extends Property {

    Railroad(String name, int price) {
        super(name, price);
    }

    int getRent() {
        Railroad[] railroads = Board.getRailroads();
        int railroadsOwned = 0;
        for (Railroad railroad : railroads) {
            if (railroad.owner == owner) {
                railroadsOwned++;
            }
        }
        /* railroadsOwned   25 * 2 ^ (railroadsOwned - 1)
           1                25
           2                50
           3                100
           4                200
           5*               400
           6*               800                           */
        return (int) (25 * Math.pow(2, railroadsOwned - 1));
    }

}
