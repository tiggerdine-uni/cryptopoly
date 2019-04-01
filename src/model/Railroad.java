package model;

public class Railroad extends Property {

    Railroad(String name, int price) {
        super(name, price);
    }

    int getRent() {
        /* railroadsOwned   25 * 2 ^ (railroadsOwned - 1)
           1                25
           2                50
           3                100
           4                200
           5*               400
           6*               800                           */
        return (int) (25 * Math.pow(2, Board.railroadsOwned(owner) - 1));
    }

}
