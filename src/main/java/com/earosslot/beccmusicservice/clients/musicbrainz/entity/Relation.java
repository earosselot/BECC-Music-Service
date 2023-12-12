package com.earosslot.beccmusicservice.clients.musicbrainz.entity;

public class Relation {

    private String type;

    private Url url;

    public Relation(String type, Url url) {
        this.type = type;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }
}
