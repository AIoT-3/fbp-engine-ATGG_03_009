package com.fbp.engine.runner;

import java.util.ArrayList;
import java.util.List;

public class ConsumerTest {
    public static void main(String[] args){
        List<String> buffer = new ArrayList<>();

        Thread producer = new Thread(()-> {
            for(int i =0; i<100; i++){
                String msg = "메시지-" +i;
                buffer.add(msg);
                System.out.println("생산: " +msg);
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread consumer = new Thread(()->{
            while (true){
                if(!buffer.isEmpty()){
                    String msg = buffer.remove(0);
                    System.out.println("소비: "+msg);
                }
            }
        });


        producer.start();
        consumer.start();
    }
}
