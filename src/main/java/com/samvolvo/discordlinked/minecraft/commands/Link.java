package com.samvolvo.discordlinked.minecraft.commands;

import com.samvolvo.discordlinked.Utils.MainConfig;
import com.samvolvo.discordlinked.api.UserData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Link implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    /**
    Check if the sender is a Player in minecraft.
     */
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length != 2){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', MainConfig.getPrefix() + "§7: Please provide us with your code send by our discord bot to you. \n if he didn't send it to you use /link in the discord server."));
            }else{
                // check if the player already has a discord id to his profile
                String id = ""; // set the id
                if ( id != null){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',MainConfig.getPrefix() + "&7: &aYou are already linked to a discord account!"));
                    return true;
                }else{
                    // set the discord id to the linked person
                }
            }
        }

        return true;
    }
}
