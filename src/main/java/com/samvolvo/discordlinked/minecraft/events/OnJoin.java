package com.samvolvo.discordlinked.minecraft.events;

import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.api.database.PlayerCache;
import com.samvolvo.discordlinked.api.database.models.PlayerData;
import com.samvolvo.discordlinked.api.database.utils.PlayerDataUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class OnJoin implements Listener {
    private final DiscordLinked plugin;
    private final PlayerCache playerCache;
    private final Map<Player, Long> titleTimer = new HashMap<>();

    public OnJoin(DiscordLinked plugin){
        this.plugin = plugin;
        this.playerCache = plugin.getPlayerCache();
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        String versionMessage = plugin.getUpdateChecker().generateUpdateMessageColored(plugin.getDescription().getVersion());
        if (versionMessage != null){
            e.getPlayer().sendMessage(versionMessage);
        }

        if (plugin.getConfig().getBoolean("general.joinMessages")){
            plugin.getMessages().sendJoinLeaveDc(e.getPlayer(), "join");
        }

        if (!plugin.getConfig().getBoolean("minecraft.needVerifiedDiscord")){
            return;
        }

        PlayerData data = plugin.getPlayerDataUtil().getDataByUuid(e.getPlayer().getUniqueId().toString());
        plugin.getPlayerCache().put(data.getUuid(), data);

        if (data.getId() != null && data.getId() != ""){
            return;
        }else{
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("minecraft.prefix") + "&7: &cYou need to link your discord account to play on this server!"));
            TextComponent message = new TextComponent("§eClick here to join the discord!");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, plugin.getConfig().getString("discord.invite")));
            e.getPlayer().spigot().sendMessage(message);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        plugin.getPlayerCache().remove(e.getPlayer().getUniqueId().toString());
        plugin.getMessages().sendJoinLeaveDc(e.getPlayer(), "leave");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (!plugin.getConfig().getBoolean("minecraft.needVerifiedDiscord")){
            return;
        }

        PlayerData data = playerCache.get(e.getPlayer().getUniqueId().toString());
        if (data.getId() == null || data.getId().isEmpty()) {
            e.setCancelled(true);
            Player player = e.getPlayer();
            if (titleTimer.getOrDefault(player, (long) 0) < System.currentTimeMillis()) {
                plugin.getMinecraftTools().sendTitle(e.getPlayer(), "§cA linked account is required!", "§ePlease connect your discord account!");
                titleTimer.put(player, System.currentTimeMillis() + 3000);
            }
        }
    }
}
