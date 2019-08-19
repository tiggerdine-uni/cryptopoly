package util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UtilTest {

    private static final int N = 100;

    @Test
    public void randomSubstring() {
        assertEquals("", Util.randomSubstring("", 0));

        assertEquals("", Util.randomSubstring("", 1));

        assertEquals("", Util.randomSubstring("A", 0));

        assertEquals("A", Util.randomSubstring("A", 1));

        assertEquals("A", Util.randomSubstring("A", 2));

        assertEquals("", Util.randomSubstring("AB", 0));

        List<String> substrings = new ArrayList<>();
        substrings.add("A");
        substrings.add("B");
        for(int i = 0; i < N; i++) {
            assertTrue(substrings.contains(Util.randomSubstring("AB", 1)));
        }

        assertEquals("AB", Util.randomSubstring("AB", 2));

        assertEquals("AB", Util.randomSubstring("AB", 3));

        assertEquals("", Util.randomSubstring("ABC", 0));

        substrings.add("C");
        for(int i = 0; i < N; i++) {
            assertTrue(substrings.contains(Util.randomSubstring("ABC", 1)));
        }

        ArrayList<String> substrings2 = new ArrayList<>();
        substrings2.add("AB");
        substrings2.add("BC");
        for(int i = 0; i < N; i++) {
            assertTrue(substrings2.contains(Util.randomSubstring("ABC", 2)));
        }

        assertEquals("ABC", Util.randomSubstring("ABC", 3));

        assertEquals("ABC", Util.randomSubstring("ABC", 4));
    }
}