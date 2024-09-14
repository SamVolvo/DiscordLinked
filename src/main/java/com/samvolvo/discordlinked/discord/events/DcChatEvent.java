package com.samvolvo.discordlinked.discord.events;

import com.samvolvo.discordlinked.DiscordLinked;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DcChatEvent extends ListenerAdapter {
    private DiscordLinked plugin;

    public DcChatEvent(DiscordLinked plugin){
        this.plugin = plugin;
    }


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()){
            if (event.getChannel().getId().equals(DiscordLinked.getInstance().getConfig().getString("discord.chatChannel"))){
                var bericht = event.getMessage();
                String messageContent = bericht.getContentRaw();

                plugin.getMessages().sendMessageMc(event.getMember(), messageContent);
            }
        }
    }
}
