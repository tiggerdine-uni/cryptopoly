package util;

import java.math.BigInteger;
import java.util.Arrays;

public class Convert {

    public static String bytesToHex(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (byte b : bytes) {
            String h = Integer.toHexString(0xff & b);
            if(h.length() == 1) {
                s.append('0');
            }
            s.append(h);
        }
        return s.toString();
    }

    /**
     * Unused.
     *
     * @param i
     * @return
     */
    public static byte[] intToBytes(int i) {
        return BigInteger.valueOf(i).toByteArray();
    }

}
