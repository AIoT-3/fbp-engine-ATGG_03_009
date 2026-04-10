package com.fbp.engine.node;

import com.fbp.engine.core.AbstractNode;
import com.fbp.engine.message.Message;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PrintNode extends AbstractNode {

    public PrintNode(String id) {
        super(id);
        addInputPort("in");
    }

    @Override
    protected void onProcess(Message message) throws InterruptedException {
        // 노드 ID 메시지 내용출력만하고 전달은 안함
        System.out.println(getId() + message);
    }
}
