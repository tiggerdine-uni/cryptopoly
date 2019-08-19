package controller;

import java.util.Random;

/**
 * A pair of six-sided dice.
 */
public class Dice {

    static int a, b;
    static Random r = new Random();

    public static int add() {
        return a + b;
    }

    static boolean equ() {
        return a == b;
    }

    public static int roll() {
        a = r.nextInt(6) + 1;
        b = r.nextInt(6) + 1;
        return add();
    }

}
