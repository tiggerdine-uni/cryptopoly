package model;

public enum Type {

    STREET("Street"),
    RAILROAD("Railroad"),
    UTILITY("Utility"),
    GO("Go"),
    COMMUNITY_CHEST("Community Chest"),
    INCOME_TAX("Income Tax"),
    CHANCE("Chance"),
    JAIL("Jail"),
    FREE_PARKING("Free Parking"),
    GO_TO_JAIL("Go to Jail"),
    SUPER_TAX("Super Tax");

    String s;

    Type(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
