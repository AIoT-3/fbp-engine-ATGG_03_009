package com.fbp.engine.node;

import com.fbp.engine.core.Node;
import com.fbp.engine.message.Message;
import com.fbp.engine.core.OutputPort;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractNode implements Node {
    //추력 포트 여러개
    protected Map<String, OutputPort> outputPortMap = new HashMap<>();

    //공통 전처리
    @Override
    public void process(Message message) {
        onProcess(message);//실제 로직 실행
    }

    // 자식 클래스가 구현해야 하는 실제 처리 로직
    protected abstract void onProcess(Message message);

    //특정 포트를 통해 메시지 전송
    protected void send(String portName, Message message){
        outputPortMap.get(portName).send(message);
    }
}
