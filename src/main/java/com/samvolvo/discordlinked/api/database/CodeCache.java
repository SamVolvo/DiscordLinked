package com.samvolvo.discordlinked.api.database;

import com.samvolvo.discordlinked.api.database.models.PlayerData;

import java.util.HashMap;

public class CodeCache {
    private final HashMap<String, String> cache;

    public CodeCache(){
        this.cache = new HashMap<>();
    }

    public void put(String code, String id){
        cache.put(code, id);
    }

    public String get(String code){
        /* returns the id what is linked to this code */
        return cache.get(code);
    }

    public void Remove(String code){
        cache.remove(code);
    }

}
