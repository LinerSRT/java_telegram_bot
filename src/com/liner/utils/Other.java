package com.liner.utils;

public class Other {
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static String fromArray(String[] strings, int startIndex){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex; i < strings.length; i++) {
            stringBuilder.append(strings[i]).append(" ");
        }
        return stringBuilder.toString();
    }
}
