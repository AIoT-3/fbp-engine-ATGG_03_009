package com.fbp.engine.node;

import com.fbp.engine.core.*;
import com.fbp.engine.message.Message;

public class FilterNode implements Node,Runnable{
    private final String id;
    private final String key;
    private final double threshold;
    private final InputPort inputPort;
    private final OutputPort outputPort;
    private Boolean running = true;
    private final Connection input;
    private final Connection output;



    public FilterNode(String id, String key, double threshold,Connection input,Connection output) {
        this.id = id;
        this.key = key;
        this.threshold = threshold;
        this.input = input;
        this.output = output;

        this.inputPort = new DefaultInputPort(this,"in");
        this.outputPort = new DefaultOutputPort("out");
    }

    public InputPort getInputPort() {
        return inputPort;
    }

    public OutputPort getOutputPort() {
        return outputPort;
    }

    @Override
    public void run() {
        Message message = null;
        try {
            while (running){
                message = input.poll();

                String data = (String) message.get("data");

                if(data.contains("3")){
                    output.deliver(message);
                    System.out.println("통과: "+data);
                }else{
                    System.out.println("제거: "+data);
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void process(Message message) throws InterruptedException {

        Object value = message.get(key);
        // 1. key 존재 여부 확인
        if (value == null) {
            return; // 키 없으면 무시
        }

        // 3. 타입 체크 (안전성)
        if (!(value instanceof Number)) {
            return; // 숫자가 아니면 무시
        }

        double numericValue = ((Number) value).doubleValue();

        // 4. threshold 비교
        if (numericValue >= threshold) {
            // 조건 만족 -> 다음 노드로 전달
            outputPort.send(message);
        }
        // else: 아무것도 안함
    }
}
