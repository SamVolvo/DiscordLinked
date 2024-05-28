package com.samvolvo.discordlinked.discord.commands;

import com.samvolvo.discordlinked.discord.managers.EmbedManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Account extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equalsIgnoreCase("account")){
            if (event.getOption("user") == null){
                User user = event.getUser();
                Player player = Bukkit.getPlayer("SamVolvo");
                MessageEmbed embed = EmbedManager.account(player, user, event.getJDA().getSelfUser());

                event.replyEmbeds(embed).queue();
            }else{
                User user = event.getOption("user").getAsUser();
                Player player = Bukkit.getPlayer("SamVolvo");
                MessageEmbed embed = EmbedManager.account(player, user, event.getJDA().getSelfUser());

                event.replyEmbeds(embed).queue();
            }
        }
    }
}
