package com.liner.commands;

import com.liner.messages.KeyPair;
import com.liner.models.User;
import com.liner.ui.UI;
import com.liner.utils.Bot;
import com.liner.utils.DB;
import com.liner.utils.Icons;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminListCommand extends Command {
    @Override
    public String getCommand() {
        return "/admins";
    }

    @Override
    public String getDescription() {
        return "Посмотреть список администраторов в боте";
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
        List<User> userList = DB.connect(null).all(User.class);
        StringBuilder stringBuilder = new StringBuilder();
        userList.forEach(user -> {
            if (user.isAdmin())
                stringBuilder.append("\t\t\t\t\uD83D\uDC64 @").append(user.getUsername()).append(" - [").append(user.getId()).append("]").append("\n");
        });
        Bot.sendText(getChatID(), UI.createResponse(
                Icons.ORANGE_ROMB,
                "Список администраторов",
                stringBuilder.toString()
        ), TimeUnit.SECONDS.toMillis(60));
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
