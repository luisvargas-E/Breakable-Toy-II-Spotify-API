
package com.spotifybt.Spotify.dto;

public class ArtistDto {
    private String id;
    private String name;
    private String image;
    private String spotifyUrl;

    public ArtistDto(String id, String name, String image, String spotifyUrl) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.spotifyUrl = spotifyUrl;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getImage() { return image; }
    public String getSpotifyUrl() { return spotifyUrl; }
}
