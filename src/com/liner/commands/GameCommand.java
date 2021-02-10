package com.liner.commands;

import com.liner.Main;
import com.liner.games.Game2048;
import com.liner.games.GameSession1;
import com.liner.messages.KeyPair;
import com.liner.models.User;

import java.util.Arrays;

public class GameCommand extends Command {
    private Game2048 game2048;
    private String[] gamesTypes = new String[]{
            "2048"
    };

    @Override
    public String getCommand() {
        return "/play";
    }

    @Override
    public String getDescription() {
        return "Начать играть в игры с ботом";
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
        if (Arrays.asList(arguments).contains("2048")) {
            game2048 = new Game2048(sender);
            GameSession1 gameSession1 = new GameSession1(sender,game2048, new String[]{
                    "up",
                    "down",
                    "left",
                    "right"
            });
            Main.gameSession1List.remove(gameSession1);
            Main.gameSession1List.add(gameSession1);
            game2048.start();
        }
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
