package com.liner.commands;

import com.liner.LinerBot;
import com.liner.Main;
import com.liner.messages.KeyPair;
import com.liner.models.User;
import com.liner.triggers.Trigger;
import com.liner.ui.UI;
import com.liner.utils.DB;
import com.liner.utils.Icons;
import com.pengrad.telegrambot.model.Message;

import java.util.concurrent.TimeUnit;

public class RemoveTriggerCommand extends Command {
    @Override
    public String getCommand() {
        return "/untrigger";
    }

    @Override
    public String getDescription() {
        return "Удалить триггер бота";
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
        return false;
    }

    @Override
    public void execute(User sender, User target, String[] arguments) {
        Message message = sender.getLastMessage().replyToMessage();
        if (message != null) {
            if (message.from().isBot() && message.from().username().equalsIgnoreCase("liner_chat_bot")) {
                String text = message.text().split("\uD83D\uDDD1")[0];
                for (Trigger trigger : Main.linerBot.triggerList) {
                    if (text.contains(trigger.response)) {
                        Main.linerBot.triggerList.remove(trigger);
                        LinerBot.save(Main.linerBot);
                        LinerBot.sendText(getChatID(), UI.createResponse(
                                Icons.CHECK,
                                "Триггер удален",
                                "Указанный триггер был успешно удален, бот теперь не будет на на него реагировать"
                        ));
                        return;
                    }
                }
                LinerBot.sendText(getChatID(), UI.createResponse(
                        Icons.FAIL,
                        "Внимание",
                        "Сообщение не содержит триггеров бота!"
                ));
            } else {
                LinerBot.sendText(getChatID(), UI.createResponse(
                        Icons.FAIL,
                        "Ошибка",
                        "Вы должны отправить команду в ответ на сообщение-триггер бота!"
                ));
            }
        } else {
            LinerBot.sendText(getChatID(), UI.createResponse(
                    Icons.FAIL,
                    "Ошибка",
                    "Вы должны отправить команду в ответ на сообщение-триггер бота!"
            ));
        }
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
