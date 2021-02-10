package com.liner.games;

import com.liner.LinerBot;
import com.liner.models.User;
import com.pengrad.telegrambot.model.Message;

import java.util.Arrays;
import java.util.List;

public class GameSession1 {
    private User player;
    private Game game;
    private List<String> triggerCommands;
    private boolean isRunning = false;

    public GameSession1(User player, Game game, String[] listen) {
        this.player = player;
        this.game = game;
        this.triggerCommands = Arrays.asList(listen);
        start();
    }

    public void start(){
        isRunning = true;
    }

    public void stop(){
        isRunning = false;
    }

    public void handle(Message message){
        User user = User.fromMessage(message);
        if(player.getId() != user.getId())
            return;
        if(isRunning) {
            if(game.isGameOver()){
                stop();
            }
            if (message.text() != null) {
                for (String entry : message.text().split(" ")) {
                    if (triggerCommands.contains(entry)) {
                        if (user.getId() == player.getId()) {
                            game.play(message, entry);
                        } else {
                            LinerBot.deleteMessage(message);
                        }
                    }
                }
            }
        }
    }
}
