package com.liner.games;

public class AbstractGameData {
    public long playerID;
    public boolean isGameFinished;
    public boolean gameCanContinue;

    public AbstractGameData(long playerID, boolean isGameFinished, boolean gameCanContinue) {
        this.playerID = playerID;
        this.isGameFinished = isGameFinished;
        this.gameCanContinue = gameCanContinue;
    }
}
