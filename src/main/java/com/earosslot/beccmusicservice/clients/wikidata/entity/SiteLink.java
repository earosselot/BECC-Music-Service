package com.earosslot.beccmusicservice.clients.wikidata.entity;

public class SiteLink {

    String site;

    String title;

    public SiteLink() {}

    public SiteLink(String site, String title) {
        this.site = site;
        this.title = title;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
