package com.liner.games;


import com.liner.models.User;
import com.pengrad.telegrambot.model.Message;

public interface Session<T extends AbstractGame<D, U>, D extends AbstractGameData, U extends GameUser> {
    boolean isPlayer(User user);
    boolean run(Message message);
    boolean isPlaying();
    T getGame();
    void startGame(D gameData);
    void stopGame(D gameData);
    void saveGame(D gameData);
    void loadGame(D gameData);
    void destroy();
}
