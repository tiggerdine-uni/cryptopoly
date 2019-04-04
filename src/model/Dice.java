package model;

import java.util.Random;

public class Dice {

    private static int a, b;
    private static Random r = new Random();

    // TODO rename
    static int add() {
        return a + b;
    }

    // TODO rename
    static boolean equ() {
        return a == b;
    }

    static int roll() {
        a = r.nextInt(6) + 1;
        b = r.nextInt(6) + 1;
        return add();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(Dice.roll() + " " + Dice.equ());
        }
    }

}
