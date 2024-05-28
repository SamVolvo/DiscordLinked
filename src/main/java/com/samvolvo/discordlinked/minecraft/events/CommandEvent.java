package com.samvolvo.discordlinked.minecraft.events;

import com.samvolvo.discordlinked.Utils.MainConfig;
import com.samvolvo.discordlinked.Utils.SendToDiscord;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvent implements Listener {

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage(); // The full command entered by the player

        // Customize the message format as desired
        SendToDiscord.commandSendMC(command, player);

        // Send the message to another player (e.g., SamVolvo)
        for (Player p : Bukkit.getOnlinePlayers()){
            if (p.hasPermission("discordlinked.alerts")){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfig.getPrefix() + " &c" + player.getDisplayName() + " &e has used &1" + command));
            }
        }
    }
}
