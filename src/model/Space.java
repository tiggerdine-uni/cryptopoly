package model;

import java.util.ArrayList;
import java.util.List;

public class Space implements Inspectable {

    private int index;
    private String name;
    private Type type;
    private List<Player> players;

    Space(int index, String name, Type type) {
        this.index = index;
        this.name = name;
        this.type = type;
        players = new ArrayList<>();
    }

    @Override
    public String inspect() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append('\n');
        sb.append("Type: ").append(type.toString()).append('\n');
        sb.append("Players: ");
        if (players.isEmpty()) {
            sb.append("-  ");
        } else {
            for (Player player : players) {
                sb.append(player.getName()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
    }

    boolean isProperty() {
        return false;
    }

    boolean isStreet() {
        return false;
    }

    boolean isRailroad() {
        return false;
    }

    boolean isUtility() {
        return false;
    }

    String getName() {
        return name;
    }

    Type getType() {
        return type;
    }

    boolean removePlayer(Player player) {
        return players.remove(player);
    }

    boolean addPlayer(Player player) {
        if (players.contains(player)) {
            return false;
        }
        return players.add(player);
    }

    int getIndex() {
        return index;
    }
}
