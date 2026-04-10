package com.fbp.engine.node;

import com.fbp.engine.core.AbstractNode;
import com.fbp.engine.message.Message;

import java.util.function.Function;

public class TransformNode extends AbstractNode {
    public Function<Message, Message> transformer;

    public TransformNode(String id, Function<Message, Message> transformer) {
        super(id);
        this.transformer = transformer;

        addInputPort("in");
        addOutputPort("out");
    }

    @Override
    protected void onProcess(Message message) throws InterruptedException {
        if (transformer.apply(message) != null){
            send("out", message);
        }
    }

    TransformNode transformNode = new TransformNode("transform",msg ->{
        Double f = (Double) msg.get("tempF");

        double c = (f-32) *5 /9;
        return msg.withEntry("tempC",c);
    });
}
