package com.spotifybt.Spotify.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class ArtistDetailsDto {
    private String id;
    private String name;
    private String imageUrl;
    private int followers;
    private int popularity;
    private String externalUrl;
    private JsonNode topTracks;
    private JsonNode albums;

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public JsonNode getTopTracks() {
        return topTracks;
    }

    public void setTopTracks(JsonNode topTracks) {
        this.topTracks = topTracks;
    }

    public JsonNode getAlbums() {
        return albums;
    }

    public void setAlbums(JsonNode albums) {
        this.albums = albums;
    }
}
