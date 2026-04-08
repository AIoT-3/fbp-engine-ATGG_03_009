package com.fbp.engine.runner;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedTest {
    public static void main(String[] args){
        List<String> buffer = new ArrayList<>();
        final Object lock = buffer;

        Thread pro = new Thread(() ->{
            try{
                for(int i =0; i<5; i++){
                    synchronized (lock){
                        String msg = "메시지: "+i;
                        buffer.add(msg);
                        System.out.println("생산: "+msg);

                        lock.notify();
                    }
                    Thread.sleep(100);
                }
                synchronized(lock){
                    buffer.add("END");
                    lock.notify();
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread con = new Thread(() ->{
           try{
               while (true){
                   String msg;

                   synchronized (lock){
                       while (buffer.isEmpty()){
                           lock.wait();
                       }
                       msg = buffer.remove(0);
                   }
                   if("END".equals(msg)){
                       break;
                   }

                   System.out.println("소비: "+msg);

               }

           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
        });

        pro.start();
        con.start();

    }
}
