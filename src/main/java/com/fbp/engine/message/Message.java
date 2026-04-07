package com.fbp.engine.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Message {
    private final String id;
    private final Map<String, Object> payload;
    private final long timestamp;

   public Message(Map<String, Object>payload){
       this.id = UUID.randomUUID().toString();

       //불변 객체로 만들기
       this.payload = Collections.unmodifiableMap(new HashMap<>(payload));

       this.timestamp = System.currentTimeMillis();
   }

   public String getId(){
       return id;
   }

   public Map<String, Object> getPayload(){
       return payload;
   }

   public long getTimestamp(){
       return timestamp;
   }



   @SuppressWarnings("unchecked")
    public <T> T get(String key){
       return (T) payload.get(key);
   }


   //키존재 여부
   public boolean hasKey(String key){
       return payload.containsKey(key);
   }

   // 새로운 키 value 추가된 메시지 반혼
   public Message withEntry(String key, Object value){
        Map<String, Object> newPayload = new HashMap<>(payload);
        newPayload.put(key,value);
        return new Message(newPayload);
    }

   //특정키 제거된 메시지 반환
   public Message withoutKey(String key){
       Map<String, Object> newPayload = new HashMap<>(payload);
       newPayload.remove(key);
       return new Message(newPayload);
   }

   @Override
   public String toString(){
       return payload.toString();
   }
}
