/*SpotifyAuthController*/
package com.spotifybt.Spotify.controller;



import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotifybt.Spotify.config.SpotifySettings;
import com.spotifybt.Spotify.service.TokenCache;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SpotifySettings settings;
    private final TokenCache cache;

    @Autowired
    public AuthController(SpotifySettings settings, TokenCache cache) {
        this.settings = settings;
        this.cache = cache;
    }

    @GetMapping("/spotify")
    public ResponseEntity<Void> initiateSpotifyLogin() {
        String redirect = settings.getAuthEndpoint()
                + "?client_id=" + settings.fetchClientId()
                + "&response_type=code"
                + "&redirect_uri=" + URLEncoder.encode(settings.getCallbackUrl(), StandardCharsets.UTF_8)
                + "&scope=" + URLEncoder.encode(settings.getPermissionScope(), StandardCharsets.UTF_8);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirect))
                .build();
    }

    


    @GetMapping("/spotify/callback")
public ResponseEntity<Void> receiveSpotifyCode(@RequestParam("code") String code) {
    RestTemplate client = new RestTemplate();
    ObjectMapper jsonMapper = new ObjectMapper();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    String credentials = settings.fetchClientId() + ":" + settings.fetchClientSecret();
    String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
    httpHeaders.set("Authorization", "Basic " + encodedCredentials);

    String form = "grant_type=authorization_code"
            + "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8)
            + "&redirect_uri=" + settings.getCallbackUrl();

    HttpEntity<String> httpEntity = new HttpEntity<>(form, httpHeaders);

    try {
        ResponseEntity<String> result = client.postForEntity(settings.getTokenEndpoint(), httpEntity, String.class);

        Map<String, Object> parsed = jsonMapper.readValue(result.getBody(), Map.class);
        String accessToken = (String) parsed.get("access_token");
        String refreshToken = (String) parsed.get("refresh_token");

        cache.saveAccessToken(accessToken);
        cache.saveRefreshToken(refreshToken);

        // 🔁 Redirige al frontend con el token en la URL
        String redirectUrl = settings.getFrontendUrl() + "/callback?access_token=" + accessToken;
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();

    } catch (Exception error) {
        error.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}

}


