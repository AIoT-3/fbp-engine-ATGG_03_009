package com.fbp.engine;

import com.fbp.engine.core.Connection;
import com.fbp.engine.core.DefaultInputPort;
import com.fbp.engine.core.Node;
import com.fbp.engine.message.Message;
import com.fbp.engine.node.GeneratorNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GeneratorNodeTest {

    static class TestNode implements Node{
        int count =0;
        @Override
        public String getId() {
            return "test";
        }

        @Override
        public void process(Message message) {
            count++;
        }
    }
    static class CaptureNode implements Node {
        Message last;

        public String getId() { return "c"; }

        public void process(Message m) { last = m; }
    }

    @Test
    void testGenerateMessage() throws InterruptedException {
        GeneratorNode generatorNode = new GeneratorNode("g1");
        TestNode node = new TestNode();

        Connection connection = new Connection("c1");
        connection.setTarget(new DefaultInputPort(node,"in"));

        generatorNode.getOutputPort().connect(connection);

        generatorNode.generate("k","v");

        assertEquals(1,node.count);
    }

    @Test
    void testpayload(){
        GeneratorNode generatorNode = new GeneratorNode("g1");
        CaptureNode node = new CaptureNode();

        Connection connection = new Connection("c1");
        connection.setTarget(new DefaultInputPort(node,"in"));

        generatorNode.getOutputPort().connect(connection);
        generatorNode.generate("k","v");

        assertEquals("v",node.last.get("k"));
    }

    @Test
    void testOutputPort(){
        GeneratorNode generatorNode = new GeneratorNode("g1");

        assertNotNull(generatorNode.getOutputPort());

    }

    @Test
    void testmultiGenerate(){
        GeneratorNode generatorNode = new GeneratorNode("g1");
        TestNode node = new TestNode();

        Connection connection = new Connection("c1");
        connection.setTarget(new DefaultInputPort(node,"in"));

        generatorNode.getOutputPort().connect(connection);

        generatorNode.generate("k",1);
        generatorNode.generate("k",2);
        generatorNode.generate("k",3);


        assertEquals(3,node.count);
    }


}
