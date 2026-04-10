package com.fbp.engine.node;

import com.fbp.engine.core.AbstractNode;
import com.fbp.engine.message.Message;

public class SplitNode extends AbstractNode {

    private final String key;
    private final double threshold;

    public SplitNode(String id, String key, double threshold) {
        super(id);
        this.key = key;
        this.threshold = threshold;

        addInputPort("in");
        addOutputPort("match");
        addInputPort("mismatch");
    }

    @Override
    protected void onProcess(Message message) throws InterruptedException {
         double msg = message.get(key);
         if(msg >= threshold){
             send("match",message);
         }else{
             send("mismatch", message);
         }
    }
}
