package com.samvolvo.discordlinked.Utils;

import com.samvolvo.discordlinked.DiscordLinked;
import org.bukkit.configuration.file.FileConfiguration;

public class MainConfig {
    private static DiscordLinked main;
    private static FileConfiguration config;

    public MainConfig(DiscordLinked plugin){
        main = plugin;
        config = main.getConfig();
    }

    public static String getString(String caller){
        return config.getString(caller);
    }

    public static boolean getBoolean(String caller){
        return config.getBoolean(caller);
    }

}
