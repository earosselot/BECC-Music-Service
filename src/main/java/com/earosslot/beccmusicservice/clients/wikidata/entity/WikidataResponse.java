package com.earosslot.beccmusicservice.clients.wikidata.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WikidataResponse {

    private final Map<String, ArtistInfo> entities = new HashMap<>();

    public Map<String, ArtistInfo> getEntities() {
        return entities;
    }

    @JsonAnySetter
    public void setEntity(String key, ArtistInfo artistInfo) {
        entities.put(key, artistInfo);
    }

}
