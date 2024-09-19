package com.samvolvo.discordlinked.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.samvolvo.discordlinked.DiscordLinked;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpdateChecker {
    private final DiscordLinked plugin;

    public UpdateChecker(DiscordLinked plugin){
        this.plugin = plugin;
    }

    private final String RELEASES_URL = "https://api.github.com/repos/SamVolvo/DiscordLinked/releases";

    public List<String> generateUpdateMessage(String v) {
        try{
            String currentVersion = "V" + v;
            int releasesBehind = getReleasesBehind(currentVersion);
            JsonArray releases = getAllReleases();
            String tagName = releases.get(0).getAsJsonObject().get("tag_name").getAsString();
            List<String> message = new ArrayList<>();
            if (releasesBehind > 0) {
                message.add("*********************************************************************");
                       message.add("DiscordLinked is outdated!");
                       message.add("\"Latest version: " + tagName);
                       message.add("Your version: " +   plugin.getDescription().getVersion());
                       message.add("https://www.spigotmc.org/resources/discordlinked.119665/");
                       message.add("*********************************************************************");
            }else{
               return message;
            }
            return message;
        }catch (Exception e){
            return Collections.singletonList("Unable to connect to version Check!");
        }
    }

    public String generateUpdateMessageColored(String v) {
        String currentVersion = "V" + v;
        int releasesBehind = getReleasesBehind(currentVersion);
        if (releasesBehind > 0) {
            return ("§cYou are §4" + releasesBehind + "§c release(s) behind!\n" +
                    "Download the newest release at https://www.spigotmc.org/resources/discordlinked.119665/");
        }
        else {
            return null;
        }
    }

    private int getReleasesBehind(String currentVersion) {
        try {
            int behind = 0;
            JsonArray releases = getAllReleases();
            if (releases != null) {
                for (JsonElement release : releases) {
                    String tagName = release.getAsJsonObject().get("tag_name").getAsString();
                    if (!tagName.equalsIgnoreCase(currentVersion)) behind++;
                    else return behind;
                }
                return behind;
            }
        } catch (IOException ignore) {
        }
        return 0;
    }

    private JsonArray getAllReleases() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(RELEASES_URL)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return JsonParser.parseString(response.body().string()).getAsJsonArray();
            } else {
                return null;
            }
        }
    }
}
