package com.earosslot.beccmusicservice.clients.wikidata;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "musify.client.wikidata")
@Getter
@Setter
public class WikidataConfig {

    private String entityDataUrl;
    private Long connectTimeoutMs;
    private Long readTimeoutMs;
}
