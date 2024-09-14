package com.samvolvo.discordlinked.api.tools;

import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.api.DiscordWebhooks;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.bukkit.entity.Player;

public class DiscordTools {
    private DiscordLinked plugin;
    private ShardManager shardManager;

    public DiscordTools(DiscordLinked plugin){
        this.plugin = plugin;
        this.shardManager = plugin.getShardManager();
    }

    public TextChannel getTextChannel(String channelId, Guild guild){
        return guild.getTextChannelById(channelId);
    }






        // Find the username by discord id
    public String getUsernameFromId(String discordId) {
        try {
            User user = shardManager.retrieveUserById(discordId).complete();
            return user.getName();
        } catch (ErrorResponseException e) {
            // Handle the case where the user is not found
            return null;
        }
    }
}
