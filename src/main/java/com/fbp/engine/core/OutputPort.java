package com.fbp.engine.core;

import com.fbp.engine.message.Message;

public interface OutputPort {
    void send(Message message);// 메시지를 커넥션으로 전달
    void connect(Connection connection);//커넥션과 연결 (1:다) 가능
}
