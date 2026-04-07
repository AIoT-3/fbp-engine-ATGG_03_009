package com.fbp.engine.core;

import com.fbp.engine.message.Message;

import java.util.LinkedList;
import java.util.Queue;

public class Connection {
    private final Queue<Message> queue = new LinkedList<>();//메시지 버퍼(비동기 처리)
    private InputPort inputPort;// 메시지를 받을 대상


    //연결 대상 설정
    public void setInputPort(InputPort inputPort) {
        this.inputPort = inputPort;
    }

    //아웃풋에서 호출 메시지를 큐에 저장
    public void send(Message message) {
        queue.offer(message);
    }

    //실제 메시지를 꺼내서 다음 노드로 전달
    public void transfer() {
        Message message = queue.poll();
        if (message != null && inputPort != null) {
            inputPort.receive(message);
        }
    }
}
