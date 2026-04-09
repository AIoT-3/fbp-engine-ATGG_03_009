package com.fbp.engine;

import com.fbp.engine.core.Connection;
import com.fbp.engine.message.Message;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockingQueueTest {
    @Test
    void testDeliverPoll() throws InterruptedException {
        Connection connection = new Connection("c1");

        Message message = new Message(Map.of("data","test"));

        connection.deliver(message);
        Message result = connection.poll();
        assertEquals(message,result);
    }

    @Test
    void testFileOrder() throws InterruptedException {
        Connection connection = new Connection("c1");

        Message message1 = new Message(Map.of("t1","test"));
        Message message2 = new Message(Map.of("t2","test"));
        Message message3 = new Message(Map.of("t3","test"));

        connection.deliver(message1);
        connection.deliver(message2);
        connection.deliver(message3);

        assertEquals(message1,connection.poll());
        assertEquals(message2,connection.poll());
        assertEquals(message3,connection.poll());
    }

    @Test
    void testMultiThread() throws InterruptedException {
        Connection connection = new Connection("c1");
        CountDownLatch latch = new CountDownLatch(1);

        Message message = new Message(Map.of("t1","test"));

        new Thread(() ->{
            try {
                connection.deliver(message);
                latch.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        try {
            assertTrue(latch.await(1, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Message result = connection.poll();
        assertEquals(message,result);
    }

    @Test
    void testWaitPoll(){
        Connection connection = new Connection("c1");
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() ->{
            try {
                Message message = connection.poll();
                if(message != null){
                    latch.countDown();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        try {
            Thread.sleep(300);

            connection.deliver(new Message(Map.of("t1","test")));

            assertTrue(latch.await(1,TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testBufferMaxSize() throws InterruptedException {
        Connection connection = new Connection("c1",2);
        CountDownLatch latch = new CountDownLatch(1);

        connection.deliver(new Message(Map.of("t1","1")));
        connection.deliver(new Message(Map.of("t1","2")));
        new Thread(() -> {
            try {
                connection.deliver(new Message(Map.of("t3","3")));
                latch.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Thread.sleep(300);
        assertEquals(2,connection.getBufferSize());
        assertEquals(1,latch.getCount());

        connection.poll();

        assertTrue(latch.await(1,TimeUnit.SECONDS));
    }

    @Test
    void testBufferSizeCheck() throws InterruptedException {
        Connection connection = new Connection("c1");

        connection.deliver(new Message(Map.of("t1","test")));
        connection.deliver(new Message(Map.of("t2","test")));

        assertEquals(2,connection.getBufferSize());

        connection.poll();

        assertEquals(1,connection.getBufferSize());
    }

}
