package com.liner.games.casino;

import com.liner.games.GameUser;

@SuppressWarnings("unused")
public class CasinoUser extends GameUser {
    private int cash = 1000;
    private int totalCashSpent = 0;
    private int totalCashIncome = 0;
    private int totalGamesPlayed = 0;
    private int totalDicePlayed = 0;
    private int totalBlackjackPlayed = 0;
    private int totalSlotsPlayed = 0;
    private int totalLotteryPlayed = 0;
    private int totalDiceCashSpent = 0;
    private int totalBlackjackCashSpent = 0;
    private int totalSlotsCashSpent = 0;
    private int totalLotteryCashSpent = 0;

    public CasinoUser(long id) {
        super(id);
    }

    public void addCash(int amount){
        cash = increase(cash, amount);
    }

    public void removeCash(int amount){
        cash = decrease(cash, amount);
    }

    public void setCash(int cash){
        this.cash = cash;
    }

    public void setTotalCashSpent(int totalCashSpent) {
        this.totalCashSpent = totalCashSpent;
    }

    public void setTotalCashIncome(int totalCashIncome) {
        this.totalCashIncome = totalCashIncome;
    }

    public void setTotalGamesPlayed(int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public void setTotalDicePlayed(int totalDicePlayed) {
        this.totalDicePlayed = totalDicePlayed;
    }

    public void setTotalBlackjackPlayed(int totalBlackjackPlayed) {
        this.totalBlackjackPlayed = totalBlackjackPlayed;
    }

    public void setTotalSlotsPlayed(int totalSlotsPlayed) {
        this.totalSlotsPlayed = totalSlotsPlayed;
    }

    public void setTotalLotteryPlayed(int totalLotteryPlayed) {
        this.totalLotteryPlayed = totalLotteryPlayed;
    }

    public void setTotalDiceCashSpent(int totalDiceCashSpent) {
        this.totalDiceCashSpent = totalDiceCashSpent;
    }

    public void setTotalBlackjackCashSpent(int totalBlackjackCashSpent) {
        this.totalBlackjackCashSpent = totalBlackjackCashSpent;
    }

    public void setTotalSlotsCashSpent(int totalSlotsCashSpent) {
        this.totalSlotsCashSpent = totalSlotsCashSpent;
    }

    public void setTotalLotteryCashSpent(int totalLotteryCashSpent) {
        this.totalLotteryCashSpent = totalLotteryCashSpent;
    }

    public int getCash() {
        return cash;
    }

    public int getTotalCashSpent() {
        return totalCashSpent;
    }

    public int getTotalCashIncome() {
        return totalCashIncome;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public int getTotalDicePlayed() {
        return totalDicePlayed;
    }

    public int getTotalBlackjackPlayed() {
        return totalBlackjackPlayed;
    }

    public int getTotalSlotsPlayed() {
        return totalSlotsPlayed;
    }

    public int getTotalLotteryPlayed() {
        return totalLotteryPlayed;
    }

    public int getTotalDiceCashSpent() {
        return totalDiceCashSpent;
    }

    public int getTotalBlackjackCashSpent() {
        return totalBlackjackCashSpent;
    }

    public int getTotalSlotsCashSpent() {
        return totalSlotsCashSpent;
    }

    public int getTotalLotteryCashSpent() {
        return totalLotteryCashSpent;
    }
}
