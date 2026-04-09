package com.fbp.engine.core;

import com.fbp.engine.message.Message;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractNode implements Node{
    private final String id;
    protected final Map<String, InputPort> inputPorts = new HashMap<>();
    protected final Map<String, OutputPort> outputPorts = new HashMap<>();

    public AbstractNode(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void initialize() {
        System.out.println(id + " initialize");
    }

    @Override
    public void shutdown() {
        System.out.println(id + " shutdown");
    }

    @Override
    public void process(Message message) throws InterruptedException {
        System.out.println(id + " process " + message);

        onProcess(message);

        System.out.println(id + " done");
    }

    protected abstract void onProcess(Message message) throws InterruptedException;

    protected void addInputPort(String name){
        inputPorts.put(name,new DefaultInputPort(this,name));
    }

    protected void addOutputPort(String name){
        outputPorts.put(name,new DefaultOutputPort(name));
    }

    public InputPort getInputPort(String name){
        return inputPorts.get(name);
    }

    public OutputPort getOutputPort(String name){
        return outputPorts.get(name);
    }

    protected void send(String portName, Message message) throws InterruptedException {
        OutputPort port = outputPorts.get(portName);
        if(port !=null ){
            port.send(message);
        }
    }
}
