package com.liner.commands;

import com.liner.LinerBot;
import com.liner.messages.KeyPair;
import com.liner.models.User;
import com.liner.ui.UI;
import com.liner.utils.DB;
import com.liner.utils.Icons;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BanListCommand extends Command {
    @Override
    public String getCommand() {
        return "/bans";
    }

    @Override
    public String getDescription() {
        return "Посмотреть список тех кто сидит на бутылке";
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

    private int banned = 0;
    @Override
    public void execute(User sender, User target, String[] arguments) {
        List<User> userList = DB.connect(null).all(User.class);
        StringBuilder stringBuilder = new StringBuilder();
        banned = 0;
        userList.forEach(user -> {
            if (user.isBanned()) {
                stringBuilder.append("\t\t\t\t\uD83D\uDC49 @").append(user.getUsername()).append(" - [").append(user.getId()).append("]").append("\n");
                banned++;
            }
        });
        if (banned == 0)
            stringBuilder.append("\t\t\t\t Никто еще не сидит на бутылке");
        LinerBot.sendText(getChatID(), UI.createResponse(
                "\uD83C\uDF7E",
                "Список любителей бутылки",
                stringBuilder.toString()
        ));
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
