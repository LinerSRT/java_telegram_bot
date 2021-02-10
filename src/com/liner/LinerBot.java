package com.liner;

import com.liner.commands.*;
import com.liner.triggers.Trigger;
import com.liner.ui.UI;
import com.liner.utils.DB;
import com.liner.utils.Icons;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class LinerBot {
    private static LinerBot linerBot;
    public static String TOKEN = "1676073898:AAFjKcTAoBn5Jdhs5_8FHUM0-uGgnDS3zDg";
    public int deleteMessageTimeout; //Delete message after this value, in seconds
    public int allowedStickersTimeout; // Allow send only one sticker per this timeout, in seconds
    public int messageStackLimit; //Limit saving messages to 50, f
    //or exclude memory overlapping and decrease working delay
    public static List<Command> commandList;
    public List<Trigger> triggerList;
    public List<Message> messageStack;


    public LinerBot() {
        this(5,
                10,
                50,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public LinerBot(int deleteMessageTimeout, int allowedStickersTimeout, int messageStackLimit, List<Command> commandList, List<Trigger> triggerList, List<Message> messageStack) {
        this.deleteMessageTimeout = deleteMessageTimeout;
        this.allowedStickersTimeout = allowedStickersTimeout;
        linerBot = this;
        linerBot.messageStackLimit = messageStackLimit;
        this.commandList = commandList;
        this.triggerList = triggerList;
        linerBot.messageStack = messageStack;
        commandList.add(new TestCommand());
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

    public static void toMessageStack(Message message) {
        if (!linerBot.messageStack.contains(message) && messageStackSize() <= linerBot.messageStackLimit) {
            linerBot.messageStack.add(message);
            save(linerBot);
        } else {
            linerBot.messageStack.remove(0);
            toMessageStack(message);
        }
    }

    public static void removeFromMessageStack(Message message) {
        if (linerBot.messageStack.remove(message))
            save(linerBot);
    }

    public static int messageStackSize() {
        return linerBot.messageStack.size();
    }

    public static void clearMessageStack() {
        linerBot.messageStack.forEach(LinerBot::removeFromMessageStack);
    }

    private static Message addThenDeleteMessage() {
        Message message = linerBot.messageStack.get(messageStackSize() - 1);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                deleteMessage(message);
            }
        }, TimeUnit.SECONDS.toMillis(linerBot.deleteMessageTimeout));
        return message;
    }

    public static void deleteMessage(Message message){
        removeFromMessageStack(message);
        Main.bot.execute(new DeleteMessage(message.chat().id(), message.messageId()));
    }

    public static Message replyText(long chatID, long messageID, String text) {
        text = UI.addBottom(text, getTimeToDeleteTextBottom(linerBot.deleteMessageTimeout));
        toMessageStack(
                Main.bot.execute(
                        new SendMessage(chatID, text)
                                .parseMode(ParseMode.Markdown)
                                .disableWebPagePreview(true)
                                .disableNotification(true)
                                .replyToMessageId(Math.toIntExact(messageID))
                ).message()
        );
        return addThenDeleteMessage();
    }


    public static Message sendText(long chatID, String text) {
        text = UI.addBottom(text, getTimeToDeleteTextBottom(linerBot.deleteMessageTimeout));
        toMessageStack(
                Main.bot.execute(
                        new SendMessage(
                                chatID,
                                text
                        ).disableNotification(true)
                ).message()
        );
        return addThenDeleteMessage();
    }


    public static Message sendPhoto(long chatID, String text, byte[] bytes) {
        text = UI.addBottom(text, getTimeToDeleteTextBottom(linerBot.deleteMessageTimeout));
        toMessageStack(
                Main.bot.execute(
                        new SendPhoto(
                                chatID,
                                bytes
                        ).caption(text).disableNotification(true)
                ).message()
        );
        return addThenDeleteMessage();
    }

    public static Message sendAudio(long chatID, String text, byte[] bytes) {
        text = UI.addBottom(text, getTimeToDeleteTextBottom(linerBot.deleteMessageTimeout));
        toMessageStack(
                Main.bot.execute(
                        new SendAudio(
                                chatID,
                                bytes
                        ).caption(text).disableNotification(true)
                ).message()
        );
        return addThenDeleteMessage();
    }

    public static Message sendSticker(long chatID, byte[] bytes) {
        toMessageStack(
                Main.bot.execute(
                        new SendSticker(
                                chatID,
                                bytes
                        ).disableNotification(true)
                ).message()
        );

        return addThenDeleteMessage();
    }


    public static Message sendVideo(long chatID, String text, byte[] bytes) {
        text = UI.addBottom(text, getTimeToDeleteTextBottom(linerBot.deleteMessageTimeout));
        toMessageStack(
                Main.bot.execute(
                        new SendVideo(
                                chatID,
                                bytes
                        ).caption(text).disableNotification(true)
                ).message()
        );

        return addThenDeleteMessage();
    }


    private static String getTimeToDeleteTextBottom(int deleteAfter) {
        return "\uD83D\uDDD1 Это сообщение удалится через: ⏱ " + deleteAfter + " секунд.\n "
                + Icons.ATTENTION + "Если сообщение не удалилось:\n" +
                "\t\t\t\t /clean - удалить весь стек\n";
    }

    public static LinerBot load() {
        if (!DB.exists("bot_settings")) {
            LinerBot linerBot = new LinerBot();
            DB.connect("bot_settings").set(linerBot);
            return linerBot;
        }
        return DB.connect("bot_settings").get(LinerBot.class);
    }

    public static void save(LinerBot linerBot) {
        DB.connect("bot_settings").set(linerBot);
    }

    public int getDeleteMessageTimeout() {
        return deleteMessageTimeout;
    }

    public void setDeleteMessageTimeout(int deleteMessageTimeout) {
        this.deleteMessageTimeout = deleteMessageTimeout;
    }

    public int getAllowedStickersTimeout() {
        return allowedStickersTimeout;
    }

    public void setAllowedStickersTimeout(int allowedStickersTimeout) {
        this.allowedStickersTimeout = allowedStickersTimeout;
    }

    public List<Command> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<Command> commandList) {
        this.commandList = commandList;
    }

    public List<Trigger> getTriggerList() {
        return triggerList;
    }

    public void setTriggerList(List<Trigger> triggerList) {
        this.triggerList = triggerList;
    }
}
