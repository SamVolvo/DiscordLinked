package com.samvolvo.discordlinked.discord.managers;

import com.samvolvo.discordlinked.DiscordLinked;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.awt.*;
import java.time.Instant;

public class EmbedManager {
    private final DiscordLinked plugin;

    public EmbedManager(DiscordLinked plugin){
        this.plugin = plugin;
    }

    public static MessageEmbed joinEmbed(Player p){
        String playerName = p.getName();
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
        String playerName = p.getName();
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

    public MessageEmbed account(OfflinePlayer player, User user, User bot){
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setAuthor("DiscordLinked", "https://samvolvo.com/", user.getAvatarUrl())
                .setTitle( user.getEffectiveName() + "\'s Account")
                .addField("Minecraft Display Name:", player.getName(), false)
                .addField("Warnings:", String.valueOf(plugin.getPlayerCache().get(player.getUniqueId().toString()).getWarnings()) , false)
                .setThumbnail("https://api.mineatar.io/body/full/" + player.getUniqueId() + "?scale=16")
                .setColor(Color.decode("#bbff00"))
                .setFooter(bot.getEffectiveName(), bot.getAvatarUrl())
                .setTimestamp(Instant.now());

        return embedBuilder.build();
    }

    public static MessageEmbed commandDc(String command, User user){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Command Alert");
        embed.setDescription("Er is een command gebruikt: " + command);
        embed.setColor(0xFF0000); // Stel de kleur in (bijvoorbeeld rood)
        embed.setFooter("Gebruikt door: " + user.getName(), user.getAvatarUrl());

        return embed.build();
    }
}
