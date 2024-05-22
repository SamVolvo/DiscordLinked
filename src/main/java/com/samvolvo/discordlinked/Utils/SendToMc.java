package com.samvolvo.discordlinked.Utils;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SendToMc extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();

        for (Player player : Bukkit.getOnlinePlayers()){
            if (player.hasPermission("discordlinked.commandLogs")){
                player.sendMessage("§b§lDiscord Linked&e&l: &a§l"+ event.getMember().getEffectiveName() + " &ehas used the &c/" + command + " &ecommand in discord");
            }
        }

    }
}
