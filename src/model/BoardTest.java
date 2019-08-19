package model;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static model.Colour.BROWN;
import static model.Colour.LIGHT_BLUE;
import static model.Type.*;
import static org.junit.Assert.*;

public class BoardTest {

    @Before
    public void setUp() throws Exception {
        Method method = Board.class.getDeclaredMethod("reset");
        method.setAccessible(true);
        method.invoke(null);
    }

    @Test
    public void addSpace() {
        assertTrue(Board.addSpace("A", GO));
        assertFalse(Board.addSpace("A", null));
        assertFalse(Board.addSpace("B", GO));
        assertTrue(Board.addSpace("B", JAIL));
        assertFalse(Board.addSpace("C", JAIL));
    }

    @Test
    public void addStreet() {
        assertTrue(Board.addStreet("A", 1, BROWN, new int[]{1, 1, 1, 1, 1, 1}, 1));
        assertFalse(Board.addStreet("A", 1, BROWN, new int[]{1, 1, 1, 1, 1, 1}, 1));
        assertFalse(Board.addStreet("B", 0, BROWN, new int[]{1, 1, 1, 1, 1, 1}, 1));
        assertFalse(Board.addStreet("B", 1, null, new int[]{1, 1, 1, 1, 1, 1}, 1));
        assertFalse(Board.addStreet("B", 1, BROWN, null, 1));
        assertFalse(Board.addStreet("B", 1, BROWN, new int[]{1, 1, 1, 1, 1}, 1));
        assertFalse(Board.addStreet("B", 1, BROWN, new int[]{1, 1, 1, 1, 1, 1}, 0));
    }

    @Test
    public void addRailroad() {
        assertTrue(Board.addRailroad("A", 1));
        assertFalse(Board.addRailroad("A", 1));
        assertFalse(Board.addRailroad("B", 0));
    }

    @Test
    public void addUtility() {
        assertTrue(Board.addUtility("A", 1));
        assertFalse(Board.addUtility("A", 1));
        assertFalse(Board.addUtility("B", 0));
    }

    @Test
    public void getNumberOfSpaces() {
        assertEquals(0, Board.getNumberOfSpaces());
        Board.addStreet("A", 1, BROWN, new int[]{1, 1, 1, 1, 1, 1}, 1);
        assertEquals(1, Board.getNumberOfSpaces());
        Board.addStreet("B", 1, BROWN, new int[]{1, 1, 1, 1, 1, 1}, 1);
        assertEquals(2, Board.getNumberOfSpaces());
    }

    @Test
    public void getSpace() {
        Board.addSpace("A", GO);
        Board.addSpace("B", COMMUNITY_CHEST);
        Space space = Board.getSpace(0);
        assertEquals("A", space.getName());
        Space space2 = Board.getSpace(1);
        assertEquals("B", space2.getName());
    }

    @Test
    public void getProperties() {
        Board.addSpace("A", GO);
        Board.addStreet("B", 1, BROWN, new int[]{1, 1, 1, 1, 1, 1}, 1);
        Board.addRailroad("C", 1);
        Board.addUtility("D", 1);
        List<Property> properties = Board.getProperties();
        assertEquals(3, properties.size());
        assertEquals("B", properties.get(0).getName());
        assertEquals("C", properties.get(1).getName());
        assertEquals("D", properties.get(2).getName());
    }

    @Test
    public void getStreets() {
        Board.addSpace("A", GO);
        Board.addStreet("B", 1, BROWN, new int[]{1, 1, 1, 1, 1, 1}, 1);
        Board.addStreet("C", 1, BROWN, new int[]{1, 1, 1, 1, 1, 1}, 1);
        List<Street> streets = Board.getStreets();
        assertEquals(2, streets.size());
        assertEquals("B", streets.get(0).getName());
        assertEquals("C", streets.get(1).getName());
    }

    @Test
    public void getRailroads() {
        Board.addSpace("A", GO);
        Board.addRailroad("B", 1);
        Board.addRailroad("C", 1);
        List<Railroad> railroads = Board.getRailroads();
        assertEquals(2, railroads.size());
        assertEquals("B", railroads.get(0).getName());
        assertEquals("C", railroads.get(1).getName());
    }

    @Test
    public void getUtilities() {
        Board.addSpace("A", GO);
        Board.addUtility("B", 1);
        Board.addUtility("C", 1);
        List<Utility> utilities = Board.getUtilities();
        assertEquals(2, utilities.size());
        assertEquals("B", utilities.get(0).getName());
        assertEquals("C", utilities.get(1).getName());
    }

    @Test
    public void getPropertyWithName() {
        Board.addStreet("A", 1, BROWN, new int[]{1, 1, 1, 1, 1, 1}, 1);
        assertNotNull(Board.getPropertyWithName("A"));
        assertNull(Board.getPropertyWithName("B"));
    }

    @Test
    public void getStreetWithName() {
        Board.addStreet("A", 1, BROWN, new int[]{1, 1, 1, 1, 1, 1}, 1);
        assertNotNull(Board.getPropertyWithName("A"));
        assertNull(Board.getStreetWithName("B"));
    }

    @Test
    public void getStreetsWithColour() {
        Board.addStreet("A", 1, BROWN, new int[]{1, 1, 1, 1, 1, 1}, 1);
        Board.addStreet("B", 1, LIGHT_BLUE, new int[]{1, 1, 1, 1, 1, 1}, 1);
        List<Street> streets = Board.getStreetsWithColour(BROWN);
        assertEquals(1, streets.size());
        assertEquals("A", streets.get(0).getName());
    }
}