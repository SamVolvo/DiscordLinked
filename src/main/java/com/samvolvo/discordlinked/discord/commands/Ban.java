package com.samvolvo.discordlinked.discord.commands;

import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.api.database.models.PlayerData;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Ban extends ListenerAdapter {
    private DiscordLinked plugin;

    public Ban(DiscordLinked plugin){
        this.plugin = plugin;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        Member target = event.getOption("user").getAsMember();
        String reason = event.getOption("reason") != null ? event.getOption("reason").getAsString() : "No reason provided";
        int duration = event.getOption("duration") != null ? event.getOption("duration").getAsInt() : 0;

        if (target == null){
            event.getHook().sendMessage("User not found.").setEphemeral(true).queue();
            return;
        }

        event.getGuild().ban(target, duration, TimeUnit.DAYS).reason(reason).queue();
        OfflinePlayer player = plugin.getPlayerDataUtil().getPlayerById(target.getId());
        if (player == null){
            event.getHook().sendMessage("There was no minecraft account found on this member.\nThe member is banned from the discord but not from minecraft.").queue();
        }else{
            // Ban the player from the Minecraft server
            BanList banList = Bukkit.getBanList(BanList.Type.NAME);
            BanEntry banEntry = banList.addBan(player.getName(), reason, (Date) null, "Discord Ban");

            if (duration > 0) {
                Date unbanDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(duration));
                banEntry.setExpiration(unbanDate);
            }

            event.getHook().sendMessage("User " + target.getEffectiveName() + " has been banned from both Discord and Minecraft for: " + reason).queue();
        }





    }
}
