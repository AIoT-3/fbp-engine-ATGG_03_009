package com.fbp.engine.core;

import com.fbp.engine.message.Message;

public interface InputPort {
    void receive(Message message) throws InterruptedException;//외부에서 메시지 받음
    String getName();
}
