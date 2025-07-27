package com.spotifybt.Spotify.dto;

import java.util.List;

public class SearchResultDto {
    private List<ArtistDto> artists;
    private List<TrackDto> tracks;
    private List<AlbumDto> albums;

    public SearchResultDto() {
    // Necesario para instanciación sin argumentos (test y deserialización)
}


    public SearchResultDto(List<ArtistDto> artists, List<TrackDto> tracks, List<AlbumDto> albums) {
        this.artists = artists;
        this.tracks = tracks;
        this.albums = albums;
    }

    public List<ArtistDto> getArtists() {
        return artists;
    }

    public List<TrackDto> getTracks() {
        return tracks;
    }

    public List<AlbumDto> getAlbums() {
        return albums;
    }
}
