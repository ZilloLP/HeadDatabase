package de.zillolp.headdatabase.config;

import de.zillolp.headdatabase.utils.ConfigUtil;
import de.zillolp.headdatabase.utils.StringUtil;

public class LanguageTools {
    private static String PREFIX;
    private static String NO_PERMISSION;
    private static String ONLY_PLAYER;

    public static void load() {
        ConfigUtil configUtil = new ConfigUtil("language.yml");
        PREFIX = StringUtil.replaceDefaults(configUtil.getString("PREFIX"));
        NO_PERMISSION = StringUtil.replaceDefaults(configUtil.getString("NO_PERMISSION"));
        ONLY_PLAYER = StringUtil.replaceDefaults(configUtil.getString("ONLY_PLAYER"));
    }

    public static String getPREFIX() {
        return PREFIX;
    }

    public static String getNO_PERMISSION() {
        return PREFIX + NO_PERMISSION;
    }

    public static String getONLY_PLAYER() {
        return PREFIX + ONLY_PLAYER;
    }
}
