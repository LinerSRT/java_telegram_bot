package com.liner.messages;

import com.liner.models.User;
import com.liner.triggers.TriggerEntity;
import com.pengrad.telegrambot.model.Message;

public abstract class MessageHandler<T extends TriggerEntity> implements Listener<T>{
    private T trigger;

    public MessageHandler(T trigger) {
        this.trigger = trigger;
    }

    public void handle(Message message){
        User sender = User.fromTelegramUser(message.from());
        User target = message.replyToMessage() == null ? null:User.fromTelegramUser(message.replyToMessage().from());
        String[] arguments = message.text() == null ? null:message.text().split(" ");
        if(arguments != null && target == null){
            for(String argument:arguments){
                if(argument.contains("@")){
                    target = User.fromUsername("@"+argument);
                    if(target != null)
                        break;
                }
            }
        }
        if(trigger.triggered(message)){
            onTriggered(sender, target, arguments);
        }
    }

    @Override
    public T getTrigger() {
        return trigger;
    }
}
