package com.samvolvo.discordlinked.discord.managers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
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
                .setColor(Color.GREEN)
                .setThumbnail(playerHeadUrl)
                ;

        return embed.build();
    }

    public static MessageEmbed leaveEmbed(Player p){
        String playerName = p.getDisplayName();
        String playerUUID = p.getUniqueId().toString();
        String playerHeadUrl = "https://api.mineatar.io/face/" + playerUUID; // Using mineatar for player head

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(playerName + " has left the server.")
                .setColor(Color.RED)
                .setThumbnail(playerHeadUrl)
        ;

        return embed.build();
    }

    public static MessageEmbed startStop(String OnOff){
        EmbedBuilder embed = new EmbedBuilder();

        if (OnOff.equalsIgnoreCase("on")){
            embed.setTitle("Server is online!")
                    .setColor(Color.WHITE)
                    .setTimestamp(Instant.now())
                    .setFooter("DiscordLinked")
            ;
        }else{
            embed.setTitle("Server is offline!")
                    .setColor(Color.WHITE)
                    .setTimestamp(Instant.now())
                    .setFooter("DiscordLinked")
            ;
        }

        return embed.build();
    }
}
