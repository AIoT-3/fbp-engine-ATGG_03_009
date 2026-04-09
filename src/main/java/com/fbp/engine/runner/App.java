package com.fbp.engine.runner;

import com.fbp.engine.core.Connection;
import com.fbp.engine.node.PrintNode;
import com.fbp.engine.node.FilterNode;
import com.fbp.engine.node.GeneratorNode;
import com.fbp.engine.node.TimerNode;


public class App {
    public static void main(String[] args) throws InterruptedException {


        TimerNode timer = new TimerNode("timer", 500); // 0.5초
        FilterNode filter = new FilterNode("filter", "tick", 3);
        PrintNode print = new PrintNode("print");


        Connection c1 = new Connection("c1");
        Connection c2 = new Connection("c2");


        timer.getOutputPort("out").connect(c1);
        c1.setTarget(filter.getInputPort("in"));

        filter.getOutputPort("out").connect(c2);
        c2.setTarget(print.getInputPort("in"));


        timer.initialize();
        filter.initialize();
        print.initialize();

        Thread.sleep(3000);

        timer.shutdown();
        filter.shutdown();
        print.shutdown();
    }
}

