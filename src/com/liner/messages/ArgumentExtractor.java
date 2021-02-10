package com.liner.messages;

import com.pengrad.telegrambot.model.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@SuppressWarnings("rawtypes | unchecked")
public class ArgumentExtractor {
    private final String[] args;

    public ArgumentExtractor(Message message) {
        if(message.text() == null) {
            args = new String[0];
        } else {
            args = Arrays.copyOfRange(message.text().split(" "), 1, message.text().split(" ").length);
        }
    }

    public static boolean isCorrect(KeyPair[] keyPairs, KeyValue[] keyValues){
        return isCorrect(Arrays.asList(keyPairs), Arrays.asList(keyValues));
    }
    public static boolean isCorrect(List<KeyPair> keyPairs, List<KeyValue> keyValues){
        return keyPairs.size() == keyValues.size();
    }

    public List<KeyValue> extract(List<KeyPair> keyPairList){
        List<KeyValue> keyValueList = new ArrayList<>();
        if(args.length == 0 || args.length != keyPairList.size())
            return keyValueList;
        for(KeyPair keyPair:keyPairList){
            int index = keyPairList.indexOf(keyPair);
            if(keyPair.tClass.isAssignableFrom(String.class)){
                if(args[index].length() != 0 && !args[index].equals("")) {
                    //TODO IF PAIR IS LAST AND ARGS HAVE MORE ENTRIES ADD ALL AFTER!!!
                    keyValueList.add(new KeyValue<>(keyPair.key, args[index]));
                }
            } else if(keyPair.tClass.isAssignableFrom(Integer.class)){
                if(args[index].length() != 0 && !args[index].equals("") && !args[index].equals(" "))
                    try {
                        keyValueList.add(new KeyValue<>(keyPair.key, Integer.parseInt(args[index])));
                    } catch (NumberFormatException e){
                        e.printStackTrace();
                    }
            }
        }
        return keyValueList;
    }

    public  List<KeyValue> extract(KeyPair[] keyPairList){
        return extract(Arrays.asList(keyPairList));
    }
}