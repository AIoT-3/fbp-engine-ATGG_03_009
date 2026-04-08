package com.fbp.engine.core;

import com.fbp.engine.message.Message;

import java.util.concurrent.LinkedBlockingQueue;

public class Connection {
    private final String id;
    private final LinkedBlockingQueue<Message> buffer;//메시지 버퍼(비동기 처리)
    private InputPort target;// 메시지를 받을 대상


    public Connection(String id) {
        this(id,100);
    }

    public Connection(String id, int cap) {
        this.id = id;
        this.buffer = new LinkedBlockingQueue<>(cap);
    }

    public void deliver(Message message) throws InterruptedException{
        buffer.put(message);
    }

    public Message poll() throws InterruptedException{
        return buffer.take();
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
