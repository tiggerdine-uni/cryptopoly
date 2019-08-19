package util;

import java.util.Random;

public class Util {

    private static Random r = new Random();

    public static String randomSubstring(String s, int length) {
        if (length > s.length()) {
            return s;
        }
        int beginIndex = r.nextInt(s.length() - length + 1);
        int endIndex = beginIndex + length;
        return s.substring(beginIndex, endIndex);
    }

}
