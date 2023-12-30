package com.earosslot.beccmusicservice.clients.musicbrainz;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "musify.client.music-brainz")
@Getter
@Setter
public class MusicBrainzConfig {

    private String artistUrl;
    private String userAgentHeader;
    private Long connectTimeoutMs;
    private Long readTimeoutMs;

}
