package com.liner.commands;

import com.liner.messages.KeyPair;
import com.liner.models.User;
import com.liner.ui.UI;
import com.liner.utils.Bot;
import com.liner.utils.Icons;
import com.liner.utils.Other;

public class TestCommand extends Command{
    @Override
    public String getCommand() {
        return "/test";
    }

    @Override
    public String getDescription() {
        return "null";
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
        return false;
    }

    @Override
    public void execute(User sender, User target, String[] arguments) {
        String command = Other.fromArray(arguments, 1);
        Bot.sendText(getChatID(), UI.createResponse(Icons.CHECK, command.split("=")[0], command.split("=")[1]));
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
