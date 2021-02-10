package com.liner.commands;

import com.liner.LinerBot;
import com.liner.Main;
import com.liner.messages.KeyPair;
import com.liner.models.User;
import com.liner.ui.UI;
import com.liner.utils.Icons;

import java.util.concurrent.TimeUnit;

public class CleanCommand extends Command{
    @Override
    public String getCommand() {
        return "/clean";
    }

    @Override
    public String getDescription() {
        return "Очистить стэк сообщений бота (например чат захламлен сообщениями от бота)";
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
        int count = LinerBot.messageStackSize();
        LinerBot.clearMessageStack();
        LinerBot.sendText(getChatID(), UI.createResponse(
                Icons.CHECK,
                "Готово",
                "Очистка завершена! Очищено: "+count+" шт."
        ));
    }
    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
