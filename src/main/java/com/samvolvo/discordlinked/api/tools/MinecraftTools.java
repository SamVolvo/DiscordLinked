package com.samvolvo.discordlinked.api.tools;

import com.samvolvo.discordlinked.DiscordLinked;
import org.bukkit.entity.Player;

public class MinecraftTools {
    private DiscordLinked plugin;

    public MinecraftTools(DiscordLinked plugin){
        this.plugin = plugin;
    }

    public void sendTitle(Player player, String title, String subtitle){
        player.sendTitle(title, subtitle, 10, 70, 20);
    }

}
