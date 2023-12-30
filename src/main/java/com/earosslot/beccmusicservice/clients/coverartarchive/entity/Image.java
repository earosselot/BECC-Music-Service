package com.earosslot.beccmusicservice.clients.coverartarchive.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
class Image {

    List<String> types;
    String image;

    public Image(List<String> types, String image) {
        this.types = types;
        this.image = image;
    }

}
