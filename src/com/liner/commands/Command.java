package com.liner.commands;

import com.liner.messages.ArgumentExtractor;
import com.liner.messages.KeyPair;
import com.liner.messages.KeyValue;
import com.liner.messages.MessageHandler;
import com.liner.models.User;
import com.liner.triggers.CommandTrigger;
import com.liner.triggers.TriggerEntity;
import com.liner.ui.UI;
import com.liner.utils.Bot;
import com.liner.utils.Icons;
import com.pengrad.telegrambot.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public abstract class Command {
    private final CommandTrigger commandTrigger;
    private final MessageHandler<CommandTrigger> listener;
    private User sender;

    public Command() {
        this.commandTrigger = new CommandTrigger(getCommand(), getDescription(), TriggerEntity.Type.TEXT);
        this.listener = new MessageHandler<CommandTrigger>(commandTrigger) {
            @Override
            public void onTriggered(User sender, User target, String[] arguments) {
                Command.this.sender = sender;
                if (needOwnerRight()) {
                    if (sender.isOwner())
                        execute(sender, target, arguments);
                    else
                        Bot.sendText(getChatID(), UI.createResponse(
                                Icons.FUCK,
                                "Узбагойся!",
                                "Данная команда доступна только создателю!"
                        ), TimeUnit.SECONDS.toMillis(60));
                } else if (needAdminRight()) {
                    if (sender.isAdmin() || sender.isOwner())
                        execute(sender, target, arguments);
                    else
                        Bot.sendText(getChatID(), UI.createResponse(
                                Icons.FUCK,
                                "Узбагойся!",
                                "Данная команда доступна только администраторам!"
                        ), TimeUnit.SECONDS.toMillis(60));
                } else if (needNotBeBanned()) {
                    if (!sender.isBanned()) {
                        execute(sender, target, arguments);
                    } else
                        Bot.sendText(getChatID(), UI.createResponse(
                                Icons.FUCK,
                                "Узбагойся!",
                                "Ты не должнен быть забанен, что бы воспользоватся командой!"
                        ), TimeUnit.SECONDS.toMillis(60));
                } else {
                    execute(sender, target, arguments);
                }
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Bot.delete(getMessage(), false);
                    }
                }, 1000);
            }
        };
    }

    public abstract String getCommand();

    public abstract String getDescription();

    public abstract boolean needAdminRight();

    public abstract boolean needOwnerRight();

    public abstract boolean needNotBeBanned();

    public abstract void execute(User sender, User target, String[] arguments);

    public void listen(Message message) {
        listener.handle(message);
    }

    public long getChatID() {
        return sender.getLastMessage().chat().id();
    }

    public Message getMessage() {
        return sender.getLastMessage();
    }

    public abstract KeyPair[] getCommandArgumentKeyPairs();

    public List<KeyValue> getCommandArguments(Message message){
        return new ArgumentExtractor(message).extract(getCommandArgumentKeyPairs());
    }
}
