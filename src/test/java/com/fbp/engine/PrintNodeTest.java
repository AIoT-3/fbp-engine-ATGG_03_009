package com.fbp.engine;

import com.fbp.engine.core.Node;
import com.fbp.engine.core.PrintNode;
import com.fbp.engine.message.Message;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PrintNodeTest {

    //1. getId 반환 테스트
    @Test
    void testGetId() {
        PrintNode node = new PrintNode("printer-1");

        assertEquals("printer-1", node.getId());
    }

    //2. process 정상 동작 테스트
    @Test
    void testProcess() {
        PrintNode node = new PrintNode("printer-1");

        Message message = new Message(Map.of("temperature", 25.5));

        assertDoesNotThrow(() -> node.process(message));
    }

    //3. Node 인터페이스 구현 테스트
    @Test
    void testImplementsNodeInterface() {
        Node node = new PrintNode("printer-1");

        assertNotNull(node);
    }
}