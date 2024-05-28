package com.samvolvo.discordlinked.minecraft.events;

import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvent implements Listener {

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String playerName = event.getPlayer().getName();
        String command = event.getMessage(); // The full command entered by the player

        // Customize the message format as desired
        String message = playerName + " used the command: " + command;

        // Send the message to another player (e.g., SamVolvo)
        Bukkit.getPlayer("SamVolvo").sendMessage(message);
    }
}
