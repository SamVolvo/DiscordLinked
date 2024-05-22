package com.samvolvo.discordlinked.discord.managers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.entity.Player;

import java.awt.*;
import java.time.Instant;

public class EmbedManager {
    public static MessageEmbed joinEmbed(Player p){
        String playerName = p.getDisplayName();
        String playerUUID = p.getUniqueId().toString();
        String playerHeadUrl = "https://api.mineatar.io/face/" + playerUUID; // Using mineatar for player head

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(playerName + " has joined the server.")
                .setAuthor("DiscordLinked")
                .setColor(Color.GREEN)
                .setThumbnail(playerHeadUrl)
                .setTimestamp(Instant.now())
                ;

        return embed.build();
    }

    public static MessageEmbed leaveEmbed(Player p){
        String playerName = p.getDisplayName();
        String playerUUID = p.getUniqueId().toString();
        String playerHeadUrl = "https://api.mineatar.io/face/" + playerUUID; // Using mineatar for player head

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(playerName + " has left the server.")
                .setAuthor("DiscordLinked")
                .setColor(Color.RED)
                .setThumbnail(playerHeadUrl)
                .setTimestamp(Instant.now())
        ;

        return embed.build();
    }
}
