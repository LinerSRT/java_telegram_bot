package com.liner.games;

import com.liner.models.User;
import com.pengrad.telegrambot.model.Message;

import java.util.List;

public abstract class Game {
    public User player;

    public Game(User player) {
        this.player = player;
    }

    private List<String> listenCommands;
    public abstract String getUI();
    public abstract void play(Message message, String command);
    public abstract boolean isGameOver();
    public abstract void start();
}
