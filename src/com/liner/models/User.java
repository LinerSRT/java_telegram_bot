package com.liner.models;

import com.liner.utils.Bot;
import com.liner.utils.DB;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.sun.istack.internal.Nullable;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class User {
    private long id;
    private String username;
    private String name;
    private MessagesStats messagesStats;
    private boolean isAdmin = false;
    private boolean isBanned = false;


    public User(long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.messagesStats = new MessagesStats();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
        DB.connect(Long.toString(id)).set(this);
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
        DB.connect(Long.toString(id)).set(this);
    }


    public String getUsername() {
        return username;
    }


    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public boolean isOwner() {
        return id == 750117845;
    }

    public static Message fromReplyM(User current) {
        return current.getLastMessage().replyToMessage();
    }

    @Nullable
    public Message getLastMessage() {
        return messagesStats.message;
    }

    public MessagesStats getMessagesStats() {
        return messagesStats;
    }

    public static User fromUpdate(Update update) {
        long userID = update.message().from().id();
        User user = fromUID(userID);
        if (user == null) {
            user = new User(userID, update.message().from().username(), update.message().from().firstName());
            DB.connect(Long.toString(userID)).set(user);
            return user;
        }
        return user;
    }

    @Nullable
    public static User fromUID(long uid) {
        if (DB.exists(Long.toString(uid))) {
            return DB.connect(Long.toString(uid)).get(User.class);
        } else {
            return null;
        }
    }

    @Nullable
    public static User fromMessage(Message message){
        long userUID = message.from().id();
        if (DB.exists(Long.toString(userUID))) {
            return fromUID(userUID);
        } else {
            return fromTelegramUser(message.from());
        }
    }

    public static User fromTelegramUser(com.pengrad.telegrambot.model.User telegramUser){
        if (DB.exists(Long.toString(telegramUser.id()))) {
            return fromUID(telegramUser.id());
        } else {
            User user = new User(telegramUser.id(), telegramUser.username(), telegramUser.firstName());
            DB.connect(Long.toString(user.id)).set(user);
            return user;
        }
    }

    @Nullable
    public static User fromUsername(String username) {
        username = username.replace("@", "");
        for (User user : DB.connect(null).all(User.class)) {
            if (user.username != null && user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public void processUpdate(Message message) {
        if(isBanned()){
            Bot.delete(message, false);
            Bot.sendText(message.chat().id(), "Ты забанен, не пиши сюда больше!", TimeUnit.SECONDS.toMillis(3));
            return;
        }
        messagesStats.handleMessage(message);
        DB.connect(Long.toString(id)).set(this);
    }
}
