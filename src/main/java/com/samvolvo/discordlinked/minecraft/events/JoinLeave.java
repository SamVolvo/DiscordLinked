package com.samvolvo.discordlinked.minecraft.events;

import com.samvolvo.discordlinked.Utils.SendToDiscord;
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
    }

    @EventHandler
    public static void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        SendToDiscord.sendJoinLeaveDc(p, "leave");
    }
}
