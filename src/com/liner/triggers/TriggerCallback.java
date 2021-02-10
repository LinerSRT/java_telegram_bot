package com.liner.triggers;

import com.pengrad.telegrambot.model.Message;

public interface TriggerCallback {
    boolean triggered(Message message);
}
