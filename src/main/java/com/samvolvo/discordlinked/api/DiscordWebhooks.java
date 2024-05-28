package com.samvolvo.discordlinked.api;

import com.samvolvo.discordlinked.Utils.MainConfig;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiscordWebhooks {

    public static void sendDiscordWebhookChat(String username, String avatarUrl, String content) {
        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("avatar_url", avatarUrl);
            json.put("content", content);

            URL url = new URL(MainConfig.getString("discord.webhook_url"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            os.write(json.toString().getBytes());
            os.flush();
            os.close();

            connection.getResponseCode(); // Trigger the request
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendDiscordWebhookCommand(String username, String avatarUrl, String content) {
        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("avatar_url", avatarUrl);
            json.put("content", username + " used the " + content + " command.");

            URL url = new URL(MainConfig.getString("discord.webhook_url"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            os.write(json.toString().getBytes());
            os.flush();
            os.close();

            connection.getResponseCode(); // Trigger the request
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
