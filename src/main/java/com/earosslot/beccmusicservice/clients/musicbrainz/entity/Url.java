package com.earosslot.beccmusicservice.clients.musicbrainz.entity;

public class Url {

    private String resource;

    public Url() {}

    public Url(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
