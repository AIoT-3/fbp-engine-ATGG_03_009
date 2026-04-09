package com.fbp.engine.node;

import com.fbp.engine.core.*;
import com.fbp.engine.message.Message;

public class FilterNode extends AbstractNode {
    private final String key;
    private final double threshold;

    public FilterNode(String id, String key, double threshold) {
        super(id);
        this.key = key;
        this.threshold = threshold;
        addInputPort("in");
        addOutputPort("out");
    }

    @Override
    protected void onProcess(Message message) throws InterruptedException {
        double num =  message.get(key);
        if(num >= threshold){
            System.out.println("생성: "+ getId() + "/" + num);
            send("out",message);
        }else{
            System.out.println("제거: "+ getId() + "/" +num);
        }
    }
}