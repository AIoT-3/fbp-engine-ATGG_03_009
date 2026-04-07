package com.fbp.engine.core;

import com.fbp.engine.message.Message;

import javax.sound.sampled.Port;

public class DefaultInputPort implements InputPort {
    private final Node node;
    private final String name;

    public DefaultInputPort(Node node, String name) {
        this.node = node;
        this.name = name;
    }

    @Override
    public void receive(Message message) {
        //메시지를 받으면 해당 노드의 호출
        node.process(message);
    }

    @Override
    public String getName() {
        return name;
    }
}
