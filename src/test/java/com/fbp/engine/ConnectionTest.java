package com.fbp.engine;

import com.fbp.engine.core.Connection;
import com.fbp.engine.core.DefaultInputPort;
import com.fbp.engine.core.InputPort;
import com.fbp.engine.core.Node;
import com.fbp.engine.message.Message;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTest {

    static class TestNode implements Node {
        Message receivdeMessage;
        int cnt = 0;
        int firstValue = 0;
        int lastValue = 0;

        @Override
        public String getId() {
            return "test";
        }

        @Override
        public void process(Message message) {
            receivdeMessage = message;
            cnt++;

            int v = message.get("v");
            if(cnt == 1){
                firstValue = v;
                lastValue =v;
            }
        }
    }

    @Test
    void testDeliverToTarget(){
        Connection connection = new Connection("c1");
        TestNode node = new TestNode();
        InputPort inputPort = new DefaultInputPort(node,"in");
        connection.setTarget(inputPort);

        Message message = new Message(Map.of("a",1));
        connection.deliver(message);
    }

    @Test
    void testTarget(){
        Connection connection = new Connection("c2");
        Message message = new Message(Map.of("key","value"));
        assertDoesNotThrow(() -> connection.deliver(message));
    }

    @Test
    void testBufferSize(){
        Connection connection = new Connection("c3");

        connection.deliver(new Message(Map.of()));
        connection.deliver(new Message(Map.of()));

        assertEquals(2, connection.getBufferSize());
    }

    @Test
    void testOrder(){
        Connection connection = new Connection("c4");

        TestNode testNode = new TestNode();
        connection.setTarget(new DefaultInputPort(testNode,"in"));

        Message m1 = new Message(Map.of("v",1));
        Message m2 = new Message(Map.of("v",2));

        connection.deliver(m1);
        connection.deliver(m2);

        assertEquals(2,testNode.cnt);
        assertEquals(1,testNode.firstValue);
        assertEquals(1,testNode.lastValue);
    }
}
