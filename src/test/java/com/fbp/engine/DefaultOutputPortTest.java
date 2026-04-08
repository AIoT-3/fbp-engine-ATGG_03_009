package com.fbp.engine;


import com.fbp.engine.core.Connection;
import com.fbp.engine.core.DefaultInputPort;
import com.fbp.engine.core.DefaultOutputPort;
import com.fbp.engine.core.Node;
import com.fbp.engine.message.Message;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultOutputPortTest {

    @Test
    void testOneConnection() throws InterruptedException {
        DefaultOutputPort outputPort = new DefaultOutputPort("out");

        Connection connection = new Connection("c1");

        TestHelperNode node = new TestHelperNode();
        connection.setTarget(new DefaultInputPort(node,"in"));
        outputPort.connect(connection);

        outputPort.send(new Message(Map.of("a",1)));

        assertEquals(1,node.count);
    }

    @Test
    void testMultiConnection() throws InterruptedException {
        DefaultOutputPort outputPort = new DefaultOutputPort("out");

        Connection connection = new Connection("c1");
        Connection connection1 = new Connection("c2");

        TestHelperNode node = new TestHelperNode();
        TestHelperNode node1 = new TestHelperNode();

        connection.setTarget(new DefaultInputPort(node,"in"));
        connection1.setTarget(new DefaultInputPort(node1,"in"));

        outputPort.connect(connection);
        outputPort.connect(connection1);

        outputPort.send(new Message(Map.of("a",1)));

        assertEquals(1,node.count);
        assertEquals(1,node1.count);

    }

    @Test
    void testNonConnection(){
        DefaultOutputPort outputPort = new DefaultOutputPort("out");
        assertDoesNotThrow(() -> outputPort.send(new Message(Map.of("a",1))));
    }

    static class TestHelperNode implements Node {
        int count = 0;

        @Override
        public String getId() {
            return "test";
        }

        @Override
        public void process(Message message) {
            count++;
        }
    }
}
