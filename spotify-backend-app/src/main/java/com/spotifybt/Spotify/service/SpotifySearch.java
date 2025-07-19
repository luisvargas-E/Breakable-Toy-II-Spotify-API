package com.spotifybt.Spotify.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotifybt.Spotify.dto.ArtistDto;
import com.spotifybt.Spotify.dto.TrackDto;
import com.spotifybt.Spotify.dto.AlbumDto;
import com.spotifybt.Spotify.dto.SearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifySearch {

    private final TokenCache tokenCache;
    private final AccessTokenManager accessTokenManager;

    public ResponseEntity<SearchResultDto> search(String query) {
        String token = tokenCache.getAccessToken();
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ResponseEntity<String> response = sendSearchRequest(query, "artist,track,album", token);

        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            boolean refreshed = accessTokenManager.updateAccessToken();
            if (refreshed) {
                token = tokenCache.getAccessToken();
                response = sendSearchRequest(query, "artist,track,album", token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            // ARTISTS
            List<ArtistDto> artists = new ArrayList<>();
            for (JsonNode artist : root.path("artists").path("items")) {
                String id = artist.path("id").asText();
                String name = artist.path("name").asText();
                String image = artist.path("images").isArray() && artist.path("images").size() > 0
                        ? artist.path("images").get(0).path("url").asText()
                        : "";
                String spotifyUrl = artist.path("external_urls").path("spotify").asText();
                artists.add(new ArtistDto(id, name, image, spotifyUrl));
            }

            // TRACKS
            List<TrackDto> tracks = new ArrayList<>();
            for (JsonNode track : root.path("tracks").path("items")) {
                String id = track.path("id").asText();
                String name = track.path("name").asText();
                String artistName = track.path("artists").get(0).path("name").asText();
                String artistId = track.get("artists").get(0).get("id").asText();
                String image = track.path("album").path("images").get(0).path("url").asText();
                String spotifyUrl = track.path("external_urls").path("spotify").asText();
                tracks.add(new TrackDto(id, name, artistName,artistId, image, spotifyUrl));
            }

            // ALBUMS
            List<AlbumDto> albums = new ArrayList<>();
            for (JsonNode album : root.path("albums").path("items")) {
                String id = album.path("id").asText();
                String name = album.path("name").asText();
                String artistName = album.path("artists").get(0).path("name").asText();
                String image = album.path("images").isArray() && album.path("images").size() > 0
                        ? album.path("images").get(0).path("url").asText()
                        : "";
                String spotifyUrl = album.path("external_urls").path("spotify").asText();
                albums.add(new AlbumDto(id, name, artistName, image, spotifyUrl));
            }

            return ResponseEntity.ok(new SearchResultDto(artists, tracks, albums));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ResponseEntity<String> sendSearchRequest(String query, String type, String token) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        String url = UriComponentsBuilder.fromUri(URI.create("https://api.spotify.com/v1/search"))
                .queryParam("q", query)
                .queryParam("type", type)
                .queryParam("limit", 10)
                .toUriString();

        HttpEntity<Void> request = new HttpEntity<>(headers);
        try {
            return client.exchange(url, HttpMethod.GET, request, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Failed to search on Spotify: " + e.getMessage() + "\"}");
        }
    }
}
