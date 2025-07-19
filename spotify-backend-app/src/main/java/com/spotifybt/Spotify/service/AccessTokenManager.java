/*TokenService*/
package com.spotifybt.Spotify.service;



import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotifybt.Spotify.config.SpotifySettings;

@Service
public class AccessTokenManager {

    private final SpotifySettings settings;
    private final TokenCache tokenCache;

    public AccessTokenManager(SpotifySettings settings, TokenCache tokenCache) {
        this.settings = settings;
        this.tokenCache = tokenCache;
    }

    /**
     * Tries to refresh the current access token using the stored refresh token.
     * 
     * @return true if refresh was successful, false otherwise
     */
    public boolean updateAccessToken() {
        String storedRefreshToken = tokenCache.getRefreshToken();

        if (storedRefreshToken == null || storedRefreshToken.trim().isEmpty()) {
            System.out.println("No refresh token found in cache.");
            return false;
        }

        try {
            // Prepare HTTP request
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String credentials = settings.fetchClientId() + ":" + settings.fetchClientSecret();
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
            httpHeaders.set("Authorization", "Basic " + encodedCredentials);

            String formData = "grant_type=refresh_token&refresh_token=" + storedRefreshToken;
            HttpEntity<String> httpEntity = new HttpEntity<>(formData, httpHeaders);

            // Send POST request
            RestTemplate client = new RestTemplate();
            ResponseEntity<String> tokenResponse = client.postForEntity(
                settings.getTokenEndpoint(),
                httpEntity,
                String.class
            );

            if (tokenResponse.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> parsedJson = new ObjectMapper().readValue(tokenResponse.getBody(), Map.class);

                String refreshedToken = (String) parsedJson.get("access_token");
                if (refreshedToken != null) {
                    tokenCache.saveAccessToken(refreshedToken);
                    System.out.println("Access token refreshed successfully: " + refreshedToken);
                    return true;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Failed to refresh access token.");
        return false;
    }

    public String getAccessToken() {
        return tokenCache.getAccessToken();
    }
}
