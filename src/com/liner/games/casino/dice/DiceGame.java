package com.liner.games.casino.dice;

import com.liner.games.AbstractGame;
import com.liner.games.casino.CasinoUser;
import com.liner.models.User;
import com.liner.ui.UI;
import com.liner.utils.Bot;
import com.pengrad.telegrambot.model.Message;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DiceGame extends AbstractGame<DiceData, CasinoUser> {

    public DiceGame(User telegramUser, CasinoUser gameUser) {
        super(DiceData.load(gameUser.id), telegramUser, CasinoUser.load(telegramUser.getId(), CasinoUser.class));
    }

    @Override
    public boolean run(Message message) {
        User user = User.fromMessage(message);
        if (isPlayer(user)) {
            processGame(message);
        }
        return false;
    }

    @Override
    public boolean isPlaying() {
        return gameData.gameCanContinue;
    }

    @Override
    public void startGame(DiceData gameData) {

    }

    @Override
    public void stopGame(DiceData gameData) {

    }

    @Override
    public void saveGame(DiceData gameData) {

    }

    @Override
    public void loadGame(DiceData gameData) {

    }

    @Override
    public void destroy() {

    }

    private void processGame(Message message) {
        String[] args = message.text().split(" ");
        gameData.bet = Integer.parseInt(args[1]);
        gameData.firstNumber = Integer.parseInt(args[2]);
        gameData.secondNumber = Integer.parseInt(args[3]);
        gameData.dice1 = new Random().nextInt(7);
        gameData.dice2 = new Random().nextInt(7);
        gameData.cashWin = 0;
        if (
                (gameData.firstNumber == gameData.dice1 || gameData.firstNumber == gameData.dice2) ||
                (gameData.secondNumber == gameData.dice1 || gameData.secondNumber == gameData.dice2)
        ) {
            gameData.cashWin = gameData.bet * 2;
        }
        if (
                gameData.firstNumber == gameData.dice1 &&
                gameData.secondNumber == gameData.dice2
        ) {
            gameData.cashWin = gameData.bet * 5;
        }
        if(gameData.cashWin == 0){
            gameUser.removeCash(gameData.bet);
        } else {
            gameUser.addCash(gameData.cashWin);
        }
        gameData.gameCanContinue = gameUser.getCash() > gameData.bet;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t\t\t\t\uD83C\uDFB0Ставка: \uD83D\uDCB5").append(gameData.bet).append("\n");
        stringBuilder.append("\t\t\t\t❓Загадано: \uD83C\uDFB2").append(gameData.firstNumber).append(" и \uD83C\uDFB2").append(gameData.secondNumber).append("\n");
        stringBuilder.append("\t\t\t\t\uD83D\uDD18ИИ сыграл: \uD83C\uDFB2").append(gameData.dice1).append(" и \uD83C\uDFB2").append(gameData.dice2).append("\n");
        if(gameData.cashWin == 0){
            stringBuilder.append("\n\n\t\t\t\t\uD83D\uDCA2Ни один куб не совпал, вы проиграли \uD83D\uDCB5").append(gameData.bet).append("\n");
        } else {
            stringBuilder.append("\n\n\t\t\t\t\uD83C\uDF81УРА! Вы выиграли: \uD83D\uDCB5").append(gameData.cashWin).append("\n");
        }
        stringBuilder.append("\t\t\t\t\uD83D\uDCB0Ваш баланс: \uD83D\uDCB5").append(gameUser.getCash());
        Bot.sendText(message.chat().id(), UI.createResponse(
                "\uD83C\uDFB2",
                "Игра <<Кубики>>",
                stringBuilder.toString()
        ), TimeUnit.SECONDS.toMillis(Integer.MAX_VALUE));
        gameData.cashWin = 0;
        gameUser.save();
        gameData.save();
    }
}
