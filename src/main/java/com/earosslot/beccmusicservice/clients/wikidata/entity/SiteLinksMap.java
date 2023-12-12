package com.earosslot.beccmusicservice.clients.wikidata.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteLinksMap {

    final HashMap<String, SiteLink> siteLinkHashMap = new HashMap<>();

    @JsonAnySetter
    public void setSiteLinkHashMap(String key, SiteLink value) {
        siteLinkHashMap.put(key, value);
    }

    public String getTitle(String sitelink) {
        return siteLinkHashMap.get(sitelink).getTitle();
    }
}
