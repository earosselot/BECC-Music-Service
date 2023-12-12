package com.earosslot.beccmusicservice.entity;

import com.earosslot.beccmusicservice.clients.musicbrainz.entity.AlbumMB;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Album {

    private final String id;
    private final String title;
    private String imageUrl;

    public Album(String id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public Album(AlbumMB releaseGroup) {
        id = releaseGroup.getId();
        title = releaseGroup.getTitle();
    }

    @Override
    public String toString() {
        return "Album {" + "\n" +
                "id='" + id + '\'' + "\n" +
                ", title='" + title + '\'' + "\n" +
                ", imageUrl='" + imageUrl + '\'' + "\n" +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (!id.equals(album.id)) return false;
        if (!Objects.equals(title, album.title)) return false;
        return Objects.equals(imageUrl, album.imageUrl);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String albumImage) {
        this.imageUrl = albumImage;
    }

    public boolean hasImageUrl() {
        return imageUrl != null;
    }
}
