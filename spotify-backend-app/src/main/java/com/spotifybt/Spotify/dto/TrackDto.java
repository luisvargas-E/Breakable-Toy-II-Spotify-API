package com.spotifybt.Spotify.dto;

public class TrackDto {
    private String id;
    private String name;
    private String artist;
    private String albumImage;
    private String spotifyUrl;

    public TrackDto(String id, String name, String artist, String albumImage, String spotifyUrl) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.albumImage = albumImage;
        this.spotifyUrl = spotifyUrl;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getArtist() { return artist; }
    public String getAlbumImage() { return albumImage; }
    public String getSpotifyUrl() { return spotifyUrl; }
}
