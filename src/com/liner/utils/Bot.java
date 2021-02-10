package com.liner.utils;

import com.liner.ui.UI;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.*;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class Bot {
    private static Bot bot;
    private TelegramBot telegramBot;
    private List<Message> messageStack;

    public static void create(TelegramBot telegramBot) {
        bot = new Bot();
        bot.telegramBot = telegramBot;
        bot.messageStack = DB.connect("message_stack").getList(Message.class);
    }

    public static void registerMessage(Message message) {
        if (bot == null)
            return;
        bot.messageStack.add(message);
        DB.connect("message_stack").set(bot.messageStack);
    }

    public static int getStackSize(){
        return bot.messageStack.size();
    }

    public static Message replyText(long chatID, long messageID, String text) {
        return replyText(chatID, messageID, text, -1);
    }

    public static Message replyText(long chatID, long messageID, String text, long deleteAfter) {
        if (messageID == -1) {
            sendText(chatID, text, deleteAfter);
            return null;
        }
        if (bot == null)
            return null;
        text = UI.addBottom(text, getTimeToDeleteTextBottom(deleteAfter));
        bot.messageStack.add(
                bot.telegramBot.execute(
                        new SendMessage(chatID, text)
                                .parseMode(ParseMode.Markdown)
                                .disableWebPagePreview(true)
                                .disableNotification(true)
                                .replyToMessageId(Math.toIntExact(messageID))
                ).message()
        );
        DB.connect("message_stack").set(bot.messageStack);
        Message message = bot.messageStack.get(bot.messageStack.size() - 1);
        if (deleteAfter != -1)
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    delete(message, false);
                }
            }, deleteAfter);
        return message;
    }

    public static Message sendText(long chatID, String text) {
        return sendText(chatID, text, -1);
    }

    public static Message sendText(long chatID, String text, long deleteAfter) {
        if (bot == null)
            return null;

        text = UI.addBottom(text, getTimeToDeleteTextBottom(deleteAfter));
        bot.messageStack.add(
                bot.telegramBot.execute(
                        new SendMessage(
                                chatID,
                                text
                        ).disableNotification(true)
                ).message()
        );
        DB.connect("message_stack").set(bot.messageStack);
        Message message = bot.messageStack.get(bot.messageStack.size() - 1);
        if (deleteAfter != -1)
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    delete(message, false);
                }
            }, deleteAfter);
        return message;
    }


    public static Message sendPhoto(long chatID, String text, byte[] bytes) {
        return sendPhoto(chatID, text, bytes, -1);
    }

    public static Message sendPhoto(long chatID, String text, byte[] bytes, long deleteAfter) {
        if (bot == null)
            return null;

        text = UI.addBottom(text, getTimeToDeleteTextBottom(deleteAfter));
        bot.messageStack.add(
                bot.telegramBot.execute(
                        new SendPhoto(
                                chatID,
                                bytes
                        ).caption(text).disableNotification(true)
                ).message()
        );
        DB.connect("message_stack").set(bot.messageStack);
        Message message = bot.messageStack.get(bot.messageStack.size() - 1);
        if (deleteAfter != -1)
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    delete(message, false);
                }
            }, deleteAfter);
        return message;
    }

    public static Message sendAudio(long chatID, String text, byte[] bytes) {
        return sendAudio(chatID, text, bytes, -1);
    }

    public static Message sendAudio(long chatID, String text, byte[] bytes, long deleteAfter) {
        if (bot == null)
            return null;
        text = UI.addBottom(text, getTimeToDeleteTextBottom(deleteAfter));
        bot.messageStack.add(
                bot.telegramBot.execute(
                        new SendAudio(
                                chatID,
                                bytes
                        ).caption(text).disableNotification(true)
                ).message()
        );
        DB.connect("message_stack").set(bot.messageStack);
        Message message = bot.messageStack.get(bot.messageStack.size() - 1);
        if (deleteAfter != -1)
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    delete(message, false);
                }
            }, deleteAfter);
        return message;
    }

    public static Message sendSticker(long chatID, byte[] bytes) {
        return sendSticker(chatID, bytes, -1);
    }

    public static Message sendSticker(long chatID, byte[] bytes, long deleteAfter) {
        if (bot == null)
            return null;
        bot.messageStack.add(
                bot.telegramBot.execute(
                        new SendSticker(
                                chatID,
                                bytes
                        ).disableNotification(true)
                ).message()
        );
        DB.connect("message_stack").set(bot.messageStack);
        Message message = bot.messageStack.get(bot.messageStack.size() - 1);
        if (deleteAfter != -1)
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    delete(message, false);
                }
            }, deleteAfter);
        return message;
    }

    public static Message sendVideo(long chatID, String text, byte[] bytes) {
        return sendVideo(chatID, text, bytes, -1);
    }

    public static Message sendVideo(long chatID, String text, byte[] bytes, long deleteAfter) {
        if (bot == null)
            return null;
        text = UI.addBottom(text, getTimeToDeleteTextBottom(deleteAfter));
        bot.messageStack.add(
                bot.telegramBot.execute(
                        new SendVideo(
                                chatID,
                                bytes
                        ).caption(text).disableNotification(true)
                ).message()
        );
        DB.connect("message_stack").set(bot.messageStack);
        Message message = bot.messageStack.get(bot.messageStack.size() - 1);
        if (deleteAfter != -1)
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    delete(message, false);
                }
            }, deleteAfter);
        return message;
    }

    public static void editMessage(long chatID, Message oldMessage, Message newMessage) {
        bot.messageStack.remove(oldMessage);
        bot.messageStack.add(newMessage);
        bot.telegramBot.execute(
                new EditMessageText(chatID, oldMessage.messageId(), newMessage.text())
        );
    }


    public static void delete(Message message, boolean includeStack) {
        if (bot == null)
            return;
        if (includeStack)
            bot.messageStack.remove(message);
        bot.telegramBot.execute(new DeleteMessage(message.chat().id(), message.messageId()));
    }

    public static void clear() {
        if (bot == null)
            return;
        for (Message message : bot.messageStack)
            if (message != null)
                delete(message, false);
        bot.messageStack.clear();
        DB.connect("message_stack").set(bot.messageStack);
    }

    private static String getTimeToDeleteTextBottom(long deleteAfter) {
        String modified =  "\uD83D\uDDD1 Это сообщение удалится через: ⏱ " + TimeUnit.MILLISECONDS.toSeconds(deleteAfter) + " секунд.\n "
                + Icons.ATTENTION + "Если сообщение не удалилось:\n" +
                "\t\t\t\t /clean - удалить весь стек\n";
        return deleteAfter == -1 ? "" : modified;
    }
}
