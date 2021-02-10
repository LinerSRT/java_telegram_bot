package com.liner.commands;

import com.liner.messages.KeyPair;
import com.liner.models.MessagesStats;
import com.liner.models.User;
import com.liner.models.Word;
import com.liner.ui.UI;
import com.liner.utils.Bot;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class StatCommand extends Command {
    @Override
    public String getCommand() {
        return "/stats";
    }

    @Override
    public String getDescription() {
        return "Показать статистику пользователя либо посмотреть свою";
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
        return true;
    }

    @Override
    public void execute(User sender, User target, String[] arguments) {
        String ui = target == null ? createUI(sender) : createUI(target);
        Bot.sendText(getChatID(), UI.createResponse(
                "\uD83D\uDC64",
                "Детальная статистика пользователя",
                ui
        ), TimeUnit.SECONDS.toMillis(60));
    }

    private String createUI(User user) {
        StringBuilder stringBuilder = new StringBuilder();
        MessagesStats messagesStats = user.getMessagesStats();
        stringBuilder.append("\t\uD83D\uDCCEИдентификатор: ").append(user.getId()).append("\n");
        stringBuilder.append("\t\uD83D\uDCCEНикнейм: @").append(user.getUsername()).append("\n");
        stringBuilder.append("\t\uD83D\uDCF0Имя: ").append(user.getName()).append("\n");
        stringBuilder.append("\t⚜️Администратор: ").append(user.isAdmin() ? "Да" : "Нет").append("\n");
        stringBuilder.append("\t\uD83D\uDD34Забанен: ").append(user.isBanned() ? "Да" : "Нет").append("\n");
        stringBuilder.append("\t\uD83D\uDCE8Сообщений написал: ").append(messagesStats.messagesCount).append("\n");
        stringBuilder.append("\t\uD83D\uDCE9Собщений в день: ").append(messagesStats.messagesPerDay).append("\n");
        stringBuilder.append("\t\uD83D\uDCDDНаписано символов (всего): ").append(messagesStats.totalCharsWritten).append("\n");
        stringBuilder.append("\t\uD83D\uDCE7Последнее сообщение было: ⏱").append(new SimpleDateFormat("HH:mm dd.MM.yyyy").format(messagesStats.lastPostedMessageTime)).append("\n");
        stringBuilder.append("\t\uD83D\uDCC4Популярные слова: \n");
        messagesStats.popularWords.stream()
                .sorted(Collections.reverseOrder(Comparator.comparingInt(w -> w.uses))).limit(10)
                .forEach(
                    word -> stringBuilder.append("\t\t\t\t\uD83D\uDCCC [").append(word.word).append(" ] написано ").append(word.uses).append(" шт.\n")
                );
        return stringBuilder.toString();
    }

    @Override
    public KeyPair[] getCommandArgumentKeyPairs() {
        return new KeyPair[0];
    }
}
