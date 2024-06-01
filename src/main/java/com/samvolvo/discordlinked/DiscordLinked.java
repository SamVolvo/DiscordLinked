package com.samvolvo.discordlinked;

import com.samvolvo.discordlinked.Utils.*;
import com.samvolvo.discordlinked.api.CustomConfigCreator;
import com.samvolvo.discordlinked.api.UserData;
import com.samvolvo.discordlinked.discord.commands.Account;
import com.samvolvo.discordlinked.discord.events.DcChatEvent;
import com.samvolvo.discordlinked.discord.managers.CommandManager;
import com.samvolvo.discordlinked.discord.managers.EmbedManager;
import com.samvolvo.discordlinked.minecraft.events.CommandEvent;
import com.samvolvo.discordlinked.minecraft.events.McChatEvent;
import com.samvolvo.discordlinked.minecraft.events.JoinLeave;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public final class DiscordLinked extends JavaPlugin {

    private static FileConfiguration config;
    private static File configFile;
    private static ShardManager shardManager;
    private static File userDirectory;
    private static HashMap<UUID, UserData> userDataMap = new HashMap<>();
    private static HashMap<String, UUID> discordIdMap = new HashMap<>();

    private int tokenState;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§bDiscord&aLinked: §a§lActive");

        saveDefaultConfig();
        loadConfig();

        userDirectory = new File(getDataFolder(), "userDataFiles");
        if (!userDirectory.exists()) {
            userDirectory.mkdirs();
        }

        CustomConfigCreator.setCustomFile(getDataFolder().toString(), "discordIdUUID");
        FileConfiguration discordIdUUIDFile = CustomConfigCreator.getCustomFile("discordIdUUID");
        discordIdUUIDFile.options().copyDefaults(true);
        CustomConfigCreator.saveCustomFile("discordIdUUID");
        for (String discordId : discordIdUUIDFile.getConfigurationSection("discordId").getKeys(false)) {
            discordIdMap.put(discordId, UUID.fromString(discordIdUUIDFile.get("discordId").toString()));
        }

        //Config Checks!
        String token = getConfig().getString("DC_Token");
        getLogger().info("DC_Token in config: " + token); // Debug statement

        if (token == null || token.equals("")) {
            getLogger().info("§cDisabling §cDiscordLinked: §cPlease fill in the bot token in the config.yml!");
            getServer().getPluginManager().disablePlugin(this);
            tokenState = 0;
            return; // Exit the onEnable method early
        } else {
            getLogger().info("§aDC_Token found in config: " + token);
        }

        MainConfig cnf = new MainConfig(this);
        //Discord
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(getConfig().getString("DC_Token"));
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setActivity(Activity.playing("Minecraft"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        builder.build();
        shardManager = builder.build();

        shardManager.addEventListener(new CommandManager(),new DcChatEvent(),new Account());

        //Commands

        //Listeners
        Bukkit.getPluginManager().registerEvents(new JoinLeave(), this);
        Bukkit.getPluginManager().registerEvents(new McChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new CommandEvent(), this);

    }


    public static ShardManager getShardManager() {
        return shardManager;
    }

    @Override
    public void onDisable() {
        if (tokenState == 0){
            for (Player player : Bukkit.getOnlinePlayers()){
                SendToDiscord.sendJoinLeaveDc(player, "leave");
            }

            SendToDiscord.sendEmbedDc(EmbedManager.startStop("off"));
        }

        Bukkit.getConsoleSender().sendMessage("§bDiscord&aLinked: §c§lDisabled");
        saveConfig();
    }

    private void loadConfig() {
        if (configFile == null) {
            configFile = new File(getDataFolder(), "config.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            getLogger().warning("Unable to save config.yml!");
        }
    }

    public static void joinAllPlayers(){
        for (Player player : Bukkit.getOnlinePlayers()){
            SendToDiscord.sendJoinLeaveDc(player, "join");
        }
    }

    public static File getUserDirectory() {
        return userDirectory;
    }

    public static HashMap<UUID, UserData> getUserDataMap() {
        return userDataMap;
    }

}
