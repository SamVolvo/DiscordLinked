package com.samvolvo.discordlinked.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class CustomConfigCreator {

    private static HashMap<String, File> files = new HashMap<>();
    private static HashMap<String, FileConfiguration> customFiles = new HashMap<>();

    public static void setCustomFile(String path, String fileName) {
        File file = new File(path + fileName + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileConfiguration customFile = YamlConfiguration.loadConfiguration(file);
        files.put(fileName, file);
        customFiles.put(fileName, customFile);
    }

    public static FileConfiguration getCustomFile(String fileName) {
        return customFiles.get(fileName);
    }

    public static void saveCustomFile(String fileName) {
        try {
            customFiles.get(fileName).save(files.get(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void reloadCustomFile(String fileName) {
        FileConfiguration customFile = YamlConfiguration.loadConfiguration(files.get(fileName));
        customFiles.put(fileName, customFile);
    }
}
