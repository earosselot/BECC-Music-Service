package com.earosslot.beccmusicservice.clients.coverartarchive.entity;


import java.util.List;

public class AlbumCover {

    public static final String FRONT_COVER = "Front";
    public static final String ALBUM_IMAGE_NOT_FOUND = "Album image not found";
    private List<Image> images;

    public AlbumCover() {
    }

    public AlbumCover(List<Image> images) {
        this.images = images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getFrontImage() {
        try {
            Image frontImage = images.stream()
                    .filter(image -> image.types.contains(FRONT_COVER))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(ALBUM_IMAGE_NOT_FOUND));
            return frontImage.getImage();
        } catch (Exception exception) {
            throw new RuntimeException(ALBUM_IMAGE_NOT_FOUND);
        }
    }

    static class Image {

        List<String> types;
        String image;

        public Image() {
        }

        public Image(List<String> types, String image) {
            this.types = types;
            this.image = image;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
