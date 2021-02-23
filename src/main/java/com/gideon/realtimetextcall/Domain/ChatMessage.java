package com.gideon.realtimetextcall.Domain;

import java.sql.Time;

public class ChatMessage {
    private long time;
    private String sender;
    private String content;
    public ChatMessage(){}

    public ChatMessage(String sender, String content, long time){
        this.sender = sender;
        this.content = content;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
