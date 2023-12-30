package com.earosslot.beccmusicservice.clients.musicbrainz.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Relation {

    private String type;

    private Url url;

}
