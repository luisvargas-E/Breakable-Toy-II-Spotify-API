package com.spotifybt.Spotify.controller;




import com.spotifybt.Spotify.dto.ArtistDetailsDto;
import com.spotifybt.Spotify.service.ArtistsDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artists")
public class ArtistDetailsController {

    @Autowired
    private ArtistsDetailsService artistsDetailsService;

    @GetMapping("/{id}")
public ResponseEntity<ArtistDetailsDto> getArtistDetails(
        @PathVariable String id,
        @RequestHeader("Authorization") String authHeader) {

    String accessToken = authHeader.replace("Bearer ", "");
    ArtistDetailsDto details = artistsDetailsService.getArtistDetails(id, accessToken);
    return ResponseEntity.ok(details);
}

}
