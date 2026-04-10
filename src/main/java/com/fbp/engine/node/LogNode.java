package com.fbp.engine.node;

import com.fbp.engine.core.AbstractNode;
import com.fbp.engine.message.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogNode extends AbstractNode {
    public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");

    public LogNode(String id) {
        super(id);
        addInputPort("in");
        addOutputPort("out");
    }

    @Override
    protected void onProcess(Message message) throws InterruptedException {
        // 메시지 수신
        // 현재 시간 로그 출력
        // send로 다음 노드에 전달
        String time = LocalDateTime.now().format(formatter);

        System.out.println("시간: " + time);
        send("out",message);
    }
}
