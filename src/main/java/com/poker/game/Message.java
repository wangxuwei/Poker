package com.poker.game;


import java.util.Date;

public class Message {
    final private long time;
    final private String message;

    public Message(String message) {
        this.message = message;
        this.time = new Date().getTime();
    }

    public long getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}
