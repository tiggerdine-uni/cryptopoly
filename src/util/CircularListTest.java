package util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CircularListTest {

    private CircularList<String> circularList;

    @Before
    public void setUp() throws Exception {
        circularList = new CircularList<>();
    }

    @Test
    public void hasNext() {
        assertFalse(circularList.hasNext());
        circularList.add("");
        assertTrue(circularList.hasNext());
        circularList.next();
        assertTrue(circularList.hasNext());
    }

    @Test
    public void next() {
        circularList.add("A");
        circularList.add("B");
        assertEquals("A", circularList.next());
        assertEquals("B", circularList.next());
        assertEquals("A", circularList.next());
    }

    @Test
    public void add() {
        assertTrue(circularList.add(""));
        circularList.next();
        assertFalse(circularList.add(""));
    }

    @Test
    public void shuffle() {
        assertTrue(circularList.shuffle());
        assertTrue(circularList.add(""));
        circularList.next();
        assertFalse(circularList.shuffle());
    }

    @Test
    public void getIndex() {
        assertEquals(-1, circularList.getIndex());
        circularList.add("");
        assertEquals(-1, circularList.getIndex());
        circularList.add("");
        circularList.next();
        assertEquals(0, circularList.getIndex());
        circularList.next();
        assertEquals(1, circularList.getIndex());
        circularList.next();
        assertEquals(0, circularList.getIndex());
    }
}