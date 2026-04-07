package com.fbp.engine.core;

import com.fbp.engine.message.Message;

import java.util.ArrayList;
import java.util.List;

public class DefaultOutputPort implements OutputPort{
    private final List<Connection> connections = new ArrayList<>();
    private final String name;

    public DefaultOutputPort(String name) {
        this.name = name;
    }

    @Override
    public void send(Message message) {
        for(Connection con: connections){
            con.deliver(message);
        }
    }

    @Override
    public void connect(Connection connection) {
        connections.add(connection);
    }

    @Override
    public String getName() {
        return name;
    }
}
