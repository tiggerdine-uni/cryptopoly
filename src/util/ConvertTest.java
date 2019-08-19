package util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ConvertTest {

    @Test
    public void bytesToHex() {
        assertEquals("", Convert.bytesToHex(new byte[0]));
        assertEquals("00", Convert.bytesToHex(new byte[]{0}));
        assertEquals("01", Convert.bytesToHex(new byte[]{1}));
        assertEquals("0a", Convert.bytesToHex(new byte[]{10}));
        assertEquals("0b", Convert.bytesToHex(new byte[]{11}));
        assertEquals("10", Convert.bytesToHex(new byte[]{16}));
        assertEquals("11", Convert.bytesToHex(new byte[]{17}));
        assertEquals("1a", Convert.bytesToHex(new byte[]{26}));
        assertEquals("1b", Convert.bytesToHex(new byte[]{27}));
        assertEquals("0000", Convert.bytesToHex(new byte[]{0, 0}));
        assertEquals("0001", Convert.bytesToHex(new byte[]{0, 1}));
        assertEquals("0100", Convert.bytesToHex(new byte[]{1, 0}));
        assertEquals("0101", Convert.bytesToHex(new byte[]{1, 1}));
    }

    @Test
    public void intToBytes() {
        assertEquals("[0]", Arrays.toString(Convert.intToBytes(0)));
        assertEquals("[1]", Arrays.toString(Convert.intToBytes(1)));
        assertEquals("[127]", Arrays.toString(Convert.intToBytes(127)));
        assertEquals("[0, -128]", Arrays.toString(Convert.intToBytes(128)));
        assertEquals("[0, -1]", Arrays.toString(Convert.intToBytes(255)));
        assertEquals("[1, 0]", Arrays.toString(Convert.intToBytes(256)));
        assertEquals("[1, 1]", Arrays.toString(Convert.intToBytes(257)));
    }
}