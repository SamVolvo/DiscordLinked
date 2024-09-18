package com.samvolvo.discordlinked.minecraft.commands;

import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.api.database.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Warn implements CommandExecutor {
    private final DiscordLinked plugin;

    public Warn(DiscordLinked plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player){

            if (!player.hasPermission("discordlinked.warn")){
                plugin.getMessages().McError(player, 2);
                return true;
            }

            if (args.length != 1){
                plugin.getMessages().McError(player, 12);
                return false;
            }

                Player target = Bukkit.getPlayer(args[0]);
                if (target == null){
                    plugin.getMessages().McError(player, 3);
                    return true;
                }

                PlayerData data = plugin.getPlayerCache().get(target.getUniqueId().toString());
                int warnings = data.getWarnings();

                data.setWarnings(warnings + 1);
                plugin.getPlayerCache().put(data.getUuid(), data);
                plugin.getPlayerDataUtil().updateData(data.getUuid(), data);

                plugin.getMessages().McMessage(player, plugin.getPrefix() + "&7: &eWarned " + target.getName());
                plugin.getMessages().McMessage(target, plugin.getPrefix() + "&7: &eYou just got warned by &c" + player.getDisplayName());
        }
        return true;
    }
}
