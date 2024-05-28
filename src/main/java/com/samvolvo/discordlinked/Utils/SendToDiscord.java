package com.samvolvo.discordlinked.Utils;

import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.discord.managers.EmbedManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.bukkit.entity.Player;

import static com.samvolvo.discordlinked.api.DiscordWebhooks.*;

public class SendToDiscord {
    private static ShardManager shardManager = DiscordLinked.getShardManager();
    private static String guildId = MainConfig.getString("discord.guildId");
    private static String channelId = MainConfig.getString("discord.chatChannel");
    private static String adminChannel = MainConfig.getString("discord.adminChannel");


    public static void commandSendMC(String command, Player p){
        Guild guild = shardManager.getGuildById(guildId);
        TextChannel channel = getTextChannel(adminChannel, guild);

        channel.sendMessage()
    }

    public static void sendEmbedDc(MessageEmbed embed){
        Guild guild = shardManager.getGuildById(guildId);
        TextChannel channel = getTextChannel(channelId, guild);

        channel.sendMessageEmbeds(embed).queue();
    }

    public static void sendMessageDc(Player p, String message){
        String playerName = p.getDisplayName();
        String playerUUID = p.getUniqueId().toString();
        String avatarUrl = "https://api.mineatar.io/face/" + playerUUID;

        sendDiscordWebhookChat(playerName, avatarUrl, message);
    }

    public static void sendJoinLeaveDc(Player p, String joinLeave) {
        System.out.println("Sending join/leave message. Guild ID: " + guildId);
        Guild guild = shardManager.getGuildById(guildId);
        if (guild == null) {
            System.out.println("Guild not found! Guild ID might be incorrect.");
            return;
        }

        System.out.println("Chat Channel ID: " + channelId);
        TextChannel channel = getTextChannel(channelId, guild);
        if (channel == null) {
            System.out.println("Channel not found! Channel ID might be incorrect.");
            return;
        }

        if (joinLeave.equalsIgnoreCase("join")) {
            MessageEmbed joinEmbed = EmbedManager.joinEmbed(p);
            channel.sendMessageEmbeds(joinEmbed).queue();
        } else if (joinLeave.equalsIgnoreCase("leave")) {
            MessageEmbed leaveEmbed = EmbedManager.leaveEmbed(p);
            channel.sendMessageEmbeds(leaveEmbed).queue();
        }
    }


    public static TextChannel getTextChannel(String channelId, Guild guild){
       return guild.getTextChannelById(channelId);
    }
}