package com.fbp.engine.node;

import com.fbp.engine.core.AbstractNode;
import com.fbp.engine.message.Message;

import java.util.Map;

public class CounterNode extends AbstractNode {
    private int count = 0;

    public CounterNode(String id) {
        super(id);

        addInputPort("in");
        addOutputPort("out");
    }

    @Override
    protected void onProcess(Message message) throws InterruptedException {
        count++;
        Message message1 = new Message(Map.of("count",count));

        send("out",message1);
    }

    @Override
    public void shutdown() {
        System.out.println("[" + getId()+ "]" + "총 처리 메시지 : "+count +"건");
    }
}

