package com.fbp.engine.core;

import com.fbp.engine.message.Message;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PrintNode implements Node,Runnable {
    private final String id;
    private final InputPort inputPort = new DefaultInputPort(this,"in");
    private final Connection connection;

    public PrintNode(String id, Connection connection) {
        this.id = id;
        this.connection = connection;
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

    @Override
    public void run() {
        try{
            while (true){
                Message message = connection.poll();
                System.out.println("소비: "+message);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
