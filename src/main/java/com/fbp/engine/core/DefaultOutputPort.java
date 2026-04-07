package com.fbp.engine.core;

import com.fbp.engine.message.Message;

import java.util.ArrayList;
import java.util.List;

public class DefaultOutputPort implements OutputPort{
    private final List<Connection> connections = new ArrayList<>();


    @Override
    public void send(Message message) {
        for(Connection con: connections){
            con.send(message);
        }
    }

    @Override
    public void connect(Connection connection) {
        connections.add(connection);
    }
}
