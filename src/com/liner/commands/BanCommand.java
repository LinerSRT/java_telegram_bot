package com.liner.commands;

import com.liner.messages.KeyPair;
import com.liner.models.User;
import com.liner.ui.UI;
import com.liner.utils.Bot;
import com.liner.utils.Icons;

import java.util.concurrent.TimeUnit;

public class BanCommand extends Command{
    @Override
    public String getCommand() {
        return "/ban_him";
    }

    @Override
    public String getDescription() {
        return "Забанить пользователя";
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
        if(target != null){
            if(!target.isBanned()){
                target.setBanned(true);
                Bot.sendText(getChatID(), UI.createResponse(
                        Icons.CHECK,
                        "Пользователь забанен",
                        "Теперь @"+target.getUsername()+" забанен, его сообщения будут автоматически удаляются, пока он не будет разбанен!"
                ), TimeUnit.SECONDS.toMillis(60));
            } else {
                Bot.sendText(getChatID(), UI.createResponse(
                        Icons.WARN,
                        "Увы, еще одна не влезет",
                        "У @"+target.getUsername()+" уже есть бутылка в жопе!"
                ), TimeUnit.SECONDS.toMillis(60));
            }
        } else {
            Bot.sendText(getChatID(), UI.createResponse(
                    Icons.FAIL,
                    "Еще одна бутылка миновала чью-то сраку",
                    "Я не смог понять кого на бутылку посадить"
            ), TimeUnit.SECONDS.toMillis(60));
        }
    }
    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
