package model;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static model.Colour.BROWN;
import static org.junit.Assert.*;

public class StreetTest {

    @Before
    public void setUp() throws Exception {
        Method method = Board.class.getDeclaredMethod("reset");
        method.setAccessible(true);
        method.invoke(null);
    }

    @Test
    public void getRent() {
        Board.addStreet("A", 1, BROWN, new int[]{1, 2, 3, 4, 5, 6}, 1);

        Street street = Board.getStreetWithName("A");

        Player player = new Player("A");

        street.setOwner(player);
        assertEquals(2, street.getRent());

        Board.addStreet("B", 1, BROWN, new int[]{1, 1, 1, 1, 1, 1}, 1);
        assertEquals(1, street.getRent());

        street.improve();
        assertEquals(2, street.getRent());

        street.improve();
        assertEquals(3, street.getRent());

        street.improve();
        assertEquals(4, street.getRent());

        street.improve();
        assertEquals(5, street.getRent());

        street.improve();
        assertEquals(6, street.getRent());
    }
}