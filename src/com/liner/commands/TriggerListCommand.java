package com.liner.commands;

import com.liner.LinerBot;
import com.liner.Main;
import com.liner.messages.KeyPair;
import com.liner.models.User;
import com.liner.ui.UI;
import com.liner.utils.Icons;

import java.util.concurrent.TimeUnit;

public class TriggerListCommand extends Command {
    @Override
    public String getCommand() {
        return "/triggers";
    }

    @Override
    public String getDescription() {
        return "Показывает список триггеров бота";
    }

    @Override
    public boolean needAdminRight() {
        return false;
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
        StringBuilder stringBuilder = new StringBuilder();
        Main.linerBot.triggerList.forEach(trigger -> {
            String listening;
            switch (trigger.type) {
                case STICKER:
                    listening = "\uD83C\uDFDEСтикер - [" + trigger.trigger + "]";
                    break;
                case DOCUMENT:
                    listening = "\uD83D\uDCC1Документ - [" + trigger.trigger + "]";
                    break;
                case VIDEO:
                    listening = "\uD83D\uDDA5Видео - [" + trigger.trigger + "]";
                    break;
                case AUDIO:
                    listening = "\uD83C\uDFA7Аудио - [" + trigger.trigger + "]";
                    break;
                default:
                    listening = "✉️Сообщение - [" + trigger.trigger + "]";
            }
            stringBuilder.append("\n\uD83D\uDCCC").append("Слушает: \n\t\t\t\t\t\t\t\t").append(listening).append("\n\t\t\t\t\t\t\t\t").append(Icons.TYPE).append("Отвечает: ").append(trigger.response).append("\n");
        });
        LinerBot.sendText(getChatID(), UI.createResponse(
                "\uD83E\uDDF2",
                "Список тригерров:",
                stringBuilder.toString()
        ));
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
