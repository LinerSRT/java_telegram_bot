package com.liner.commands;

import com.liner.messages.KeyPair;
import com.liner.models.User;
import com.liner.ui.UI;
import com.liner.utils.Bot;
import com.liner.utils.Icons;

import java.util.concurrent.TimeUnit;

public class MakeAdminCommand extends Command {
    @Override
    public String getCommand() {
        return "/make_admin";
    }

    @Override
    public String getDescription() {
        return "Добавляет нового администратора в бота";
    }

    @Override
    public boolean needAdminRight() {
        return false;
    }

    @Override
    public boolean needOwnerRight() {
        return true;
    }

    @Override
    public boolean needNotBeBanned() {
        return true;
    }

    @Override
    public void execute(User sender, User target, String[] arguments) {
        if (target != null) {
            if (!target.isAdmin()) {
                target.setAdmin(true);
                Bot.sendText(getChatID(), UI.createResponse(
                        Icons.CHECK,
                        "Администратор добавлен",
                        "Теперь @" + target.getUsername() + " администратор."
                ), TimeUnit.SECONDS.toMillis(60));
            } else {
                Bot.sendText(getChatID(), UI.createResponse(
                        Icons.WARN,
                        "Внимание",
                        "@" + target.getUsername() + " уже является администратором!"
                ), TimeUnit.SECONDS.toMillis(60));
            }
        } else {
            Bot.sendText(getChatID(), UI.createResponse(
                    Icons.WARN,
                    "Внимание",
                    "Я не смог понять кому выдать права администратора."
            ), TimeUnit.SECONDS.toMillis(60));
        }
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
