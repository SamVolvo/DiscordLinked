package com.samvolvo.discordlinked.minecraft.events;

import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.api.database.PlayerCache;
import com.samvolvo.discordlinked.api.database.models.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class McChatEvent implements Listener {
    private DiscordLinked plugin;
    private final PlayerCache playerCache;

    public McChatEvent(DiscordLinked plugin){
        this.plugin = plugin;
        this.playerCache = plugin.getPlayerCache();
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        plugin.getMessages().sendMessageDc(e.getPlayer(), e.getMessage());
    }

    @EventHandler
    public void onChatCheck(AsyncPlayerChatEvent e){
        if (!plugin.getConfig().getBoolean("minecraft.needVerifiedDiscord")){
            return;
        }

        PlayerData data = playerCache.get(e.getPlayer().getUniqueId().toString());
        if (data.getId() == null || data.getId().isEmpty()) {
            e.setCancelled(true);
            Player player = e.getPlayer();
            plugin.getMessages().McMessage(player, "§cYou are not allowed to chat yet!\n§cLink your §bdiscord §cfirst!");
        }
    }

}
