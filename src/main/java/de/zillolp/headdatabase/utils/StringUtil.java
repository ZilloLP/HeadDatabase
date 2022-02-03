package de.zillolp.headdatabase.utils;

public class StringUtil {

    public static boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String replaceDefaults(String message) {
        message = message.replace("%Ae%", "Ä");
        message = message.replace("%ae%", "ä");
        message = message.replace("%Oe%", "Ö");
        message = message.replace("%oe%", "ö");
        message = message.replace("%Ue%", "Ü");
        message = message.replace("%ue%", "ü");
        message = message.replace("%sz%", "ß");
        message = message.replace("%>%", "»");
        message = message.replace("%<%", "«");
        message = message.replace("%*%", "×");
        message = message.replace("%|%", "┃");
        message = message.replace("%->%", "➜");
        message = message.replace("&", "§");
        return message;
    }
}
