package com.liner.commands;

import com.liner.LinerBot;
import com.liner.Main;
import com.liner.messages.KeyPair;
import com.liner.models.User;
import com.liner.triggers.Trigger;
import com.liner.triggers.TriggerEntity;
import com.liner.ui.UI;
import com.liner.utils.DB;
import com.liner.utils.Icons;
import com.pengrad.telegrambot.model.*;

import java.util.concurrent.TimeUnit;

public class AddTriggerCommand extends Command {
    private String response;


    @Override
    public String getCommand() {
        return "/trigger";
    }

    @Override
    public String getDescription() {
        return "Добавить триггер для бота";
    }

    @Override
    public boolean needAdminRight() {
        return true;
    }

    @Override
    public boolean needOwnerRight() {
        return false;
    }

    @Override
    public boolean needNotBeBanned() {
        return true;
    }

    @Override
    public void execute(User sender, User target, String[] arguments) {
        String message = sender.getLastMessage().text();
        Message repliedMessage = sender.getLastMessage().replyToMessage();
        if(repliedMessage != null && arguments.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < arguments.length; i++) {
                String word = arguments[i];
                if(!word.equals(getCommand())){
                    stringBuilder.append(word).append(" ");
                }
            }
            response = stringBuilder.toString();
            message = repliedMessage.text();
            Sticker sticker = repliedMessage.sticker();
            Document document = repliedMessage.document();
            Audio audio = repliedMessage.audio();
            Video video = repliedMessage.video();
            if (message != null) {
                createTrigger(new Trigger(message, response, TriggerEntity.Type.TEXT), message);
            } else if (sticker != null) {
                createTrigger(new Trigger(sticker.fileUniqueId(), response, TriggerEntity.Type.STICKER), "Стикер: " + sticker.emoji());
            } else if (document != null) {
                createTrigger(new Trigger(document.fileUniqueId(), response, TriggerEntity.Type.DOCUMENT), "\uD83D\uDCC1 Документ: " + document.mimeType());
            } else if (audio != null) {
                createTrigger(new Trigger(audio.fileUniqueId(), response, TriggerEntity.Type.AUDIO), "\uD83C\uDFA7 Аудио: " + audio.title());
            } else if (video != null) {
                createTrigger(new Trigger(video.fileUniqueId(), response, TriggerEntity.Type.VIDEO), "\uD83D\uDDA5 Видео: " + video.fileName());
            }
        } else if(message != null && arguments.length > 2){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 2; i < arguments.length; i++) {
                String word = arguments[i];
                if(!word.equals(getCommand())){
                    stringBuilder.append(word).append(" ");
                }
            }
            response = stringBuilder.toString();
            createTrigger(new Trigger(arguments[1], response, TriggerEntity.Type.TEXT), arguments[1]);
        } else {
            LinerBot.sendText(getChatID(), UI.createResponse(
                    Icons.WARN,
                    "Внимание",
                    "Вы ввели не верные аргументы или не прикрепили сообщение на которое нужно реагировать!"
            ));
        }
    }

    private void createTrigger(Trigger trigger, String listening) {
        LinerBot.sendText(getChatID(), UI.createResponse(
                Icons.CHECK,
                "Отлично, триггер создан",
                "\n\t\t\t" + Icons.LISTEN + "Слушает: " + listening + "\t\t\t" + Icons.TYPE + "Отвечает: " + response
        ));
        Main.linerBot.triggerList.add(trigger);
        LinerBot.save(Main.linerBot);
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
