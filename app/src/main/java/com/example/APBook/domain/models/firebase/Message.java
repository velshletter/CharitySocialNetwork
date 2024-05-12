package com.example.APBook.domain.models.firebase;


import com.google.firebase.Timestamp;

public class Message {
    private String messageId;
    private String text;
    private Timestamp timestamp;
    private String senderId;

    public Message(String text, Timestamp timestamp, String senderId, String messageId) {
        this.text = text;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.messageId = messageId;
    }

    public Message(String text, Timestamp timestamp, String senderId) {
        this.text = text;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
