package com.earosslot.beccmusicservice.clients.coverartarchive.entity;


import com.earosslot.beccmusicservice.exeptions.MusifyException;
import lombok.Setter;

import java.util.List;

@Setter
public class AlbumCover {

    public static final String FRONT_COVER = "Front";
    public static final String ALBUM_IMAGE_NOT_FOUND = "Album image not found";
    private List<Image> images;

    public AlbumCover() {
    }

    public AlbumCover(List<Image> images) {
        this.images = images;
    }

    public String getFrontImage() {
        try {
            Image frontImage = images.stream()
                    .filter(image -> image.types.contains(FRONT_COVER))
                    .findFirst()
                    .orElseThrow(() -> new MusifyException(ALBUM_IMAGE_NOT_FOUND));
            return frontImage.getImage();
        } catch (Exception exception) {
            throw new MusifyException(ALBUM_IMAGE_NOT_FOUND);
        }
    }
}
