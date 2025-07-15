package com.spotifybt.Spotify.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotifybt.Spotify.dto.ArtistDto;
import com.spotifybt.Spotify.service.AccessTokenManager;
import com.spotifybt.Spotify.service.TokenCache;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserMusicController {

    private final TokenCache cache;
    private final AccessTokenManager accessTokenManager;

    public UserMusicController(TokenCache cache, AccessTokenManager accessTokenManager) {
        this.cache = cache;
        this.accessTokenManager = accessTokenManager;
    }

    /**
     * Endpoint: GET /user/favorites/artists
     * Returns the top 10 artists simplified.
     */
    @GetMapping("/favorites/artists")
    public ResponseEntity<List<ArtistDto>> fetchTopArtists() {
        String token = cache.getAccessToken();
        System.out.println(">>> Access Token from cache: " + token);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ResponseEntity<String> apiResponse = requestTopArtistsFromSpotify(token);

        if (apiResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            System.out.println("Access token is invalid or expired. Attempting refresh...");
            boolean updated = accessTokenManager.updateAccessToken();

            if (updated) {
                apiResponse = requestTopArtistsFromSpotify(cache.getAccessToken());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(apiResponse.getBody());
            JsonNode items = root.path("items");

            List<ArtistDto> top10 = new ArrayList<>();

            for (int i = 0; i < Math.min(10, items.size()); i++) {
                JsonNode artist = items.get(i);
                String id = artist.path("id").asText();
                String name = artist.path("name").asText();
                String image = artist.path("images").isArray() && artist.path("images").size() > 0
                        ? artist.path("images").get(0).path("url").asText()
                        : "";
                String spotifyUrl = artist.path("external_urls").path("spotify").asText();

                top10.add(new ArtistDto(id, name, image, spotifyUrl));
            }

            return ResponseEntity.ok(top10);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Makes the request to Spotify's API to fetch top artists.
     */
    private ResponseEntity<String> requestTopArtistsFromSpotify(String token) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        try {
            return client.exchange(
                    "https://api.spotify.com/v1/me/top/artists",
                    HttpMethod.GET,
                    httpEntity,
                    String.class
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Spotify API call failed: " + ex.getMessage() + "\"}");
        }
    }
}
