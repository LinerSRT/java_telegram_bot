package com.liner.games;

import com.liner.models.User;
import com.pengrad.telegrambot.model.Message;

public abstract class AbstractGame<D extends AbstractGameData, U extends GameUser>{
    public D gameData;
    public User telegramUser;
    public U gameUser;

    public AbstractGame(D gameData, User telegramUser, U gameUser) {
        this.gameData = gameData;
        this.telegramUser = telegramUser;
        this.gameUser = gameUser;
    }

    public abstract boolean run(Message message);
    public boolean isPlayer(User user){
        return user.getId() == telegramUser.getId();
    }
    public abstract boolean isPlaying();
    public abstract void startGame(D gameData);
    public abstract void stopGame(D gameData);
    public abstract void saveGame(D gameData);
    public abstract void loadGame(D gameData);
    public abstract void destroy();
}
