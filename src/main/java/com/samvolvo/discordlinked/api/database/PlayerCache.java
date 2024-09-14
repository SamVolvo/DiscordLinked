package com.samvolvo.discordlinked.api.database;

import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.api.database.models.PlayerData;
import com.samvolvo.discordlinked.api.database.utils.PlayerDataUtil;

import java.util.HashMap;
import java.util.UUID;

public class PlayerCache {
    private DiscordLinked plugin;
    private final HashMap<String, PlayerData> cache;

    public PlayerCache(DiscordLinked plugin){
        this.plugin = plugin;
        this.cache = new HashMap<>();
    }

    public void put(String uuid, PlayerData playerData){
        cache.put(uuid, playerData);
    }

    public PlayerData get(String uuid) {
        PlayerData data;
        if (cache.get(uuid) != null){
            data = cache.get(uuid);
        }else{
            data = plugin.getPlayerDataUtil().getDataByUuid(uuid);
            cache.put(uuid, data);
        }
        return data;
    }

    public void remove(String uuid) {
        cache.remove(uuid);
    }
}
