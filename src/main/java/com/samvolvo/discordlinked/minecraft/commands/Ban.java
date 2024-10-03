package com.samvolvo.discordlinked.minecraft.commands;

import com.samvolvo.discordlinked.DiscordLinked;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Ban implements CommandExecutor {
    private final DiscordLinked plugin;

    public Ban(DiscordLinked plugin){
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {



        return true;
    }
}
