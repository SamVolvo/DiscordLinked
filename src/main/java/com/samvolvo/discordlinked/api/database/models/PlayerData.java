package com.samvolvo.discordlinked.api.database.models;

public class PlayerData {
    private final int DataId;
    private String uuid;
    private String id;
    private int warnings;

    public PlayerData(int DataId, String uuid, String id, int warnings){
        this.DataId = DataId;
        this.uuid = uuid;
        this.id = id;
        this.warnings = warnings;
    }

    public int getDataId() {
        return DataId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWarnings() {
        return warnings;
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }
}
