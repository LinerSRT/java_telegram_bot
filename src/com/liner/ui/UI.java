package com.liner.ui;

import com.liner.utils.Icons;

public class UI {

    public static String createResponse(String icon, String title, String text){
        return createResponse(icon, title, text, "");
    }

    public static String createResponse(String icon, String title, String text, String bottom){
        return "╭" + createLine("━", 20) + "\n" +
                icon + "\t" + title + "\n" +
                "╰" + createLine("━", 20) + "\n" +
                text + ((bottom.length()!=0)?(
                "\n"+createLine("━", 20)+ "\n"
                +bottom):"");
    }

    public static String addBottom(String response, String bottomText){
        return response+
                "\n"+createLine("━", 20)+ "\n"
                +bottomText;
    }

    private static String createLine(String character, int size){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size + 2; i++) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }
}
