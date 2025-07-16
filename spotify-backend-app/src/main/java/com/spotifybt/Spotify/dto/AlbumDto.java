package com.spotifybt.Spotify.dto;

public class AlbumDto {
    private String id;
    private String name;
    private String artist;
    private String image;
    private String spotifyUrl;

    public AlbumDto(String id, String name, String artist, String image, String spotifyUrl) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.image = image;
        this.spotifyUrl = spotifyUrl;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getArtist() { return artist; }
    public String getImage() { return image; }
    public String getSpotifyUrl() { return spotifyUrl; }
}
