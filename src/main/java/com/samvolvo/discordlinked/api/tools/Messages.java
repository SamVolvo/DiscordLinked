package com.samvolvo.discordlinked.api.tools;

import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.api.DiscordWebhooks;
import com.samvolvo.discordlinked.discord.managers.EmbedManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.samvolvo.discordlinked.api.DiscordWebhooks.sendDiscordWebhookChat;

public class Messages extends ListenerAdapter {
    private final DiscordLinked plugin;
    private final String prefix;
    private final ShardManager shardManager;

    public Messages(DiscordLinked plugin){
        this.plugin = plugin;
        prefix = plugin.getConfig().getString("minecraft.prefix");
        shardManager = plugin.getShardManager();
        guildId = plugin.getConfig().getString("discord.guildId");
        channelId = DiscordLinked.getInstance().getConfig().getString("discord.chatChannel");
        adminChannel = DiscordLinked.getInstance().getConfig().getString("discord.adminChannel");
    }

    // Minecraft
    public void McMessage(Player player, String message){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7: &e" + message));
    }

    public void McError(Player player, int code){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7: &cThere was an error! §aCode: " + code));
    }

    public void sendMessageMc(Member member, String message){
        for (Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("minecraft.messages.discord_prefix") + " &r" + member.getEffectiveName() + ": " + message));
        }
    }



    // Discord
    private final String guildId;
    private final String channelId;
    private final String adminChannel;

    public void sendJoinLeaveDc(Player p, String joinLeave) {
        Guild guild;
        try {
            guild = shardManager.getGuildById(plugin.getConfig().getString("discord.guildId"));
        } catch (NullPointerException e){
            plugin.getLogger().info("Please specify a Guild ID in the config.");
            return;
        }

        if (guild == null) {
            plugin.getLogger().info("Guild not found! Guild ID might be incorrect.");
            return;
        }

        TextChannel channel = plugin.getDiscordTools().getTextChannel(channelId, guild);
        if (channel == null) {
            plugin.getLogger().info("Channel not found! Channel ID might be incorrect.");
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

    public void joinAllPlayers(){
        for (Player player : Bukkit.getOnlinePlayers()){
            sendJoinLeaveDc(player, "join");
        }
    }

    public void commandSendMC(String command, Player p){
        Guild guild = shardManager.getGuildById(guildId);
        TextChannel channel = plugin.getDiscordTools().getTextChannel(adminChannel, guild);
        String playerUUID = p.getUniqueId().toString();
        String avatarUrl = "https://api.mineatar.io/face/" + playerUUID;

        DiscordWebhooks.sendDiscordWebhookCommand(p.getName(), avatarUrl, command);
    }

    public void sendEmbedDc(MessageEmbed embed){
        Guild guild = shardManager.getGuildById(guildId);
        TextChannel channel = plugin.getDiscordTools().getTextChannel(channelId, guild);

        channel.sendMessageEmbeds(embed).queue();
    }

    public void sendMessageDc(Player p, String message){
        String playerName = p.getName();
        String playerUUID = p.getUniqueId().toString();
        String avatarUrl = "https://api.mineatar.io/face/" + playerUUID;

        sendDiscordWebhookChat(playerName, avatarUrl, message);
    }

    public void sendAdminEmbed(MessageEmbed embed){
        Guild guild = shardManager.getGuildById(guildId);
        TextChannel channel = plugin.getDiscordTools().getTextChannel(adminChannel, guild);

        channel.sendMessageEmbeds(embed).queue();
    }

}
