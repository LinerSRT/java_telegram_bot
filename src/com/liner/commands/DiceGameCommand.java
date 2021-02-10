package com.liner.commands;

import com.liner.games.GameUser;
import com.liner.games.casino.CasinoUser;
import com.liner.games.casino.dice.DiceGame;
import com.liner.messages.KeyPair;
import com.liner.models.User;

public class DiceGameCommand extends Command{
    private DiceGame diceGame;
    private CasinoUser gameUser;

    @Override
    public String getCommand() {
        return "/dice";
    }

    @Override
    public String getDescription() {
        return "Играть в кубики";
    }

    @Override
    public boolean needAdminRight() {
        return false;
    }

    @Override
    public boolean needOwnerRight() {
        return true;
    }

    @Override
    public boolean needNotBeBanned() {
        return false;
    }

    @Override
    public void execute(User sender, User target, String[] arguments) {
        gameUser = CasinoUser.load(sender.getId(), CasinoUser.class);
        diceGame = new DiceGame(sender, gameUser);
        diceGame.run(sender.getLastMessage());
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
