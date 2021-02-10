package com.liner.triggers;

import com.liner.messages.MessageHandler;
import com.liner.models.User;
import com.liner.utils.Bot;
import com.pengrad.telegrambot.model.*;

import java.util.concurrent.TimeUnit;

public class Trigger extends TriggerEntity{

    public Trigger(String trigger, String response, Type type) {
        super(trigger, response, type);
    }

    public static MessageHandler<Trigger> create(Trigger trigger){
        return new MessageHandler<Trigger>(new Trigger(trigger.trigger, trigger.response, trigger.type)) {
            @Override
            public void onTriggered(User sender, User target, String[] arguments) {
                if(trigger.response != null)
                    Bot.replyText(
                            sender.getLastMessage().chat().id(),
                            sender.getLastMessage().messageId(),
                            trigger.response,
                            TimeUnit.SECONDS.toMillis(60)
                    );
            }
        };
    }

    @Override
    public boolean triggered(Message message) {
        String test = message.text();
        Sticker sticker = message.sticker();
        Document document = message.document();
        Audio audio = message.audio();
        Video video = message.video();
        if(test != null){
            return trigger.equalsIgnoreCase(test) || test.contains(trigger);
        } else if(sticker != null){
            return trigger.equalsIgnoreCase(sticker.fileUniqueId());
        } else if(audio != null){
            return trigger.equalsIgnoreCase(audio.fileUniqueId());
        } else if(document != null){
            return trigger.equalsIgnoreCase(document.fileUniqueId());
        } else if(video != null){
            return trigger.equalsIgnoreCase(video.fileUniqueId());
        }
        return false;
    }
}
