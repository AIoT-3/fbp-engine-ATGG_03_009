package com.fbp.engine;

import com.fbp.engine.core.Node;
import com.fbp.engine.message.Message;
import org.junit.jupiter.api.Test;

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

    }
}
