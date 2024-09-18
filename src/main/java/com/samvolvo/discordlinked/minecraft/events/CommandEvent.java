package com.samvolvo.discordlinked.minecraft.events;

import com.samvolvo.discordlinked.DiscordLinked;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.*;

public class CommandEvent implements Listener {
    private final DiscordLinked plugin;
    private final List<String> blockedCommands = new ArrayList<>();

    public CommandEvent(DiscordLinked plugin){
        this.plugin = plugin;

        List<String> ignoredCommands = (List<String>) plugin.getConfig().getList("minecraft.sendCommands.ignoredCommands");
        if (ignoredCommands != null) {
            blockedCommands.addAll(ignoredCommands);
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event){

        if (!plugin.getConfig().getBoolean("minecraft.sendCommands.isEnabled")){
            return;
        }

        Player player = event.getPlayer();
        String command = event.getMessage().substring(1);

        if (blockedCommands.contains(command)) {
            return;
        }

        if (player.hasPermission("discordlinked.alerts.bypass")){
            return;
        }

        plugin.getMessages().commandSendMC("/" + command, player);

        for (Player p : Bukkit.getOnlinePlayers()){
            if (p.hasPermission("discordlinked.alerts")){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPrefix() + " &c" + player.getDisplayName() + " &e has used &b/" + command + " &e in minecraft."));
            }
        }
    }
}
