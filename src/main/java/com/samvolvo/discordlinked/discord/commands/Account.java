package com.samvolvo.discordlinked.discord.commands;

import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.discord.managers.EmbedManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Account extends ListenerAdapter {
    private final DiscordLinked plugin;

    public Account(DiscordLinked plugin){
        this.plugin = plugin;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equalsIgnoreCase("account")){
            if (event.getOption("user") != null){
                User user = event.getOption("user").getAsUser();

                OfflinePlayer player = plugin.getPlayerDataUtil().getPlayerById(user.getId());

                if (player == null){
                    event.reply("There is no data about this player.").setEphemeral(true).queue();
                    return;
                }

                MessageEmbed embed = plugin.getEmbedManager().account(player, user, event.getJDA().getSelfUser());

                event.replyEmbeds(embed).queue();
            }else{
                event.reply("This user has no known minecraft account.").setEphemeral(true).queue();
            }
        }
    }
}
