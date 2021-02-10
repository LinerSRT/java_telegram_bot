package com.liner;


import com.liner.commands.*;
import com.liner.games.GameSession1;
import com.liner.messages.MessageHandler;
import com.liner.models.User;
import com.liner.triggers.Trigger;
import com.liner.utils.DB;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Main {
    public static LinerBot linerBot;
    public static TelegramBot bot;
    public static List<MessageHandler<Trigger>> triggerHandlers = new ArrayList<>();
    public static List<GameSession1> gameSession1List = new ArrayList<>();


    public static void main(String[] args) {
        DB.init("data");
        bot = new TelegramBot(LinerBot.TOKEN);
        linerBot = LinerBot.load();
        linerBot.triggerList.forEach(trigger -> triggerHandlers.add(Trigger.create(trigger)));
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                if (update.message() != null) {
                    process(update.message());
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private static void process(Message message) {
        User user = User.fromMessage(message);
        user.processUpdate(message);
        for (Command command : LinerBot.commandList)
            command.listen(message);
        for (MessageHandler<Trigger> textTriggerMessageHandler : triggerHandlers)
            textTriggerMessageHandler.handle(message);
        for(GameSession1 gameSession1 : gameSession1List)
            gameSession1.handle(message);
    }
}
