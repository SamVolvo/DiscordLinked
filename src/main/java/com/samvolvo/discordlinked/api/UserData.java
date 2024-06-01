package com.samvolvo.discordlinked.api;

public class UserData {

    private String displayName;
    private String discordId;
    private int warnings;

    public UserData(String displayName, String discordId, int warnings) {
        this.displayName = displayName;
        this.discordId = discordId;
        this.warnings = warnings;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public int getWarnings() {
        return warnings;
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }
}
