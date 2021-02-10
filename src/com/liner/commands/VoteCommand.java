package com.liner.commands;

import com.liner.LinerBot;
import com.liner.messages.KeyPair;
import com.liner.models.User;
import com.liner.utils.DB;
import com.liner.utils.Icons;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class VoteCommand extends Command{
    private String[] vote_types = new String[]{
            "add_trigger",
            "remove_trigger",
            "ban",
            "unban"
    };
    @Override
    public String getCommand() {
        return "/vote";
    }

    @Override
    public String getDescription() {
        return "Начать голосование для выполнения команды, если у вас нет прав на нее";
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
        if(arguments.length < 2 || target == null){
            StringBuilder stringBuilder = new StringBuilder();
            if(!Arrays.asList(vote_types).contains(arguments.length == 1?arguments[0]:arguments[1]))
                stringBuilder.append(Icons.WARN + "Команда не содержит типа голосования!\n");
            if(target == null)
                stringBuilder.append(Icons.WARN + "Команда не содержит пересланного сообщения!\n");
            stringBuilder.append("\n\uD83D\uDCCAДоступные типы голосования: ");
            for (String action : vote_types) {
                stringBuilder.append("\n\t\t\t\uD83D\uDCCC").append(action);
            }
            LinerBot.sendText(getChatID(), stringBuilder.toString());
        } else {
            int voteID = Math.abs(new Random().nextInt());
            String voteType = arguments[1];
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\nГолосование \uD83D\uDCCE"+voteID+" начато! [FAKE]");
            switch (voteType){
                case "add_trigger":
                    stringBuilder.append("\n\n\t\t\t\t➡️Голосуем за добавление нового триггера\n");
                    break;
                case "remove_trigger":
                    stringBuilder.append("\n\n\t\t\t\t➡️Голосуем за удаление триггера\n");
                    break;
                case "ban":
                    stringBuilder.append("\n\n\t\t\t\t➡️Голосуем за бан пользователю @"+target.getUsername()+"\n");
                    break;
                case "unban":
                    stringBuilder.append("\n\n\t\t\t\t➡️Голосуем за разбан пользователюя @"+target.getUsername()+"\n");
                    break;
            }
            List<User> userList = DB.connect(null).all(User.class);
            stringBuilder.append("\n\uD83D\uDCC8Необходимо как минимум "+(userList.size()/2+1)+" голосов для рассмотрения администраторами");
            stringBuilder.append("\n\uD83D\uDCC8Необходимо "+userList.size()+" голосов для решения без участия администраторов");


            LinerBot.sendText(getChatID(), stringBuilder.toString());
        }
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
