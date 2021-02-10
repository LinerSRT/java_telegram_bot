package com.liner.commands;

import com.liner.messages.KeyPair;
import com.liner.models.User;
import com.liner.ui.UI;
import com.liner.utils.Bot;
import com.liner.utils.DB;
import com.liner.utils.Icons;
import com.liner.utils.Other;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class UnBanCommand extends Command {
    @Override
    public String getCommand() {
        return "/un_ban";
    }

    @Override
    public String getDescription() {
        return "Разбанить пользователя";
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
        if (target != null) {
            if (target.isBanned()) {
                target.setBanned(false);
                Bot.sendText(getChatID(), UI.createResponse(
                        Icons.CHECK,
                        "Пользователь разбанен",
                        "Теперь @" + target.getUsername() + " больше не забанен и может писать сообщения!"
                ), TimeUnit.SECONDS.toMillis(60));
            } else {
                Bot.sendText(getChatID(), UI.createResponse(
                        Icons.WARN,
                        "Пользователь уже разбанен",
                        "Вы не можете разбанить @" + target.getUsername() + " так как он не забанен!"
                ), TimeUnit.SECONDS.toMillis(60));
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
                if (targetUser.isBanned()) {
                    targetUser.setBanned(false);
                    Bot.sendText(getChatID(), UI.createResponse(
                            Icons.CHECK,
                            "Пользователь разбанен",
                            "Теперь @" + targetUser.getUsername() + " больше не забанен и может писать сообщения!"
                    ), TimeUnit.SECONDS.toMillis(60));
                } else {
                    Bot.sendText(getChatID(), UI.createResponse(
                            Icons.WARN,
                            "Пользователь уже разбанен",
                            "Вы не можете разбанить @" + targetUser.getUsername() + " так как он не забанен!"
                    ), TimeUnit.SECONDS.toMillis(60));
                }
            } else {
                Bot.sendText(getChatID(), UI.createResponse(
                        Icons.FAIL,
                        "Ошибка!",
                        "Я не смог узнать кого вы хотите разбанить"
                ), TimeUnit.SECONDS.toMillis(60));
            }
        } else {
            Bot.sendText(getChatID(), UI.createResponse(
                    Icons.FAIL,
                    "Ошибка!",
                    "Я не смог узнать кого вы хотите разбанить"
            ), TimeUnit.SECONDS.toMillis(60));
        }
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
