


// AlbumController.java
package com.spotifybt.Spotify.controller;

import com.spotifybt.Spotify.dto.AlbumDetailsDto;
import com.spotifybt.Spotify.service.ArtistsDetailsService;
import com.spotifybt.Spotify.service.TokenCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final ArtistsDetailsService service;
    private final TokenCache cache;

    public AlbumController(ArtistsDetailsService service, TokenCache cache) {
        this.service = service;
        this.cache = cache;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDetailsDto> getAlbumDetails(@PathVariable String id) {
        String token = cache.getAccessToken();
        if (token == null) return ResponseEntity.status(401).build();

        try {
            AlbumDetailsDto dto = service.getAlbumDetails(id, token);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
