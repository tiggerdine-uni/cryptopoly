package crypto;

import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;

import static org.junit.Assert.*;

public class KeysTest {

    private Keys k;

    @Before
    public void setUp() throws Exception {
        k = new Keys();
        k.add("Martin");
    }

    @Test
    public void add() {
        assertTrue(k.add("Zuza"));
        assertFalse(k.add("Martin"));
    }

    @Test
    public void validate() {
        k.add("Zuza");
        PrivateKey key = k.getPrivateKey("Martin");
        PrivateKey key2 = k.getPrivateKey("Zuza");
        assertTrue(k.validate("Martin", key));
        assertFalse(k.validate("Martin", key2));
        assertTrue(k.validate("Zuza", key2));
        assertFalse(k.validate("Zuza", key));
    }
}