package com.liner.models;

import com.pengrad.telegrambot.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MessagesStats {
    public List<Word> popularWords;
    public long messagesCount;
    public int messagesPerDay;
    public long totalCharsWritten;
    public long lastPostedMessageTime;
    private long lastTimeCheck;
    public Message message;

    public MessagesStats() {
        this.messagesCount = 0;
        this.messagesPerDay = 0;
        this.popularWords = new ArrayList<>();
        this.totalCharsWritten = 0;
        this.lastPostedMessageTime = System.currentTimeMillis();
        this.lastTimeCheck = System.currentTimeMillis();
    }

    public void handleMessage(Message message) {
        this.message = message;
        messagesCount++;
        lastPostedMessageTime = System.currentTimeMillis();
        if (lastTimeCheck + TimeUnit.DAYS.toMillis(1) < lastPostedMessageTime) {
            lastTimeCheck = lastPostedMessageTime;
            messagesPerDay = 0;
        }
        messagesPerDay++;
        String messageText = message.text();
        if (messageText != null) {
            String[] words = messageText.split(" ");
            for (String word : words) {
                if (word.length() < 3)
                    continue;
                if (!containWord(word)) {
                    popularWords.add(new Word(word, 1));
                }
                for (Word entry : popularWords) {
                    if (entry.word.equals(word))
                        entry.uses += 1;
                }
            }
            if (popularWords.size() > 25) {
                popularWords.forEach(word -> {
                    int valuesSummary = popularWords.stream().mapToInt(i -> word.uses).sum();
                    int magic = Math.round((float) valuesSummary / popularWords.size());
                    if (word.uses < magic) {
                        popularWords.remove(word);
                    }
                });
            }
            totalCharsWritten += messageText.toCharArray().length;
        }
    }

    @Override
    public String toString() {
        return "MessagesStats{" +
                "messagesCount=" + messagesCount +
                ", messagesPerDay=" + messagesPerDay +
                ", popularWords=" + popularWords +
                ", totalCharsWritten=" + totalCharsWritten +
                ", lastPostedMessageTime=" + lastPostedMessageTime +
                ", lastTimeCheck=" + lastTimeCheck +
                ", message=" + message +
                '}';
    }

    private boolean containWord(String text) {
        for (Word word : popularWords) {
            if (word.word.equals(text))
                return true;
        }
        return false;
    }
}
