package com.spotifybt.Spotify.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotifybt.Spotify.dto.AlbumDetailsDto;
import com.spotifybt.Spotify.dto.ArtistDetailsDto;
import com.spotifybt.Spotify.dto.TrackDto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ArtistsDetailsService {

    @Autowired
    private AccessTokenManager tokenManager;

    private final String BASE_URL = "https://api.spotify.com/v1";

    public ArtistDetailsDto getArtistDetails(String artistId, String accessToken) {
        String token = tokenManager.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        try {
            // ARTIST
            ResponseEntity<String> artistRes = restTemplate.exchange(
                BASE_URL + "/artists/" + artistId,
                HttpMethod.GET,
                entity,
                String.class
            );
            JsonNode artistJson = mapper.readTree(artistRes.getBody());
            System.out.println("artistJson = " + artistJson.toPrettyString());

            // TOP TRACKS
            ResponseEntity<String> tracksRes = restTemplate.exchange(
                BASE_URL + "/artists/" + artistId + "/top-tracks?market=US",
                HttpMethod.GET,
                entity,
                String.class
            );
            JsonNode tracksJson = mapper.readTree(tracksRes.getBody()).get("tracks");

            // ALBUMS
            ResponseEntity<String> albumsRes = restTemplate.exchange(
                BASE_URL + "/artists/" + artistId + "/albums?limit=15",
                HttpMethod.GET,
                entity,
                String.class
            );
            JsonNode albumsJson = mapper.readTree(albumsRes.getBody()).get("items");

            // Mapear a DTO limpio
            ArtistDetailsDto dto = new ArtistDetailsDto();
            dto.setId(artistJson.get("id").asText());
            dto.setName(artistJson.get("name").asText());
            dto.setFollowers(artistJson.get("followers").get("total").asInt());
            dto.setPopularity(artistJson.get("popularity").asInt());
            dto.setExternalUrl(artistJson.get("external_urls").get("spotify").asText());

            JsonNode images = artistJson.get("images");
            if (images != null && images.isArray() && images.size() > 0) {
                dto.setImageUrl(images.get(0).get("url").asText());
            }

            dto.setTopTracks(tracksJson);
            dto.setAlbums(albumsJson);

            return dto;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching artist details", e);
        }
    }


    // ArtistDetailsService.java

public AlbumDetailsDto getAlbumDetails(String albumId, String accessToken) {
    RestTemplate client = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    HttpEntity<Void> entity = new HttpEntity<>(headers);

    // 1. Obtener datos del álbum
    ResponseEntity<JsonNode> response = client.exchange(
        "https://api.spotify.com/v1/albums/" + albumId,
        HttpMethod.GET,
        entity,
        JsonNode.class
    );

    JsonNode album = response.getBody();
    if (album == null) {
        throw new RuntimeException("Album data not found in response");
    }
    AlbumDetailsDto dto = new AlbumDetailsDto();

    dto.setId(album.path("id").asText());
    dto.setName(album.path("name").asText());
    dto.setImageUrl(album.path("images").get(0).path("url").asText());
    dto.setReleaseYear(album.path("release_date").asText().substring(0, 4));
    dto.setTotalTracks(album.path("total_tracks").asInt());
    dto.setArtistName(album.path("artists").get(0).path("name").asText());

    List<TrackDto> trackList = new ArrayList<>();
    int totalDurationMs = 0;

    for (JsonNode track : album.path("tracks").path("items")) {
        totalDurationMs += track.path("duration_ms").asInt();
        trackList.add(new TrackDto(
            track.path("id").asText(),
            track.path("name").asText(),
            track.path("artists").get(0).path("name").asText(),
            track.path("artists").get(0).path("id").asText(),
            "", // no hay imagen por track
            track.path("external_urls").path("spotify").asText()
        ));
    }

    dto.setTracks(trackList);
    dto.setTotalDuration(formatDuration(totalDurationMs));

    return dto;
}

private String formatDuration(int ms) {
    int totalSeconds = ms / 1000;
    int minutes = totalSeconds / 60;
    int seconds = totalSeconds % 60;
    return String.format("%d:%02d", minutes, seconds);
}

}
