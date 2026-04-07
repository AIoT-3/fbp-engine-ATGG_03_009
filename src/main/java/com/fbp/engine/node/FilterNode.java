package com.fbp.engine.node;

import com.fbp.engine.core.*;
import com.fbp.engine.message.Message;

public class FilterNode implements Node {
    private final String id;
    private final String key;
    private final double threshold;
    private final InputPort inputPort;
    private final OutputPort outputPort;

    public FilterNode(String id, String key, double threshold) {
        this.id = id;
        this.key = key;
        this.threshold = threshold;

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
    public String getId() {
        return id;
    }

    @Override
    public void process(Message message) {
        // 1. key 존재 여부 확인
        if (!message.hasKey(key)) {
            return; // 키 없으면 무시
        }

        // 2. 값 꺼내기 (제네릭 사용)
        Object value = message.get(key);

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
