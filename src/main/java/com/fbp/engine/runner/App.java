package com.fbp.engine.runner;

import com.fbp.engine.core.Connection;
import com.fbp.engine.core.PrintNode;
import com.fbp.engine.node.GeneratorNode;



public class App {
    public static void main(String[] args) {
        // 노드 생성
        GeneratorNode generatorNode = new GeneratorNode("gen-1");
        PrintNode printNode = new PrintNode("printer-1");

        Connection connection = new Connection();

        connection.setInputPort(printNode.getInputPort());
        generatorNode.getOutputPort().connect(connection);

        generatorNode.generate("id",2);

        connection.transfer();
    }
}
