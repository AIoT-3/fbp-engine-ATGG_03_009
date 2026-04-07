package com.fbp.engine.runner;

import com.fbp.engine.core.Connection;
import com.fbp.engine.core.PrintNode;
import com.fbp.engine.node.FilterNode;
import com.fbp.engine.node.GeneratorNode;



public class App {
    public static void main(String[] args) {
        // 노드 생성
        GeneratorNode generatorNode = new GeneratorNode("gen-1");
        PrintNode printNode = new PrintNode("printer-1");
        FilterNode filterNode = new FilterNode("filter-1", "temperature", 30);

        // 2. Connection 생성
        Connection conn1 = new Connection("conn-1");
        Connection conn2 = new Connection("conn-2");

        // 3. 연결 구성
        // Generator -> Filter
        generatorNode.getOutputPort().connect(conn1);
        conn1.setTarget(filterNode.getInputPort());

        // Filter -> Print
        filterNode.getOutputPort().connect(conn2);
        conn2.setTarget(printNode.getInputPort());

        System.out.println("===============");
        generatorNode.generate("temperature",25);

        System.out.println("===============");
        generatorNode.generate("temperature",30);
    }
}
