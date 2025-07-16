package com.spotifybt.Spotify.controller;

import com.spotifybt.Spotify.dto.SearchResultDto;
import com.spotifybt.Spotify.service.SpotifySearch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spotify")
@RequiredArgsConstructor
public class SpotifySearchController {

    private final SpotifySearch searchService;

    /**
     * Endpoint: GET /spotify/search?query={query}
     * Example: /spotify/search?query=Oasis
     */
    @GetMapping("/search")
    public ResponseEntity<SearchResultDto> search(@RequestParam String query) {
        System.out.println(">>> Realizando búsqueda completa para: " + query);
        return searchService.search(query);
    }
}
