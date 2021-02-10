package com.liner.commands;

import com.liner.LinerBot;
import com.liner.messages.KeyPair;
import com.liner.models.User;
import com.liner.ui.UI;
import com.liner.utils.DB;
import com.liner.utils.Icons;
import com.liner.utils.Other;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RemoveAdminCommand extends Command {
    @Override
    public String getCommand() {
        return "/de_admin";
    }

    @Override
    public String getDescription() {
        return "Удаляет администратора из бота";
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
            if (target.isAdmin()) {
                target.setAdmin(false);
                LinerBot.sendText(getChatID(), UI.createResponse(
                        Icons.CHECK,
                        "Администратор удален",
                        "Теперь @" + target.getUsername() + " лишен прав администратора."
                ));
            } else {
                LinerBot.sendText(getChatID(), UI.createResponse(
                        Icons.CHECK,
                        "Внимание",
                        "@" + target.getUsername() + " не является администратором."
                ));
            }
        } else if (arguments.length > 1) {
            List<User> userList = DB.connect(null).all(User.class);
            User targetUser = null;
            for (User user : userList) {
                String argument = arguments[1];
                if (user.getUsername() != null) {
                    String username = user.getUsername();
                    if (username.equals(argument)) {
                        targetUser = user;
                        break;
                    }
                } else if (Other.isNumeric(argument)) {
                    String userID = String.valueOf(user.getId());
                    if (userID.equals(argument)) {
                        targetUser = user;
                        break;
                    }
                }
            }
            if (targetUser != null) {
                if (targetUser.isAdmin()) {
                    targetUser.setAdmin(false);
                    LinerBot.sendText(getChatID(), UI.createResponse(
                            Icons.CHECK,
                            "Администратор удален",
                            "Теперь @" + targetUser.getUsername() + " лишен прав администратора."
                    ));
                } else {
                    LinerBot.sendText(getChatID(), UI.createResponse(
                            Icons.CHECK,
                            "Внимание",
                            "@" + targetUser.getUsername() + " не является администратором."
                    ));
                }
            } else {
                LinerBot.sendText(getChatID(), UI.createResponse(
                        Icons.FAIL,
                        "Ошибка",
                        "Я не смог понять у кого забрать права администратора."
                ));
            }
        } else {
            LinerBot.sendText(getChatID(), UI.createResponse(
                    Icons.FAIL,
                    "Ошибка",
                    "Я не смог понять у кого забрать права администратора."
            ));
        }
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
