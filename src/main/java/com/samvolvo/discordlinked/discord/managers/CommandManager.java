package com.samvolvo.discordlinked.discord.managers;

import com.samvolvo.discordlinked.Utils.SendToDiscord;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        sendStart();
        List<CommandData> commandData = new ArrayList<>();




        //commandData.add(Commands.slash("help", "All the commands for line.lol bot"));


        event.getGuild().updateCommands().addCommands(commandData).queue();

    }

    private void sendStart(){
        SendToDiscord.sendEmbedDc(EmbedManager.startStop("on"));
    }

}
