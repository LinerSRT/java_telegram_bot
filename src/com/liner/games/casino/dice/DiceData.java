package com.liner.games.casino.dice;

import com.liner.games.AbstractGameData;
import com.liner.utils.DB;

public class DiceData extends AbstractGameData {
    int bet;
    int firstNumber;
    int secondNumber;
    int dice1;
    int dice2;
    int cashWin;

    public DiceData(long playerID, boolean isGameFinished, boolean gameCanContinue, int bet, int firstNumber, int secondNumber, int dice1, int dice2, int cashWin) {
        super(playerID, isGameFinished, gameCanContinue);
        this.bet = bet;
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.cashWin = cashWin;
    }

    public static DiceData load(long playerID){
        if(DB.exists("dice_data_" + playerID)){
            return DB.connect("dice_data_" + playerID).get(DiceData.class);
        } else {
            DiceData diceData = new DiceData(
                    playerID,
                    false,
                    true,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0
            );
            diceData.save();
            return diceData;
        }
    }

    public void save() {
        DB.connect("dice_data_" + playerID).set(this);
    }
}
