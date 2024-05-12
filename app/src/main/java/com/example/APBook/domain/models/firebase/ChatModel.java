package com.example.APBook.domain.models.firebase;

import com.google.firebase.Timestamp;

public class ChatModel {
//    Timestamp lastMessageTimestamp;
    String chatId;
    String uid1;
    String uid2;
    Timestamp createdAt;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public ChatModel(String chatId, String uid1, String uid2, Timestamp createdAt) {
//        this.lastMessageTimestamp = lastMessageTimestamp;
        this.chatId = chatId;
        this.uid1 = uid1;
        this.uid2 = uid2;
        this.createdAt = createdAt;
    }




    public String getUid1() {
        return uid1;
    }

    public void setUid1(String uid1) {
        this.uid1 = uid1;
    }

    public String getUid2() {
        return uid2;
    }

    public void setUid2(String uid2) {
        this.uid2 = uid2;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}