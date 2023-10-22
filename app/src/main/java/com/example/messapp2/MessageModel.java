package com.example.messapp2;

public class MessageModel {
    String messageContent;
    String userId;
    String time;

    public MessageModel(String messageContent, String userId, String time) {
        this.messageContent = messageContent;
        this.userId = userId;
        this.time = time;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getUserId() {
        return userId;
    }

    public String getTime(){
        return time;
    }
}
