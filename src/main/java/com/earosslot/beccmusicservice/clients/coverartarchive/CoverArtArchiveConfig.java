package com.earosslot.beccmusicservice.clients.coverartarchive;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "musify.client.cover-art-archive")
@Getter
@Setter
public class CoverArtArchiveConfig {

    private String coverUrl;
    private Long connectTimeoutMs;
    private Long readTimeoutMs;
}
