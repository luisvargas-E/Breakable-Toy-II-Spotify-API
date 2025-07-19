package com.spotifybt.Spotify.dto;

import java.util.List;

public class AlbumDetailsDto {
    private String id;
    private String name;
    private String imageUrl;
    private String releaseYear;
    private int totalTracks;
    private String totalDuration;
    private String artistName;
    private List<TrackDto> tracks;

    public AlbumDetailsDto() {
    }

    public AlbumDetailsDto(String id, String name, String imageUrl, String releaseYear, int totalTracks,
                           String totalDuration, String artistName, List<TrackDto> tracks) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.releaseYear = releaseYear;
        this.totalTracks = totalTracks;
        this.totalDuration = totalDuration;
        this.artistName = artistName;
        this.tracks = tracks;
    }

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

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getTotalTracks() {
        return totalTracks;
    }

    public void setTotalTracks(int totalTracks) {
        this.totalTracks = totalTracks;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public List<TrackDto> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDto> tracks) {
        this.tracks = tracks;
    }
}
