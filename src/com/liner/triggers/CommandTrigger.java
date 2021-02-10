package com.liner.triggers;

import com.pengrad.telegrambot.model.Message;

public class CommandTrigger extends TriggerEntity {
    private String command;
    private String description;


    public CommandTrigger(String command, String description, String response, Type type) {
        super(command, response, type);
        this.command = command;
        this.description = description;
    }


    public CommandTrigger(String command, String description, Type type) {
        this(command, description, null, type);
    }

    @Override
    public boolean triggered(Message message) {
        String messageText = message.text();
        if (messageText == null)
            return false;
        boolean containCommand = false;
        if (messageText.contains("@liner_chat_bot"))
            messageText = messageText.replace("@liner_chat_bot", "");
        if (trigger.startsWith("/")) {
            String[] arguments = messageText.split(" ");
            for (String argument : arguments) {
                if (argument.startsWith("/") && argument.equals(trigger)) {
                    containCommand = true;
                    break;
                }
            }
        }
        return containCommand;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}
