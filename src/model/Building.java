package model;

enum Building {
    NOTHING("-") {
        @Override
        public Building previous() {
            return null;
        }
    },
    HOUSE("1 House"),
    TWO_HOUSES("2 Houses"),
    THREE_HOUSES("3 Houses"),
    FOUR_HOUSES("4 Houses"),
    HOTEL("Hotel") {
        @Override
        public Building next() {
            return null;
        }
    };

    private final String s;

    Building(String s) {
        this.s = s;
    }

    public Building next() {
        return values()[ordinal() + 1];
    }

    public Building previous() {
        return values()[ordinal() - 1];
    }

    @Override
    public String toString() {
        return s;
    }
}
