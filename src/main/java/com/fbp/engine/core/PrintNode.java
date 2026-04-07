package com.fbp.engine.core;

import com.fbp.engine.message.Message;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PrintNode implements Node {
    private final String id;
    private final InputPort inputPort = new DefaultInputPort(this);

    public PrintNode(String id) {
        this.id = id;
    }

    public InputPort getInputPort() {
        return inputPort;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void process(Message message) {
        System.out.println("[" + id + "] " + message.getPayload());
    }
}
