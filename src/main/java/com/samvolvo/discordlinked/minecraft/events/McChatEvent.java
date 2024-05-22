package com.samvolvo.discordlinked.minecraft.events;

import com.samvolvo.discordlinked.Utils.SendToDiscord;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class McChatEvent implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        SendToDiscord.sendMessageDc(e.getPlayer(), e.getMessage());
    }
}
