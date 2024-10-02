package com.samvolvo.discordlinked.api.database;
import com.samvolvo.discordlinked.DiscordLinked;
import com.samvolvo.discordlinked.api.database.models.PlayerData;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;

public class Database {
    private HikariDataSource dataSource;
    private DiscordLinked plugin;


    public Database(DiscordLinked discordLinked){
        try{
            plugin = discordLinked;
            FileConfiguration config = plugin.getConfig();
            HikariConfig dbConfig = new HikariConfig();
            dbConfig.setJdbcUrl("jdbc:mysql://" + config.getString("database.URL") + "/" + config.getString("database.Name"));
            dbConfig.setUsername(config.getString("database.User"));
            dbConfig.setPassword(config.getString("database.Password"));
            dbConfig.addDataSourceProperty("cachePrepStmts", "true");
            dbConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            dbConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            dbConfig.setMaximumPoolSize(100);

            dataSource = new HikariDataSource(dbConfig);

            // Test the connection and log succes!
            try(Connection connection = dataSource.getConnection()){
                if (connection != null && !connection.isClosed()){
                    plugin.samvolvoLogger().info("Successfully connected to the database.");
                }
                createTables();
            }catch (SQLException e){
                plugin.samvolvoLogger().error("Failed to connect to the database.");
            }
        }catch (Exception e){
            plugin.samvolvoLogger().warning("Could not connect to a database.");
        }
    }

    public Connection getConnection(){
        try{
            return dataSource.getConnection();
        }catch (SQLException e){
            plugin.samvolvoLogger().info("An error accured while trying to get the database.");
            return null;
        }
    }

    public void close(){
        if (dataSource != null && !dataSource.isClosed()){
            dataSource.close();
        }
    }

    private void createTables(){
        Connection connection = getConnection();
        try{
            Statement playerDataStatement = connection.createStatement();
            playerDataStatement.execute("CREATE TABLE IF NOT EXISTS PlayerData(DataId INT AUTO_INCREMENT PRIMARY KEY, uuid varchar(36), id varchar(36), warnings TINYINT UNSIGNED);");

        }catch (SQLException e){
            plugin.samvolvoLogger().info("There was an error in the database.");
        }
    }

    // PlayerData database functions
    public PlayerData findPlayerDataByUUID(String uuid){
        PreparedStatement statement = null;
        try{
            statement = getConnection().prepareStatement("SELECT * FROM PlayerData WHERE uuid = ?;");
            statement.setString(1, uuid);

            ResultSet results = statement.executeQuery();

            if (results.next()){
                int DataId = results.getInt("DataId");
                String Id = results.getString("id");
                int warnings = results.getInt("warnings");

                PlayerData data = new PlayerData(DataId, uuid, Id, warnings);
                statement.close();
                return data;
            }else{
                statement.close();
                PlayerData data = new PlayerData(0, uuid, null, 0);
                createPlayerData(data);
                return data;
            }

        }catch (SQLException e){
            plugin.samvolvoLogger().error("There was an error finding data in the database.");
            throw new RuntimeException(e);
        }
    }

    public PlayerData findPlayerDataById(String id){
        PreparedStatement statement = null;
        try{
            statement = getConnection().prepareStatement("SELECT * FROM PlayerData WHERE id = ?;");
            statement.setString(1, id);

            ResultSet results = statement.executeQuery();

            if (results.next()){
                int DataId = results.getInt("DataId");
                String uuid = results.getString("uuid");
                int warnings = results.getInt("warnings");

                PlayerData data = new PlayerData(DataId, uuid, id, warnings);
                statement.close();
                return data;
            }else{
                statement.close();
                PlayerData data = new PlayerData(0, null, id, 0);
                createPlayerData(data);
                return data;
            }

        }catch (SQLException e){
            plugin.samvolvoLogger().error("There was an error finding data in the database.");
            throw new RuntimeException(e);
        }
    }


    private void createPlayerData(PlayerData data) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "INSERT INTO PlayerData (uuid, id, warnings) VALUES (?, ?, ?);"
            );
            statement.setString(1, data.getUuid());
            statement.setString(2, data.getId());
            statement.setInt(3, data.getWarnings());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            plugin.samvolvoLogger().info("There was an error creating player data in the database.");
            throw new RuntimeException(e);
        }
    }

    public void updatePlayerData(PlayerData playerData) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "UPDATE PlayerData SET id = ?, warnings = ? WHERE uuid = ?;"
            );
            statement.setString(1, playerData.getId());
            statement.setInt(2, playerData.getWarnings());
            statement.setString(3, playerData.getUuid());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            plugin.samvolvoLogger().error("There was an error updating player data in the database.");
            throw new RuntimeException(e);
        }
    }
}
