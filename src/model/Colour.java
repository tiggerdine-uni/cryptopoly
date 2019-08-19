package model;

public enum Colour {
    BROWN("Brown"),
    LIGHT_BLUE("Light Blue"),
    PINK("Pink"),
    ORANGE("Orange"),
    RED("Red"),
    YELLOW("Yellow"),
    GREEN("Green"),
    DARK_BLUE("Dark Blue");

    private final String s;

    Colour(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
