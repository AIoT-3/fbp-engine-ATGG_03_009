package com.fbp.engine.runner;

import com.fbp.engine.core.Connection;
import com.fbp.engine.core.PrintNode;
import com.fbp.engine.node.FilterNode;
import com.fbp.engine.node.GeneratorNode;



public class App {
    public static void main(String[] args) {

        Connection connection = new Connection("c1");
        Connection connection1 = new Connection("c2");

        Thread t1 = new Thread(new GeneratorNode("gen",connection));
        Thread t2 = new Thread(new PrintNode("print",connection1));
        Thread t3 = new Thread(new FilterNode("filter","f",30,connection,connection1));

        t1.start();
        t2.start();
        t3.start();
    }
}
