package com.fbp.engine.core;

import com.fbp.engine.message.Message;

import java.util.LinkedList;
import java.util.Queue;

public class Connection {
    private final String id;
    private final Queue<Message> buffer;//메시지 버퍼(비동기 처리)
    private InputPort target;// 메시지를 받을 대상


    public Connection(String id) {
        this.id = id;
        this.buffer = new LinkedList<>();
    }

    public void deliver(Message message){
        buffer.offer(message);

        if(target != null){
            Message message1 = buffer.poll();

            if(message1 !=null){
                target.receive(message1);
            }
        }
    }

    public void setTarget(InputPort target) {
        this.target = target;
    }

    public int getBufferSize(){
        return buffer.size();
    }

    public String getId() {
        return id;
    }
}
