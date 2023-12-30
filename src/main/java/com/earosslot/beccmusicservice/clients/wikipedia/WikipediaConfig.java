package com.earosslot.beccmusicservice.clients.wikipedia;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "musify.client.wikipedia")
@Getter
@Setter
public class WikipediaConfig {

    private String summaryUrl;
    private Long connectTimeoutMs;
    private Long readTimeoutMs;
}
