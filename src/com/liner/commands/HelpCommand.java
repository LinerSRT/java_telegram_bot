package com.liner.commands;

import com.liner.LinerBot;
import com.liner.messages.KeyPair;
import com.liner.models.User;
import com.liner.ui.UI;
import com.liner.utils.Icons;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HelpCommand extends Command {
    private final List<Command> commandList;

    public HelpCommand(List<Command> commandList) {
        this.commandList = commandList;
    }

    @Override
    public String getCommand() {
        return "/howto";
    }

    @Override
    public String getDescription() {
        return "Показать доступные команды бота";
    }

    @Override
    public boolean needAdminRight() {
        return false;
    }

    @Override
    public boolean needOwnerRight() {
        return false;
    }

    @Override
    public boolean needNotBeBanned() {
        return false;
    }

    @Override
    public void execute(User sender, User target, String[] arguments) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : commandList) {
            String icon;
            if (command.needOwnerRight()) {
                icon = Icons.RED_TRIANGLE;
            } else if (command.needAdminRight()) {
                icon = Icons.ORANGE_ROMB;
            } else {
                icon = Icons.BLUE_ROMB;
            }
            stringBuilder.append(icon).append(command.getCommand()).append(" - ").append(command.getDescription()).append("\n");
        }
        stringBuilder.append("\nОбозначения:\n" + Icons.RED_TRIANGLE + " - доступно создателю\n" + Icons.ORANGE_ROMB + " - доступно администраторам\n" + Icons.BLUE_ROMB + " - доступно всем\n\n");
        LinerBot.sendText(getChatID(), UI.createResponse(
                "⚙\uD83D\uDCC4",
                "Список доступных комманд",
                stringBuilder.toString()
        ));
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
