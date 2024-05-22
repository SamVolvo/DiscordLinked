package com.samvolvo.discordlinked;

import com.samvolvo.discordlinked.Utils.*;
import com.samvolvo.discordlinked.minecraft.events.JoinLeave;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class DiscordLinked extends JavaPlugin {

    private static FileConfiguration config;
    private static File configFile;
    private static ShardManager shardManager;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§bDCLinked: §a§lActive");

        saveDefaultConfig();
        loadConfig();



        //Config Checks!
        String token = getConfig().getString("DC_Token");
        getLogger().info("DC_Token in config: " + token); // Debug statement

        if (token == null || token.equals("")) {
            getLogger().info("§cDisabling §cDiscordLinked: §cPlease fill in the bot token in the config.yml!");
            getServer().getPluginManager().disablePlugin(this);
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

        shardManager.addEventListener();

        //Commands

        //Listeners
        Bukkit.getPluginManager().registerEvents(new JoinLeave(), this);

    }


    public static ShardManager getShardManager() {
        return shardManager;
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§bDCLinked: §a§lDisabled");
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

}
