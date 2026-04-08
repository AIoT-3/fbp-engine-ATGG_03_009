package com.fbp.engine.node;

import com.fbp.engine.core.Connection;
import com.fbp.engine.core.DefaultOutputPort;
import com.fbp.engine.core.Node;
import com.fbp.engine.core.OutputPort;
import com.fbp.engine.message.Message;

import java.util.Map;

public class GeneratorNode implements Runnable,Node {
    private final String id;
    private final OutputPort outputPort = new DefaultOutputPort("out");
    private final Connection connection;

    public GeneratorNode(String id, Connection connection) {
        this.id = id;
        this.connection = connection;
    }

    public OutputPort getOutputPort() {
        return outputPort;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void process(Message message) throws InterruptedException {
        outputPort.send(message);
    }

    public void generate(String key, Object value) throws InterruptedException {
        Message message = new Message(Map.of(key,value));
        outputPort.send(message);
    }

    @Override
    public void run() {
        try{
            for(int i =0; i<= 5;i++){
                Message message = new Message(Map.of("data","메시지: "+i));
                connection.deliver(message);
                System.out.println("생산: "+message);
                Thread.sleep(1000);

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
