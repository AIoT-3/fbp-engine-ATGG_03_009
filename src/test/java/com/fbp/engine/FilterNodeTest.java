package com.fbp.engine;

import com.fbp.engine.core.Connection;
import com.fbp.engine.core.DefaultInputPort;
import com.fbp.engine.core.Node;
import com.fbp.engine.message.Message;
import com.fbp.engine.node.FilterNode;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FilterNodeTest {
    static class TestNode implements Node{
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

    @Test
    void testPass(){
        FilterNode filterNode = new FilterNode("f1","temp",30);
        TestNode node = new TestNode();

        Connection connection = new Connection("c1");
        connection.setTarget(new DefaultInputPort(node,"in"));

        filterNode.process(new Message(Map.of("temp",35)));

        assertEquals(0,node.count);
    }

    @Test
    void testBlock(){
        FilterNode filterNode = new FilterNode("f1","temp",30);
        TestNode node = new TestNode();

        Connection connection = new Connection("c1");
        connection.setTarget(new DefaultInputPort(node,"in"));

        filterNode.process(new Message(Map.of("temp",25)));

        assertEquals(0,node.count);
    }

    @Test
    void testBoundary(){
        FilterNode filterNode = new FilterNode("f1","temp",30);
        TestNode node = new TestNode();

        Connection connection = new Connection("c1");
        connection.setTarget(new DefaultInputPort(node,"in"));

        filterNode.process(new Message(Map.of("temp",30)));

        assertEquals(0,node.count);
    }

    @Test
    void testNoKey(){
        FilterNode filterNode = new FilterNode("f1","temp",30);

        assertDoesNotThrow(() -> filterNode.process(new Message(Map.of("x",1))));
    }
}
