package com.samvolvo.discordlinked.discord.managers;

import com.samvolvo.discordlinked.DiscordLinked;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {
    private DiscordLinked plugin;

    public CommandManager(DiscordLinked plugin){
        this.plugin = plugin;
    }


    @Override
    public void onGuildReady(GuildReadyEvent event) {
        sendStart();
        plugin.getMessages().joinAllPlayers();

        List<CommandData> commandData = new ArrayList<>();

        commandData.add(Commands.slash("account", "Check an account.")
                .addOption(OptionType.USER, "user", "Who do you wanne check", true)
        );

        commandData.add(Commands.slash("link", "Link your account to an minecraft account."));


        //commandData.add(Commands.slash("help", "All the commands for line.lol bot"));


        event.getGuild().updateCommands().addCommands(commandData).queue();

    }

    private void sendStart(){
        plugin.getMessages().sendEmbedDc(EmbedManager.startStop("on"));
    }

}
