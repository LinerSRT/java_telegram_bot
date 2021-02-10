package com.liner;


import com.liner.commands.*;
import com.liner.games.GameSession1;
import com.liner.messages.MessageHandler;
import com.liner.models.User;
import com.liner.triggers.Trigger;
import com.liner.utils.Bot;
import com.liner.utils.DB;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Main {
    private static final String TOKEN = "1676073898:AAFjKcTAoBn5Jdhs5_8FHUM0-uGgnDS3zDg";
    public static TelegramBot telegramBot;
    public static List<Command> commandList = new ArrayList<>();
    public static List<Trigger> triggerList = new ArrayList<>();
    public static List<MessageHandler<Trigger>> triggerHandlers = new ArrayList<>();
    public static List<GameSession1> gameSession1List = new ArrayList<>();


    public static void main(String[] args) {
        DB.init("data");
        telegramBot = new TelegramBot(TOKEN);
        Bot.create(telegramBot);
        loadTriggers();
        loadCommands();


        telegramBot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                if (update.message() != null) {
                    process(update.message());
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private static void loadCommands() {
        commandList.add(new DiceGameCommand());
        commandList.add(new GameCommand());
        commandList.add(new VoteCommand());
        commandList.add(new AdminListCommand());
        commandList.add(new StatCommand());
        commandList.add(new CleanCommand());
        commandList.add(new AddTriggerCommand());
        commandList.add(new RemoveTriggerCommand());
        commandList.add(new TriggerListCommand());
        commandList.add(new MakeAdminCommand());
        commandList.add(new RemoveAdminCommand());
        commandList.add(new BanCommand());
        commandList.add(new UnBanCommand());
        commandList.add(new BanListCommand());
        commandList.add(new HelpCommand(commandList));
    }

    @SuppressWarnings("rawtypes")
    public static void loadTriggers() {
        if (triggerList != null && !triggerList.isEmpty())
            triggerList.clear();
        if (triggerHandlers != null && !triggerHandlers.isEmpty())
            triggerHandlers.clear();
        triggerList = DB.connect("triggers").getList(Trigger.class);
        for (Trigger trigger : triggerList) {
            triggerHandlers.add(Trigger.create(trigger));
        }
    }

    private static void process(Message message) {
        User user = User.fromMessage(message);
        user.processUpdate(message);
        for (Command command : commandList)
            command.listen(message);
        for (MessageHandler<Trigger> textTriggerMessageHandler : triggerHandlers)
            textTriggerMessageHandler.handle(message);
        for(GameSession1 gameSession1 : gameSession1List)
            gameSession1.handle(message);
    }
}
