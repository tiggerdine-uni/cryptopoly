package model;

import controller.Dice;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UtilityTest {

    @Before
    public void setUp() throws Exception {
        Method method = Board.class.getDeclaredMethod("reset");
        method.setAccessible(true);
        method.invoke(null);
    }

    @Test
    public void getRent() {
        Board.addUtility("A", 1);
        Board.addUtility("B", 1);

        List<Utility> utilities = Board.getUtilities();
        Utility utility = utilities.get(0);
        Utility utility2 = utilities.get(1);

        Player player = new Player("A");

        int roll = Dice.roll();

        utility.setOwner(player);;
        assertEquals(4 * roll, utility.getRent());

        utility2.setOwner(player);;
        assertEquals(10 * roll, utility2.getRent());
    }
}