package com.samvolvo.discordlinked.api.database.utils;

import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.api.database.Database;
import com.samvolvo.discordlinked.api.database.models.PlayerData;

import java.util.UUID;

public class PlayerDataUtil {
    private final DiscordLinked plugin;
    private final Database database;

    public PlayerDataUtil(DiscordLinked plugin){
        this.plugin = plugin;
        this.database = plugin.getDatabase();
    }

    public PlayerData getDataByUuid(String uuid){
        PlayerData data = database.findPlayerDataByUUID(uuid);
        return data;
    }

    public void linkDiscord(UUID uuid, String id){
        String Uuid = uuid.toString();

        PlayerData data = new PlayerData(0, Uuid, id, 0);
        database.updatePlayerData(data);
        plugin.getPlayerCache().put(Uuid, data);
    }

    public void updateData(String uuid, PlayerData data){
        database.updatePlayerData(data);
        plugin.getPlayerCache().put(uuid, data);
    }



}
