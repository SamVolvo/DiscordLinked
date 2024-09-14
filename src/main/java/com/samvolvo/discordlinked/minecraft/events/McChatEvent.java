package com.samvolvo.discordlinked.minecraft.events;

import com.samvolvo.discordlinked.DiscordLinked;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class McChatEvent implements Listener {
    private DiscordLinked plugin;

    public McChatEvent(DiscordLinked plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        plugin.getMessages().sendMessageDc(e.getPlayer(), e.getMessage());
    }
}
