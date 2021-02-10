package com.liner.triggers;

import com.pengrad.telegrambot.model.Message;

public class TriggerEntity implements TriggerCallback {
    public String trigger;
    public String response;
    public Type type;


    public TriggerEntity(String trigger, String response, Type type) {
        this.trigger = trigger;
        this.response = response;
        this.type = type;
    }

    @Override
    public boolean triggered(Message message) {
        return false;
    }



    public enum Type{
        TEXT,
        STICKER,
        DOCUMENT,
        AUDIO,
        VIDEO
    }
}
