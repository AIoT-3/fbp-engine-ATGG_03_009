package com.fbp.engine;

import com.fbp.engine.message.Message;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void testCreate() {
        Message msg = new Message(Map.of("temperature", 25.5));

        assertNotNull(msg.getId());
        assertTrue(msg.getTimestamp() > 0);
        assertEquals(25.5, msg.get("temperature"));
    }

    @Test
    void testGenericGet() {
        Message msg = new Message(Map.of("temperature", 25.5));
        Double temp = msg.get("temperature");

        assertEquals(25.5, temp);
    }

    @Test
    void testMissingKey() {
        Message msg = new Message(Map.of());
        assertNull(msg.get("none"));
    }

    @Test
    void testImmutablePayload() {
        Message msg = new Message(Map.of("a", 1));

        assertThrows(UnsupportedOperationException.class, () -> {
            msg.getPayload().put("b", 2);
        });
    }

    @Test
    void testWithEntry() {
        Message msg1 = new Message(Map.of("a", 1));
        Message msg2 = msg1.withEntry("b", 2);

        assertNotSame(msg1, msg2);
        assertFalse(msg1.hasKey("b"));
        assertEquals(2, (int) msg2.get("b"));
    }

    @Test
    void testWithoutKey() {
        Message msg1 = new Message(Map.of("a", 1, "b", 2));
        Message msg2 = msg1.withoutKey("b");

        assertTrue(msg1.hasKey("b"));
        assertFalse(msg2.hasKey("b"));
    }

    @Test
    void testToString() {
        Message msg = new Message(Map.of("a", 1));
        assertNotNull(msg.toString());
        assertTrue(msg.toString().contains("a"));
    }
}
