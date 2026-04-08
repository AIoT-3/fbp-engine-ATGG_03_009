package com.fbp.engine;

import com.fbp.engine.core.Connection;
import com.fbp.engine.message.Message;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockingQueueTest {
    @Test
    void testDeliverPoll() throws InterruptedException {
        Connection connection = new Connection("c1");

        Message message = new Message(Map.of("data","test"));

        connection.deliver(message);
        Message result = connection.poll();
        assertEquals(message,result);
    }

    @Test
    void testFileOrder(){

    }
}
