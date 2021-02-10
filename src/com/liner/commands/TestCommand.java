package com.liner.commands;

import com.liner.LinerBot;
import com.liner.RPGGame;
import com.liner.messages.ArgumentExtractor;
import com.liner.messages.KeyPair;
import com.liner.models.User;

public class TestCommand extends Command {
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
        new ArgumentExtractor(getMessage()).extract(getCommandArgumentKeyPairs()).forEach(keyValue -> {
            if (keyValue.key.equals("size")) {
                LinerBot.sendText(getChatID(), new RPGGame((Integer) keyValue.value).drawMap());
            }
        });
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[]{
                new KeyPair("size", Integer.class)
        };
    }
}
