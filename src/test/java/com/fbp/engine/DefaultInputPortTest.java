package com.fbp.engine;

import com.fbp.engine.core.DefaultInputPort;
import com.fbp.engine.core.InputPort;
import com.fbp.engine.core.Node;
import com.fbp.engine.message.Message;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultInputPortTest {

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
    void testReceive(){
        TestNode node = new TestNode();
        InputPort port = new DefaultInputPort(node,"in");

        port.receive(new Message(Map.of("a",1)));

        assertEquals(1,node.count);
    }

    @Test
    void portName(){
        InputPort port = new DefaultInputPort(new TestNode(),"in");

        assertEquals("in",port.getName());
    }
}
