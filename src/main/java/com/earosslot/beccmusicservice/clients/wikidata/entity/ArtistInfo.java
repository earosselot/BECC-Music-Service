package com.earosslot.beccmusicservice.clients.wikidata.entity;

public class ArtistInfo {

    private SiteLinksMap sitelinks;

    public ArtistInfo() {
    }

    public ArtistInfo(SiteLinksMap sitelinks) {
        this.sitelinks = sitelinks;
    }

    public void setSitelinks(SiteLinksMap sitelinks) {
        this.sitelinks = sitelinks;
    }

    public String getSitelinkTitle(String sitelink) {
        return sitelinks.getTitle(sitelink);
    }
}
