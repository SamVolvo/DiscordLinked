package com.samvolvo.discordlinked.api.database.utils;

import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.api.database.Database;
import com.samvolvo.discordlinked.api.database.PlayerCache;
import com.samvolvo.discordlinked.api.database.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

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

    public PlayerData getDataById(String id){
        PlayerData data = database.findPlayerDataById(id);
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

    public OfflinePlayer getPlayerById(String discordId){
        PlayerData data = database.findPlayerDataById(discordId);
        if (data.getUuid() == null){
            return null;
        }

        if (data.getUuid() != null){
            // Vervang dit door de UUID van de speler die je wilt vinden
            UUID playerUUID = UUID.fromString(data.getUuid());

            // Haal de OfflinePlayer op basis van de UUID
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);

            if (offlinePlayer != null && offlinePlayer.hasPlayedBefore()) {
                return offlinePlayer;
            }
        }

        return null;
    }



}
