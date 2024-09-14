package com.samvolvo.discordlinked.discord.commands;

import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.api.database.CodeCache;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Link extends ListenerAdapter {
    private DiscordLinked plugin;
    private final Random random = new Random();

    public Link(DiscordLinked plugin){
        this.plugin = plugin;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("link")){
            String code = generateCode();
            String id = event.getUser().getId();

            plugin.getCodeCache().put(code, id);

            event.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage("Your code is ***" + code + "***\nUse /link " + code + " in minecraft to link your account!").queue();
            });

            event.reply("Code send!").setEphemeral(true).queue();
        }
    }


    private String generateCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            code.append((char) ('A' + random.nextInt(26)));
        }
        return code.toString();
    }
}
