package com.fbp.engine.runner;

import com.fbp.engine.core.Connection;
import com.fbp.engine.core.Flow;
import com.fbp.engine.node.*;


public class App {
    public static void main(String[] args) throws InterruptedException {

/*
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
        */

        Flow flow = new Flow("pipeline");

        flow.addNode(new TimerNode("timer", 1000))
                .addNode(new LogNode("log"))
                .addNode(new FilterNode("filter", "tick", 3))
                .addNode(new PrintNode("print"))

                .connect("timer", "out", "log", "in")
                .connect("log", "out", "filter", "in")
                .connect("filter", "out", "print", "in");

        flow.initialize();

        Thread.sleep(7000);

        flow.shutdown();
    }
}

