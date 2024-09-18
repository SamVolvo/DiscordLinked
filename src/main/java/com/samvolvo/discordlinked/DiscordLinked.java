package com.samvolvo.discordlinked;

import com.samvolvo.discordlinked.api.database.CodeCache;
import com.samvolvo.discordlinked.api.database.Database;
import com.samvolvo.discordlinked.api.database.PlayerCache;
import com.samvolvo.discordlinked.api.database.utils.PlayerDataUtil;
import com.samvolvo.discordlinked.api.tools.DiscordTools;
import com.samvolvo.discordlinked.api.tools.Messages;
import com.samvolvo.discordlinked.api.tools.MinecraftTools;
import com.samvolvo.discordlinked.discord.commands.Account;
import com.samvolvo.discordlinked.discord.commands.Link;
import com.samvolvo.discordlinked.discord.events.DcChatEvent;
import com.samvolvo.discordlinked.discord.managers.CommandManager;
import com.samvolvo.discordlinked.discord.managers.EmbedManager;
import com.samvolvo.discordlinked.minecraft.commands.Warn;
import com.samvolvo.discordlinked.minecraft.events.CommandEvent;
import com.samvolvo.discordlinked.minecraft.events.McChatEvent;
import com.samvolvo.discordlinked.minecraft.events.OnJoin;
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

public final class DiscordLinked extends JavaPlugin {
    private static DiscordLinked instance;

    private static FileConfiguration config;
    private static File configFile;
    private static ShardManager shardManager;
    private static File userDirectory;

    private int tokenState;

    private static Database database;
    private PlayerDataUtil playerDataUtil;
    private PlayerCache playerCache;
    private CodeCache codeCache;

    // Utils
    private Messages messages;
    private DiscordTools discordTools;
    private MinecraftTools minecraftTools;
    private EmbedManager embedManager;

    // Config
    private String prefix;

    @Override
    public void onEnable() {
        instance = this;


        Bukkit.getConsoleSender().sendMessage("§bDiscord§aLinked§7: §aActive");

        saveDefaultConfig();
        loadConfig();

        prefix = getConfig().getString("minecraft.prefix");

        // Activate Classes
            // Data
        database = new Database(this);
        playerCache = new PlayerCache(this);
        codeCache = new CodeCache();
        playerDataUtil = new PlayerDataUtil(this);
        embedManager = new EmbedManager(this);


        //Config Checks!
        String token = getConfig().getString("DC_Token");

        if (token == null || token.isEmpty()) {
            getLogger().info("Disabling DiscordLinked: Please fill in the bot token in the config.yml! Code: 0");
            getServer().getPluginManager().disablePlugin(this);
            tokenState = 0;
            return; // Exit the onEnable method early
        }

        //Discord
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(getConfig().getString("DC_Token"));
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setActivity(Activity.playing("Minecraft"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        builder.build();
        shardManager = builder.build();

        // Tools
        discordTools = new DiscordTools(this);
        minecraftTools = new MinecraftTools(this);
        messages = new Messages(this);

        shardManager.addEventListener(new CommandManager(this),new DcChatEvent(this),new Account(this), new Link(this));

        //Commands
        getCommand("link").setExecutor(new com.samvolvo.discordlinked.minecraft.commands.Link(this));
        getCommand("warn").setExecutor(new Warn(this));

        //Listeners
        Bukkit.getPluginManager().registerEvents(new McChatEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new CommandEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new OnJoin(this), this);



    }




    @Override
    public void onDisable() {
        if (tokenState == 0){
            for (Player player : Bukkit.getOnlinePlayers()){
                messages.sendJoinLeaveDc(player, "leave");
            }

            messages.sendEmbedDc(EmbedManager.startStop("off"));
        }

        Bukkit.getConsoleSender().sendMessage("§bDiscord&aLinked: §c§lDisabled");
        saveConfig();
    }


    // Getters
    public ShardManager getShardManager() {
        return shardManager;
    }

    public PlayerCache getPlayerCache(){
        return playerCache;
    }

    public CodeCache getCodeCache(){
        return codeCache;
    }

    public PlayerDataUtil getPlayerDataUtil(){
        return playerDataUtil;
    }

    public DiscordTools getDiscordTools(){
        return discordTools;
    }

    public Messages getMessages(){
        return messages;
    }

    public MinecraftTools getMinecraftTools(){
        return minecraftTools;
    }

    public String getPrefix(){
        return prefix;
    }

    public EmbedManager getEmbedManager(){
        return embedManager;
    }

    // Config

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

    public static Database getDatabase(){
        return database;
    }

    public static DiscordLinked getInstance(){
        return instance;
    }

}
