package com.earosslot.beccmusicservice.clients.wikipedia.entity;

import com.fasterxml.jackson.annotation.JsonAlias;

public class WikipediaResponse {

    @JsonAlias("extract_html")
    String extractHtml;

    public String getExtractHtml() {
        return extractHtml;
    }

    public void setExtractHtml(String extractHtml) {
        this.extractHtml = extractHtml;
    }
}
