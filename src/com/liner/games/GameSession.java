package com.liner.games;

import com.liner.models.User;
import com.pengrad.telegrambot.model.Message;

public class GameSession<T extends AbstractGame<D, U>, U extends GameUser, D extends AbstractGameData> implements Session<T, D, U>{
    T game;
    D gameData;

    public GameSession(T game, D gameData) {
        this.game = game;
        this.gameData = gameData;
    }

    @Override
    public boolean isPlayer(User user) {
        return game.isPlayer(user);
    }

    @Override
    public boolean run(Message message) {
        return game.run(message);
    }

    @Override
    public boolean isPlaying() {
        return game.isPlaying();
    }

    @Override
    public T getGame() {
        return game;
    }

    @Override
    public void startGame(D gameData) {
        game.startGame(gameData);
    }

    @Override
    public void stopGame(D gameData) {
        game.stopGame(gameData);
    }

    @Override
    public void saveGame(D gameData) {

        game.saveGame(gameData);
    }

    @Override
    public void loadGame(D gameData) {
        game.loadGame(gameData);
    }

    @Override
    public void destroy() {
        game.destroy();
        //TODO Remove session from memory
    }
}
