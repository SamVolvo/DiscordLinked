package com.samvolvo.discordlinked.discord.events;

import com.samvolvo.discordlinked.Utils.MainConfig;
import com.samvolvo.discordlinked.Utils.SendToMc;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DcChatEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()){
            if (event.getChannel().getId().equals(MainConfig.getString("discord.chatChannel"))){
                var bericht = event.getMessage();
                String messageContent = bericht.getContentRaw();

                SendToMc.sendMessageMc(event.getMember(), messageContent);
            }
        }
    }
}
