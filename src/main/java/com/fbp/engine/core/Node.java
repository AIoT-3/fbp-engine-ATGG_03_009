package com.fbp.engine.core;


import com.fbp.engine.message.Message;

public interface Node {
    String getId();//노드 식별자 (Flow에서 구분)
    void process(Message message) throws InterruptedException;// 메시지 처리
}
