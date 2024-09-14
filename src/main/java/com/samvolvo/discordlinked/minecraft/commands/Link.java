package com.samvolvo.discordlinked.minecraft.commands;

import com.samvolvo.discordlinked.DiscordLinked;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Link implements CommandExecutor {
    private final DiscordLinked plugin;

    public Link(DiscordLinked plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    /**
    Check if the sender is a Player in minecraft.
     */
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length != 1){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', DiscordLinked.getInstance().getConfig().getString("minecraft.prefix") + "§7: Please provide us with your code send by our discord bot to you. \n if he didn't send it to you use /link in the discord server."));
            }else{
                // check if the player already has a discord id to his profile
                String id = plugin.getPlayerDataUtil().getDataByUuid(p.getUniqueId().toString()).getId(); // set the id
                if (id != null){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',DiscordLinked.getInstance().getConfig().getString("minecraft.prefix") + "&7: &aYou are already linked to a discord account!"));
                    return true;
                }else{
                    // Link the player to the provided id
                    String code = args[0];
                    String discordId = plugin.getCodeCache().get(code);

                    if (discordId != null) {
                        // Code is valid, proceed with linking
                        plugin.getPlayerDataUtil().linkDiscord(p.getUniqueId(), discordId);
                        // Add your linking logic here
                        String username = plugin.getDiscordTools().getUsernameFromId(discordId);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', DiscordLinked.getInstance().getConfig().getString("minecraft.prefix") + "&7: &aYou have been successfully linked to &b&l" + username + "&e!"));
                    } else {
                        // Code is invalid
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', DiscordLinked.getInstance().getConfig().getString("minecraft.prefix") + "&7: &cInvalid code. Please check the code and try again."));
                    }
                }
            }
        }

        return true;
    }
}
