package model;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

public class RailroadTest {

    @Before
    public void setUp() throws Exception {
        Method method = Board.class.getDeclaredMethod("reset");
        method.setAccessible(true);
        method.invoke(null);
    }

    @Test
    public void getRent() {
        Board.addRailroad("A", 1);
        Board.addRailroad("B", 1);
        Board.addRailroad("C", 1);
        Board.addRailroad("D", 1);

        List<Railroad> railroads = Board.getRailroads();
        Railroad railroad = railroads.get(0);
        Railroad railroad2 = railroads.get(1);
        Railroad railroad3 = railroads.get(2);
        Railroad railroad4 = railroads.get(3);

        Player player = new Player("A");

        railroad.setOwner(player);;
        assertEquals(25, railroad.getRent());

        railroad2.setOwner(player);;
        assertEquals(50, railroad2.getRent());

        railroad3.setOwner(player);;
        assertEquals(100, railroad3.getRent());

        railroad4.setOwner(player);;
        assertEquals(200, railroad4.getRent());
    }
}