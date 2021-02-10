package com.liner.games;

import com.liner.models.User;
import com.liner.utils.Bot;
import com.pengrad.telegrambot.model.Message;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class Game2048 extends Game{
    private int[][] matrix = new int[4][4];
    private boolean isGameOver;
    private int score;
    private String board;
    private Message oldUI;


    public Game2048(User player) {
        super(player);
        this.board = "";
        this.score = 0;
        this.isGameOver = false;
        addNumber();
        addNumber();
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return isGameOver;
    }


    public void addNumber() {
        int numberofzeroes = 0;
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                if (ints[j] == 0) {
                    numberofzeroes++;
                }
            }
        }
        if (numberofzeroes == 0) {
            isGameOver = true;
        } else {
            int random = (int) (Math.random() * numberofzeroes + 1);
            int reached = 0;
            for (int m = 0; m < matrix.length; m++) {
                for (int n = 0; n < matrix.length; n++) {
                    if (matrix[m][n] == 0) {
                        reached++;
                        if (reached == random) {
                            matrix[m][n] = 2;
                            break;
                        }
                    }
                }
            }
        }
    }

    public void moveDown() {
        slide();
        combine();
        slide();
        rotate(360);
        addNumber();
    }

    public void moveUp() {
        rotate(180);
        slide();
        combine();
        slide();
        rotate(180);
        addNumber();
    }

    public void moveRight() {
        rotate(90);
        slide();
        combine();
        slide();
        rotate(270);
        addNumber();
    }

    public void moveLeft() {
        rotate(270);
        slide();
        combine();
        slide();
        rotate(90);
        addNumber();
    }

    public void slide() {
        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i + 1][j] == 0) {
                    matrix[i + 1][j] = matrix[i][j];
                    matrix[i][j] = 0;
                }
                if (matrix[i + 1][j] == matrix[i][j]) {
                    combine();
                }
            }
        }
    }

    public void combine() {
        for (int i = matrix.length - 1; i > 0; i--) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == matrix[i - 1][j]) {
                    matrix[i][j] += matrix[i - 1][j];
                    score += matrix[i - 1][j];
                    matrix[i - 1][j] = 0;
                }
            }
        }
    }

    public void rotate(int angle) {
        for (int i = 0; i < angle / 90; i++) {
            matrix = transpose(matrix);
            matrix = flip(matrix);
        }
    }

    public int[][] transpose(int[][] arr) {
        int[][] result = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                result[j][i] = arr[i][j];
            }
        }
        return result;
    }

    public int[][] flip(int[][] arr) {
        int[][] result = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                result[i][(arr.length - 1) - j] = arr[i][j];
            }
        }
        return result;
    }

    @Override
    public String getUI() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83C\uDFAE Игра: 2048\n");
        stringBuilder.append("️\t\t\t\t⚙Сессия игрока: @"+player.getUsername()+"\n");
        stringBuilder.append("️\t\t\t\t\uD83E\uDDEEСчет: "+getScore()+"\n");
        for (int[] tile : matrix) {
            stringBuilder.append("\t\t\t\t ️\n");
            for (int j = 0; j < matrix.length; j++) {
                stringBuilder.append("\t\t\t\t").append(getColored(tile[j]));
            }
        }
        return stringBuilder.toString();
    }

    private String getColored(int value){
        //⬜️🟨🟧🟫🟩🟦🟪🟥⬛️
        if(value == 0)
            return "⬜️"+value+"⬜️";
        else if(value >= 2 && value < 8)
            return "\uD83D\uDFE8"+value+"\uD83D\uDFE8";
        else if(value >= 8 && value < 32)
            return "\uD83D\uDFE7"+value+"\uD83D\uDFE7";
        else if(value >= 32 && value < 128)
            return "\uD83D\uDFEB"+value+"\uD83D\uDFEB";
        else if(value >= 128 && value < 512)
            return "\uD83D\uDFE9"+value+"\uD83D\uDFE9";
        else if(value >= 512 && value < 2048)
            return "\uD83D\uDFE6"+value+"\uD83D\uDFE6";
        else if(value >= 2048 && value < 8192)
            return "\uD83D\uDFEA"+value+"\uD83D\uDFEA";
        else if(value >= 8192 && value < 16384)
            return "\uD83D\uDFE5"+value+"\uD83D\uDFE5";
        else
            return "⬛️"+value+"⬛️";
    }

    @Override
    public void play(Message message, String command) {
        switch (command){
            case "up":
                moveUp();
                break;
            case "down":
                moveDown();
                break;
            case "left":
                moveLeft();
                break;
            case "right":
                moveRight();
                break;
        }
        if(oldUI != null)
            Bot.delete(oldUI, false);
        oldUI = Bot.sendText(message.chat().id(), getUI(), TimeUnit.MINUTES.toMillis(5));
        Bot.delete(message, false);
    }

    @Override
    public void start() {
        oldUI = Bot.sendText(player.getLastMessage().chat().id(), getUI(), TimeUnit.MINUTES.toMillis(5));
        Bot.delete(player.getLastMessage(), false);
    }
}
