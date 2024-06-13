package com.samvolvo.discordlinked.minecraft.events;

import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.Utils.SendToDiscord;
import com.samvolvo.discordlinked.api.CustomConfigCreator;
import com.samvolvo.discordlinked.api.UserData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeave implements Listener {

    @EventHandler
    public static void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        SendToDiscord.sendJoinLeaveDc(p, "join");

        UserData userData = loadUserDataFromFile(p);
        DiscordLinked.getUserDataMap().put(p.getUniqueId(), userData);
    }

    private static UserData loadUserDataFromFile(Player p) {
        CustomConfigCreator.setCustomFile(DiscordLinked.getUserDirectory().toString(), p.getUniqueId().toString());
        FileConfiguration userFile = CustomConfigCreator.getCustomFile(p.getUniqueId().toString());
        userFile.options().copyDefaults(true);

        if (!userFile.contains("displayName")) userFile.set("displayName", p.getDisplayName());
        if (!userFile.contains("discordId")) userFile.set("discordId", null); // <- IDK how you get the discordId from the user
        if (!userFile.contains("warnings")) userFile.set("warnings", 0);

        CustomConfigCreator.saveCustomFile(p.getUniqueId().toString());

        String displayName = userFile.getString("displayName");
        String discordId = userFile.getString("discordId");
        int warnings = userFile.getInt("warnings");

        return new UserData(displayName, discordId, warnings);
    }

    @EventHandler
    public static void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        SendToDiscord.sendJoinLeaveDc(p, "leave");

        UserData userData = DiscordLinked.getUserDataMap().get(p.getUniqueId());
        saveUserDataToFile(p, userData);
    }

    private static void saveUserDataToFile(Player p, UserData userData) {
        FileConfiguration userFile = CustomConfigCreator.getCustomFile(p.getUniqueId().toString());

        userFile.set("displayName", userData.getDisplayName());
        userFile.set("discordId", userData.getDiscordId());
        userFile.set("warnings", userData.getWarnings());

        CustomConfigCreator.saveCustomFile(p.getUniqueId().toString());
    }
}
